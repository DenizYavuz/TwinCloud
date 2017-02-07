package UserInterface;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.border.BevelBorder;
import javax.swing.JCheckBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JProgressBar;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

import FileOperations.FileOperations;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.awt.event.AWTEventListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JScrollPane;

public class ShareGUI extends JFrame {
	Object[][] list ;
	
	FileOperations fileOperations;
	private JTable tableUsers;
	private JTable tableFileNames;
	public JButton btnAddUser;
	
	public ShareGUI(final FileOperations fileOperations) {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ShareGUI.class.getResource("/UserInterface/twincloud.png")));

		
		setTitle("Share");
		this.fileOperations = fileOperations;
		
		getContentPane().setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 698, 533);
				
		JLabel lblTwinCloudFiles = new JLabel();
		lblTwinCloudFiles.setText("Files");
		lblTwinCloudFiles.setForeground(new Color(255, 153, 0));
		lblTwinCloudFiles.setFont(new Font("Segoe Print", Font.BOLD, 18));
				
		btnAddUser = new JButton();
		btnAddUser.setVisible(false);
		btnAddUser.setIcon(new ImageIcon(ShareGUI.class.getResource("/UserInterface/addUser2.png")));

		btnAddUser.setForeground(Color.WHITE);
		btnAddUser.setFont(new Font("Segoe Print", Font.BOLD, 12));
		btnAddUser.setBackground(Color.WHITE);
		btnAddUser.setContentAreaFilled(false);
		btnAddUser.setOpaque(true);
		
		btnAddUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				if (!fileOperations.getUserName().equals(getOwnerOfTheFile())) {
					final DialogGUI dialog = new DialogGUI("You are not owner of the file !", "cross.png");
					dialog.setVisible(true);
					dialog.btnOk.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							dialog.setVisible(false);
							dialog.dispose();
						}
					});
					return;
				}
				
				ShareDialogGUIWorker shareDialogGUIWorker = new ShareDialogGUIWorker();
				shareDialogGUIWorker.openShareGUI(getSelectedFileName(), getOwnerOfTheFile(), fileOperations);
			}
		});
		
		final JButton btnDeleteUser = new JButton();
		btnDeleteUser.setVisible(false);
		btnDeleteUser.setIcon(new ImageIcon(ShareGUI.class.getResource("/UserInterface/deleteUser2.png")));
		btnDeleteUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				deleteUser();
			}
		});
		btnDeleteUser.setForeground(new Color(255, 153, 0));
		btnDeleteUser.setFont(new Font("Segoe Print", Font.BOLD, 12));
		btnDeleteUser.setBackground(Color.WHITE);
		btnDeleteUser.setContentAreaFilled(false);
		btnDeleteUser.setOpaque(true);
		
		final JLabel lblShare = new JLabel();
		lblShare.setVisible(false);
		lblShare.setText("Share");
		lblShare.setForeground(new Color(255, 153, 0));
		lblShare.setFont(new Font("Segoe Print", Font.BOLD, 14));
		
		final JLabel lblUnshare = new JLabel();
		lblUnshare.setVisible(false);
		lblUnshare.setText("Unshare");
		lblUnshare.setForeground(new Color(255, 153, 0));
		lblUnshare.setFont(new Font("Segoe Print", Font.BOLD, 14));
		
		final JLabel lblClickTheShare = new JLabel();
		lblClickTheShare.setVisible(false);
		lblClickTheShare.setText("Click the Share button to add user.");
		lblClickTheShare.setForeground(new Color(255, 153, 0));
		lblClickTheShare.setFont(new Font("Segoe Print", Font.PLAIN, 14));
		
		JButton buttonRefresh = new JButton();
		buttonRefresh.setIcon(new ImageIcon(ShareGUI.class.getResource("/UserInterface/loading2.png")));
		buttonRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fillNameList();
				DefaultTableModel model = (DefaultTableModel) tableUsers.getModel();
				int rowCount = model.getRowCount();
				for (int i = rowCount - 1; i >= 0; i--) {
					model.removeRow(i);
				}
				btnAddUser.setVisible(false);
				btnDeleteUser.setVisible(false);
				lblShare.setVisible(false);
				lblUnshare.setVisible(false);
				lblClickTheShare.setVisible(false);
			}
		});
		buttonRefresh.setForeground(new Color(255, 153, 0));
		buttonRefresh.setFont(new Font("Segoe Print", Font.BOLD, 12));
		buttonRefresh.setBackground(Color.WHITE);
		buttonRefresh.setContentAreaFilled(false);
		buttonRefresh.setOpaque(true);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JLabel lblUsers = new JLabel();
		lblUsers.setText("Users");
		lblUsers.setForeground(new Color(255, 153, 0));
		lblUsers.setFont(new Font("Segoe Print", Font.BOLD, 18));
		
		JLabel lblRefreshFiles = new JLabel();
		lblRefreshFiles.setText("Refresh Files");
		lblRefreshFiles.setForeground(new Color(255, 153, 0));
		lblRefreshFiles.setFont(new Font("Segoe Print", Font.BOLD, 14));
		
		JLabel lblPleaseSelectA = new JLabel();
		lblPleaseSelectA.setText("Please select a file to share.");
		lblPleaseSelectA.setForeground(new Color(255, 153, 0));
		lblPleaseSelectA.setFont(new Font("Segoe Print", Font.PLAIN, 14));
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(buttonRefresh, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblRefreshFiles)
								.addComponent(lblPleaseSelectA, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblTwinCloudFiles, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(182)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnAddUser, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(10)
									.addComponent(lblShare, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblUnshare)
								.addComponent(btnDeleteUser, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)))
						.addComponent(lblUsers)
						.addComponent(lblClickTheShare))
					.addGap(10))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTwinCloudFiles, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUsers, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPleaseSelectA, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblClickTheShare, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(scrollPane, 0, 0, Short.MAX_VALUE)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(buttonRefresh)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(btnDeleteUser, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnAddUser, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRefreshFiles, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblShare, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUnshare, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		DefaultTableModel model = new DefaultTableModel();
		model.setColumnIdentifiers(new String[] {"User Name", "Full Name", "Permission"});
		tableUsers = new JTable(model) {

            private static final long serialVersionUID = 1L;

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                int firstRow = 0;
                if (row % 2 == 0) {
                	((JComponent) c).setBackground(Color.WHITE);
                } else {
                    ((JComponent) c).setBackground(Color.WHITE);
                }
                String type = (String) getModel().getValueAt(row, 2);
                if (type.equals("owner")) {
                	((JComponent) c).setBackground(Color.WHITE);
				}
                if (isRowSelected(row)) {
                	c.setBackground(new Color(255, 153, 0));
                }
//                if (isRowSelected(row) && isColumnSelected(column)) {
//                ((JComponent) c).setBorder(new LineBorder(Color.red));
//                }
                return c;
            }
        };
		tableUsers.setBackground(Color.WHITE);
		tableUsers.setShowGrid(false);
			
		scrollPane.setViewportView(tableUsers);
		getContentPane().setLayout(groupLayout);
		
		DefaultTableModel modelFileName = new DefaultTableModel();
		modelFileName.setColumnIdentifiers(new String[] {"File Names"});
		tableFileNames = new JTable(modelFileName) {

            private static final long serialVersionUID = 1L;

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                int firstRow = 0;
                if (row % 2 == 0) {
                	((JComponent) c).setBackground(Color.WHITE);
                } else {
                    ((JComponent) c).setBackground(Color.WHITE);
                }
                if (isRowSelected(row)) {
	                c.setBackground(new Color(255, 153, 0));
                }
                return c;
            }
        };
		tableFileNames.setBackground(Color.WHITE);
		tableFileNames.setShowGrid(false);
        tableFileNames.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listSharedUserOfFile(getSelectedFileName());
				btnAddUser.setVisible(true);
				btnDeleteUser.setVisible(true);
				lblShare.setVisible(true);
				lblUnshare.setVisible(true);
				lblClickTheShare.setVisible(true);
			}
		});
			
        scrollPane_1.setViewportView(tableFileNames);
        
        scrollPane.getViewport().setBackground(tableUsers.getBackground());
        scrollPane_1.getViewport().setBackground(tableFileNames.getBackground());
	
        // When clicked outside of table, clear selection and refresh.
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
			@Override
			public void eventDispatched(AWTEvent event) {
				if (event.getID() == MouseEvent.MOUSE_CLICKED) {
					MouseEvent mevent = (MouseEvent) event;
					int row = tableFileNames.rowAtPoint(mevent.getPoint());
					if (row == -1) {
						tableFileNames.clearSelection();
						fillNameList();
						DefaultTableModel model = (DefaultTableModel) tableUsers.getModel();
						int rowCount = model.getRowCount();
						for (int i = rowCount - 1; i >= 0; i--) {
							model.removeRow(i);
						}
						btnAddUser.setVisible(false);
						btnDeleteUser.setVisible(false);
						lblShare.setVisible(false);
						lblUnshare.setVisible(false);
						lblClickTheShare.setVisible(false);
					}
				}
			}
		}, AWTEvent.MOUSE_EVENT_MASK);
		
		fillNameList();
	}
	
	public String getSelectedFileName() {
		int selectedRowIndex = tableFileNames.getSelectedRow();
		String fileName = (String) tableFileNames.getModel().getValueAt(selectedRowIndex, 0);
		return fileName;
	}
	
	public void deleteUser() {
		int selectedRowIndex = tableUsers.getSelectedRow();
		if (selectedRowIndex == -1) {
			final DialogGUI dialog = new DialogGUI("Please select a user !", "cross.png");
			dialog.setVisible(true);
			dialog.btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dialog.setVisible(false);
					dialog.dispose();
				}
			});
			return;
		}
		String userName = (String) tableUsers.getModel().getValueAt(selectedRowIndex, 0);
		
		if (fileOperations.getUserName().equals(getOwnerOfTheFile())) {
			fileOperations.unshareFile(getSelectedFileName(), userName);
			listSharedUserOfFile(getSelectedFileName());
		}
		else {
			final DialogGUI dialog = new DialogGUI("You are not owner of the file !", "cross.png");
			dialog.setVisible(true);
			dialog.btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dialog.setVisible(false);
					dialog.dispose();
				}
			});
		}
	}
	
	public String getOwnerOfTheFile() {
		DefaultTableModel model = (DefaultTableModel) tableUsers.getModel();
		int rowCount = model.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			String role = (String) model.getValueAt(i, 2);
			if (role.equals("owner"))
				return (String) model.getValueAt(i, 0);
		}
		
