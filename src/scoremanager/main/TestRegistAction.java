package scoremanager.main;

//成績の登録、消去のページ
//学生登録、消去をもとにするためいったんコピペ

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.TestDao;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー

		String entYearStr="";// 入力された入学年度
		String classNum = "";//入力されたクラス番号
		String isAttendStr="";//入力された在学フラグ 後から使うかも
		String subject="";//入力された科目
		String numStr = "";// 入力された回数
		int num = 0;// 回数
		int entYear = 0;// 入学年度
		boolean isAttend = false;// 在学フラグ 後から使うかも
		List<Test> tests = null;// 学生リスト
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得
		TestDao tDao = new TestDao();//学生Dao
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		Map<String, String> errors = new HashMap<>();// エラーメッセージ

		//リクエストパラメータ―の取得 2


		//リクエストパラメータ―の取得 2
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subject = req.getParameter("f3");
		numStr = req.getParameter("f4");

		if (isAttendStr != null) {
			// 在学フラグを立てる
			isAttend = true;
		}

		//DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list = cNumDao.filter(teacher.getSchool());

		//ビジネスロジック 4
		if (entYearStr != null) {
			// 数値に変換
			entYear = Integer.parseInt(entYearStr);
		}

		if (numStr != null) {
			// 数値に変換
			num = Integer.parseInt(numStr);
		}

		if (entYear != 0 && !classNum.equals("0")&&subject.equals("0")&& num != 0){
			// 入学年度、クラス番号、科目、回数

			//studentsリストに
			tests = tDao.filter(teacher.getSchool(), entYear, classNum, subject, num, isAttend);

		}
//		else {
//			errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
//			req.setAttribute("errors", errors);
//			// 全学生情報を取得
//			tests = tDao.filter(teacher.getSchool(), isAttend);
//			System.out.println("4");
//
//		}

		//ビジネスロジック 4
		// リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		// 10年前から1年後まで年をリストに追加
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}

		//DBへデータ保存 5

		//レスポンス値をセット 6
		// リクエストに入学年度をセット
		req.setAttribute("f1", entYear);
		// リクエストにクラス番号をセット
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subject);
		req.setAttribute("f4", num);
		// リクエストに学生リストをセット
		req.setAttribute("tests", tests);
		// リクエストにデータをセット
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("subject ?",);//今は形だけ
		req.setAttribute("numStr ?",);//形だけ
		//↑のこいつらをstudent_list.jspに渡す
		//JSPへフォワード 7
		req.getRequestDispatcher("student_list.jsp").forward(req, res);
	}

}