package toturialJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Database {
	private Connection con = null;
	private PreparedStatement ps = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public Database() {
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:projectrpl.sqlite");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void insertData(String id,String nama, String start, String deadline, String deskripsi,String tipe) throws ParseException {
		try {
			ps = con.prepareStatement("INSERT INTO task (ID,nama_task,start_time,deadline,deskripsi,tipe) VALUES (?,?,?,?,?,?)");
			ps.setString(1, id);
			ps.setString(2, nama);
			ps.setString(3, start);
			ps.setString(4, deadline);
			ps.setString(5, deskripsi);
			ps.setString(6, tipe);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void deleteData(String ID) {
		try {
			ps = con.prepareStatement("delete from task where id = ?");
			ps.setString(1, ID);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void updateData(String id,String nama,String start,String deadline,String deskripsi,String tipe) {
		try {
			ps = con.prepareStatement("update task set nama_task = ?, start_time = ?,deadline = ?, deskripsi = ?,tipe = ? where ID = ?");
			ps.setString(1, nama);
			ps.setString(2, start);
			ps.setString(3, deadline);
			ps.setString(4, deskripsi);
			ps.setString(5, tipe);
			ps.setString(6, id);
			//System.out.print(start);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void insertLog(String event, String nama, String waktu) {
		try {
			ps = con.prepareStatement("insert into Log (event,nama,time) values (?,?,?)");
			ps.setString(1, event);
			ps.setString(2, nama);
			ps.setString(3, waktu);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<Task> getData() {
		ArrayList<Task> data = new ArrayList<Task>();

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from task order by ID");
			while (rs.next()) {
				Task t = new Task();
				t.setId(rs.getString("ID"));
				t.setTask_name(rs.getString("nama_task"));
				t.setStart(rs.getString("start_time"));
				t.setWaktu(rs.getString("deadline"));
				t.setDeskripsi(rs.getString("deskripsi"));
				t.setTipe(rs.getString("tipe"));
				data.add(t);
	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	public ArrayList<Log> getLog() {
		ArrayList<Log> log = new ArrayList<Log>();

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from Log order by id");
			while (rs.next()) {
				Log l = new Log();
				l.setId(rs.getString("id"));
				l.setEvent(rs.getString("event"));
				l.setNamaTask(rs.getString("nama"));
				l.setWaktu(rs.getString("time"));
				log.add(l);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return log;
	}
	
	public ArrayList<String> getId() {
		ArrayList<String> data = new ArrayList<String>();

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery("select * from task");
			while (rs.next()) {
				String t;
				t = (rs.getString("ID"));
				data.add(t);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}
	
	public int getWaktu(String date) {
		String statement = "select * from task where deadline like '"+ date +"'";
		int count = 0;

		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(statement);
			while (rs.next()) {
				count++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}
	
	public void deleteLog() {
		try {
			ps = con.prepareStatement("delete from Log ");
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
