package scoremanager.main;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action{

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();//セッション

		SubjectDao subDao = new SubjectDao();//科目Dao
//		int entYear;//入学年度
		String subject_cd = "";//科目コード
		String subject_name = "";//科目名
		Subject subject = null;//学生

		Map<String, String> errors = new HashMap<>();// エラーメッセージ

		ClassNumDao cNumDao = new ClassNumDao();// クラス番号Daoを初期化

		Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得
		LocalDate todaysDate = LocalDate.now();// LcalDateインスタンスを取得
//		int year = todaysDate.getYear();// 現在の年を取得
//		List<Integer> entYearSet = new ArrayList<>();//入学年度のリストを初期化

		//リクエストパラメータ―の取得 2
//		entYear = Integer.parseInt(req.getParameter("ent_year"));//入学年度
		subject_cd = req.getParameter("subject_cd");//学生番号
		subject_name = req.getParameter("subject_name");//氏名

		//DBからデータ取得 3
		subject = subDao.get(subject_cd, teacher.getSchool());// 科目管理から科目インスタンスを取得
		List<String> list = cNumDao.filter(teacher.getSchool());// ログインユーザーの学校コードをもとにクラス番号の一覧を取得


		//ビジネスロジック 4
		//DBへデータ保存 5
		//条件で手順4~5の内容が分岐



		if (subject == null) {// 学生が未登録だった場合
			// 学生インスタンスを初期化
			subject = new Subject();
			// インスタンスに値をセット
			subject.setSubject_cd(subject_cd);
			subject.setSubject_name(subject_name);
//				subject.setAttend(true);
			subject.setSchool(((Teacher)session.getAttribute("user")).getSchool());
			// 科目を保存
			subDao.save(subject);
		} else {//入力された学番がDBに保存されていた場合
			errors.put("subject_cd", "科目コードが重複しています");
		}


		//エラーがあったかどうかで手順6~7の内容が分岐
		//エラーがあったか場合に備えて　以下のモノをリクエストに取り込む

		//レスポンス値をセット 6
		//JSPへフォワード 7
//		req.setAttribute("class_num_set", list);//クラス番号のlistをセット
//		req.setAttribute("ent_year_set", entYearSet);//入学年度のlistをセット

		if(!errors.isEmpty()){
			// リクエスト属性をセット
			req.setAttribute("errors", errors);
			req.setAttribute("sub_cd", subject_cd);
			req.setAttribute("sub_name", subject_name);
			req.getRequestDispatcher("subject_create.jsp").forward(req, res);
			return;
		}
		req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
	}

}

