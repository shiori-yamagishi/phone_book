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

	static final String REGIST_MESSAGE = "追加しました";

	//追加画面初期表示
	public void registInit(ModelAndView mav) {
		mav.setViewName("regist");
	}

	//追加処理、入力チェック処理
	public void regist(RegistForm input, ModelAndView mav) {
		String name = input.getName();
		String phoneNumber = input.getPhoneNumber();

		if (!Validation.blank(name, phoneNumber, mav)) {
			return;
		}

		if (!Validation.validateName(name, mav) | !Validation.validatePhoneNumber(phoneNumber, mav)) {
			return;
		}
		phoneBookRepository.regist(name, phoneNumber);
		mav.addObject("registMsg", REGIST_MESSAGE);
	}

}
