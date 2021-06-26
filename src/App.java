import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    public static void main(String args[]){
      // 接続文字列、ユーザID、パスワード
      String url = "jdbc:postgresql://localhost:5432/postgres";
      String user = "user";
      String password = "user";
  
      Connection conn = null;
      Statement stmt = null;
      ResultSet rset = null;
    
      try {
        //PostgreSQLへ接続
        conn = DriverManager.getConnection(url, user, password);
  
        //SELECT文の実行
        stmt = conn.createStatement();
        String sql = "SELECT 1 * 2";
        rset = stmt.executeQuery(sql);
  
        //SELECT結果の受け取り
        while(rset.next()){
         String col = rset.getString(1);
         System.out.println(col);
        }
  
      } catch (SQLException e){
        e.printStackTrace();
      } finally {
        try {
          if(rset != null)rset.close();
          if(stmt != null)stmt.close();
          if(conn != null)conn.close();
        } catch (SQLException e){
          e.printStackTrace();
        }
      }
   }
}
  
