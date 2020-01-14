package nknr.sms.retriever.service.model;

import com.google.gson.annotations.SerializedName;

public class SMSResponse{

	@SerializedName("code")
	private String code;

	@SerializedName("req-type")
	private String reqType;

	@SerializedName("smscost")
	private double smscost;

	@SerializedName("message")
	private String message;

	@SerializedName("balacne")
	private int balacne;

	@SerializedName("status")
	private String status;

	public String getCode(){
		return code;
	}

	public String getReqType(){
		return reqType;
	}

	public double getSmscost(){
		return smscost;
	}

	public String getMessage(){
		return message;
	}

	public int getBalacne(){
		return balacne;
	}

	public String getStatus(){
		return status;
	}
}