  package Client;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;

import Client.UI.Send_email;
import Client.UI.WELCOME;
import DataStruct.Authenticator;
import DataStruct.Ticket;
import Des.DES;
import Server.V;

public class Client {
	
	private static int Number = -1; //包的初始编号
	private final int MAXNUMBER = 0; //包的最大编号
	private final static String ASID = "1101"; //ASID
	public final static String TGSID = "1110"; 
	public final static String SERVERID = "1111"; 
	private final static String[] Kc = {"3096589494327972966542767555645488415857410521298179560751893624567975523927775168085739664949238616280271893353946263715523651672294362843822766996968340714023382235747900221065977","3889"}; //client公钥
	private final static String[] selfK ={"3096589494327972966542767555645488415857410521298179560751893624567975523927775168085739664949238616280271893353946263715523651672294362843822766996968340714023382235747900221065977" , "1152163794881094595676879571359995304125912323044089952277703799112846640042039256420690483427161040887459792478554485040196767218736825329254102072887596847184101841933983341452289"}; //client私钥
	public static String ASIP = "192.168.43.115";
	public static String TGSIP = "192.168.43.37";
	public static String SERVERIP = "192.168.43.252";
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
	 public static DataStruct.Package clientToAS(String clientID,String tgsID){ 
		DataStruct.Package p= new DataStruct.Package();
		
		p.setID(clientID); 
		p.setRequestID(tgsID);
		p.setTimeStamp(DataStruct.Package.Create_TS());
		
		if(Number > 16)
		{
			Number = -1;
		}
		Number++;
		String number = supplement(4, Integer.toBinaryString(Number));
		String securityCode = DataStruct.Head.zero(128);
		DataStruct.Head h= new DataStruct.Head(ASID,clientID,"0","0","1","1","1","0","0","0",number,securityCode,"0000000000000000");
		p.setHead(h);
		
		return p;
	}
	
