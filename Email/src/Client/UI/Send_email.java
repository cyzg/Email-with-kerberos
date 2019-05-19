package Client.UI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Client.CReceiver;
import Client.Client;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Send_email extends JFrame {
	private JPanel contentPane;
	private JTextField textField_sender;
	private JTextField textField_time;
	JTextArea textArea_message;
	private String id;

	public Send_email(String id) throws HeadlessException {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Send_email frame3 = new Send_email();
					frame3.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public 	Send_email() {
		setTitle("\u4EB2\u7231\u7684User");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(520, 320, 687, 356);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("\u9000\u51FA");
		button.setBounds(323, 7, 78, 27);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				WELCOME frame = new WELCOME();
				frame.setVisible(true);
				
			}
		});
		button.setFont(new Font("仿宋", Font.PLAIN, 18));
		contentPane.add(button);
		JButton history = new JButton("查看收件箱");
		history.addActionListener(new ActionListener() {                                                       //发邮件按钮    
			public void actionPerformed(ActionEvent e) {
				receive_email email = new receive_email();
				email.setVisible(true);
				email.setId(id);
				String message = Client.requestEmail(id);
				Socket socket;
				try {
					socket = new Socket(Client.SERVERIP,5555);
				boolean send = Client.send(socket,message);
					if(send) {//发送一条请求 请求接收历史邮件
						try {
							System.out.println(id);
				        	String rmessage = Client.receive(socket);
				        	int num = Integer.parseInt(Client.BinaryToString(rmessage));
				        	if(num != 0)
				        	{
				        		new Thread(CReceiver.listener(6666,email,num)).start();
							}
				        	else System.out.println("没有收到邮件");
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	//收历史邮件
						socket.close();
					}
					else {
						socket.close();
					}
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		history.setBounds(37, 271, 130, 27);
		history.setFont(new Font("仿宋", Font.PLAIN, 18));
		contentPane.add(history);
		
		
		JLabel label = new JLabel("\u6D88\u606F\u65E5\u5FD7");
		label.setBounds(505, 11, 87, 18);
		label.setFont(new Font("仿宋", Font.PLAIN, 18));
		contentPane.add(label);
		
		JScrollPane scrollPane = new JScrollPane();//消息日志
		scrollPane.setBounds(434, 33, 221, 265);
		contentPane.add(scrollPane);
		
		textArea_message = new JTextArea();
		textArea_message.setEditable(false);
		textArea_message.setLineWrap(true);
		scrollPane.setViewportView(textArea_message);
		
		JScrollPane scrollPane_1 = new JScrollPane();  //写邮件
		scrollPane_1.setBounds(37, 81, 371, 177);
		contentPane.add(scrollPane_1);
		
		JTextArea textArea_write = new JTextArea();
		textArea_write.setLineWrap(true);
		textArea_write.setWrapStyleWord(true);
		scrollPane_1.setViewportView(textArea_write);
		
		JLabel label_sender = new JLabel("\u53D1\u4EF6\u4EBA\uFF1A");
		label_sender.setFont(new Font("仿宋", Font.PLAIN, 18));
		label_sender.setBounds(37, 47, 80, 18);
		contentPane.add(label_sender);
		
		JLabel label_time = new JLabel("\u53D1\u4EF6\u65F6\u95F4\uFF1A");
		label_time.setFont(new Font("仿宋", Font.PLAIN, 18));
		label_time.setBounds(177, 47, 102, 18);
		contentPane.add(label_time);
		
		textField_sender = new JTextField();
		textField_sender.setBounds(103, 46, 50, 24);
		contentPane.add(textField_sender);
		textField_sender.setColumns(10);
		
		textField_time = new JTextField();
		textField_time.setEditable(false);
		textField_time.setColumns(10);
		textField_time.setBounds(260, 46, 150, 24);
		contentPane.add(textField_time);
		

		JButton write = new JButton("\u5199Email");
		write.setBounds(301, 271, 102, 27);
		write.setFont(new Font("仿宋", Font.PLAIN, 18));
		contentPane.add(write);
		write.addActionListener(new ActionListener() {                                                       //发邮件按钮    
			public void actionPerformed(ActionEvent e) {
				String receiveid = textField_sender.getText();
				String content = textArea_write.getText();
				String TS = DataStruct.Package.Create_TS();
				char M[] = TS.toCharArray();
				textField_time.setText(""+M[0]+M[1]+M[2]+M[3]+"-"+M[4]+M[5]+"-"+M[6]+M[7]+" "+M[8]+M[9]+":"+M[10]+M[11]+":"+M[12]+M[13]);
				setmessage("-----发送邮件-----");
				setmessage("Receive:"+receiveid);
				setmessage("Time:"+textField_time.getText());
				setmessage("Email:"+content);
				DataStruct.APPPackage psend= Client.connect(id,receiveid,content,TS);
				String message =Client.appackageToBinary(psend);
				String rmessage = "";
				Socket ss;
				try {
					ss = new Socket(Client.SERVERIP,5555);
		    	if(Client.send(ss,message)) {
		    		rmessage = Client.receive(ss);
		    		ss.close();
				}
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
		    	}
				//String s1 =appackageToBinary(connect(0003,0004,"654321"));
				DataStruct.APPPackage p = Client.apppackageAnalyse(rmessage);
				if(Client.verifyPackage(p, psend.getTimeStamp())) {
					System.out.println("发送成功！");
					Send_success frame= new Send_success();
					frame.setVisible(true);
					
				}
				else {
					System.out.println("发送失败！");
					Send_fail frame= new Send_fail();
					frame.setVisible(true);
				}
			}
		});
		
	}
	public void setmessage(String s) {
		if(!textArea_message.getText().equals(""))
		this.textArea_message.setText(textArea_message.getText()+"\r\n"+s); 
		else this.textArea_message.setText(s);
	}
}
