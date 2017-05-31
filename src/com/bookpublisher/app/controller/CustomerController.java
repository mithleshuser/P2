package com.bookpublisher.app.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.bookpublisher.app.dto.AuthorsDTO;
import com.bookpublisher.app.dto.BookDTO;
import com.bookpublisher.app.dto.CustomerDTO;
import com.bookpublisher.app.dto.LoginDTO;
import com.bookpublisher.app.dto.PublisherDTO;
import com.bookpublisher.app.service.CustomerService;


@Controller
@RequestMapping("/")
public class CustomerController {
	
	@Autowired
	private CustomerService userService;
	
	@RequestMapping(method=RequestMethod.POST,value="/user")
	public String addUser(CustomerDTO user, ModelMap map){
		System.out.println("user going to add");
		String id=userService.add(user);
		if(id!=null){
			map.put("msg",user.getName()+" registered with Id "+id);
		}
		return "index";
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/login")
	public String userLogin(LoginDTO user, ModelMap map){
		try{
			LoginDTO user2=userService.login(user);
		map.put("msg", user2.getEmail()+" succefully logged In");
		return "home";
		}catch(NullPointerException e){
			map.put("msg", "User Not found!!");
			return "login";
		}
	}
	
	
	
	@RequestMapping(method=RequestMethod.POST,value="/publists")
	public String addPublication(Map<String, Object> model, @ModelAttribute("published") PublishedForm obj ,BindingResult result, SessionStatus status){
		System.out.println("addPublication ---- ");
		PublisherDTO p_obj = copyToObjects(obj);
		int id=userService.addPublishedForm(p_obj);
		if(id!=0){
			model.put("msg"," registered with Id "+id);
		}
		return "index";
	
	}
	

	
	private PublisherDTO copyToObjects(PublishedForm obj) {
		PublisherDTO publisherObject = new PublisherDTO();
		AuthorsDTO authorObj = new AuthorsDTO();
		BookDTO bookObj = new BookDTO();
		
		
		Set<AuthorsDTO> set_authors = new HashSet<AuthorsDTO>();
		Set<BookDTO> set_books = new HashSet<BookDTO>();
		Set<PublisherDTO> set_publisherObj = new HashSet<PublisherDTO>();
		
		publisherObject.setPublishar_name(obj.getPublishar_name());
		publisherObject.setPublishar_ratings(obj.getAuthor_ratings());
		
		authorObj.setAuthor_name(obj.getAuthor_name());
		authorObj.setAuthor_ratings(obj.getAuthor_ratings());
		
		bookObj.setBook_name(obj.getBook_name());
		bookObj.setBook_edition(obj.getBook_edition());
		bookObj.setBook_ratings(obj.getBook_ratings());
		
		set_authors.add(authorObj);
		set_books.add(bookObj);
		set_publisherObj.add(publisherObject);
		
		bookObj.setAuthors(set_authors);
		authorObj.setBooks(set_books);
		authorObj.setPublisher(set_publisherObj);
		publisherObject.setAuthors(set_authors);
		
		
		
		return publisherObject;
	}

	@RequestMapping(method=RequestMethod.GET,value="/login_link")
	public String getLoginPage(){
		return "login";
	}
	
	

}
