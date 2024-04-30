package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao2;
import tool.Action;

public class TestListSubjectExecuteAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();   // セッション
		Teacher teacher = (Teacher) session.getAttribute("user");   // ログインユーザーを取得
		TestListSubjectDao2 TLSubDao2 = new TestListSubjectDao2();   // 成績Daoを初期化
		SubjectDao SubDao = new SubjectDao();  // 科目Daoを初期化

		int entYear;   // 入学年度
		String classNum = "";   // クラス番号
		String subjectCd = "";   // 科目

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

		//DBからデータ取得 3
		//なし

		//ビジネスロジック 4
		//なし

		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		//なし

		//JSPへフォワード 7
		req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
	}
}
