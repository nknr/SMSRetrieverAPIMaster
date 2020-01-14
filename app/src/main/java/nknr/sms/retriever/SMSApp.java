package nknr.sms.retriever;

import android.app.Application;

public class SMSApp extends Application {

    private static AppSignatureHashHelper appSignture;

    public static String getHashKey() {
        return appSignture.getAppSignatures().get(0);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appSignture = new AppSignatureHashHelper(this);
    }
}
