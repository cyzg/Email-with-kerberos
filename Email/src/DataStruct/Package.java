package DataStruct;

import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
	/**
	 * 
	 * @param time存活时间（分钟）
	 * @return
	 */
	 public static String Create_lifeTime(int time)
	    {
		 
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		  Date now = new Date();
		  Date afterDate = new Date(now .getTime() + time*60000);
			return sdf.format(afterDate);
	   }
	/**
	 * 
	 * @param time存活时间（分钟）
	 * @return
	 */
	 public static String Create_lifeTime(String strDate,int time)
	    {
	        //注意：SimpleDateFormat构造函数的样式与strDate的样式必须相符
	        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
	        SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyyMMddHHmmss"); //加上时间
	        //必须捕获异常
	        try {
	            Date date=simpleDateFormat.parse(strDate);
	            Date afterDate = new Date(date.getTime() + time*60000);
	            return sDateFormat.format(afterDate);
	        } catch(ParseException px) {
	            px.printStackTrace();
	        }
			return strDate;
	   }
	 
    public static String Create_TS()
    {
    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
    	Calendar calendar = Calendar.getInstance();    
	    calendar.setTime(new Date());   
	   // System.out.println();
	    return df.format(calendar.getTime());
    }
    public static boolean  isalive(String lifeTime ) {
		String TimeNow="";
          SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
          TimeNow=df.format(new Date());// new Date()为获取当前系统时间
          if(lifeTime.compareTo(TimeNow) >=0) {
        	 return true; 
          }
          else {return false;}
	}
	/**
	 * ip转binary
	 * @param ip
	 * @return
	 */
	public static String ipToBinary(InetAddress ip)
    {
        byte[] b=ip.getAddress();
        long l= b[0]<<24L & 0xff000000L|
                b[1]<<16L & 0xff0000L  |
                b[2]<<8L  &  0xff00L   |
                b[3]<<0L  &  0xffL ;
         
        return Integer.toBinaryString((int)l);
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
	

	long HashCode() {

		final int prime = 31;
		long result = 1;
		result = prime * result + ((Auth == null) ? 0 : Auth.hashCode());
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		result = prime * result + ((Ticket == null) ? 0 : Ticket.hashCode());
		result = prime * result + ((lifeTime == null) ? 0 : lifeTime.hashCode());
		result = prime * result + ((requestID == null) ? 0 : requestID.hashCode());
		result = prime * result + ((sessionKey == null) ? 0 : sessionKey.hashCode());
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
		System.out.println("Auth.hashCode()："+ID.hashCode());
		System.out.println("timeStamp.hashCode()："+Ticket.hashCode());
		System.out.println(result);
		return (int)result;
    }
	public int hashCode() {
		return packageOutput().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Package other = (Package) obj;
		if (Auth == null) {
			if (other.Auth != null)
				return false;
		} else if (!Auth.equals(other.Auth))
			return false;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		if (Ticket == null) {
			if (other.Ticket != null)
				return false;
		} else if (!Ticket.equals(other.Ticket))
			return false;
		if (lifeTime == null) {
			if (other.lifeTime != null)
				return false;
		} else if (!lifeTime.equals(other.lifeTime))
			return false;
		if (requestID == null) {
			if (other.requestID != null)
				return false;
		} else if (!requestID.equals(other.requestID))
			return false;
		if (sessionKey == null) {
			if (other.sessionKey != null)
				return false;
		} else if (!sessionKey.equals(other.sessionKey))
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		return true;
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

