package com.todo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.todo.entity.Todo;
import com.todo.entity.TodoList;


@SpringBootTest
class ToDoListApiApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";
	
	private String formattingString = "%s:%s/todo";

	private static RestTemplate resttemplate;

	@BeforeAll
	public static void init() {
		resttemplate = new RestTemplate();
	}

	@BeforeEach
	
	public void setUp() {
		baseUrl = String.format(formattingString, baseUrl, port);
	}

	@Test
	@Order(1)
	public void testSaveFirstTodo() {
			Todo todo = new Todo("First task", false);
			ResponseEntity<Todo> response = resttemplate.postForEntity(baseUrl, todo, Todo.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
			assertThat(response.getBody().getTaskId()).isEqualTo(1);
			assertThat(response.getBody().getTaskName()).isEqualTo(todo.getTaskName());
	}
	
	@Test
	@Order(2)
	public void testSaveSecondTodo() {
			Todo todo = new Todo("Second task", false);
			ResponseEntity<Todo> response = resttemplate.postForEntity(baseUrl, todo, Todo.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
			assertThat(response.getBody().getTaskId()).isEqualTo(2);
			assertThat(response.getBody().getTaskName()).isEqualTo(todo.getTaskName());
	}
	
	
	@Test
	@Order(3)
	public void testSaveThirdTodo() {
			Todo todo = new Todo("Third task", false);
			ResponseEntity<Todo> response = resttemplate.postForEntity(baseUrl, todo, Todo.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
			assertThat(response.getBody().getTaskId()).isEqualTo(3);
			assertThat(response.getBody().getTaskId()).isEqualTo(todo.getTaskName());
	}
	
	@Test
	@Order(4)
	public void testGetTodoById() {
			ResponseEntity<Todo> response = resttemplate.getForEntity(baseUrl + "/1", Todo.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
			assertThat(response.getBody().getTaskId()).isEqualTo(1);
			assertThat(response.getBody().getTaskName()).isEqualTo("First task");
	}
	
	@Test
	@Order(5)
	public void testGetTodoList() {
			ResponseEntity<TodoList> response = resttemplate.getForEntity(baseUrl, TodoList.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
			assertThat(response.getBody().getTodoList().size()).isEqualTo(3);
	}
	
	@Test
	@Order(6)
	public void testUpdateFirstTodo() {
			 Map<String, Object> fields = new HashMap<String, Object>();
			 fields.put("task", "First task");
			 fields.put("completed", true);
			resttemplate.put(baseUrl+"/{id}", fields, 1);
			ResponseEntity<Todo> response = resttemplate.getForEntity(baseUrl + "/1", Todo.class);
			assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
			assertThat(response.getBody().getTaskId()).isEqualTo(1);
			assertThat(response.getBody().getTaskName()).isEqualTo(fields.get("task"));
			assertThat(response.getBody().isCompleted()).isEqualTo(fields.get("completed"));
	}
	
	@Test
	@Order(7)
	public void testDeleteTodo() {
		resttemplate.delete(baseUrl+"/{id}", 2);
		ResponseEntity<TodoList> response = resttemplate.getForEntity(baseUrl, TodoList.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody().getTodoList().size()).isEqualTo(2);
	}

}
