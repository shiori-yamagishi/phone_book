package com.ojtproject.phonebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.ojtproject.phonebook.dao.PhoneBookRepository;
import com.ojtproject.phonebook.form.RegistForm;
import com.ojtproject.phonebook.utility.Validation;

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
		String name = input.getName();
		String phoneNumber = input.getPhoneNumber();
		if (!Validation.validateName(name, mav) || !Validation.validatePhoneNumber(phoneNumber, mav)) {
			return;
		}
		phoneBookRepository.regist(name, phoneNumber);
	}

	/*public void validation() {
		String inputedName = null;
		ModelAndView mav = null;
		Check.validateName(inputedName, mav);
		String inputedphoneNumber = null;
		Check.validatephoneNumber(inputedphoneNumber, mav);

	}*/

}
