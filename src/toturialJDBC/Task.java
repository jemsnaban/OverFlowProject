package toturialJDBC;

import java.io.Serializable;

public class Task {
	private String id;
	private String task_name;
	private String start;
	private String waktu;
	private String deskripsi;
	private String tipe;
	
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	
	public Serializable getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTask_name() {
		return task_name;
	}
	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}
	public String getWaktu() {
		return waktu;
	}
	public void setWaktu(String waktu) {
		this.waktu = waktu;
	}
	public String getDeskripsi() {
		return deskripsi;
	}
	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}
	public String getTipe() {
		return tipe;
	}
	public void setTipe(String tipe) {
		this.tipe = tipe;
	}
}
