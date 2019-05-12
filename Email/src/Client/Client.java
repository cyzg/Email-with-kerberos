  package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import DataStruct.Authenticator;

public class Client {
	
	private static int Number = -1; //包的初始编号
	private final int MAXNUMBER = 0; //包的最大编号
	private final static String ASID = "0001"; //ASID
	private final static String TGSID = "0010"; 
	private final static String SERVERID = "0011"; 
	
	
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
	public static DataStruct.Package clientToAS(int clientID,int tgsID,String TS1){ 
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
		
		DataStruct.Head h= new DataStruct.Head(clientID1,ASID,"0","0","1","1","1","0","0","0",number,"00","0000");
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
	DataStruct.Package clientToTGS(int clientID,int serverID, DataStruct.Ticket ticketTGS, DataStruct.Authenticator authTGS){
		DataStruct.Package p= new DataStruct.Package();

		String clientID1 = supplement(4, Integer.toBinaryString(clientID));
		String serverID1 = supplement(4, Integer.toBinaryString(serverID));
		
		p.setRequestID(serverID1);
		p.setTicket(ticketTGS);
		p.setAuth(authTGS);
		
		if(Number > 16)
		{
			Number = -1;
		}
		Number++;
		String number = supplement(4, Integer.toBinaryString(Number));
		
		DataStruct.Head h= new DataStruct.Head(clientID1,TGSID,"0","0","0","1","0","0","1","1",number,"00","0000");
		p.setHead(h);
		
		return p;
	}
	/**
	 * 封装发送给 server 的包
	 * 设置头部对应标志字段
	 * @param ticketV Server票据
	 * @param authV   Server认证
	 * @return 返回包类型
	 */
	DataStruct.Package clentToV(int clientID,DataStruct.Ticket ticketV, DataStruct.Authenticator authV) {
		DataStruct.Package p= new DataStruct.Package();

		String clientID1 = supplement(4, Integer.toBinaryString(clientID));
		
		p.setTicket(ticketV);
		p.setAuth(authV);
		
		if(Number > 16)
		{
			Number = -1;
		}
		Number++;
		String number = supplement(4, Integer.toBinaryString(Number));
		
		DataStruct.Head h= new DataStruct.Head(clientID1,TGSID,"0","0","0","0","0","0","1","1",number,"00","0000");
		p.setHead(h);
		
		return p;
	}
	/**
	 * 生成认证
	 * @param ID ClientID
	 * @param AD ClientAD
	 * @param k Client和接收方的DES密钥
	 * @return
	 */
	public DataStruct.Authenticator generateAuth(String ID,String AD,String k){
		DataStruct.Authenticator a= new Authenticator(ID,AD,DataStruct.Package.Create_TS());
		String cipher = Des.DES.encrypt(a.AuthOutput(), k);
		a.setClientID("");
		a.setClientIP("");
		a.setTimeStamp("");
		char M[] = cipher.toCharArray();
		
		for(int i = 0;i<cipher.length();i++)
		{
			if(i<4) {
				a.setClientID(a.getClientID()+M[i]);
			}
			else if(i<14) {
				a.setClientIP(a.getClientIP()+M[i]);
			}
			else {
				a.setTimeStamp(a.getTimeStamp()+M[i]);
			}
		}
		//加密后放入a中
		return a;
	}
    /*
	 * 将string字符串编程ascii码二进制编码的string字符串
	 */
	public static String StringoBinary(String string) 
	{
		int length = string.length();
		char M[] = string.toCharArray();
		//System.out.println(""+M[0]+M[1]+M[2]+M[3]+"-"+M[4]+M[5]+"-"+M[6]+M[7]+" "+M[8]+M[9]+":"+M[10]+M[11]+":"+M[12]+M[13]);
		int M1[] = new int[M.length];
		String tmp = new String(); 

		String s ="";  //进行二进制的累加
		for(int i=0;i<M.length;i++)
		{
			M1[i] = M[i]-'\0'; //每一位都是int了，现在开始转换二进制
	
			tmp = supplement(8, Integer.toBinaryString(M1[i]));
			
			
			//每一位都转成了二进制
			s = s + tmp; //加入string中
		}
 		return s;	
	}
    /*
	 * 将string字符串编程ascii码二进制编码的string字符串(数字转)
	 */
	public static String StringToBinary(String string) 
	{
		char M[] = string.toCharArray();
		//System.out.println(""+M[0]+M[1]+M[2]+M[3]+"-"+M[4]+M[5]+"-"+M[6]+M[7]+" "+M[8]+M[9]+":"+M[10]+M[11]+":"+M[12]+M[13]);
		int M1[] = new int[M.length];
		String tmp = new String(); 

		String s ="";  //进行二进制的累加
		for(int i=0;i<M.length;i++)
		{
			if (Character.isDigit(M[i])){  // 判断是否是数字
			    M1[i] = Integer.parseInt(String.valueOf(M[i]));
			}
			else {
				System.err.println("String转Binary出错，并不是数字");
			}
	
			tmp = supplement(4, Integer.toBinaryString(M1[i]));
			//每一位都转成了二进制
			s = s + tmp; //加入string中
		}
 		return s;		
	}
	/**
	 * 二进制转十进制
	 * @param string
	 * @return
	 */
	public static String BinaryToString(String string) 
	{
		int length = string.length();
		char C[] = string.toCharArray();
		String M[] = new String[length/4];
		for(int i=0;i<M.length;i++){
			M[i] = "";
		}
		//System.out.println(""+M[0]+M[1]+M[2]+M[3]+"-"+M[4]+M[5]+"-"+M[6]+M[7]+" "+M[8]+M[9]+":"+M[10]+M[11]+":"+M[12]+M[13]);
		int M1[] = new int[length/4];
		String s ="";  //进行二进制的累加
		for(int i1=0;i1<length;i1++)
		{
			M[i1/4] = M[i1/4]+C[i1];
			if(i1%4 == 3) {
				M1[i1/4] = Integer.parseInt(M[i1/4],2);
				s = s + M1[i1/4]; //加入string中
			}
		}
			return s;		
	}
	/**
	 * 将 Package 类型转化为二进制流数据
	 * 依次判断每一属性，进行拼接
	 * @param p 数据包
	 * @return 二进制字符串
	 */
	static String packageToBinary(DataStruct.Package p)
	{ 
		String s = new String();
		String send = p.toString();
		String lt = p.getLifeTime();
		String ts = p.getTimeStamp();
		
		
		if(p.getHead().getExistTS().equals("1")) {
			s = StringToBinary(p.getTimeStamp());
			p.setTimeStamp(s);
		}
		
		if(p.getHead().getExistLifeTime().equals("1")) {
			s = StringToBinary(p.getLifeTime());
			p.setLifeTime(s);
		}
		
		send = p.packageOutput();
		System.out.println(p.toString());
		p.setTimeStamp(ts);
		p.setLifeTime(lt);
		return send;
	}
	/**
	 * 发送消息
	 * 用 socket 输出流进行发送
	 * @param socket 套接字，对应的 socket 对象
	 * @param message 要发送的信息
	 * @return 发送成功返回 true
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	static boolean send(Socket socket,String message) throws IOException{
		OutputStream os=null; 		
        try {  
        	 os = socket.getOutputStream();   
	            os.write(message.getBytes());   
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
        	os.flush();
           socket.shutdownOutput();
         }
       
		return true;
	}
	/**
	 * 接收消息
	 * @param socket 传入对应的 socket 对象,
	 * @return 返回接收到的消息
	 */
	static String receive(Socket socket){
	      InputStream is=null;
	      String ssss = null;
	        try {
	            is = socket.getInputStream();
	            //4.对获取的输入流进行的操作
	            
	            byte [] b = new byte[20];
	            int len;
	            while((len = is.read(b))!=-1){
	                String str = new String(b,0,len);
	                ssss+=str;
	                System.out.println(str);
	            }
	            System.out.println(ssss);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }finally{
	            //5.关闭相应的流以及Socket,ServerSocket的对象
	            if(is!=null){
	                try {
	                    is.close();
	                } catch (IOException e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                }
	            }
	            if(socket!=null){
	                try {
	                    socket.close();
	                } catch (IOException e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                }
	            } 
	        }
	        
		return ssss;
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
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws IOException {
		
		System.out.println("--client--");
		
		DataStruct.Package p= new DataStruct.Package();
		
		int clientID = 4;
		int tgsID = 3;
		String TS1 = DataStruct.Package.Create_TS();
		
		p = clientToAS(clientID, tgsID , TS1);

		System.out.println(p.toString());
		//System.out.println(StringToBinary("4"));
		
		System.out.println("---------------");
		

        	System.out.println("client on");

    		Socket socket = new Socket("192.168.1.111",5555);
		System.out.println("发送："+p.getHead().headOutput()+packageToBinary(p));
        	String message =p.getHead().headOutput()+packageToBinary(p);
        	String s = "";
        	if(send(socket,message)) {
        		s = receive(socket);
        		System.out.println("收到"+s);
        	}
	}

}

