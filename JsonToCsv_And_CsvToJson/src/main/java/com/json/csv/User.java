package com.json.csv;


import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ",", skipFirstLine = true, generateHeaderColumns = true)
public class User {
    @DataField(pos = 1, columnName = "userId")
    private int userId;
    @DataField(pos = 2, columnName = "id")
    private int id;

    @DataField(pos = 3, columnName = "todo")
    private String todo;

    @DataField(pos = 4, columnName = "completed")
    private Boolean completed;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
}
