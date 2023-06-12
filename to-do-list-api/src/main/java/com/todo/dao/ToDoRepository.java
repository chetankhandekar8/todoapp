package com.todo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.entity.Todo;

public interface ToDoRepository extends JpaRepository<Todo, Integer> {

	List<Todo> findAllByOrderByTaskIdDesc();

}
