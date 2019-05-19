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
		button.setFont(new Font("����", Font.PLAIN, 18));
		contentPane.add(button);
		JButton history = new JButton("�鿴�ռ���");
		history.addActionListener(new ActionListener() {                                                       //���ʼ���ť    
			public void actionPerformed(ActionEvent e) {

				receive_email email = new receive_email(id);
				email.setVisible(true);
				Socket socket = null;
				try {
					socket = new Socket(Client.SERVERID,5555);
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String message = Client.requestEmail(id);	
				
				try {
					if(Client.send(socket,message)) {//����һ������ ���������ʷ�ʼ�
						new Thread(CReceiver.listener(6666)).start();	//����ʷ�ʼ�
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}
		});
		history.setBounds(37, 271, 130, 27);
		history.setFont(new Font("����", Font.PLAIN, 18));
		contentPane.add(history);
		
		
		JLabel label = new JLabel("\u6D88\u606F\u65E5\u5FD7");
		label.setBounds(505, 11, 87, 18);
		label.setFont(new Font("����", Font.PLAIN, 18));
		contentPane.add(label);
		
		JScrollPane scrollPane = new JScrollPane();//��Ϣ��־
		scrollPane.setBounds(434, 33, 221, 265);
		contentPane.add(scrollPane);
		
		JTextArea textArea_message = new JTextArea();
		textArea_message.setEditable(false);
		textArea_message.setLineWrap(true);
		scrollPane.setViewportView(textArea_message);
		
		JScrollPane scrollPane_1 = new JScrollPane();  //д�ʼ�
		scrollPane_1.setBounds(37, 81, 371, 177);
		contentPane.add(scrollPane_1);
		
		JTextArea textArea_write = new JTextArea();
		textArea_write.setLineWrap(true);
		textArea_write.setWrapStyleWord(true);
		scrollPane_1.setViewportView(textArea_write);
		
		JLabel label_sender = new JLabel("\u53D1\u4EF6\u4EBA\uFF1A");
		label_sender.setFont(new Font("����", Font.PLAIN, 18));
		label_sender.setBounds(37, 47, 72, 18);
		contentPane.add(label_sender);
		
		JLabel label_time = new JLabel("\u53D1\u4EF6\u65F6\u95F4\uFF1A");
		label_time.setFont(new Font("����", Font.PLAIN, 18));
		label_time.setBounds(213, 47, 102, 18);
		contentPane.add(label_time);
		
		textField_sender = new JTextField();
		textField_sender.setBounds(103, 46, 108, 24);
		contentPane.add(textField_sender);
		textField_sender.setColumns(10);
		
		textField_time = new JTextField();
		textField_time.setEditable(false);
		textField_time.setColumns(10);
		textField_time.setBounds(301, 46, 108, 24);
		contentPane.add(textField_time);
		

		JButton write = new JButton("\u5199Email");
		write.setBounds(301, 271, 102, 27);
		write.setFont(new Font("����", Font.PLAIN, 18));
		contentPane.add(write);
		write.addActionListener(new ActionListener() {                                                       //���ʼ���ť    
			public void actionPerformed(ActionEvent e) {
				String receiveid = textField_sender.getText();
				String content = textArea_write.getText();
				DataStruct.APPPackage psend= Client.connect(id,receiveid,content);
				String message =Client.appackageToBinary(psend);
				String rmessage = "";
				Socket socket;
				try {
					socket = new Socket(Client.SERVERIP,5555);
		    	if(Client.send(socket,message)) {
		    		rmessage = Client.receive(socket);
		    		socket.close();
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
					System.out.println("���ͳɹ���");
					Send_success frame= new Send_success();
					frame.setVisible(true);
					
				}
				else {
					System.out.println("����ʧ�ܣ�");
					Send_fail frame= new Send_fail();
					frame.setVisible(true);
				}
			}
		});
		
	}
	
}
