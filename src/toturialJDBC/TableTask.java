package toturialJDBC;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

//extend fungsi2 yang ada dalam kelas abstrak
public class TableTask extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6041155667940817464L;
	private ArrayList<Task> dataTask;
	private String[] kolom = { "Nama Task", "Mulai","Deadline", "Deskipsi"};
	private Database db;

	public TableTask() {
		db = new Database();
		dataTask = db.getData();
	}

	public String getColumnName(int idx) {
		return this.kolom[idx];
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return kolom.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return dataTask.size();
	}
	
	public void refreshTable(){
		dataTask = db.getData();
		fireTableDataChanged();
	}

	public void removeRow(int row) {
		dataTask.remove(row);
		fireTableRowsDeleted(row, row);
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if (columnIndex == 0) {
			return dataTask.get(rowIndex).getTask_name();
		} else if (columnIndex == 1) {
			return dataTask.get(rowIndex).getStart();
		} else if (columnIndex == 2) {
			return dataTask.get(rowIndex).getWaktu();
		}else if(columnIndex == 3){
			return dataTask.get(rowIndex).getDeskripsi();
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		// TODO Auto-generated method stub
		return col == 3;
	}

	public Object getIdToDo(int convertRowIndexToModel) {
		// TODO Auto-generated method stub
		return dataTask.get(convertRowIndexToModel).getId();
	}
	public Object getType(int convertRowIndexToModel) {
		// TODO Auto-generated method stub
		return dataTask.get(convertRowIndexToModel).getTipe();
	}

}
