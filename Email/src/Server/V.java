package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import DataStruct.Authenticator;
import DataStruct.Ticket;
import Des.DES;
import TGS.TGSReceiver;

public class V {

	private static int Number = -1; //包的初始编号
	private final int MAXNUMBER = 0; //包的最大编号
	private final static String SERVERID = "0011"; 
	private final static String[] SELFKEY = {"1197109859790913087686830134943274236719072137378604521650533072095054080831" , "1119539357176536616075341013316710825069096752350867012830658571383977123009"}; //TGS私钥

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
						s[11] , s[12] , s[13] , s[14], s[15], s[16]+s[17]+s[18]+s[19] , s[20]+s[21] ,
						s[22]+s[23]+s[24]+s[25]+s[26]+s[27]+s[28]+s[29]+s[30]+s[31]+s[32]+s[33]+s[34]+s[35]+s[36]+s[37] ));
	
		System.out.println(p.getHead());
		//分离出包内容和Ticket和Auth
		String messageT = "" , messageA = "";
		int tic = Integer.parseInt(BinaryToString(p.getHead().getExpend()));//加密后tic的长度
		for(int i = headLength;i<message.length();i++)
		{
			if(i<headLength+tic){
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
					System.err.println("分析发现V收到的package长度有误，请检查！！");
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
				System.err.println("分析发现V收到的package长度有误，请检查！！");
			       System.exit(0);
			}
	}
		ticket.setTimeStamp(BinaryToString(ticket.getTimeStamp()));
		ticket.setLifeTime(BinaryToString(ticket.getLifeTime()));
		auth.setTimeStamp(BinaryToString(auth.getTimeStamp()));
		p.setTicket(ticket);
		p.setAuth(auth);
		
		System.out.println("分析的包："+ p);
		return p ;
	}

	/**
	 * 解密 Authenticator,验证票据
	 * 用 Kc,v 解开 Authenticator
	 * 内容和 Ticket 对照验证
	 * 返回布尔验证结果
	 * @param authen
	 * @param ticketTGS
	 * @return 验证正确返回true
	 */
	public boolean checkIdentity(DataStruct.Package p){
		if(p.getTicket().getRequestID().equals(SERVERID))
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
			System.err.println("Ticket申请ID与不是SERVERID");
			return false;
		}
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
	 * 打包传给client
	 * @param clientID
	 * @param TS
	 * @return
	 */
	public String packData(String clientID,String TS,String k){
		DataStruct.Package p = new DataStruct.Package();;
		
		p.setTimeStamp(DataStruct.Package.Create_lifeTime(TS, 1));
		
		if(Number > 16)
		{
			Number = -1;
		}
		Number++;
		String number = supplement(4, Integer.toBinaryString(Number));
		
		DataStruct.Head h= new DataStruct.Head(clientID,SERVERID,"0","0","0","0","1","0","0","0",number,"00","0000000000000000");
		p.setHead(h);
		
		p.setTimeStamp(StringToBinary(p.getTimeStamp()));
		
		//des加密
		String send = DES.encrypt(p.getTimeStamp(), k);
		
		return p.getHead().headOutput()+send;
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
	/**
	 * 与Client端连接后的通信
	 */
	public void connect() {
	}
	
	public static void main(String[] args) throws IOException, InterruptedException{
		 System.out.println("-------V打开----------");
		int port=5555;
		new Thread(VReceiver.listener(port)).start();
	}
}
