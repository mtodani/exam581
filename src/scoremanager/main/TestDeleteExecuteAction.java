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

		List<Test> DeleteTestlist= new ArrayList<>();// 学生リスト
		List<Test> list = new ArrayList<>();
		List<Integer> list_point = new ArrayList<>();
		List<Student> students= new ArrayList<Student>();
		boolean is_Attend=true;
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		int year = todaysDate.getYear();// 現在の年を取得


		//リクエストパラメータ―の取得 2
		String no = req.getParameter("d1");//学番
		String subStr = req.getParameter("d2");
		String numStr = req.getParameter("d3");
		//学番
		System.out.println(no);
		//科目
		System.out.println(subStr);
		//回数
		System.out.println(numStr);

		Subject subject = subjectDao.get(subStr,teacher.getSchool());
		Student student = sDao.get(no);

//		String noStr = req.getParameter("student_no");
//		String pointStr = req.getParameter("point");
//		int point = Integer.parseInt(pointStr);

		//tests = req.getParameter("tests");
		if (numStr != null) {
			// 数値に変換
			num = Integer.parseInt(numStr);
		}

		Test DeleteTest = new Test();

		DeleteTest= tDao.delete_get(student, subject, teacher.getSchool(), num);
		//DeleteTestlist.add(DeleteTest);
		System.out.println("DeleteTest");
		System.out.println(DeleteTest);



		//繰り返しで1つずつ格納

		tDao.delete(DeleteTest);

		//エラーがあったかどうかで手順6~7の内容が分岐
		//JSPへフォワード 7

		//req.setAttribute("class_num_set", list);

		req.getRequestDispatcher("test_delete_done.jsp").forward(req, res);
	}
}
