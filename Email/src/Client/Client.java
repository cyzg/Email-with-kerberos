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
	
	private static int Number = -1; //���ĳ�ʼ���
	private final int MAXNUMBER = 0; //���������
	private final static String ASID = "0001"; //ASID
	private final static String TGSID = "0010"; 
	private final static String SERVERID = "0011"; 
	
	
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
	 * ��װ���͸�AS�İ� 
	 * ����Ҫ���͵���Ϣ
	 * ����ͷ����Ӧ��־�ֶ�
	 * @param clientID �ͻ���ID
	 * @param tgsID TGSID
	 * @param TS1 ʱ���
	 * @return ���ط�װ��ɵİ�
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
	 * client���͸�TGS�����ݰ�
	 * ����ͷ����Ӧ��־�ֶ�
	 * @param serverID clientҪ���ʵķ�����ID
	 * @param ticketTGS TGS��Ʊ��
	 * @param authTGS client���͸�TGS����֤
	 * @return ���ط�װ��ɵİ�
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
	 * ��װ���͸� server �İ�
	 * ����ͷ����Ӧ��־�ֶ�
	 * @param ticketV ServerƱ��
	 * @param authV   Server��֤
	 * @return ���ذ�����
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
	 * ������֤
	 * @param ID ClientID
	 * @param AD ClientAD
	 * @param k Client�ͽ��շ���DES��Կ
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
		//���ܺ����a��
		return a;
	}
    /*
	 * ��string�ַ������ascii������Ʊ����string�ַ���
	 */
	public static String StringoBinary(String string) 
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
	
			tmp = supplement(8, Integer.toBinaryString(M1[i]));
			
			
			//ÿһλ��ת���˶�����
			s = s + tmp; //����string��
		}
 		return s;	
	}
    /*
	 * ��string�ַ������ascii������Ʊ����string�ַ���(����ת)
	 */
	public static String StringToBinary(String string) 
	{
		char M[] = string.toCharArray();
		//System.out.println(""+M[0]+M[1]+M[2]+M[3]+"-"+M[4]+M[5]+"-"+M[6]+M[7]+" "+M[8]+M[9]+":"+M[10]+M[11]+":"+M[12]+M[13]);
		int M1[] = new int[M.length];
		String tmp = new String(); 

		String s ="";  //���ж����Ƶ��ۼ�
		for(int i=0;i<M.length;i++)
		{
			if (Character.isDigit(M[i])){  // �ж��Ƿ�������
			    M1[i] = Integer.parseInt(String.valueOf(M[i]));
			}
			else {
				System.err.println("StringתBinary��������������");
			}
	
			tmp = supplement(4, Integer.toBinaryString(M1[i]));
			//ÿһλ��ת���˶�����
			s = s + tmp; //����string��
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
			return s;		
	}
	/**
	 * �� Package ����ת��Ϊ������������
	 * �����ж�ÿһ���ԣ�����ƴ��
	 * @param p ���ݰ�
	 * @return �������ַ���
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
	 * ������Ϣ
	 * �� socket ��������з���
	 * @param socket �׽��֣���Ӧ�� socket ����
	 * @param message Ҫ���͵���Ϣ
	 * @return ���ͳɹ����� true
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
	 * ������Ϣ
	 * @param socket �����Ӧ�� socket ����,
	 * @return ���ؽ��յ�����Ϣ
	 */
	static String receive(Socket socket){
	      InputStream is=null;
	      String ssss = null;
	        try {
	            is = socket.getInputStream();
	            //4.�Ի�ȡ�����������еĲ���
	            
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
	            //5.�ر���Ӧ�����Լ�Socket,ServerSocket�Ķ���
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
	 * ���ķ���
	 * ���յ�����Ϣ�����ͷ��
	 * ����ͷ����־λ���н������ݲ���
	 * ��Ӧ���ݶηŵ� package ���Զ�Ӧ����
	 * @param message ���ܵ���Ϣ
	 * @return ���ض�Ӧ������
	 */
	DataStruct.Package packageAnalyse(String message){
		return new DataStruct.Package();
	}
	/**
	 * ����Kc
	 * �� clientID �� client �� pwd �Ĺ�ϣֵ�����ϣ�����У����� 8 ���ֽڵ���Կ
	 * @param p
	 * @return ���ַ�����ʽ������Կ
	 */
	public String generateKeyc(DataStruct.Package p){
		return "";
	}
	/**
	 * ע��
	 * �ͻ��ڿͻ������� ID������
	 * ���ҽ�����͹�Կ��AS�Ĺ�Կ����
	 * @return ���ܺ�İ�
	 */
	 DataStruct.Package login(){
		 return new DataStruct.Package();
	}
	/**
	 * ��Server�����Ӻ��ͨ��
	 */
	void connect()
	{  
	}
	
	/**
	 * ������
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
		System.out.println("���ͣ�"+p.getHead().headOutput()+packageToBinary(p));
        	String message =p.getHead().headOutput()+packageToBinary(p);
        	String s = "";
        	if(send(socket,message)) {
        		s = receive(socket);
        		System.out.println("�յ�"+s);
        	}
	}

}

