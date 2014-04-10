package toturialJDBC;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.Window.Type;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Help extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Help frame = new Help();
					frame.setVisible(true);
					for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
		                if ("Windows".equals(info.getName())) {
		                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
		                    //UIManager.getLookAndFeelDefaults().put("Table.ascendingSortIcon", new BevelArrowIcon(BevelArrowIcon.UP, false, false));
		                    //UIManager.getLookAndFeelDefaults().put("Table.descendingSortIcon", new BevelArrowIcon(BevelArrowIcon.DOWN, false, false));
		                    break;
		                }
		               frame.setSize(301,340);
		               frame.setResizable(false);
		                
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Help() {
		setType(Type.POPUP);
		setTitle("HELP");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 301, 359);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 287, 320);
		contentPane.add(scrollPane);
		
		JTextArea txtrClickAdd = new JTextArea();
		txtrClickAdd.setBackground(Color.WHITE);
		txtrClickAdd.setEditable(false);
		txtrClickAdd.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14));
		txtrClickAdd.setText("1. Click ADD button to add task\r\n     - There will be a form to add \r\n        your tasks\r\n     - The form consist of \r\n        task name, Start time of your \r\n        task, Deadline, and Description\r\n     -  You must choose \r\n        the type of task, whether it is \r\n        a timed task, floating task, \r\n        or deadline  task\r\n     - enter the name of the task \r\n        so it will not getting error\r\n\r\n2. Click DELETE button to delete task\r\n     - make sure that one of the task\r\n        has been selected\r\n\r\n3. Click UNDO button to cancel \r\n    an operation\r\n     - undo can only cancel one operation\r\n\r\n4. To search a task, just type any \r\n    keyword in the text field on \r\n    the top corner of the aplication\r\n\r\n");
		scrollPane.setViewportView(txtrClickAdd);
		
		
		
	}
}
