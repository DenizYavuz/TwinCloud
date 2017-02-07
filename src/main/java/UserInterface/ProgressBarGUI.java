package UserInterface;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import java.awt.Window.Type;
import java.awt.Toolkit;

public class ProgressBarGUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public ProgressBarGUI(String text) {
		setTitle("Progress");
		setIconImage(Toolkit.getDefaultToolkit().getImage(ProgressBarGUI.class.getResource("/UserInterface/twincloud.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 185);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel label = new JLabel();
		label.setText(text);
		label.setForeground(new Color(255, 153, 0));
		label.setFont(new Font("Segoe Print", Font.BOLD, 18));
		label.setBackground(Color.WHITE);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setForeground(new Color(255, 153, 0));
		progressBar.setBackground(Color.WHITE);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(progressBar, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGap(1)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(38)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(66, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
		setLocationRelativeTo(null);
	}
}
