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
	 * ������
	 * ����ͷ���������ܵ��İ���⿪�������ֶδ浽 Package ������
	 * @param message
	 * @return ���ط�����ɵ�package
	 */
	public DataStruct.Package packAnalyse(String message){
		return new DataStruct.Package();
	}
	/**
	 * ��֤��
	 * @param p ������İ�
	 * @return ������ȷ�ķ���true
	 */
    public boolean verifyPackage(DataStruct.Package p){
    	return true;
    }
	
	/**
	 * ���ɻỰ��Կ K(c,tgs)����
	 * ������ɻỰ��Կ���ַ�����ʽ����
	 * @return  K(c,tgs)��Կ
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
	 * ����TicketTGS
	 * �� Kc,tgs|| IDc|| ADc|| IDtgs|| TS2|| Lifetime2 ��ʽ��װ Ticket
	 * �� Ktgs ����
	 * ���ַ�����ʽ����
	 * @param p �����İ��е�����
	 * @return ���ؼ��ܺ��Ticket
	 */
	public String generateTicketTGS(DataStruct.Package p){
		
		
		return "";
	}
	/**
	 * �������
	 * �γ�  KeyCtgs||IDtgs||ʱ���||��������||Tickettgs ��
	 * �γɵİ��� Keyc ����
	 * ���ܵ��������ַ�����ʽ���� 
	 * @param IDtgs tgs��ID
	 * @param TicketTgs ���ܺ��tgsƱ��
	 * @return
	 */
	public String packData(String IDtgs,String TicketTgs){
		return "";
	}
	/**
	 * �� Package ����ת��Ϊ������������
	 * �����ж�ÿһ���ԣ�����ƴ��
	 * @param p ���ݰ�
	 * @return �������ַ���
	 */
	String packageToBiarny(Package p)
	{ 
		return "";
	}
	/**
	 * ������Ϣ
	 * �� socket ��������з���
	 * @param socket �׽��֣���Ӧ�� socket ����
	 * @param message Ҫ���͵���Ϣ
	 * @return ���ͳɹ����� true
	 */
	static boolean send(Socket socket,String message){
    	OutputStream os=null;
        try {  
              os = socket.getOutputStream();   
            os.write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            //4.�ر���Ӧ������Socket����
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
	 * ������Ϣ
	 * @param socket �����Ӧ�� socket ����,
	 * @return ���ؽ��յ�����Ϣ
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
            //4.�Ի�ȡ�����������еĲ���
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
            //5.�ر���Ӧ�����Լ�Socket,ServerSocket�Ķ���
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
	 * ע��
	 * AS��ȷ���Ƿ�ͬ��ע�ᣨ��ʾ��UI�ϣ�
	 * ͬ��ע�����û�ID��������Կ�������ݿ�
	 * @param p
	 */
	void login(DataStruct.Package p){
	}
	/**
	 * ������
	 */
	public static void main(String[] args) throws Exception {
		//as�˿�9090 c�˿�9080
		receive();
		//String testip=InetAddress.getLocalHost().getHostAddress();//���Ի�����IP
	//	Socket socketasc = new Socket(testip,9080);//AS-C��socket
	//	String message="AS����C�Ĳ���";
	//	send(socketasc,message);
		System.out.println(receive());
		
//		String s = generateKeyCtgs();
//		System.out.println(s);
		
		
	}
}
