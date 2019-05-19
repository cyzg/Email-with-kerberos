package Client.UI;

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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import Client.CReceiver;
import Client.Client;

public class receive_email extends JFrame{
	private JPanel contentPane;
	private JTextArea textArea_rece_email;
	private String id;
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					receive_email frame11 = new receive_email();
					frame11.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public receive_email(String id) throws HeadlessException {
		this.id = id;
	}

	/**
	 * Create the frame.
	 */
	public receive_email() {
		setTitle("亲爱的user");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(520, 320, 503, 325);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 40, 348, 208);
		contentPane.add(scrollPane);
		
		JLabel label_sender = new JLabel("收到的邮件：");
		label_sender.setFont(new Font("仿宋", Font.PLAIN, 18));
		label_sender.setBounds(1, 10, 200, 18);
		contentPane.add(label_sender);
		
		textArea_rece_email = new JTextArea();
		textArea_rece_email.setEditable(false);
		textArea_rece_email.setWrapStyleWord(true);
		textArea_rece_email.setLineWrap(true);
		scrollPane.setViewportView(textArea_rece_email);
		
		JButton button_send_email = new JButton("刷新");
		button_send_email.addActionListener(new ActionListener() {                                                       //发邮件按钮    
			public void actionPerformed(ActionEvent e) {receive_email email = new receive_email();
			email.setVisible(true);
			email.setId(id);
			String message = Client.requestEmail(id);
			String rmessage = "";
			Socket socket;
			try {
				socket = new Socket(Client.SERVERIP,5555);
				boolean send = Client.send(socket,message);
				if(send) {//发送一条请求 请求接收历史邮件
		        	rmessage = Client.receive(socket);
		        	int num = Integer.parseInt(Client.BinaryToString(rmessage));
					try {
						System.out.println(id);
						new Thread(CReceiver.listener(6666,email,num)).start();
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
		
		
		button_send_email.setFont(new Font("仿宋", Font.PLAIN, 18));
		button_send_email.setBounds(384, 73, 90, 36);
		contentPane.add(button_send_email);
		
		JButton button_refresh = new JButton("返回");
		button_refresh.setFont(new Font("仿宋", Font.PLAIN, 18));
		button_refresh.setBounds(384, 170, 87, 36);
		contentPane.add(button_refresh);
		button_refresh.addActionListener(new ActionListener() {                                                      //刷新按钮
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});;
	}
	
	public JTextArea getTextArea_rece_email() {
		return textArea_rece_email;
	}

	public void setTextArea_rece_email(JTextArea textArea_rece_email) {
		this.textArea_rece_email = textArea_rece_email;
	}

	public void getEmail(String s) {
		if(!textArea_rece_email.getText().equals(""))
		this.textArea_rece_email.setText(textArea_rece_email.getText()+"\r\n"+s); 
		else this.textArea_rece_email.setText(s);
	}
}
