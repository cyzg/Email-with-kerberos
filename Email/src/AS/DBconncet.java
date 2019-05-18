package AS;

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
		        //创建表user
		        stat.executeUpdate("create table if not exists `user`"
		        		+ "(`id` int NOT NULL AUTO_INCREMENT,"
		        		+ " `name` char(16) NOT NULL, "
		        		+ " `password` varchar(50) NOT NULL,"
		        		+ "`pk` text NOT NULL, "
		        		+ "PRIMARY KEY (`id`))ENGINE=InnoDB DEFAULT CHARSET=utf8;");

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
	
	public void insertData(Statement stat, String name,String password,String k){
		try{
			String newType1=new String(name.getBytes(),"GBK");//字节转码
			String newType2=new String(password.getBytes(),"GBK");
			String newType3=new String(k.getBytes(),"GBK");
			String sql="INSERT INTO user(name,password,pk)VALUES(\""+newType1+"\",\""+newType2+"\",\""+newType3+"\")";
			stat.executeUpdate(sql);//更新语句
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
	}
	public int getID( Statement stat,String name) throws SQLException, UnsupportedEncodingException{
		int clientid = 0;
		ResultSet id = stat.executeQuery("select id from user where name = \""+name+"\"");
			while(id.next()) {
				clientid = id.getInt("id");
			}
			return clientid;
	}
	public boolean selectName(Statement stat,String mID) throws SQLException{
		String name;
		String sql="SELECT name FROM user";
		try{
			ResultSet rs=stat.executeQuery(sql);//返回结果集
			while(rs.next()){//指针向后移动
				name = rs.getString("name");
				if(name.equals(mID)){
					return true;
				}
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	public boolean log(Statement stat,String mID,String pw) throws SQLException{
		String password;
		String sql="SELECT name,password FROM user where name = \""+mID+"\"";
		try{
			ResultSet rs=stat.executeQuery(sql);//返回结果集
			while(rs.next()){//指针向后移动
				password = rs.getString("password");
				if(password.equals(pw)){
					return true;
				}
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
}
