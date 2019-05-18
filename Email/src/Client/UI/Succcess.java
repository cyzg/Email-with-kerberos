package Client.UI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Succcess extends JFrame{
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		

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
				Connection  connect= DriverManager.getConnection( "jdbc:mysql:///email?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC","root","123123");
		             //连接URL为   jdbc:mysql//服务器地址/数据库名  ，后面的2个参数分别是登陆用户名和密码
		        System.out.println("Success connect Mysql server!");	
		        Statement stmt = connect.createStatement();
		        ResultSet rs = stmt.executeQuery("select * from sss");
		                                                                //user 为你表的名称
		  while (rs.next()) {
		          System.out.println(rs.getString("ID"));
		        }
	}
	    catch (Exception e) {
		        System.out.print("get data error!");
		        e.printStackTrace();
		      }
		    
	
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Succcess frame2 = new Succcess();
					frame2.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Succcess() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u6CE8\u518C\u6210\u529F\uFF01");
		label.setFont(new Font("仿宋", Font.PLAIN, 27));
		label.setBounds(151, 72, 174, 56);
		contentPane.add(label);
		
		JButton button = new JButton("\u8FD4\u56DE");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WELCOME frame = new WELCOME();
				frame.setVisible(true);
			}
		});
		button.setFont(new Font("仿宋", Font.PLAIN, 18));
		button.setBounds(327, 190, 91, 27);
		contentPane.add(button);
	}
}
