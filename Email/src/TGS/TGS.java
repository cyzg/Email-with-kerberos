package TGS;

import java.net.Socket;

import DataStruct.Ticket;

public class TGS {
	/**
	 * 分析包
	 * 根据头部，将接受到的包拆解开，各个字段存到 Package 对象中
	 * @param message
	 * @return 返回分析完成的package
	 */
	public DataStruct.Package packAnalyse(String message){
		return new DataStruct.Package();
	}
	/**
	 * 传入分离开的 Tickettgs 密文
	 * 用 Ktgs 解密
	 * @param Tickettgs
	 * @return 返回解密的票据
	 */
	public DataStruct.Ticket AnalyseTicket(String Tickettgs){
	
		return new Ticket();
	}
	/**
	 * 解密 Authenticator,验证票据
	 * 用 Kc,tgs 解开 Authenticator
	 * 内容和 Ticket 对照验证
	 * 返回布尔验证结果
	 * @param authen
	 * @param ticketTGS
	 * @return 验证正确返回true
	 */
	public boolean checkIdentity(DataStruct.Authenticator authen,DataStruct.Ticket ticketTGS){
	
		return true;
	} 
	/**
	 * 生成会话密钥 Key(c,v)
	 * 随机生成会话密钥 Kcv
	 * @return 字符串形式密钥
	 */
	public String generateKeyCV(){
		return "";
	}
	/**
	 * 生成Ticket
	 * 按 Kc,v||IDC||ADC|| IDv||TS4||Lifetime4 格式封装 Ticket
	 * 用 Kv 加密
	 * 以字符串形式返回
	 * @param p 解析的包中的内容
	 * @return 返回加密后的Ticket
	 */
	public String generateTicketV(DataStruct.Package p){
		return "";
	}
	/**
	 * 打包数据
	 * 形成 Kc,v|| IDV|| TS4|| Ticketv 包
	 * 形成的包用  KeyCtgs加密
	 * 加密的密文以字符串形式返回 
	 * @param IDv v的ID
	 * @param Ticketv 加密后的v票据
	 * @return
	 */
	public String packData(String IDv,String Ticketv){
		return "";
	}
	/**
	 * 发送消息
	 * 用 socket 输出流进行发送
	 * @param socket 套接字，对应的 socket 对象
	 * @param message 要发送的信息
	 * @return 发送成功返回 true
	 */
	boolean send(Socket socket,String message){
		return true;
	}
	/**
	 * 接收消息
	 * @param socket 传入对应的 socket 对象,
	 * @return 返回接收到的消息
	 */
	String receive(Socket socket){
		return "";
	}
}
