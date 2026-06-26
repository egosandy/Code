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
 * <p>Backed by SharedPreferences + Gson. The persisted data set is tiny (a single
 * logged-in user, the FCM token, the feature list, the merchant item list and the
 * shopping cart), so this mirrors the exact read/write semantics the app relied on
 * while staying compatible with AGP 8 / SDK 36 (Realm Java is EOL).
 *
 * <p>Behaviour is intentionally identical to the old Realm usage:
 * primary-keyed collections (cart by {@code idItem}, fitur by {@code idFitur}) are
 * upsert/replace-by-key, and "save list" operations replace the whole collection.
 *
 * <p>The whole app talks to this single facade, so swapping the backing store for a
 * real Room database later only touches this class — no call sites change.
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
        prefs.edit().putString(K_USER, gson.toJson(user)).apply();
    }

    public synchronized User getUser() {
        String s = prefs.getString(K_USER, null);
        return s == null ? null : gson.fromJson(s, User.class);
    }

    public synchronized void deleteUser() {
        prefs.edit().remove(K_USER).apply();
    }

    // ---------------------------------------------------------------- FCM token

    public synchronized void saveToken(FirebaseToken token) {
        prefs.edit().putString(K_TOKEN, token == null ? null : gson.toJson(token)).apply();
    }

    public synchronized FirebaseToken getToken() {
        String s = prefs.getString(K_TOKEN, null);
        return s == null ? null : gson.fromJson(s, FirebaseToken.class);
    }

    // ---------------------------------------------------------------- Fitur

    public synchronized void saveFitur(List<FiturModel> list) {
        prefs.edit().putString(K_FITUR, gson.toJson(list)).apply();
    }

    public synchronized List<FiturModel> getAllFitur() {
        return readList(K_FITUR, new TypeToken<ArrayList<FiturModel>>() {}.getType());
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
        prefs.edit().putString(K_ITEM, gson.toJson(list)).apply();
    }

    public synchronized List<ItemModel> getAllItems() {
        return readList(K_ITEM, new TypeToken<ArrayList<ItemModel>>() {}.getType());
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
        return readList(K_CART, new TypeToken<ArrayList<PesananMerchant>>() {}.getType());
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
                saveCart(list);
                return;
            }
        }
        list.add(item);
        saveCart(list);
    }

    public synchronized void deleteCartByItem(int idItem) {
        List<PesananMerchant> list = getAllCart();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null && list.get(i).getIdItem() == idItem) {
                list.remove(i);
                break;
            }
        }
        saveCart(list);
    }

    public synchronized void clearCart() {
        prefs.edit().remove(K_CART).apply();
    }

    private void saveCart(List<PesananMerchant> list) {
        prefs.edit().putString(K_CART, gson.toJson(list)).apply();
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
