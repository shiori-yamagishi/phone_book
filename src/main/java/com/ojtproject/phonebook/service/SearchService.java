package com.ojtproject.phonebook.service;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
		String address = input.getAddress();

		List<SearchResultForm> searchList = new ArrayList<>();
		if (keyword == null | ("".equals(keyword) & "".equals(address))) { //初期処理画面の初期表示は全件表示、名前検索かつ住所選択が空欄だった場合も全件表示
			phoneBookList = phoneBookRepository.findAll();

		} else if (!"".equals(keyword) & "".equals(address)) { //名前検索が入力されているが、住所検索が空欄の場合
			if (!Validation.validateNameSearch(keyword, mav)) { //入力チェック処理
				phoneBookList = phoneBookRepository.findAll();//入力チェックに引っかかった場合は全件表示させる

			} else {
				phoneBookList = phoneBookRepository.findByKeyword(keyword);
			}

		} else if ("".equals(keyword) & !"".equals(address)) { //名前検索は未入力だが、住所検索は選択されている場合
			phoneBookList = phoneBookRepository.findByAddress(address);

		} else if (!"".equals(keyword) & !"".equals(address)) { //名前も入力されていて住所検索も選択されている場合
			if (!Validation.validateNameSearch(keyword, mav)) { //入力チェック処理
				phoneBookList = phoneBookRepository.findAll();//入力チェックに引っかかった場合は全件表示させる

			} else {
				phoneBookList = phoneBookRepository.findByKeywordAndAddress(keyword, address);
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
				sf.setAddress(entity.getAddress());
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
				sf.setAddress(entity.getAddress());
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

		mav.addObject("searchList", (List<SearchResultForm>) session.getAttribute("listPage" + previousPage));
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

	/*csv出力処理*/
	public ModelAndView exportCsv(ModelAndView mav) {
		List<PhoneBook> phoneBookList = (List<PhoneBook>) session.getAttribute("phoneBookList");

		//住所のみを格納したリスト
		List<String> addressList = new ArrayList<String>() {
			{
				for (int i = 0; i < phoneBookList.size(); i++) {
					add(phoneBookList.get(i).getAddress());
				}
			}
		};

		//重複している住所を除く
		ArrayList<String> notDuplicateAddress = new ArrayList<String>(new HashSet<>(addressList));

		Map<String, ArrayList<PhoneBook>> export = new HashMap<>();

		for (int i = 0; i < notDuplicateAddress.size(); i++) { //住所ごとに集約されたデータを格納
			ArrayList<PhoneBook> exportList = new ArrayList<>();

			for (int j = 0; j < phoneBookList.size(); j++) {
				if (notDuplicateAddress.get(i).equals(phoneBookList.get(j).getAddress())) {
					exportList.add(phoneBookList.get(j));
				}
			}

			export.put(notDuplicateAddress.get(i), exportList); //Mapに格納
		}

		try (PrintWriter pw = new PrintWriter(
				new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream("C:\\Users\\shior\\Downloads\\電話帳アプリOJT\\ojt.csv", false),
						"shift-JIS")));) { //csvファイルの作成

			pw.print("都道府県");
			pw.print(",");
			pw.print("人数");
			pw.println();

			for (Map.Entry<String, ArrayList<PhoneBook>> z : export.entrySet()) {
				pw.print(z.getKey());
				pw.print(",");
				pw.print(z.getValue().size());
				pw.println();
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			//クローズ処理不要
		}

		return mav;

	}

}
