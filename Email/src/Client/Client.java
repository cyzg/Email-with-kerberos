package Client;

import java.net.Socket;

public class Client {
	/**
	 * ��װ���͸�AS�İ� 
	 * ����Ҫ���͵���Ϣ
	 * ����ͷ����Ӧ��־�ֶ�
	 * @param clientID �ͻ���ID
	 * @param tgsID TGSID
	 * @param TS1 ʱ���
	 * @return ���ط�װ��ɵİ�
	 */
	DataStruct.Package clientToAS(int clientID,int tgsID,String TS1){ 
		
		return new DataStruct.Package();
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
	void main(){

	}

}

