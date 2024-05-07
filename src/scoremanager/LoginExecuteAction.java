package scoremanager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.Action;

public class LoginExecuteAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//Sessionを有効にする
		HttpSession session = req.getSession(true);
		//ローカル変数の宣言 1
		String url = "";

		Teacher teacher = new Teacher();

		//リクエストパラメータ―の取得 2
		String id = req.getParameter("id");
		String password = req.getParameter("password");


		//DBからデータ取得 3
		TeacherDao teacherDao = new TeacherDao();

		try {
			teacher = teacherDao.login(id, password);

			// 認証成功した場合リダイレクト
			if (teacher != null) {
				//セッションに"user"という変数名で値はTeacher型
	            session.setAttribute("user", teacher);
	            //menuページにリダイレクト
	    		url = "main/Menu.action";
	    		res.sendRedirect(url);
	        } else {
	        	//認証失敗の場合
	            req.setAttribute("errorMessage", "ログインに失敗しました。IDまたはパスワードが正しくありません。");
	            // エラーメッセージとともにログインページに戻る
	            req.getRequestDispatcher("Login.action").forward(req, res);
	        }

		 } catch (Exception e) {
	            e.printStackTrace(); // 例外情報の出力
	            req.getRequestDispatcher("/error.jsp").forward(req, res); // エラーページへリダイレクト
        }


	}

}
