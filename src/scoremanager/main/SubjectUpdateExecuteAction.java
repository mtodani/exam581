package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		SubjectDao subDao = new SubjectDao();// 科目Dao
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");// ログインユーザーを取得
		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		//リクエストパラメータ―の取得 2
		String subject_cd = req.getParameter("subject_cd");
		String subject_name = req.getParameter("subject_name");
		System.out.print(subject_cd);
		System.out.print(subject_name);


		//DBからデータ取得 3
		Subject subject = subDao.get(subject_cd,teacher.getSchool());// 科目コードと学校コードから科目インスタンスを取得


		//ビジネスロジック 4
		//DBへデータ保存 5
		//条件で4～5が分岐
		if (!subject.isSubject_now()) {
			//科目が論理削除されていた場合
			errors.put("subject_now", "科目が存在していません");

		} else {
			// 科目が存在していた場合
			// インスタンスに値をセット
			subject.setSubject_name(subject_name);
			// 科目を保存
			subDao.save(subject);
		}

		//エラーがあったかどうかで手順6~7の内容が分岐
		//レスポンス値をセット 6
		//JSPへフォワード 7
		if(!errors.isEmpty()){//エラーがあった場合、更新画面へ戻る
			// リクエスト属性をセット
			req.setAttribute("errors", errors);
			req.setAttribute("subject_name", subject_name);
			req.getRequestDispatcher("subject_update.jsp").forward(req, res);
			return;
		}
		// 成功した場合、完了画面へフォワード
		req.getRequestDispatcher("subject_update_done.jsp").forward(req, res);
	}
}


