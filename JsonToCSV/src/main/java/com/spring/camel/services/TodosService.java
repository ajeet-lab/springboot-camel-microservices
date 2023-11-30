package com.spring.camel.services;

import java.util.List;

import com.spring.camel.entities.Todos;

public interface TodosService {

	public List<Todos> getAllTodos();
	public Todos todoGetById(int id);
	public Todos updateTodo(Todos body, int id);
	
}
