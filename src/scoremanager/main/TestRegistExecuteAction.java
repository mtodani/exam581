package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

		//リクエストパラメータ―の取得 2
		String entYearStr = req.getParameter("f1");
		String classNum = req.getParameter("f2");
		String subjectcd = req.getParameter("f3");
		String numStr = req.getParameter("f4");
//		String noStr = req.getParameter("student_no");
//		String pointStr = req.getParameter("point");
//		int point = Integer.parseInt(pointStr);

		System.out.println(entYearStr);
		System.out.println(classNum);
		System.out.println(subjectcd );
		System.out.println(subjectcd);

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

		tests = tDao.filter(entYear,classNum,subject,num,teacher.getSchool());

		//繰り返しで1つずつ格納
		for(Test test2:tests ){
			String point = req.getParameter("point_" + test2.getStudent().getStudent_no());
			test2.setPoint(Integer.parseInt(point));
			test2.setSubject(subject);
			list_point.add(Integer.parseInt(point));
			list.add(test2);

		}

		String student_num = req.getParameter("student_no");
		String student_point = req.getParameter("scores");
		System.out.println(student_num);
		System.out.println(student_point);

		//Student student = sDao.get(no);// 学生番号から学生インスタンスを取得
//		List<String> list = cNumDao.filter(teacher.getSchool());//ログインユーザーの学校コードをもとにクラス番号の一覧を取得

		tDao.save(list);



		//エラーがあったかどうかで手順6~7の内容が分岐
		//レスポンス値をセット 6
		//JSPへフォワード 7
		req.setAttribute("class_num_set", list);

		if(!errors.isEmpty()){//エラーがあった場合、更新画面へ戻る
			// リクエスト属性をセット
			req.setAttribute("errors", errors);
			req.setAttribute("f1", entYear);
			req.setAttribute("f2", classNum);
			req.setAttribute("f3", subjectcd);
			req.setAttribute("f4", num);
			return;
		}

		req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
	}
}