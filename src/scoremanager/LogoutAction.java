package scoremanager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class LogoutAction extends Action{


	public void execute(
			HttpServletRequest request, HttpServletResponse response
		) throws Exception {

			HttpSession session=request.getSession();

			//ログインしていた場合はセッションからユーザーを取り除く
			if (session.getAttribute("user")!=null) {
				session.removeAttribute("user");

				request.getRequestDispatcher("logout.jsp").forward(request, response);

			}

			//エラーの場合の対応は未実装
//			return "logout-error.jsp";
		}

}
