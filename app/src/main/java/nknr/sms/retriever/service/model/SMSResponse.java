package nknr.sms.retriever.service.model;


import com.google.gson.annotations.SerializedName;


public class SMSResponse{

	@SerializedName("code")
	private String code;

	@SerializedName("total-messages-sent")
	private int totalMessagesSent;

	@SerializedName("req-type")
	private String reqType;

	@SerializedName("remaining-sms")
	private int remainingSms;

	@SerializedName("message")
	private String message;

	@SerializedName("usetype")
	private String usetype;

	@SerializedName("balacne")
	private String balacne;

	@SerializedName("status")
	private String status;

	public SMSResponse(String code, int totalMessagesSent, String reqType, int remainingSms, String message, String usetype, String balacne, String status) {
		this.code = code;
		this.totalMessagesSent = totalMessagesSent;
		this.reqType = reqType;
		this.remainingSms = remainingSms;
		this.message = message;
		this.usetype = usetype;
		this.balacne = balacne;
		this.status = status;
	}

	public String getCode(){
		return code;
	}

	public int getTotalMessagesSent(){
		return totalMessagesSent;
	}

	public String getReqType(){
		return reqType;
	}

	public int getRemainingSms(){
		return remainingSms;
	}

	public String getMessage(){
		return message;
	}

	public String getUsetype(){
		return usetype;
	}

	public String getBalacne(){
		return balacne;
	}

	public String getStatus(){
		return status;
	}
}