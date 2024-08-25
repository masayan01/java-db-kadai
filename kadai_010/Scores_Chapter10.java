package kadai_010;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Scores_Chapter10 {
    public static void main(String[] args){
        Connection con = null;
        Statement statement = null;

        try{

            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                 "masanari0806");

                 System.out.println("データベース接続成功");
                
                 statement = con.createStatement();
                 String sql = "UPDATE scores SET score_math = 95, score_english = 80 WHERE id = 5;";
                 
                 System.out.println("レコードの更新を実行します");
                 int rowCnt = statement.executeUpdate(sql);
                 System.out.println(rowCnt + "件のレコードを更新しました");

                 String sql1 = """
                 SELECT * FROM scores 
                 ORDER BY
                  score_math DESC,
                  score_english DESC
                 """;

                 ResultSet result = statement.executeQuery(sql1);
                System.out.println("数学・英語の点数が高い順に並べ替えました");
                 while (result.next()) {
                    int id = result.getInt("id");
                    String name =result.getString("name");
                    int math = result.getInt("score_math");
                    int eng = result.getInt("score_english");
                    System.out.println(result.getRow() + "件目：生徒ID=" + id + "/氏名=" + name + "/数学=" + math + "/英語=" + eng);
                 }

        }catch(SQLException e) {
            System.out.println("エラー発生：" + e.getMessage());
        } finally {
            if( statement != null ) {
                try { statement.close(); } catch(SQLException ignore) {}
            }
            if( con != null ) {
                try { con.close(); } catch(SQLException ignore) {}
    }
    
}
}}