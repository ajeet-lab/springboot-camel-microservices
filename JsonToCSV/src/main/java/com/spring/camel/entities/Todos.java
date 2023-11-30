package com.spring.camel.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@Entity 
@CsvRecord(separator = ",", skipFirstLine = true, generateHeaderColumns = true)
public class Todos {
	
	@Id
	@DataField(pos = 1, columnName = "id")
	private int id;
	@DataField(pos = 2, columnName = "userId")
	private int userId;
	@DataField(pos = 3, columnName = "title")
	private String title;
	@DataField(pos = 4, columnName = "completed")
	private String completed;
	
	public Todos() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Todos(int id, int userId, String title, String completed) {
		super();
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.completed = completed;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompleted() {
		return completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}

	@Override
	public String toString() {
		return "Todos [id=" + id + ", userId=" + userId + ", title=" + title + ", completed=" + completed + "]";
	}
		
	
	
}
