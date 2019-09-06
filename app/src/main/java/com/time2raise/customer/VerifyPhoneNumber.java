package com.time2raise.customer;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.time2raise.customer.data.NetworkClient;
import com.time2raise.customer.data.apis.Customer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class VerifyPhoneNumber extends AppCompatActivity implements View.OnClickListener {

    Spinner selectCountryCode;

    TextView phoneNumber;
    Button sendVerificationCode;

    View numberPhoneLine;
    View codeCountryLine;

    Button[] numbers = new Button[12];
    Button delete;

    Button obhod;

    int codeCountryPosition = -1;

    StringBuilder resultNumberPhone = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone_number);

        sendVerificationCode = findViewById(R.id.send_verification_code);

        sendVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateContact();
            }
        });

        obhod = findViewById(R.id.OBHOD);
        obhod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEnterVerificationCode("");
            }
        });

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

        numberPhoneLine = findViewById(R.id.number_phone_line);
        codeCountryLine = findViewById(R.id.code_country_line);



        for (int i = 0; i < 10; i++) {
            numbers[i].setOnClickListener(this);
        }
        delete.setOnClickListener(this);


        phoneNumber = findViewById(R.id.phone_number);
        selectCountryCode = findViewById(R.id.select_country_code);

        phoneNumber.setOnClickListener(this);
        //selectCountryCode.setOnItemClickListener;

        String[] data = {"USA (+1)", "Spain (+34)", "Tajikistan (+992)", "Russia (+7)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        selectCountryCode.setAdapter(adapter);
        // заголовок
        selectCountryCode.setPrompt("Title");
        // выделяем элемент
        selectCountryCode.setSelection(0);
        // устанавливаем обработчик нажатия
        selectCountryCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                codeCountryPosition = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    // Проверка на исключительность номера телефона.
    public void validateContact(){
        if (codeCountryPosition == 2 || codeCountryPosition == 3) {
            final StringBuilder sendVerificationNumber;
            if (codeCountryPosition == 2) {
                sendVerificationNumber = new StringBuilder("+992" + resultNumberPhone);
            } else {
                sendVerificationNumber = new StringBuilder("+7" + resultNumberPhone);
            }

            Retrofit retrofit = NetworkClient.getRetrofitClient();
            Customer customer = retrofit.create(Customer.class);
            Call call = customer.validationPhone(sendVerificationNumber.toString());
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    if (response.code() == 404) {
                        // Если такого номера телефона нет, то сработает эта функция.
                        sendVerificationPhone(sendVerificationNumber.toString());
                    }
                    else {
                        phoneNumber.setError(getString(R.string.error_number_phone));
                        phoneNumber.requestFocus();
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    t.printStackTrace();
                }
            });
            System.out.println(sendVerificationNumber + " --------------------------------------");
        }else {
            phoneNumber.setError(getString(R.string.error_number_phone));
            phoneNumber.requestFocus();
        }
    }

    // Отправка номера телефона для получения смс кода.
    private void sendVerificationPhone(String phone){
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        Customer customer = retrofit.create(Customer.class);
        Call call = customer.getVerificationCode(phone);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });

        // Переход на ввод полученного ао смс код-а.
        startEnterVerificationCode(phone);
    }

    private void startEnterVerificationCode(String phone) {
        Intent intent = new Intent(this, VerificationCode.class);
        intent.putExtra("phone", phone);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
            case R.id.phone_number:
                setBigBottomLineNumberPhone();
                break;
        }
    }

    public void setBigBottomLineNumberPhone(){
        setSmallBottomLineCodeCountry();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(numberPhoneLine.getWidth(),
                getBaseContext().getResources().getDimensionPixelOffset(R.dimen.number_phone_line_height_after));
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = getBaseContext().getResources().getDimensionPixelOffset(R.dimen.number_phone_line_margin_top_after);
        numberPhoneLine.setLayoutParams(params);
    }

    public void setSmallBottomLineCodeCountry(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(codeCountryLine.getWidth(),
                getBaseContext().getResources().getDimensionPixelOffset(R.dimen.code_country_line_height_before));
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = getBaseContext().getResources().getDimensionPixelOffset(R.dimen.code_country_line_margin_top_before);
        codeCountryLine.setLayoutParams(params);
    }

    public void setBigBottomLineCodeCountry(){
        setSmallBottomLineNumberPhone();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(codeCountryLine.getWidth(),
                getBaseContext().getResources().getDimensionPixelOffset(R.dimen.code_country_line_height_after));
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = getBaseContext().getResources().getDimensionPixelOffset(R.dimen.code_country_line_margin_top_after);
        codeCountryLine.setLayoutParams(params);
    }

    public void setSmallBottomLineNumberPhone(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(numberPhoneLine.getWidth(),
                getBaseContext().getResources().getDimensionPixelOffset(R.dimen.number_phone_line_height_before));
        params.gravity = Gravity.CENTER_HORIZONTAL;
        params.topMargin = getBaseContext().getResources().getDimensionPixelOffset(R.dimen.code_country_line_margin_top_before);
        numberPhoneLine.setLayoutParams(params);
    }



    private void deleteChar() {
        if(resultNumberPhone.length() > 0) {
            resultNumberPhone.deleteCharAt(resultNumberPhone.length() - 1);
            phoneNumber.setText(resultNumberPhone);
            if (resultNumberPhone.length() == 10)
                numberPhoneLine.setBackgroundColor(getColor(R.color.green));
            else {
                numberPhoneLine.setBackgroundColor(getColor(R.color.red));
            }
        }
    }


    public void setNumber(char digit){
        if (resultNumberPhone.length() < 15) {
            resultNumberPhone.append(digit);
            phoneNumber.setText(resultNumberPhone.toString());
            if (resultNumberPhone.length() == 10) {
                numberPhoneLine.setBackgroundColor(getColor(R.color.green));
            }
            else {
                numberPhoneLine.setBackgroundColor(getColor(R.color.red));
            }
        }
    }
}
