package AS;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import DataStruct.Package;
import DataStruct.Ticket;

public class AS {
	
	
	private static int Number = -1; //包的初始编号
	private final int MAXNUMBER = 0; //包的最大编号
	private final static String ASID = "0001"; //ASID
	private final static String KEYTGSAS = "0011000011110110101010001000011011100110111111101110011011001111"; //TGS与AS的密
	/**
	 * 分析包
	 * 根据头部，将接受到的包拆解开，各个字段存到 Package 对象中
	 * @param message
	 * @return 返回分析完成的package
	 */
	public static DataStruct.Package packAnalyse(String message){
		
		DataStruct.Package p = new DataStruct.Package();

		int headLength = p.getHead().headOutput().length();
		char M[] = message.toCharArray();
		String s[] = new String[headLength];
		for(int i = 0;i<headLength;i++)
		{
			s[i] = String.valueOf(M[i]);
		}
		p.setHead(new DataStruct.Head( s[0]+s[1]+s[2]+s[3] , s[4]+s[5]+s[6]+s[7] , s[8] , s[9] , s[10] , 
						s[11] , s[12] , s[13] , s[14], s[15], s[16]+s[17]+s[18]+s[19] , s[20]+s[21] , s[22]+s[23]+s[24]+s[25] ));
		
			int count[] = {0,0,0,0,0,0,0,0};
		for(int i = headLength;i<message.length();i++)
		{
			if(p.getHead().getExistSessionKey().equals("1")) {
				System.out.println("注册");
				count[0]++;
				if(count[0] == 64)
					break;
			}
			else{
				if(i<headLength+4)
				p.setID(p.getID()+M[i]);
				else if(i<headLength+8)
				p.setRequestID(p.getRequestID()+M[i]);
				else if(i<headLength+64)
					p.setTimeStamp(p.getTimeStamp()+M[i]);
				else {
						System.err.println("分析发现package长度有误，请检查！！");
					       System.exit(0);
					}
			}
		}
		p.setTimeStamp(BinaryToString(p.getTimeStamp()));
		System.out.println("分析的包："+ p);
		return p ;
	}
	/**
	 * 验证包
	 * @param p 分析完的包
	 * @return 包是正确的返回true
	 */
    public static boolean verifyPackage(DataStruct.Package p){
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
	 * Byte转String
	 * @param b
	 */
	static String BytetoString(byte[] b) {
		String s="";
		for (int i = 0; i < b.length; i++) {
			s = s+b[i];
			if (i % 8 == 7) 
				System.out.print(" ");
		}
		System.out.println();
		return s;
	}
	/**
	 * ip转binary
	 * @param ip
	 * @return
	 */
	public static String ipToBinary(InetAddress ip)
    {
//		String s = "";
//		char M[] = ip.toString().toCharArray();
		
//		for(int i = 1 ;i<M.length;i++)
//			s += M[i];
		
        byte[] b=ip.getAddress();
//        System.out.println(b[0]);
//        System.out.println(b[1]);
//        System.out.println(b[2]);
     //  s = s+b[0];
//        System.out.println(s);
        long l= b[0]<<24L & 0xff000000L|
                b[1]<<16L & 0xff0000L  |
                b[2]<<8L  &  0xff00L   |
                b[3]<<0L  &  0xffL ;
         
        return Integer.toBinaryString((int)l);
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
	public static DataStruct.Ticket generateTicketTGS(DataStruct.Package p,InetAddress inetAddress){
		String lifetime = DataStruct.Package.Create_lifeTime(10);
		String clientIP = "";
//		char M[] = inetAddress.toString().toCharArray();
//		
//		for(int i = 1 ;i<M.length;i++)
//			clientIP += M[i];
		//System.out.println(ipToBinary(inetAddress));
		clientIP = ipToBinary(inetAddress);
		DataStruct.Ticket t = new Ticket(generateKeyCtgs(), p.getID(), clientIP,
				p.getRequestID(), DataStruct.Package.Create_TS(), lifetime);

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
	 * 加密的密文以包形式返回 
	 * @param clientID client的ID
	 * @param IDtgs tgs的ID
	 * @param TicketTgs 加密后的tgs票据
	 * @return
	 */
	public static DataStruct.Package packData(String clientID,String IDtgs,DataStruct.Ticket TicketTgs){
		DataStruct.Package p = new DataStruct.Package();;
		
		p.setSessionKey(TicketTgs.getSessionKey());
		p.setRequestID(IDtgs);
		p.setTimeStamp(DataStruct.Package.Create_TS());
		p.setLifeTime(TicketTgs.getLifeTime());
		p.setTicket(TicketTgs);
		
		if(Number > 16)
		{
			Number = -1;
		}
		Number++;
		String number = supplement(4, Integer.toBinaryString(Number));
		
		DataStruct.Head h= new DataStruct.Head(ASID,clientID,"0","1","0","1","1","1","1","0",number,"00","0000");
		p.setHead(h);
		
		
		return p;
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
		System.out.println();
			return s;		
	}
	/**
	 * 将 Package 加密（包括Ticket加密）类型转化为二进制流数据
	 * 依次判断每一属性，进行拼接
	 * @param p 数据包
	 * @return 二进制字符串
	 */
	public static String packageToBinary(DataStruct.Package p)
	{ 
		String s = new String();
		String send = p.toString();
		
		String lt = p.getLifeTime();
		String ts = p.getTimeStamp();
		DataStruct.Ticket t = p.getTicket();
		
		System.out.println("打包："+p);		

		
		if(p.getHead().getExistTS().equals("1")) {
			s = StringToBinary(p.getTimeStamp());
			p.setTimeStamp(s);
		}
		
		if(p.getHead().getExistLifeTime().equals("1")) {
			s = StringToBinary(p.getLifeTime());
			p.setLifeTime(s);
		}
		if(p.getHead().getExistTicket().equals("1")) {
			DataStruct.Ticket temp = p.getTicket();
			s = StringToBinary(p.getTicket().getTimeStamp());
			temp.setTimeStamp(s);
			s = StringToBinary(p.getTicket().getLifeTime());
			temp.setLifeTime(s);
			p.setTicket(temp);
		}
		send = p.getHead().headOutput()+p.packageOutput();

		System.out.println("打包："+p);
		
		//给Ticket加密
		String cipher = Des.DES.encrypt(t.ticketOutput(), KEYTGSAS);
	//	System.out.println(cipher);
	//	t = new Ticket("", "", "", "", "", "");
		
	//	char M[] = cipher.toCharArray();
		
		//加密后放入t中
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
//			else if(){
//				t.setLifeTime(t.getLifeTime()+M[i]);
//			}
//		}
		
		//加密 用c的publick  从数据库调用
//		String kc = generateKeyCtgs();
//		String c = Des.DES.encrypt(send, kc);
		
		p.setTimeStamp(ts);
		p.setLifeTime(lt);
		p.setTicket(t);
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
		  	System.out.println("收到的包："+ssss);
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

		 System.out.println("-------AS打开----------");
		int port=5555;
		new Thread(Receiver.listener(port)).start();
	}
}
