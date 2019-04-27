package DataStruct;

public class Package{          //完整数据包
	private Head head;      //头部
	private String sessionKey;  
	private String ID;        //发送请求的ID
	private String requestID;  //被请求方的ID
	private String timeStamp;  //时间戳
	private String lifeTime;    //ticket有效期
	private Ticket Ticket;    //被请求方的票据
	private Authenticator Auth; //请求方的确认信息
}

