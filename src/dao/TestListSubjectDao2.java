package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListSubject;

public class TestListSubjectDao2 {

	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		//まずはここに処理追加
		 // リストを初期化
	    List<Student> list = new ArrayList<>();

	    try {
	        // リザルトセットを全権走査
	        while (rSet.next()) {
	            // 学生インスタンスを初期化
	            Student student = new Student();

	            // 学生インスタンスに検索結果をセット
	            student.setStudent_no(rSet.getString("student_no"));
	            student.setStudent_name(rSet.getString("student_name"));
	            student.setEntYear(rSet.getInt("ent_year"));
	            student.setClass_num(rSet.getString("class_num"));
	            student.setAttend(rSet.getBoolean("is_attend"));
	            student.setSchool(school);

	            // リストに追加
	            list.add(student);
	        }
	    } catch (SQLException | NullPointerException e) {
	        e.printStackTrace();
	    }

	    return list;
	}

}
