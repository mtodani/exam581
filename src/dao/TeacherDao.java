package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.School;
import bean.Teacher;

public class TeacherDao extends Dao {

    // ログイン処理
    public Teacher login(String id, String password) throws Exception{

    	Teacher teacher = null;

    	//school_cdによってJOIN SCHOOLさせて、SCHOOL_nameがゲットできるように
        String sql = "SELECT * FROM TEACHER JOIN SCHOOL ON TEACHER.SCHOOL_CD = SCHOOL.SCHOOL_CD where teacher_id = ? and password = ?";

        Connection conn = getConnection();
	    PreparedStatement stmt = conn.prepareStatement(sql);
	    stmt.setString(1, id);
	    stmt.setString(2, password);

        ResultSet rs = stmt.executeQuery();

        if (rs.next()) { // 認証成功の場合（該当アカウントがあった場合）

            String teacherName = rs.getString("TEACHER_NAME");
            String schoolCd = rs.getString("SCHOOL_CD");
            String schoolName = rs.getString("SCHOOL_NAME");

            // Teacherオブジェクトを作成して設定
            teacher = new Teacher();
            teacher.setTeacher_id(id);
            teacher.setPassword(password);
            teacher.setTeacher_name(teacherName);


            // Schoolオブジェクトを作成して設定
            School school = new School();
            school.setSchool_cd(schoolCd);
            school.setSchool_name(schoolName);

            teacher.setSchool(school);

            // 認証済みフラグを設定
            teacher.setAuthenticated(true);
        }


        //ログインされたteacherのデータを返す
        return teacher;

    }

}
