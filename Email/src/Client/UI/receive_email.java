package Client.UI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

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
		
//		JButton button_send_email = new JButton("\u53D1\u90AE\u4EF6");
//		button_send_email.addActionListener(new ActionListener() {                                                       //发邮件按钮    
//			public void actionPerformed(ActionEvent e) {
//				Send_email send_email = new Send_email();
//				send_email.setVisible(true);
//			}
//		});
//		
//		
//		button_send_email.setFont(new Font("仿宋", Font.PLAIN, 18));
//		button_send_email.setBounds(384, 73, 90, 36);
//		contentPane.add(button_send_email);
		
		JButton button_refresh = new JButton("返回");
		button_refresh.setFont(new Font("仿宋", Font.PLAIN, 18));
		button_refresh.setBounds(384, 170, 87, 36);
		contentPane.add(button_refresh);
		button_refresh.addActionListener(new ActionListener() {                                                      //刷新按钮
			public void actionPerformed(ActionEvent e) {
				dispose();
				WELCOME frame = new WELCOME();
				frame.setVisible(true);
			}
		});;
	}
	public void getEmail(String s) {
		if(!textArea_rece_email.getText().equals(""))
		this.textArea_rece_email.setText(textArea_rece_email.getText()+"\r\n"+s); 
		else this.textArea_rece_email.setText(s);
	}
}
