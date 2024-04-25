package dao;

import java.sql.ResultSet;
import java.util.List;

import javax.security.auth.Subject;

import bean.School;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao{

	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {

	}

	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {

	}

}
