package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import tool.Action;

public class TestListAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得


		//リクエストパラメータ―の取得 2

		//DBからデータ取得 3

		//ビジネスロジック 4

		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6


	}

}
