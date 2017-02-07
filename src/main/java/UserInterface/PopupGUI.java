package UserInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Toolkit;

public class PopupGUI extends JFrame {

	private JPanel contentPane;
	public JTextField textInput;
	public JButton buttonSecond;
	public JButton buttonFirst;
	public JLabel labelFirst;
	JLabel labelSecond;
	

	/**
	 * Create the frame.
	 */
	public PopupGUI(String frameTitle, String label1, String label2, String button1, String button2) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PopupGUI.class.getResource("/UserInterface/twincloud.png")));
		setTitle(frameTitle);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 279, 211);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		buttonFirst = new JButton();
		buttonFirst.setText(button1);
		buttonFirst.setForeground(new Color(255, 153, 0));
		buttonFirst.setFont(new Font("Segoe Print", Font.BOLD, 12));
		buttonFirst.setBackground(Color.WHITE);
		
		buttonSecond = new JButton();
		buttonSecond.setText(button2);
		buttonSecond.setForeground(new Color(255, 153, 0));
		buttonSecond.setFont(new Font("Segoe Print", Font.BOLD, 12));
		buttonSecond.setBackground(Color.WHITE);
		
		textInput = new JTextField();
		textInput.setFont(new Font("Tahoma", Font.BOLD, 18));
		textInput.setColumns(10);
		
		labelFirst = new JLabel(label1);
		labelFirst.setFont(new Font("Segoe Print", Font.PLAIN, 12));
		labelFirst.setForeground(new Color(255, 153, 0));
		
		labelSecond = new JLabel(label2);
		labelSecond.setForeground(new Color(255, 153, 0));
		labelSecond.setFont(new Font("Segoe Print", Font.PLAIN, 12));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(labelFirst, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(buttonFirst)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(buttonSecond, GroupLayout.PREFERRED_SIZE, 93, Short.MAX_VALUE))
						.addComponent(labelSecond, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
						.addComponent(textInput, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(labelFirst)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(labelSecond, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textInput, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonFirst)
						.addComponent(buttonSecond))
					.addContainerGap(12, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
		setLocationRelativeTo(null);
	}
}
