package kadai_007;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Posts_Chapter {
    public static void main(String[] args){
        Connection con = null;
        PreparedStatement statement = null;

        String[][] list ={
            {"1003","2023-02-08","昨日の夜は徹夜でした","13"},
            {"1002","2023-02-08","お疲れ様です！","12"},
            {"1003","2023-02-09","今日も頑張ります","18"},
            {"1001","2023-02-09","無理は禁物ですよ！","17"},
            {"1002","2023-02-10","明日から連休ですね！","20"}
        };

        try{

            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root", 
                "masanari0806");

                System.out.println("データベース接続成功");

            String sql = "INSERT INTO posts(user_id, posted_at, post_content, likes) VALUES (?,?,?,?);";
                statement =con.prepareStatement(sql);

                int rowCnt = 0; 
            for(int i= 0; i < list.length; i++){
                statement.setInt(1,Integer.parseInt(list[i][0]));
                java.sql.Date sqlDate = java.sql.Date.valueOf(list[i][1]);
                statement.setDate(2,sqlDate);
                statement.setString(3,list[i][2]);
                statement.setInt(4,Integer.parseInt(list[i][3]));

                rowCnt += statement.executeUpdate();
            }
            System.out.println(rowCnt + "件のレコードを追加しました");

            Statement statement1 = con.createStatement();
            String sql1 = "SELECT * FROM posts WHERE user_id = 1002;";
            System.out.println("ユーザーIDが1002のレコードを検索しました");
            ResultSet result = statement1.executeQuery(sql1);

            while(result.next()){
                String pat = result.getString("posted_at");
                String pcon = result.getString("post_content");
                int like = result.getInt("likes");

                System.out.println(result.getRow() + "件目：投稿日時＝" + pat + "/投稿内容＝" + pcon + "/いいね数＝" + like);
            }
            }catch(SQLException e){
                System.out.println("エラー発生：" + e.getMessage());
            }finally{
                if(statement !=null){
                    try{statement.close();}catch(SQLException ignore){}
                }
                if(con != null){
                    try{con.close();}catch(SQLException ignore){}
                }
            }
    }
}

