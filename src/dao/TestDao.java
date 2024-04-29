package dao;

//StudentList Daoをパクってます

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;
public class TestDao extends Dao{

	private String baseSql = "select * from test where";

	//get(学生番号、科目、学校、何回目テストか)
	//get(2374475、数学、knz、1)　この条件に当てはまるtestのデータを持っている
	public Test get(Student student,Subject subject,School school,int no) throws Exception {

		//学生インスタンスを初期化
	    Test test = new Test();
	    //データエースへのコネクションを確立
	    Connection connection = getConnection();
	    //プリペアードステートメント
	    PreparedStatement statement = null;
	    try {
	        //プリペアードSQL文をセット
	    	statement = connection.prepareStatement("select * from test where student_no= ? and subject_cd and school_cd and no=?");
	    	//プリペアードステートメントに学校コードをバインド
	    	statement.setString(1, student.getStudent_no());
	    	statement.setString(2, subject.getSubject_cd());
	    	statement.setString(3, school.getSchool_cd());
	    	statement.setInt(4, no);
	        //プリペアードステートメント
		    ResultSet rSet = statement.executeQuery();

	        if (rSet.next()) {
	        	// リザルトセットが存在する場合
			    // テストインスタンスに検索結果をセット
	            test.setStudent(student);
	            test.setSchool(school);
	            test.setClassNum(rSet.getString("class_num"));
	            test.setNo(no);
	            test.setPoint(rSet.getInt("point"));
	            test.setSubject(subject);
	        }
	        else{
	        	//リザルトセットが存在する場合
	        	//学生インスタンス
	        	test= null;
	        }
	    } catch (SQLException e) {
	        throw e;
	    } finally {
	        // リソースを閉じる
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	        if (connection != null) {
	            try {
	                connection.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }
	    return test;
	}


	//ResultSet型をtest型に変換するメソッド
	//ポストフィルター
	private List<Test> postFilter(ResultSet rSet, School school){
		//戻り値用のリスト
	    List<Test> list = new ArrayList<>();
	    try {
	        while (rSet.next()) {
	            // 学生インスタンスを初期化
	        	Test test = new Test();
	            // 学生インスタンスに検索結果をセット
	        	test.setEntyear(rSet.getInt("ent_year"));
	        	test.setSubject(rSet.getSubject("subject"));
	        	test.setNo(rSet.getInt("no"));
	        	test.setClassNum(rSet.getString("class_num"));
	        	test.setPoint(rSet.getInt("point"));
	        	test.setSchool(school);
	            // リストに追加
	            list.add(test);
	        }
	    } catch (SQLException | NullPointerException e) {
	        e.printStackTrace();
	    }

	    return list;
	}


	//フィルター
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school)throws Exception{
		//リスト初期化
		List<Test> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement= null;
		//リザルトセット
		ResultSet rSet = null;
		//SQl文の条件
		String condition = " ent_year=? and class_num=? and subject=?";
		//SQL文のソート
		String order = " order by asc";

		try{
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + order);
			//プリペアードステートメントに入学年度をバインド
			statement.setInt(1,entYear);
			//プリペアードステートメントにクラス番号をバインド
			statement.setString(2,classNum);
			//プリペアードステートメントに科目番号をバインド
			statement.setString(3,subject.getSubject_cd());


			//プリペアードステートメントを実行
			rSet = statement.executeQuery();
			//リストへの格納処理を実行
			list = postFilter(rSet,school);
		} catch (Exception e){
			throw e;
		} finally{
			//プリペアードステートメントを閉じる
			if(statement !=null){
				try {
					statement.close();
				} catch (SQLException sqle){
					throw sqle;
				}
			}
			//コネクションを閉じる
			if(connection !=null){
				try {
					connection.close();
				} catch (SQLException sqle){
					throw sqle;
				}
			}
		}

		return list;
	}

	public boolean save () throws Exception {

	}

}
