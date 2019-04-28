package Client;

import java.net.Socket;

public class Client {
	
	private static int Number = -1; //���ĳ�ʼ���
	private final int MAXNUMBER = 0; //���������
	private final static String ASID = "0001"; //���������
	
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
	static DataStruct.Package clientToAS(int clientID,int tgsID,String TS1){ 
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
		
		DataStruct.Head h= new DataStruct.Head(number,clientID1,ASID,"0","0","1","1","1","0","0","0");
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
	DataStruct.Package clientToTGS(int serverID, DataStruct.Ticket ticketTGS, DataStruct.Authenticator authTGS){
		return new DataStruct.Package();
	}
	/**
	 * ��װ���͸� server �İ�
	 * ����ͷ����Ӧ��־�ֶ�
	 * @param ticketV ServerƱ��
	 * @param authV   Server��֤
	 * @return ���ذ�����
	 */
	DataStruct.Package clentToV(DataStruct.Ticket ticketV, DataStruct.Authenticator authV) {
		return new DataStruct.Package();
	}
	/**
	 * �� Package ����ת��Ϊ������������
	 * �����ж�ÿһ���ԣ�����ƴ��
	 * @param p ���ݰ�
	 * @return �������ַ���
	 */
	static String packageToBiarny(DataStruct.Package p)
	{ 
		String send = p.toString();
		return send;
	}
	/**
	 * ������Ϣ
	 * �� socket ��������з���
	 * @param socket �׽��֣���Ӧ�� socket ����
	 * @param message Ҫ���͵���Ϣ
	 * @return ���ͳɹ����� true
	 */
	boolean send(Socket socket,String message){
		return true;
	}
	/**
	 * ������Ϣ
	 * @param socket �����Ӧ�� socket ����,
	 * @return ���ؽ��յ�����Ϣ
	 */
	String receive(Socket socket){
		return "";
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
	 */
	public static void main(String[] args) {
		
		System.out.println("--client--");
		
		DataStruct.Package p= new DataStruct.Package();
		
		int clientID = 4;
		int tgsID = 3;
		String TS1 ="123";
		
		p = clientToAS(clientID, tgsID , TS1);
		System.out.println(p.getID());
		DataStruct.Head h= p.getHead();
		System.out.println(p.getHead().getNumber());
		System.out.println(packageToBiarny(p));

		System.out.println("---------------");
		System.out.println(p.getHead().hashCode());
		
		
	}

}

