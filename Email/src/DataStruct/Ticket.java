package DataStruct;

public class Ticket{
	private String sessionKey;
	private String ID;       //发送请求的ID
	private String IP;       //发送请求的IP
	private String requestID; //被请求方的ID
	private String timeStamp; //时间戳
	private String lifeTime;//ticket有效期
	
	public Ticket(String sessionKey, String iD, String iP, String requestID, String timeStamp, String lifeTime) {
		super();
		this.sessionKey = sessionKey;
		ID = iD;
		IP = iP;
		this.requestID = requestID;
		this.timeStamp = timeStamp;
		this.lifeTime = lifeTime;
	}
	
//	public Ticket() {
//		this.sessionKey = sessionKey;
//		ID = iD;
//		IP = iP;
//		this.requestID = requestID;
//		this.timeStamp = timeStamp;
////		this.lifeTime = lifeTime;
////	}

	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getIP() {
		return IP;
	}
	public void setIP(String iP) {
		IP = iP;
	}
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getLifeTime() {
		return lifeTime;
	}
	public void setLifeTime(String lifeTime) {
		this.lifeTime = lifeTime;
	}
	
	
	
}

