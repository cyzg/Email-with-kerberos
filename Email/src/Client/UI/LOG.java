package Client.UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Client.Client;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class LOG extends JFrame {
	private JPanel back;
	private JTextField name;
	private JTextField pw;
	String Sk[];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LOG frame1 = new LOG();
					frame1.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LOG() {
		setTitle("\u65B0\u7528\u6237\u6CE8\u518C");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(650, 400, 428, 284);
		back = new JPanel();
		back.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(back);
		back.setLayout(null);
		
		JLabel ID = new JLabel("User\uFF1A");
		ID.setBounds(72, 55, 54, 39);
		ID.setFont(new Font("·ÂËÎ", Font.PLAIN, 18));
		back.add(ID);
		
		JLabel password = new JLabel("\u5BC6\u7801\uFF1A");
		password.setFont(new Font("·ÂËÎ", Font.PLAIN, 18));
		password.setBounds(72, 131, 72, 18);
		back.add(password);
		
		name = new JTextField();
		name.setFont(new Font("·ÂËÎ", Font.PLAIN, 18));
		name.setColumns(10);
		name.setBounds(156, 62, 176, 24);
		back.add(name);
		
		JButton log = new JButton("\u6CE8\u518C");
		log.setFont(new Font("·ÂËÎ", Font.PLAIN, 18));
		log.setBounds(219, 193, 72, 27);
		back.add(log);
		
		JButton return_back = new JButton("\u8FD4\u56DE");
		return_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {                                //·µ»Ø°´Å¥
				dispose();
				WELCOME frame = new WELCOME();
				frame.setVisible(true);
				setVisible(false);
			}
		});
		return_back.setFont(new Font("·ÂËÎ", Font.PLAIN, 18));
		return_back.setBounds(324, 193, 72, 27);
		back.add(return_back);
		
		pw = new JTextField();
		pw.setFont(new Font("·ÂËÎ", Font.PLAIN, 18));
		pw.setColumns(10);
		pw.setBounds(156, 128, 176, 24);
		back.add(pw);

		log.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {      		  //×¢²á°´Å¥S
				String s = "";                         
				s = signinrun();
				if(s == "2") {
					System.err.println("ÓÃ»§ÃûºÍÃÜÂë²»ÄÜÎª¿Õ£¬×¢²áÊ§°Ü£¡");
					Not_null frame7 = new Not_null();
					frame7.setVisible(true);
				}
				else if((s.equals("1")) ) {
					System.err.println("¸ÃidÒÑ±»×¢²á£¡");
					ID_USED id_ueser = new ID_USED();
					id_ueser.setVisible(true);
				}
				else if(s.equals("")) {
					System.err.println("×¢²áÊ§°Ü£¡");
					SIGN_FALSE sign_false = new SIGN_FALSE();
					sign_false.setVisible(true);
				}
				else{
					System.out.println("×¢²á³É¹¦£¡");
					dispose();
					Succcess frame2 = new Succcess();
					frame2.setVisible(true);
				}
			}
		});
	}
	String signinrun()
	{
		String k[][] = RSA.keymanger.keymanger();
		Sk = k[1];
		System.out.println(name.getText());
		if(!(name.getText().equals("") || pw.getText().equals(""))) {
				
			DataStruct.Package p = Client.signin(name.getText(),pw.getText(),k[0]);
			String send = p.getHead().headOutput()+p.packageOutput();
			String re = null;
	    	//·¢¸øAS
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
		else return "2";
	}
}

