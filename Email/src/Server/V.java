package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import DataStruct.Authenticator;
import DataStruct.Ticket;
import Des.DES;
import TGS.TGSReceiver;

public class V {

	private static int Number = -1; //���ĳ�ʼ���
	private final int MAXNUMBER = 0; //���������
	private final static String SERVERID = "0011"; 
	private final static String[] SELFKEY = {"1197109859790913087686830134943274236719072137378604521650533072095054080831" , "1119539357176536616075341013316710825069096752350867012830658571383977123009"}; //TGS˽Կ

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
						s[11] , s[12] , s[13] , s[14], s[15], s[16]+s[17]+s[18]+s[19] , s[20]+s[21] ,
						s[22]+s[23]+s[24]+s[25]+s[26]+s[27]+s[28]+s[29]+s[30]+s[31]+s[32]+s[33]+s[34]+s[35]+s[36]+s[37] ));
	
		System.out.println(p.getHead());
		//����������ݺ�Ticket��Auth
		String messageT = "" , messageA = "";
		int tic = Integer.parseInt(BinaryToString(p.getHead().getExpend()));//���ܺ�tic�ĳ���
		for(int i = headLength;i<message.length();i++)
		{
			if(i<headLength+tic){
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
					System.err.println("��������V�յ���package�����������飡��");
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
				System.err.println("��������V�յ���package�����������飡��");
			       System.exit(0);
			}
	}
		ticket.setTimeStamp(BinaryToString(ticket.getTimeStamp()));
		ticket.setLifeTime(BinaryToString(ticket.getLifeTime()));
		auth.setTimeStamp(BinaryToString(auth.getTimeStamp()));
		p.setTicket(ticket);
		p.setAuth(auth);
		
		System.out.println("�����İ���"+ p);
		return p ;
	}

	/**
	 * ���� Authenticator,��֤Ʊ��
	 * �� Kc,v �⿪ Authenticator
	 * ���ݺ� Ticket ������֤
	 * ���ز�����֤���
	 * @param authen
	 * @param ticketTGS
	 * @return ��֤��ȷ����true
	 */
	public boolean checkIdentity(DataStruct.Package p){
		if(p.getTicket().getRequestID().equals(SERVERID))
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
			System.err.println("Ticket����ID�벻��SERVERID");
			return false;
		}
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
	 * �������client
	 * @param clientID
	 * @param TS
	 * @return
	 */
	public String packData(String clientID,String TS,String k){
		DataStruct.Package p = new DataStruct.Package();;
		
		p.setTimeStamp(DataStruct.Package.Create_lifeTime(TS, 1));
		
		if(Number > 16)
		{
			Number = -1;
		}
		Number++;
		String number = supplement(4, Integer.toBinaryString(Number));
		
		DataStruct.Head h= new DataStruct.Head(clientID,SERVERID,"0","0","0","0","1","0","0","0",number,"00","0000000000000000");
		p.setHead(h);
		
		p.setTimeStamp(StringToBinary(p.getTimeStamp()));
		
		//des����
		String send = DES.encrypt(p.getTimeStamp(), k);
		
		return p.getHead().headOutput()+send;
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
	/**
	 * ��Client�����Ӻ��ͨ��
	 */
	public void connect() {
	}
	
	public static void main(String[] args) throws IOException, InterruptedException{
		 System.out.println("-------V��----------");
		int port=5555;
		new Thread(VReceiver.listener(port)).start();
	}
}
