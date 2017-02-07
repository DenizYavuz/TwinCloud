package UserInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Window.Type;
import javax.swing.JProgressBar;
import java.awt.Dialog.ModalExclusionType;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import java.awt.Font;

public class SharingProgressGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public SharingProgressGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setType(Type.UTILITY);
		setBackground(Color.ORANGE);
		setTitle("Sharing");
		setIconImage(Toolkit.getDefaultToolkit().getImage(SharingProgressGUI.class.getResource("/UserInterface/twincloud.png")));
		setBounds(100, 100, 379, 444);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setForeground(new Color(255, 153, 0));
		progressBar.setBackground(Color.WHITE);
		
		JLabel lblNewLabel = new JLabel("  ");
		lblNewLabel.setIcon(new ImageIcon(SharingProgressGUI.class.getResource("/UserInterface/2015 Q4 - Image.png")));
		
		JLabel lblYourFileIs = new JLabel();
		lblYourFileIs.setText("Your file is sharing securely...");
		lblYourFileIs.setForeground(new Color(255, 153, 0));
		lblYourFileIs.setFont(new Font("Segoe Print", Font.BOLD, 18));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addComponent(lblNewLabel))
				.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 352, GroupLayout.PREFERRED_SIZE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(48)
					.addComponent(lblYourFileIs))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addComponent(lblYourFileIs, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
		setLocationRelativeTo(null);
	}
}
