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

public class AS {
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
	 * 验证包
	 * @param p 分析完的包
	 * @return 包是正确的返回true
	 */
    public boolean verifyPackage(DataStruct.Package p){
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
	 * @return 返回加密后的Ticket
	 */
	public String generateTicketTGS(DataStruct.Package p){
		
		
		return "";
	}
	/**
	 * 打包数据
	 * 形成  KeyCtgs||IDtgs||时间戳||生存周期||Tickettgs 包
	 * 形成的包用 Keyc 加密
	 * 加密的密文以字符串形式返回 
	 * @param IDtgs tgs的ID
	 * @param TicketTgs 加密后的tgs票据
	 * @return
	 */
	public String packData(String IDtgs,String TicketTgs){
		return "";
	}
	/**
	 * 将 Package 类型转化为二进制流数据
	 * 依次判断每一属性，进行拼接
	 * @param p 数据包
	 * @return 二进制字符串
	 */
	String packageToBiarny(Package p)
	{ 
		return "";
	}
	/**
	 * 发送消息
	 * 用 socket 输出流进行发送
	 * @param socket 套接字，对应的 socket 对象
	 * @param message 要发送的信息
	 * @return 发送成功返回 true
	 */
	static boolean send(Socket socket,String message){
    	OutputStream os=null;
        try {  
              os = socket.getOutputStream();   
            os.write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            //4.关闭相应的流和Socket对象
            if(os!=null){
                try {
                    os.close();
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
		return true;
	}
	/**
	 * 接收消息
	 * @param socket 传入对应的 socket 对象,
	 * @return 返回接收到的消息
	 */
	static String receive()throws IOException{
		ServerSocket ss=null;
        Socket s=null;
        String ssss=null;
      InputStream is=null; 
      System.out.println("receive");
        try { 
        	ss = new ServerSocket(9090); 
        	s = ss.accept();
        	 System.out.println("receive");
        	new Thread(receive()).start();
            is = s.getInputStream();
            //4.对获取的输入流进行的操作
            byte [] b = new byte[20];
            int len;
            while((len = is.read(b))!=-1){
                String str = new String(b,0,len);
                ssss+=str;
            }
           // System.out.println(ssss);
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
            if(s!=null){
                try {
                    s.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(ss!=null){
                try {
                    ss.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
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
		//as端口9090 c端口9080
		receive();
		//String testip=InetAddress.getLocalHost().getHostAddress();//测试机本机IP
	//	Socket socketasc = new Socket(testip,9080);//AS-C的socket
	//	String message="AS发给C的测试";
	//	send(socketasc,message);
		System.out.println(receive());
		
//		String s = generateKeyCtgs();
//		System.out.println(s);
		
		
	}
}
