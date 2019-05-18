package AS.UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;

public class AP extends JFrame {

	private JPanel contentPane;
	private JTextArea text_receive;
	private JTextArea text_send;
	private JTextField txt1=new JTextField();
	private JTextField txt2=new JTextField();

	private String text1;
	private String text2;
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
		setTitle("AP");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 757, 604);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollBar scrollBar_1 = new JScrollBar();
		scrollBar_1.setBounds(651, 281, 21, 209);
		contentPane.add(scrollBar_1);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(651, 45, 21, 209);
		contentPane.add(scrollBar);
		
		JLabel receive = new JLabel("\u63A5\u6536");
		receive.setBounds(36, 126, 76, 37);
		receive.setFont(new Font("·ÂËÎ", Font.PLAIN, 27));
		contentPane.add(receive);
		
		text_receive = new JTextArea();
		text_receive.setWrapStyleWord(true);
		text_receive.setLineWrap(true);
		text_receive.setBounds(126, 45, 546, 209);
		text_receive.setColumns(10);
		contentPane.add(text_receive);
		
		text_send = new JTextArea();
		text_send.setWrapStyleWord(true);
		text_send.setLineWrap(true);
		text_send.setColumns(10);
		text_send.setBounds(126, 281, 546, 209);
		contentPane.add(text_send);
		
		JLabel send = new JLabel("\u53D1\u9001");
		send.setFont(new Font("·ÂËÎ", Font.PLAIN, 27));
		send.setBounds(36, 372, 76, 37);
		contentPane.add(send);
		
		JButton clean = new JButton("\u6E05\u7A7A");
		clean.setFont(new Font("·ÂËÎ", Font.PLAIN, 20));
		clean.addActionListener(new ActionListener() {
		
			public void actionPerformed(ActionEvent e) {
				if(text1!=null||text2!=null)
				{
				txt1.setText(null);
				txt2.setText(null);
//				txt1.setVisible(false);
//				txt2.setVisible(false);
				
				}
				
			}
		});
		clean.setBounds(637, 509, 85, 37);
		contentPane.add(clean);
	}
	
	public JTextArea getText_receive() {
		return text_receive;
	}

	public void setText_receive(String s) {
//		if(text_receive.getText() != null)
		this.text_receive.setText(s); 
//		else this.text_receive.setText(s);
	}

	public JTextArea getText_send() {
		return text_send;
	}

	public void setText_send(JTextArea text_send) {
		this.text_send = text_send;
	}

	public String getText1(){
		return text1;
	}
	public String getText2(){
		return text2;
	}
}
