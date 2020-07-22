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

	static final String UPDATE_MESSAGE = "編集しました";

	//編集画面初期表示
	public void updateInit(ModelAndView mav, @RequestParam(value = "id", required = true) int id,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "phoneNumber", required = true) String phoneNumber,
			@RequestParam(value = "address", required = true) String address) {

		mav.addObject("id", id);
		mav.addObject("name", name);
		mav.addObject("phoneNumber", phoneNumber);
		mav.addObject("address", address);
		mav.setViewName("update");
	}

	//編集処理、入力チェック処理
	public boolean update(UpdateForm input, ModelAndView mav,
			@RequestParam(value = "id", required = true) int id) {
		String name = input.getName();
		String phoneNumber = input.getPhoneNumber();
		String address = input.getAddress();
		boolean updateResult = true;

		if (!Validation.blank(name, phoneNumber, mav)) {//空欄チェック
			updateResult = false;
			mav.addObject("id", id);
			mav.addObject("name", name);
			mav.addObject("phoneNumber", phoneNumber);
			mav.addObject("address", address);
			return updateResult;
		}

		if (!Validation.validateName(name, mav) | !Validation.validatePhoneNumber(phoneNumber, mav)) {//登録者名と電話番号の入力チェック（003_9)
			updateResult = false;
			mav.addObject("id", id);
			mav.addObject("name", name);
			mav.addObject("phoneNumber", phoneNumber);
			mav.addObject("address", address);
			return updateResult;
		}

		try {
			phoneBookRepository.update(name, phoneNumber, id, address);
			mav.addObject("updateMsg", UPDATE_MESSAGE);//編集メッセージの表示（003_2)
		} catch (Exception e) {
			mav.addObject("updateError", "編集に失敗しました");//編集失敗メッセージの表示（003_3)
			updateResult = false;
			return updateResult;
		}

		return updateResult;

	}

}
