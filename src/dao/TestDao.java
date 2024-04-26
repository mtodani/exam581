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
public class TestDao extends Dao{

	private String baseSql;

	//ゲット
	public Student get(String no) throws Exception {
	    Student student = new Student();
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    try {
	        // SQL文を作成
	        String sql = "SELECT * FROM student WHERE student_no = ?";
	        statement = connection.prepareStatement(sql);
	        statement.setString(1, no);

		    ResultSet rSet = statement.executeQuery();
		    SchoolDao schoolDao = new SchoolDao();

	        // リザルトセットから学生インスタンスを作成
	        if (rSet.next()) {
	            student.setStudent_no(rSet.getString("student_no"));
	            student.setStudent_name(rSet.getString("student_name"));
	            student.setEntYear(rSet.getInt("ent_year"));
	            student.setClass_num(rSet.getString("class_num"));
	            student.setAttend(rSet.getBoolean("is_attend"));
	            // 学校インスタンスを取得して設定
	            School school = new School();
	            ;
	            student.setSchool(schoolDao.get(rSet.getString("school_cd")));
	        }
	        else{
	        	student= null;
	        }
	    } catch (SQLException e) {
//	        e.printStackTrace();
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
	    return student;
	}


	//ポストフィルター
	private List<Student> postFilter(ResultSet rSet, School school) throws Exception {
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


	//フィルター
	public List<Student> filter(School school, int entYear, String classNum, String subject, int num, boolean isAttend)throws Exception{
		//リスト初期化
		List<Student> list = new ArrayList<>();
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement= null;
		//リザルトセット
		ResultSet rSet = null;
		//SQl文の条件
		String condition = " and ent_year=? and class_num=? and subject=? and count=?";
		//SQL文のソート
		//学生番号の昇順
		String order = " order by student_no asc";

		//SQL文が在学のフラグ条件
		String conditionIsAttend = "";
		//在学フラグがtrueの場合
		if(isAttend){
			conditionIsAttend = " and is_attend=true";
		}

		try{
			//プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend +order);
			//プリペアードステートメントに学校コードをバインド
			statement.setString(1,school.getSchool_cd());
			//プリペアードステートメントにに入学年度をバインド
			statement.setInt(2,entYear);
			//プリペアードステートメントにクラス番号をバインド
			statement.setString(3,classNum);
			//プリペアードステートメントを実行
			rSet = statement.executeQuery();
			//リストへの格納処理を実行
			list = postFilter(rSet, school);
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
