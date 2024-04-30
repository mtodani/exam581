package scoremanager.main;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject2;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao2;
import tool.Action;

public class TestListSubjectExecute2Action extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		TestListSubjectDao2 TLSubDao2 = new TestListSubjectDao2();//成績Dao
		SubjectDao SubDao = new SubjectDao();

		int entYear;//入学年度
		String classNum = "";//クラス番号
		String subjectCd = "";//科目

		Map<Integer,Integer> points = new HashMap<>();// 成績（何回目、点数）

		Map<String, String> errors = new HashMap<>();// エラーメッセージ
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		List<Integer> entYearSet = new ArrayList<>();//入学年度のリストを初期化
		Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得

		List<TestListSubject2> TLSubList = new ArrayList<>();//空の成績リスト（dao.filterの返却先）



		//リクエストパラメータ―の取得 2
		entYear = Integer.parseInt(req.getParameter("f1"));//入学年度
		classNum = req.getParameter("f2");//クラス番号
		subjectCd = req.getParameter("f3");


		req.setAttribute("f1", entYear);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectCd);

		System.out.println(entYear);
		System.out.println(classNum);
		System.out.println(subjectCd);

		//ビジネスロジック 4
		if (entYear == 0 || classNum == "0" || subjectCd == "0") {// 入学年度が選択されていない場合
			errors.put("select", "入学年度、クラス、科目のすべてを選択してください");
		}else{

			//DBからデータ取得 3
			School school = teacher.getSchool();
			Subject sub = SubDao.get(subjectCd,school);
			System.out.println(school.getSchool_name());
			System.out.println(sub.getSubject_name());


			TLSubList = TLSubDao2.filter(school, entYear, classNum, sub);
			//DBから成績表示に必要なデータをリスト形式で取得


			System.out.println(TLSubList.get(1).getPoints().keySet());
			System.out.print("aaaa");


			if (TLSubList == null || TLSubList.size() == 0) {// 検索結果がない場合
				String message = "成績情報が存在しませんでした。";
				req.setAttribute("NoIns", message);
			}else{
				req.setAttribute("test_list_subs", TLSubList);
			}
		}


		//DBへデータ保存 5
		//なし
		//レスポンス値をセット 6
		//なし
		//JSPへフォワード 7
		req.getRequestDispatcher("test_list_student2.jsp").forward(req, res);
	}
}
