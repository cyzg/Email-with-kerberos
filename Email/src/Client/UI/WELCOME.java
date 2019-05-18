package Client.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
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
	private JTextField txt1=new JTextField();
	private JTextField txt2= new JTextField();
	
	
	private String text1;
	private String text2;
	private JTextArea textField_2;

	
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
		setTitle("                           Welcome");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 300, 1000, 579);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel ID = new JLabel("User\uFF1A");
		ID.setBounds(43, 76, 72, 18);
		ID.setFont(new Font("仿宋", Font.PLAIN, 18));
		
		JLabel password = new JLabel("\u5BC6\u7801\uFF1A");
		password.setBounds(43, 127, 72, 18);
		password.setFont(new Font("仿宋", Font.PLAIN, 18));
		
		textField = new JTextField();
		textField.setBounds(129, 73, 176, 24);
		textField.setFont(new Font("仿宋", Font.PLAIN, 18));
		textField.setColumns(10);
		textField_1 = new JTextField();
		textField_1.setBounds(129, 126, 176, 24);
		textField_1.setFont(new Font("仿宋", Font.PLAIN, 18));
		textField_1.setColumns(10);
		
		textField_2 = new JTextArea();
		textField_2.setForeground(Color.BLACK);
		textField_2.setWrapStyleWord(true);
		textField_2.setLineWrap(true);
		textField_2.setBounds(411, 37, 219, 184);
		contentPane.add(textField_2);
		textField_2.setEnabled(false);
		textField_2.setEditable(false);
		textField_2.setColumns(10);
	
		
		JButton button = new JButton("\u6CE8\u518C");
		button.setBounds(296, 13, 72, 27);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				LOG frame1=new LOG();
				frame1.setVisible(true);
				
			}
		});
		button.setFont(new Font("仿宋", Font.PLAIN, 18));
		
		JButton button_1 = new JButton("\u767B\u5F55");
		button_1.setBounds(296, 194, 72, 27);
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String s = null;                         
				s = loginrun();				
				if((s.equals("1")) ) 
					System.err.println("账号或密码错误！");
				else{
					System.out.println("账号密码正确！");
					try {
						if(kerberos(s))
						{
							Send_email frame3 = new Send_email();
							frame3.setVisible(true);
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
			
		JLabel label = new JLabel("\u6D88\u606F\u65E5\u5FD7");
		label.setBounds(474, 17, 72, 18);
		label.setFont(new Font("仿宋", Font.PLAIN, 18));
		contentPane.setLayout(null);
		contentPane.add(ID);
		contentPane.add(password);
		contentPane.add(textField);
		contentPane.add(button);
		contentPane.add(button_1);
		contentPane.add(textField_1);
		contentPane.add(textField_2);
		contentPane.add(label);
	}
	
	public String getText1(){
		return text1;
	}
	public String getText2(){
		return text2;
	}
	String loginrun()
	{
		DataStruct.Package p = Client.login(textField.getText(),textField_1.getText());
		String send = p.getHead().headOutput()+p.packageOutput();
		String re = null;
    	//发给AS
		try {
	        Socket socket = new Socket("192.168.1.111",5555);
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

	boolean kerberos(String id) throws UnknownHostException, IOException
	{
		System.out.println("--client--");

		DataStruct.Package p= new DataStruct.Package();

		String clientID = id;
    	String clientIP = "";
		String TS1 = DataStruct.Package.Create_TS();
		
		p = Client.clientToAS(clientID, Client.TGSID );
		System.out.println("发给AS的包："+p.toString());
		textField_2.setText("发给AS的包"+p.toString());
		textField_2.setText(textField_2.getText()+"\r\n-------连接AS--------");
		System.out.println("-------连接AS--------");
        
        //发给AS
    	/**/Socket socket = new Socket("192.168.1.111",5555);
        String message =Client.packageToBinary(p);
        String s = "";
        if(Client.send(socket,message)) {
        	s = Client.receive(socket);
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	if(s == "")
        	{
        		System.err.println("未收到内容，请重新发送");
            	socket.close();
        	}
        	socket.close();
        } 

        DataStruct.Package p2= Client.packageAnalyse(s, null);
		textField_2.setText("收到AS的包："+p2.toString());
    	if(p2 == null) {
    		System.err.println("AS发送的包有误，请重新发送！！");
    	}
        
		System.out.println("-------连接TGS--------");
    	clientIP = DataStruct.Package.ipToBinary(Client.getIpAddress());

    	p = Client.clientToTGS(clientID, Client.SERVERID, p2.getTicket(), Client.generateAuth(p.getID(),clientIP,p2.getSessionKey()));
    	System.out.println("发给TGS的包"+p);
        message =Client.packageToBinary(p);
    	//发给TGS
        socket = new Socket("192.168.1.106",5555);
        if(Client.send(socket,message)) {
        	s = Client.receive(socket);
        	socket.close();
        } 
        DataStruct.Package p3= Client.packageAnalyse(s, p2.getSessionKey());
    	
		System.out.println("-------连接V--------");
    	p = Client.clentToV(clientID, p3.getTicket(), Client.generateAuth(p3.getHead().getDestID(),clientIP,p3.getSessionKey()));
    	String TSv = p.getTimeStamp();
    	p.setTimeStamp("");
    	
    	System.out.println("发给V的包"+p);
    	
        message =Client.packageToBinary(p);
    	
    	//发给V
        socket = new Socket("192.168.1.101",5555);
    	
    	if(Client.send(socket,message)) {
    	s = Client.receive(socket);
    	socket.close();
    	}
    	DataStruct.Package p4= Client.packageAnalyse(s, p2.getSessionKey());
    	if(Client.verifyPackage(p4, TSv)) {
    		System.out.println("kerberos认证成功，进入Email！");
    		return true;
    	}
    	else {
    		System.err.println("kerberos认证失败，请重试");
    		return false;
    	}	
	}
}
