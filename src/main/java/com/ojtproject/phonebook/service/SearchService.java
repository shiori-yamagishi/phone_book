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
				phoneBookList = phoneBookRepository.findAll();//入力チェックに引っかかった場合は全件表示させる
				//return;
			} else {
				phoneBookList = phoneBookRepository.findResult(keyword);
			}
		}

		session.setAttribute("phoneBookList", phoneBookList);

		/*ページング処理（001_3,001_4)*/
		int pageNum = 0;

		if (phoneBookList != null && !phoneBookList.isEmpty()) {
			for (int j = 0; j < 15; j++) {
				if (phoneBookList.size() == j) {
					break;
				}
				recordCount++;
				PhoneBook entity = phoneBookList.get(j);
				SearchResultForm sf = new SearchResultForm();
				sf.setResultId(recordCount);
				sf.setId(entity.getId());
				sf.setName(entity.getName());
				sf.setPhoneNumber(entity.getPhoneNumber());
				searchList.add(sf);
			}
		}

		mav.addObject("searchList", searchList);

		if (!phoneBookList.isEmpty()) {
			pageNum++;
		}

		if (phoneBookList.size() <= 15) { //表示するデータがない場合は、ページボタンをそれぞれ非表示にする
			mav.addObject("isNoPage", true);
		} else {
			mav.addObject("isNoPage", false);
		}

		//pageNum++;
		mav.addObject("pageNum", pageNum);
		session.setAttribute("listPage" + pageNum, searchList);
		mav.setViewName("search");

	}

	/*次ページへ遷移する処理*/
	public void toNextPage(int pageNum, ModelAndView mav) {
		List<PhoneBook> phoneBookList;
		phoneBookList = (List<PhoneBook>) session.getAttribute("phoneBookList");

		if (pageNum < 0) {
			pageNum = 0;
		}
		pageNum++;

		int noPage = 0;
		boolean isNoPage = false;

		if (phoneBookList.size() % 15 == 0) {
			noPage = phoneBookList.size() / 15;
		} else {
			noPage = (phoneBookList.size() / 15) + 1;
		}

		List<SearchResultForm> searchList = new ArrayList<>();
		int firstRecordNum = 15 * (pageNum - 1);
		int count = firstRecordNum + 15;
		int recordCount = (pageNum * 15) - 15;

		if (phoneBookList != null) {
			for (int j = firstRecordNum; j < count; j++) {
				if (j == phoneBookList.size()) {
					break;
				}
				recordCount++;
				PhoneBook entity = phoneBookList.get(j);
				SearchResultForm sf = new SearchResultForm();
				sf.setResultId(recordCount);
				sf.setId(entity.getId());
				sf.setName(entity.getName());
				sf.setPhoneNumber(entity.getPhoneNumber());
				searchList.add(sf);
			}
		}

		mav.addObject("searchList", searchList);

		if (noPage == pageNum) {
			isNoPage = true;
		}

		mav.addObject("isNoPage", isNoPage);
		mav.addObject("pageNum", pageNum);
		session.setAttribute("listPage" + pageNum, searchList);
		mav.setViewName("search");

	}

	/*前ページへ遷移する処理*/
	public void toPreviousPage(int pageNum, ModelAndView mav) {
		int previousPage = pageNum - 1;

		/*if (previousPage < 1) {
			previousPage = 0;
		}*/

		mav.addObject("searchList", (List<SearchResultForm>) session.getAttribute("listPage" + previousPage));
		//List<PhoneBook> phoneBookList = (List<PhoneBook>) session.getAttribute("phoneBookList");
		mav.addObject("pageNum", previousPage);
		mav.addObject("isNoPage", false);
		mav.setViewName("search");

	}

	/*削除処理*/
	public void delete(ModelAndView mav, @RequestParam(value = "id", required = true) int id) throws Exception {

		try {
			phoneBookRepository.delete(id);
			mav.addObject("deleteMsg", DELETE_MESSAGE);//削除確認メッセージ表示(001_11)
		} catch (Exception e) {
			mav.addObject("deleteError", "削除に失敗しました");//削除失敗メッセージ表示(001_12)
		}

	}

}
