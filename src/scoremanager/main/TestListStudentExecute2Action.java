package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent2;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao2;
import tool.Action;

public class TestListStudentExecute2Action extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		TestListStudentDao2 TLStuDao2 = new TestListStudentDao2();//成績Dao
		StudentDao StuDao = new StudentDao();
		SubjectDao SubDao = new SubjectDao();

		String student_num;//学生番号
		Map<Integer,Integer> points = new HashMap<>();// 成績（何回目、点数）

		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得


		Map<String, String> errors = new HashMap<>();// エラーメッセージ
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		List<Integer> entYearSet = new ArrayList<>();//入学年度のリストを初期化
		Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得

		List<TestListStudent2> TLStuList = new ArrayList<>();//空の成績リスト（dao.filterの返却先）
		Student student = new Student();


		//リクエストパラメータ―の取得 2
		student_num = req.getParameter("stu_num");//学生情報

		req.setAttribute("stu_num", student_num);

		System.out.println(student_num);

		//ビジネスロジック 4

		// 10年前から1年後まで年をリストに追加
		for (int i = year - 10; i < year + 10; i++) {
			entYearSet.add(i);
		}

		//DBからデータ取得 3

		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> clist = cNumDao.filter(teacher.getSchool());

		// ログインユーザーの学校コードをもとに科目の一覧を取得
		List<Subject> slist = SubDao.filter(teacher.getSchool());



		try{
			student = StuDao.get(student_num);
			TLStuList = TLStuDao2.filter(student);
		}catch(NullPointerException e){
			errors.put("nullpo", "学生番号が存在しませんでした。");

		}


		//DBから成績表示に必要なデータをリスト形式で取得

		req.setAttribute("test_list_student",TLStuList);


		//DBへデータ保存 5
		//なし
		//レスポンス値をセット 6

		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("clist", clist);
		req.setAttribute("slist", slist);


		System.out.print(errors.get("nullpo"));
		req.setAttribute("errors", errors);

		//JSPへフォワード 7
		req.getRequestDispatcher("test_list_student2.jsp").forward(req, res);
	}
}
