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
import bean.TestListSubject2;

public class TestListSubjectDao2 extends Dao{


	private List<TestListSubject2> postFilter(ResultSet rSet) throws Exception {
		//まずはここに処理追加
		 // リストを初期化
	    List<TestListSubject2> list = new ArrayList<>();
	    String student ="";
	    // インスタンスを初期化
	    TestListSubject2 test_list_subject = new TestListSubject2();
        Map<Integer, Integer> points = new HashMap<>();

	    try {
	        // リザルトセットを全権走査
	        while (rSet.next()) {

	        	// 前回と同じ学生が連続した場合
	            if(student.equals(rSet.getString("student_no"))){

	            	// 学生インスタンスに検索結果をセット
	            	points.put(rSet.getInt("test_no"),rSet.getInt("point"));
	            	test_list_subject.setPoints(points);

	            // 前回と違う学生が来た場合
	            } else {

	            	// ループが初回じゃない場合
	            	if(!(student.equals(""))) {
	            		// リストに追加
	    	            list.add(test_list_subject);
	            	}

		            // 学生インスタンスを初期化
		            test_list_subject = new TestListSubject2();
		            points = new HashMap<>();

		            // 学生インスタンスに検索結果をセット
		            test_list_subject.setEntYear(rSet.getInt("ent_year"));;
		            test_list_subject.setStudentNo(rSet.getString("student_no"));
		            test_list_subject.setStudentName(rSet.getString("student_name"));
		            test_list_subject.setClassNum(rSet.getString("class_num"));
		            points.put(rSet.getInt("test_no"),rSet.getInt("point"));
		            test_list_subject.setPoints(points);

		         // 学生情報を更新
	            	student = rSet.getString("student_no");
	            }


	        }

	        if(!(student.equals(""))) {
	            // リストに追加
	            list.add(test_list_subject);
	        }


	    } catch (SQLException | NullPointerException e) {
	        e.printStackTrace();
	    }

	    return list;
	}


	//フィルター（検索の時に使いそう？）
	public List<TestListSubject2> filter(School school, int entYear, String classNum, Subject subject) throws Exception {
		//まずはここに処理追加

		//リストに成績をためてpostfilterで取り出す
		List<TestListSubject2> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;


        try {
            statement = connection.prepareStatement(
            "SELECT ent_year,test.student_no ,student_name,test.class_num,test.test_no,point "
            + "FROM TEST inner join student on test.student_no = student.student_no "
            + "where ent_year = ? and test.class_num = ? and subject_cd = ? and test.school_cd = ?");
            statement.setInt(1, entYear);
            statement.setString(2, classNum);
            statement.setString(3, subject.getSubject_cd());
            statement.setString(4, school.getSchool_cd());

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
