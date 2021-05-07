package com.example.cowin;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    String FILTER_KEYWORD = "CoWIN";

    String mPhoneNumber;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkAndRequestPermissions();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView mobileNumberView = findViewById(R.id.number);


        SmsReceiver.bindListener(new SmsListener() {
            @Override
            public void messageReceived(String messageText) {
//                Log.i(TAG, messageText);
                mPhoneNumber = mobileNumberView.getText().toString().trim();
                Log.i(TAG, mPhoneNumber);
                if (messageText.contains(FILTER_KEYWORD)) {
                    sendOTP(messageText, mPhoneNumber);
                }

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private boolean checkAndRequestPermissions() {
        int sms = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);

        if (sms != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }


    public void sendOTP(String sms, String number) {

        apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        Log.i(TAG, number);

        apiInterface.sendOTP(sms, number).enqueue(new Callback<SendOTP>() {
            @Override
            public void onResponse(Call<SendOTP> call, Response<SendOTP> response) {
                Log.i(TAG, number);
                Log.i(TAG, response.message());
            }

            @Override
            public void onFailure(Call<SendOTP> call, Throwable t) {
                Log.i(TAG, "failure " + t.getMessage());


            }
        });
    }
}


