package toturialJDBC;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.timer.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ProgressBarUI;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

import java.awt.Color;

import javax.swing.border.CompoundBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class splashScreen extends JFrame {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					splashScreen frame = new splashScreen();
					frame.setUndecorated(true);
					frame.setSize(585, 340);
					frame.setResizable(true);
					// mengatur agar screen ditengah
					Dimension screenSize = Toolkit.getDefaultToolkit()
							.getScreenSize();
					frame.setLocation(
							(screenSize.width - frame.getWidth()) / 2,
							(screenSize.height - frame.getHeight()) / 2);

					frame.setVisible(true);
					
					for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
		                if ("Windows".equals(info.getName())) {
		                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
		                    //UIManager.getLookAndFeelDefaults().put("Table.ascendingSortIcon", new BevelArrowIcon(BevelArrowIcon.UP, false, false));
		                    //UIManager.getLookAndFeelDefaults().put("Table.descendingSortIcon", new BevelArrowIcon(BevelArrowIcon.DOWN, false, false));
		                    break;
		                }


					}
					}catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private JPanel contentPane;
	JProgressBar progressBar = new JProgressBar();
	
	

	public splashScreen() {
		
		new Thread() {
			public void run() {
				for (int i = 0; i < 101; i++) {

					try {
						sleep(100);
						progressBar.setValue(i + 10);

					} catch (InterruptedException ex) {
						Logger.getLogger(splashScreen.class.getName()).log(
								Level.SEVERE, null, ex);
					}

				}

				dispose();
				Interface gui = new Interface();
				gui.setVisible(true);
				// System.exit(0);

			}
		}.start();

		setBackground(new Color(30, 144, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 378);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		progressBar.setStringPainted(true);

		progressBar.setForeground(Color.WHITE);
		progressBar.setBackground(new Color(0, 191, 255));
		progressBar.setBounds(98, 198, 412, 8);
		progressBar.setBorder(new CompoundBorder(new CompoundBorder(), null));
		contentPane.add(progressBar);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(splashScreen.class.getResource("/icon/Splash3.png")));
		lblNewLabel.setBounds(0, -17, 584, 369);
		contentPane.add(lblNewLabel);

	}

};
