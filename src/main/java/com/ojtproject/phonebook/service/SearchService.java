package com.ojtproject.phonebook.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.ojtproject.phonebook.dao.PhoneBookRepository;
import com.ojtproject.phonebook.entity.PhoneBook;
import com.ojtproject.phonebook.form.SearchForm;
import com.ojtproject.phonebook.form.SearchResultForm;

@Service
public class SearchService {

	@Autowired
	private HttpSession session;
	@Autowired
	private PhoneBookRepository phoneBookRepository;

	/**入力された名前と電話帳リストにある名前を比較して合致するものをListに格納するメソッド*/
	public void execute(SearchForm input, ModelAndView mav) {
		List<PhoneBook> phoneBookList = null;
		String keyword = input.getKeyword();//入力された名前を取得

		List<SearchResultForm> searchList = new ArrayList<>();
		if (keyword == null) {
			phoneBookList = phoneBookRepository.findAll();
		} else if (!keyword.equals("")) {
			/*phoneBookList = phoneBookRepository.findResult(keyword);*/
		}
		session.setAttribute("phoneBookList", phoneBookList);

		for (int i = 0; i < phoneBookList.size(); i++) {
			PhoneBook entity = phoneBookList.get(i);
			SearchResultForm sf = new SearchResultForm();
			sf.setName(entity.getName());
			sf.setPhoneNumber(entity.getPhoneNumber());
			searchList.add(sf);
			//System.out.println("null");
		}
		mav.addObject("searchList", searchList);
		mav.setViewName("search");
		SearchService.searchMsg(searchList, keyword, mav);
	}

	private static void searchMsg(List<SearchResultForm> searchList, String inputName, ModelAndView mav) {
		/*if (inputName == null) {return;}
		if (inputName.equals("")) {
			mav.addObject("msg", Message.SEARCH_EMPTY);
		} else if (searchList.size() == 0) {
			mav.addObject("msg", Message.SEARCH_NOT_HIT);
		} else {
			mav.addObject("msg", searchList.size() + Message.SEARCH_HIT_COUNT);
		}*/
	}

}