//		ArrayList<String> userNames = fileOperations.getSharedUsers(getSelectedFileName());
//		for (String userName : userNames) {
//			String[] splitUserName;
//			splitUserName = userName.split(":");
//			if (splitUserName[2].equals("owner"))
//				return userName;
//		}
		return null;
	}
	
	public void listSharedUserOfFile(String fileName) {
		ArrayList<String> userNames = fileOperations.getSharedUsers(fileName);
		
		DefaultTableModel model = (DefaultTableModel) tableUsers.getModel();
		int rowCount = model.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		for (String userName : userNames) {
			String[] splitUserName;
			splitUserName = userName.split(":");
			if (splitUserName[2].equals("owner")) {
				//model.addRow(new Object[]{" !  Please click to Share button", "to share the selected file.", "owner"});
				model.addRow(new Object[]{splitUserName[0], splitUserName[1], splitUserName[2]});
			}
			else
				model.addRow(new Object[]{splitUserName[0], splitUserName[1], splitUserName[2]});
		}
		
	}
	
	public void fillNameList() {
		ArrayList<String> fileNames = fileOperations.getFileNameList();
		DefaultTableModel model = (DefaultTableModel) tableFileNames.getModel();
		int rowCount = model.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			model.removeRow(i);
		}
		for (String fileName : fileNames) {
			model.addRow(new Object[]{fileName});
		}
	}
}