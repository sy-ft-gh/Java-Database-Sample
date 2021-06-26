import java.util.ArrayList;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.postgresql.geometric.PGpoint;

public class DataSelect {
    public static void main(String[] args) {
        // コンソール入力で下限とする降水量を指定
        float lowerprcp = -1;
        try (Scanner sc1 = new Scanner(System.in)) {
        	do {
                System.out.println("下限の降水量を入力してください。");
                String ln = sc1.nextLine();
                if (ln == null || ln.contentEquals("")) {
                    lowerprcp = 0;
                } else {
                    try {
                        lowerprcp = Float.parseFloat(ln);
                    } catch (NumberFormatException e) {
                        System.out.println("降水量は小数値で入力してください。");
                    }
                }
            } while(lowerprcp == -1);
        }
        // 接続文字列、ユーザID、パスワード
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String user = "user";
        String password = "user";

        //SELECT文の作成
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT weather.city, weather.temp_lo, weather.temp_hi,weather.prcp, weather.date, cities.location ")
            .append("FROM weather INNER JOIN cities ON (weather.city = cities.name) ");
        if (lowerprcp > 0) {
            sql.append("WHERE weather.prcp >= ").append(lowerprcp).append(" ");
        }
        sql.append("ORDER BY weather.date ");
  
        ArrayList<WeatherInfo> weathers = new ArrayList<WeatherInfo>();
        try (Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement()) {
            ResultSet rset = stmt.executeQuery(sql.toString());
            //SELECT結果の受け取り
            while(rset.next()){
                WeatherInfo wi = new WeatherInfo();
    
                wi.City = rset.getString("city");
                wi.TempLo = rset.getInt("temp_lo");
                wi.TempHi = rset.getInt("temp_hi");
                wi.Prcp = rset.getFloat("prcp");
                wi.date = rset.getDate("date");
                wi.location = (PGpoint)rset.getObject("location");

                weathers.add(wi);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        // 一覧をコンソール出力
        weathers.stream().forEach(wi -> {
            String msg = "city:" + wi.City + ",temp_lo:" + wi.TempLo 
                  + ",temp_hi:" + wi.TempHi + ",prcp:" + wi.Prcp
                  + ",date:" + wi.date
                  + ",location:" + wi.location;
            System.out.println(msg);
            }
        );
    }
}
