package TGS;

import java.io.IOException;
import java.net.Socket;

import TGS.UI.AP;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;

import DataStruct.Authenticator;
import DataStruct.Package;
import DataStruct.Ticket;
import Des.DES;

public class TGS {
	

	private static int Number = -1; //���ĳ�ʼ���
	private final int MAXNUMBER = 0; //���������
	private final static String TGSID = "1110"; 
	private final static String[] SELFKEY = {"865703290362069039664574527025207637385565054952203601297173618790874525723" , "259555164968416688158625327465001826979021157149123374785762616336430795281"}; //TGS˽Կ
	private final static String[] KEYV = {"1197109859790913087686830134943274236719072137378604521650533072095054080831" , "3889"}; //TGS˽Կ

	/**
	 * ������
	 * ����ͷ���������ܵ��İ���⿪�������ֶδ浽 Package ������
	 * @param message
	 * @return ���ط�����ɵ�package
	 */
	public DataStruct.Package packAnalyse(String message){
		System.out.println("-----��ʼ������-----");
		DataStruct.Package p = new DataStruct.Package();
		DataStruct.Ticket ticket = new Ticket();
		DataStruct.Authenticator auth = new Authenticator();
		
		//�����ͷ��
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
		System.out.println(p.getHead());
		String pack = message.replaceFirst(p.getHead().headOutput(), "");
		
		//��֤��Ϣ��֤��
		if(DataStruct.Head.MD5(pack).equals(p.getHead().getSecurityCode())) {

			//����������ݺ�Ticket��Auth
			String messageT = "" , messageA = "";
			int tic = Integer.parseInt(BinaryToString(p.getHead().getExpend()));//���ܺ�tic�ĳ���
			for(int i = headLength;i<message.length();i++)
			{
				if(i<headLength+4)
					p.setRequestID(p.getRequestID()+M[i]);
				else if(i<headLength+4+tic){
					messageT = messageT + M[i];
				}
				else {
					messageA = messageA + M[i];
				}
			
			}
	
			//Ticket���� ��tgs˽Կ
			RSA.rsa rsa = new RSA.rsa();
			System.out.println("����:"+messageT);	
			messageT = rsa.decrypt(messageT, SELFKEY);
			System.out.println("����:"+messageT);
			char T[] = messageT.toCharArray();
			//��Ticket
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
						System.err.println("��������TGS�յ���package�����������飡��");
					       System.exit(0);
					}
			}
			
