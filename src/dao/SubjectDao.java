package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao{

	/**
	 * getメソッド 科目コードと学校コードを指定して科目インスタンスを1件取得する
	 *
	 * @param cd:String
	 *            科目コード
	 * @return 科目クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */


	public Subject get(String subject_cd,School school) throws Exception {
		Subject subject = new Subject();

	    Connection connection = getConnection();
	    PreparedStatement statement = null;
//	    ResultSet rSet = null;

	    try {

	        // SQL文を作成
	        String sql = "SELECT * FROM subject WHERE subject_cd = ?subject_cd = ? AND school_cd = ?";
	        statement = connection.prepareStatement(sql);
	        statement.setString(1, subject_cd);
	        statement.setString(2, school.getSchool_cd());


		    ResultSet rSet = statement.executeQuery();

		    SchoolDao schoolDao = new SchoolDao();


	        // リザルトセットから学生インスタンスを作成
	        if (rSet.next()) {
	            subject.setSubject_cd(rSet.getString("subject_cd"));
	            subject.setSubject_name(rSet.getString("subject_name"));
	            subject.setSchool(school);

	        }
	        else{
	        	subject= null;
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

	    return subject;
	}

	/**
	 * baseSql:String 共通SQL文 プライベート
	 */
	private String baseSql = "select * from subject where school_cd=? ";


	/**
	 * filterメソッド 学校を指定して科目の一覧を取得する
	 *
	 * @param school:School
	 *            学校

	 * @return 科目のリスト:List<Student> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	public List<Subject> filter(School school) throws Exception {
		//まずはここに処理追加
		List<Subject> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;


        String order = " order by subject_cd asc";

        String conditionIsAttend = "";


        try {
            statement = connection.prepareStatement(baseSql + conditionIsAttend + order);
            statement.setString(1, school.getSchool_cd());

            rSet = statement.executeQuery();
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


	public boolean save (Subject subject) throws Exception {
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;

		try {
			// データベースから科目を取得
			Subject old = get (subject.getSubject_cd(),subject.getSchool());
			if(old == null) {
				// 科目が存在しなかった場合
				// プリペアードステートメンにINSERT文をセットと
				statement = connection. prepareStatement (
				"insert into student (subject_cd,subject_name,school_cd) values(?, ?, ? )");
				// プリペアードステートメントに値をバインド
				statement.setString(1,subject.getSubject_cd()) ;
				statement.setString (2,subject.getSubject_name()) ;
				statement.setString(3,subject.getSchool().getSchool_cd ());
			}else {
				//学生が存在した場合 更新！
				//プリペアードステートメントにUPDATE文をセット
				statement = connection
				.prepareStatement ("update subject set subject_name=?, where subject_cd=? ");
				// プリペアードステートメントに値をバインド
				statement.setString(1,subject.getSubject_name());
				statement. setString (2, subject.getSubject_cd ()) ;

			}

			//プリペアードステートメントを実行
			count = statement.executeUpdate ();

		}catch(Exception e) {
				throw e;
		}finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement. close ();
				}catch (SQLException sqle) {
					throw sqle;
				}
			}
			//コネクションを閉じる
			if (connection != null) {
				try {
					connection.close ();
				}catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		if (count > 0) {
			//実行件数が1件以上ある場合
			return true;
		}else{
		    //実行件数が0件の場合
		    return false;
		}
}

}
