package DataStruct;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class APPPackage {
	private Head head;      //头部
	private String sendID;        //发送请求的ID
	private String receiveID;  //被请求方的ID
	private String timeStamp;  //时间戳
	private APPAuthenticator Auth; //请求方的确认信息
	private Email Email;
	
	
	  public static String Create_TS()
	    {
	    	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	    	Calendar calendar = Calendar.getInstance();    
		    calendar.setTime(new Date());   
		   // System.out.println();
		    return df.format(calendar.getTime());
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
		
		public APPPackage(Head head, String sendID, String receiveID, String timeStamp,APPAuthenticator auth,DataStruct.Email email) {
			super();
			this.head = head;
			this.sendID = sendID;
			this.receiveID = receiveID;
			this.timeStamp = timeStamp;
	
			Auth =auth;
			Email=email;
			}
		
		public APPPackage() {
			this.head = new Head();
			this.sendID="";
			this.receiveID = "";
			this.timeStamp="";
			Email=new Email();
			Auth = new APPAuthenticator();
		}
		
		@Override
		public String toString() {
			return "APPPackage [head=" + head + ", sendID=" + sendID + ", receiveID=" + receiveID
	+ ", timeStamp=" + timeStamp  +  ", Auth=" + Auth + ", Email=" + Email + ", sessionKey=" +"]";
		}
		public String packageOutput() {
			return sendID + receiveID + timeStamp + Email.EmailOutput() + Auth.APPAuthOutput();
		}
		
		public Head getHead() {
			return head;
		}
		public void setHead(Head head) {
			this.head = head;
		}
		public void setsendID(String sendID) {
			this.sendID=sendID;
		}
		
		public String getsendID() {
			return sendID;
		}
		public void setreceiveID(String receiveID) {
			this.receiveID=receiveID;
		}
		public String getreceiveID() {
			return receiveID;
		}
		public String getTimeStamp() {
			return timeStamp;
		}
		public void setTimeStamp(String timeStamp) {
			this.timeStamp = timeStamp;
		}
		
		public Email getEmail() {
			return Email;
		}
		public void setEmail( Email email) {
			Email = email;
		}
		public APPAuthenticator getAuth() {
			return Auth;
		}
		public void setAuth(APPAuthenticator auth) {
			Auth = auth;
		}
}
