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

public class Send_fail extends JFrame{
	private JPanel contentPane;
	private final JLabel label = new JLabel("\u53D1\u9001\u5931\u8D25\uFF01");
	/**
	 * Create the frame.
	 */
	public Send_fail() {
		setTitle("\u63D0\u793A");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(650,400, 397, 267);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		label.setBounds(127, 96, 138, 36);
		contentPane.add(label);
		label.setFont(new Font("·ÂËÎ", Font.PLAIN, 24));
		
		JButton button = new JButton("\u8FD4\u56DE");
		button.addActionListener(new ActionListener() {                                                           //°´Å¥
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.setFont(new Font("·ÂËÎ", Font.PLAIN, 18));
		button.setBounds(286, 181, 79, 29);
		contentPane.add(button);
	}

}
