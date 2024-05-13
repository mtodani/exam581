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
	        String sql = "SELECT * FROM subject WHERE subject_cd = ? AND school_cd = ?";
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
	            subject.setSubject_now(rSet.getBoolean("subject_now"));

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
	 * postFilterメソッド フィルター後のリストへの格納処理 プライベート
	 *
	 * @param rSet:リザルトセット
	 * @param school:School
	 *            学校
	 * @return 科目のリスト:List<Subject> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	private List<Subject> postFilter(ResultSet rSet,School school) throws Exception {
		//まずはここに処理追加
		 // リストを初期化
	    List<Subject> list = new ArrayList<>();

	    try {
	        // リザルトセットを全権走査
	        while (rSet.next()) {
	            // 科目インスタンスを初期化
	        	Subject subject = new Subject();

	            // 科目インスタンスに検索結果をセット
	        	subject.setSubject_cd(rSet.getString("subject_cd"));
	        	subject.setSubject_name(rSet.getString("subject_name"));
	            subject.setSchool(school);
	            subject.setSubject_now(rSet.getBoolean("subject_now"));

	            // リストに追加
	            list.add(subject);
	        }
	    } catch (SQLException | NullPointerException e) {
	        e.printStackTrace();
	    }

	    return list;
	}

	/**
	 * filterメソッド 学校、在学フラグを指定して学生の一覧を取得する
	 *
	 * @param school:School
	 *            学校
	 * @param isAttend:boolean
	 *            在学フラグ
	 * @return 学生のリスト:List<Student> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	public List<Subject> filter(School school) throws Exception {
		//まずはここに処理追加
		List<Subject> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;
        String condition_subject_now = "and subject_now=true";
        String order = " order by subject_cd asc";

        // SQL文の作成

        try {
            statement = connection.prepareStatement(baseSql + condition_subject_now + order);
            statement.setString(1, school.getSchool_cd());
            rSet = statement.executeQuery();

            list = postFilter(rSet, school);
        } catch (Exception e) {
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
				"insert into subject (subject_cd,subject_name,school_cd,subject_now) values(?, ?, ? ,?)");
				// プリペアードステートメントに値をバインド
				statement.setString(1,subject.getSubject_cd()) ;
				statement.setString (2,subject.getSubject_name()) ;
				statement.setString(3,subject.getSchool().getSchool_cd ());
				statement.setBoolean(4,subject.isSubject_now());
			}else {
				//学生が存在した場合 更新！
				//プリペアードステートメントにUPDATE文をセット
				statement = connection
				.prepareStatement ("update subject set subject_name=? where subject_cd=? ");
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
	public boolean delete (Subject subject) throws Exception {
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		//実行回数
		int count = 0;

		try {

			//プリペアードステートメントにUPDATE文をセット
			statement = connection
			.prepareStatement ("update subject set subject_now=? where subject_cd=? ");
			// プリペアードステートメントに値をバインド
			statement.setBoolean(1,subject.isSubject_now());
			statement. setString (2, subject.getSubject_cd ()) ;


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
