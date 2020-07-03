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

	static int recordCount = 0;

	static final String DELETE_MESSAGE = "正常に削除されました";

	/**入力された名前と電話帳リストにある名前を比較して合致するものをListに格納するメソッド*/
	public void search(SearchForm input, ModelAndView mav) {
		List<PhoneBook> phoneBookList = new ArrayList<>();
		String keyword = input.getKeyword();//入力された名前を取得

		List<SearchResultForm> searchList = new ArrayList<>();
		if (keyword == null) {
			phoneBookList = phoneBookRepository.findAll();
		} else if ("".equals(keyword)) {
			phoneBookList = phoneBookRepository.findAll();
		} else {
			if (!Validation.validateNameSearch(keyword, mav)) { //入力チェック処理
				return;
			} else {
				phoneBookList = phoneBookRepository.findResult(keyword);
			}
		}

		session.setAttribute("phoneBookList", phoneBookList);

		for (int i = 0; i < phoneBookList.size(); i++) {
			PhoneBook entity = phoneBookList.get(i);
			SearchResultForm sf = new SearchResultForm();
			sf.setId(entity.getId());
			sf.setName(entity.getName());
			sf.setPhoneNumber(entity.getPhoneNumber());
			searchList.add(sf);
		}
		mav.addObject("searchList", searchList);
		mav.setViewName("search");
	}

	/*int pageNum = 0;

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
	session.setAttribute("listPage" + pageNum, searchList);
	mav.setViewName("search");

	}

	//次ページへ遷移する処理
	public void toNextPage(int pageNum, ModelAndView mav) {
	List<PhoneBook> phoneBookList;
	phoneBookList = (List<PhoneBook>) session.getAttribute("phoneBookList");
	int firstDataNum = 15 * (pageNum - 1);
	int count = firstDataNum + 15;
	int recordCount = pageNum * 15 - 15;

	if (phoneBookList.size() - (15 * pageNum) > 0 && phoneBookList != null) {
		pageNum++;
	}

	List<SearchResultForm> searchList = new ArrayList<>();

	if (phoneBookList != null) {
		for (int j = firstDataNum; j < count; j++) {
			if (j == phoneBookList.size()) {
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

	mav.addObject("searchList", searchList);
	mav.addObject("pageNum", pageNum);
	session.setAttribute("listPage" + pageNum, searchList);
	mav.setViewName("search");

	}

	//前ページへ遷移する処理
	public void toPreviousPage(int pageNum, ModelAndView mav) {
	int previousPage = pageNum - 1;

	if (previousPage < 1) {
		previousPage = 1;
	}

	mav.addObject("searchList", (List<SearchResultForm>) session.getAttribute("listPage" + previousPage));
	mav.addObject("pageNum", previousPage);
	mav.setViewName("search");

	}*/

	//削除処理

	public void delete(ModelAndView mav, @RequestParam(value = "id", required = true) int id) {

		phoneBookRepository.delete(id);
		mav.addObject("deleteMsg", DELETE_MESSAGE);

	}

}
