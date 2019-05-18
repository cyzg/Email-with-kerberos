package TGS;

import java.io.IOException;
import java.net.Socket;

import TGS.UI.AP;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

import DataStruct.Authenticator;
import DataStruct.Package;
import DataStruct.Ticket;
import Des.DES;

public class TGS {
	

	private static int Number = -1; //包的初始编号
	private final int MAXNUMBER = 0; //包的最大编号
	private final static String TGSID = "1110"; 
	private final static String[] SELFKEY = {"865703290362069039664574527025207637385565054952203601297173618790874525723" , "259555164968416688158625327465001826979021157149123374785762616336430795281"}; //TGS私钥
	private final static String[] KEYV = {"1197109859790913087686830134943274236719072137378604521650533072095054080831" , "3889"}; //TGS私钥

	/**
	 * 分析包
	 * 根据头部，将接受到的包拆解开，各个字段存到 Package 对象中
	 * @param message
	 * @return 返回分析完成的package
	 */
	public DataStruct.Package packAnalyse(String message){
		System.out.println("-----开始解析包-----");
		DataStruct.Package p = new DataStruct.Package();
		DataStruct.Ticket ticket = new Ticket();
		DataStruct.Authenticator auth = new Authenticator();
		
		//分离出头部
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
		System.out.println(p.getHead());
		String pack = message.replaceFirst(p.getHead().headOutput(), "");
		
		//验证消息验证码
		if(DataStruct.Head.MD5(pack).equals(p.getHead().getSecurityCode())) {

			//分离出包内容和Ticket和Auth
			String messageT = "" , messageA = "";
			int tic = Integer.parseInt(BinaryToString(p.getHead().getExpend()));//加密后tic的长度
			for(int i = headLength;i<message.length();i++)
			{
				if(i<headLength+4)
					p.setRequestID(p.getRequestID()+M[i]);
				else if(i<headLength+4+tic){
					messageT = messageT + M[i];
				}
				else {
					messageA = messageA + M[i];
				}
			
			}
	
			//Ticket解密 用tgs私钥
			RSA.rsa rsa = new RSA.rsa();
			System.out.println("密文:"+messageT);	
			messageT = rsa.decrypt(messageT, SELFKEY);
			System.out.println("明文:"+messageT);
			char T[] = messageT.toCharArray();
			//解Ticket
			for(int i = 0;i < messageT.length();i++)
			{				
				if(i < 64)
					ticket.setSessionKey(ticket.getSessionKey()+T[i]);
				else if(i<68)
					ticket.setID(ticket.getID()+T[i]);
				else if(i<100)
					ticket.setIP(ticket.getIP()+T[i]);
				else if(i<104)
					ticket.setRequestID(ticket.getRequestID()+T[i]);
				else if(i<104+56)
					ticket.setTimeStamp(ticket.getTimeStamp()+T[i]);
				else if(i<104+56+56)
					ticket.setLifeTime(ticket.getLifeTime()+T[i]);
				else {
						System.err.println("分析发现TGS收到的package长度有误，请检查！！");
					       System.exit(0);
					}
			}
			
			//Auth解密，用Ticket中的sessionkey解密
			messageA = DES.decrypt(messageA, ticket.getSessionKey());
			char A[] = messageA.toCharArray();
			for(int i = 0;i < messageA.length();i++)
			{
				if(i<4)
					auth.setClientID(auth.getClientID()+A[i]);
				else if(i<36)
					auth.setClientIP(auth.getClientIP()+A[i]);
				else if(i<36+56)
					auth.setTimeStamp(auth.getTimeStamp()+A[i]);
				else {
					System.err.println("分析发现TGS收到的package长度有误，请检查！！");
				       System.exit(0);
				}
		}
			ticket.setTimeStamp(BinaryToString(ticket.getTimeStamp()));
			ticket.setLifeTime(BinaryToString(ticket.getLifeTime()));
			auth.setTimeStamp(BinaryToString(auth.getTimeStamp()));
			p.setTicket(ticket);
			p.setAuth(auth);
			
			System.out.println("分析的包："+ p);
			return p;
		}
		else {
			System.err.println("TGS收到的包有误");
			return null;
		}
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
    /*
	 * 将string字符串按位转二进制编码的string字符串(数字转)
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
	 * 验证票据
	 * 用 Kc,tgs 解开 Authenticator
	 * 内容和 Ticket 对照验证
	 * 返回布尔验证结果
	 * @param authen
	 * @param ticketTGS
	 * @return 验证正确返回true
	 */
	public boolean checkIdentity(DataStruct.Package p){
		if(p.getTicket().getRequestID().equals(TGSID))
			if(p.getAuth().getClientID().equals(p.getTicket().getID()))
				if(p.getAuth().getClientIP().equals(p.getTicket().getIP()))
					if(DataStruct.Package.isalive(p.getTicket().getLifeTime()))
					return true;
					else
					{
						System.err.println("TicketTGS已过期");
						return false;
					}
				else
				{
					System.err.println("AuthIP与TicketIP不符");
					return false;
				}
			else
			{
				System.err.println("AuthID与TicketID不符");
				return false;
			}
		else 
		{
			System.err.println("Ticket申请ID与不是TGSID");
			return false;
		}
	} 
	/**
	 * 生成会话密钥 Key(c,v)
	 * 随机生成会话密钥 Kcv
	 * @return 字符串形式密钥
	 */
	public String generateKeyCV(){
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
	/**
	 * 生成Ticket
	 * 按 Kc,v||IDC||ADC|| IDv||TS4||Lifetime4 格式封装 Ticket
	 * 用 Kv 加密
	 * 以字符串形式返回
	 * @param p 解析的包中的内容
	 * @return 返回加密后的Ticket
	 */
	public DataStruct.Ticket generateTicketV(DataStruct.Package p,InetAddress inetAddress){
		System.out.println("-----正在生成Ticket-----");
		String lifetime = DataStruct.Package.Create_lifeTime(2);
		String clientIP = ipToBinary(inetAddress);
		DataStruct.Ticket t = new Ticket(generateKeyCV(), p.getHead().getSourceID(), clientIP,
				p.getRequestID(), DataStruct.Package.Create_TS(), lifetime);
		System.out.println("生成的Ticket："+t);
		
		return t;
	}

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
	 * 打包数据
	 * 形成 Kc,v|| IDV|| TS4|| Ticketv 包
	 * 形成的包用  KeyCtgs加密
	 * 加密的密文以字符串形式返回 
	 * @param IDv v的ID
	 * @param Ticketv 加密后的v票据
	 * @return
	 */
	public DataStruct.Package packData(String clientID,String IDv,DataStruct.Ticket Ticketv){
		DataStruct.Package p = new DataStruct.Package();;
		
		p.setSessionKey(Ticketv.getSessionKey());
		p.setRequestID(IDv);
		p.setTimeStamp(DataStruct.Package.Create_TS());
		p.setTicket(Ticketv);
		
		if(Number > 16)
		{
			Number = -1;
		}
		Number++;
		String number = supplement(4, Integer.toBinaryString(Number));
		String securityCode = DataStruct.Head.zero(128);
		
		DataStruct.Head h= new DataStruct.Head(clientID,TGSID,"0","1","0","1","1","0","1","0",number,securityCode,"0000000000000000");
		p.setHead(h);
		
		return p;
	}
	/** 
	 * 将 Package 类型转化为二进制流数据
	 * 依次判断每一属性，进行拼接
	 * @param p 数据包
	 * @return 二进制字符串
	 */
	String packageToBiarny(DataStruct.Package p , String k)
	{ 
		RSA.rsa rsa = new RSA.rsa();
		DataStruct.Ticket ticket = p.getTicket();
		String s = new String();
		String send = p.toString();
		String lt = p.getLifeTime();
		String ts = p.getTimeStamp();

		System.out.println("打包："+p);		
		
		if(p.getHead().getExistTS() == "1") {
			s = StringToBinary(p.getTimeStamp());
			p.setTimeStamp(s);
		}
		
		if(p.getHead().getExistLifeTime() == "1") {
			s = StringToBinary(p.getLifeTime());
			p.setLifeTime(s);
		}
		//把t转为二进制
		if(p.getHead().getExistTicket().equals("1")) {
			DataStruct.Ticket t = p.getTicket();
			t.setTimeStamp(StringToBinary(p.getTicket().getTimeStamp()));
			t.setLifeTime(StringToBinary(p.getTicket().getLifeTime()));	
			//给Ticket加密
			String cipher = rsa.encrypt(t.ticketOutput(), KEYV);
			t = new Ticket("", "", "", "", "", "");
			
			char M[] = cipher.toCharArray();
			
			//加密后放入t中
			for(int i = 0;i<cipher.length();i++)
			{
				if(i<64) {
					t.setSessionKey(t.getSessionKey()+M[i]);
				}
				else if(i<68) {
					t.setID(t.getID()+M[i]);
				}
				else if(i<68+32){
					t.setIP(t.getIP()+M[i]);
				}
				else if(i<68+36) {
					t.setRequestID(t.getRequestID()+M[i]);
				}
				else if(i<68+36+56) {
					t.setTimeStamp(t.getTimeStamp()+M[i]);
				}
				else {
					t.setLifeTime(t.getLifeTime()+M[i]);
				}
			}
			p.setTicket(t);
		}
		String tic = Integer.toString((p.getTicket().ticketOutput().length())); //tickettgs加密后的长度
		tic = supplement(16, StringToBinary(tic));
		p.getHead().setExpend(tic);
		
		//加密 用Kc,tgs
		String c = DES.encrypt(p.packageOutput(), k);//加密后的包

		//生成消息认证码
		String sc = DataStruct.Head.MD5(c);
		p.getHead().setSecurityCode(sc);
		
		send = p.getHead().headOutput()+c;
	
		p.setTimeStamp(ts);
		p.setLifeTime(lt);
		p.setTicket(ticket);
		return send;
	}
	/**
	 * 发送消息
	 * 用 socket 输出流进行发送
	 * @param socket 套接字，对应的 socket 对象
	 * @param message 要发送的信息
	 * @return 发送成功返回 true
	 * @throws IOException 
	 */
	public static boolean send(Socket socket,String message) throws IOException{
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
	 * @throws IOException 
	 */
 
	public static String receive(Socket s) throws IOException{
		String ssss="";
		 InputStream is=null; 
		  try { 
	       	    
	            is = s.getInputStream();
	            //4.对获取的输入流进行的操作
	            byte [] b = new byte[100];
	            int len;
	            while((len = is.read(b))!=-1){
	                String str = new String(b,0,len);
	                ssss+=str;
	            }
	           
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }finally{
	        	s.shutdownInput();
	            }		
		  	System.out.println("收到的包："+ssss);
	            return ssss;
	} 
	public static void main(String[] args) throws IOException, InterruptedException {
		 System.out.println("-------TGS打开----------");
		 AP ui = new AP();
		 ui.setVisible(true);
		 int port=5555;
		 TGSReceiver r = new TGSReceiver();
		 new Thread(r.listener(port,ui)).start();
	}
}
