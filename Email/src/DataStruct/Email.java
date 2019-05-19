package DataStruct;

public class Email {
	private String content;   //�ʼ�����
	private String timeStamp;  //ʱ���
	public Email(String content,String timeStamp) {
		super();
		this.content = content;
		this.timeStamp = timeStamp;
	}
	public Email() {
		this.content = "";
		this.timeStamp = "";
	}
	
	
	@Override
	public String toString() {
		return "Email [content=" + content + ", timeStamp=" + timeStamp + "]";
	}
	public String EmailOutput() {
		return  timeStamp+content ;
	}
	public String getcontent() {
		return content;
	}
	public void setcontent(String content) {
		this.content = content;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
