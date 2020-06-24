package com.ojtproject.phonebook.utility;

import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import com.ojtproject.phonebook.entity.PhoneBook;

/**
 *入力された名前と電話番号にエラーがないかをチェックするクラス
 */
public class InputCheck {
	/**
	 * 入力された名前と電話番号をチェックするメソッド
	 * @param inputName 入力された名前
	 * @param inputPhoneNumber 入力された電話番号
	 * @param mav
	 * @param phoneBookList
	 * @return エラーありならtrue、なしならfalse
	 * */
	public static boolean phoneBookCheck(String inputName, String inputPhoneNumber, ModelAndView mav,
			List<PhoneBook> phoneBookList) {
		return true;
	}
}