package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao{

	/**
	 * baseSql:String 共通SQL文 プライベート
	 */
	private String baseSql = "select * from student where school_cd=? ";


	/**
	 * postFilterメソッド フィルター後のリストへの格納処理 プライベート
	 *
	 * @param rSet:リザルトセット
	 * @return 学生のリスト:List<TestListStudent> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {

		// リストを初期化
		List<TestListStudent> list = new ArrayList<>();


	}

	public List<TestListStudent> filter(Student student) throws Exception {

	}

}
