package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class TestList2Action extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー


		String entYearStr="";// 入力された入学年度
		String classNum = "";//入力されたクラス番号
		String subject="";//入力された科目
		int entYear = 0;// 入学年度

		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		SubjectDao subDao = new SubjectDao();// 科目Daoを初期化

		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得


		//リクエストパラメータ―の取得 2
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subject = req.getParameter("f3");


		//DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> clist = cNumDao.filter(teacher.getSchool());

		// ログインユーザーの学校コードをもとに科目の一覧を取得
		List<Subject> slist = subDao.filter(teacher.getSchool());


		//ビジネスロジック 4
		if (entYearStr != null) {
			// 数値に変換
			entYear = Integer.parseInt(entYearStr);
		}

		// リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		// 10年前から1年後まで年をリストに追加
		for (int i = year - 10; i < year + 10; i++) {
			entYearSet.add(i);
		}

		//DBへデータ保存 5
		//なし
		//レスポンス値をセット 6
		// リクエストに入学年度をセット
		req.setAttribute("f1", entYear);
		// リクエストにクラス番号をセット
		req.setAttribute("f2", classNum);
		// リクエストに学生リストをセット
		req.setAttribute("f3", subject);

		// リクエストにデータをセット
		
		//フィールド
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("clist", clist);
		req.setAttribute("slist", slist);


		//JSPへフォワード 7
		req.getRequestDispatcher("test_list2.jsp").forward(req, res);
	}
}
