package AS;

import java.net.Socket;

public class AS {
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
	 * ��֤��
	 * @param p ������İ�
	 * @return ������ȷ�ķ���true
	 */
    public boolean verifyPackage(DataStruct.Package p){
    	return true;
    }
	
	/**
	 * ���ɻỰ��Կ K(c,tgs)����
	 * ������ɻỰ��Կ���ַ�����ʽ����
	 * @return  K(c,tgs)��Կ
	 */
	public String generateKeyCtgs(){
	//
		return "";
	}
	/**
	 * ����TicketTGS
	 * �� Kc,tgs|| IDc|| ADc|| IDtgs|| TS2|| Lifetime2 ��ʽ��װ Ticket
	 * �� Ktgs ����
	 * ���ַ�����ʽ����
	 * @param p �����İ��е�����
	 * @return ���ؼ��ܺ��Ticket
	 */
	public String generateTicketTGS(DataStruct.Package p){
		return "";
	}
	/**
	 * �������
	 * �γ�  KeyCtgs||IDtgs||ʱ���||��������||Tickettgs ��
	 * �γɵİ��� Keyc ����
	 * ���ܵ��������ַ�����ʽ���� 
	 * @param IDtgs tgs��ID
	 * @param TicketTgs ���ܺ��tgsƱ��
	 * @return
	 */
	public String packData(String IDtgs,String TicketTgs){
		return "";
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
	 * ע��
	 * AS��ȷ���Ƿ�ͬ��ע�ᣨ��ʾ��UI�ϣ�
	 * ͬ��ע�����û�ID��������Կ�������ݿ�
	 * @param p
	 */
	void login(DataStruct.Package p){
	}
	/**
	 * ������
	 */
	void main(){

	}
}
