package Client;

import java.net.Socket;

public class Client {
	
	private static int Number = -1; //包的初始编号
	private final int MAXNUMBER = 0; //包的最大编号
	private final static String ASID = "0001"; //包的最大编号
	
	/**
	 * 把str补齐到n位，高位写0
	 * @param n 
	 * @param str 要补齐的字符串
	 * @return
	 */
	public static String supplement(int n,String str){ 
		if(n>str.length()) {
			int sl=str.length();//string原长度
			for(int i=0;i<(n-sl);i++) {
				str="0"+str;
			}
		}
		return str;
	}
	/**
	 * 封装发送给AS的包 
	 * 输入要发送的信息
	 * 设置头部对应标志字段
	 * @param clientID 客户端ID
	 * @param tgsID TGSID
	 * @param TS1 时间戳
	 * @return 返回封装完成的包
	 */
	static DataStruct.Package clientToAS(int clientID,int tgsID,String TS1){ 
		DataStruct.Package p= new DataStruct.Package();
		
		String clientID1 = supplement(4, Integer.toBinaryString(clientID));
		String tgsID1 = supplement(4, Integer.toBinaryString(tgsID));
		
		p.setID(clientID1); 
		p.setRequestID(tgsID1);
		p.setTimeStamp(TS1);
		
		if(Number > 16)
		{
			Number = -1;
		}
		Number++;
		String number = supplement(4, Integer.toBinaryString(Number));
		
		DataStruct.Head h= new DataStruct.Head(number,clientID1,ASID,"0","0","1","1","1","0","0","0");
		p.setHead(h);
		
		return p;
	}
	
	/**
	 * client发送给TGS的数据包
	 * 设置头部对应标志字段
	 * @param serverID client要访问的服务器ID
	 * @param ticketTGS TGS的票据
	 * @param authTGS client发送给TGS的认证
	 * @return 返回封装完成的包
	 */
	DataStruct.Package clientToTGS(int serverID, DataStruct.Ticket ticketTGS, DataStruct.Authenticator authTGS){
		return new DataStruct.Package();
	}
	/**
	 * 封装发送给 server 的包
	 * 设置头部对应标志字段
	 * @param ticketV Server票据
	 * @param authV   Server认证
	 * @return 返回包类型
	 */
	DataStruct.Package clentToV(DataStruct.Ticket ticketV, DataStruct.Authenticator authV) {
		return new DataStruct.Package();
	}
	/**
	 * 将 Package 类型转化为二进制流数据
	 * 依次判断每一属性，进行拼接
	 * @param p 数据包
	 * @return 二进制字符串
	 */
	static String packageToBiarny(DataStruct.Package p)
	{ 
		String send = p.toString();
		return send;
	}
	/**
	 * 发送消息
	 * 用 socket 输出流进行发送
	 * @param socket 套接字，对应的 socket 对象
	 * @param message 要发送的信息
	 * @return 发送成功返回 true
	 */
	boolean send(Socket socket,String message){
		return true;
	}
	/**
	 * 接收消息
	 * @param socket 传入对应的 socket 对象,
	 * @return 返回接收到的消息
	 */
	String receive(Socket socket){
		return "";
	}
	/**
	 * 包的分析
	 * 将收到的消息分离出头部
	 * 按照头部标志位进行解析数据部分
	 * 对应数据段放到 package 属性对应部分
	 * @param message 接受的消息
	 * @return 返回对应的数据
	 */
	DataStruct.Package packageAnalyse(String message){
		return new DataStruct.Package();
	}
	/**
	 * 生成Kc
	 * 用 clientID 和 client 的 pwd 的哈希值放入哈希函数中，生成 8 个字节的密钥
	 * @param p
	 * @return 以字符串形式返回密钥
	 */
	public String generateKeyc(DataStruct.Package p){
		return "";
	}
	/**
	 * 注册
	 * 客户在客户端设置 ID和密码
	 * 并且将密码和公钥用AS的公钥加密
	 * @return 加密后的包
	 */
	 DataStruct.Package login(){
		 return new DataStruct.Package();
	}
	/**
	 * 与Server端连接后的通信
	 */
	void connect()
	{  
	}
	
	/**
	 * 主函数
	 */
	public static void main(String[] args) {
		
		System.out.println("--client--");
		
		DataStruct.Package p= new DataStruct.Package();
		
		int clientID = 4;
		int tgsID = 3;
		String TS1 ="123";
		
		p = clientToAS(clientID, tgsID , TS1);
		System.out.println(p.getID());
		DataStruct.Head h= p.getHead();
		System.out.println(p.getHead().getNumber());
		System.out.println(packageToBiarny(p));

		System.out.println("---------------");
		System.out.println(p.getHead().hashCode());
		
		
	}

}

