package Client.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import Client.Client;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JTextPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;


public class WELCOME extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;	
	
	private String text1;
	private String text2;
	//private JTextArea textField_2;
	private JTextArea message1;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WELCOME frame = new WELCOME();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public WELCOME() {
		setTitle("   ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 250, 855, 475);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel ID = new JLabel("User\uFF1A");
		ID.setBounds(43, 167, 72, 18);
		ID.setFont(new Font("仿宋", Font.PLAIN, 18));
		
		JLabel password = new JLabel("\u5BC6\u7801\uFF1A");
		password.setBounds(43, 267, 72, 18);
		password.setFont(new Font("仿宋", Font.PLAIN, 18));
		
		textField = new JTextField();
		textField.setBounds(129, 164, 176, 24);
		textField.setFont(new Font("仿宋", Font.PLAIN, 18));
		textField.setColumns(10);
		textField_1 = new JTextField();
		textField_1.setBounds(129, 264, 176, 24);
		textField_1.setFont(new Font("仿宋", Font.PLAIN, 18));
		textField_1.setColumns(10);
		
		JLabel lblAs = new JLabel("\u6D88\u606F\u65E5\u5FD7");
		lblAs.setBounds(585, 17, 80, 18);
		lblAs.setFont(new Font("仿宋", Font.PLAIN, 18));
		contentPane.setLayout(null);
		
		JScrollBar scrollBar_TGS = new JScrollBar();
		JTextArea text=new JTextArea();
		scrollBar_TGS.setBounds(371, 264, 21, 173);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(437, 48, 366, 358);
		contentPane.add(scrollPane);
		
		message1 = new JTextArea();
		message1.setLineWrap(true);
		message1.setEditable(false);
		message1.setForeground(Color.GRAY);
		message1.setFont(new Font("仿宋", Font.PLAIN, 18));
		message1.setColumns(10);
		scrollPane.setViewportView(message1);
		
		
		
	
		JButton button = new JButton("\u6CE8\u518C");
		button.setBounds(296, 98, 72, 27);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				LOG frame1=new LOG();
				frame1.setVisible(true);
			}
		});
		button.setFont(new Font("仿宋", Font.PLAIN, 18));
		
		JButton button_1 = new JButton("\u767B\u5F55");
		button_1.setBounds(296, 367, 72, 27);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String s = null;                         
				s = loginrun();				
				if((s.equals("1")) ) 
				{
					message1.setText("账号或密码错误！");
					System.err.println("账号或密码错误！");
					PW_FALSE pw_false = new PW_FALSE();
					pw_false.setVisible(true);
				}
				else if((s.equals("2")) ) {
					message1.setText("用户名和密码不能为空!");
					System.err.println("用户名和密码不能为空!");
					Not_null frame7 = new Not_null();
					frame7.setVisible(true);
				}
				else{
					message1.setText("账号密码正确！");
					System.out.println("账号密码正确！");
					try {
						boolean keb= false;
						System.out.println("--client--");

						DataStruct.Package p= new DataStruct.Package();

						String clientID = s;
				    	String clientIP = "";
						
						p = Client.clientToAS(clientID, Client.TGSID );
						System.out.println("发给AS的包："+p.toString());
						message1.setText("发给AS的包:"+p.toString());
						message1.setText(message1.getText()+"\r\n\r\n-------连接AS--------");
						System.out.println("-------连接AS--------");
				        
				        //发给AS
				    	/**/Socket socket = new Socket(Client.ASIP,5555);
				        String message =Client.packageToBinary(p);
				        String rmessage = "";
				        if(Client.send(socket,message)) {
				        	rmessage = Client.receive(socket);
				        	try {
								Thread.sleep(1000);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				        	if(rmessage.equals(""))
				        	{
				        		message1.setText(message1.getText()+"\r\n\r\n未收到内容，请重新发送!!\r\n");
				        		System.err.println("未收到内容，请重新发送");
				            	socket.close();
				        	}
				        	socket.close();
				        } 
				      //发给TGS
				        DataStruct.Package p2= Client.packageAnalyse(rmessage, null);
						message1.setText(message1.getText()+"\r\n收到AS的包：\r\n"+p2.toString());
				    	if(p2.packageOutput().equals("")) {
			        		message1.setText(message1.getText()+"\r\n\r\nAS发送的包有误，请重新发送！！\r\n");
				    		System.err.println("AS发送的包有误，请重新发送！！");
				    	}
						message1.setText(message1.getText()+"\r\n\r\n-------连接TGS--------");
						System.out.println("-------连接TGS--------");
				    	clientIP = DataStruct.Package.ipToBinary(Client.getIpAddress());

				    	p = Client.clientToTGS(clientID, Client.SERVERID, p2.getTicket(), Client.generateAuth(p.getID(),clientIP,p2.getSessionKey()));
				    	System.out.println("发给TGS的包"+p);
						message1.setText(message1.getText()+"\r\n发给TGS的包:\r\n"+p.toString());
						
				        message =Client.packageToBinary(p);
				        socket = new Socket(Client.TGSIP,5555);
				        if(Client.send(socket,message)) {
				        	rmessage = Client.receive(socket);
				        	socket.close();
				        } 
				        DataStruct.Package p3= Client.packageAnalyse(rmessage, p2.getSessionKey());

						message1.setText(message1.getText()+"\r\n收到TGS的包：\r\n"+p2.toString());
						message1.setText(message1.getText()+"\r\n\r\n-------连接V--------");
						System.out.println("-------连接V--------");
				    	p = Client.clentToV(clientID, p3.getTicket(), Client.generateAuth(p3.getHead().getDestID(),clientIP,p3.getSessionKey()));
				    	String TSv = p.getTimeStamp();
				    	p.setTimeStamp("");
				    	
						message1.setText(message1.getText()+"\r\n发给V的包:\r\n"+p.toString());
				    	System.out.println("发给V的包"+p);
				    	
				        message =Client.packageToBinary(p);
				    	
				    	//发给V
				        socket = new Socket(Client.SERVERIP,5555);
				    	
				    	if(Client.send(socket,message)) {
				    		rmessage = Client.receive(socket);
				    		socket.close();
				    	}
				    	DataStruct.Package p4= Client.packageAnalyse(rmessage, p3.getSessionKey());
				    	message1.setText(message1.getText()+"\r\n收到V的包：\r\n"+p4.toString());
				    	if(Client.verifyPackage(p4, TSv)) {
							message1.setText(message1.getText()+"\r\nkerberos认证成功，进入Email！");
				    		System.out.println("kerberos认证成功，进入Email！");
				    		keb = true;
				    	}
				    	else {
							message1.setText(message1.getText()+"\r\nkerberos认证失败，请重试！");
				    		System.err.println("kerberos认证失败，请重试!");
				    		keb =  false;
				    	}
						if(keb)
						{	
							EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
//									dispose();	
									Send_email send_email = new Send_email();
									send_email.setId(clientID);									
									send_email.setVisible(true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						}
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						
						e1.printStackTrace();
					}
				}
				
			}
		});
		button_1.setFont(new Font("仿宋", Font.PLAIN, 18));
			
		contentPane.setLayout(null);
		contentPane.add(text);
		contentPane.add(ID);
		contentPane.add(password);
		contentPane.add(textField);
		contentPane.add(button);
		contentPane.add(button_1);
		contentPane.add(textField_1);
				contentPane.add(lblAs);
		
		JLabel lblwelcome = new JLabel("*********  Welcome  *********");
		lblwelcome.setFont(new Font("仿宋", Font.PLAIN, 18));
		lblwelcome.setBounds(62, 17, 280, 40);
		contentPane.add(lblwelcome);
	}
	
	public String getText1(){
		return text1;
	}
	public String getText2(){
		return text2;
	}
	String loginrun()
	{
		if(!(textField.getText().equals("") || textField_1.getText().equals(""))) {
			DataStruct.Package p = Client.login(textField.getText(),textField_1.getText());
			String send = p.getHead().headOutput()+p.packageOutput();
			String re = null;
	    	//发给AS
			try {
		        Socket socket = new Socket(Client.ASIP,5555);
		        if(Client.send(socket,send)) {
		        	re = Client.receive(socket);
					socket.close();
		        }
			} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
			} 
	    		return re;
		}
		return "2";
	}
}
