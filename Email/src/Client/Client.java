  package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import DataStruct.Authenticator;
import DataStruct.Ticket;

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
	 static DataStruct.Package clientToAS(int clientID,String tgsID,String TS1){ 
		DataStruct.Package p= new DataStruct.Package();
		
		String clientID1 = supplement(4, Integer.toBinaryString(clientID));
		
		p.setID(clientID1); 
		p.setRequestID(tgsID);
		p.setTimeStamp(TS1);
		
		if(Number > 16)
		{
			Number = -1;
		}
		Number++;
		String number = supplement(4, Integer.toBinaryString(Number));
		String securityCode = "00";
		//String securityCode = StringToBinary(Integer.toBinaryString(p.hashCode()));
		DataStruct.Head h= new DataStruct.Head(clientID1,ASID,"0","0","1","1","1","0","0","0",number,securityCode,"0000");
		p.setHead(h);
		
		return p;
	}
	
	/**
	 * client���͸�TGS�����ݰ�
	 * ����ͷ����Ӧ��־�ֶ�
	 * @param tgsid clientҪ���ʵķ�����ID
	 * @param ticketTGS TGS��Ʊ��
	 * @param authTGS client���͸�TGS����֤
	 * @return ���ط�װ��ɵİ�
	 */
	DataStruct.Package clientToTGS(int clientID,String serverID, DataStruct.Ticket ticketTGS, DataStruct.Authenticator authTGS){
		DataStruct.Package p= new DataStruct.Package();

		String clientID1 = supplement(4, Integer.toBinaryString(clientID));
		
		p.setRequestID(serverID);
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
//		String cipher = Des.DES.encrypt(a.AuthOutput(), k);
//		a.setClientID("");
//		a.setClientIP("");
//		a.setTimeStamp("");
//		char M[] = cipher.toCharArray();
//		
//		for(int i = 0;i<cipher.length();i++)
//		{
//			if(i<4) {
//				a.setClientID(a.getClientID()+M[i]);
//			}
//			else if(i<14) {
//				a.setClientIP(a.getClientIP()+M[i]);
//			}
//			else if(i<66){
//				a.setTimeStamp(a.getTimeStamp()+M[i]);
//			}
//		}
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
		
		send = p.getHead().headOutput()+p.packageOutput();
		p.setLifeTime(lt);
		p.setTimeStamp(ts);
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
	        socket.shutdownOutput();  
        	os.flush();
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
	      String ssss = "";
	        try {
	            is = socket.getInputStream();
	            //4.�Ի�ȡ�����������еĲ���
	            
	            byte [] b = new byte[20];
	            int len;
	            while((len = is.read(b))!= -1){
	                String str = new String(b,0,len);
	                ssss+=str;
	            }
        		System.out.println("�յ�"+ssss);
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }finally{
	            //5.�ر���Ӧ�����Լ�Socket,ServerSocket�Ķ���

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
	 * ���ķ���
	 * ���յ�����Ϣ�����ͷ��
	 * ����ͷ����־λ���н������ݲ���
	 * ��Ӧ���ݶηŵ� package ���Զ�Ӧ����
	 * @param message ���ܵ���Ϣ
	 * @param k1 Package��Կ
	 * @return ���ض�Ӧ������
	 */
	static DataStruct.Package packageAnalyse(String message,String k1){
		System.out.println("-----��ʼ������-----");
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
			
		
		if(p.getHead().getExistSessionKey().equals("0")) {
				System.out.println("����");
			for(int i = headLength;i<message.length();i++)
			{	
			}
		}
		else if(p.getHead().getExistLifeTime().equals("1")) {
			//˵����AS����
			//package���� ��t1
			String m = message;
			DataStruct.Ticket ticket = new Ticket();
			char C[] = m.toCharArray();
			for(int i = headLength;i<m.length();i++)
			{	
				if(i<headLength+64)
					p.setSessionKey(p.getSessionKey()+C[i]);
				else if(i<headLength+68)
					p.setRequestID(p.getRequestID()+C[i]);
				else if(i<headLength+68+56)
					p.setTimeStamp(p.getTimeStamp()+C[i]);
				else if(i<headLength+68+56+56)
					p.setLifeTime(p.getLifeTime()+C[i]);
				else if(i<headLength+68+56+56+216){
					if(i<headLength+68+56+56+64)
						ticket.setSessionKey(ticket.getSessionKey()+C[i]);
					else if(i<headLength+68+56+56+68)
						ticket.setID(ticket.getID()+C[i]);
					else if(i<headLength+68+56+56+99)
						ticket.setIP(ticket.getIP()+C[i]);
					else if(i<headLength+68+56+56+103)
						ticket.setRequestID(ticket.getRequestID()+C[i]);
					else if(i<headLength+68+56+56+103+56)
						ticket.setTimeStamp(ticket.getTimeStamp()+C[i]);
					else 
						ticket.setLifeTime(ticket.getLifeTime()+C[i]);
				}
				else {
						System.err.println("��������AS��������package�����������飡��");
					       System.exit(0);
				}
			}	

			String string = p.getTimeStamp();
			p.setTimeStamp(BinaryToString(string));
			
			p.setLifeTime(BinaryToString(p.getLifeTime()));
			
//			ticket.setTimeStamp(BinaryToString(ticket.getTimeStamp()));
//			ticket.setLifeTime(BinaryToString(ticket.getLifeTime()));
			p.setTicket(ticket);
		}
		else if(p.getHead().getExistTicket().equals("1")){
			for(int i = headLength;i<message.length();i++)
			{
					if(i<headLength+4)
					p.setID(p.getID()+M[i]);
					else if(i<headLength+8)
					p.setRequestID(p.getRequestID()+M[i]);
					else if(i<headLength+64)
						p.setTimeStamp(p.getTimeStamp()+M[i]);
					else {
							System.err.println("��������TGS��������package�����������飡��");
						       System.exit(0);
						}
				}
		}
		//p.setTimeStamp(BinaryToString(p.getTimeStamp()));
		System.out.println("�����İ���"+ p);
		return p ;
	}
	
	//�����ܵ�
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
    	String clientIP = "";
		String TS1 = DataStruct.Package.Create_TS();
		
		p = clientToAS(clientID, TGSID , TS1);

		System.out.println("����AS�İ�"+p.toString());
		System.out.println("-------����AS--------");
        System.out.println("client on");
        
    	Socket socket = new Socket("localhost",5555);
    	System.out.println("���ͣ�"+packageToBinary(p));
        String message =packageToBinary(p);
        String s = "";
//        if(send(socket,message)) {
//        	s = receive(socket);
//        	socket.close();
//        }
        s = "0001010001011110001000000010111010100100101011110011010001000111000000101111001011011111100010001000000001100100000101000101000010000000010010010000000010000000011001000001010001010000100000001000100100000010111010100100101011110011010001000111000000101111001011011111100100111111100000000000000000000000100100010000000011001000001010001010000100000000100100100000000100000000110010000010100010100001000000010001001000000";
    	
        

		System.out.println("-------����TGS--------");
     //   socket = new Socket("localhost",5555);
    	
    	clientIP = DataStruct.Package.ipToBinary(socket.getInetAddress());
    	
    	DataStruct.Package p2= packageAnalyse(s, null);
    	Client c = new Client();
    	p = c.clientToTGS(clientID, c.SERVERID, p.getTicket(), c.generateAuth(p.getID(),clientIP,p2.getSessionKey()));
    	System.out.println(p);
//      if(send(socket,message)) {
//    	s = receive(socket);
//    	socket.close();
//    }
	}
}

