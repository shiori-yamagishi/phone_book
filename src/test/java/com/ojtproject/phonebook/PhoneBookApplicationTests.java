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

	/*private SearchResultForm form;
	@BeforeEach
	public void beforeEach() {
		form = new SearchResultForm();
		form.setName("aaa");
	}*/

	@Test
	public void testValidateNameSearch() {
		ModelAndView mav = new ModelAndView();
		assertThat(Validation.validateNameSearch("", mav), is(false));
		assertThat(Validation.validateNameSearch("あああああいいいいいうううううえええええおおおおお", mav), is(true));
		assertThat(Validation.validateNameSearch("あああああいいいいいうううううえええええおおおおおか", mav), is(false));
		assertThat(Validation.validateNameSearch("12345678901234567890", mav), is(false));
		assertThat(Validation.validateNameSearch("123456789012345678901", mav), is(false));

	}

	@Test
	public void validateNameTest() {

	}

	@Test
	public void fullWidthTest() {

	}

	@Test
	public void validatePhoneNumberTest() {

	}

	@Test
	public void halfWidthTest() {

	}

}
