package UserInterface;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;
import java.net.URL;

import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import Exceptions.GoogleSignUpException;
import UserAccountOperations.SignUp;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;

public class SignUpGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField textName;
	private JTextField textSurname;
	private JTextField textUsername;
	private JPasswordField textPassword;
	private JTextField textPhone;
	private JTextField textCaptcha;
	private JLabel labelNameSurnameError;
	private JLabel labelUsernameError;
	private JLabel labelPasswordError;
	private JLabel labelPhoneError;
	private JLabel labelCaptcha;

	SignUp signup = null;

	public void signUp() {

		String signupReturn = signup.googleSignUp();
		if (signupReturn.equals("success")) {

		}
		else if (signupReturn.isEmpty()) {
			
		}
		else {
			String[] errors = signupReturn.split(";");
			for (String error : errors) {
				String code = error.split(":")[0];
				String description = error.split(":")[1];
				if (code.equals("nameError")) {
					labelNameSurnameError.setText(description);
				} else if (code.equals("surnameError")) {
					labelNameSurnameError.setText(description);
				} else if (code.equals("mailAdressError")) {
					labelUsernameError.setText(description);
				} else if (code.equals("passwordError")) {
					labelPasswordError.setText(description);
				} else if (code.equals("phoneError")) {
					labelPhoneError.setText(description);
				}
			}
		}

		// signup.dropboxSignUp(textName.getText(), textSurname.getText(),
		// textUsername.getText() + "@gmail.com", textPassword.getText());
	}

	public void refreshCaptcha() {
		try {
			String captchaURL = signup.refreshCaptcha();
			URL url = new URL(captchaURL);

			ImageIcon icon = new ImageIcon(url, "");
			labelCaptcha.setIcon(icon);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public SignUpGUI() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SignUpGUI.class.getResource("/UserInterface/twincloud.png")));
		signup = new SignUp();
		setBounds(100, 100, 455, 634);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addComponent(panel,
				GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addComponent(panel,
				GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE));

		JLabel labelProgramName = new JLabel();
		labelProgramName.setText("TwinCloud");
		labelProgramName.setForeground(new Color(255, 153, 0));
		labelProgramName.setFont(new Font("Segoe Print", Font.BOLD, 24));

		JLabel labelWindowJob = new JLabel();
		labelWindowJob.setText("Create an account on TwinCLoud ");
		labelWindowJob.setForeground(new Color(255, 153, 0));
		labelWindowJob.setFont(new Font("Segoe Print", Font.BOLD, 18));

		labelNameSurnameError = new JLabel();
		labelNameSurnameError.setText(" ");
		labelNameSurnameError.setForeground(Color.RED);

		textName = new JTextField();
		textName.setFont(new Font("Segoe Print", Font.PLAIN, 16));
		textName.setText("Name");
		textName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textName.getText().equals("Name"))
					textName.setText("");
			}
		});
		textName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				signup.enterFirstName(textName.getText());
			}
		});

		textSurname = new JTextField();
		textSurname.setFont(new Font("Segoe Print", Font.PLAIN, 16));
		textSurname.setText("Surname");
		textSurname.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textSurname.getText().equals("Surname"))
					textSurname.setText("");
			}
		});
		textSurname.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				signup.enterLastName(textSurname.getText());
			}
		});

		labelUsernameError = new JLabel();
		labelUsernameError.setText(" ");
		labelUsernameError.setForeground(Color.RED);

		textUsername = new JTextField();
		textUsername.setFont(new Font("Segoe Print", Font.PLAIN, 16));
		textUsername.setText("Username");
		textUsername.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textUsername.getText().equals("Username"))
					textUsername.setText("");
			}
		});
		textUsername.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				signup.enterUserName(textUsername.getText());
			}
		});

		labelPasswordError = new JLabel();
		labelPasswordError.setText(" ");
		labelPasswordError.setForeground(Color.RED);

		textPassword = new JPasswordField();
		textPassword.setFont(new Font("Segoe Print", Font.PLAIN, 16));
		textPassword.setText("password123");
		textPassword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textPassword.getText().equals("password123"))
					textPassword.setText("");
			}
		});
		textPassword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				signup.enterPassword(textPassword.getText());
			}
		});

		labelPhoneError = new JLabel();
		labelPhoneError.setText(" ");
		labelPhoneError.setForeground(Color.RED);

		textPhone = new JTextField();
		textPhone.setFont(new Font("Segoe Print", Font.PLAIN, 16));
		textPhone.setText("Phone");
		textPhone.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textPhone.getText().equals("Phone"))
					textPhone.setText("");
			}
		});
		textPhone.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				signup.enterPhone(textPhone.getText());
			}
		});

		textCaptcha = new JTextField();
		textCaptcha.setFont(new Font("Segoe Print", Font.PLAIN, 16));
		textCaptcha.setText("Captcha");
		textCaptcha.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textCaptcha.getText().equals("Captcha"))
					textCaptcha.setText("");
			}
		});
		textCaptcha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				signup.enterCaptcha(textCaptcha.getText());
			}
		});

		labelCaptcha = new JLabel();
		labelCaptcha.setText(" ");

		JButton buttonRefresh = new JButton();
		buttonRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshCaptcha();
			}
		});
		buttonRefresh.setFont(new Font("Segoe Print", Font.BOLD, 13));
		buttonRefresh.setForeground(new Color(255, 153, 0));
		buttonRefresh.setBackground(Color.WHITE);
		buttonRefresh.setText("Refresh");

		JButton buttonSignup = new JButton();
		buttonSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signUp();
			}
		});
		buttonSignup.setText("Sign Up");
		buttonSignup.setForeground(new Color(255, 153, 0));
		buttonSignup.setFont(new Font("Segoe Print", Font.BOLD, 14));
		buttonSignup.setBackground(Color.WHITE);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(textName, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textSurname, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE))
								.addComponent(labelNameSurnameError, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
								.addComponent(textUsername, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
								.addComponent(labelUsernameError, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
								.addComponent(textPassword, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
								.addComponent(labelPasswordError, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
								.addComponent(textPhone, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
								.addComponent(labelCaptcha, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
								.addComponent(labelPhoneError, GroupLayout.PREFERRED_SIZE, 419, Short.MAX_VALUE)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(textCaptcha, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(buttonRefresh))))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(164)
							.addComponent(buttonSignup, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(149, Short.MAX_VALUE)
					.addComponent(labelProgramName, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
					.addGap(143))
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(65, Short.MAX_VALUE)
					.addComponent(labelWindowJob)
					.addGap(63))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(36)
					.addComponent(labelProgramName, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(labelWindowJob, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(33)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textSurname, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(textName, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(labelNameSurnameError)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textUsername, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(labelUsernameError, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textPassword, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(labelPasswordError)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textPhone, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(labelPhoneError)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(labelCaptcha, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(buttonRefresh, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
						.addComponent(textCaptcha, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(35)
					.addComponent(buttonSignup, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addGap(31))
		);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);
		setLocationRelativeTo(null);
		//refreshCaptcha();
	}
}
