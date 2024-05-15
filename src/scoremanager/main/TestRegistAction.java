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

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
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
		String subjectcd="";//入力された科目
		String numStr ="";// 入力された回数
		int num = 0;// 回数
		int entYear = 0;// 入学年度
		boolean is_Attend=true;
		List<Test> tests= new ArrayList<Test>();// 学生リスト
		List<Student> students= new ArrayList<Student>();
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得
		TestDao tDao = new TestDao();//学生Dao
		StudentDao sDao = new StudentDao();//学生Dao
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化
		SubjectDao subjectDao = new SubjectDao();// 科目Daoを初期化
		Map<String, String> errors = new HashMap<>();// エラーメッセージ

		//リクエストパラメータ―の取得 2
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		subjectcd = req.getParameter("f3");
		numStr = req.getParameter("f4");

		//DBからデータ取得 3
		// ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		List<String> list = cNumDao.filter(teacher.getSchool());
		// ログインユーザーの学校コードをもとに科目の一覧を取得
		List<Subject> list2 = subjectDao.filter(teacher.getSchool());

		//ビジネスロジック 4
		if (entYearStr != null) {
			// 数値に変換
			entYear = Integer.parseInt(entYearStr);
		}

		if (numStr != null) {
			// 数値に変換
			num = Integer.parseInt(numStr);
		}

		Subject subject = new Subject();
//		subject = subjectDao.get(subjectcd, teacher.getSchool());
//		Subject subject = subjectDao.get(subjectcd,teacher.getSchool());

		subject.setSubject_cd(subjectcd);
//		subject.setSubject_name("Webデザイン");
//		subject.setSubject_now(true);
//		subject.setSchool(teacher.getSchool());

		//tests = tDao.filter(entYear,classNum,subject,num,teacher.getSchool());

		//
		students = sDao.filter(teacher.getSchool(), entYear, classNum, is_Attend);

		//forで一人ひとり格納
		for(Student stu:students ){
			//String	stu_no = stu.getStudent_no();
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

		//Test test_score = tDao.get(stu, subject, teacher.getSchool(), num);
		//System.out.println("★");


//		} else {
//			errors.put("f1", "error");
//			req.setAttribute("errors", errors);
//		}
		//ビジネスロジック 4
		// リストを初期化
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

		//DBへデータ保存 5

		//レスポンス値をセット 6
		// リクエストに入学年度をセット
		req.setAttribute("f1", entYear);
		// リクエストにクラス番号をセット
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectcd);
		req.setAttribute("f4", num);
		// リクエストに学生リストをセット
		req.setAttribute("tests", tests);
		// リクエストにデータをセット
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("subject_set",list2);
		req.setAttribute("numStr_set",NumSet);
		//↑のこいつらをstudent_list.jspに渡す
		//JSPへフォワード 7
		req.getRequestDispatcher("test_regist.jsp").forward(req, res);

	}

}