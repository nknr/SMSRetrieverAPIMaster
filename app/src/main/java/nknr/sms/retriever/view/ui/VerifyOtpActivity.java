package nknr.sms.retriever.view.ui;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.auth.api.phone.SmsRetriever;

import nknr.sms.retriever.R;
import nknr.sms.retriever.SMSReceiver;
import nknr.sms.retriever.databinding.ActivityVerifyOtpBinding;


public class VerifyOtpActivity extends AppCompatActivity implements SMSReceiver.OTPReceiveListener {

    public static final String TAG = VerifyOtpActivity.class.getSimpleName();
    private SMSReceiver smsReceiver;
    private ActivityVerifyOtpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_otp);

        startSMSListener();
    }

    private void startSMSListener() {
        try {
            smsReceiver = new SMSReceiver();
            smsReceiver.setOTPListener(this);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
            this.registerReceiver(smsReceiver, intentFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOTPReceived(String message) {
        Log.d(TAG, "message " + message);
        binding.mobile.setText(extractOtpFromSMS(message));

        if (smsReceiver != null) {
            unregisterReceiver(smsReceiver);
            smsReceiver = null;
        }
    }


    private String extractOtpFromSMS(String message){
        message = message.replaceAll("<#> Your otp code is: ","");
        return message.substring(0,5);
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
