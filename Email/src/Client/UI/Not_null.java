package Client.UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Not_null extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Not_null frame7 = new Not_null();
					frame7.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Not_null() {
		setTitle("\u63D0\u793A");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(650, 400, 436, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u7528\u6237\u540D\u6216\u5BC6\u7801\u4E0D\u80FD\u4E3A\u7A7A\uFF01");
		label.setFont(new Font("·ÂËÎ", Font.PLAIN, 24));
		label.setBounds(78, 60, 288, 74);
		contentPane.add(label);
		
		JButton button = new JButton("\u8FD4\u56DE");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {  
				dispose();	
			}
		});
		button.setFont(new Font("·ÂËÎ", Font.PLAIN, 18));
		button.setBounds(325, 213, 79, 29);
		contentPane.add(button);
		
		JLabel label_1 = new JLabel("\u8BF7\u91CD\u65B0\u8F93\u5165\uFF01");
		label_1.setFont(new Font("·ÂËÎ", Font.PLAIN, 24));
		label_1.setBounds(135, 128, 187, 46);
		contentPane.add(label_1);
	}
}
