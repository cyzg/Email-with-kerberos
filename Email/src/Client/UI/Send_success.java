package Client.UI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Send_success extends JFrame{
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Send_success frame9 = new Send_success();
					frame9.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Send_success() {
		setTitle("\u63D0\u793A");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(650,400, 413, 282);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u53D1\u9001\u6210\u529F\uFF01");
		label.setBounds(137, 61, 165, 117);
		label.setFont(new Font("仿宋", Font.PLAIN, 24));
		contentPane.add(label);
		
		JButton button = new JButton("\u8FD4\u56DE");
		button.addActionListener(new ActionListener() {                                                 //按钮，返回send_email界面
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		button.setFont(new Font("仿宋", Font.PLAIN, 18));
		button.setBounds(302, 196, 79, 29);
		contentPane.add(button);
	}
}
