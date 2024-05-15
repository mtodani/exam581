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
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.Action;

public class TestRegistExecuteAction extends Action {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1

		int num = 0;// 回数
		int entYear = 0;// 入学年度
		StudentDao sDao = new StudentDao();// 学生Dao
		TestDao tDao = new TestDao();// TestDao
		SubjectDao subjectDao = new SubjectDao();// SubjectDao
		boolean isAttend = false;//在学フラグ
		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher)session.getAttribute("user");// ログインユーザーを取得
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		Map<String, String> errors = new HashMap<>();//エラーメッセージ

		List<Test> tests= new ArrayList<>();// 学生リスト
		List<Test> list = new ArrayList<>();
		List<Integer> list_point = new ArrayList<>();
		List<Student> students= new ArrayList<Student>();
		boolean is_Attend=true;
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得

		//リクエストパラメータ―の取得 2
		String entYearStr = req.getParameter("f1");
		String classNum = req.getParameter("f2");
		String subjectcd = req.getParameter("f3");
		String numStr = req.getParameter("f4");

		List<Integer> entYearSet = new ArrayList<>();
		// 10年前から1年後まで年をリストに追加
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}

		//回数のリスト
		List<Integer> NumSet = new ArrayList<>();
		// 回数に1回、２回を追加
		NumSet.add(1);
		NumSet.add(2);

		List<String> execute_list = cNumDao.filter(teacher.getSchool());
		List<Subject> execute_list2 = subjectDao.filter(teacher.getSchool());
		req.setAttribute("class_num_set", execute_list);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("subject_set",execute_list2);
		req.setAttribute("numStr_set",NumSet);
//		String noStr = req.getParameter("student_no");
//		String pointStr = req.getParameter("point");
//		int point = Integer.parseInt(pointStr);

		//tests = req.getParameter("tests");
		if (entYearStr != null) {
			// 数値に変換
			entYear = Integer.parseInt(entYearStr);
		}
		if (numStr != null) {
			// 数値に変換
			num = Integer.parseInt(numStr);
		}

		Subject subject = subjectDao.get(subjectcd,teacher.getSchool());

		students = sDao.filter(teacher.getSchool(), entYear, classNum, is_Attend);

		for(Student stu:students ){
			Test score = new Test();
			Test test_score = new Test(); //for内で初期化しないとバグる
			test_score.setStudent(stu);
			test_score.setClassNum(classNum);
			test_score.setSubject(subject);
			test_score.setSchool(teacher.getSchool());
			test_score.setNo(num);
			test_score.setPoint(0);

			score = tDao.get(stu, subject, teacher.getSchool(), num);
			if(score != null){
				test_score.setPoint(score.getPoint());
			}
			tests.add(test_score);
		}
		System.out.println("num");
		System.out.println(tests.get(1).getPoint());

		//繰り返しで1つずつ格納
		for(Test test2:tests ){

			int int_point = 0;
			String point = req.getParameter("point_" + test2.getStudent().getStudent_no());

			try{
				int_point = Integer.parseInt(point);
				System.out.println(point);
			}catch(NumberFormatException num_e){
				errors.put("test_errors", "0～100の範囲で入力してください");
				req.setAttribute("errors",errors);
				System.out.println(errors);
				req.getRequestDispatcher("test_regist.jsp").forward(req,res);
			}
			if(point != "0"){

				if(int_point< 0 || int_point> 100){
					errors.put("test_errors2", "0～100の範囲で入力してください");
					req.setAttribute("errors",errors);
					req.getRequestDispatcher("test_regist.jsp").forward(req,res);
				} else{
					test2.setPoint(int_point);
				}
				list.add(test2);
			}
		}

		tDao.save(list);

		//エラーがあったかどうかで手順6~7の内容が分岐
		//JSPへフォワード 7

		//req.setAttribute("class_num_set", list);

		req.getRequestDispatcher("test_regist_done.jsp").forward(req, res);
	}
}