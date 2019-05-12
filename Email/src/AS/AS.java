package AS;

import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import org.w3c.dom.Node;

import Client.Client;

import DataStruct.Head;
import DataStruct.Package;
import DataStruct.Ticket;

public class AS {
	
	
	private static int Number = -1; //包的初始编号
	private final int MAXNUMBER = 0; //包的最大编号
	private final static String ASID = "0001"; //ASID
	/**
	 * 分析包
	 * 根据头部，将接受到的包拆解开，各个字段存到 Package 对象中
	 * @param message
	 * @return 返回分析完成的package
	 */
	public static DataStruct.Package packAnalyse(String message){
		
		DataStruct.Package p = new DataStruct.Package();
		
		char M[] = message.toCharArray();
		String s[] = new String[28];
		for(int i = 0;i<26;i++)
		{
			s[i] = String.valueOf(M[i]);
		}
		p.setHead(new DataStruct.Head( s[0]+s[1]+s[2]+s[3] , s[4]+s[5]+s[6]+s[7] , s[8] , s[9] , s[10] , 
						s[11] , s[12] , s[13] , s[14], s[15], s[16]+s[17]+s[18]+s[19] , s[20]+s[21] , s[22]+s[23]+s[24]+s[25] ));
		
			int count[] = {0,0,0,0,0,0,0,0};
		for(int i = 26;i<message.length();i++)
		{
			if(p.getHead().getExistSessionKey().equals("1")) {
				System.out.println("注册");
				count[0]++;
				if(count[0] == 64)
					break;
			}
			else{
				if(i<30)
				p.setID(p.getID()+M[i]);
				else if(i<34)
				p.setRequestID(p.getRequestID()+M[i]);
				else 
					p.setTimeStamp(p.getTimeStamp()+M[i]);
			}
		}
		p.setTimeStamp(BinaryToString(p.getTimeStamp()));
		return p ;
	}
	/**
	 * 验证包
	 * @param p 分析完的包
	 * @return 包是正确的返回true
	 */
    public boolean verifyPackage(DataStruct.Package p){
    	//调用数据库，查看ID是否属实
    	return true;
    }    
	/**
	 * 生成会话密钥 K(c,tgs)函数
	 * 随机生成会话密钥以字符串形式返回
	 * @return  K(c,tgs)密钥
	 */
	public static String generateKeyCtgs(){
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
	 * 生成TicketTGS
	 * 按 Kc,tgs|| IDc|| ADc|| IDtgs|| TS2|| Lifetime2 格式封装 Ticket
	 * 用 Ktgs 加密
	 * 以字符串形式返回
	 * @param p 解析的包中的内容
	 * @param k Ktgs
	 * @return 返回加密后的Ticket
	 */
	public DataStruct.Ticket generateTicketTGS(DataStruct.Package p,String k){
		String lifetime = "";
		DataStruct.Ticket t = new Ticket(generateKeyCtgs(), p.getID(), p.getAuth().getClientIP(),
				p.getRequestID(), DataStruct.Package.Create_TS(), lifetime);

		//String cipher = Des.DES.encrypt(t.AuthOutput(), k);用rsa加密
		t = new Ticket("", "", "", "", "", "");
		
		//char M[] = cipher.toCharArray();
		
//		for(int i = 0;i<cipher.length();i++)
//		{
//			if(i<64) {
//				t.setSessionKey(t.getSessionKey()+M[i]);
//			}
//			else if(i<68) {
//				t.setID(t.getID()+M[i]);
//			}
//			else if(i<78){
//				t.setIP(t.getIP()+M[i]);
//			}
//			else if(i<82) {
//				t.setRequestID(t.getRequestID()+M[i]);
//			}
//			else if(i<174) {
//				t.setTimeStamp(t.getTimeStamp()+M[i]);
//			}
//			else {
//				t.setLifeTime(t.getLifeTime()+M[i]);
//			}
//		}
		//加密后放入t中
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
	 * 形成  KeyCtgs||IDtgs||时间戳||生存周期||Tickettgs 包
	 * 形成的包用 Keyc 加密
	 * 加密的密文以字符串形式返回 
	 * @param clientID client的ID
	 * @param IDtgs tgs的ID
	 * @param TicketTgs 加密后的tgs票据
	 * @return
	 */
	public String packData(String clientID,String IDtgs,DataStruct.Ticket TicketTgs){
		DataStruct.Package p = new DataStruct.Package();;
		
		String lifetime = "";
		p.setSessionKey(generateKeyCtgs());
		p.setRequestID(IDtgs);
		p.setTimeStamp(DataStruct.Package.Create_TS());
		p.setLifeTime(lifetime);
		p.setTicket(TicketTgs);
		
		if(Number > 16)
		{
			Number = -1;
		}
		Number++;
		String number = supplement(4, Integer.toBinaryString(Number));
		
		DataStruct.Head h= new DataStruct.Head(ASID,clientID,"0","1","0","1","1","1","1","0",number,"00","0000");
		p.setHead(h);
		
		//加密 用c的publick  从数据库调用
		String kc = "";
		String c = Des.DES.encrypt(p.packageOutput(), kc);
		
		return c;
	}
    /*
	 * 将string字符串编程ascii码二进制编码的string字符串
	 */
	public static String StringToBinary(String string) 
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
	
			tmp = supplement(8, Integer.toBinaryString(M1[i])); //每一位都转成了二进制
			s = s + String.valueOf(tmp); //加入string中
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
		System.out.println();
			return s;		
	}
	/**
	 * 将 Package 类型转化为二进制流数据
	 * 依次判断每一属性，进行拼接
	 * @param p 数据包
	 * @return 二进制字符串
	 */
	String packageToBiarny(DataStruct.Package p)
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
	 */
	public static boolean send(Socket socket,String message) throws IOException{
		OutputStream os=null; 	
        try {  
        	System.out.println("send");
        	 os = socket.getOutputStream();   
	            os.write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            os.flush();
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
	       	     System.out.println("receive2");
	            is = s.getInputStream();
	            //4.对获取的输入流进行的操作
	            byte [] b = new byte[20];
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
		  	System.out.println(ssss);
	            return ssss;
	} 
	     
	/**
	 * 注册
	 * AS端确认是否同意注册（显示在UI上）
	 * 同意注册则将用户ID和密码密钥放入数据库
	 * @param p
	 */
	void login(DataStruct.Package p){
	}
	/**
	 * 主函数
	 */
	public static void main(String[] args) throws Exception {

		System.out.println("---------AS打开---------");
		int port=5555;
		new Thread(Receiver.listener(port)).start();
		
		
		DataStruct.Package p = new Package();

//		int clientID = 4;
//		int tgsID = 3;
//		String TS1 = DataStruct.Package.Create_TS();
//		p = Client.clientToAS(clientID, tgsID , TS1);
//		DataStruct.Ticket TicketTgs = generateTicketTGS(p,generateKeyCtgs());
//				
//		String s = packData(p.getID(),p.getRequestID(),DataStruct.Ticket TicketTgs)
//		System.out.println(p.toString());
//	
	}
}

	class HandlerThread implements Runnable {    
        private Socket socket;    
        public HandlerThread(Socket client) {    
            socket = client;    
            new Thread(this).start();    
        }
		public void start() {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		}   