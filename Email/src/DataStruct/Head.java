package DataStruct;

public class Head {   //首部协议
	private String number;   //编号
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
	
	public Head(String number, String destID, String sourceID, String existLogin, String existSessionKey,
			String existID, String existRequstID, String existTS, String existLifeTime, String existTicket,
			String existAuthenticator) {
		super();
		this.number = number;
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
	}

	

	public Head() {
		super();
	}



	@Override
	public String toString() {
		return number+ destID  + sourceID + existLogin
			+ existSessionKey + existID +  existRequstID
				 + existTS + existLifeTime +existTicket
				+existAuthenticator ;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destID == null) ? 0 : destID.hashCode());
		result = prime * result + ((existAuthenticator == null) ? 0 : existAuthenticator.hashCode());
		result = prime * result + ((existID == null) ? 0 : existID.hashCode());
		result = prime * result + ((existLifeTime == null) ? 0 : existLifeTime.hashCode());
		result = prime * result + ((existLogin == null) ? 0 : existLogin.hashCode());
		result = prime * result + ((existRequstID == null) ? 0 : existRequstID.hashCode());
		result = prime * result + ((existSessionKey == null) ? 0 : existSessionKey.hashCode());
		result = prime * result + ((existTS == null) ? 0 : existTS.hashCode());
		result = prime * result + ((existTicket == null) ? 0 : existTicket.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((sourceID == null) ? 0 : sourceID.hashCode());
		return result;
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
	
}


