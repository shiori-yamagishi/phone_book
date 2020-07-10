package com.ojtproject.phonebook.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.ojtproject.phonebook.dao.PhoneBookRepository;
import com.ojtproject.phonebook.form.RegistForm;
import com.ojtproject.phonebook.form.SearchForm;
import com.ojtproject.phonebook.utility.Validation;

@Service
public class RegistService {

	/*@Autowired
	private HttpSession session;*/
	@Autowired
	private PhoneBookRepository phoneBookRepository;

	SearchForm search;
	static final String REGIST_MESSAGE = "追加しました";

	//追加画面初期表示
	public void registInit(ModelAndView mav) {
		mav.setViewName("regist");
	}

	//追加処理、入力チェック処理
	public boolean regist(RegistForm input, ModelAndView mav) {
		String name = input.getName();
		String phoneNumber = input.getPhoneNumber();
		boolean registResult = true;

		if (!Validation.blank(name, phoneNumber, mav)) {
			registResult = false;
			return registResult;
		}

		if (!Validation.validateName(name, mav) | !Validation.validatePhoneNumber(phoneNumber, mav)) {
			registResult = false;
			return registResult;
		}

		try {
			phoneBookRepository.regist(name, phoneNumber);
			mav.addObject("registMsg", REGIST_MESSAGE);
		} catch (Exception e) {
			mav.addObject("registError", "追加に失敗しました");
			registResult = false;
			return registResult;
		}

		return registResult;

	}

}
