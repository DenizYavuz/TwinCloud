package UserInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import UserAccountOperations.SignUp;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class SignUpGUISimple extends JFrame {

	private JPanel contentPane;
	private JTextField textUserName;
	private JPasswordField textPassword;
	
	SignUp signup = null;
	
	public void signUp() {
		signup.googleSignUpSimple();
	}

	public SignUpGUISimple() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SignUpGUISimple.class.getResource("/UserInterface/twincloud.png")));
		signup = new SignUp();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 471, 336);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel label = new JLabel();
		label.setText("TwinCloud");
		label.setForeground(new Color(255, 153, 0));
		label.setFont(new Font("Segoe Print", Font.BOLD, 24));
		
		JLabel label_1 = new JLabel();
		label_1.setText("Create an account on TwinCLoud ");
		label_1.setForeground(new Color(255, 153, 0));
		label_1.setFont(new Font("Segoe Print", Font.BOLD, 18));
		
		textUserName = new JTextField();
		textUserName.setText("Username");
		textUserName.setFont(new Font("Segoe Print", Font.PLAIN, 16));
		textUserName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textUserName.getText().equals("Username"))
					textUserName.setText("");
			}
		});
		
		textPassword = new JPasswordField();
		textPassword.setText("password123");
		textPassword.setFont(new Font("Segoe Print", Font.PLAIN, 16));
		textPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textPassword.getText().equals("password123"))
					textPassword.setText("");
			}
		});
		
		JButton buttonSignUp = new JButton();
		buttonSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signUp();
			}
		});
		buttonSignUp.setText("Sign Up");
		buttonSignUp.setForeground(new Color(255, 153, 0));
		buttonSignUp.setFont(new Font("Segoe Print", Font.BOLD, 14));
		buttonSignUp.setBackground(Color.WHITE);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(139)
									.addComponent(label, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(55)
									.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 311, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(textUserName, GroupLayout.PREFERRED_SIZE, 419, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(textPassword, GroupLayout.PREFERRED_SIZE, 419, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(160)
							.addComponent(buttonSignUp, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(16, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(54, Short.MAX_VALUE)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(textUserName, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textPassword, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(buttonSignUp, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addGap(333))
		);
		contentPane.setLayout(gl_contentPane);
		setLocationRelativeTo(null);
	}
}
