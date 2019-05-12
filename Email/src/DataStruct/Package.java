package DataStruct;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * �������ݰ�
 * @author zhuo
 *
 */
public class Package{          
	private Head head;      //ͷ��
	private String sessionKey;  
	private String ID;        //���������ID
	private String requestID;  //�����󷽵�ID
	private String timeStamp;  //ʱ���
	private String lifeTime;    //ticket��Ч��
	private Ticket Ticket;    //�����󷽵�Ʊ��
	private Authenticator Auth; //���󷽵�ȷ����Ϣ
	
	
    public static String Create_TS()
    {
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    	Calendar calendar = Calendar.getInstance();    
	    calendar.setTime(new Date());   
	   // System.out.println();
	    return df.format(calendar.getTime());
    }
    
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
		this.sessionKey = "";
		ID = "";
		this.requestID = "";
		this.timeStamp = "";
		this.lifeTime = "";
		Ticket = new Ticket();
		Auth = new Authenticator();
	}
	
	@Override
	public String toString() {
		return "Package [head=" + head + ", sessionKey=" + sessionKey + ", ID=" + ID + ", requestID=" + requestID
				+ ", timeStamp=" + timeStamp + ", lifeTime=" + lifeTime + ", Ticket=" + Ticket + ", Auth=" + Auth + "]";
	}
	public String packageOutput() {
		return sessionKey + ID + requestID + timeStamp + lifeTime + Ticket.ticketOutput() + Auth.AuthOutput();
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

