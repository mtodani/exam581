package scoremanager.main;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;

public class Util{


	public Teacher getUser(HttpServletRequest req)throws Exception{

		//ローカル変数の宣言 1
		HttpSession session = req.getSession();//セッション
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		Teacher teacher = (Teacher)session.getAttribute("user");//ログインユーザー



		String isAttendStr="";//入力された在学フラグ

		boolean isAttend = false;// 在学フラグ
		List<Student> students = null;// 学生リスト


		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得


		StudentDao sDao = new StudentDao();//学生Dao

		Map<String, String> errors = new HashMap<>();// エラーメッセージ

	}

	public void setClassNumSet(HttpServletRequest req)throws Exception{

		HttpSession session = req.getSession();//セッション
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		String classNum = "";//入力されたクラス番号
		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化

		req.setAttribute("classNum", classNum);


	}

	public void setEntYearSet(HttpServletRequest req)throws Exception{

		HttpSession session = req.getSession();//セッション
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		String entYearStr="";// 入力された入学年度
		int entYear = 0;// 入学年度
		int year = todaysDate.getYear();// 現在の年を取得

	}

	public void setSubjects(HttpServletRequest req)throws Exception{
		
		SubjectDao subDao = new SubjectDao();//科目Dao
//		int entYear;//入学年度
		String subject_cd = "";//科目コード
		String subject_name = "";//科目名
		Subject subject = null;//科目

		HttpSession session = req.getSession();//セッション
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		String SubjectsStr = "";

	}

	public void setNumSet(HttpServletRequest req)throws Exception{

		HttpSession session = req.getSession();//セッション
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
		String NumSetStr = "";

	}
}
