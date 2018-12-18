package com.myapp.springboot.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


import org.springframework.stereotype.Service;

import com.myapp.springboot.model.Todo;

@Service
public class TodoService implements TodoServiceI {

	private static List<Todo> todos = new ArrayList<>();
	private static int todoCount = 3;

	static {
		todos.add(new Todo(1, "benno", "learn springMVC", new Date(), false));
		todos.add(new Todo(2, "benno", "learn struts", new Date(), false));
		todos.add(new Todo(1, "benno", "learn Hibernate", new Date(), false));
	}

	@Override
	public List<Todo> retrieveTodos(String user) {
		List<Todo> filteredTodos = new ArrayList<>();
		for (Todo todo : todos) {
			if (todo.getUser().equals(user))
				filteredTodos.add(todo);
		}

		return filteredTodos;
	}

	@Override
	public void addTodo(String name, String desc, Date targetDate, boolean isDone) {
		todos.add(new Todo(++todoCount, name, desc, targetDate, isDone));

	}

	@Override
	public void deleteTodo(int id) {
		Iterator<Todo> iterator = todos.iterator();
		while (iterator.hasNext()) {
			Todo todo = iterator.next();
			if (todo.getId() == id) {
				iterator.remove();
			}
		}

	}

	@Override
	public Todo retrieveTodo(int id) {
		for (Todo todo : todos) {
			if (todo.getId() == id)
				return todo;
		}
		return null;
	}

	public void updateTodo(Todo todo) {
		todos.remove(todo);
		todos.add(todo);
		
	}

}
