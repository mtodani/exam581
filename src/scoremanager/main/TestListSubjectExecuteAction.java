package scoremanager.main;

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
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.Action;

public class TestListSubjectExecuteAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();   // セッション
		Teacher teacher = (Teacher) session.getAttribute("user");   // ログインユーザーを取得
		TestListSubjectDao TLSubDao = new TestListSubjectDao();   // 成績Daoを初期化
		SubjectDao SubDao = new SubjectDao();  // 科目Daoを初期化

		int entYear;   // 入学年度
		String classNum = "";   // クラス番号
		String subjectCd = "";   // 科目
		Map<Integer,Integer> points = new HashMap<>();   // 成績
		List<TestListSubject> TLSubList = new ArrayList<>();   //空の成績リスト

		List<Integer> entYearSet = new ArrayList<>();   // 入学年度のリストを初期化
		ClassNumDao cNumDao = new ClassNumDao();   // クラス番号Daoを初期化
		Map<String, String> errors = new HashMap<>();   // エラーメッセージ

		//リクエストパラメータ―の取得 2
		entYear = Integer.parseInt(req.getParameter("f1"));   // 入学年度
		classNum = req.getParameter("f2");   // クラス番号
		subjectCd = req.getParameter("f3");   // 科目

		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectCd);


		//ビジネスロジック 4
		// 入学年度が選択されていない場合
		if (entYear == 0 || classNum == "0" || subjectCd == "0") {
			errors.put("select", "入学年度、クラス、科目のすべてを選択してください");
		} else {

			//DBからデータ取得 3
			School school = teacher.getSchool();
			Subject sub = SubDao.get(subjectCd,school);
			TLSubList = TLSubDao.filter(school, entYear, classNum, sub);
		}

		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		//なし

		//JSPへフォワード 7
		req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
	}
}
