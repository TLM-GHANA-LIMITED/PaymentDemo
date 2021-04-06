package com.tlmghana.paymentdemo.java;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.expresspaygh.api.ExpressPayApi;
import com.tlmghana.paymentdemo.R;

import org.json.JSONException;

import java.util.HashMap;

import static com.tlmghana.paymentdemo.utils.constant.ACCOUNT_NUMBER;
import static com.tlmghana.paymentdemo.utils.constant.AMOUNT;
import static com.tlmghana.paymentdemo.utils.constant.CURRENCY;
import static com.tlmghana.paymentdemo.utils.constant.EMAIL;
import static com.tlmghana.paymentdemo.utils.constant.ENDPOINT;
import static com.tlmghana.paymentdemo.utils.constant.FIRST_NAME;
import static com.tlmghana.paymentdemo.utils.constant.LAST_NAME;
import static com.tlmghana.paymentdemo.utils.constant.ORDER_DESC;
import static com.tlmghana.paymentdemo.utils.constant.ORDER_ID;
import static com.tlmghana.paymentdemo.utils.constant.PHONE;
import static com.tlmghana.paymentdemo.utils.constant.SUBMIT;
import static com.tlmghana.paymentdemo.utils.constant.TAGS;
import static com.tlmghana.paymentdemo.utils.constant.USER_NAME;
import static com.tlmghana.paymentdemo.utils.constant.initDialog;

public class MainActivity extends AppCompatActivity implements ExpressPayApi.ExpressPayPaymentCompletionListener {

    protected ExpressPayApi expressPayApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


    }

    private void init() {

        expressPayApi = new ExpressPayApi(this, ENDPOINT);

        expressPayApi.setDebugMode(true);

        Button payBtn = findViewById(R.id.payBtn);

        payBtn.setOnClickListener(v -> order());

    }

    public void order() {

        HashMap<String, String> params = new HashMap<>();

        params.put("request", SUBMIT);
        params.put("order_id", ORDER_ID);
        params.put("currency", CURRENCY);
        params.put("amount", AMOUNT);
        params.put("order_desc", ORDER_DESC);
        params.put("user_name", USER_NAME);
        params.put("first_name", FIRST_NAME);
        params.put("last_name", LAST_NAME);
        params.put("email", EMAIL);
        params.put("phone_number", PHONE);
        params.put("account_number", ACCOUNT_NUMBER);


        expressPayApi.submit(params, MainActivity.this, (jsonObject, message) -> {
            if (jsonObject != null) {

                //You can access the returned token
                try {
                    String status = jsonObject.getString("status");

                    if (status.equalsIgnoreCase("1")) {
                        String token = expressPayApi.getToken();
                        Log.d(TAGS, token);

                        expressPayApi.checkout(MainActivity.this);

                    } else {
                        Log.d(TAGS, message);
                        initDialog(message, MainActivity.this);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d(TAGS, message);
                    initDialog(message, MainActivity.this);

                }

            } else {
                Log.d(TAGS, message);
                initDialog(message, MainActivity.this);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null)
            expressPayApi.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onExpressPayPaymentFinished(boolean paymentCompleted, String message) {
        if (paymentCompleted) {
            //Payment was completed
            String token = expressPayApi.getToken();

            queryPayment(token);

        } else {
            //There was an error
            Log.d(TAGS, message);
            initDialog(message, MainActivity.this);
        }
    }

    public void queryPayment(String token) {

        expressPayApi.query(token, (paymentSuccessful, jsonObject, message) -> {

            if (paymentSuccessful) {
                initDialog(message, MainActivity.this);
            } else {
                //There was an error
                Log.d(TAGS, message);
                initDialog(message, MainActivity.this);
            }
        });
    }


}
