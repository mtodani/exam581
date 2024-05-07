package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao{


	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {

		// リストを初期化
	    List<TestListSubject> list = new ArrayList<>();

	    try {
	        // リザルトセットを全権走査
	        while (rSet.next()) {
	            // インスタンスを初期化
	            TestListSubject test_list_subject = new TestListSubject();
	            Map<Integer, Integer> points = new HashMap<>();

	            // 学生インスタンスに検索結果をセット
	            test_list_subject.setEntYear(rSet.getInt("ent_year"));
	            test_list_subject.setStudentNo(rSet.getString("student_no"));
	            test_list_subject.setStudentName(rSet.getString("student_name"));
	            test_list_subject.setClassNum(rSet.getString("class_num"));
	            points.put(rSet.getInt("test_no"),rSet.getInt("point"));
	            test_list_subject.setPoints(points);

	            // リストに追加
	            list.add(test_list_subject);
	        }
	    } catch (SQLException | NullPointerException e) {
	        e.printStackTrace();
	    }

	    return list;
	}


	public List<TestListSubject> filter(School school, int entYear, String classNum, Subject sub) throws Exception {

		// リストを初期化
		List<TestListSubject> list = new ArrayList<>();
		// コネクションを確立
        Connection connection = getConnection();
        // プリペアードステートメント
        PreparedStatement statement = null;
        // リザルトセット
        ResultSet rSet = null;

        try {

        	// プリペアードステートメントにSQL文をセット
        	statement = connection.prepareStatement(
                    "SELECT ent_year,test.student_no ,student_name,test.class_num,test.test_no,point "
                    + "FROM TEST inner join student on test.student_no = student.student_no "
                    + "where ent_year = ? and test.class_num = ? and subject_cd = ? and test.school_cd = ?");
        	statement.setInt(1, entYear);
            statement.setString(2, classNum);
            statement.setString(3, sub.getSubject_cd());
            statement.setString(4, school.getSchool_cd());

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
