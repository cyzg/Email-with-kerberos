package TGS;

import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import DataStruct.Package;
import DataStruct.Ticket;

public class TGS {
	

	private static int Number = -1; //包的初始编号
	private final int MAXNUMBER = 0; //包的最大编号
	private final static String TGSID = "0010"; 
	/**
	 * 分析包
	 * 根据头部，将接受到的包拆解开，各个字段存到 Package 对象中
	 * @param message
	 * @return 返回分析完成的package
	 */
	public DataStruct.Package packAnalyse(String message){
		return new DataStruct.Package();
	}
	/**
	 * 传入分离开的 Tickettgs 密文
	 * 用 Ktgs 解密
	 * @param Tickettgs
	 * @return 返回解密的票据
	 */
	public DataStruct.Ticket AnalyseTicket(String Tickettgs){
	
		return new Ticket();
	}
	/**
	 * 解密 Authenticator,验证票据
	 * 用 Kc,tgs 解开 Authenticator
	 * 内容和 Ticket 对照验证
	 * 返回布尔验证结果
	 * @param authen
	 * @param ticketTGS
	 * @return 验证正确返回true
	 */
	public boolean checkIdentity(DataStruct.Authenticator authen,DataStruct.Ticket ticketTGS){
	
		return true;
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
	 * 生成Ticket
	 * 按 Kc,v||IDC||ADC|| IDv||TS4||Lifetime4 格式封装 Ticket
	 * 用 Kv 加密
	 * 以字符串形式返回
	 * @param p 解析的包中的内容
	 * @return 返回加密后的Ticket
	 */
	public DataStruct.Ticket generateTicketV(DataStruct.Package p){
		String lifetime = "";
		DataStruct.Ticket t = new Ticket(generateKeyCV(), p.getID(), p.getAuth().getClientIP(),
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
	 * 形成 Kc,v|| IDV|| TS4|| Ticketv 包
	 * 形成的包用  KeyCtgs加密
	 * 加密的密文以字符串形式返回 
	 * @param IDv v的ID
	 * @param Ticketv 加密后的v票据
	 * @return
	 */
	public String packData(String clientID,String IDv,DataStruct.Ticket Ticketv){
		DataStruct.Package p = new DataStruct.Package();;
		
		p.setSessionKey(generateKeyCV());
		p.setRequestID(IDv);
		p.setTimeStamp(DataStruct.Package.Create_TS());
		p.setTicket(Ticketv);
		
		if(Number > 16)
		{
			Number = -1;
		}
		Number++;
		String number = supplement(4, Integer.toBinaryString(Number));
		
		DataStruct.Head h= new DataStruct.Head(TGSID,clientID,"0","1","0","1","1","1","1","0",number,"00","0000");
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
		
		
		if(p.getHead().getExistTS() == "1") {
			s = StringToBinary(p.getTimeStamp());
			p.setTimeStamp(s);
		}
		
		if(p.getHead().getExistLifeTime() == "1") {
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
	public static void main(String[] args) {
		
	}
}
