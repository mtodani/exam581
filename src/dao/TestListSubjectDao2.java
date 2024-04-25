package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.TestListSubject;

public class TestListSubjectDao2 {

	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		//まずはここに処理追加
		 // リストを初期化
	    List<TestListSubject> list = new ArrayList<>();

	    try {
	        // リザルトセットを全権走査
	        while (rSet.next()) {
	            // 学生インスタンスを初期化
	            TestListSubject test_list_subject = new TestListSubject();

	            // 学生インスタンスに検索結果をセット
	            test_list_subject.setEntYear(rSet.getInt("ent_year"));;
	            test_list_subject.setClassNum(rSet.getString("class_num"));
	            test_list_subject.setEntYear(rSet.get("subject"));

	            // リストに追加
	            list.add(student);
	        }
	    } catch (SQLException | NullPointerException e) {
	        e.printStackTrace();
	    }

	    return list;
	}

}
