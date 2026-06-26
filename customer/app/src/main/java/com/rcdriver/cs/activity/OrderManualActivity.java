package com.rcdriver.cs.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.models.PesananMerchant;
import com.rcdriver.cs.utils.Log;
import com.rcdriver.cs.utils.Utility;

import java.util.List;
import java.util.Objects;

import io.realm.Realm;

public class OrderManualActivity extends AppCompatActivity {
    private Context context;
    private EditText edtPesanan, edtHarga;
    private Button btnAdd;
    private TextView qty, minus, plus, qtytext, costtext;
    private LinearLayout lqty, ledit, lreset;
    private ImageView imageView;
    int itemQty = 0, maxIdItem = 0, idItem = 0;
    boolean isEdit = false;
    private Realm realm;
    CardView pricecountainer;
    String id, resume, alamat, tutupki, idresto, alamatresto, namamerchant;
    double merlat, merlon, distance, lat, lon;
    int idfitur;
    public long cost;
    public long price =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_manual);
        context = this;
        realm = BaseApp.getInstance(context).getRealmInstance();
        edtPesanan = findViewById(R.id.edt_nama);
        edtHarga = findViewById(R.id.edt_harga);
        btnAdd = findViewById(R.id.button6);
        qty = findViewById(R.id.quantity_text);
        minus = findViewById(R.id.remove_quantity);
        plus = findViewById(R.id.add_quantity);
        lqty = findViewById(R.id.lquantity);
        ledit = findViewById(R.id.ledit);
        imageView = findViewById(R.id.imageView10);
        pricecountainer = findViewById(R.id.price_container);
        qtytext = findViewById(R.id.qty_text);
        costtext = findViewById(R.id.cost_text);
        lreset = findViewById(R.id.lreset);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        idfitur = intent.getIntExtra("idfitur", 0);
        lat = intent.getDoubleExtra("lat", 0);
        lon = intent.getDoubleExtra("lon", 0);
        merlat = intent.getDoubleExtra("merlat", 0);
        merlon = intent.getDoubleExtra("merlon", 0);
        namamerchant = intent.getStringExtra("namamerchant");
        idresto = intent.getStringExtra("idresto");
        alamat = intent.getStringExtra("alamat");
        alamatresto = intent.getStringExtra("alamatresto");
        tutupki = intent.getStringExtra("tutupki");

        Log.e("ALAMAT", alamat);

        btnAdd.setEnabled(false);

        edtPesanan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    // Disable the button if the EditText is empty
                    btnAdd.setEnabled(false);
                } else {
                    // Enable the button if the EditText has text
                    btnAdd.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemQty++;
                idItem = maxIdItem+1;
                if(!edtHarga.getText().toString().isEmpty()){
                    price = Long.parseLong(edtHarga.getText().toString());
                }
                setView(true);
                qty.setText("" + itemQty);
                cost = itemQty * price;
                AddPesanan(idItem, cost, itemQty, edtPesanan.getText().toString());
                calculatePrice();

            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemQty++;
                setView(true);
                qty.setText("" + itemQty);
                if(!edtHarga.getText().toString().isEmpty()){
                    price = Long.parseLong(edtHarga.getText().toString());
                }
                cost = itemQty * price;
                UpdatePesanan(idItem, cost, itemQty, edtPesanan.getText().toString());
                calculatePrice();
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemQty - 1 >= 0) {
                    itemQty--;
                    qty.setText("" + itemQty);
                    if(!edtHarga.getText().toString().isEmpty()){
                        price = Long.parseLong(edtHarga.getText().toString());
                    }
                    cost = itemQty * price;
                    UpdatePesanan(idItem, cost, itemQty, edtPesanan.getText().toString());
                    calculatePrice();
                    if (itemQty == 0) {
                        DeletePesanan(idItem);
                        setView(false);
                    }
                }
            }
        });

        ledit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEdit){
                    isEdit = false;
                    imageView.setImageDrawable(context.getDrawable(R.drawable.icon_edit));
                    edtHarga.setEnabled(false);
                }else {
                    isEdit = true;
                    imageView.setImageDrawable(context.getDrawable(R.drawable.icon_save));
                    edtHarga.setEnabled(true);
                    if(!edtHarga.getText().toString().isEmpty()){
                        price = Long.parseLong(edtHarga.getText().toString());
                    }
                    cost = itemQty * price;
                    UpdatePesanan(idItem, cost, itemQty, edtPesanan.getText().toString());
                    calculatePrice();
                }
            }
        });

        lreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setView(false);
                calculatePrice();
                idItem = maxIdItem+1;
                edtPesanan.setText("");
                edtHarga.setText("");
                edtPesanan.requestFocus();
                itemQty = 0;
            }
        });

        calculatePrice();
    }
    
    private void setView(boolean isVisible){
        if(isVisible){
            lqty.setVisibility(View.VISIBLE);
            btnAdd.setVisibility(View.GONE);
            edtHarga.setEnabled(false);
        }else {
            lqty.setVisibility(View.GONE);
            btnAdd.setVisibility(View.VISIBLE);
            edtHarga.setEnabled(true);
        }
    }

    private void AddPesanan(int idMakanan, long totalHarga, int qty, String notes) {
        PesananMerchant pesananfood = new PesananMerchant();
        pesananfood.setIdItem(idMakanan);
        pesananfood.setTipe(1);
        pesananfood.setTotalHarga(totalHarga);
        pesananfood.setQty(qty);
        pesananfood.setNamaPesanan(notes);
        realm.beginTransaction();
        realm.copyToRealm(pesananfood);
        realm.commitTransaction();

    }

    private void UpdatePesanan(int idMakanan, long totalHarga, int qty, String notes) {
        realm.beginTransaction();
        PesananMerchant updateFood = realm.where(PesananMerchant.class).equalTo("idItem", idMakanan).findFirst();
        Objects.requireNonNull(updateFood).setTotalHarga(totalHarga);
        updateFood.setQty(qty);
        updateFood.setNamaPesanan(notes);
        realm.copyToRealm(updateFood);
        realm.commitTransaction();

    }

    private void DeletePesanan(int idMakanan) {
        realm.beginTransaction();
        PesananMerchant deleteFood = realm.where(PesananMerchant.class).equalTo("idItem", idMakanan).findFirst();
        Objects.requireNonNull(deleteFood).deleteFromRealm();
        realm.commitTransaction();
    }

    public void calculatePrice() {
        List<PesananMerchant> existingFood = realm.where(PesananMerchant.class).findAll();

        int quantity = 0;
        long cost = 0;
        for (int p = 0; p < existingFood.size(); p++) {
            quantity += Objects.requireNonNull(existingFood.get(p)).getQty();
            cost += Objects.requireNonNull(existingFood.get(p)).getTotalHarga();
        }
        maxIdItem = existingFood.size();
        Log.e("IDITEM", idItem + "----" + cost);

        if (quantity > 0) {
            pricecountainer.setVisibility(View.VISIBLE);
            pricecountainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tutupki.equals("0")) {
                        Intent i = new Intent(OrderManualActivity.this, DetailOrderActivity.class);
                        i.putExtra("lat", lat);
                        i.putExtra("lon", lon);
                        i.putExtra("merlat", merlat);
                        i.putExtra("merlon", merlon);
                        i.putExtra("alamat", alamat);
                        i.putExtra("FiturKey", idfitur);
                        i.putExtra("distance", distance);
                        i.putExtra("alamatresto", alamatresto);
                        i.putExtra("idresto", idresto);
                        i.putExtra("namamerchant", namamerchant);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(context, "Pedagang tutup", Toast.LENGTH_LONG).show();
                    }
                }
            });

        } else {
            pricecountainer.setVisibility(View.GONE);
        }

        qtytext.setText("" + quantity + " Item");
        Utility.currencyTXT(costtext, String.valueOf(cost), this);
    }
}