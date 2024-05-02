package scoremanager.main;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		HttpSession session = req.getSession(true);// セッションを取得
		SubjectDao subDao = new SubjectDao();// 科目コードDaoを初期化
		Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得


		//リクエストパラメータ―の取得 2
		//なし

		//DBからデータ取得 3
		List<Subject> list = subDao.filter(teacher.getSchool());// ログインユーザーの学校コードをもとに科目コードの一覧を取得

		//ビジネスロジック 4


		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
//		req.setAttribute("class_num_set", list);//科目コードのlistをセット
//		req.setAttribute("ent_year_set", entYearSet);//入学年度のlistをセット

		//JSPへフォワード 7
		req.getRequestDispatcher("subject_create.jsp").forward(req, res);
	}
}


