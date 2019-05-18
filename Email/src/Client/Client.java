  package Client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Enumeration;

import com.mysql.cj.DataStoreMetadata;

import Client.UI.LOG;
import DataStruct.Authenticator;
import DataStruct.Ticket;
import Des.DES;

public class Client {
	
	private static int Number = -1; //���ĳ�ʼ���
	private final int MAXNUMBER = 0; //���������
	private final static String ASID = "1101"; //ASID
	public final static String TGSID = "1110"; 
	public final static String SERVERID = "1111"; 
	private final static String[] selfK ={"3096589494327972966542767555645488415857410521298179560751893624567975523927775168085739664949238616280271893353946263715523651672294362843822766996968340714023382235747900221065977" , "1152163794881094595676879571359995304125912323044089952277703799112846640042039256420690483427161040887459792478554485040196767218736825329254102072887596847184101841933983341452289"}; //client˽Կ
	 
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
	 public static DataStruct.Package clientToAS(String clientID,String tgsID){ 
		DataStruct.Package p= new DataStruct.Package();
		
		p.setID(clientID); 
		p.setRequestID(tgsID);
		p.setTimeStamp(DataStruct.Package.Create_TS());
		
		if(Number > 16)
		{
			Number = -1;
		}
		Number++;
		String number = supplement(4, Integer.toBinaryString(Number));
		String securityCode = DataStruct.Head.zero(128);
		DataStruct.Head h= new DataStruct.Head(ASID,clientID,"0","0","1","1","1","0","0","0",number,securityCode,"0000000000000000");
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
	public static DataStruct.Package clientToTGS(String clientID,String serverID, DataStruct.Ticket ticketTGS, DataStruct.Authenticator authTGS){
		DataStruct.Package p= new DataStruct.Package();
		
		p.setRequestID(serverID);
		p.setTicket(ticketTGS);
		p.setAuth(authTGS);
		
		if(Number > 16)
		{
			Number = -1;
		}
		Number++;
		String number = supplement(4, Integer.toBinaryString(Number));
		

		String tic = Integer.toString((p.getTicket().ticketOutput().length())); //tickettgs���ܺ�ĳ���
		tic = supplement(16, StringToBinary(tic));
		String securityCode = DataStruct.Head.zero(128);
		
		DataStruct.Head h= new DataStruct.Head(TGSID,clientID,"0","0","0","1","0","0","1","1",number,securityCode,tic);
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
	public static DataStruct.Package clentToV(String clientID,DataStruct.Ticket ticketV, DataStruct.Authenticator authV) {
		DataStruct.Package p= new DataStruct.Package();
		
		p.setTicket(ticketV);
		p.setAuth(authV);
		p.setTimeStamp(DataStruct.Package.Create_TS());
		
		if(Number > 16)
		{
			Number = -1;
		}
		Number++;
		String number = supplement(4, Integer.toBinaryString(Number));
		
		String tic = Integer.toString((p.getTicket().ticketOutput().length())); //tickettgs���ܺ�ĳ���
		tic = supplement(16, StringToBinary(tic));
		String securityCode = DataStruct.Head.zero(128);
		DataStruct.Head h= new DataStruct.Head(TGSID,clientID,"0","0","0","0","0","0","1","1",number,securityCode,tic);
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
	public static DataStruct.Authenticator generateAuth(String ID,String AD,String k){

		String ts = DataStruct.Package.Create_TS();
		DataStruct.Authenticator a= new Authenticator(ID,AD,StringToBinary(ts));
		System.out.println(a);
		String cipher = Des.DES.encrypt(a.AuthOutput(), k);
		//���ܺ����a��
		a.setClientID("");
		a.setClientIP("");
		a.setTimeStamp("");
		char M[] = cipher.toCharArray();

		for(int i = 0;i<cipher.length();i++)
		{
			if(i<4) {
				a.setClientID(a.getClientID()+M[i]);
			}
			else if(i<40) {
				a.setClientIP(a.getClientIP()+M[i]);
			}
			else {
				a.setTimeStamp(a.getTimeStamp()+M[i]);
			}
		}
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
		if(length%4 != 0) {
			System.err.println("Client������תʮ����ʱ�������Ƴ�������");
		}
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
	public static String packageToBinary(DataStruct.Package p)
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
		//������Ϣ��֤��
		String sc = DataStruct.Head.MD5(p.packageOutput());
		p.getHead().setSecurityCode(sc);
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
	public static boolean send(Socket socket,String message) throws IOException{
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
	public static String receive(Socket socket){
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
        		System.out.println("�յ���"+ssss);
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
	public static DataStruct.Package packageAnalyse(String message,String k){
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
				s[11] , s[12] , s[13] , s[14], s[15], s[16]+s[17]+s[18]+s[19] ,  "",
				s[148]+s[149]+s[150]+s[151]+s[152]+s[153]+s[154]+s[155]+s[156]+s[157]+s[158]+s[159]+s[160]+s[161]+s[162]+s[163] ));
		for(int n = 20 ; n < 148 ; n++) {
			p.getHead().setSecurityCode(p.getHead().getSecurityCode()+s[n]);
		}
		String pack = message.replaceFirst(p.getHead().headOutput(), "");
		//��֤��Ϣ��֤��
		if(DataStruct.Head.MD5(pack).equals(p.getHead().getSecurityCode())) {
			if(p.getHead().getExistSessionKey().equals("0") && p.getHead().getExistTS().equals("0")) {
					System.out.println("����");
				for(int i = headLength;i<message.length();i++)
				{	
				}
			}
			else if(p.getHead().getExistLifeTime().equals("1")) {
				//˵����AS����
				//package���� ��client˽Կ
				String m = message.replaceFirst(p.getHead().headOutput(),"");
				RSA.rsa rsa = new RSA.rsa();	
				message = rsa.decrypt(m, selfK);
	
				System.out.println("����:"+m);
				System.out.println("����:"+message);
				char C[] = message.toCharArray();
				//���
				int tic = Integer.parseInt(BinaryToString(p.getHead().getExpend()));
				DataStruct.Ticket ticket = new Ticket();
				for(int i = 0;i<message.length();i++)
				{	
					if(i<64)
						p.setSessionKey(p.getSessionKey()+C[i]);
					else if(i<68)
						p.setRequestID(p.getRequestID()+C[i]);
					else if(i<68+56)
						p.setTimeStamp(p.getTimeStamp()+C[i]);
					else if(i<68+56+56)
						p.setLifeTime(p.getLifeTime()+C[i]);
					else if(i<68+56+56+tic+1){
						if(i<68+56+56+64)
							ticket.setSessionKey(ticket.getSessionKey()+C[i]);
						else if(i<68+56+56+68)
							ticket.setID(ticket.getID()+C[i]);
						else if(i<68+56+56+100)
							ticket.setIP(ticket.getIP()+C[i]);
						else if(i<68+56+56+104)
							ticket.setRequestID(ticket.getRequestID()+C[i]);
						else if(i<68+56+56+104+56)
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
				
				p.setTicket(ticket);
			}
			else if(p.getHead().getExistTicket().equals("1")){
				//˵����TGS����
				//package���� ��Ktgs��c ���ν���k
				String m = message.replaceFirst(p.getHead().headOutput(),"");
				message = DES.decrypt(m, k);
				char C[] = message.toCharArray();
				//���
				int tic = Integer.parseInt(BinaryToString(p.getHead().getExpend()));
				DataStruct.Ticket ticket = new Ticket();
				for(int i = 0;i<message.length();i++)
				{	
					if(i<64)
						p.setSessionKey(p.getSessionKey()+C[i]);
					else if(i<68)
						p.setRequestID(p.getRequestID()+C[i]);
					else if(i<68+56)
						p.setTimeStamp(p.getTimeStamp()+C[i]);
					else if(i<68+56+tic+1){
						if(i<68+56+64)
							ticket.setSessionKey(ticket.getSessionKey()+C[i]);
						else if(i<68+56+68)
							ticket.setID(ticket.getID()+C[i]);
						else if(i<68+56+100)
							ticket.setIP(ticket.getIP()+C[i]);
						else if(i<68+56+104)
							ticket.setRequestID(ticket.getRequestID()+C[i]);
						else if(i<68+56+104+56)
							ticket.setTimeStamp(ticket.getTimeStamp()+C[i]);
						else 
							ticket.setLifeTime(ticket.getLifeTime()+C[i]);
					}
					else {
							System.err.println("��������TGS��������package�����������飡��");
						       System.exit(0);
					}
				}	
	
				String string = p.getTimeStamp();
				p.setTimeStamp(BinaryToString(string));
				p.setTicket(ticket);
			}
			else if(p.getHead().getExistTS().equals("1")){
				//˵����V����
				//package���� ��Kv��c ���ν���k
				String m = message.replaceFirst(p.getHead().headOutput(),"");
				message = DES.decrypt(m, k);
				p.setTimeStamp(BinaryToString(p.getTimeStamp()));
			}	
			System.out.println("�����İ���"+ p);
			return p ;
			}
			else {
				System.err.println("�յ��İ�����");
				return null;
			}			
	}
	
	/**
	 * ��֤��
	 * @param p ������İ�
	 * @return ������ȷ�ķ���true
	 */
    public static boolean verifyPackage(DataStruct.Package p,String TS){
    	//�������ݿ⣬�鿴ID�Ƿ���ʵ
    	if(p.getTimeStamp().equals(DataStruct.Package.Create_lifeTime(TS, 1)))
    	return true;
    	else
    		return false;
    }    
	
	public static InetAddress getIpAddress() {
	    try {
	      Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
	      InetAddress ip = null;
	      while (allNetInterfaces.hasMoreElements()) {
	        NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
	        if (netInterface.isLoopback() || netInterface.isVirtual() || !netInterface.isUp()) {
	          continue;
	        } else {
	          Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
	          while (addresses.hasMoreElements()) {
	            ip = addresses.nextElement();
	            if (ip != null && ip instanceof Inet4Address) {
	              return ip;
	            }
	          }
	        }
	      }
	    } catch (Exception e) {
	    	System.err.println("IP��ַ��ȡʧ��" + e.toString());
	    }
	    return null;
	  }

	/**
	 * ע��
	 * �ͻ��ڿͻ������� ID������
	 * ���ҽ�����͹�Կ��AS�Ĺ�Կ����
	 * @return ���ܺ�İ�
	 */
	 public static DataStruct.Package signin(String id,String pw,String[] Pk){
		DataStruct.Package p= new DataStruct.Package();
		
		//String clientID1 = supplement(4, Integer.toBinaryString(clientID));
		
		p.setSessionKey(Pk[0]);
		p.setID(supplement(16,id)); 
		p.setRequestID(StringoBinary(pw));
//		p.setLifeTime();
		
		if(Number > 16)
		{
			Number = -1;
		}
		Number++;
		String number = supplement(4, Integer.toBinaryString(Number));

		String sc = DataStruct.Head.MD5(p.packageOutput());
		p.getHead().setSecurityCode(sc);
		String expend = Integer.toString((Pk[0].length())); //tickettgs���ܺ�ĳ���
		expend = supplement(16, StringToBinary(expend));
		String securityCode = DataStruct.Head.MD5(p.packageOutput());
		DataStruct.Head h= new DataStruct.Head(ASID,"0000","1","1","1","1","0","0","0","0",number,securityCode,expend);
		p.setHead(h);
		
		return p;
	}
		/**
		 * ��¼
		 * �ͻ��ڿͻ������� ID������
		 * ���ҽ�����͹�Կ��AS�Ĺ�Կ����
		 * @return ���ܺ�İ�
		 */
		 public static DataStruct.Package login(String id,String pw){
			DataStruct.Package p= new DataStruct.Package();
	
			p.setID(supplement(16,id)); 
			p.setRequestID(StringoBinary(pw));
			
			if(Number > 16)
			{
				Number = -1;
			}
			Number++;
			String number = supplement(4, Integer.toBinaryString(Number));

			String sc = DataStruct.Head.MD5(p.packageOutput());
			p.getHead().setSecurityCode(sc);

			String securityCode = DataStruct.Head.MD5(p.packageOutput());
			DataStruct.Head h= new DataStruct.Head(ASID,"0000","1","0","1","0","0","0","0","0",number,securityCode,"0000000000000000");
			p.setHead(h);
			
			return p;
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
		LOG log = new LOG();
		log.setVisible(true);
	}
}

