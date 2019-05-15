package Client.UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSplitPane;
import javax.swing.JDesktopPane;
import javax.swing.BoxLayout;
import java.awt.Label;
import java.awt.Font;
import javax.swing.JPasswordField;

public class LOG_SIGN extends JFrame {

	private JPanel contentPane;
	private JTextField massage;
	private JTextField user;
	private JPasswordField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LOG_SIGN frame = new LOG_SIGN();
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
	public LOG_SIGN() {
		setTitle("                             Welcome");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 687, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label_user = new JLabel("User:");
		label_user.setFont(new Font("·ÂËÎ", Font.PLAIN, 18));
		label_user.setBounds(59, 87, 63, 18);
		contentPane.add(label_user);
		
		JLabel label_password = new JLabel("\u5BC6\u7801\uFF1A");
		label_password.setFont(new Font("·ÂËÎ", Font.PLAIN, 18));
		label_password.setBounds(59, 154, 63, 18);
		contentPane.add(label_password);
		
		JButton sign = new JButton("\u6CE8\u518C");
		sign.setFont(new Font("·ÂËÎ", Font.PLAIN, 18));
		sign.setBounds(323, 32, 76, 27);
		sign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Success frame3 = new Success ();
				frame3.setVisible(true);
				
			}
		});
		contentPane.add(sign);
		
		massage = new JTextField();
		massage.setEditable(false);
		massage.setEnabled(false);
		massage.setBounds(434, 33, 221, 209);
		massage.setColumns(10);
		contentPane.add(massage);
		
		user = new JTextField();
		user.setColumns(10);
		user.setBounds(155, 84, 192, 24);
		contentPane.add(user);
		
		JLabel label_message = new JLabel("\u6D88\u606F\u65E5\u5FD7");
		label_message.setFont(new Font("·ÂËÎ", Font.PLAIN, 18));
		label_message.setBounds(504, 13, 76, 18);
		contentPane.add(label_message);
		
		JButton log = new JButton("\u767B\u5F55");
		log.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Email frame2 = new Email ();
				frame2.setVisible(true);
			}
		});
		log.setFont(new Font("·ÂËÎ", Font.PLAIN, 18));
		log.setBounds(323, 215, 76, 27);
		contentPane.add(log);
		
		password = new JPasswordField();
		password.setBounds(155, 153, 192, 24);
		contentPane.add(password);
	}
}
