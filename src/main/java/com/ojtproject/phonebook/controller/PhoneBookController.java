package com.ojtproject.phonebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ojtproject.phonebook.form.RegistForm;
import com.ojtproject.phonebook.form.SearchForm;
import com.ojtproject.phonebook.form.UpdateForm;
import com.ojtproject.phonebook.service.RegistService;
import com.ojtproject.phonebook.service.SearchService;
import com.ojtproject.phonebook.service.UpdateService;

@Controller
public class PhoneBookController {
	@Autowired
	private SearchService search;
	@Autowired
	private RegistService regist;
	@Autowired
	private UpdateService update;

	/**トップページを表示*/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView searchInit(ModelAndView mav) {
		return search(new SearchForm(), mav);
	}

	/**検索ロジックを呼び出して検索ページへ遷移*/
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView search(SearchForm input, ModelAndView mav) {
		search.search(input, mav);
		return mav;
	}

	/**次ページに遷移*/
	@RequestMapping(value = "/searchNextPage", method = RequestMethod.POST)
	public ModelAndView next(@RequestParam(value = "pageNum", required = true) int pageNum, SearchForm input,
			ModelAndView mav) {
		search.toNextPage(pageNum, mav);
		return mav;

	}

	/**前ページに遷移*/
	@RequestMapping(value = "/searchPreviousPage", method = RequestMethod.POST)
	public ModelAndView previous(@RequestParam(value = "pageNum", required = true) int pageNum, SearchForm input,
			ModelAndView mav) {
		search.toPreviousPage(pageNum, mav);
		return mav;

	}

	/**削除処理を行う*/
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView delete(ModelAndView mav, @RequestParam(value = "id", required = true) int id) {

		try {
			search.delete(mav, id);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return searchInit(mav);
	}

	/**追加画面を表示*/
	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	public ModelAndView registInit(ModelAndView mav) {
		regist.registInit(mav);
		return mav;
	}

	/**追加処理を行う*/
	@RequestMapping(value = "/registnew", method = RequestMethod.POST)
	public ModelAndView regist(RegistForm input, ModelAndView mav) {

		boolean notHasErrors = regist.regist(input, mav);
		if (!notHasErrors) {
			return registInit(mav);
		}

		return searchInit(mav);
	}

	/**編集画面を表示*/
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView updateInit(ModelAndView mav, @RequestParam(value = "id", required = true) int id,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "phoneNumber", required = true) String phoneNumber) {
		update.updateInit(mav, id, name, phoneNumber);
		return mav;
	}

	/**編集処理を行う*/
	@RequestMapping(value = "/updatenew", method = RequestMethod.POST)
	public ModelAndView update(UpdateForm input, ModelAndView mav,
			@RequestParam(value = "id", required = true) int id) {

		boolean notHasErrors = update.update(input, mav, id);
		if (!notHasErrors) {
			mav.setViewName("update");
			return mav;
		}

		mav.setViewName("update");
		return searchInit(mav);
	}

}
