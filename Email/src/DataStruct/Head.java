package DataStruct;

public class Head {   //首部协议
	private String destID;   //目的ID
	private String sourceID;   //源ID
	private String existLogin; //是否是注册（如果是注册，则seeeionkey是keyc，ID是账号，后面是密码）
	private String existSessionKey;  //是否存在sessionKey
	private String existID;    //是否存在请求方ID
	private String existRequstID;   //是否存在被请求方ID
	private String existTS;        //是否存在时间戳
	private String existLifeTime;  //  是否存在生存期限
	private String existTicket;    //是否存在票据
	private String existAuthenticator;   //是否存在认证
	
	private String number;   //编号
	private String securityCode;   //验证码
	private String expend;   //扩展位
	
	public Head(String destID, String sourceID, String existLogin, String existSessionKey, String existID,
			String existRequstID, String existTS, String existLifeTime, String existTicket, String existAuthenticator,
			String number, String securityCode, String expend) {
		super();
		this.destID = destID;
		this.sourceID = sourceID;
		this.existLogin = existLogin;
		this.existSessionKey = existSessionKey;
		this.existID = existID;
		this.existRequstID = existRequstID;
		this.existTS = existTS;
		this.existLifeTime = existLifeTime;
		this.existTicket = existTicket;
		this.existAuthenticator = existAuthenticator;
		this.number = number;
		this.securityCode = securityCode;
		this.expend = expend;
	}

	public Head() {
		destID = "0000";
		sourceID = "0000";
		existLogin = "0";
		existSessionKey = "0";
		existID = "0";
		existRequstID = "0";
		existTS = "0";
		existLifeTime = "0";
		existTicket = "0";
		existAuthenticator = "0";
		number = "0000";
		securityCode = "00";
		expend = "0000000000000000";
	}

	@Override
	public String toString() {
		return "Head [destID=" + destID + ", sourceID=" + sourceID + ", existLogin=" + existLogin + ", existSessionKey="
				+ existSessionKey + ", existID=" + existID + ", existRequstID=" + existRequstID + ", existTS=" + existTS
				+ ", existLifeTime=" + existLifeTime + ", existTicket=" + existTicket + ", existAuthenticator="
				+ existAuthenticator + ", number=" + number + ", securityCode=" + securityCode + ", expend=" + expend
				+ "]";
	}	

	public String headOutput() {
		return destID + sourceID + existLogin + existSessionKey + existID + existRequstID + existTS + existLifeTime
				+ existTicket + existAuthenticator + number + securityCode + expend;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getDestID() {
		return destID;
	}
	public void setDestID(String destID) {
		this.destID = destID;
	}
	public String getSourceID() {
		return sourceID;
	}
	public void setSourceID(String sourceID) {
		this.sourceID = sourceID;
	}
	public String getExistLogin() {
		return existLogin;
	}
	public void setExistLogin(String existLogin) {
		this.existLogin = existLogin;
	}
	public String getExistSessionKey() {
		return existSessionKey;
	}
	public void setExistSessionKey(String existSessionKey) {
		this.existSessionKey = existSessionKey;
	}
	public String getExistID() {
		return existID;
	}
	public void setExistID(String existID) {
		this.existID = existID;
	}
	public String getExistRequstID() {
		return existRequstID;
	}
	public void setExistRequstID(String existRequstID) {
		this.existRequstID = existRequstID;
	}
	public String getExistTS() {
		return existTS;
	}
	public void setExistTS(String existTS) {
		this.existTS = existTS;
	}
	public String getExistLifeTime() {
		return existLifeTime;
	}
	public void setExistLifeTime(String existLifeTime) {
		this.existLifeTime = existLifeTime;
	}
	public String getExistTicket() {
		return existTicket;
	}
	public void setExistTicket(String existTicket) {
		this.existTicket = existTicket;
	}
	public String getExistAuthenticator() {
		return existAuthenticator;
	}
	public void setExistAuthenticator(String existAuthenticator) {
		this.existAuthenticator = existAuthenticator;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getExpend() {
		return expend;
	}

	public void setExpend(String expend) {
		this.expend = expend;
	}
	
}


