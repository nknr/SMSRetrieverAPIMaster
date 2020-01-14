package nknr.sms.retriever.utility;


import androidx.annotation.StringDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public class Constant {
    public static final String BASE_URL = "https://www.sms4india.com/api/v1/";

    public static final String API_KEY = "LQ8LEP0JGTBTCWEV9I7NZCKGZQDTDRDF";
    public static final String SECRET_KEY = "NVCE5ANEK92XIMS6";
    public static final String SENDER_ID = "SMS4India";


    @Retention(RetentionPolicy.SOURCE)
    @StringDef({UserType.DEVELOPMENT,UserType.PRODUCTION})
    public @interface UserType {
         String DEVELOPMENT = "stage";
         String PRODUCTION = "prod";
    }
}
