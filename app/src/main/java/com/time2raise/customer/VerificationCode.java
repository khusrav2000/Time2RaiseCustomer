package com.time2raise.customer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.time2raise.customer.data.NetworkClient;
import com.time2raise.customer.data.apis.Customer;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.time2raise.customer.data.model.Message;

public class VerificationCode extends AppCompatActivity implements View.OnClickListener {

    // Все кнопки от нуля до девяти.
    Button[] numbers = new Button[12];

    Button delete;

    TextView firstNumber;
    TextView secondNumber;
    TextView thirdNumber;
    TextView fourthNumber;

    StringBuilder verificationCode = new StringBuilder();

    TextView wrongCode;

    public static final String APP_PREFERENCES = "SkipLoginPhone";

    public static final String APP_PREFERENCES_PHONE= "getPhone";
    public static final String APP_TOKEN = "getToken";

    SharedPreferences skipLoginPhone;

    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        skipLoginPhone = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        numbers[0] = findViewById(R.id.number_zero);
        numbers[1] = findViewById(R.id.number_one);
        numbers[2] = findViewById(R.id.number_two);
        numbers[3] = findViewById(R.id.number_three);
        numbers[4] = findViewById(R.id.number_four);
        numbers[5] = findViewById(R.id.number_five);
        numbers[6] = findViewById(R.id.number_six);
        numbers[7] = findViewById(R.id.number_seven);
        numbers[8] = findViewById(R.id.number_eight);
        numbers[9] = findViewById(R.id.number_nine);

        delete = findViewById(R.id.delete);

        firstNumber = findViewById(R.id.first_number_of_code);
        secondNumber = findViewById(R.id.second_number_of_code);
        thirdNumber = findViewById(R.id.third_number_of_code);
        fourthNumber = findViewById(R.id.fourth_number_of_code);

        wrongCode = findViewById(R.id.wrong_code);

        findViewById(R.id.obhod_enter_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMain();
            }
        });

        for (int i = 0; i < 10; i++) {
            numbers[i].setOnClickListener(this);
        }
        delete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.number_zero:
                setNumber('0');
                break;
            case R.id.number_one:
                setNumber('1');
                break;
            case R.id.number_two:
                setNumber('2');
                break;
            case R.id.number_three:
                setNumber('3');
                break;
            case R.id.number_four:
                setNumber('4');
                break;
            case R.id.number_five:
                setNumber('5');
                break;
            case R.id.number_six:
                setNumber('6');
                break;
            case R.id.number_seven:
                setNumber('7');
                break;
            case R.id.number_eight:
                setNumber('8');
                break;
            case R.id.number_nine:
                setNumber('9');
                break;
            case R.id.delete:
                deleteChar();
                break;
        }
    }
    private void setNumber(char digit){
        if (verificationCode.length() < 4) {

            verificationCode.append(digit);
            if (verificationCode.length() == 1) {
                firstNumber.setText(String.valueOf(digit));
            } else if (verificationCode.length() == 2) {
                secondNumber.setText(String.valueOf(digit));
            } else if (verificationCode.length() == 3) {
                thirdNumber.setText(String.valueOf(digit));
            } else if (verificationCode.length() == 4) {
                fourthNumber.setText(String.valueOf(digit));
                System.out.println("CHEKKKKK --------------------------------");
                // если заполнить все четыре цифры, то сработает это функция.
                verificationNumber(verificationCode.toString());
            }

        }
    }

    // Здесь проверяется вводимый пользователем код с кодом который пришел по смс.
    private void verificationNumber(String verificationCode){
        phone = getIntent().getStringExtra("phone");
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        Customer customer = retrofit.create(Customer.class);
        Call call = customer.checkVerificationCode(phone, verificationCode);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.code() == 200){
                    System.out.println("YES-----------------------------------------------");
                    Message message = (Message) response.body();
                    // Если оба код-а совпадают, то откроется страница заполнения данных о пользователе.
                    startProfileActivity(message);
                }else {
                    // Если нет, то всплывает сообщения об не совпадении код-ов.
                    wrongCode.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

    private void startProfileActivity(Message message) {

        // Перед тем, как перейти в профиль данные о входе по этому номеру сохраняется в Preference.
        SharedPreferences.Editor editor = skipLoginPhone.edit();
        editor.putString(APP_PREFERENCES_PHONE, phone);
        editor.putString(APP_TOKEN, message.getMassage());
        editor.apply();

        System.out.println(message.getMassage());

        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }


    // Здесь обрабатывается кнопка очистки цифр.
    private void deleteChar(){
        wrongCode.setVisibility(View.GONE);
        if (verificationCode.length() > 0){
            verificationCode.deleteCharAt(verificationCode.length() - 1);
            if (verificationCode.length() == 0){
                firstNumber.setText("");
            } else if (verificationCode.length() == 1){
                secondNumber.setText("");
            } else if (verificationCode.length() == 2){
                thirdNumber.setText("");
            } else if (verificationCode.length() == 3) {
                fourthNumber.setText("");
            }
        }
    }

    private void startMain(){
        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
    }
}
