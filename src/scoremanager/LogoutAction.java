package scoremanager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class LogoutAction extends Action{


	public void execute(
			HttpServletRequest request, HttpServletResponse response
		) throws Exception {

			//セッション情報をゲット
			HttpSession session=request.getSession();

			//ログインしていた場合はセッションからユーザーを取り除く
			if (session.getAttribute("user")!=null) {
				session.removeAttribute("user");
				//そして、logout画面にフォワード
				request.getRequestDispatcher("logout.jsp").forward(request, response);

			}

		}

}
