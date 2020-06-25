package com.ojtproject.phonebook.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ojtproject.phonebook.dao.PhoneBookRepository;
import com.ojtproject.phonebook.form.UpdateForm;
import com.ojtproject.phonebook.utility.Validation;

@Service
public class UpdateService {

	@Autowired
	private HttpSession session;
	@Autowired
	private PhoneBookRepository phoneBookRepository;

	//編集画面初期表示
	public void updateInit(ModelAndView mav, @RequestParam(value = "id", required = true) int id,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "phoneNumber", required = true) String phoneNumber) {

		mav.addObject("id", id);
		mav.addObject("name", name);
		mav.addObject("phoneNumber", phoneNumber);
		mav.setViewName("update");
	}

	//編集処理、入力チェック処理
	public void update(UpdateForm input, ModelAndView mav,
			@RequestParam(value = "id", required = true) int id) {
		String name = input.getName();
		String phoneNumber = input.getPhoneNumber();

		if (!Validation.validateName(name, mav) || !Validation.validatePhoneNumber(phoneNumber, mav)) {
			return;
		}
		phoneBookRepository.update(name, phoneNumber, id);
	}

}
