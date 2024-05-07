package scoremanager.main;

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

public class SubjectUpdateAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		SubjectDao subDao = new SubjectDao();//科目Dao
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");// ログインユーザーを取得
		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		//リクエストパラメータ―の取得 2
		String subject_cd = req.getParameter("subject_cd");//学番

		//DBからデータ取得 3
		Subject subject = subDao.get(subject_cd,teacher.getSchool());//科目番号から科目インスタンスを取得
		System.out.print(subject_cd);
		List<Subject> list = subDao.filter(teacher.getSchool());//ログインユーザーの学校コードをもとにクラス番号の一覧を取得


		//ビジネスロジック 4
		//DBへデータ保存 5
		//レスポンス値をセット 6
		//条件で手順4~6の内容が分岐
		req.setAttribute("subject", subject);

		if (subject != null) {// 科目が存在していた場合
			req.setAttribute("subject_cd", subject.getSubject_cd());
			req.setAttribute("subject_name", subject.getSubject_name());

//			req.setAttribute("is_attend", student.isAttend());
		} else {// 学生が存在していなかった場合
			errors.put("subject_cd", "学生が存在していません");
			req.setAttribute("errors", errors);
		}
		//JSPへフォワード 7
		req.getRequestDispatcher("subject_update.jsp").forward(req, res);
	}
}


