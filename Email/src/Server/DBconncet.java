package Server;

import java.awt.EventQueue;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JPanel;
import javax.swing.JTextField;


public class DBconncet {
	protected static final String JTextField = null;

	public static Connection connect() {
		Statement stat = null;
		Connection conn = null;
		   try {
		      Class.forName("com.mysql.cj.jdbc.Driver");     //加载MYSQL JDBC驱动程序   		  
		     System.out.println("Success loading Mysql Driver!");
		    }
		    catch (Exception e) {
		      System.out.print("Error loading Mysql Driver!");
		      e.printStackTrace();
		    }
		    		    
		  try {
		        @SuppressWarnings("unused")
				//Connection  connect= DriverManager.getConnection( "jdbc:mysql:///email?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC","root","123456");
		             //连接URL为   jdbc:mysql//服务器地址/数据库名  ，后面的2个参数分别是登陆用户名和密码
	
		        //一开始必须填一个已经存在的数据库
		        String url = "jdbc:mysql:///email?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC";   
		        conn = DriverManager.getConnection(url, "root", "123456");
		        stat = conn.createStatement();
		        stat.executeUpdate("drop table if exists `server`");
		        //创建表user
		        stat.executeUpdate("create table if not exists `server`"
		        		+ "(`num` int NOT NULL AUTO_INCREMENT,"
		        		+ " `receiveid` char(4) NOT NULL, "
		        		+ " `sendid` char(4) NOT NULL,"
		        		+ " `len` char(16) NOT NULL, "
		        		+ "`content` text NULL, "
		        		+ "PRIMARY KEY (`num`))ENGINE=InnoDB DEFAULT CHARSET=utf8;");

		        System.out.println("Success connect Mysql server!");

	        //关闭数据库
	        stat.close();
	}
	    catch (Exception e) {
		        System.out.print("get data error!");
		        e.printStackTrace();
	    }
		  return conn;
	}
	
	public void insertData(Statement stat, String message,String len){
			String id = message.substring(0, 4);	
			String requestid = message.substring(4, 8);
			String content = message.substring(8, message.length());
			
		try{
//			String newType1=new String(name.getBytes(),"GBK");//字节转码
//			String newType2=new String(password.getBytes(),"GBK");
//			String newType3=new String(k.getBytes(),"GBK");
			String sql="INSERT INTO server(receiveid,sendid,len,content)VALUES(\""+id+"\",\""+requestid+"\",\""+len+"\",\""+content+"\")";
			stat.executeUpdate(sql);//更新语句
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
	}
	
	public int selectID( Statement stat,String receiveid) throws SQLException, UnsupportedEncodingException{
		String current = "";
		int count = 0;
		ResultSet id = stat.executeQuery("select receiveid from server");
			while(id.next()) {
				current = id.getString("receiveid");
				if(receiveid.equals(current))
					count++;
			}
			return count;
	}
	
	
	@SuppressWarnings("null")
	public String[][]  getData( Statement stat,String receiveid,int num) throws SQLException, UnsupportedEncodingException{
		String data[][] = new String[num][3];
		int count = 0;
		ResultSet id = stat.executeQuery("select sendid,len,content from server where receiveid = \""+receiveid+"\"");
			while(id.next()) {
				data[count][0] = id.getString("sendid");
				data[count][1] = id.getString("len");
				data[count][2] = id.getString("content");
				count++;
			}
			return data;
	}

}
