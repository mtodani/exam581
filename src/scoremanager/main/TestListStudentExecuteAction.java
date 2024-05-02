package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class TestListStudentExecuteAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1


		//リクエストパラメータ―の取得 2


		//DBからデータ取得 3


		//ビジネスロジック 4


		//DBへデータ保存 5
		//なし
		//レスポンス値をセット 6
		//なし
		//JSPへフォワード 7
		req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
	}
}
