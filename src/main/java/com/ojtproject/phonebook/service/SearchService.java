package com.ojtproject.phonebook.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ojtproject.phonebook.dao.PhoneBookRepository;
import com.ojtproject.phonebook.entity.PhoneBook;
import com.ojtproject.phonebook.form.SearchForm;
import com.ojtproject.phonebook.form.SearchResultForm;
import com.ojtproject.phonebook.utility.Validation;

@Service
public class SearchService {

	@Autowired
	private HttpSession session;
	@Autowired
	private PhoneBookRepository phoneBookRepository;

	/**入力された名前と電話帳リストにある名前を比較して合致するものをListに格納するメソッド*/
	public void search(SearchForm input, ModelAndView mav) {
		List<PhoneBook> phoneBookList = new ArrayList<>();
		String keyword = input.getKeyword();//入力された名前を取得

		List<SearchResultForm> searchList = new ArrayList<>();
		if (keyword == null) {
			phoneBookList = phoneBookRepository.findAll();
		} else if (keyword.equals("")) {
			phoneBookList = phoneBookRepository.findAll();
		} else if (!keyword.equals("")) {
			if (!Validation.validateNameSearch(keyword, mav)) {    //入力チェック処理
				return;
			} else {
				phoneBookList = phoneBookRepository.findResult(keyword);
			}
		}

		session.setAttribute("phoneBookList", phoneBookList);

		int recordCount = 0;
		int pageNum = 0;

		if (phoneBookList != null && !phoneBookList.isEmpty()) {
			for (int j = 0; j < 15; j++) {
				if (phoneBookList.size() < 15) {
					break;
				}
				recordCount++;
				PhoneBook entity = phoneBookList.get(j);
				SearchResultForm sf = new SearchResultForm();
				sf.setId(entity.getId());
				sf.setName(entity.getName());
				sf.setPhoneNumber(entity.getPhoneNumber());
				searchList.add(sf);
			}
		}

		mav.addObject("searshList", searchList);
		pageNum++;
		mav.addObject("pageNum", pageNum);
		mav.setViewName("search");


		for (int i = 0; i < phoneBookList.size(); i++) {
			PhoneBook entity = phoneBookList.get(i);
			SearchResultForm sf = new SearchResultForm();
			sf.setId(entity.getId());
			sf.setName(entity.getName());
			sf.setPhoneNumber(entity.getPhoneNumber());
			searchList.add(sf);
			//System.out.println("null");
		}
		mav.addObject("searchList", searchList);
		mav.setViewName("search");
		//SearchService.searchMsg(searchList, keyword, mav);
	}


	//次ページへ遷移する処理
	public void toNextPage() {

	}


	//前ページへ遷移する処理
	public void toPreviousPage() {

	}



	//削除処理
	public void delete(ModelAndView mav, @RequestParam(value = "id", required = true) int id) {
		phoneBookRepository.delete(id);
	}

}
