package bean;

public class Subject implements java.io.Serializable{

	private String subject_cd;
	private String subject_name;
	private School school;


	//ゲッター・セッター
	public String getSubject_cd() {
		return subject_cd;
	}
	public void setSubject_cd(String subject_cd) {
		this.subject_cd = subject_cd;
	}
	public String getSubject_name() {
		return subject_name;
	}
	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}

}