			//Auth���ܣ���Ticket�е�sessionkey����
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
					System.err.println("��������TGS�յ���package�����������飡��");
				       System.exit(0);
				}
		}
			ticket.setTimeStamp(BinaryToString(ticket.getTimeStamp()));
			ticket.setLifeTime(BinaryToString(ticket.getLifeTime()));
			auth.setTimeStamp(BinaryToString(auth.getTimeStamp()));
			p.setTicket(ticket);
			p.setAuth(auth);
			
			System.out.println("�����İ���"+ p);
			return p;
		}
		else {
			System.err.println("TGS�յ��İ�����");
			return null;
		}
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
    /*
	 * ��string�ַ�����λת�����Ʊ����string�ַ���(����ת)
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
	 * ��֤Ʊ��
	 * �� Kc,tgs �⿪ Authenticator
	 * ���ݺ� Ticket ������֤
	 * ���ز�����֤���
	 * @param authen
	 * @param ticketTGS
	 * @return ��֤��ȷ����true
	 */
	public boolean checkIdentity(DataStruct.Package p){
		if(p.getTicket().getRequestID().equals(TGSID))
			if(p.getAuth().getClientID().equals(p.getTicket().getID()))
				if(p.getAuth().getClientIP().equals(p.getTicket().getIP()))
					if(DataStruct.Package.isalive(p.getTicket().getLifeTime()))
					return true;
					else
					{
						System.err.println("TicketTGS�ѹ���");
						return false;
					}
				else
				{
					System.err.println("AuthIP��TicketIP����");
					return false;
				}
			else
			{
				System.err.println("AuthID��TicketID����");
				return false;
			}
		else 
		{
			System.err.println("Ticket����ID�벻��TGSID");
			return false;
		}
	} 
	/**
	 * ���ɻỰ��Կ Key(c,v)
	 * ������ɻỰ��Կ Kcv
	 * @return �ַ�����ʽ��Կ
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
	 * ipתbinary
	 * @param ip
	 * @return
	 */
	public static String ipToBinary(InetAddress ip)
    {
        byte[] b=ip.getAddress();
        long l= b[0]<<24L & 0xff000000L|
                b[1]<<16L & 0xff0000L  |
                b[2]<<8L  &  0xff00L   |
                b[3]<<0L  &  0xffL ;
         
        return Integer.toBinaryString((int)l);
	}
	/**
	 * ����Ticket
	 * �� Kc,v||IDC||ADC|| IDv||TS4||Lifetime4 ��ʽ��װ Ticket
	 * �� Kv ����
	 * ���ַ�����ʽ����
	 * @param p �����İ��е�����
	 * @return ���ؼ��ܺ��Ticket
	 */
	public DataStruct.Ticket generateTicketV(DataStruct.Package p,InetAddress inetAddress){
		System.out.println("-----��������Ticket-----");
		String lifetime = DataStruct.Package.Create_lifeTime(2);
		String clientIP = ipToBinary(inetAddress);
		DataStruct.Ticket t = new Ticket(generateKeyCV(), p.getHead().getSourceID(), clientIP,
				p.getRequestID(), DataStruct.Package.Create_TS(), lifetime);
		System.out.println("���ɵ�Ticket��"+t);
		
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
	 * �γ� Kc,v|| IDV|| TS4|| Ticketv ��
	 * �γɵİ���  KeyCtgs����
	 * ���ܵ��������ַ�����ʽ���� 
	 * @param IDv v��ID
	 * @param Ticketv ���ܺ��vƱ��
	 * @return
	 */
	public DataStruct.Package packData(String clientID,String IDv,DataStruct.Ticket Ticketv){
		DataStruct.Package p = new DataStruct.Package();;
		
		p.setSessionKey(Ticketv.getSessionKey());
		p.setRequestID(IDv);
		p.setTimeStamp(DataStruct.Package.Create_TS());
		p.setTicket(Ticketv);
		
		if(Number > 16)
		{
			Number = -1;
		}
		Number++;
		String number = supplement(4, Integer.toBinaryString(Number));
		String securityCode = DataStruct.Head.zero(128);
		
		DataStruct.Head h= new DataStruct.Head(clientID,TGSID,"0","1","0","1","1","0","1","0",number,securityCode,"0000000000000000");
		p.setHead(h);
		
		return p;
	}
	/** 
	 * �� Package ����ת��Ϊ������������
	 * �����ж�ÿһ���ԣ�����ƴ��
	 * @param p ���ݰ�
	 * @return �������ַ���
	 */
	String packageToBiarny(DataStruct.Package p , String k)
	{ 
		RSA.rsa rsa = new RSA.rsa();
		DataStruct.Ticket ticket = p.getTicket();
		String s = new String();
		String send = p.toString();
		String lt = p.getLifeTime();
		String ts = p.getTimeStamp();

		System.out.println("�����"+p);		
		
		if(p.getHead().getExistTS() == "1") {
			s = StringToBinary(p.getTimeStamp());
			p.setTimeStamp(s);
		}
		
		if(p.getHead().getExistLifeTime() == "1") {
			s = StringToBinary(p.getLifeTime());
			p.setLifeTime(s);
		}
		//��tתΪ������
		if(p.getHead().getExistTicket().equals("1")) {
			DataStruct.Ticket t = p.getTicket();
			t.setTimeStamp(StringToBinary(p.getTicket().getTimeStamp()));
			t.setLifeTime(StringToBinary(p.getTicket().getLifeTime()));	
			//��Ticket����
			String cipher = rsa.encrypt(t.ticketOutput(), KEYV);
			t = new Ticket("", "", "", "", "", "");
			
			char M[] = cipher.toCharArray();
			
			//���ܺ����t��
			for(int i = 0;i<cipher.length();i++)
			{
				if(i<64) {
					t.setSessionKey(t.getSessionKey()+M[i]);
				}
				else if(i<68) {
					t.setID(t.getID()+M[i]);
				}
				else if(i<68+32){
					t.setIP(t.getIP()+M[i]);
				}
				else if(i<68+36) {
					t.setRequestID(t.getRequestID()+M[i]);
				}
				else if(i<68+36+56) {
					t.setTimeStamp(t.getTimeStamp()+M[i]);
				}
				else {
					t.setLifeTime(t.getLifeTime()+M[i]);
				}
			}
			p.setTicket(t);
		}
		String tic = Integer.toString((p.getTicket().ticketOutput().length())); //tickettgs���ܺ�ĳ���
		tic = supplement(16, StringToBinary(tic));
		p.getHead().setExpend(tic);
		
		//���� ��Kc,tgs
		String c = DES.encrypt(p.packageOutput(), k);//���ܺ�İ�

		//������Ϣ��֤��
		String sc = DataStruct.Head.MD5(c);
		p.getHead().setSecurityCode(sc);
		
		send = p.getHead().headOutput()+c;
	
		p.setTimeStamp(ts);
		p.setLifeTime(lt);
		p.setTicket(ticket);
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
	 * @throws IOException 
	 */
 
	public static String receive(Socket s) throws IOException{
		String ssss="";
		 InputStream is=null; 
		  try { 
	       	    
	            is = s.getInputStream();
	            //4.�Ի�ȡ�����������еĲ���
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
		  	System.out.println("�յ��İ���"+ssss);
	            return ssss;
	} 
	public static void main(String[] args) throws IOException, InterruptedException {
		 System.out.println("-------TGS��----------");
		 AP ui = new AP();
		 ui.setVisible(true);
		 int port=5555;
		 TGSReceiver r = new TGSReceiver();
		 new Thread(r.listener(port,ui)).start();
	}
}
