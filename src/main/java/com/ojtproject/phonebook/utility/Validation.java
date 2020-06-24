package com.ojtproject.phonebook.utility;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

public class Validation {
	/**
	 * @Param inputedName
	 * @Param inputedPhoneNumber
	 * @Param mav
	 * @Param phoneBookList
	 * @return
	 */

	public static final String BLANK = "登録者と電話番号両方を入力してください";

	public static final String NAME_FULL_WIDTH = "登録者名は全角文字で入力してください";

	//public static final String NAME_MAX_MSG = "登録者名は20文字以内で入力してください";

	public static final String PHONE_NUMBER_HALF_WIDTH = "電話番号は半角文字で入力してください";

	public static final String PHONE_NUMBER_MSG = "電話番号は10桁か11桁で入力してください";

	public static final int NAME_MAX_LIMIT = 20;

	public static final int PHONE_NUMBER_MIN_LIMIT = 10;

	public static final int PHONE_NUMBER_MAX_LIMIT = 11;

	public static boolean validateNameSearch(String name, ModelAndView mav) {
		boolean result = true;
		if (!FullWidth(name)) {
			result = false;
			mav.addObject("messageSearch", NAME_FULL_WIDTH);
		}
		return result;
	}

	public static boolean validateName(String inputedName, ModelAndView mav) {
		boolean result = true;
		if (StringUtils.isEmpty(inputedName)) {
			result = false;
			mav.addObject("messageA", BLANK);
		} else if (!FullWidth(inputedName)) {
			result = false;
			mav.addObject("messageA", NAME_FULL_WIDTH);
		}

		return result;
	}

	private static boolean FullWidth(String inputedName) {
		return Pattern.matches("^[^!-~｡-ﾟ]*$", inputedName);
	}

	public static boolean validatePhoneNumber(String inputedPhoneNumber, ModelAndView mav) {
		boolean result = true;
		if ("".equals(inputedPhoneNumber)) {
			result = false;
			mav.addObject("messageB", BLANK);
		} else if (!HalfWidth(inputedPhoneNumber)) {
			result = false;
			mav.addObject("messageB", PHONE_NUMBER_HALF_WIDTH);
		} else if (inputedPhoneNumber.length() < PHONE_NUMBER_MIN_LIMIT) {
			result = false;
			mav.addObject("messageB", PHONE_NUMBER_MSG);
		} else if (inputedPhoneNumber.length() > PHONE_NUMBER_MAX_LIMIT) {
			result = false;
			mav.addObject("messageB", PHONE_NUMBER_MSG);
		}

		return result;

	}

	private static boolean HalfWidth(String phoneNumber) {
		return Pattern.matches("^[0-9]*$", phoneNumber);
	}

}
