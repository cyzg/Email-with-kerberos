package AS;

import java.net.Socket;

public class AS {
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
	 * 验证包
	 * @param p 分析完的包
	 * @return 包是正确的返回true
	 */
    public boolean verifyPackage(DataStruct.Package p){
    	return true;
    }
	
	/**
	 * 生成会话密钥 K(c,tgs)函数
	 * 随机生成会话密钥以字符串形式返回
	 * @return  K(c,tgs)密钥
	 */
	public String generateKeyCtgs(){
	//
		return "";
	}
	/**
	 * 生成TicketTGS
	 * 按 Kc,tgs|| IDc|| ADc|| IDtgs|| TS2|| Lifetime2 格式封装 Ticket
	 * 用 Ktgs 加密
	 * 以字符串形式返回
	 * @param p 解析的包中的内容
	 * @return 返回加密后的Ticket
	 */
	public String generateTicketTGS(DataStruct.Package p){
		return "";
	}
	/**
	 * 打包数据
	 * 形成  KeyCtgs||IDtgs||时间戳||生存周期||Tickettgs 包
	 * 形成的包用 Keyc 加密
	 * 加密的密文以字符串形式返回 
	 * @param IDtgs tgs的ID
	 * @param TicketTgs 加密后的tgs票据
	 * @return
	 */
	public String packData(String IDtgs,String TicketTgs){
		return "";
	}
	/**
	 * 将 Package 类型转化为二进制流数据
	 * 依次判断每一属性，进行拼接
	 * @param p 数据包
	 * @return 二进制字符串
	 */
	String packageToBiarny(Package p)
	{ 
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
	/**
	 * 注册
	 * AS端确认是否同意注册（显示在UI上）
	 * 同意注册则将用户ID和密码密钥放入数据库
	 * @param p
	 */
	void login(DataStruct.Package p){
	}
	/**
	 * 主函数
	 */
	void main(){

	}
}
