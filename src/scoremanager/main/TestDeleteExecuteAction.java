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

public class TestDeleteExecuteAction extends Action{
	//メモ　学番、科目コード、回数をjspからもらって
	//Daoのgetでその値があるか確認あればdeleteに渡して削除へ
	//jspは完了のページだけ
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//ローカル変数の宣言 1

		String no = "";
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


		List<Test> DeleteTestlist= new ArrayList<>();// 学生リスト
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

		System.out.println(students.get(0).getStudent_no());
		System.out.println(subject.getSubject_cd());
		System.out.println(num);

		Test DeleteTest = new Test();
		for(Student stu:students ){
			no = req.getParameter("d1_" + stu.getStudent_no());//学番
			if(no != null){
				DeleteTest= tDao.delete_get(stu, subject, teacher.getSchool(), num);
				System.out.println("DeleteTest");
				System.out.println(DeleteTest);
				boolean bool = tDao.delete(DeleteTest);
			}

		}


		//繰り返しで1つずつ格納



		//エラーがあったかどうかで手順6~7の内容が分岐
		//JSPへフォワード 7

		//req.setAttribute("class_num_set", list);

		req.getRequestDispatcher("test_delete_done.jsp").forward(req, res);
	}
}
