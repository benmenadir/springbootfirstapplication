package com.myapp.springboot.service;

import java.util.Date;
import java.util.List;


import com.myapp.springboot.model.Todo;


public interface TodoServiceI {

	public List<Todo> retrieveTodos(String user);
	public void addTodo(String name, String desc, Date targetDate, boolean isDone);
	public void deleteTodo(int id);
	public Todo retrieveTodo(int id) ;
	public void updateTodo(Todo todo);
}
