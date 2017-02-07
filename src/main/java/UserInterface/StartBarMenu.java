package UserInterface;

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import UserAccountOperations.LogIn;
import FileOperations.FileOperations;

public class StartBarMenu {

	private static final java.io.File TWINCLOUD = new java.io.File(
			System.getProperty("user.home"), "Twin Cloud");

	private FileOperations fileoperations;
	private LogIn login;

	private TrayIcon ti;
	private JPopupMenu popup;
	private JMenuItem mntmTwincloud;
	private JMenuItem mntmCapacity;
	private JMenuItem mntmOpenTwinCloud;
	private JCheckBoxMenuItem chckbxmntmEnableAutoSync;
	private JMenuItem mntmLogOut;
	private JMenuItem mntmExit;
	private JMenuItem mntmInfo;
	private JMenuItem mntmShareFile;

	public boolean isInitialisation = false;

	public StartBarMenu(final FileOperations fileoperations, LogIn login) {

		this.login = login;
		this.fileoperations = fileoperations;

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
		}
		try {
			ti = new TrayIcon(ImageIO.read(getClass().getResource(
					"twincloud.png")), "Twin Cloud");
			ti.setImageAutoSize(true);
			popup = new JPopupMenu();

			mntmTwincloud = new JMenuItem("Twin Cloud");
			mntmTwincloud.setForeground(new Color(255, 153, 0));
			mntmTwincloud.setFont(new Font("Segoe Print", Font.BOLD, 14));
			popup.add(mntmTwincloud);

			mntmCapacity = new JMenuItem("X GB of Y GB used");
			popup.add(mntmCapacity);

			mntmInfo = new JMenuItem("Everything is up to date");
			popup.add(mntmInfo);

			JSeparator separator_1 = new JSeparator();
			popup.add(separator_1);

			mntmOpenTwinCloud = new JMenuItem("Open Twin Cloud folder");
			mntmOpenTwinCloud.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Desktop.getDesktop().open(
								new File(TWINCLOUD.getAbsolutePath()));
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});
			popup.add(mntmOpenTwinCloud);

			mntmShareFile = new JMenuItem("Share File");
			mntmShareFile.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ShareGUI shareGUI = new ShareGUI(fileoperations);
					shareGUI.setVisible(true);
				}
			});
			popup.add(mntmShareFile);

			chckbxmntmEnableAutoSync = new JCheckBoxMenuItem("Enable Auto Sync");
			chckbxmntmEnableAutoSync.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if (isAutoSyncEnabled()) {
						(new Thread() {
							public void run() {
								changeIcon("loading.gif");
								changeInfo("loading.gif", "Syncing...");
								fileoperations.initialFileOperations();
								changeIcon("twincloud.png");
								changeInfo("check.png", "Up to date.");
							}
						}).start();
					}
				}
			});
			popup.add(chckbxmntmEnableAutoSync);
			chckbxmntmEnableAutoSync.setState(true);

			JSeparator separator = new JSeparator();
			popup.add(separator);

			mntmLogOut = new JMenuItem("Log out and Exit");
			mntmLogOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					logOut();
				}
			});
			popup.add(mntmLogOut);

			mntmExit = new JMenuItem("Exit");
			mntmExit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			popup.add(mntmExit);

			ti.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1
							&& e.getClickCount() == 2) {
						ShareGUI shareGUI = new ShareGUI(fileoperations);
						shareGUI.setVisible(true);
					}
					if (e.getButton() == MouseEvent.BUTTON1
							&& e.getClickCount() == 1) {
						Rectangle bounds = getSafeScreenBounds(e.getPoint());
						Point point = e.getPoint();
						int x = point.x;
						int y = point.y;
						if (y < bounds.y) {
							y = bounds.y;
						} else if (y > bounds.y + bounds.height) {
							y = bounds.y + bounds.height;
						}
						if (x < bounds.x) {
							x = bounds.x;
						} else if (x > bounds.x + bounds.width) {
							x = bounds.x + bounds.width;
						}
						if (x + popup.getPreferredSize().width > bounds.x
								+ bounds.width) {
							x = (bounds.x + bounds.width)
									- popup.getPreferredSize().width;
						}
						if (y + popup.getPreferredSize().height > bounds.y
								+ bounds.height) {
							y = (bounds.y + bounds.height)
									- popup.getPreferredSize().height;
						}
						popup.setLocation(x, y);
//						if (popup.isVisible())
//							popup.setVisible(false);
//						else {
//							armPopup();
//							popup.setVisible(true);
//						}
						
						armPopup();
						popup.setVisible(true);
					}
				}
			});
			
			

			SystemTray.getSystemTray().add(ti);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void armPopup() {
		if (popup != null) {
			Toolkit.getDefaultToolkit().addAWTEventListener(
					new AWTEventListener() {
						@Override
						public void eventDispatched(AWTEvent event) {

							if (event instanceof MouseEvent) {
								MouseEvent m = (MouseEvent) event;
								if (m.getID() == MouseEvent.MOUSE_CLICKED) {
									popup.setVisible(false);
									Toolkit.getDefaultToolkit()
											.removeAWTEventListener(this);
								}
							}
							if (event instanceof WindowEvent) {
								WindowEvent we = (WindowEvent) event;
								if (we.getID() == WindowEvent.WINDOW_DEACTIVATED
										|| we.getID() == WindowEvent.WINDOW_STATE_CHANGED) {
									popup.setVisible(false);
									Toolkit.getDefaultToolkit()
											.removeAWTEventListener(this);
								}
							}
						}

					}, AWTEvent.MOUSE_EVENT_MASK | AWTEvent.WINDOW_EVENT_MASK);

		}
	}

	public boolean isAutoSyncEnabled() {
		if (chckbxmntmEnableAutoSync == null)
			return true;
		return chckbxmntmEnableAutoSync.isSelected();
	}

	public void changeInfo(String icon, String info) {
		if (isInitialisation)
			return;
		mntmInfo.setText(info);
		try {
			ImageIcon imageIcon = new ImageIcon(createResizedCopy(
					ImageIO.read(getClass().getResourceAsStream(icon)), 15, 15,
					false));
			mntmInfo.setIcon(imageIcon);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changeUsedSpace(String capacity, String usedSpace) {
		mntmCapacity.setText(usedSpace + " MB of " + capacity + " GB used");
	}

	public void changeIcon(String icon) {
		if (isInitialisation)
			return;
		try {
			ti.setImage(ImageIO.read(getClass().getResource(icon)));
		} catch (IOException e) {

		}
	}

	private void logOut() {
		login.logOut();
		System.exit(0);
	}

	BufferedImage createResizedCopy(Image originalImage, int scaledWidth,
			int scaledHeight, boolean preserveAlpha) {
		int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB
				: BufferedImage.TYPE_INT_ARGB;
		BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight,
				imageType);
		Graphics2D g = scaledBI.createGraphics();
		if (preserveAlpha) {
			g.setComposite(AlphaComposite.Src);
		}
		g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
		g.dispose();
		return scaledBI;
	}

	public static Rectangle getSafeScreenBounds(Point pos) {

		Rectangle bounds = getScreenBoundsAt(pos);
		Insets insets = getScreenInsetsAt(pos);

		bounds.x += insets.left;
		bounds.y += insets.top;
		bounds.width -= (insets.left + insets.right);
		bounds.height -= (insets.top + insets.bottom);

		return bounds;

	}

	public static Insets getScreenInsetsAt(Point pos) {
		GraphicsDevice gd = getGraphicsDeviceAt(pos);
		Insets insets = null;
		if (gd != null) {
			insets = Toolkit.getDefaultToolkit().getScreenInsets(
					gd.getDefaultConfiguration());
		}
		return insets;
	}

	public static Rectangle getScreenBoundsAt(Point pos) {
		GraphicsDevice gd = getGraphicsDeviceAt(pos);
		Rectangle bounds = null;
		if (gd != null) {
			bounds = gd.getDefaultConfiguration().getBounds();
		}
		return bounds;
	}

	public static GraphicsDevice getGraphicsDeviceAt(Point pos) {
		GraphicsDevice device = null;
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice lstGDs[] = ge.getScreenDevices();
		ArrayList<GraphicsDevice> lstDevices = new ArrayList<GraphicsDevice>(
				lstGDs.length);
		for (GraphicsDevice gd : lstGDs) {
			GraphicsConfiguration gc = gd.getDefaultConfiguration();
			Rectangle screenBounds = gc.getBounds();

			if (screenBounds.contains(pos)) {
				lstDevices.add(gd);
			}
		}

		if (lstDevices.size() > 0) {
			device = lstDevices.get(0);
		} else {
			device = ge.getDefaultScreenDevice();
		}

		return device;
	}
}