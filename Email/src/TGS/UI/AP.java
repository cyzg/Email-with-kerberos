package TGS.UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class AP extends JFrame {

	private JPanel contentPane;
	
	private JTextArea textArea_receive;
	private JTextArea textArea_send;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AP frame = new AP();
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
	public AP() {
		setTitle("AS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 757, 604);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel receive = new JLabel("\u63A5\u6536");
		receive.setBounds(36, 126, 76, 37);
		receive.setFont(new Font("·ÂËÎ", Font.PLAIN, 27));
		contentPane.add(receive);
		
		JLabel send = new JLabel("\u53D1\u9001");
		send.setBounds(36, 372, 76, 37);
		send.setFont(new Font("·ÂËÎ", Font.PLAIN, 27));
		contentPane.add(send);
		
		JButton clean = new JButton("\u6E05\u7A7A");
		contentPane.add(clean);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(126, 281, 546, 209);
		contentPane.add(scrollPane_1);
		
		textArea_send = new JTextArea();
		textArea_send.setWrapStyleWord(true);
		textArea_send.setLineWrap(true);
		scrollPane_1.setViewportView(textArea_send);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(126, 39, 546, 209);
		contentPane.add(scrollPane);
		
		textArea_receive = new JTextArea();
		textArea_receive.setWrapStyleWord(true);
		textArea_receive.setLineWrap(true);
		scrollPane.setViewportView(textArea_receive);
		clean.setBounds(637, 509, 85, 37);
		clean.setFont(new Font("·ÂËÎ", Font.PLAIN, 20));
		clean.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				if(textArea_send.getText() != null ||textArea_receive.getText() != null)
				{
					textArea_send.setText(null);
					textArea_receive.setText(null);				
				}
				
			}
		});
	}

	
	public JTextArea getText_receive() {
		return textArea_receive;
	}

	public void setText_receive(String s) {
		if(!textArea_receive.getText().equals(""))
		this.textArea_receive.setText(textArea_receive.getText()+"\n"+s); 
		else this.textArea_receive.setText(s);
	}

	public String getText_send() {
		return textArea_send.getText();
	}

	public void setText_send(String s) {
		if(!textArea_send.getText().equals(""))
		this.textArea_send.setText(textArea_send.getText()+"\r\n"+s); 
		else this.textArea_send.setText(s);
	}

}
