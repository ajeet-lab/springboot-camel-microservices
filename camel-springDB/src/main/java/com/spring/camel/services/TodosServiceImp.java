package com.spring.camel.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.camel.entities.Todos;
import com.spring.camel.repo.TodosRepo;

@Service("todosService")
public class TodosServiceImp implements TodosService {
	
	@Autowired
	private TodosRepo todosRepo;

	@Override
	public List<Todos> getAllTodos() {
		List<Todos> todos = todosRepo.findAll();
		return todos;
	}

	@Override
	public Todos todoGetById(int id) {
		Todos todo = todosRepo.findById(id).orElse(null);
		return todo;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Todos updateTodo(Todos body, int id) {
		Todos todo = todosRepo.findById(id).orElse(null);
//		Todos todo = todosRepo.getById(id);
		System.out.println(todo);
		todo.setId(todo.getId());
		todo.setTitle("Hello ajeet");
		todo.setUserId(body.getUserId());
		Todos updateTodos = todosRepo.save(todo);
		return updateTodos;
	}

}
