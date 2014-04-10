package toturialJDBC;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ButtonGroup;
import javax.swing.DefaultRowSorter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.InternalFrameUI;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;

//import net.miginfocom.swing.MigLayout;

import javax.swing.ImageIcon;

import java.awt.Color;

import javax.swing.UIManager;

import java.awt.Font;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Stack;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.CardLayout;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.toedter.calendar.JDateChooser;

import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JCheckBox;

import java.awt.SystemColor;

import javax.swing.border.BevelBorder;

public class Interface extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8212786186286739658L;
	private JPanel contentPane;
	private JPanel PanelGerak;
	private JTextField textField;
	private TableTask tt = new TableTask();
	private TableLog tl = new TableLog();
	private JTable table;
	private JTextField textNama;
	private JTextField textNamaUpdate;
	private Database db = new Database();
	private JRadioButton rdbtnTimedTask;
	private JRadioButton rdbtnFloatingTask;
	private JRadioButton rdbtnDeadlineTask;
	private JRadioButton rdbtnTimedTaskUp;
	private JRadioButton rdbtnFloatingTaskUp;
	private JRadioButton rdbtnDeadlineTaskUp;
	final JDateChooser dateAwal;
	final JDateChooser dateAkhir;
	final JDateChooser dateAwalUpdate;
	final JDateChooser dateAkhirUpdate;
	final JTextArea textDeskripsiUpdate;
	private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private ArrayList<String> arrayId = new ArrayList<>();
	private Object idToUpdate;
	private int timeCount;
	private Object idToDelete;
	private String namaLama;
	private Stack<String> stcId = new Stack<>();
	private Stack<String> stcLog = new Stack<>();
	private Stack<Task> stcDelete = new Stack<>();
	private Stack<Task> stcUpdate = new Stack<>();
	final RowSorter<TableModel> sorter;
	final RowSorter<TableModel> sorLog;
	private JLabel lblJlhAll;
	private JLabel lblJlhToday;
	private JLabel lblcTomm;
	private JTable tableLog;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Interface frame = new Interface();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

					for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
							.getInstalledLookAndFeels()) {
						if ("Windows".equals(info.getName())) {
							javax.swing.UIManager.setLookAndFeel(info
									.getClassName());
							// UIManager.getLookAndFeelDefaults().put("Table.ascendingSortIcon",
							// new BevelArrowIcon(BevelArrowIcon.UP, false,
							// false));
							// UIManager.getLookAndFeelDefaults().put("Table.descendingSortIcon",
							// new BevelArrowIcon(BevelArrowIcon.DOWN, false,
							// false));
							break;

						}

						frame.setSize(793, 490);
						frame.setVisible(true);
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
	public Interface() {

		// title bar icon

		setBackground(new Color(230, 230, 250));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 803, 501);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("Button.highlight"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Date now = new Date();
		SimpleDateFormat hari = new SimpleDateFormat("dd");
		SimpleDateFormat bulan = new SimpleDateFormat("MMM");

		final JLabel Undo = new JLabel("");
		Undo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				arrayId = db.getId();
				for (String d : arrayId) {
					stcId.push(d);
				}
				if (stcLog.lastElement().equals("Insert")) {
					db.deleteData(stcId.lastElement().toString());
				} else if (stcLog.lastElement().equals("Delete")) {
					String id = stcDelete.lastElement().getId().toString();
					String nama = stcDelete.lastElement().getTask_name()
							.toString();
					String awal = stcDelete.lastElement().getStart().toString();
					String akhir = stcDelete.lastElement().getWaktu()
							.toString();
					String deskripsi = stcDelete.lastElement().getDeskripsi()
							.toString();
					String tipe = stcDelete.lastElement().getTipe().toString();
					insert(id, nama, awal, akhir, deskripsi, tipe);
				} else if (stcLog.lastElement().equals("Update")) {
					String id = stcUpdate.lastElement().getId().toString();
					String nama = stcUpdate.lastElement().getTask_name()
							.toString();
					String awal = stcUpdate.lastElement().getStart().toString();
					String akhir = stcUpdate.lastElement().getWaktu()
							.toString();
					String deskripsi = stcUpdate.lastElement().getDeskripsi()
							.toString();
					String tipe = stcUpdate.lastElement().getTipe().toString();
					update(id, nama, awal, akhir, deskripsi, tipe);
				}

				String jumlah = jumlahHariIni();
				String jlh = jumlahSemua();
				String jlhBesok = jumlahBesok();
				lblJlhAll.setText(jlh);
				lblJlhToday.setText(jumlah);
				lblcTomm.setText(jlhBesok);
				tt.refreshTable();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				Undo.setIcon(new ImageIcon(Interface.class
						.getResource("/toturialJDBC/icon/undo5.png")));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				Undo.setIcon(new ImageIcon(Interface.class
						.getResource("/toturialJDBC/icon/undo4.png")));
			}
		});
		Undo.setIcon(new ImageIcon(Interface.class
				.getResource("/toturialJDBC/icon/undo4.png")));
		Undo.setBounds(466, 17, 89, 32);
		contentPane.add(Undo);

		final JLabel Delete = new JLabel("");
		Delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (table.getSelectedRow() != -1) {
					int selRow = table.getSelectedRow();
					idToDelete = tt.getIdToDo(table
							.convertRowIndexToModel(selRow));
					int dialogResult = JOptionPane.showConfirmDialog(null,
							"anda yakin akan menghapus?");
					if (dialogResult == JOptionPane.YES_OPTION) {
						Task t = new Task();
						t.setId(idToDelete.toString());
						t.setTask_name(table.getValueAt(selRow, 0).toString());
						t.setStart(table.getValueAt(selRow, 1).toString());
						t.setWaktu(table.getValueAt(selRow, 2).toString());
						t.setDeskripsi(table.getValueAt(selRow, 3).toString());
						t.setTipe(tt.getType(
								table.convertRowIndexToModel(selRow))
								.toString());

						Date date = new Date();
						db.insertLog("Delete task", table.getValueAt(selRow, 0)
								.toString(), date.toString());

						stcDelete.push(t);
						tt.removeRow(selRow);
						db.deleteData(idToDelete.toString());
						stcLog.push("Delete");
						String jumlah = jumlahHariIni();
						String jlh = jumlahSemua();
						String jlhBesok = jumlahBesok();
						lblJlhAll.setText(jlh);
						lblJlhToday.setText(jumlah);
						lblcTomm.setText(jlhBesok);
						tl.refreshLog();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"pilih task yang mau dihapus");
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				Delete.setIcon(new ImageIcon(Interface.class
						.getResource("/toturialJDBC/icon/Del1.png")));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				Delete.setIcon(new ImageIcon(Interface.class
						.getResource("/toturialJDBC/icon/Del2.png")));
			}
		});
		Delete.setIcon(new ImageIcon(Interface.class
				.getResource("/toturialJDBC/icon/Del2.png")));
		Delete.setBounds(347, 17, 104, 32);
		contentPane.add(Delete);

		JLabel btnSearch = new JLabel("");
		btnSearch.setBounds(725, 26, 62, 24);
		contentPane.add(btnSearch);
		btnSearch.setIcon(new ImageIcon(Interface.class
				.getResource("/toturialJDBC/icon/btnSearch3.png")));

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void keyTyped(KeyEvent arg0) {
				String text = textField.getText();
				RowFilter<TableModel, Object> filter = RowFilter
						.orFilter(Arrays.asList(RowFilter.regexFilter(text, 0),
								RowFilter.regexFilter(text, 1),
								RowFilter.regexFilter(text, 2),
								RowFilter.regexFilter(text, 3)));
				((DefaultRowSorter<TableModel, Integer>) sorter)
						.setRowFilter(filter);
				tt.refreshTable();
			}
		});
		textField.setFont(new Font("Calibri", Font.BOLD, 15));
		textField.setHorizontalAlignment(SwingConstants.LEFT);
		textField.setBackground(new Color(255, 255, 255));
		textField.setForeground(new Color(105, 105, 105));
		textField.setBounds(602, 26, 121, 23);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setBorder(null);

		JLabel Search = new JLabel("");
		Search.setForeground(new Color(245, 245, 245));
		Search.setIcon(new ImageIcon(Interface.class
				.getResource("/toturialJDBC/icon/search2.png")));
		Search.setBounds(599, 22, 182, 32);
		contentPane.add(Search);

		final JLabel Edit = new JLabel("");
		Edit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (table.getSelectedRow() != -1) {
					ArrayList<String> tampil = new ArrayList<>();
					ArrayList<Date> tampung = new ArrayList<Date>();
					int selRow = table.getSelectedRow();
					Object tipe = tt.getType(table
							.convertRowIndexToModel(selRow));
					try {
						if (!table.getValueAt(selRow, 1).equals("")) {
							Date d1 = dateFormat.parse(table.getValueAt(selRow,
									1).toString());
							tampung.add(d1);
							dateAwalUpdate.setDate(tampung.get(0));
						} else {
							tampung.add(null);
							dateAwalUpdate.setDate(null);
						}
						if (!table.getValueAt(selRow, 2).equals("")) {
							Date d2 = dateFormat.parse(table.getValueAt(selRow,
									2).toString());
							tampung.add(d2);
							dateAkhirUpdate.setDate(tampung.get(1));
						} else {
							dateAkhirUpdate.setDate(null);
						}
					} catch (ParseException d) {
						// TODO Auto-generated catch block
						d.printStackTrace();
					}

					idToUpdate = tt.getIdToDo(table
							.convertRowIndexToModel(selRow));

					tampil.add(table.getValueAt(selRow, 0).toString());
					tampil.add(table.getValueAt(selRow, 3).toString());
					textNamaUpdate.setText(tampil.get(0));
					textDeskripsiUpdate.setText(tampil.get(1));

					if (tipe.toString().equals("1")) {
						dateAwalUpdate.setEnabled(true);
						dateAkhirUpdate.setEnabled(true);
						rdbtnTimedTaskUp.setSelected(true);
					} else if (tipe.toString().equals("2")) {
						dateAwalUpdate.setEnabled(false);
						dateAkhirUpdate.setEnabled(false);
						rdbtnFloatingTaskUp.setSelected(true);
					} else if (tipe.toString().equals("3")) {
						dateAwalUpdate.setEnabled(false);
						dateAkhirUpdate.setEnabled(true);
						rdbtnDeadlineTaskUp.setSelected(true);
					}

					Task task = new Task();
					task.setId(idToUpdate.toString());
					task.setTask_name(table.getValueAt(selRow, 0).toString());
					task.setStart(table.getValueAt(selRow, 1).toString());
					task.setWaktu(table.getValueAt(selRow, 2).toString());
					task.setDeskripsi(table.getValueAt(selRow, 3).toString());
					task.setTipe(tipe.toString());
					stcUpdate.push(task);
					setNamaLama(tampil.get(0).toString());

					showPanel("update");
				} else {
					JOptionPane.showMessageDialog(null,
							"pilih task yang ingin diupdate");
				}

			}

			
		});
		Edit.setIcon(new ImageIcon(Interface.class.getResource("/toturialJDBC/icon/edit1.png")));
		Edit.setBounds(250, 17, 76, 32);
		contentPane.add(Edit);

		final JLabel lblAdd = new JLabel("");
		lblAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				showPanel("add");
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {

				lblAdd.setIcon(new ImageIcon(Interface.class
						.getResource("/toturialJDBC/icon/Add2.png")));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				lblAdd.setIcon(new ImageIcon(Interface.class
						.getResource("/toturialJDBC/icon/Add1.png")));
			}
		});

		lblAdd.setIcon(new ImageIcon(Interface.class
				.getResource("/toturialJDBC/icon/Add1.png")));
		lblAdd.setBounds(149, 17, 76, 32);
		contentPane.add(lblAdd);

		JLabel lblTanggal = new JLabel("Bulan");
		lblTanggal.setForeground(Color.WHITE);
		lblTanggal.setFont(new Font("Algerian", Font.PLAIN, 36));
		lblTanggal.setBounds(8, 17, 46, 47);
		lblTanggal.setText(hari.format(now));
		contentPane.add(lblTanggal);

		JLabel lblNewLabel = new JLabel("Hari");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(16, -6, 31, 38);
		contentPane.add(lblNewLabel);
		lblNewLabel.setText(bulan.format(now));

		final JLabel lblClock = new JLabel("New label");
		lblClock.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblClock.setForeground(Color.WHITE);
		lblClock.setBounds(64, -3, 112, 32);
		contentPane.add(lblClock);

		ActionListener Listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String sJam = " ";
				String sMenit = " ";
				String sDetik = " ";

				Date today = new Date();
				int now_Jam = today.getHours(); // Mengambil jam saat ini
				int now_Menit = today.getMinutes(); // Mengambil menit saat ini
				int now_Detik = today.getSeconds(); // Mengambil detik saat ini

				if (now_Jam <= 9) {
					sJam = "0"; // Menambah angka 0 di depan Jam
				}
				if (now_Menit <= 9) {
					sMenit = "0"; // Menambah angka 0 di depan Menit
				}
				if (now_Detik <= 9) {
					sDetik = "0"; // Menambah angka 0 di depan Detik
				}

				String Jam = sJam + Integer.toString(now_Jam);
				String Menit = sMenit + Integer.toString(now_Menit);
				String Detik = sDetik + Integer.toString(now_Detik);
				lblClock.setText(Jam + ":" + Menit + ":" + Detik);

			}
		};

		Timer waktu = new Timer(1000, Listener);
		waktu.start();

		JLabel Calender = new JLabel("");
		Calender.setIcon(new ImageIcon(Interface.class
				.getResource("/toturialJDBC/icon/calender-google-icon.png")));
		Calender.setBounds(0, -16, 76, 88);
		contentPane.add(Calender);

		lblJlhToday = new JLabel("");
		lblJlhToday.setForeground(Color.WHITE);
		lblJlhToday.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblJlhToday.setBounds(44, 102, 26, 24);
		String jlh = jumlahHariIni();
		lblJlhToday.setText(jlh);
		contentPane.add(lblJlhToday);

		lblJlhAll = new JLabel("");
		lblJlhAll.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblJlhAll.setForeground(Color.WHITE);
		lblJlhAll.setBounds(44, 190, 26, 24);
		String jumlah = jumlahSemua();

		lblcTomm = new JLabel("");
		lblcTomm.setForeground(Color.WHITE);
		lblcTomm.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblcTomm.setBounds(45, 140, 31, 31);
		lblcTomm.setText(jumlahBesok());
		contentPane.add(lblcTomm);
		lblJlhAll.setText(jumlah);
		contentPane.add(lblJlhAll);

		PanelGerak = new JPanel();
		PanelGerak.setBounds(86, 53, 701, 409);
		contentPane.add(PanelGerak);
		PanelGerak.setLayout(new CardLayout(0, 0));

		JPanel panelDisplay = new JPanel();
		panelDisplay.setBackground(SystemColor.text);
		PanelGerak.add(panelDisplay, "display");
		panelDisplay.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setBounds(0, 0, 701, 379);
		panelDisplay.add(scrollPane);

		table = new JTable(tt);
		table.setFont(new Font("Calibri", Font.PLAIN, 14));
		sorter = new TableRowSorter<TableModel>(tt);
		panelDisplay.setLayout(null);
		table.setRowSorter(sorter);
		scrollPane.setViewportView(table);

		JPanel panelAdd = new JPanel();
		PanelGerak.add(panelAdd, "add");
		panelAdd.setLayout(null);

		textNama = new JTextField();
		textNama.setFont(new Font("Sitka Small", Font.PLAIN, 12));
		textNama.setColumns(10);
		textNama.setBounds(279, 93, 308, 20);
		panelAdd.add(textNama);

		JLabel labelNama = new JLabel("Nama");
		labelNama.setFont(new Font("Sitka Small", Font.BOLD, 13));
		labelNama.setBounds(104, 96, 79, 14);
		panelAdd.add(labelNama);

		dateAwal = new JDateChooser();
		dateAwal.setBounds(279, 145, 148, 20);
		panelAdd.add(dateAwal);

		dateAkhir = new JDateChooser();
		dateAkhir.setBounds(279, 197, 148, 20);
		panelAdd.add(dateAkhir);

		JLabel labelStart = new JLabel("Start");
		labelStart.setFont(new Font("Sitka Small", Font.BOLD, 13));
		labelStart.setBounds(104, 151, 79, 14);
		panelAdd.add(labelStart);

		JLabel labelDeadline = new JLabel("Deadline");
		labelDeadline.setFont(new Font("Sitka Small", Font.BOLD, 13));
		labelDeadline.setBounds(104, 203, 79, 14);
		panelAdd.add(labelDeadline);

		rdbtnTimedTask = new JRadioButton("Timed Task");
		rdbtnTimedTask.setFont(new Font("Sitka Small", Font.BOLD, 13));
		rdbtnTimedTask.setBounds(104, 46, 109, 23);
		rdbtnTimedTask.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dateAwal.setEnabled(true);
				dateAkhir.setEnabled(true);
			}
		});
		panelAdd.add(rdbtnTimedTask);

		rdbtnFloatingTask = new JRadioButton("Floating Task");
		rdbtnFloatingTask.setFont(new Font("Sitka Small", Font.BOLD, 13));
		rdbtnFloatingTask.setBounds(254, 46, 162, 23);
		rdbtnFloatingTask.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dateAwal.setEnabled(false);
				dateAkhir.setEnabled(false);
			}
		});
		panelAdd.add(rdbtnFloatingTask);

		rdbtnDeadlineTask = new JRadioButton("Deadline Task");
		rdbtnDeadlineTask.setFont(new Font("Sitka Small", Font.BOLD, 13));
		rdbtnDeadlineTask.setBounds(449, 46, 148, 23);
		rdbtnDeadlineTask.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dateAwal.setEnabled(false);
				dateAkhir.setEnabled(true);
			}
		});
		panelAdd.add(rdbtnDeadlineTask);

		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnDeadlineTask);
		group.add(rdbtnFloatingTask);
		group.add(rdbtnTimedTask);

		JLabel lblDeskripsi = new JLabel("Deskripsi");
		lblDeskripsi.setFont(new Font("Sitka Small", Font.BOLD, 13));
		lblDeskripsi.setBounds(104, 263, 131, 14);
		panelAdd.add(lblDeskripsi);

		final JTextArea textDeskripsi = new JTextArea();
		textDeskripsi.setFont(new Font("Sitka Small", Font.PLAIN, 13));
		textDeskripsi.setBounds(279, 258, 308, 68);
		panelAdd.add(textDeskripsi);

		JLabel lblAddTask = new JLabel("Add Task");
		lblAddTask.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lblAddTask.setBounds(25, 11, 109, 28);
		panelAdd.add(lblAddTask);

		final JLabel lblSave = new JLabel("");
		lblSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String awal = "";
				String akhir = "";
				if (textNama.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "masukkan nama task");
				} else {
					if (rdbtnDeadlineTask.isSelected()) {
						akhir = dateFormat.format(dateAkhir.getDate());
						insert(null, textNama.getText(), "", akhir,
								textDeskripsi.getText(), "3");
					} else if (rdbtnTimedTask.isSelected()) {
						awal = dateFormat.format(dateAwal.getDate());
						akhir = dateFormat.format(dateAkhir.getDate());
						insert(null, textNama.getText(), awal, akhir,
								textDeskripsi.getText(), "1");
					} else if (rdbtnFloatingTask.isSelected()) {
						insert(null, textNama.getText(), "", "",
								textDeskripsi.getText(), "2");
					}
					JOptionPane.showMessageDialog(null,
							"tugas berhasil ditambahkan");
					Date date = new Date();
					db.insertLog("add data", textNama.getText(),
							date.toString());
					textNama.setText("");
					dateAwal.setDate(null);
					dateAkhir.setDate(null);
					textDeskripsi.setText("");
					tt.refreshTable();
					stcLog.push("Insert");
					String jumlah = jumlahHariIni();
					String jlh = jumlahSemua();
					String jlhBesok = jumlahBesok();
					lblJlhAll.setText(jlh);
					lblJlhToday.setText(jumlah);
					lblcTomm.setText(jlhBesok);

					tl.refreshLog();
					showPanel("display");
				}
			}

			
		});
		lblSave.setBounds(548, 349, 48, 49);
		lblSave.setIcon(new ImageIcon(Interface.class.getResource("/icon/Save_add.png")));
		panelAdd.add(lblSave);

		JPanel panelUpdate = new JPanel();
		PanelGerak.add(panelUpdate, "update");
		panelUpdate.setLayout(null);

		textNamaUpdate = new JTextField();
		textNamaUpdate.setColumns(10);
		textNamaUpdate.setBounds(270, 102, 296, 20);
		panelUpdate.add(textNamaUpdate);

		dateAwalUpdate = new JDateChooser();
		dateAwalUpdate.setBounds(270, 151, 156, 20);
		panelUpdate.add(dateAwalUpdate);

		dateAkhirUpdate = new JDateChooser();
		dateAkhirUpdate.setBounds(270, 196, 156, 20);
		panelUpdate.add(dateAkhirUpdate);

		rdbtnTimedTaskUp = new JRadioButton("Timed Task");
		rdbtnTimedTaskUp.setFont(new Font("Sitka Small", Font.PLAIN, 13));
		rdbtnTimedTaskUp.setBounds(96, 53, 109, 23);
		rdbtnTimedTaskUp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dateAwalUpdate.setEnabled(true);
				dateAkhirUpdate.setEnabled(true);
			}
		});
		panelUpdate.add(rdbtnTimedTaskUp);

		rdbtnFloatingTaskUp = new JRadioButton("Floating Task");
		rdbtnFloatingTaskUp.setFont(new Font("Sitka Small", Font.PLAIN, 13));
		rdbtnFloatingTaskUp.setBounds(252, 53, 134, 23);
		rdbtnFloatingTaskUp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dateAwalUpdate.setEnabled(false);
				dateAkhirUpdate.setEnabled(false);
			}
		});
		panelUpdate.add(rdbtnFloatingTaskUp);

		rdbtnDeadlineTaskUp = new JRadioButton("Deadline Task");
		rdbtnDeadlineTaskUp.setFont(new Font("Sitka Small", Font.PLAIN, 13));
		rdbtnDeadlineTaskUp.setBounds(439, 53, 140, 23);
		rdbtnDeadlineTaskUp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				dateAwalUpdate.setEnabled(false);
				dateAkhirUpdate.setEnabled(true);
			}
		});
		panelUpdate.add(rdbtnDeadlineTaskUp);

		ButtonGroup groupUpdate = new ButtonGroup();
		groupUpdate.add(rdbtnDeadlineTaskUp);
		groupUpdate.add(rdbtnFloatingTaskUp);
		groupUpdate.add(rdbtnTimedTaskUp);

		JLabel lblUpdateTask = new JLabel("Update Task");
		lblUpdateTask.setFont(new Font("Segoe UI Semibold", Font.BOLD, 13));
		lblUpdateTask.setBounds(31, 11, 134, 35);
		panelUpdate.add(lblUpdateTask);

		JLabel lblNamaUpdate = new JLabel("Nama");
		lblNamaUpdate.setFont(new Font("Sitka Small", Font.PLAIN, 13));
		lblNamaUpdate.setBounds(96, 105, 69, 14);
		panelUpdate.add(lblNamaUpdate);

		JLabel lblStartUpdate = new JLabel("Start");
		lblStartUpdate.setFont(new Font("Sitka Small", Font.PLAIN, 13));
		lblStartUpdate.setBounds(96, 157, 69, 14);
		panelUpdate.add(lblStartUpdate);

		JLabel lblDeadlineUpdate = new JLabel("Deadline");
		lblDeadlineUpdate.setFont(new Font("Sitka Small", Font.PLAIN, 13));
		lblDeadlineUpdate.setBounds(96, 202, 95, 14);
		panelUpdate.add(lblDeadlineUpdate);

		JLabel lblDeskripsiUpdate = new JLabel("Deskripsi");
		lblDeskripsiUpdate.setFont(new Font("Sitka Small", Font.PLAIN, 13));
		lblDeskripsiUpdate.setBounds(96, 248, 95, 14);
		panelUpdate.add(lblDeskripsiUpdate);

		textDeskripsiUpdate = new JTextArea();
		textDeskripsiUpdate.setFont(new Font("Sitka Small", Font.PLAIN, 13));
		textDeskripsiUpdate.setBounds(270, 245, 296, 87);
		panelUpdate.add(textDeskripsiUpdate);

		final JLabel lblOk = new JLabel("");
		lblOk.setIcon(new ImageIcon(Interface.class.getResource("/icon/Save_add.png")));
		lblOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String awal = "";
				String akhir = "";
				if (dateAwalUpdate.isEnabled()
						&& !dateAwalUpdate.getDate().equals("")) {
					awal = dateFormat.format(dateAwalUpdate.getDate());
				}
				if (dateAkhirUpdate.isEnabled()
						&& !dateAkhirUpdate.getDate().equals("")) {
					akhir = dateFormat.format(dateAkhirUpdate.getDate());
				}

				if (textNamaUpdate.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "masukkan nama task");
				} else {
						int result = JOptionPane.showConfirmDialog(null,
								"anda yakin ingin mengubah task ini?");
						if (result == JOptionPane.YES_OPTION) {
							if (rdbtnDeadlineTaskUp.isSelected()) {
								update(idToUpdate.toString(),
										textNamaUpdate.getText(), "", akhir,
										textDeskripsiUpdate.getText(), "3");
							} else if (rdbtnTimedTaskUp.isSelected()) {
								update(idToUpdate.toString(),
										textNamaUpdate.getText(), awal, akhir,
										textDeskripsiUpdate.getText(), "1");
							} else if (rdbtnFloatingTaskUp.isSelected()) {
								update(idToUpdate.toString(),
										textNamaUpdate.getText(), "", "",
										textDeskripsiUpdate.getText(), "2");
							}
							textNamaUpdate.setText("");
							dateAwalUpdate.setDate(null);
							dateAkhirUpdate.setDate(null);
							textDeskripsiUpdate.setText("");

							JOptionPane.showMessageDialog(null,
									"task berhasil di-edit");
							Date da = new Date();
							db.insertLog("Update", getNamaLama().toString(),
									da.toString());
							stcLog.push("Update");
							showPanel("display");
							String jumlah = jumlahHariIni();
							String jlh = jumlahSemua();
							String jlhBesok = jumlahBesok();
							lblJlhAll.setText(jlh);
							lblJlhToday.setText(jumlah);
							lblcTomm.setText(jlhBesok);
							((DefaultRowSorter<TableModel, Integer>) sorter)
									.setRowFilter(null);
							tt.refreshTable();
							tl.refreshLog();
						} else if (result == JOptionPane.CANCEL_OPTION) {
							showPanel("display");
						}
					//}

				}
			}

			
		});
		lblOk.setBounds(520, 355, 46, 43);
		panelUpdate.add(lblOk);

		JPanel panelLog = new JPanel();
		PanelGerak.add(panelLog, "log");
		panelLog.setLayout(null);

		JScrollPane scrollPaneLog = new JScrollPane();
		scrollPaneLog.setBounds(10, 11, 619, 330);
		panelLog.add(scrollPaneLog);

		tableLog = new JTable(tl);
		tableLog.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null,
				null, null));
		sorLog = new TableRowSorter<TableModel>(tl);
		panelDisplay.setLayout(null);
		scrollPaneLog.setViewportView(tableLog);

		final JLabel lblSaveToFile = new JLabel("");
		lblSaveToFile
				.setIcon(new ImageIcon(Interface.class.getResource("/icon/Clear2.png")));
		lblSaveToFile.setToolTipText("Save to File");
		lblSaveToFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JFileChooser chooser = new JFileChooser();
				int retrieve = chooser.showSaveDialog(null);
				ArrayList<Log> log = new ArrayList<Log>();
				log = db.getLog();

				if (retrieve == JFileChooser.APPROVE_OPTION) {
					try {
						FileWriter fw = new FileWriter(chooser
								.getSelectedFile() + ".txt");
						BufferedWriter bufer = new BufferedWriter(fw);
						for (int i = 0; i < log.size(); i++) {
							bufer.write(i + 1 + ". "
									+ log.get(i).getEvent().toString() + " "
									+ log.get(i).getNamaTask().toString() + " "
									+ log.get(i).getWaktu().toString());
							bufer.newLine();
						}

						bufer.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

			

		});
		lblSaveToFile.setBounds(526, 355, 81, 43);
		panelLog.add(lblSaveToFile);

		final JLabel lblClearLog = new JLabel("");
		lblClearLog
				.setIcon(new ImageIcon(Interface.class.getResource("/icon/saveto2.png")));
		lblClearLog.setToolTipText("Clear Log");
		lblClearLog.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				db.deleteLog();
				tl.refreshLog();
			}

			
		});
		lblClearLog.setBounds(445, 355, 60, 43);
		panelLog.add(lblClearLog);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Interface.class
				.getResource("/toturialJDBC/icon/Untitled-1.jpg")));
		label.setBounds(0, 0, 787, 51);
		contentPane.add(label);

		final JLabel lblToday = new JLabel("Today");
		lblToday.setToolTipText("Due task for today");
		lblToday.setIcon(new ImageIcon(Interface.class.getResource("/icon/TodayAwal2.png")));
		lblToday.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				showPanel("display");
				Date date = new Date();
				String d = dateFormat.format(date);
				RowFilter<TableModel, Object> filter = RowFilter
						.orFilter(Arrays.asList(RowFilter.regexFilter(d, 2)));
				((DefaultRowSorter<TableModel, Integer>) sorter)
						.setRowFilter(filter);
				tt.refreshTable();
			}

			
		});
		lblToday.setBounds(0, 109, 82, 24);
		contentPane.add(lblToday);

		final JLabel lblAll = new JLabel("All Task");
		lblAll.setToolTipText("All Task");
		lblAll.setIcon(new ImageIcon(Interface.class.getResource("/icon/Today_all2.png")));
		lblAll.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				showPanel("display");
				((DefaultRowSorter<TableModel, Integer>) sorter)
						.setRowFilter(null);
				tt.refreshTable();
			}

			
		});
		lblAll.setBounds(0, 190, 89, 38);
		contentPane.add(lblAll);

		final JLabel lblTommorow = new JLabel("Tommorow");
		lblTommorow.setToolTipText("Task for Tommorow");
		lblTommorow
				.setIcon(new ImageIcon(Interface.class.getResource("/icon/Today2.png")));
		lblTommorow.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Date date = new Date();
				String d = dateFormat.format(date);
				int temp = Integer.parseInt(d.substring(0, 2)) + 1;
				String fil = Integer.toString(temp);
				RowFilter<TableModel, Object> filter = RowFilter
						.orFilter(Arrays.asList(RowFilter.regexFilter(fil, 2)));
				((DefaultRowSorter<TableModel, Integer>) sorter)
						.setRowFilter(filter);
				tt.refreshTable();
			}

			
		});
		lblTommorow.setBounds(0, 144, 89, 32);
		contentPane.add(lblTommorow);
		String jlhBesok = jumlahBesok();

		final JLabel lblLog = new JLabel("LOg");
		lblLog.setToolTipText("Logging");
		lblLog.setIcon(new ImageIcon(Interface.class.getResource("/icon/Log.png")));
		lblLog.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				showPanel("log");
			}

			
		});
		lblLog.setBounds(0, 239, 90, 32);
		contentPane.add(lblLog);

		final JLabel lblHelp = new JLabel("Help");
		lblHelp.setToolTipText("Help");
		lblHelp.addMouseListener(new MouseAdapter() {
			

			@Override
			public void mouseClicked(MouseEvent arg0) {

				Help baru = new Help();
				baru.setVisible(true);

			}

			
		});
		lblHelp.setIcon(new ImageIcon(Interface.class.getResource("/icon/Help1.png")));
		lblHelp.setBounds(0, 427, 86, 24);
		contentPane.add(lblHelp);

		JLabel lblmenusamping = new JLabel("");
		lblmenusamping.setBounds(0, 40, 89, 422);
		contentPane.add(lblmenusamping);
		lblmenusamping
				.setIcon(new ImageIcon(Interface.class.getResource("/icon/bar2.png")));
	}

	public void insert(String id, String nama, String awal, String akhir,
			String deskripsi, String tipe) {
		try {
			db.insertData(id, nama, awal, akhir, deskripsi, tipe);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update(String id, String nama, String start, String deadline,
			String deskripsi, String tipe) {
		try {
			db.updateData(id, nama, start, deadline, deskripsi, tipe);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void showPanel(String namaPanel) {
		CardLayout cardLayout = (CardLayout) (PanelGerak.getLayout());
		cardLayout.show(PanelGerak, namaPanel);
	}

	public String getNamaLama() {
		return namaLama;
	}

	public void setNamaLama(String namaLama) {
		this.namaLama = namaLama;
	}

	public String jumlahHariIni() {
		Date date = new Date();
		String dd = dateFormat.format(date);
		timeCount = db.getWaktu(dd.toString());
		int jlhToday = timeCount;
		String jlh = Integer.toString(jlhToday);
		return jlh;
	}

	public String jumlahSemua() {
		int jlhAll = tt.getRowCount();
		String jumlah = Integer.toString(jlhAll);
		return jumlah;
	}

	public String jumlahBesok() {
		Date date = new Date();
		String dd = dateFormat.format(date);
		int temp = Integer.parseInt(dd.substring(0, 2)) + 1;
		String fil = Integer.toString(temp) + "-" + dd.substring(3);
		timeCount = db.getWaktu(fil.toString());
		int jlhToday = timeCount;
		String jlh = Integer.toString(jlhToday);
		return jlh;
	}
}
