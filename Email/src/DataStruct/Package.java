package DataStruct;

public class Package{          //�������ݰ�
	private Head head;      //ͷ��
	private String sessionKey;  
	private String ID;        //���������ID
	private String requestID;  //�����󷽵�ID
	private String timeStamp;  //ʱ���
	private String lifeTime;    //ticket��Ч��
	private Ticket Ticket;    //�����󷽵�Ʊ��
	private Authenticator Auth; //���󷽵�ȷ����Ϣ
	
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
		super();
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

