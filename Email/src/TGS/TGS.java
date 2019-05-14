package TGS;

import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import DataStruct.Package;
import DataStruct.Ticket;

public class TGS {
	

	private static int Number = -1; //���ĳ�ʼ���
	private final int MAXNUMBER = 0; //���������
	private final static String TGSID = "0010"; 
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
	 * ������뿪�� Tickettgs ����
	 * �� Ktgs ����
	 * @param Tickettgs
	 * @return ���ؽ��ܵ�Ʊ��
	 */
	public DataStruct.Ticket AnalyseTicket(String Tickettgs){
	
		return new Ticket();
	}
	/**
	 * ���� Authenticator,��֤Ʊ��
	 * �� Kc,tgs �⿪ Authenticator
	 * ���ݺ� Ticket ������֤
	 * ���ز�����֤���
	 * @param authen
	 * @param ticketTGS
	 * @return ��֤��ȷ����true
	 */
	public boolean checkIdentity(DataStruct.Authenticator authen,DataStruct.Ticket ticketTGS){
	
		return true;
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
	 * ����Ticket
	 * �� Kc,v||IDC||ADC|| IDv||TS4||Lifetime4 ��ʽ��װ Ticket
	 * �� Kv ����
	 * ���ַ�����ʽ����
	 * @param p �����İ��е�����
	 * @return ���ؼ��ܺ��Ticket
	 */
	public DataStruct.Ticket generateTicketV(DataStruct.Package p){
		String lifetime = "";
		DataStruct.Ticket t = new Ticket(generateKeyCV(), p.getID(), p.getAuth().getClientIP(),
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
	 * �γ� Kc,v|| IDV|| TS4|| Ticketv ��
	 * �γɵİ���  KeyCtgs����
	 * ���ܵ��������ַ�����ʽ���� 
	 * @param IDv v��ID
	 * @param Ticketv ���ܺ��vƱ��
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
		  	System.out.println("�յ��İ���"+ssss);
	            return ssss;
	} 
	public static void main(String[] args) {
		
	}
}
