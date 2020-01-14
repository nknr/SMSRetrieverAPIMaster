package nknr.sms.retriever.view.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

import nknr.sms.retriever.R;
import nknr.sms.retriever.SMSApp;
import nknr.sms.retriever.databinding.ActivityGenerateOtpBinding;
import nknr.sms.retriever.service.repository.SendOtpRepository;
import nknr.sms.retriever.utility.RandomNumber;
import nknr.sms.retriever.SMSReceiver;

public class MainActivity extends AppCompatActivity implements SMSReceiver.OTPReceiveListener {

    public static final String TAG = MainActivity.class.getSimpleName();
    private int RESOLVE_HINT = 2;
    private SMSReceiver smsReceiver;
    private ActivityGenerateOtpBinding binding;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_generate_otp);

        setupGoogleClient();
        getHintPhoneNumber();
        startSMSListener();
    }

    private void setupGoogleClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, connectionResult -> {
                    //
                })
                .addApi(Auth.CREDENTIALS_API)
                .build();


    }

    public void getHintPhoneNumber() {
        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)
                .build();
        PendingIntent mIntent = Auth.CredentialsApi.getHintPickerIntent(mGoogleApiClient, hintRequest);
        try {
            startIntentSenderForResult(mIntent.getIntentSender(), RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_HINT && resultCode == Activity.RESULT_OK && data != null) {
            Credential credential = Objects.requireNonNull(data).getParcelableExtra(Credential.EXTRA_KEY);
            binding.mobile.setText(credential.getId());
        }
    }


    public void onProceed(View view){
        startSMSListener();
    }

    private void startSMSListener() {
        try {
            smsReceiver = new SMSReceiver();
            smsReceiver.setOTPListener(this);

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
            this.registerReceiver(smsReceiver, intentFilter);

            SmsRetrieverClient client = SmsRetriever.getClient(this);

            Task<Void> task = client.startSmsRetriever();
            task.addOnSuccessListener(aVoid -> sendOtp());

            task.addOnFailureListener(e -> Log.d(TAG, " error :" + e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("DefaultLocale")
    private void sendOtp() {
        String mobileNumber = binding.mobile.getText().toString();
        String message = String.format("<#> Your otp code is: %d. %s ",
                 RandomNumber.getRandomNum(),
                 SMSApp.getHashKey());

        SendOtpRepository.generateOtp(mobileNumber,message);
    }


    @Override
    public void onOTPReceived(String otp) {
        showToast("OTP Received: " + otp);
        Log.d(TAG, "otp " + otp);
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver);
            smsReceiver = null;
        }
    }

    @Override
    public void onOTPTimeOut() {
        showToast("OTP Time out");
        Log.d(TAG, "timeout");
    }

    @Override
    public void onOTPReceivedError(String error) {
        showToast(error);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver);
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
