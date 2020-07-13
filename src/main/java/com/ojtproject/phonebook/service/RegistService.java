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

		if (!Validation.blank(name, phoneNumber, mav)) {//空欄チェック
			registResult = false;
			mav.addObject("name", name);
			mav.addObject("phoneNumber", phoneNumber);
			return registResult;
		}

		if (!Validation.validateName(name, mav) | !Validation.validatePhoneNumber(phoneNumber, mav)) {//登録者名と電話番号の入力チェック（002_9)
			registResult = false;
			mav.addObject("name", name);
			mav.addObject("phoneNumber", phoneNumber);
			return registResult;
		}

		try {
			phoneBookRepository.regist(name, phoneNumber);
			mav.addObject("registMsg", REGIST_MESSAGE);//追加メッセージの表示（002_2)
		} catch (Exception e) {
			mav.addObject("registError", "追加に失敗しました");//追加失敗メッセージの表示（002_3)
			registResult = false;
			return registResult;
		}

		return registResult;

	}

}