	/**
	 * client发送给TGS的数据包
	 * 设置头部对应标志字段
	 * @param tgsid client要访问的服务器ID
	 * @param ticketTGS TGS的票据
	 * @param authTGS client发送给TGS的认证
	 * @return 返回封装完成的包
	 */
	public static DataStruct.Package clientToTGS(String clientID,String serverID, DataStruct.Ticket ticketTGS, DataStruct.Authenticator authTGS){
		DataStruct.Package p= new DataStruct.Package();
		
		p.setRequestID(serverID);
		p.setTicket(ticketTGS);
		p.setAuth(authTGS);
		
		if(Number > 16)
		{
			Number = -1;
		}
		Number++;
		String number = supplement(4, Integer.toBinaryString(Number));
		

		String tic = Integer.toString((p.getTicket().ticketOutput().length())); //tickettgs加密后的长度
		tic = supplement(16, StringToBinary(tic));
		String securityCode = DataStruct.Head.zero(128);
		
		DataStruct.Head h= new DataStruct.Head(TGSID,clientID,"0","0","0","1","0","0","1","1",number,securityCode,tic);
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
	public static DataStruct.Package clentToV(String clientID,DataStruct.Ticket ticketV, DataStruct.Authenticator authV) {
		DataStruct.Package p= new DataStruct.Package();
		
		p.setTicket(ticketV);
		p.setAuth(authV);
		p.setTimeStamp(DataStruct.Package.Create_TS());
		
		if(Number > 16)
		{
			Number = -1;
		}
		Number++;
		String number = supplement(4, Integer.toBinaryString(Number));
		
		String tic = Integer.toString((p.getTicket().ticketOutput().length())); //tickettgs加密后的长度
		tic = supplement(16, StringToBinary(tic));
		String securityCode = DataStruct.Head.zero(128);
		DataStruct.Head h= new DataStruct.Head(TGSID,clientID,"0","0","0","0","0","0","1","1",number,securityCode,tic);
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
	public static DataStruct.Authenticator generateAuth(String ID,String AD,String k){

		String ts = DataStruct.Package.Create_TS();
		DataStruct.Authenticator a= new Authenticator(ID,AD,StringToBinary(ts));
		System.out.println(a);
		String cipher = Des.DES.encrypt(a.AuthOutput(), k);
		//加密后放入a中
		a.setClientID("");
		a.setClientIP("");
		a.setTimeStamp("");
		char M[] = cipher.toCharArray();

		for(int i = 0;i<cipher.length();i++)
		{
			if(i<4) {
				a.setClientID(a.getClientID()+M[i]);
			}
			else if(i<40) {
				a.setClientIP(a.getClientIP()+M[i]);
			}
			else {
				a.setTimeStamp(a.getTimeStamp()+M[i]);
			}
		}
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
	
	public static String BinaryoString(String string) {
		// TODO Auto-generated method stub
		int length = string.length();
		if(length%8 != 0) {
			System.err.println("Client二进制转十进制时，二进制长度有误");
		}
		char C[] = string.toCharArray();
		String M[] = new String[length/8];
		for(int i=0;i<M.length;i++){
			M[i] = "";
		}
		//System.out.println(""+M[0]+M[1]+M[2]+M[3]+"-"+M[4]+M[5]+"-"+M[6]+M[7]+" "+M[8]+M[9]+":"+M[10]+M[11]+":"+M[12]+M[13]);
		int M1[] = new int[length/8];
		String s ="";  //进行二进制的累加
		for(int i1=0;i1<length;i1++)
		{
			M[i1/8] = M[i1/8]+C[i1];
			if(i1%8 == 7) {
				M1[i1/8] = Integer.parseInt(M[i1/8],2);
				s = s + (char)M1[i1/8]; //加入string中
			}
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
		if(length%4 != 0) {
			System.err.println("Client二进制转十进制时，二进制长度有误");
		}
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
	public static String packageToBinary(DataStruct.Package p)
	{ 
		String s = new String();
		String send = "";
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
		//生成消息认证码
		String sc = DataStruct.Head.MD5(p.packageOutput());
		p.getHead().setSecurityCode(sc);
		send = p.getHead().headOutput()+p.packageOutput();
		p.setLifeTime(lt);
		p.setTimeStamp(ts);
		return send;
	}
	public static String appackageToBinary(DataStruct.APPPackage p)
	{ 
		String s = new String();
		String send = "";
		String ts = p.getTimeStamp();

		if(p.getHead().getExistTS().equals("1")) {
			s = StringToBinary(p.getTimeStamp());
			System.out.println("二进制tp"+s);
			p.setTimeStamp(s);
			p.getAuth().setTimeStamp(s);
			p.getEmail().setTimeStamp(s);
			
		}
		//加密auth
		RSA.rsa rsa= new RSA.rsa();
		String au=rsa.encrypt(p.getAuth().APPAuthOutput(), Kc);
		String auth = Integer.toString((au.length())); //auth加密后的长度
		auth = supplement(16, StringToBinary(auth));
		p.getHead().setExpend(auth);
		//生成消息认证码
		String m =p.getsendID()+p.getreceiveID()+p.getTimeStamp()+au+p.getEmail().EmailOutput();
		String sc = DataStruct.Head.MD5(m);
		p.getHead().setSecurityCode(sc);
		send = p.getHead().headOutput()+m;
		p.setTimeStamp(ts);
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
	public static boolean send(Socket socket,String message) throws IOException{
		OutputStream os=null; 		
        try {  
        	 os = socket.getOutputStream();   
	            os.write(message.getBytes()); 
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
	        socket.shutdownOutput();  
        	os.flush();
         }
       
		return true;
	}
	/**
	 * 接收消息
	 * @param socket 传入对应的 socket 对象,
	 * @return 返回接收到的消息
	 */
	public static String receive(Socket socket){
	      InputStream is=null;
	      String ssss = "";
	        try {
	            is = socket.getInputStream();
	            //4.对获取的输入流进行的操作
	            
	            byte [] b = new byte[20];
	            int len;
	            while((len = is.read(b))!= -1){
	                String str = new String(b,0,len);
	                ssss+=str;
	            }
        		System.out.println("收到："+ssss);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }finally{
	            //5.关闭相应的流以及Socket,ServerSocket的对象

				try {
					socket.shutdownInput();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
	 * @param k1 Package密钥
	 * @return 返回对应的数据
	 */
	public static DataStruct.Package packageAnalyse(String message,String k){
		System.out.println("-----开始解析包-----");
		DataStruct.Package p = new DataStruct.Package();
		int headLength = p.getHead().headOutput().length();
		
		char M[] = message.toCharArray();
		String s[] = new String[headLength];
		for(int i = 0;i<headLength;i++)
		{
			s[i] = String.valueOf(M[i]);
		}
		p.setHead(new DataStruct.Head( s[0]+s[1]+s[2]+s[3] , s[4]+s[5]+s[6]+s[7] , s[8] , s[9] , s[10] , 
				s[11] , s[12] , s[13] , s[14], s[15], s[16]+s[17]+s[18]+s[19] ,  "",
				s[148]+s[149]+s[150]+s[151]+s[152]+s[153]+s[154]+s[155]+s[156]+s[157]+s[158]+s[159]+s[160]+s[161]+s[162]+s[163] ));
		for(int n = 20 ; n < 148 ; n++) {
			p.getHead().setSecurityCode(p.getHead().getSecurityCode()+s[n]);
		}
		String pack = message.replaceFirst(p.getHead().headOutput(), "");
		//验证消息验证码
		if(DataStruct.Head.MD5(pack).equals(p.getHead().getSecurityCode())) {	
			 if(p.getHead().getExistLifeTime().equals("1")&&p.getHead().getExistTicket().equals("1")) {
				//说明是AS发的
				//package解密 用client私钥
				String m = message.replaceFirst(p.getHead().headOutput(),"");
				RSA.rsa rsa = new RSA.rsa();	
				message = rsa.decrypt(m, selfK);
	
				System.out.println("密文:"+m);
				System.out.println("明文:"+message);
				char C[] = message.toCharArray();
				//解包
				int tic = Integer.parseInt(BinaryToString(p.getHead().getExpend()));
				DataStruct.Ticket ticket = new Ticket();
				for(int i = 0;i<message.length();i++)
				{	
					if(i<64)
						p.setSessionKey(p.getSessionKey()+C[i]);
					else if(i<68)
						p.setRequestID(p.getRequestID()+C[i]);
					else if(i<68+56)
						p.setTimeStamp(p.getTimeStamp()+C[i]);
					else if(i<68+56+56)
						p.setLifeTime(p.getLifeTime()+C[i]);
					else if(i<68+56+56+tic+1){
						if(i<68+56+56+64)
							ticket.setSessionKey(ticket.getSessionKey()+C[i]);
						else if(i<68+56+56+68)
							ticket.setID(ticket.getID()+C[i]);
						else if(i<68+56+56+100)
							ticket.setIP(ticket.getIP()+C[i]);
						else if(i<68+56+56+104)
							ticket.setRequestID(ticket.getRequestID()+C[i]);
						else if(i<68+56+56+104+56)
							ticket.setTimeStamp(ticket.getTimeStamp()+C[i]);
						else 
							ticket.setLifeTime(ticket.getLifeTime()+C[i]);
					}
					else {
							System.err.println("分析发现AS发过来的package长度有误，请检查！！");
						       System.exit(0);
					}
				}	
	
				String string = p.getTimeStamp();
				p.setTimeStamp(BinaryToString(string));
				p.setLifeTime(BinaryToString(p.getLifeTime()));
				
				p.setTicket(ticket);
			}
			else if(p.getHead().getExistTicket().equals("1")){
				//说明是TGS发的
				//package解密 用Ktgs，c 传参进来k
				String m = message.replaceFirst(p.getHead().headOutput(),"");
				message = DES.decrypt(m, k);
				char C[] = message.toCharArray();
				//解包
				int tic = Integer.parseInt(BinaryToString(p.getHead().getExpend()));
				DataStruct.Ticket ticket = new Ticket();
				for(int i = 0;i<message.length();i++)
				{	
					if(i<64)
						p.setSessionKey(p.getSessionKey()+C[i]);
					else if(i<68)
						p.setRequestID(p.getRequestID()+C[i]);
					else if(i<68+56)
						p.setTimeStamp(p.getTimeStamp()+C[i]);
					else if(i<68+56+tic+1){
						if(i<68+56+64)
							ticket.setSessionKey(ticket.getSessionKey()+C[i]);
						else if(i<68+56+68)
							ticket.setID(ticket.getID()+C[i]);
						else if(i<68+56+100)
							ticket.setIP(ticket.getIP()+C[i]);
						else if(i<68+56+104)
							ticket.setRequestID(ticket.getRequestID()+C[i]);
						else if(i<68+56+104+56)
							ticket.setTimeStamp(ticket.getTimeStamp()+C[i]);
						else 
							ticket.setLifeTime(ticket.getLifeTime()+C[i]);
					}
					else {
							System.err.println("分析发现TGS发过来的package长度有误，请检查！！");
						       System.exit(0);
					}
				}	
	
				String string = p.getTimeStamp();
				p.setTimeStamp(BinaryToString(string));
				p.setTicket(ticket);
			}
			else if(p.getHead().getExistTS().equals("1")){
				//说明是V发的
				//package解密 用Kv，c 传参进来k
				String m = message.replaceFirst(p.getHead().headOutput(),"");
				message = DES.decrypt(m, k);
				p.setTimeStamp(BinaryToString(message.substring(0, 56)));
			}	
			System.out.println("分析的包："+ p);
			return p ;
			}
			else {
				System.err.println("收到的包有误");
				return null;
			}			
	}
	public static DataStruct.APPPackage apppackageAnalyse(String message){
		System.out.println("收到："+message);
		DataStruct.APPPackage p=new DataStruct.APPPackage();
		System.out.println("-----开始解析包-----");
		int headLength = p.getHead().headOutput().length();
		char M[] = message.toCharArray();
		String s[] = new String[headLength];
		for(int i = 0;i<(headLength);i++)
		{
			s[i] = String.valueOf(M[i]);
		}
		p.setHead(new DataStruct.Head( s[0]+s[1]+s[2]+s[3] , s[4]+s[5]+s[6]+s[7] , s[8] , s[9] , s[10] , 
				s[11] , s[12] , s[13] , s[14], s[15], s[16]+s[17]+s[18]+s[19] ,  "",
				s[148]+s[149]+s[150]+s[151]+s[152]+s[153]+s[154]+s[155]+s[156]+s[157]+s[158]+s[159]+s[160]+s[161]+s[162]+s[163] ));
		for(int n = 20 ; n < 148 ; n++) {
			p.getHead().setSecurityCode(p.getHead().getSecurityCode()+s[n]);
		}
		System.out.println(p.getHead());
		String pack = message.replaceFirst(p.getHead().headOutput(), "");
		
		if(DataStruct.Head.MD5(pack).equals(p.getHead().getSecurityCode())) {
				//消息验证码
			if(p.getHead().getExistSessionKey().equals("1")){
				//应用传送包
				String stringid[] = new String[64];
				for(int i = 0;i< 64 ;i++) {
					stringid[i] = String.valueOf(M[headLength+i]);
				}
				p.setsendID(stringid[0]+stringid[1]+stringid[2]+stringid[3]);
				p.setreceiveID(stringid[4]+stringid[5]+stringid[6]+stringid[7]);
				String ts ="";
				for(int n = 8 ; n < 64 ; n++) {
					ts+=stringid[n];
				}
				int au = Integer.parseInt(BinaryToString(p.getHead().getExpend()));//au长度
				System.out.println();
				String auth= message.substring(228, 228+au);
				System.out.println(auth);
				System.out.println(ts);
				RSA.rsa rsa=new RSA.rsa();
				String deauth=rsa.decrypt(auth, selfK);//解密后的AUTH
				p.setTimeStamp(BinaryToString(ts));
				p.getAuth().setsessionkey(deauth.substring(0, 64));
				p.getAuth().setsendID(deauth.substring(64, 68));
				p.getAuth().setreceiveID(deauth.substring(68, 72));
				p.getAuth().setTimeStamp(BinaryToString(deauth.substring(72,128)));
				
				//Email 拆
				String ms="";
				for(int n = 228+au ; n < 228+au+56 ; n++) {
					ms+=String.valueOf(M[n]);
				}
				p.getEmail().setTimeStamp(BinaryToString(ms));
				System.out.println("时间戳"+p.getEmail().getTimeStamp());
				String em=message.substring(228+56+au);//content密文
				System.out.println("em"+em);
				
				//p.getEmail().setcontent(BinaryToString(DES.decrypt(em, p.getAuth().getsessionkey())));
				String c = DES.decrypt(em, p.getAuth().getsessionkey());
				p.getEmail().setcontent(BinaryoString(c));
			}
			else if(p.getHead().getExistTS().equals("1")){
				//收到
				String m = message.replaceFirst(p.getHead().headOutput(),"");
				p.setTimeStamp(BinaryToString(m));
			}
			System.out.println("解析的包"+p);
			return p;
		}
		else {
			System.err.println("收到的包有误");
			return null;
		}	
	}
	/**
	 * 验证包
	 * @param p 分析完的包
	 * @return 包是正确的返回true
	 */
    public static boolean verifyPackage(DataStruct.Package p,String TS){
    	//查看v发的包
    	if(p.getTimeStamp().equals(DataStruct.Package.Create_lifeTime(TS, 1)))
    	return true;
    	else
    		return false;
    }    
	/**
	 * 验证包
	 * @param p 分析完的包
	 * @return 包是正确的返回true
	 */
    public static boolean verifyPackage(DataStruct.APPPackage p,String TS){
    	//查看v发的包
    	if(p.getTimeStamp().equals(DataStruct.Package.Create_lifeTime(TS, 1)))
    	return true;
    	else
    		return false;
    }  
	public static InetAddress getIpAddress() {
	    try {
	      Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
	      InetAddress ip = null;
	      while (allNetInterfaces.hasMoreElements()) {
	        NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
	        if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
	          continue;
	        } else {
	          Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
	          while (addresses.hasMoreElements()) {
	            ip = addresses.nextElement();
	            if (ip != null && ip instanceof Inet4Address) {
	              return ip;
	            }
	          }
	        }
	      }
	    } catch (Exception e) {
	    	System.err.println("IP地址获取失败" + e.toString());
	    }
	    return null;
	  }

	/**
	 * 注册
	 * 客户在客户端设置 ID和密码
	 * 并且将密码和公钥用AS的公钥加密
	 * @return 加密后的包
	 */
	 public static DataStruct.Package signin(String id,String pw,String[] Pk){
		DataStruct.Package p= new DataStruct.Package();
		
		//String clientID1 = supplement(4, Integer.toBinaryString(clientID));
		
		p.setSessionKey(Pk[0]);
		p.setID(supplement(16,id)); 
		p.setRequestID(StringoBinary(pw));
//		p.setLifeTime();
		
		if(Number > 16)
		{
			Number = -1;
		}
		Number++;
		String number = supplement(4, Integer.toBinaryString(Number));

		String sc = DataStruct.Head.MD5(p.packageOutput());
		p.getHead().setSecurityCode(sc);
		String expend = Integer.toString((Pk[0].length())); //tickettgs加密后的长度
		expend = supplement(16, StringToBinary(expend));
		String securityCode = DataStruct.Head.MD5(p.packageOutput());
		DataStruct.Head h= new DataStruct.Head(ASID,"0000","1","1","1","1","0","0","0","0",number,securityCode,expend);
		p.setHead(h);
		
		return p;
	}
		/**
		 * 登录
		 * 客户在客户端设置 ID和密码
		 * 并且将密码和公钥用AS的公钥加密
		 * @return 加密后的包
		 */
		 public static DataStruct.Package login(String id,String pw){
			DataStruct.Package p= new DataStruct.Package();
	
			p.setID(supplement(16,id)); 
			p.setRequestID(StringoBinary(pw));
			
			if(Number > 16)
			{
				Number = -1;
			}
			Number++;
			String number = supplement(4, Integer.toBinaryString(Number));

			String sc = DataStruct.Head.MD5(p.packageOutput());
			p.getHead().setSecurityCode(sc);

			String securityCode = DataStruct.Head.MD5(p.packageOutput());
			DataStruct.Head h= new DataStruct.Head(ASID,"0000","1","0","1","0","0","0","0","0",number,securityCode,"0000000000000000");
			p.setHead(h);
			
			return p;
		}
			public static String generateKeyCC(){
				String skey = new String();
			    for(int i= 0 ;i < 64;i++)
			    {
			    	int x = (int)(Math.random()*2);
			    	if(x == 2) {
			    		x = (int)(Math.random()*2);
			    	}
			    	skey = skey + Integer.toBinaryString(x);  
			    }
				return skey;
			}
	/**
	 * 与Server端连接后的通信
	 */
		public static DataStruct.APPPackage connect(String sendID,String receiveID,String content,String TS)
		{  		
			DataStruct.APPPackage p= new DataStruct.APPPackage();
			DataStruct.APPAuthenticator a=new DataStruct.APPAuthenticator();
			DataStruct.Email e=new DataStruct.Email();
			String sessionkey=generateKeyCC();
	
			System.out.println("test:"+content);
			System.out.println("test:"+StringoBinary(content));
			String content1= DES.encrypt(StringoBinary(content), sessionkey);
			//String content1= DES.encrypt(StringToBinary(content), sessionkey);
			a.setsendID(sendID); 
			a.setreceiveID(receiveID);	
			a.setTimeStamp(TS);
			a.setsessionkey(sessionkey);
			e.setTimeStamp(TS);
			e.setcontent(content1);
			p.setsendID(sendID); 
			p.setreceiveID(receiveID);
			p.setTimeStamp(TS);
			p.setAuth(a);
			p.setEmail(e);
			if(Number > 16)
			{
				Number = -1;
			}
			Number++;
			String number = supplement(4, Integer.toBinaryString(Number));
			String securityCode = DataStruct.Head.zero(128);
			DataStruct.Head h= new DataStruct.Head(sendID,receiveID,"0","1","1","1","1","0","0","1",number,securityCode,"0000000000000000");
			p.setHead(h);
			System.out.println(p.packageOutput());
			return p;
		}
		
		/**
		 * 
		 * @param clientID
		 * @param TS
		 * @param k
		 * @return
		 */
		public static String requestEmail(String clientID){
			DataStruct.Package p = new DataStruct.Package();;
			
			p.setTimeStamp(DataStruct.Package.Create_TS());
			
			if(Number > 16)
			{
				Number = -1;
			}
			Number++;
			String number = supplement(4, Integer.toBinaryString(Number));
			String securityCode = DataStruct.Head.zero(128);
			
			DataStruct.Head h= new DataStruct.Head(SERVERID,clientID,"0","0","0","0","1","0","0","0",number,securityCode,"0000000000000000");
			p.setHead(h);
			
			p.setTimeStamp(StringToBinary(p.getTimeStamp()));
			
			String send = p.packageOutput();
			
			//生成消息认证码
			String sc = DataStruct.Head.MD5(send);
			p.getHead().setSecurityCode(sc);
			
			return p.getHead().headOutput()+send;
		}
		
	/**
	 * 主函数
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WELCOME frame = new WELCOME();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

