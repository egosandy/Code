package com.rcdriver.cs.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rcdriver.cs.models.FirebaseToken;
import com.rcdriver.cs.models.FiturModel;
import com.rcdriver.cs.models.ItemModel;
import com.rcdriver.cs.models.PesananMerchant;
import com.rcdriver.cs.models.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Lightweight local persistence facade that replaces the previous Realm layer.
 *
 * <p>Backed by SharedPreferences + Gson, with an in-memory cache so repeated reads
 * are as cheap as the old Realm layer (Realm kept managed objects in memory). The
 * data is parsed from disk ONCE; subsequent getAll*/get* calls return the cached
 * objects without touching SharedPreferences or Gson again. The cache is only
 * refreshed when the app stores new backend data via a save*/upsert/delete call.
 * This is what keeps the app light: fetch from backend -> store once -> read from
 * memory; only re-parse when the data actually changes.
 *
 * <p>The whole app talks to this single facade; the public API is unchanged.
 */
public final class LocalStore {

    private static final String PREFS = "rc_local_store";
    private static final String K_USER = "user";
    private static final String K_TOKEN = "fcm_token";
    private static final String K_FITUR = "fitur_list";
    private static final String K_ITEM = "item_list";
    private static final String K_CART = "cart_list";

    private static volatile LocalStore instance;

    private final SharedPreferences prefs;
    private final Gson gson = new Gson();

    // ---- in-memory caches (parsed once, reused until the data changes) ----
    private User userCache;
    private boolean userLoaded;
    private FirebaseToken tokenCache;
    private boolean tokenLoaded;
    private List<FiturModel> fiturCache;
    private List<ItemModel> itemCache;
    private List<PesananMerchant> cartCache;

    private LocalStore(Context ctx) {
        prefs = ctx.getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    /** Initialised once from {@code BaseApp.onCreate()}. */
    public static synchronized void init(Context ctx) {
        if (instance == null) {
            instance = new LocalStore(ctx);
        }
    }

    /** Returns the singleton; lazily initialises from the given context if needed. */
    public static synchronized LocalStore get(Context ctx) {
        if (instance == null) {
            instance = new LocalStore(ctx);
        }
        return instance;
    }

    /** Returns the singleton initialised in {@code BaseApp}. */
    public static LocalStore get() {
        return instance;
    }

    // ---------------------------------------------------------------- User

    public synchronized void saveUser(User user) {
        userCache = user;
        userLoaded = true;
        prefs.edit().putString(K_USER, user == null ? null : gson.toJson(user)).apply();
    }

    public synchronized User getUser() {
        if (!userLoaded) {
            String s = prefs.getString(K_USER, null);
            userCache = s == null ? null : gson.fromJson(s, User.class);
            userLoaded = true;
        }
        return userCache;
    }

    public synchronized void deleteUser() {
        userCache = null;
        userLoaded = true;
        prefs.edit().remove(K_USER).apply();
    }

    // ---------------------------------------------------------------- FCM token

    public synchronized void saveToken(FirebaseToken token) {
        tokenCache = token;
        tokenLoaded = true;
        prefs.edit().putString(K_TOKEN, token == null ? null : gson.toJson(token)).apply();
    }

    public synchronized FirebaseToken getToken() {
        if (!tokenLoaded) {
            String s = prefs.getString(K_TOKEN, null);
            tokenCache = s == null ? null : gson.fromJson(s, FirebaseToken.class);
            tokenLoaded = true;
        }
        return tokenCache;
    }

    // ---------------------------------------------------------------- Fitur

    public synchronized void saveFitur(List<FiturModel> list) {
        fiturCache = list != null ? new ArrayList<>(list) : new ArrayList<FiturModel>();
        prefs.edit().putString(K_FITUR, gson.toJson(fiturCache)).apply();
    }

    public synchronized List<FiturModel> getAllFitur() {
        if (fiturCache == null) {
            fiturCache = readList(K_FITUR, new TypeToken<ArrayList<FiturModel>>() {}.getType());
        }
        return fiturCache;
    }

    public synchronized FiturModel getFitur(int idFitur) {
        for (FiturModel f : getAllFitur()) {
            if (f != null && f.getIdFitur() == idFitur) {
                return f;
            }
        }
        return null;
    }

    // ---------------------------------------------------------------- Merchant items

    public synchronized void saveItems(List<ItemModel> list) {
        itemCache = list != null ? new ArrayList<>(list) : new ArrayList<ItemModel>();
        prefs.edit().putString(K_ITEM, gson.toJson(itemCache)).apply();
    }

    public synchronized List<ItemModel> getAllItems() {
        if (itemCache == null) {
            itemCache = readList(K_ITEM, new TypeToken<ArrayList<ItemModel>>() {}.getType());
        }
        return itemCache;
    }

    public synchronized ItemModel getItem(int idItem) {
        for (ItemModel it : getAllItems()) {
            if (it != null && it.getId_item() == idItem) {
                return it;
            }
        }
        return null;
    }

    // ---------------------------------------------------------------- Cart (PesananMerchant, keyed by idItem)

    public synchronized List<PesananMerchant> getAllCart() {
        if (cartCache == null) {
            cartCache = readList(K_CART, new TypeToken<ArrayList<PesananMerchant>>() {}.getType());
        }
        return cartCache;
    }

    public synchronized PesananMerchant getCartByItem(int idItem) {
        for (PesananMerchant p : getAllCart()) {
            if (p != null && p.getIdItem() == idItem) {
                return p;
            }
        }
        return null;
    }

    /** Insert or replace the cart row with the same {@code idItem} (primary key). */
    public synchronized void upsertCart(PesananMerchant item) {
        if (item == null) {
            return;
        }
        List<PesananMerchant> list = getAllCart();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null && list.get(i).getIdItem() == item.getIdItem()) {
                list.set(i, item);
                persistCart();
                return;
            }
        }
        list.add(item);
        persistCart();
    }

    public synchronized void deleteCartByItem(int idItem) {
        List<PesananMerchant> list = getAllCart();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null && list.get(i).getIdItem() == idItem) {
                list.remove(i);
                break;
            }
        }
        persistCart();
    }

    public synchronized void clearCart() {
        cartCache = new ArrayList<>();
        prefs.edit().remove(K_CART).apply();
    }

    private void persistCart() {
        prefs.edit().putString(K_CART, gson.toJson(getAllCart())).apply();
    }

    // ---------------------------------------------------------------- helpers

    private <T> List<T> readList(String key, Type type) {
        String s = prefs.getString(key, null);
        if (s == null) {
            return new ArrayList<>();
        }
        List<T> r = gson.fromJson(s, type);
        return r == null ? new ArrayList<>() : r;
    }
}
