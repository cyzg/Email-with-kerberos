package DataStruct;

public class Head {   //�ײ�Э��
	private String destID;   //Ŀ��ID
	private String sourceID;   //ԴID
	private String existLogin; //�Ƿ���ע�ᣨ�����ע�ᣬ��seeeionkey��keyc��ID���˺ţ����������룩
	private String existSessionKey;  //�Ƿ����sessionKey
	private String existID;    //�Ƿ��������ID
	private String existRequstID;   //�Ƿ���ڱ�����ID
	private String existTS;        //�Ƿ����ʱ���
	private String existLifeTime;  //  �Ƿ������������
	private String existTicket;    //�Ƿ����Ʊ��
	private String existAuthenticator;   //�Ƿ������֤
	
	private String number;   //���
	private String securityCode;   //��֤��
	private String expend;   //��չλ
	
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


