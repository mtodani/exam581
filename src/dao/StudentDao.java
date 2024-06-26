package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao  extends Dao{

	/**
	 * getメソッド 学生番号を指定して学生インスタンスを1件取得する
	 *
	 * @param no:String
	 *            学生番号
	 * @return 学生クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */


	public Student get(String no) throws Exception {
	    Student student = new Student();

	    Connection connection = getConnection();
	    PreparedStatement statement = null;
//	    ResultSet rSet = null;

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



	/**
	 * baseSql:String 共通SQL文 プライベート
	 */
	private String baseSql = "select * from student where school_cd=? ";

	/**
	 * postFilterメソッド フィルター後のリストへの格納処理 プライベート
	 *
	 * @param rSet:リザルトセット
	 * @param school:School
	 *            学校
	 * @return 学生のリスト:List<Student> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
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

	/**
	 * filterメソッド 学校、入学年度、クラス番号、在学フラグを指定して学生の一覧を取得する
	 *
	 * @param school:School
	 *            学校
	 * @param entYear:int
	 *            入学年度
	 * @param classNum:String
	 *            クラス番号
	 * @param isAttend:boolean
	 *            在学フラグ
	 * @return 学生のリスト:List<Student> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
		//まずはここに処理追加
		List<Student> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        String condition = "and ent_year=? and class_num=?";
        String order = " order by student_no asc";

        String conditionIsAttend = "";
        if (isAttend) {
            conditionIsAttend = "and is_attend=true";
        }

        try {
            statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
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

	/**
	 * filterメソッド 学校、入学年度、在学フラグを指定して学生の一覧を取得する
	 *
	 * @param school:School
	 *            学校
	 * @param entYear:int
	 *            入学年度
	 * @param isAttend:boolean
	 *            在学フラグ
	 * @return 学生のリスト:List<Student> 存在しない場合は0件のリスト
	 * @throws Exception
	 */
	public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
		//まずはここに処理追加
		List<Student> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;
        String condition = "and ent_year=?";
        String order = " order by student_no asc";
        // 在学フラグ
        String conditionIsAttend = "";
        if (isAttend) {
            conditionIsAttend = "and is_attend=true";
        }
        try {
            // プリペアドステートメントを実行
            statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
            statement.setString(1, school.getSchool_cd());
            statement.setInt(2, entYear);
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
	public List<Student> filter(School school, boolean isAttend) throws Exception {
		//まずはここに処理追加
		List<Student> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;
        String order = " order by student_no asc";

        // SQL文の作成
        String conditionIsAttend = "";
        if (isAttend) {
            conditionIsAttend = "and is_attend=true";
        }

        try {
            statement = connection.prepareStatement(baseSql + conditionIsAttend + order);
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



	/**
	 * saveメソッド 学生インスタンスをデータベースに保存する データが存在する場合は更新、存在しない場合は登録
	 *
	 * @param student：Student
	 *            学生
	 * @return 成功:true, 失敗:false
	 * @throws Exception
	 */
//	public boolean save(Student student) throws Exception {
//		//次はここに処理追加
//	}

//	public boolean save(Student student) throws Exception {
//	    boolean result = false;
//	    Connection connection = null;
//	    PreparedStatement statement = null;
//
//	    try {
//	        // データベース接続を取得
//	        connection = getConnection();
//
//	        // SQLを作成
//	        String sql = "INSERT INTO student (student_no, student_name, ent_year, class_num, is_attend, school_cd) " +
//	                     "VALUES (?, ?, ?, ?, ?, ?) " +
//	                     "ON DUPLICATE KEY UPDATE student_name = ?, ent_year = ?, class_num = ?, is_attend = ?";
//
//	        statement = connection.prepareStatement(sql);
//	        statement.setString(1, student.getStudent_no());
//	        statement.setString(2, student.getStudent_name());
//	        statement.setInt(3, student.getEntYear());
//	        statement.setString(4, student.getClass_num());
//	        statement.setBoolean(5, student.isAttend());
//	        statement.setString(6, student.getSchool().getSchool_cd());
//	        statement.setString(7, student.getStudent_name());
//	        statement.setInt(8, student.getEntYear());
//	        statement.setString(9, student.getClass_num());
//	        statement.setBoolean(10, student.isAttend());
//
//	        // SQLを実行
//	        int rows = statement.executeUpdate();
//	        result = rows > 0;
//	    } catch (SQLException e) {
//	        e.printStackTrace();
//	        throw e;
//	    } finally {
//	        // リソースを閉じる
//	        if (statement != null) {
//	            try {
//	                statement.close();
//	            } catch (SQLException e) {
//	                e.printStackTrace();
//	            }
//	        }
//	        if (connection != null) {
//	            try {
//	                connection.close();
//	            } catch (SQLException e) {
//	                e.printStackTrace();
//	            }
//	        }
//	    }
//
//	    return result;
//	}

	public boolean save (Student student) throws Exception {
		//コネクションを確立
		Connection connection = getConnection();
		//プリペアードステートメント
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;

		try {
			// データベースから学生を取得
			Student old = get (student.getStudent_no());
			if(old == null) {
				// 学生が存在しなかった場合
				// プリペアードステートメンにINSERT文をセットと
				statement = connection. prepareStatement (
				"insert into student (student_no,student_name,ent_year,class_num,is_attend,school_cd) values(?, ?, ?, ?, ?, ?)");
				// プリペアードステートメントに値をバインド
				statement.setString(1,student.getStudent_no()) ;
				statement.setString (2,student.getStudent_name()) ;
				statement.setInt (3,student.getEntYear()) ;
				statement.setString (4,student.getClass_num()) ;
				statement.setBoolean(5,student.isAttend());
				statement.setString(6,student.getSchool().getSchool_cd ());
			}else {
				//学生が存在した場合 更新！
				//プリペアードステートメントにUPDATE文をセット
				statement = connection
				.prepareStatement ("update student set student_name=?,ent_year=?,class_num=?,is_attend=? where student_no=? ");
				// プリペアードステートメントに値をバインド
				statement.setString(1,student.getStudent_name());
				statement.setInt(2,student.getEntYear());
				statement. setString (3, student.getClass_num());
				statement. setBoolean(4, student.isAttend());
				statement. setString (5, student.getStudent_no ()) ;

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
