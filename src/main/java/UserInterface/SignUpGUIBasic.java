package UserInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import Security.CryptoUtils;
import UserAccountOperations.SignUp;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;

public class SignUpGUIBasic extends JFrame {

	private JPanel contentPane;
	public JPasswordField textPassword;
	public JTextField textUserName;
	public JButton buttonSignUp;

	/**
	 * Create the frame.
	 */
	public SignUpGUIBasic() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(SignUpGUIBasic.class.getResource("/UserInterface/twincloud.png")));
		setTitle("Sign Up");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 401, 342);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel label = new JLabel();
		label.setText("Twin Cloud");
		label.setForeground(new Color(255, 153, 0));
		label.setFont(new Font("Segoe Print", Font.BOLD, 24));
		
		buttonSignUp = new JButton();
//		buttonSignUp.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//				// TODO Username'i kontrol et Google'in sitesinden.
//				
//				CryptoUtils cryptoUtils = new CryptoUtils();
//				String googlePassword = cryptoUtils.derivePassword(textPassword.getText(), textUserName.getText() + "@gmail.com", "www.google.com");
//				String dropboxPassword = cryptoUtils.derivePassword(textPassword.getText(), textUserName.getText() + "@gmail.com", "www.dropbox.com");
//				
//				SignUpResultGUI resultGUI = new SignUpResultGUI(textUserName.getText() + "@gmail.com", googlePassword, textUserName.getText() + "@gmail.com", dropboxPassword);
//				resultGUI.setVisible(true);
//				
//				resultGUI.buttonOk.addActionListener(new ActionListener() {
//					public void actionPerformed(ActionEvent e) {
//						resultGUI.setVisible(false);
//						resultGUI.dispose();
//						setVisible(false);
//						dispose();
//					}
//				});
//			}
//		});
		buttonSignUp.setText("Sign Up");
		buttonSignUp.setForeground(new Color(255, 153, 0));
		buttonSignUp.setFont(new Font("Segoe Print", Font.BOLD, 14));
		buttonSignUp.setBackground(Color.WHITE);
		
		textPassword = new JPasswordField();
		textPassword.setText("twin_cloud");
		textPassword.setFont(new Font("Segoe Print", Font.PLAIN, 16));
		textPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textPassword.getText().equals("twin_cloud")) {
					textPassword.setText("");
				}
			}
		});
		
		textUserName = new JTextField();
		textUserName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textUserName.getText().equals("Username")) {
					textUserName.setText("");
				}
			}
		});
		textUserName.setText("Username");
		textUserName.setFont(new Font("Segoe Print", Font.PLAIN, 16));
		
		JLabel lblCreateAccountOn = new JLabel();
		lblCreateAccountOn.setText("Create account on Twin Cloud ");
		lblCreateAccountOn.setForeground(new Color(255, 153, 0));
		lblCreateAccountOn.setFont(new Font("Segoe Print", Font.BOLD, 18));
		
		JLabel lblAtLeast = new JLabel();
		lblAtLeast.setText("At least 8 characters and must contains both numbers and letters.");
		lblAtLeast.setForeground(new Color(255, 153, 0));
		lblAtLeast.setFont(new Font("Segoe Print", Font.PLAIN, 10));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(textUserName, GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
								.addComponent(textPassword, GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(104)
									.addComponent(label, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap(7, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(buttonSignUp, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
							.addGap(130))))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(44)
					.addComponent(lblCreateAccountOn)
					.addContainerGap(52, Short.MAX_VALUE))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblAtLeast, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblCreateAccountOn, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(textUserName, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(textPassword, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblAtLeast, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addComponent(buttonSignUp, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		setLocationRelativeTo(null);
	}
}
