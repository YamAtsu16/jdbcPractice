package j4_01;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Sample4_01_1 {

    public static void main(String[] args) {
        // JDBC URL, ユーザ名, パスワード
        String jdbcUrl = "jdbc:postgresql://localhost:5432/sample"; // データベースのURLを設定
        String user = "postgres"; // データベースユーザ名を設定
        String password = "postgres"; // データベースパスワードを設定
        Connection connection = null;

        try {
            // JDBCドライバの読み込み
            Class.forName("org.postgresql.Driver");

            // データベースへの接続
            connection = DriverManager.getConnection(jdbcUrl, user, password);

            if (connection != null) {
                System.out.println("データベースに接続しました");

                // テーブルが存在するかチェック
                if (!tableExists(connection, "employees")) {
                    // テーブルを作成するSQL文
                    String createTableSQL = "CREATE TABLE employees ("
                            + "id serial PRIMARY KEY,"
                            + "first_name VARCHAR(255),"
                            + "last_name VARCHAR(255),"
                            + "email VARCHAR(255)"
                            + ")";

                    // SQL文を実行
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(createTableSQL);
                }

                // テーブルにデータを挿入するSQL文
                String insertDataSQL = "INSERT INTO employees (first_name, last_name, email) VALUES (?, ?, ?)";

                // PreparedStatementを作成
                PreparedStatement preparedStatement = connection.prepareStatement(insertDataSQL);

                // サンプルデータを挿入
                preparedStatement.setString(1, "John");
                preparedStatement.setString(2, "Doe");
                preparedStatement.setString(3, "john.doe@example.com");
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "Jane");
                preparedStatement.setString(2, "Smith");
                preparedStatement.setString(3, "jane.smith@example.com");
                preparedStatement.executeUpdate();

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
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // テーブルが存在するかチェックするメソッド
    private static boolean tableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(null, null, tableName, null);
        return tables.next();
    }
}