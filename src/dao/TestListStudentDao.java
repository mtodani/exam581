package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao{


	private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {

		// リストを初期化
		List<TestListStudent> list = new ArrayList<>();

		try {
	        // リザルトセットを全権走査
	        while (rSet.next()) {
	            // インスタンスを初期化
	            TestListStudent test_list_student = new TestListStudent();

	            // 学生インスタンスに検索結果をセット
	            test_list_student.setSubjectName(rSet.getString("subject_name"));
	            test_list_student.setSubjectCd(rSet.getString("subject_cd"));
	            test_list_student.setNum(rSet.getInt("test_no"));
	            test_list_student.setPoint(rSet.getInt("point"));

	            // リストに追加
	            list.add(test_list_student);
	        }
	    } catch (SQLException | NullPointerException e) {
	        e.printStackTrace();
	    }

	    return list;
	}


	public List<TestListStudent> filter(Student student) throws Exception {

		// リストを初期化
		List<TestListStudent> list = new ArrayList<>();
		// コネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        // リザルトセット
        ResultSet rSet = null;

        try {

        	// プリペアードステートメントにSQL文をセット
        	statement = connection.prepareStatement(
        			"SELECT subject.SUBJECT_NAME,test.SUBJECT_CD,test.TEST_NO ,test.POINT " +
		            "FROM test left outer join subject " +
		            "ON  subject.subject_cd = test.subject_cd and subject.school_cd = test.school_cd "  +
		            "where test.student_no = ? ");
        	statement.setString(1,student.getStudent_no());

        	// プリペアードステートメントを実行
            rSet = statement.executeQuery();
            // リストへの格納処理を実行
            list = postFilter(rSet);

        }catch(Exception e){
			throw e;

		}finally {

            // Close resources in finally block to ensure they're always closed
            if (statement != null) {
            	try {

            		statement.close();

				} catch (SQLException sqle) {
					// TODO: handle exception
					throw sqle;
				}

            }
            if (connection != null) {
            	try {
            		 connection.close();

				} catch (SQLException sqle) {
					// TODO: handle exception
					throw sqle;
				}

            }
        }

        return list;
	}

}
