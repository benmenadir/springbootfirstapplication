package com.myapp.springboot.controller;



import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.myapp.springboot.model.Todo;
import com.myapp.springboot.service.TodoService;




@Controller
public class TodoController {
	
	@Autowired
	TodoService service; 
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// date  dd/MM/yyyy
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
		
	}
	
	@RequestMapping(value="/list-todos", method=RequestMethod.GET)
	public String showTodos(ModelMap model) {
		String name = getLoggedInUserName(); 
		model.put("todos", service.retrieveTodos(name));
		return "list-todos";
	}

	private String getLoggedInUserName() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (principal instanceof UserDetails) {
			return ((UserDetails)principal).getUsername();
		} 
		
		return principal.toString();
	}
	
	@RequestMapping(value="/add-todo", method=RequestMethod.GET)
	public String showAddTodoPage(ModelMap model) {
		Todo todo = new Todo(0,getLoggedInUserName(),"Default Desc",new Date(), false);
		model.addAttribute("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value="/add-todo", method=RequestMethod.POST)
	public String addTodoPage(ModelMap model, @Valid Todo todo,BindingResult result ) {
		if (result.hasErrors()) {
			return "todo";
		}
		service.addTodo(getLoggedInUserName(), todo.getDesc(), new Date(), false);
		model.clear();
		return "redirect:/list-todos";
	}
	@RequestMapping(value="/delete-todo", method=RequestMethod.GET)
	public String deleteTodoPage(@RequestParam("id") int id) {
		service.deleteTodo(id);
		return "redirect:/list-todos";
	}
	
	@RequestMapping(value="/update-todo", method=RequestMethod.GET)
	public String showUpdatedoPage(ModelMap model,@RequestParam("id") int id) {
		Todo todo = service.retrieveTodo(id);
		model.addAttribute("todo", todo);
		return "todo";
	}
	@RequestMapping(value="/update-todo", method=RequestMethod.POST)
	public String updatedoPage(ModelMap model, @Valid Todo todo,BindingResult result ) {
		if (result.hasErrors()) {
			return "todo";
		}
		todo.setUser(getLoggedInUserName());
		service.updateTodo(todo);
		return "redirect:/list-todos";
	}
}
