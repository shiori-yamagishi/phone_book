package com.ojtproject.phonebook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.ojtproject.phonebook.dao.PhoneBookRepository;
import com.ojtproject.phonebook.entity.PhoneBook;
import com.ojtproject.phonebook.form.SearchForm;

@Service
public class SearchService {

	@Autowired

	public PhoneBookRepository phoneBookRepository;

	public List<PhoneBook> findAll() {
		return phoneBookRepository.findAll();
	}

	/* public void delete(int id) {
	    phoneBookRepository.deleteById(id);
	}*/

	public void execute(SearchForm input, ModelAndView mav) {
		mav.setViewName("search");

	}
}
