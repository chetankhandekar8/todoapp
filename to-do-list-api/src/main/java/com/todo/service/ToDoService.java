package com.todo.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.todo.entity.Todo;

public interface ToDoService {

	public Todo saveTodoTask(Todo todo);

	public Optional<Todo> getTodoTaskById(int id);

	public List<Todo> getTodoTaskList();

	public Todo updateTodoTaskByFields(int id, Map<String, Object> fields);

	public void deleteTodoTaskById(int todoId);
}
