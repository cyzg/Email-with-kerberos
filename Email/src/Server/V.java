package Server;

import java.net.Socket;

import DataStruct.Ticket;

public class V {
	private final static String[] SELFKEY = {"1197109859790913087686830134943274236719072137378604521650533072095054080831" , "1119539357176536616075341013316710825069096752350867012830658571383977123009"}; //TGS˽Կ

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
	 * �� Kv ����
	 * @param Tickettgs
	 * @return ���ؽ��ܵ�Ʊ��
	 */
	public DataStruct.Ticket AnalyseTicket(String Tickettgs){
	
		return new DataStruct.Ticket();
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
	public boolean checkIdentity(DataStruct.Authenticator authen,DataStruct.Ticket ticketTGS){
	
		return true;
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
	 * ��Client�����Ӻ��ͨ��
	 */
	public void connect() {
	}
}
