package com.ojtproject.phonebook.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ojtproject.phonebook.dao.PhoneBookRepository;
import com.ojtproject.phonebook.form.UpdateForm;

@Service
public class UpdateService {

	@Autowired
	private HttpSession session;
	@Autowired
	private PhoneBookRepository phoneBookRepository;

	public void updateInit(ModelAndView mav, @RequestParam(value = "id", required = true) int id,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "phoneNumber", required = true) String phoneNumber) {

		mav.addObject("id", id);
		mav.addObject("name", name);
		mav.addObject("phoneNumber", phoneNumber);
		mav.setViewName("update");
	}

	public void update(UpdateForm input, ModelAndView mav,
			@RequestParam(value = "id", required = true) int id) {
		String name = input.getName();
		String phoneNumber = input.getPhoneNumber();

		phoneBookRepository.update(name, phoneNumber, id);
	}

}
