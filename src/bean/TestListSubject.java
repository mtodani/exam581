package bean;

import java.util.Map;

public class TestListSubject implements java.io.Serializable{

	/* 宣言 */
	private int entYear;
	private String studentNo;
	private String studentName;
	private String classNum;
	private Map<Integer,Integer> points;

	/* ゲッター、セッター */
	public int getEntYear() {
		return entYear;
	}
	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}
	public String getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getClassNum() {
		return classNum;
	}
	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}
	public Map<Integer, Integer> getPoints() {
		return points;
	}
	public void setPoints(Map<Integer, Integer> points) {
		this.points = points;
	}

	/**
	 * getPointメソッド 得点マップから値を取得する
	 *
	 * @param key:int
	 * 			回数
	 * @return 得点:String
	 */
	public String getPoint(int key) {
		// 得点マップから値を取得
		Integer k = points.get(key);
		if(k == null) {
			// 得点マップに値が存在しなかった場合、"-"を返却
			return "-";
		} else {
			// 得点マップに値が存在した場合、文字列で得点を返却
			return k.toString();
		}
	}

	/**
	 * putPointメソッド 得点マップに値を格納する
	 *
	 * @param key:int
	 * @param value:int
	 */
	public void putPoint(int key, int value) {
		points.get(key);
	}

}
