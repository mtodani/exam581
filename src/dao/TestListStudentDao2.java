package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent2;

public class TestListStudentDao2 extends Dao{


	private List<TestListStudent2> postFilter(ResultSet rSet) throws Exception {
		//まずはここに処理追加
		 // リストを初期化
	    List<TestListStudent2> list = new ArrayList<>();

	    try {
	        // リザルトセットを全権走査
	        while (rSet.next()) {
	            // 学生インスタンスを初期化
	            TestListStudent2 test_list_student = new TestListStudent2();

	            // 学生インスタンスに検索結果をセット
	            test_list_student.setSubjectName(rSet.getString("subject_name"));;
	            test_list_student.setSubjectCD(rSet.getString("subject_cd"));
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


	//フィルター（検索の時に使いそう？）
	public List<TestListStudent2> filter(Student student) throws Exception {
		//まずはここに処理追加

		//リストに成績をためてpostfilterで取り出す
		List<TestListStudent2> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        try {
            statement = connection.prepareStatement(
            "SELECT subject.SUBJECT_NAME,test.SUBJECT_CD,test.TEST_NO ,test.POINT " +
            "FROM test left outer join subject " +
            "ON  subject.subject_cd = test.subject_cd and subject.school_cd = test.school_cd "  +
            "where test.student_no = ? ");
            statement.setString(1,student.getStudent_no());

            rSet = statement.executeQuery();
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
