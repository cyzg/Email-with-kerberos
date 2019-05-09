package DataStruct;
/**
 * 完整数据包
 * @author zhuo
 *
 */
public class Package{          
	private Head head;      //头部
	private String sessionKey;  
	private String ID;        //发送请求的ID
	private String requestID;  //被请求方的ID
	private String timeStamp;  //时间戳
	private String lifeTime;    //ticket有效期
	private Ticket Ticket;    //被请求方的票据
	private Authenticator Auth; //请求方的确认信息
	
	public Package(Head head, String sessionKey, String iD, String requestID, String timeStamp, String lifeTime,
			DataStruct.Ticket ticket, Authenticator auth) {
		super();
		this.head = head;
		this.sessionKey = sessionKey;
		ID = iD;
		this.requestID = requestID;
		this.timeStamp = timeStamp;
		this.lifeTime = lifeTime;
		Ticket = ticket;
		Auth = auth;
	}	
	public Package() {
		this.head = new Head();
		this.sessionKey = "00000000";
		ID = "0000";
		this.requestID = "000";
		this.timeStamp = "00000000000000";
		this.lifeTime = "00000000000000";
		Ticket = new Ticket();
		Auth = new Authenticator();
	}
	
	@Override
	public String toString() {
		return "Package [head=" + head + ", sessionKey=" + sessionKey + ", ID=" + ID + ", requestID=" + requestID
				+ ", timeStamp=" + timeStamp + ", lifeTime=" + lifeTime + ", Ticket=" + Ticket + ", Auth=" + Auth + "]";
	}
	
	
	public Head getHead() {
		return head;
	}
	public void setHead(Head head) {
		this.head = head;
	}
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
	public Ticket getTicket() {
		return Ticket;
	}
	public void setTicket(Ticket ticket) {
		Ticket = ticket;
	}
	public Authenticator getAuth() {
		return Auth;
	}
	public void setAuth(Authenticator auth) {
		Auth = auth;
	}
	
}

