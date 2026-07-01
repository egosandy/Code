package com.rcdriver.dr.utils;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.rcdriver.dr.R;
import com.rcdriver.dr.json.EditHargaRequest;
import com.rcdriver.dr.json.EditHargaResponse;
import com.rcdriver.dr.models.ItemPesananModel;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogPrice {
    private Context context;
    private Dialog dialog;

    public DialogPrice(Context context) {
        this.context = context;
    }

    public void showDialog(Context context, ItemPesananModel model){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_price);
        EditText edtnama = (EditText) dialog.findViewById(R.id.amount);
        EditText edtharga = (EditText) dialog.findViewById(R.id.bank);
        EditText edtperubahan = (EditText) dialog.findViewById(R.id.namanumber);
        Button submit = (Button)dialog.findViewById(R.id.submit);
        Button cancel = (Button)dialog.findViewById(R.id.submit2);

        long price = Long.parseLong(model.getTotal_harga()) / Long.parseLong(model.getJumlah_item());
        edtnama.setText(model.getNama_pesanan());
        edtharga.setText(Utility.toformatRupiah(String.valueOf(price)));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit(model.getId(), String.valueOf(price), model.getJumlah_item());
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void edit(String id, String harga, String qty) {
        DriverService service = ServiceGenerator.createService(DriverService.class, "admin", "123456");
        EditHargaRequest param = new EditHargaRequest();
        param.setId(id);
        param.setHarga(harga);
        param.setQty(qty);
        service.editharga(param).enqueue(new Callback<EditHargaResponse>() {
            @Override
            public void onResponse(@NonNull Call<EditHargaResponse> call, @NonNull Response<EditHargaResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    dialog.dismiss();

                }
            }
            @Override
            public void onFailure(@NonNull retrofit2.Call<EditHargaResponse> call, @NonNull Throwable t) {
                Log.e("UpdateLogin", t.getMessage());
            }
        });

    }
}
