package scoremanager.main;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject2;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao2;
import tool.Action;

public class TestListSubjectExecute2Action extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		TestListSubjectDao2 TLSubDao2 = new TestListSubjectDao2();//成績Dao
		SubjectDao SubDao = new SubjectDao(); //科目Dao

		int entYear = 0;//入学年度
		String entYearStr="";// 入力された入学年度
		String classNum = "";//クラス番号
		String subjectCd = "";//科目

		Map<Integer,Integer> points = new HashMap<>();// 成績（何回目、点数）

		Map<String, String> errors = new HashMap<>();// エラーメッセージ
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		List<Integer> entYearSet = new ArrayList<>();//入学年度のリストを初期化
		Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得

		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得

		List<TestListSubject2> TLSubList = new ArrayList<>();//空の成績リスト（dao.filterの返却先）

		//リクエストパラメータ―の取得 2
		entYearStr = req.getParameter("f1");//入学年度
		classNum = req.getParameter("f2");//クラス番号
		subjectCd = req.getParameter("f3");

		System.out.print(entYearStr);
		System.out.print(classNum);
		System.out.print(subjectCd);

		req.setAttribute("f1", entYearStr);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectCd);


		//DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> clist = cNumDao.filter(teacher.getSchool());

		// ログインユーザーの学校コードをもとに科目の一覧を取得
		List<Subject> slist = SubDao.filter(teacher.getSchool());



		//ビジネスロジック 4
		if (entYearStr != null) {
			// 数値に変換
			entYear = Integer.parseInt(entYearStr);
		}

		// 10年前から1年後まで年をリストに追加
		for (int i = year - 10; i < year + 10; i++) {
			entYearSet.add(i);
		}



		//DBへデータ保存 5
		//なし
		//レスポンス値をセット 6
		if (entYear == 0 || classNum.equals("0") || subjectCd.equals("0")) {// 入学年度が選択されていない場合
			errors.put("select", "入学年度、クラス、科目のすべてを選択してください");

		}else{

			//DBからデータ取得 3
			School school = teacher.getSchool();
			Subject sub = SubDao.get(subjectCd,school);

			TLSubList = TLSubDao2.filter(school, entYear, classNum, sub);
			//DBから成績表示に必要なデータをリスト形式で取得

			req.setAttribute("test_list_subs", TLSubList);

		}

		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("clist", clist);
		req.setAttribute("slist", slist);

		System.out.print(errors.get("select"));
		req.setAttribute("errors", errors);
		//JSPへフォワード 7
		req.getRequestDispatcher("test_list_student2.jsp").forward(req, res);
	}
}
