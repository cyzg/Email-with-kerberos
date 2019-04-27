package DataStruct;

public class Head {   //首部协议
	private String number;   //编号
	private String destID;   //目的ID
	private String sourceID;   //源ID
	private String existLogin; //是否是注册（如果是注册，则seeeionkey是keyc，ID是账号，后面是密码）
	private String existSessionKey;  //是否存在sessionKey
	private String existID;    //是否存在请求方ID
	private String existRequstID;   //是否存在被请求方ID
	private String existTS;        //是否存在时间戳
	private String existLifeTime;  //  是否存在生存期限
	private String existTicket;    //是否存在票据
	private String existAuthenticator;   //是否存在认证
}


