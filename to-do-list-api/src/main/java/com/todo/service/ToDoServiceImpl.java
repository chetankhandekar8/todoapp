package com.todo.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import com.todo.dao.ToDoRepository;
import com.todo.entity.Todo;


@Service
public class ToDoServiceImpl implements ToDoService {

	@Autowired
	private ToDoRepository repository;

	@Override
	public Todo saveTodoTask(Todo todo) {
		return repository.save(todo);
	}

	@Override
	public Optional<Todo> getTodoTaskById(int id) {
		return repository.findById(id);
	}

	@Override
	public List<Todo> getTodoTaskList() {

		List<Todo> todos = repository.findAllByOrderByTaskIdDesc();
		if (todos != null && todos.size() > 0) {
			return todos;
		} else {
			return new ArrayList<>();
		}
	}

	@Override
	public Todo updateTodoTaskByFields(int id, Map<String, Object> fields) {
		Optional<Todo> existingTodoTask = repository.findById(id);

		if (existingTodoTask.isPresent()) {
			fields.forEach((key, value) -> {
				Field field = ReflectionUtils.findRequiredField(Todo.class, key);
				field.setAccessible(true);
				ReflectionUtils.setField(field, existingTodoTask.get(), value);
			});

			return repository.save(existingTodoTask.get());
		}

		return null;
	}

	@Override
	public void deleteTodoTaskById(int todoId) {
		repository.deleteById(todoId);

	}
}
