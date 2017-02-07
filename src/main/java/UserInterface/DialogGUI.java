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

public class DialogGUI extends JFrame {

	private JPanel contentPane;
	public JButton btnOk;

	/**
	 * Create the frame.
	 */
	public DialogGUI(String message, String icon) {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogGUI.class.getResource("/UserInterface/twincloud.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 433, 182);
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
		lblLoginIsSuccessfull.setIcon(new ImageIcon(DialogGUI.class.getResource(icon)));
		lblLoginIsSuccessfull.setText(message);
		lblLoginIsSuccessfull.setForeground(new Color(255, 153, 0));
		lblLoginIsSuccessfull.setFont(new Font("Segoe Print", Font.BOLD, 18));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblLoginIsSuccessfull, GroupLayout.PREFERRED_SIZE, 394, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(134)
							.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(218, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(22)
					.addComponent(lblLoginIsSuccessfull, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(33)
					.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(38, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		setLocationRelativeTo(null);
	}
}
