package com.todo.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.common.InvalidRequestException;
import com.todo.common.ToDoConstant;
import com.todo.entity.Todo;
import com.todo.service.ToDoService;

/**
 * @author Admin
 *
 */
@RestController
@RequestMapping("/todo")
public class ToDoController {

	@Autowired
	private ToDoService service;
	
	/**
	 * The function receives a POST request, processes it, creates a new Todo and saves it to the database
	 * @param todo
	 * @return returns a resource link to the created Todo
	 */
	@PostMapping
	public ResponseEntity<Todo> saveTodoTask(@RequestBody Todo todo) {

		try {
			return new ResponseEntity<>(service.saveTodoTask(todo), HttpStatus.CREATED);
		} catch (Exception ex) { 
			throw new InvalidRequestException(ToDoConstant.INVALID_REQUEST_MSG);
		}

	}

	/**
	 * The function receives a GET request, processes it and gives back a list of Todo as a response.
	 */
	@GetMapping
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<>(service.getTodoTaskList(), HttpStatus.OK);
	}

	/**
	 * The function receives a PUT request.
	 * @param id
	 * @param fields
	 * @return list of Todo as a response.
	 */
	@PutMapping("/{todoId}")
	public ResponseEntity<?> updateTodoByField(@PathVariable("todoId") Integer id,
			@RequestBody Map<String, Object> fields) {
		try {
			Todo todo = service.updateTodoTaskByFields(id, fields);
			if (todo != null) {
				return new ResponseEntity<>(todo, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			throw new InvalidRequestException(ToDoConstant.INVALID_REQUEST_MSG);
		}
	}

	@GetMapping("/{todoId}")
	public ResponseEntity<?> getTodoById(@PathVariable("todoId") Integer id) {
		Optional<Todo> todoOptional = service.getTodoTaskById(id);
		if (todoOptional.isPresent()) {
			return new ResponseEntity<>(todoOptional.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * The function receives a DELETE request, deletes the Todo with the specified Id.
	 * @param id
	 * @return 
	 */
	@DeleteMapping("/{todoId}")
	public ResponseEntity<?> deleteTodoById(@PathVariable("todoId") Integer id) {
		try {
			Optional<Todo> todoOptional = service.getTodoTaskById(id);
			if (todoOptional.isPresent()) {
				service.deleteTodoTaskById(id);
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			throw new InvalidRequestException(ToDoConstant.INVALID_REQUEST_MSG);
		}
	}
}
