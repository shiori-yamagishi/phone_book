package com.ojtproject.phonebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.ojtproject.phonebook.dao.PhoneBookRepository;
import com.ojtproject.phonebook.form.RegistForm;

@Service
public class RegistService {

	/*@Autowired
	private HttpSession session;*/
	@Autowired
	private PhoneBookRepository phoneBookRepository;

	public void registInit(ModelAndView mav) {
		mav.setViewName("regist");
	}

	public void regist(RegistForm input, ModelAndView mav) {
		int id = input.getId();
		String name = input.getName();
		String phoneNumber = input.getPhoneNumber();

		phoneBookRepository.regist(name, phoneNumber, id);
	}

}
