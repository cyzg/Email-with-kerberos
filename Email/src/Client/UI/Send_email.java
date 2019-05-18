package Client.UI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class Send_email extends JFrame {
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

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
		setBounds(100, 100, 687, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setForeground(Color.WHITE);
		scrollBar.setBounds(355, 74, 21, 128);
		contentPane.add(scrollBar);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(37, 74, 339, 128);
		contentPane.add(textField_1);
		
		textField = new JTextField();
		textField.setText("\u53D1\u4EF6\u4EBA\uFF1A                   \u53D1\u4EF6\u65F6\u95F4\uFF1A");
		textField.setFont(new Font("·ÂËÎ", Font.PLAIN, 18));
		textField.setEnabled(false);
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(37, 46, 339, 27);
		contentPane.add(textField);
		
		JButton button = new JButton("\u9000\u51FA");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				WELCOME frame = new WELCOME();
				frame.setVisible(true);
				
			}
		});
		button.setFont(new Font("·ÂËÎ", Font.PLAIN, 18));
		button.setBounds(323, 7, 78, 27);
		contentPane.add(button);
		
		JButton button_1 = new JButton("\u5199Email");
		button_1.setFont(new Font("·ÂËÎ", Font.PLAIN, 18));
		button_1.setBounds(301, 215, 102, 27);
		contentPane.add(button_1);
		
		JLabel label = new JLabel("\u6D88\u606F\u65E5\u5FD7");
		label.setFont(new Font("·ÂËÎ", Font.PLAIN, 18));
		label.setBounds(505, 11, 87, 18);
		contentPane.add(label);
		
		textField_2 = new JTextField();
		textField_2.setEnabled(false);
		textField_2.setEditable(false);
		textField_2.setColumns(10);
		textField_2.setBounds(434, 33, 221, 209);
		contentPane.add(textField_2);
	}
}
