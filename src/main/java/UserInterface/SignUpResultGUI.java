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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Toolkit;

public class SignUpResultGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textGoogleEmail;
	private JTextField textGooglePassword;
	private JTextField textDropboxMail;
	private JTextField textDropboxPassword;
	public JButton buttonOk;

	/**
	 * Create the frame.
	 */
	public SignUpResultGUI(String googleMail, String googlePassword, String dropboxMail, String dropboxPassword) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SignUpResultGUI.class.getResource("/UserInterface/twincloud.png")));
		setResizable(false);
		setTitle("Steps");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 614, 401);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel label = new JLabel();
		label.setText("Twin Cloud");
		label.setForeground(new Color(255, 153, 0));
		label.setFont(new Font("Segoe Print", Font.BOLD, 24));
		
		JLabel lblUseFollowing = new JLabel();
		lblUseFollowing.setText("1. Use following e-mail address and password to create an account on Google. ");
		lblUseFollowing.setForeground(new Color(255, 153, 0));
		lblUseFollowing.setFont(new Font("Segoe Print", Font.BOLD, 14));
		
		JLabel lblUseFollowing_1 = new JLabel();
		lblUseFollowing_1.setText("2. Use following e-mail address and password to create an account on Dropbox.");
		lblUseFollowing_1.setForeground(new Color(255, 153, 0));
		lblUseFollowing_1.setFont(new Font("Segoe Print", Font.BOLD, 14));
		
		JLabel lblVerifyYour = new JLabel();
		lblVerifyYour.setText("3. Verify your Dropbox account from the mail on Gmail.");
		lblVerifyYour.setForeground(new Color(255, 153, 0));
		lblVerifyYour.setFont(new Font("Segoe Print", Font.BOLD, 14));
		
		buttonOk = new JButton();
		buttonOk.setText("Ok");
		buttonOk.setForeground(new Color(255, 153, 0));
		buttonOk.setFont(new Font("Segoe Print", Font.BOLD, 14));
		buttonOk.setBackground(Color.WHITE);
		
		JLabel lblEmail = new JLabel();
		lblEmail.setText("E-mail:");
		lblEmail.setForeground(new Color(255, 153, 0));
		lblEmail.setFont(new Font("Segoe Print", Font.BOLD, 14));
		
		JLabel lblPassword = new JLabel();
		lblPassword.setText("Password:");
		lblPassword.setForeground(new Color(255, 153, 0));
		lblPassword.setFont(new Font("Segoe Print", Font.BOLD, 14));
		
		JLabel label_1 = new JLabel();
		label_1.setText("E-mail:");
		label_1.setForeground(new Color(255, 153, 0));
		label_1.setFont(new Font("Segoe Print", Font.BOLD, 14));
		
		JLabel label_2 = new JLabel();
		label_2.setText("Password:");
		label_2.setForeground(new Color(255, 153, 0));
		label_2.setFont(new Font("Segoe Print", Font.BOLD, 14));
		
		textGoogleEmail = new JTextField();
		textGoogleEmail.setText("google email");
		textGoogleEmail.setFont(new Font("Segoe Print", Font.PLAIN, 15));
		
		textGooglePassword = new JTextField();
		textGooglePassword.setText("google password");
		textGooglePassword.setFont(new Font("Segoe Print", Font.PLAIN, 15));
		
		textDropboxMail = new JTextField();
		textDropboxMail.setText("dropbox email");
		textDropboxMail.setFont(new Font("Segoe Print", Font.PLAIN, 15));
		
		textDropboxPassword = new JTextField();
		textDropboxPassword.setText("dropbox password");
		textDropboxPassword.setFont(new Font("Segoe Print", Font.PLAIN, 15));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblUseFollowing, GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(10)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblEmail)
										.addComponent(lblPassword))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(textGooglePassword)
										.addComponent(textGoogleEmail, GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE))
									.addGap(47))))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblVerifyYour, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 590, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(233)
									.addComponent(buttonOk, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblUseFollowing_1, GroupLayout.PREFERRED_SIZE, 590, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(10)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
										.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(textDropboxPassword)
										.addComponent(textDropboxMail, GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(204)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblUseFollowing, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEmail, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(textGoogleEmail, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(textGooglePassword, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblUseFollowing_1, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(textDropboxMail, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
							.addComponent(lblVerifyYour, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(buttonOk, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
							.addComponent(textDropboxPassword, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
		setLocationRelativeTo(null);
		
		textGoogleEmail.setText(googleMail);
		textGooglePassword.setText(googlePassword);
		textDropboxMail.setText(dropboxMail);
		textDropboxPassword.setText(dropboxPassword);
		
	}

}
