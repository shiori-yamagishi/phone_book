package com.ojtproject.phonebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ojtproject.phonebook.form.SearchForm;
import com.ojtproject.phonebook.service.SearchService;

@Controller
public class PhoneBookController {
	@Autowired
	private SearchService search;

	/**トップページを表示*/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index(ModelAndView mav) {
		return search(new SearchForm(), mav);

	}

	/**検索ロジックを呼び出して検索ページへ遷移*/
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(SearchForm input, ModelAndView mav) {
		search.execute(input, mav);
		return mav;
	}

}
