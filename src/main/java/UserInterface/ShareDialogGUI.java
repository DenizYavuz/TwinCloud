package UserInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Color;

import javax.swing.JButton;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

import FileOperations.FileOperations;

import com.google.api.services.drive.model.File;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class ShareDialogGUI extends JFrame {

	JPanel contentPane;
	JTextField textUserName;
	JRadioButton checkReadWrite;
	public JButton buttonShare;
	
	public ShareDialogGUI() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ShareDialogGUI.class.getResource("/UserInterface/twincloud.png")));		
		setTitle("Share File");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 394, 250);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblEnterTheUser = new JLabel();
		lblEnterTheUser.setText("Enter the username and choose the permission.");
		lblEnterTheUser.setForeground(new Color(255, 153, 0));
		lblEnterTheUser.setFont(new Font("Segoe Print", Font.BOLD, 14));
		
		textUserName = new JTextField();
		textUserName.setColumns(10);
		
		JLabel lblUsername = new JLabel();
		lblUsername.setText("Username");
		lblUsername.setForeground(new Color(255, 153, 0));
		lblUsername.setFont(new Font("Segoe Print", Font.PLAIN, 14));
		
		JLabel lblPermission = new JLabel();
		lblPermission.setText("Permission");
		lblPermission.setForeground(new Color(255, 153, 0));
		lblPermission.setFont(new Font("Segoe Print", Font.BOLD, 14));
		
		final JRadioButton checkRead = new JRadioButton("Read only");
		checkRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkRead.setSelected(true);
				checkReadWrite.setSelected(false);
			}
		});
		checkRead.setBackground(Color.WHITE);
		checkRead.setForeground(new Color(255, 153, 0));
		checkRead.setFont(new Font("Segoe Print", Font.PLAIN, 14));
		
		checkReadWrite = new JRadioButton("Read and write");
		checkReadWrite.setSelected(true);
		checkReadWrite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkRead.setSelected(false);
				checkReadWrite.setSelected(true);
			}
		});
		checkReadWrite.setBackground(Color.WHITE);
		checkReadWrite.setForeground(new Color(255, 153, 0));
		checkReadWrite.setFont(new Font("Segoe Print", Font.PLAIN, 14));
		
		buttonShare = new JButton();
		buttonShare.setText("Share");
		buttonShare.setForeground(new Color(255, 153, 0));
		buttonShare.setFont(new Font("Segoe Print", Font.BOLD, 14));
		buttonShare.setBackground(Color.WHITE);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblEnterTheUser, GroupLayout.PREFERRED_SIZE, 363, GroupLayout.PREFERRED_SIZE)
							.addGap(40))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblUsername)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textUserName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPermission)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(10)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(checkReadWrite)
										.addComponent(checkRead)))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(buttonShare, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblEnterTheUser, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblPermission, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(checkRead))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(2)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblUsername, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
								.addComponent(textUserName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 35, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(checkReadWrite)
					.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
					.addComponent(buttonShare, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		setLocationRelativeTo(null);
	}
}
