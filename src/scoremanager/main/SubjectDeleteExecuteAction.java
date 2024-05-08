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

public class SubjectDeleteExecuteAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		SubjectDao subDao = new SubjectDao();// 科目Dao
		HttpSession session = req.getSession();//セッション
		boolean subject_now = false;//科目フラグ
		Teacher teacher = (Teacher)session.getAttribute("user");// ログインユーザーを取得
		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		//リクエストパラメータ―の取得 2
		String subject_cd = req.getParameter("subject_cd");
		String subject_name = req.getParameter("subject_name");
		String nowStr = req.getParameter("subject_now");

		// 科目フラグが存在した場合
				if (nowStr != null) {
					// 科目フラグを消す
					subject_now = false;
				}
		System.out.println(subject_cd);
		System.out.println(subject_name);
		System.out.println(subject_now);


		//DBからデータ取得 3
		Subject subject = subDao.get(subject_cd,teacher.getSchool());// 科目コードと学校コードから科目インスタンスを取得
		List<Subject> list = subDao.filter(teacher.getSchool());//ログインユーザーの学校コードをもとにクラス番号の一覧を取得

		//ビジネスロジック 4
		//DBへデータ保存 5
		//条件で4～5が分岐
		if (subject != null) {
			// 科目が存在していた場合
			// インスタンスに値をセット
			subject.setSubject_now(subject_now);

			// 科目を保存
			subDao.delete(subject);
		} else {
			errors.put("subject_cd", "科目が存在していません");
		}

		//エラーがあったかどうかで手順6~7の内容が分岐
		//レスポンス値をセット 6
		//JSPへフォワード 7
//		req.setAttribute("class_num_set", list);

		if(!errors.isEmpty()){//エラーがあった場合、更新画面へ戻る
			// リクエスト属性をセット
			req.setAttribute("errors", errors);
			req.setAttribute("subject_cd", subject_cd);
			req.setAttribute("subject_name", subject_name);
			req.getRequestDispatcher("subject_delete.jsp").forward(req, res);
			return;
		}

		req.getRequestDispatcher("subject_delete_done.jsp").forward(req, res);
	}
}
