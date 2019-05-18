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

public class ID_USED extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ID_USED frame6 = new ID_USED();
					frame6.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ID_USED() {
		setTitle("\u63D0\u793A");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(650, 400, 404, 272);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("\u8FD4\u56DE");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setBounds(279, 182, 79, 29);
		button.setFont(new Font("·ÂËÎ", Font.PLAIN, 18));
		contentPane.add(button);
		
		JLabel lblid = new JLabel("\u8BE5ID\u5DF2\u88AB\u6CE8\u518C\uFF01");
		lblid.setFont(new Font("·ÂËÎ", Font.PLAIN, 24));
		lblid.setBounds(101, 45, 189, 62);
		contentPane.add(lblid);
		
		JLabel lblid_1 = new JLabel("\u8BF7\u66F4\u6362ID\u91CD\u65B0\u6CE8\u518C\uFF01");
		lblid_1.setFont(new Font("·ÂËÎ", Font.PLAIN, 24));
		lblid_1.setBounds(88, 107, 224, 62);
		contentPane.add(lblid_1);
	}

}
