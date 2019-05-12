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
	
	
	private static int Number = -1; //���ĳ�ʼ���
	private final int MAXNUMBER = 0; //���������
	private final static String ASID = "0001"; //ASID
	/**
	 * ������
	 * ����ͷ���������ܵ��İ���⿪�������ֶδ浽 Package ������
	 * @param message
	 * @return ���ط�����ɵ�package
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
				System.out.println("ע��");
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
	 * ��֤��
	 * @param p ������İ�
	 * @return ������ȷ�ķ���true
	 */
    public boolean verifyPackage(DataStruct.Package p){
    	//�������ݿ⣬�鿴ID�Ƿ���ʵ
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
	 * @param k Ktgs
	 * @return ���ؼ��ܺ��Ticket
	 */
	public DataStruct.Ticket generateTicketTGS(DataStruct.Package p,String k){
		String lifetime = "";
		DataStruct.Ticket t = new Ticket(generateKeyCtgs(), p.getID(), p.getAuth().getClientIP(),
				p.getRequestID(), DataStruct.Package.Create_TS(), lifetime);

		//String cipher = Des.DES.encrypt(t.AuthOutput(), k);��rsa����
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
		//���ܺ����t��
		return t;
	}
	/**
	 * ��str���뵽nλ����λд0
	 * @param n 
	 * @param str Ҫ������ַ���
	 * @return
	 */
	public static String supplement(int n,String str){ 
		if(n>str.length()) {
			int sl=str.length();//stringԭ����
			for(int i=0;i<(n-sl);i++) {
				str="0"+str;
			}
		}
		return str;
	}
	/**
	 * �������
	 * �γ�  KeyCtgs||IDtgs||ʱ���||��������||Tickettgs ��
	 * �γɵİ��� Keyc ����
	 * ���ܵ��������ַ�����ʽ���� 
	 * @param clientID client��ID
	 * @param IDtgs tgs��ID
	 * @param TicketTgs ���ܺ��tgsƱ��
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
		
		//���� ��c��publick  �����ݿ����
		String kc = "";
		String c = Des.DES.encrypt(p.packageOutput(), kc);
		
		return c;
	}
    /*
	 * ��string�ַ������ascii������Ʊ����string�ַ���
	 */
	public static String StringToBinary(String string) 
	{
		int length = string.length();
		char M[] = string.toCharArray();
		//System.out.println(""+M[0]+M[1]+M[2]+M[3]+"-"+M[4]+M[5]+"-"+M[6]+M[7]+" "+M[8]+M[9]+":"+M[10]+M[11]+":"+M[12]+M[13]);
		int M1[] = new int[M.length];
		String tmp = new String(); 

		String s ="";  //���ж����Ƶ��ۼ�
		for(int i=0;i<M.length;i++)
		{
			M1[i] = M[i]-'\0'; //ÿһλ����int�ˣ����ڿ�ʼת��������
	
			tmp = supplement(8, Integer.toBinaryString(M1[i])); //ÿһλ��ת���˶�����
			s = s + String.valueOf(tmp); //����string��
		}
 		return s;	
	}
	/**
	 * ������תʮ����
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

		String s ="";  //���ж����Ƶ��ۼ�
		for(int i1=0;i1<length;i1++)
		{
			M[i1/4] = M[i1/4]+C[i1];
			if(i1%4 == 3) {
				M1[i1/4] = Integer.parseInt(M[i1/4],2);

				s = s + M1[i1/4]; //����string��
			}
		}
		System.out.println();
			return s;		
	}
	/**
	 * �� Package ����ת��Ϊ������������
	 * �����ж�ÿһ���ԣ�����ƴ��
	 * @param p ���ݰ�
	 * @return �������ַ���
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
	 * ������Ϣ
	 * �� socket ��������з���
	 * @param socket �׽��֣���Ӧ�� socket ����
	 * @param message Ҫ���͵���Ϣ
	 * @return ���ͳɹ����� true
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
	 * ������Ϣ
	 * @param socket �����Ӧ�� socket ����,
	 * @throws IOException 
	 */
 
	public static String receive(Socket s) throws IOException{
		String ssss="";
		 InputStream is=null; 
		  try { 
	       	     System.out.println("receive2");
	            is = s.getInputStream();
	            //4.�Ի�ȡ�����������еĲ���
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

		System.out.println("---------AS��---------");
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