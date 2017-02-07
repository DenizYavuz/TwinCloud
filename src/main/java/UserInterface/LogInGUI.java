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

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import FileOperations.FileOperations;
import FolderWatcher.WatchFolderForChanges;
import UserAccountOperations.LogIn;

import java.awt.Window.Type;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Toolkit;

public class LogInGUI extends JFrame {

	private JPanel contentPane;
	public JTextField textUserName;
	public JPasswordField textPassword;
	public JButton buttonLogin;
	
	public JFrame getFrame() {
		return this;
	}

	/**
	 * Create the frame.
	 */
	public LogInGUI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(LogInGUI.class.getResource("/UserInterface/twincloud.png")));
		
		setResizable(false);
		setTitle("Log In");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 390, 356);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblTwinCloud = new JLabel();
		lblTwinCloud.setText("Twin Cloud");
		lblTwinCloud.setForeground(new Color(255, 153, 0));
		lblTwinCloud.setFont(new Font("Segoe Print", Font.BOLD, 24));
		
		JLabel lblLogInTo = new JLabel();
		lblLogInTo.setText("Log in to Twin Cloud ");
		lblLogInTo.setForeground(new Color(255, 153, 0));
		lblLogInTo.setFont(new Font("Segoe Print", Font.BOLD, 18));
		
		textUserName = new JTextField();
		textUserName.setText("Username");
		textUserName.setFont(new Font("Segoe Print", Font.PLAIN, 16));
		textUserName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textUserName.getText().equals("Username")) {
					textUserName.setText("");
				}
			}
		});
		
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
		
		buttonLogin = new JButton();
		
		buttonLogin.setText("Log In");
		buttonLogin.setForeground(new Color(255, 153, 0));
		buttonLogin.setFont(new Font("Segoe Print", Font.BOLD, 14));
		buttonLogin.setBackground(Color.WHITE);
		
		JLabel buttonCreateAccount = new JLabel();
		buttonCreateAccount.setText("Create new account");
		buttonCreateAccount.setForeground(new Color(255, 153, 0));
		buttonCreateAccount.setFont(new Font("Segoe Print", Font.ITALIC, 15));
		buttonCreateAccount.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				buttonLogin.setFont(new Font("Segoe Print", Font.BOLD, 15));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				//buttonCreateAccount.setFont(new Font("Segoe Print", Font.ITALIC, 15));
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				//setVisible(false);
				//dispose();
//				SignUpGUIBasic singupGUI = new SignUpGUIBasic();
//				singupGUI.setVisible(true);
				
				SingUpGUIWorker signupWorker = new SingUpGUIWorker();
				signupWorker.openSingUpGUI();
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(134)
							.addComponent(buttonLogin, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(114)
							.addComponent(lblTwinCloud, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(textPassword, Alignment.LEADING)
								.addComponent(textUserName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE))))
					.addContainerGap())
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(92, Short.MAX_VALUE)
					.addComponent(lblLogInTo)
					.addGap(88))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(buttonCreateAccount)
					.addContainerGap(227, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTwinCloud, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblLogInTo, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(textUserName, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(textPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(buttonLogin, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
					.addComponent(buttonCreateAccount, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		
		setLocationRelativeTo(null);
	}
}
