package scoremanager.main;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession(); // セッション
        SubjectDao subDao = new SubjectDao(); // 科目Dao
        String subject_cd = ""; // 科目コード
        String subject_name = ""; // 科目名
        Subject subject = null; // 科目
        Map<String, String> errors = new HashMap<>(); // エラーメッセージ
        Teacher teacher = (Teacher) session.getAttribute("user"); // ログインユーザーを取得

        // リクエストパラメータの取得
        subject_cd = req.getParameter("subject_cd"); // 科目コード
        subject_name = req.getParameter("subject_name"); // 科目名

        // 全角チェックの正規表現パターン
        Pattern fullWidthPattern = Pattern.compile("[^\\u0020-\\u007E]");

        // 全角チェック
        if (fullWidthPattern.matcher(subject_cd).find()) {
            errors.put("subject_cd", "科目コードは半角で入力してください。");
        }

        // DBからデータ取得
        subject = subDao.get(subject_cd, teacher.getSchool()); // 科目コードと学校コードから科目インスタンスを取得

        // ビジネスロジック
        if (subject == null && errors.isEmpty()) { // 科目が未登録だった場合
            // 科目インスタンスを初期化
            subject = new Subject();
            // インスタンスに値をセット
            subject.setSubject_cd(subject_cd);
            subject.setSubject_name(subject_name);
            subject.setSchool(teacher.getSchool());
            subject.setSubject_now(true);

            if (subject_cd.length() == 3) {
                // 科目を保存
                subDao.save(subject);
            } else {
                // subject_cdが3文字でない場合
                errors.put("subject_cd", "科目コードが3文字になっていません");
            }
        } else if (subject != null) { // 入力された科目コードがDBに保存されていた場合
            errors.put("subject_cd", "科目コードが重複しています");
        }

        // エラーがあった場合、更新画面へ戻る
        if (!errors.isEmpty()) {
            // リクエスト属性をセット
            req.setAttribute("errors", errors);
            req.setAttribute("subject_cd", subject_cd);
            req.setAttribute("subject_name", subject_name);
            req.getRequestDispatcher("subject_create.jsp").forward(req, res);
            return;
        }

        // 成功した場合、完了画面へフォワード
        req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
    }
}


