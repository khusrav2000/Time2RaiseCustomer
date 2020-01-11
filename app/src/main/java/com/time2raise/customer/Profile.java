package com.time2raise.customer;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.time2raise.customer.CircleTransform;
import com.squareup.picasso.Picasso;
import com.time2raise.customer.data.NetworkClient;
import com.time2raise.customer.data.RetUtils;
import com.time2raise.customer.data.apis.Customer;
import com.time2raise.customer.data.model.CustomerInformation;
import com.time2raise.customer.data.model.Message;

import java.io.IOException;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Profile extends AppCompatActivity {

    public static final String APP_PREFERENCES = "SkipLoginPhone";
    public static final String APP_PREFERENCES_PHONE= "getPhone";
    public static final String APP_TOKEN = "getToken";
    public static final String APP_CUSTOMER_ID = "id";
    SharedPreferences skipLoginPhone;

    Button saveProfileInformation;
    EditText clientPhone;
    Button logout;
    EditText clientFirstName;
    EditText clientLastName;
    EditText clientEmail;
    TextView changeProfileIcon;
    ProgressBar progressAddPhoto;
    final int PICK_IMAGE_ADD_ICON  = 3;
    Uri add_icon_uri = null;
    String token;

    ImageView iconProfile;

    final String STORAGE_URL = "https://drive.google.com/uc?export=download&id=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        saveProfileInformation      = findViewById(R.id.save_profile_information);
        clientPhone                 = findViewById(R.id.edit_client_phone);
        clientFirstName             = findViewById(R.id.edit_client_first_name);
        clientLastName              = findViewById(R.id.edit_client_last_name);
        clientEmail                 = findViewById(R.id.edit_client_email);
        iconProfile                 = findViewById(R.id.profile_icon);
        changeProfileIcon           = findViewById(R.id.change_profile_icon);
        progressAddPhoto            = findViewById(R.id.progress_add_photo);

        logout = findViewById(R.id.logout);


        skipLoginPhone  = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String phone    = skipLoginPhone.getString(APP_PREFERENCES_PHONE, "");
        String token    = skipLoginPhone.getString(APP_TOKEN, "");
        int id          = skipLoginPhone.getInt(APP_CUSTOMER_ID, -1);

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        Customer customer = retrofit.create(Customer.class);

        Call call = customer.getCustomerProfileById(token);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    fillFields((CustomerInformation) response.body());
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

        clientPhone.setText(phone);


        saveProfileInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainPageActivity();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLogout();
            }
        });

        changeProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final int REQUEST_PHONE_CALL = 1;
                if (ActivityCompat.checkSelfPermission(Profile.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Profile.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                }

                if (ActivityCompat.checkSelfPermission(Profile.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    System.out.println("Permission denied!");
                    return;
                }
                System.out.println("---CLICK CHANGE ICON!");
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                String[] mimeTypes = {"image/jpeg", "image/png"};
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                startActivityForResult(intent, PICK_IMAGE_ADD_ICON);
            }
        });
    }

    public Uri getUri(Intent data){

        Uri uri = null;
        if (data.getData()!= null)
            uri = data.getData();
        else
        {
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();
                ClipData.Item item = mClipData.getItemAt(0);
                uri = item.getUri();
            }
        }
        return uri;
    }

    // Этот метод сработаеть после выбора фотографий пользователем.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == PICK_IMAGE_ADD_ICON && resultCode == RESULT_OK && null != data)
            {
                add_icon_uri = getUri(data);

                if(add_icon_uri != null)
                    Picasso.get().load(add_icon_uri)
                            .fit()
                            .transform(new CircleTransform())
                            .centerCrop()
                            .into(iconProfile);
            }

        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void fillFields(CustomerInformation customerInformation) {
        clientFirstName.setText(customerInformation.getFirstName());
        clientLastName.setText(customerInformation.getLastName());
        clientEmail.setText(customerInformation.getEmail());

        Picasso picasso = Picasso.get();
        picasso.load(STORAGE_URL + customerInformation.getIconUrl())
                .fit()
                .centerCrop()
                .placeholder(R.drawable.photo)
                .error(R.drawable.photo)
                .transform(new CircleTransform())
                .into(iconProfile);
    }

    private void startLogout() {
        skipLoginPhone = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String token = skipLoginPhone.getString(APP_TOKEN, "");

        Retrofit retrofit = NetworkClient.getRetrofitClient();
        Customer customer = retrofit.create(Customer.class);

        Call call = customer.logout(token);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()){
                    SharedPreferences.Editor editor = skipLoginPhone.edit();
                    editor.clear();
                    editor.apply();
                    startVerificationCode();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    private void startVerificationCode() {
        Intent intent = new Intent(this, VerifyPhoneNumber.class);
        startActivity(intent);
    }

    private void startMainPageActivity() {
        saveProfileInformation.setVisibility(View.GONE);
        progressAddPhoto.setVisibility(View.VISIBLE);
        if (validInformation()){
            CustomerInformation customerInformation = new CustomerInformation(
                    0, clientFirstName.getText().toString(),
                    clientLastName.getText().toString(),
                    clientEmail.getText().toString(),
                    clientPhone.getText().toString(),
                    "",""
            );

            skipLoginPhone = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
            token = skipLoginPhone.getString(APP_TOKEN, "");

            Retrofit retrofit = NetworkClient.getRetrofitClient();
            Customer customer = retrofit.create(Customer.class);

            Call call = customer.addClientProfile(token, customerInformation);

            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.isSuccessful()){
                        CustomerInformation cus = (CustomerInformation) response.body();

                        SharedPreferences.Editor editor = skipLoginPhone.edit();
                        editor.putInt(APP_CUSTOMER_ID, cus.getCustomerId());
                        editor.apply();
                        if (add_icon_uri != null) {
                            addImages();
                            System.out.println(add_icon_uri);
                        } else {
                            openMainPageActivity();
                        }
                    } else {
                        progressAddPhoto.setVisibility(View.GONE);
                        saveProfileInformation.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    progressAddPhoto.setVisibility(View.GONE);
                    saveProfileInformation.setVisibility(View.VISIBLE);
                }
            });


        }
    }

    private void openMainPageActivity() {
        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
    }
    private boolean validInformation() {
        if (clientFirstName.getText().toString().equals("")){
            clientFirstName.setError(getString(R.string.error_first_name));
            return false;
        }
        if (clientLastName.getText().toString().equals("")){
            clientLastName.setError(getString(R.string.error_first_name));
            return false;
        }
        return true;
    }

    // Запуск загрузки фотографий.
    public void addImages(){
        Image image = new Image();
        image.execute();
    }

    // Загрузка фотографий на гугл драйв через сервер.
    private class Image extends AsyncTask<String, Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... param) {

            Retrofit retrofit = NetworkClient.getRetrofitClient();
            Customer customer = retrofit.create(Customer.class);
            System.out.println(" SEND IMAGES -------------------------------------------");
            if (add_icon_uri != null) {
                System.out.println("GO TO -----------------------------");
                MultipartBody.Part part = RetUtils.prepareFilePart(getCtx(), "image", add_icon_uri);
                Call call = customer.addProfileIcon(token, part);

                try {
                    Response response = call.execute();
                    System.out.println("+++++" + response);
                    if (response.isSuccessful()) {
                        Message m = (Message) response.body();
                        System.out.println(" --------------------------------------- " + m.toString());
                        //sendInitiatorInformation.setIconUrl(m.getMessages());
                    }
                } catch (IOException e) {
                    System.out.println("CAAAATCK" );
                    e.printStackTrace();
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            System.out.println("ERROR!!!!!!!!!!!!!!!!!!!!!!!!!!!!11" + aBoolean);
            if (aBoolean){
                openMainPageActivity();
            } else {
                progressAddPhoto.setVisibility(View.GONE);
                saveProfileInformation.setVisibility(View.VISIBLE);
            }
        }
    }

    public Context getCtx()
    {
        return getApplicationContext();
    }
}
