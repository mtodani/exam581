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

public class SubjectRevivalExecuteAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		SubjectDao subDao = new SubjectDao();// 科目Dao
		HttpSession session = req.getSession();//セッション
		boolean subject_now = true;//科目フラグ
		Teacher teacher = (Teacher)session.getAttribute("user");// ログインユーザーを取得
		Map<String, String> errors = new HashMap<>(); // エラーメッセージ


		//リクエストパラメータ―の取得 2
		String[] s_Rvival = req.getParameterValues("s_Rvival");

//		//ビジネスロジック 4
//		//DBへデータ保存 5
//		//条件で4～5が分岐
		if (s_Rvival == null || s_Rvival.length == 0) {
            errors.put("s_Rvival", "少なくとも一つの科目を選択してください。");

		}else{
		        // 選択された科目のコードを処理する
		        for (String s_Str : s_Rvival) {
		            // 各選択肢に対する処理を実行
		        	Subject subject = subDao.get(s_Str,teacher.getSchool());// 科目コードと学校コードから科目インスタンスを取得
		        	subject.setSubject_now(subject_now);// インスタンスに値をセット

		        	// 科目を復活
		        	subDao.delete(subject);
		        	System.out.println(s_Str);
		        }
		 }

		//エラーがあったかどうかで手順6~7の内容が分岐
		//レスポンス値をセット 6
		//JSPへフォワード 7
		if(!errors.isEmpty()){
	        req.setAttribute("errors", errors);
	        req.setAttribute("subjects", subDao.deletefilter(teacher.getSchool())); // ログインユーザーの学校コードをもとに科目一覧を取得
	        req.getRequestDispatcher("subject_revival.jsp").forward(req, res);// JSPへフォワード
		}
		// JSPへフォワード
		req.getRequestDispatcher("subject_revival_done.jsp").forward(req, res);

    }
}


