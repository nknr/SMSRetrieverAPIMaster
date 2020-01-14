package nknr.sms.retriever.service;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import nknr.sms.retriever.service.model.SMSResponse;

public interface RestApi {

    @FormUrlEncoded
    @POST("sendCampaign")
    Call<SMSResponse> sendSms(@Field("apikey") String apiKey,
                              @Field("secret") String secret,
                              @Field("usetype") String useType,
                              @Field("phone") String phone,
                              @Field("message") String message,
                              @Field("senderid") String senderid
    );

}
