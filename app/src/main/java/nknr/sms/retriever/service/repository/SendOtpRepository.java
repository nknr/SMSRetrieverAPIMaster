package nknr.sms.retriever.service.repository;

import androidx.annotation.NonNull;

import nknr.sms.retriever.service.ApiClient;
import nknr.sms.retriever.service.model.SMSResponse;
import nknr.sms.retriever.utility.Constant;
import nknr.sms.retriever.view.callback.ApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendOtpRepository {

    public static void generateOtp(String mobileNumber, String message, ApiResponse listener) {
        Call<SMSResponse> call = ApiClient.createClient().sendSms(Constant.API_KEY,
                Constant.SECRET_KEY, Constant.UserType.DEVELOPMENT, mobileNumber, message,
                Constant.SENDER_ID);

        call.enqueue(new Callback<SMSResponse>() {
            @Override
            public void onResponse(@NonNull Call<SMSResponse> call, @NonNull Response<SMSResponse> response) {
                if (response.isSuccessful()){
                    listener.onResponse(true,"");
                }else{
                    listener.onResponse(false,"Unsuccess");
                }
            }

            @Override
            public void onFailure(@NonNull Call<SMSResponse> call, @NonNull Throwable t) {
                listener.onResponse(false,"Error");
            }
        });
    }
}
