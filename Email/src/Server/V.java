package Server;

import java.net.Socket;

import DataStruct.Ticket;

public class V {
	
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
	 * 用 Kv 解密
	 * @param Tickettgs
	 * @return 返回解密的票据
	 */
	public DataStruct.Ticket AnalyseTicket(String Tickettgs){
	
		return new Ticket();
	}
	/**
	 * 解密 Authenticator,验证票据
	 * 用 Kc,v 解开 Authenticator
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
	/**
	 * 与Client端连接后的通信
	 */
	public void connect() {
	}
}
