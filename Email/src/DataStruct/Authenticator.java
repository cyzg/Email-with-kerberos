package DataStruct;

public class Authenticator {
	private String clientID;   //客户端ID
	private String clientIP;    //IP
	private String timeStamp;  //时间戳
	public Authenticator(String clientID, String clientIP, String timeStamp) {
		super();
		this.clientID = clientID;
		this.clientIP = clientIP;
		this.timeStamp = timeStamp;
	}
	public Authenticator() {
		this.clientID = "0000";
		this.clientIP = "0000";
		this.timeStamp = "00000000000000";
	}
	@Override
	public String toString() {
		return "Authenticator [clientID=" + clientID + ", clientIP=" + clientIP + ", timeStamp=" + timeStamp + "]";
	}
	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	public String getClientIP() {
		return clientIP;
	}
	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	
}
