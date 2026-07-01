package com.rcdriver.dr.activity.transfer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.Objects;

import com.rcdriver.dr.R;
import com.rcdriver.dr.constants.BaseApp;
import com.rcdriver.dr.constants.Constants;
import com.rcdriver.dr.json.GetHomeRequestJson;
import com.rcdriver.dr.json.transfer.GetReceiverRequestJson;
import com.rcdriver.dr.json.transfer.GetReceiverResponJson;
import com.rcdriver.dr.json.transfer.GetSaldoResponJson;
import com.rcdriver.dr.json.transfer.SaldoReceiver;
import com.rcdriver.dr.json.transfer.TransferRequestJson;
import com.rcdriver.dr.models.User;
import com.rcdriver.dr.utils.SettingPreference;
import com.rcdriver.dr.utils.Utility;
import com.rcdriver.dr.utils.api.ServiceGenerator;
import com.rcdriver.dr.utils.api.service.DriverService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransferSaldoActivity extends AppCompatActivity {
    private Context context;
    private User user;
    private Button btnsubmit;
    private EditText phonenumber, nominal, note;
    private ImageView back_btn;
    private TextView textError, textSaldo;
    String saldo;
    private RelativeLayout lprogress;
    ImageView ivtransfer;
    SettingPreference sp;
    private String metode;
    private final int PICK_CONTACT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_saldo);
        context = this;
        user = BaseApp.getInstance(context).getLoginUser();
        sp = new SettingPreference(context);
        btnsubmit = findViewById(R.id.buttonlogin2);
        phonenumber = findViewById(R.id.phonenumber);
        nominal = findViewById(R.id.editTextAmount);
        note = findViewById(R.id.editTextNote);
        back_btn = findViewById(R.id.back_btn_verify2);
        textError = findViewById(R.id.textViewError);
        textSaldo = findViewById(R.id.textViewSaldo);
        lprogress = findViewById(R.id.rlprogress);

        ivtransfer = findViewById(R.id.img_bg);

        metode = getIntent().getStringExtra(Constants.METHOD_NAME);

        Glide.with(this)
                .load(R.drawable.transfer)
                .into(ivtransfer);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSaldo();

        phonenumber.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX() >= (phonenumber.getRight() - phonenumber
                            .getCompoundDrawables()[DRAWABLE_RIGHT]
                            .getBounds()
                            .width())){
                        Intent i = new Intent(Intent.ACTION_PICK,
                                ContactsContract.Contacts.CONTENT_URI);
                        i.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                        startActivityForResult(i, PICK_CONTACT);
                        return true;
                    }
                }
                return false;
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonenumber.setError(null);
                nominal.setError(null);
                boolean cancel = false;
                View focus = null;
                if(phonenumber.getText().toString().isEmpty()) {
                    phonenumber.setError("No Handphone penerima harus diisi");
                    cancel = true;
                    focus = phonenumber;
                }else if(user.getPhone() == phonenumber.getText().toString()) {
                    phonenumber.setError("Anda tidak dapat melakukan transfer saldo ke akun anda sendiri.");
                    cancel = true;
                    focus = phonenumber;
                }else if(nominal.getText().toString().isEmpty()){
                    nominal.setError("Nominal harus diisi");
                    cancel = true;
                    focus = nominal;

                }else if(Integer.parseInt(nominal.getText().toString()) > Integer.parseInt(saldo)) {
                    nominal.setError("Saldo anda tidak mencukupi untuk melakukan transfer");
                    cancel = true;
                    focus = nominal;
                }else if(Integer.parseInt(nominal.getText().toString()) < Integer.parseInt(sp.getSetting()[16])) {
                    nominal.setError("Transfer minimal " + Utility.toformatRupiah(sp.getSetting()[16]));
                    cancel = true;
                    focus = nominal;
                }else if(Integer.parseInt(saldo) - Integer.parseInt(nominal.getText().toString()) < Integer.parseInt(sp.getSetting()[17])) {
                    nominal.setError("Sisa saldo minimal " + Utility.toformatRupiah(sp.getSetting()[17]));
                    cancel = true;
                    focus = nominal;

                }


                if(cancel){
                    focus.requestFocus();
                }else {
                    checkValidReceiver();
                }
            }
        });
    }

    private void checkValidReceiver(){
        lprogress.setVisibility(View.VISIBLE);
        DriverService service = ServiceGenerator.createService(DriverService.class,
                user.getNoTelepon(), user.getPassword());
        GetReceiverRequestJson param = new GetReceiverRequestJson();
        param.setIdUser(user.getId());
        param.setTipe(metode);
        param.setNoReceiver("62" +phonenumber.getText().toString());
        service.validation(param).enqueue(new Callback<GetReceiverResponJson>() {
            @Override
            public void onResponse(Call<GetReceiverResponJson> call, Response<GetReceiverResponJson> response) {
                lprogress.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    if(response.body().getMessage().equalsIgnoreCase("success")){
                        SaldoReceiver resp = response.body().getData();
                        openDialogConfirm(resp);
                    }else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GetReceiverResponJson> call, Throwable t) {
                lprogress.setVisibility(View.GONE);
            }
        });
    }

    private void openDialogConfirm(SaldoReceiver resp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alert = builder.create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_konfirm_transfer, null);
        builder.setView(dialogView);
        builder.setCancelable(true);

        TextView textNominal = (TextView)dialogView.findViewById(R.id.text_nominal);
        TextView textID = (TextView)dialogView.findViewById(R.id.text_ids);
        TextView textNama = (TextView)dialogView.findViewById(R.id.text_nama);
        Button btnSubmit = (Button)dialogView.findViewById(R.id.button2);
        Button btnCancel = (Button)dialogView.findViewById(R.id.button3);

        textID.setText(resp.getId());
        textNama.setText(resp.getNama());
        textNominal.setText(nominal.getText().toString());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransferRequestJson param = new TransferRequestJson();
                param.setId_receiver(textID.getText().toString());
                param.setId_user(user.getId());
                param.setNominal(nominal.getText().toString().replaceAll(",","")
                        .replaceAll("\\.",""));
                param.setNote(note.getText().toString());

                Intent i = new Intent(context, PinActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra(Constants.DATA, new Gson().toJson(param));
                i.putExtra(Constants.METHOD_NAME, "transfer");
                startActivity(i);
                alert.dismiss();
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        alert.setView(dialogView);
        alert.show();

    }

    private void getSaldo(){
        lprogress.setVisibility(View.VISIBLE);
        DriverService userService = ServiceGenerator.createService(
                DriverService.class, user.getNoTelepon(), user.getPassword());
        GetHomeRequestJson param = new GetHomeRequestJson();
        param.setId(user.getId());
        param.setPhone(user.getNoTelepon());
        userService.checksaldo(param).enqueue(new Callback<GetSaldoResponJson>() {
            @Override
            public void onResponse(Call<GetSaldoResponJson> call, Response<GetSaldoResponJson> response) {
                lprogress.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    if(Objects.requireNonNull(response.body().getMessage().equalsIgnoreCase("success"))){
                        textSaldo.setText("Rp" + Utility.toformatRupiah(response.body().getSaldo()));
                        saldo = response.body().getSaldo();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetSaldoResponJson> call, Throwable t) {
                lprogress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_CONTACT){
            Uri contactData = data.getData();
            String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
            Cursor cursor = getContentResolver()
                    .query(contactData, projection, null, null, null);
            cursor.moveToFirst();
            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String number = cursor.getString(column);
            char first = number.charAt(0);
            if(number.substring(0,3).equalsIgnoreCase("+62")){
                number = number.substring(3);
            }else if(number.substring(0,2).equalsIgnoreCase("62")){
                number = number.substring(2);
            }else if(Character.toString(first).equalsIgnoreCase("0")){
                number = number.substring(1);
            }
            String numberPhone = number.replaceAll("[^0-9]", "");
            phonenumber.setText(numberPhone);
        }
    }
}