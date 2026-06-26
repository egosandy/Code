package com.rcdriver.cs.activity;

import com.rcdriver.cs.utils.LocalStore;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.rcdriver.cs.BuildConfig;
import com.rcdriver.cs.R;
import com.rcdriver.cs.constants.BaseApp;
import com.rcdriver.cs.constants.Constants;
import com.rcdriver.cs.intro.WalkthroughActivity;
import com.rcdriver.cs.json.ResponseJson;
import com.rcdriver.cs.json.UpdateLoginRequest;
import com.rcdriver.cs.models.User;
import com.rcdriver.cs.utils.PicassoTrustAll;
import com.rcdriver.cs.utils.SettingPreference;
import com.rcdriver.cs.utils.api.ServiceGenerator;
import com.rcdriver.cs.utils.api.service.UserService;

import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    private Context context;
    private ImageView foto;
    private TextView nama, Saldo, lwasap;
    private SettingPreference sp;
    private User login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        context = this;
        foto = findViewById(R.id.userphoto);
        lwasap = findViewById(R.id.wasap);
        nama = findViewById(R.id.username);
        Saldo = findViewById(R.id.txtSaldo);
        TextView aboutus = findViewById(R.id.llaboutus);
        TextView privacy = findViewById(R.id.llprivacypolicy);
        RelativeLayout editprofile = findViewById(R.id.lleditprofile);
        TextView logout = findViewById(R.id.lllogout);
        TextView favorit = findViewById(R.id.favorit);
        TextView llpassword = findViewById(R.id.llpassword);
        TextView rating = findViewById(R.id.rating);
        TextView shareapp = findViewById(R.id.shareapp);
        sp = new SettingPreference(context);
        login = BaseApp.getInstance(context).getLoginUser();
        Log.e("MySaldo", String.valueOf(login.getWalletSaldo()));

        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PrivacyActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutus();
            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditProfileActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        favorit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, FavouriteActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        shareapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                String shareMessage = "Let me recommend you this application ";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            }
        });


        lwasap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ponsel = "+" + sp.getSetting()[13];
                String semuapesan = "Halo, Mohon bantuannya";
                String url="https://api.whatsapp.com/send?phone="+ponsel + "&text=" + semuapesan;
                Intent i= new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                {
                    // WhatsApp is installed on the device
                    startActivity(i);
                }

                {
                    // WhatsApp is not installed on the device

                }



            }
        });

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                            Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                            Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                }
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });

        llpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ChangepassActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDone();
            }
        });
    }

    private void clickDone() {
        new AlertDialog.Builder(context, R.style.DialogStyle)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle(getString(R.string.app_name))
                .setMessage(getString(R.string.exit))
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        SetLogin(0);
LocalStore.get().deleteUser();
                        removeNotif();
                        BaseApp.getInstance(context).setLoginUser(null);
                        startActivity(new Intent(context, WalkthroughActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                        finish();
                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void aboutus() {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_aboutus);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final ImageView close = dialog.findViewById(R.id.bt_close);
        final LinearLayout email = dialog.findViewById(R.id.email);
        final LinearLayout phone = dialog.findViewById(R.id.phone);
        final LinearLayout website = dialog.findViewById(R.id.website);
        final WebView about = dialog.findViewById(R.id.aboutus);

        String mimeType = "text/html";
        String encoding = "utf-8";
        String htmlText;
        htmlText = sp.getSetting()[1];
        String text = "<html dir=" + "><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/fonts/dmsans_regular.ttf\")}body{font-family: MyFont;color: #000000;text-align:justify;line-height:1.2}"
                + "</style></head>"
                + "<body>"
                + htmlText
                + "</body></html>";

        about.loadDataWithBaseURL(null, text, mimeType, encoding, null);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int REQUEST_PHONE_CALL = 1;
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + (sp.getSetting()[3])));
                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                    } else {
                        startActivity(callIntent);
                    }
                }
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] TO = {(sp.getSetting()[2])};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");

                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "halo");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "email" + "\n");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ProfileActivity.this,
                            "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = (sp.getSetting()[4]);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void removeNotif() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Objects.requireNonNull(notificationManager).cancel(0);
    }

    private void SetLogin(int login) {
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        UserService service = ServiceGenerator.createService(UserService.class, loginUser.getEmail(), loginUser.getPassword());
        UpdateLoginRequest param = new UpdateLoginRequest();
        param.setId(loginUser.getId());
        param.setIslogin(login);
        service.updatelogin(param).enqueue(new Callback<ResponseJson>() {
            @Override
            public void onResponse(@NonNull Call<ResponseJson> call, @NonNull Response<ResponseJson> response) {
                if (response.isSuccessful()) {
                    Log.e("UpdateLogin", response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseJson> call, @NonNull Throwable t) {
                Log.e("UpdateLogin", t.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        User loginUser = BaseApp.getInstance(context).getLoginUser();
        nama.setText(loginUser.getFullnama());
        Saldo.setText(loginUser.getEmail());
        PicassoTrustAll.getInstance(context)
                .load(Constants.IMAGESUSER + loginUser.getFotopelanggan())
                .placeholder(R.drawable.image_placeholder)
                .resize(250, 250)
                .into(foto);
    }
}
