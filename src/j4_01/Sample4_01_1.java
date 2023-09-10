package j4_01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Sample4_01_1 {

	public static void main(String[] args) {
		   // JDBC URL, ユーザ名, パスワード
        String jdbcUrl = "jdbc:postgresql://localhost:5432/sample"; // データベースのURLを設定
        String user = "postgres"; // データベースユーザ名を設定
        String password = "postgres"; // データベースパスワードを設定

        try {
            // JDBCドライバの読み込み
            Class.forName("org.postgresql.Driver");

            // データベースへの接続
            Connection connection = DriverManager.getConnection(jdbcUrl, user, password);

            if (connection != null) {
                System.out.println("データベースに接続しました");
                // ここでデータベース操作を行う
                // 例: データの取得、更新、挿入など

                // 接続を閉じる
                connection.close();
                System.out.println("データベース接続を閉じました");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("JDBCドライバが見つかりませんでした");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("データベースに接続できませんでした");
            e.printStackTrace();
        }
    }
}
