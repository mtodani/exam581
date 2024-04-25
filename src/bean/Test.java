package bean;

public class Test implements java.io.Serializable{

	// 宣言
	private String student_no;
	private String class_Num;
	private String student;
	private School school;
	private int no;
	private int point;

	// ゲッター、セッター

	public String getStudent_no() {
		return student_no;
	}
	public void setStudent_no(String student_no) {
		this.student_no = student_no;
	}
	public String getClass_Num() {
		return class_Num;
	}
	public void setClass_Num(String class_Num) {
		this.class_Num = class_Num;
	}
	public String getStudent() {
		return student;
	}
	public void setStudent(String student) {
		this.student = student;
	}
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}

}
