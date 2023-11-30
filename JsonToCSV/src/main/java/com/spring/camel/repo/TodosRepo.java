package com.spring.camel.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.camel.entities.Todos;

public interface TodosRepo extends JpaRepository<Todos, Integer> {

}
