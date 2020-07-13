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

	public static final String NAME_MAX_MSG = "登録者名は20文字以内で入力してください";

	public static final String PHONE_NUMBER_HALF_WIDTH = "電話番号は半角数字で入力してください";

	public static final String PHONE_NUMBER_MSG = "電話番号の入力可能桁数は10桁か11桁です";

	public static final int NAME_MAX_LIMIT = 20;

	public static final int PHONE_NUMBER_MIN_LIMIT = 10;

	public static final int PHONE_NUMBER_MAX_LIMIT = 11;

	/*トップページの検索入力欄の入力チェック*/
	public static boolean validateNameSearch(String name, ModelAndView mav) {
		boolean isCorrectNameSearch = true;

		if (!fullWidth(name)) {
			isCorrectNameSearch = false;
			mav.addObject("messageSearch", NAME_FULL_WIDTH);

		} else if (isNumber(name)) {
			isCorrectNameSearch = false;
			mav.addObject("messageSearch", NAME_FULL_WIDTH);

		} else if (name.length() > NAME_MAX_LIMIT) {
			isCorrectNameSearch = false;
			mav.addObject("messageSearch", NAME_MAX_MSG);
		}

		return isCorrectNameSearch;
	}

	/*空欄チェック*/
	public static boolean blank(String inputedName, String inputedPhoneNumber, ModelAndView mav) {
		boolean isBlank = true;

		if (StringUtils.isEmpty(inputedName)) {
			if ("".equals(inputedPhoneNumber)) {
				isBlank = false;
				mav.addObject("messageBlank", BLANK);
			}
		}

		return isBlank;
	}

	/*追加画面、編集画面の登録者名入力チェック*/
	public static boolean validateName(String inputedName, ModelAndView mav) {
		boolean isCorrectInputedName = true;

		if (StringUtils.isEmpty(inputedName)) {
			isCorrectInputedName = false;
			mav.addObject("messageNameCheck", BLANK);

		} else if (!fullWidth(inputedName)) {
			isCorrectInputedName = false;
			mav.addObject("messageNameCheck", NAME_FULL_WIDTH);

		} else if (isNumber(inputedName)) {//全角数字判定(001_8,002_7,003_7)
			isCorrectInputedName = false;
			mav.addObject("messageNameCheck", NAME_FULL_WIDTH);

		} else if (inputedName.length() > NAME_MAX_LIMIT) {
			isCorrectInputedName = false;
			mav.addObject("messageNameCheck", NAME_MAX_MSG);
		}

		return isCorrectInputedName;
	}

	public static boolean isNumber(String inputedName) {
		return Pattern.matches("^[０-９]*$", inputedName);
	}

	public static boolean fullWidth(String inputedName) {
		return Pattern.matches("^[^ -~｡-ﾟ]+$", inputedName);
	}

	/*追加画面、編集画面の電話番号入力チェック*/
	public static boolean validatePhoneNumber(String inputedPhoneNumber, ModelAndView mav) {
		boolean isCorrectInputedPhoneNumber = true;

		if ("".equals(inputedPhoneNumber)) {
			isCorrectInputedPhoneNumber = false;
			mav.addObject("messagePhoneNumberCheck", BLANK);

		} else if (!halfWidth(inputedPhoneNumber)) {
			isCorrectInputedPhoneNumber = false;
			mav.addObject("messagePhoneNumberCheck", PHONE_NUMBER_HALF_WIDTH);

		} else if (inputedPhoneNumber.length() < PHONE_NUMBER_MIN_LIMIT) {
			isCorrectInputedPhoneNumber = false;
			mav.addObject("messagePhoneNumberCheck", PHONE_NUMBER_MSG);

		} else if (inputedPhoneNumber.length() > PHONE_NUMBER_MAX_LIMIT) {
			isCorrectInputedPhoneNumber = false;
			mav.addObject("messagePhoneNumberCheck", PHONE_NUMBER_MSG);
		}

		return isCorrectInputedPhoneNumber;

	}

	public static boolean halfWidth(String phoneNumber) {
		return Pattern.matches("^[0-9]*$", phoneNumber);
	}

}
