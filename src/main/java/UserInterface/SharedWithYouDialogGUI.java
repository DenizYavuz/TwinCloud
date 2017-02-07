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
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class SharedWithYouDialogGUI extends JFrame {

	private JPanel contentPane;
	public JButton btnOk;

	/**
	 * Create the frame.
	 */
	public SharedWithYouDialogGUI(String message, String icon) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(SharedWithYouDialogGUI.class.getResource("/UserInterface/twincloud.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 433, 186);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		btnOk = new JButton();
		btnOk.setText("Ok");
		btnOk.setForeground(new Color(255, 153, 0));
		btnOk.setFont(new Font("Segoe Print", Font.BOLD, 14));
		btnOk.setBackground(Color.WHITE);
		
		JLabel lblLoginIsSuccessfull = new JLabel();
		//lblLoginIsSuccessfull.setIcon(new ImageIcon(SharedWithYouDialogGUI.class.getResource(icon)));
		lblLoginIsSuccessfull.setText("A file is shared with you by twincloudtobb !");
		lblLoginIsSuccessfull.setForeground(new Color(255, 153, 0));
		lblLoginIsSuccessfull.setFont(new Font("Segoe Print", Font.BOLD, 18));
		
		JLabel lblFile = new JLabel();
		lblFile.setText("File: ");
		lblFile.setForeground(new Color(255, 153, 0));
		lblFile.setFont(new Font("Segoe Print", Font.BOLD, 18));
		
		JLabel labelFile = new JLabel();
		labelFile.setText(message);
		labelFile.setForeground(new Color(255, 153, 0));
		labelFile.setFont(new Font("Segoe Print", Font.BOLD, 18));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblLoginIsSuccessfull, GroupLayout.PREFERRED_SIZE, 394, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblFile)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(labelFile, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(142)
							.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(22)
					.addComponent(lblLoginIsSuccessfull, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFile, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelFile, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(45, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		setLocationRelativeTo(null);
	}
}
