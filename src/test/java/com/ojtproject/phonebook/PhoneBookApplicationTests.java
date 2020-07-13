package com.ojtproject.phonebook;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;

import com.ojtproject.phonebook.utility.Validation;

@SpringBootTest
class PhoneBookApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void ValidationTest() {
		Validation validation = new Validation();
	}

	@Test
	public void ValidateNameSearchTest() {
		ModelAndView mav = new ModelAndView();
		assertThat(Validation.validateNameSearch("12345678901234567890", mav), is(false));
		assertThat(Validation.validateNameSearch("123456789012345678901", mav), is(false));
		assertThat(Validation.validateNameSearch("１２３４５６７８９０１２３４５６７８９０", mav), is(false));
		assertThat(Validation.validateNameSearch("あいうえおかきくけこさしすせそたちつてとな", mav), is(false));
		assertThat(Validation.validateNameSearch("あいうえおかきくけこさしすせそたちつてと", mav), is(true));

	}

	@Test
	public void blankTest() {
		ModelAndView mav = new ModelAndView();
		assertThat(Validation.blank("", "", mav), is(false));
		assertThat(Validation.blank("あいうえおかきくけこさしすせそたちつてと", "", mav), is(true));
		assertThat(Validation.blank("", "1234567890", mav), is(true));
		assertThat(Validation.blank("", "12345678901", mav), is(true));
	}

	@Test
	public void validateNameTest() {
		ModelAndView mav = new ModelAndView();
		assertThat(Validation.validateName("", mav), is(false));
		assertThat(Validation.validateName("12345678901234567890", mav), is(false));
		assertThat(Validation.validateName("123456789012345678901", mav), is(false));
		assertThat(Validation.validateName("１２３４５６７８９０１２３４５６７８９０", mav), is(false));
		assertThat(Validation.validateName("あいうえおかきくけこさしすせそたちつてとな", mav), is(false));
		assertThat(Validation.validateName("あいうえおかきくけこさしすせそたちつてと", mav), is(true));

	}

	@Test
	public void isNumberTest() {
		assertThat(Validation.isNumber("１２３４５６７８９０１２３４５６７８９０"), is(true));
		assertThat(Validation.isNumber("あいうえおかきくけこさしすせそたちつてと"), is(false));
	}

	@Test
	public void fullWidthTest() {
		assertThat(Validation.fullWidth("12345678901234567890"), is(false));
		assertThat(Validation.fullWidth("あいうえおかきくけこさしすせそたちつてと"), is(true));

	}

	@Test
	public void validatePhoneNumberTest() {
		ModelAndView mav = new ModelAndView();
		assertThat(Validation.validatePhoneNumber("", mav), is(false));
		assertThat(Validation.validatePhoneNumber("あいうえおかきくけ", mav), is(false));
		assertThat(Validation.validatePhoneNumber("あいうえおかきくけこ", mav), is(false));
		assertThat(Validation.validatePhoneNumber("あいうえおかきくけこさ", mav), is(false));
		assertThat(Validation.validatePhoneNumber("あいうえおかきくけこさし", mav), is(false));
		assertThat(Validation.validatePhoneNumber("123456789", mav), is(false));
		assertThat(Validation.validatePhoneNumber("1234567890", mav), is(true));
		assertThat(Validation.validatePhoneNumber("12345678901", mav), is(true));
		assertThat(Validation.validatePhoneNumber("123456789012", mav), is(false));

	}

	@Test
	public void halfWidthTest() {
		assertThat(Validation.halfWidth("あいうえおかきくけこ"), is(false));
		assertThat(Validation.halfWidth("あいうえおかきくけこさ"), is(false));
		assertThat(Validation.halfWidth("1234567890"), is(true));
		assertThat(Validation.halfWidth("12345678901"), is(true));

	}

}
