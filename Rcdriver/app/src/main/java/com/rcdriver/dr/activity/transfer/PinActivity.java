package com.rcdriver.dr.activity.transfer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Objects;

import com.rcdriver.dr.R;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.constants.Constants;
import com.rcdriver.dr.json.transfer.TransferRequestJson;
import com.rcdriver.dr.json.transfer.TransferResponJson;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinActivity extends AppCompatActivity {
    private Context context;
    private EditText edpassword;
    private Button btnSubmit;
    private RelativeLayout rlprogress;
    private String model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin2);
        context = this;
        edpassword = findViewById(R.id.password);
        btnSubmit = findViewById(R.id.btn_submit);
        rlprogress = findViewById(R.id.rlprogress);
        model = getIntent().getStringExtra(Constants.METHOD_NAME);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edpassword.setError(null);
                boolean cancel = false;
                View focus = null;
                if(edpassword.getText().toString().isEmpty()){
                    edpassword.setError("Password harus diisi");
                    focus = edpassword;
                    cancel = true;
                }
                if(cancel){
                    focus.requestFocus();
                }else{
                    if(model.equalsIgnoreCase("transfer")){
                        addTransfer();
                    }
                }
            }
        });
    }

    private void addTransfer() {
        rlprogress.setVisibility(View.VISIBLE);
        String intentData = getIntent().getStringExtra(Constants.DATA);
        TransferRequestJson request = new Gson().fromJson(intentData, TransferRequestJson.class);
        request.setPassword(edpassword.getText().toString());
        User loginUser = BaseApp.getInstance(this).getLoginUser();
        DriverService service = ServiceGenerator.createService(
                DriverService.class, loginUser.getNoTelepon(), loginUser.getPassword());

        service.transfer(request).enqueue(new Callback<TransferResponJson>() {
            @Override
            public void onResponse(Call<TransferResponJson> call, Response<TransferResponJson> response) {
                rlprogress.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    if(Objects.requireNonNull(response.body().getMessage().equalsIgnoreCase("success"))){

                        Intent i = new Intent(PinActivity.this, ResultTransferActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.putExtra(Constants.DATA, new Gson().toJson(response.body().getData()));
                        startActivity(i);
                        finish();
                    }else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<TransferResponJson> call, Throwable t) {
                rlprogress.setVisibility(View.GONE);
                Toast.makeText(context, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}