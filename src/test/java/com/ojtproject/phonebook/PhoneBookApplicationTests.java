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
		assertThat(Validation.validateNameSearch("あああああいいいいいうううううえええええおおおおおか", mav), is(false));
		assertThat(Validation.validateNameSearch("あああああいいいいいうううううえええええおおおおお", mav), is(true));

	}

	@Test
	public void validateNameTest() {
		ModelAndView mav = new ModelAndView();
		assertThat(Validation.validateName("", mav), is(false));
		assertThat(Validation.validateName("12345678901234567890", mav), is(false));
		assertThat(Validation.validateName("123456789012345678901", mav), is(false));
		assertThat(Validation.validateName("あああああいいいいいうううううえええええおおおおおか", mav), is(false));
		assertThat(Validation.validateName("あああああいいいいいうううううえええええおおおおお", mav), is(true));

	}

	@Test
	public void fullWidth() {
		assertThat(Validation.fullWidth("12345678901234567890"), is(false));
		assertThat(Validation.fullWidth("あああああいいいいいうううううえええええおおおおお"), is(true));

	}

	@Test
	public void validatePhoneNumberTest() {
		ModelAndView mav = new ModelAndView();
		assertThat(Validation.validatePhoneNumber("", mav), is(false));
		assertThat(Validation.validatePhoneNumber("あああああいいいい", mav), is(false));
		assertThat(Validation.validatePhoneNumber("あああああいいいいい", mav), is(false));
		assertThat(Validation.validatePhoneNumber("あああああいいいいいう", mav), is(false));
		assertThat(Validation.validatePhoneNumber("あああああいいいいいうう", mav), is(false));
		assertThat(Validation.validatePhoneNumber("123456789", mav), is(false));
		assertThat(Validation.validatePhoneNumber("1234567890", mav), is(true));
		assertThat(Validation.validatePhoneNumber("12345678901", mav), is(true));
		assertThat(Validation.validatePhoneNumber("123456789012", mav), is(false));

	}

	@Test
	public void halfWidth() {
		assertThat(Validation.halfWidth("あああああいいいいい"), is(false));
		assertThat(Validation.halfWidth("あああああいいいいいう"), is(false));
		assertThat(Validation.halfWidth("1234567890"), is(true));
		assertThat(Validation.halfWidth("12345678901"), is(true));

	}

}
