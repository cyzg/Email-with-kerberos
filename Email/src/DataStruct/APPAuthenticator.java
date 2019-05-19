package DataStruct;

public class APPAuthenticator {
	private String sendID;   //客户端ID
	private String receiveID;    //IP
	private String timeStamp;  //时间戳
	private String sessionkey;  
	public APPAuthenticator(String sendID, String receiveID, String timeStamp,String sessionkey) {
		super();
		this.sendID = sendID;
		this.receiveID = receiveID;
		this.timeStamp = timeStamp;
		this.sessionkey=sessionkey;
	}
	public APPAuthenticator() {
		this.sendID = "";
		this.receiveID = "";
		this.timeStamp = "";
		this.sessionkey="";
	}
	
	
	@Override
	public String toString() {
		return "Authenticator [sessionkey"+sessionkey+"sendID=" + sendID + ", receiveID=" + receiveID + ", timeStamp=" + timeStamp + "]";
	}
	public String APPAuthOutput() {
		return sessionkey+sendID + receiveID + timeStamp;
	}
	public String getsessionkey() {
		return sessionkey;
	}
	public void setsessionkey(String sessionkey) {
		this.sessionkey = sessionkey;
	}
	public String getsendID() {
		return sendID;
	}
	public void setsendID(String sendID) {
		this.sendID = sendID;
	}
	public String getreceiveID() {
		return receiveID;
	}
	public void setreceiveID(String receiveID) {
		this.receiveID = receiveID;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
}
