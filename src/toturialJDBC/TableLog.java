package toturialJDBC;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class TableLog extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5887930381178984762L;
	private ArrayList<Log> log = new ArrayList<>();
	private String[] kolom = { "event", "task", "waktu"};
	private Database db;
	
	public TableLog(){
		db = new Database();
		log = db.getLog();
	}
	
	public String getColumnName(int idx) {
		//return this.kolom[idx];
		return null;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return kolom.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return log.size();
	}

	@Override
	public Object getValueAt(int rowIdx, int colIdx) {
		// TODO Auto-generated method stub
		if(colIdx == 0){
			return log.get(rowIdx).getEvent();
		}else if(colIdx == 1){
			return log.get(rowIdx).getNamaTask();
		}else if(colIdx == 2){
			return log.get(rowIdx).getWaktu();
		}
		return null;
	}
	
	public void refreshLog(){
		log = db.getLog();
		fireTableDataChanged();
	}

}
