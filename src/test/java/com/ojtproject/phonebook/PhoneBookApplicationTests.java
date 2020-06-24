package com.ojtproject.phonebook;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;

import com.ojtproject.phonebook.utility.Validation;

@SpringBootTest
class PhoneBookApplicationTests {

	@Test
	void contextLoads() {
	}

	/*@BeforeEach
	public void beforeEach() {

	}*/
	@Test
	public void validateNameSearchTest() {
		Validation v = new Validation();
		boolean result = true;
		String name = "aaa";
		ModelAndView mav;
		//boolean test = v.validateNameSearch(name, mav);
		//assertEquals(false, test);

	}

	private void assertEquals(boolean b, boolean test) {
		// TODO 自動生成されたメソッド・スタブ

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
