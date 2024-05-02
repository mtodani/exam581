package scoremanager.main;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectListAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー





//		boolean isAttend = false;// 在学フラグ
		List<Subject> subjects = null;// 科目リスト
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得

//		int year = todaysDate.getYear();// 現在の年を取得
		SubjectDao subDao = new SubjectDao();//科目Dao

		Map<String, String> errors = new HashMap<>();// エラーメッセージ



		//DBからデータ取得 3


		System.out.println();

		subjects = subDao.filter(teacher.getSchool());


		// リクエストに学生リストをセット
		req.setAttribute("subjects", subjects);


		//JSPへフォワード 7
		req.getRequestDispatcher("subject_list.jsp").forward(req, res);
	}

}


