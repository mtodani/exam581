package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao2 extends Dao{

	
	
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


	//フィルター（検索の時に使いそう？）
	public List<Student> filter(School school, int entYear, String classNum, Subject subject) throws Exception {
		//まずはここに処理追加
		List<Student> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        String condition = "and ent_year=? and class_num=?";
        String order = " order by student_no asc";

        String conditionIsAttend = "";

        try {
            statement = connection.prepareStatement("select * from test" + condition + conditionIsAttend + order);
            statement.setString(1, school.getSchool_cd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);

            rSet = statement.executeQuery();
            list = postFilter(rSet, school);
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
