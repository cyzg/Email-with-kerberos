package TGS;

import java.net.Socket;

import DataStruct.Ticket;

public class TGS {
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
		return "";
	}
	/**
	 * ����Ticket
	 * �� Kc,v||IDC||ADC|| IDv||TS4||Lifetime4 ��ʽ��װ Ticket
	 * �� Kv ����
	 * ���ַ�����ʽ����
	 * @param p �����İ��е�����
	 * @return ���ؼ��ܺ��Ticket
	 */
	public String generateTicketV(DataStruct.Package p){
		return "";
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
	public String packData(String IDv,String Ticketv){
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
}
