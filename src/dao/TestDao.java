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



	//get(学生番号、科目、学校、何回目テストか)
	//get(2374475、数学、knz、1)　この条件に当てはまるtestのデータを持っている
	public Test get(Student student,Subject subject,School school,int test_no) throws Exception {

		//学生インスタンスを初期化
	    Test test = new Test();
	    //データエースへのコネクションを確立
	    Connection connection = getConnection();
	    //プリペアードステートメント
	    PreparedStatement statement = null;
	    try {
	        //プリペアードSQL文をセット
	    	statement = connection.prepareStatement(
	    			"select * from test where student_no= ? and subject_cd=? and school_cd=? and test_no=?");
	    	//プリペアードステートメントに学校コードをバインド
	    	statement.setString(1, student.getStudent_no());
	    	statement.setString(2, subject.getSubject_cd());
	    	statement.setString(3, school.getSchool_cd());
	    	statement.setInt(4, test_no);
	        //プリペアードステートメント
		    ResultSet rSet = statement.executeQuery();

	        if (rSet.next()) {
	        	// リザルトセットが存在する場合
			    // テストインスタンスに検索結果をセット
	            test.setStudent(student);
	            test.setSubject(subject);
	            test.setSchool(school);
	            test.setNo(test_no);
	            test.setPoint(rSet.getInt("point"));
	          //test.setClassNum(rSet.getString("class_num"));
	        }
	        else{
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

	private String baseSql = "SELECT Subject.Subject_cd as sj_cd, Subject.Subject_name as sj_name,Student.Student_no as st_no, "
			+"Student.ent_year as st__ent_year, Student.class_num as st_class_num,Student.is_Attend st_is_attend, "
			+"Test.Test_no as t_no, coalesce(Test.point,-1) as t_point "
			+"FROM Student LEFT OUTER JOIN (Test INNER JOIN Subject ON Test.subject_cd=Subject.Subject_cd) "
			+"ON Student.Student_no = Test.student_no ";
	//テスト
/* 			OK

			SELECT Subject.Subject_cd as sj_cd, Subject.Subject_name as sj_name,Student.Student_no as st_no,
			Student.ent_year as st__ent_year, Student.class_num as st_class_num,Student.is_Attend st_is_attend,
			Test.Test_no as t_no, coalesce(Test.point,-1) as t_point
			FROM Student LEFT OUTER JOIN (Test INNER JOIN Subject ON Test.subject_cd=Subject.Subject_cd)
			ON Student.Student_no = Test.student_no

 			これもOK
 			SELECT Subject.Subject_cd, Subject.Subject_name,Student.Student_no,
			Student.ent_year, Student.class_num,Student.is_Attend,
			Test.Test_no, coalesce(Test.point,-1)

			FROM Student LEFT OUTER JOIN (Test INNER JOIN Subject ON Test.subject_cd=Subject.Subject_cd)
			ON Student.Student_no = Test.student_no

			//これなら全部出た
			SELECT Subject.Subject_cd, Subject.Subject_name,Student.Student_no,Student.ent_year, Student.class_num,Student.is_Attend,Test.Test_no
			FROM Subject,Student,Test

			//これもクリア
		 	SELECT Subject.Subject_cd, Subject.Subject_name,Test.Test_no
			FROM Test
			INNER JOIN Subject
			ON Test.subject_cd=Subject.Subject_cd

			*/
	//ResultSet型をtest型に変換するメソッド
	//ポストフィルター
	private List<Test> postFilter(ResultSet rSet, School school) throws Exception{
		//戻り値用のリスト
	    List<Test> list = new ArrayList<>();
	    StudentDao studentdao = new StudentDao();
	    SubjectDao subjectdao = new SubjectDao();
	    try {
	        while (rSet.next()) {
	            // 初期化
	        	Test test = new Test();
	            //検索結果をセット
	        	test.setStudent(studentdao.get(rSet.getString("student_no")));
	        	test.setSubject(subjectdao.get(rSet.getString("subject_cd"),school));
	        	test.setNo(rSet.getInt("test_no"));
	        	test.setClassNum(rSet.getString("class_num"));
	        	System.out.println(test.getNo());
	        	test.setPoint(rSet.getInt("t_point"));
	        	System.out.println(test.getPoint());
	        	test.setSchool(school);
	        	System.out.println(test.getNo());
	        	System.out.println("aaa");
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
		String condition = " Where  student.school_cd = ? and  (test.subject_cd = ? or test.subject_cd is null) and student.ent_year = ? and (test.test_no = ? or test.test_no is null) "
							+"and student.class_num = ? and ( subject.school_cd is null or subject.school_cd = ?)";
//	 一応	Where student.ent_year=2023  and student.class_num = '211' and subject.subject_cd is null or subject.subject_cd = 'IT1' and test.test_no is null or test.test_no = 1 and subject.school_cd='knz'
		//SQL文のソート
//		String order = " order by asc";

		try{
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition);

			statement.setString(1,school.getSchool_cd());
			//プリペアードステートメントに科目番号をバインド
			System.out.println("ccc");
			statement.setString(2,subject.getSubject_cd());
			//プリペアードステートメントにクラス番号をバインド
			statement.setInt(3,entYear);
			System.out.println("bbb");
			//プリペアードステートメントに回数をバインド
			statement.setInt(4,num);
			//プリペアードステートメントにクラス番号をバインド
			statement.setString(5,classNum);
			//プリペアードステートメントに学校をバインド
			statement.setString(6,school.getSchool_cd());

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

	public boolean save (List<Test> lists) throws Exception {

		//プリペアードステートメント
		//PreparedStatement statement= null;
		//リザルトセット
		//ResultSet rSet = null;
		//boolean bool=false;

		int count= 0;
		for(Test test:lists){
			//コネクションを確立
			Connection connection = getConnection();
			try{
				boolean bool = save(test,connection);
				if(bool!=true){
					count++;
				}
			}
			catch (Exception e){
				throw e;
			} finally{
				if(connection != null){
					try{
						connection.close();
					}catch(SQLException sqle){
						throw sqle;
					}
				}
			}
		}
		if(count > 0){
			return true;
		}
		else{
			return false;
		}
	}
	private boolean save (Test test,Connection connection) throws Exception {
		//プリペアードステートメント
		PreparedStatement statement= null;

		//実行件数
		int count = 0;

		try{

			Test old = get(test.getStudent(),test.getSubject(),test.getSchool(),test.getNo());
			if(old == null){
				//プリペアードステートメントにINSERT文をセット
				statement = connection.prepareStatement(
				"insert into test(STUDENT_NO ,SUBJECT_CD ,SCHOOL_CD ,TEST_NO ,POINT ,CLASS_NUM )"
						+" values(?,?,?,?,?,?)");
				//プリペアードステートメントに値をバインド
				statement.setString(1, test.getStudent().getStudent_no());
				statement.setString(2, test.getSubject().getSubject_cd());
				statement.setString(3, test.getSchool().getSchool_cd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());
				statement.setString(6, test.getClassNum());
				System.out.print("インサート");

			}else {
			//	update subject set subject_now = true
				statement = connection.prepareStatement(
						"update test set point = ? "
						+"Where student_no = ? and subject_cd = ? and   school_cd = ? and  test_no = ? and  class_num = ? ");
				statement.setInt(1, test.getPoint());
				statement.setString(2, test.getStudent().getStudent_no());
				statement.setString(3, test.getSubject().getSubject_cd());
				statement.setString(4, test.getSchool().getSchool_cd());
				statement.setInt(5, test.getNo());
				statement.setString(6, test.getClassNum());
				System.out.print("アップデート");
			}
			//プリペアードステートメントを実行
			count = statement.executeUpdate();


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

		if (count >0){
			// 実行件数が1件以上ある場合
			return true;
		}else{
			return false;
		}
	}

}


