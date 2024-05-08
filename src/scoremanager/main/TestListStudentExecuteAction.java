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
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import tool.Action;

public class TestListStudentExecuteAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();   // セッション
		Teacher teacher = (Teacher) session.getAttribute("user");   // ログインユーザーを取得
		TestListStudentDao TLStuDao = new TestListStudentDao();   // 成績Daoを初期化
		SubjectDao sDao = new SubjectDao();   // 科目Daoを初期化
		StudentDao StuDao = new StudentDao();   // 学生Daoを初期化
		Student student = new Student();   // 学生

		String student_num;   // 学生番号の宣言
		Map<Integer,Integer> points = new HashMap<>();   // 成績
		List<TestListStudent> TLStuList = new ArrayList<>();   // 空の成績リスト

		LocalDate todaysDate = LocalDate.now();   // LcalDateインスタンスを取得
		int year = todaysDate.getYear();   // 現在の年を取得

		List<Integer> entYearSet = new ArrayList<>();   // 入学年度のリストを初期化
		ClassNumDao cNumDao = new ClassNumDao();   // クラス番号Daoを初期化
		Map<String, String> errors = new HashMap<>();   // エラーメッセージ


		//リクエストパラメータ―の取得 2
		student_num = req.getParameter("stu_num");   // 学生情報

		req.setAttribute("stu_num", student_num);

		System.out.println(student_num);
		System.out.println("1");


		// 10年前から1年後まで年をリストに追加
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}


		//DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> classlist = cNumDao.filter(teacher.getSchool());
		// ログインユーザーの学校コードをもとに科目の一覧を取得
		List<Subject> sublist = sDao.filter(teacher.getSchool());

		System.out.println("2");


		//ビジネスロジック 4
		try{
			student = StuDao.get(student_num);
			TLStuList = TLStuDao.filter(student);
		}catch(NullPointerException e){
			errors.put("nullpo", "学生番号が存在しませんでした。");
		}

		System.out.println("3");

		//DBからデータ取得 3
		//DBから成績表示に必要なデータをリスト形式で取得
		req.setAttribute("test_list_student",TLStuList);

		// jspで学生名を表示させるためのもの
		String stu_name = student.getStudent_name();
		req.setAttribute("stu_name", stu_name);

		//DBへデータ保存 5
		//なし

		//レスポンス値をセット 6
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("classlist", classlist);
		req.setAttribute("sublist", sublist);

		System.out.println(errors.get("nullpo"));

		req.setAttribute("errors", errors);

		System.out.println("4");

		//JSPへフォワード 7
		req.getRequestDispatcher("test_list_student.jsp").forward(req, res);
	}
}
