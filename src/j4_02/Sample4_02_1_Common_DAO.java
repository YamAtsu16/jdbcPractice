package j4_02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Sample4_02_1_Common_DAOクラス
 * @author atsu_yyy
 */
public class Sample4_02_1_Common_DAO {

	//JDBCドライバの相対パス
	String DRIVER_NAME = "org.postgresql.Driver";
	//接続先のデータベース
	String JDBC_URL = "jdbc:postgresql://localhost:5432/sample";
	//接続するユーザー名
	String USER_ID = "postgres";
	//接続するユーザーのパスワード
	String USER_PASS = "postgres";

	/**
	 * テーブルからレコードを取得するメソッド
	 * @param pk
	 * @return Sample4_02_1_Common_DTO
	 */
	public Sample4_02_1_Common_DTO selectMemberInfo(int pk) {
		//JDBCドライバのロード
		try {
			Class.forName(DRIVER_NAME); //JDBCドライバをロード＆接続先として指定
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		//SQL発行
		//JDBCの接続に使用するオブジェクトを宣言
		//※finallyブロックでも扱うためtryブロック内で宣言してはいけないことに注意
		Connection con = null; // Connection（DB接続情報）格納用変数
		PreparedStatement ps = null; // PreparedStatement（SQL発行用オブジェクト）格納用変数
		ResultSet rs = null; // ResultSet（SQL抽出結果）格納用変数

		//抽出データ（Sample4_02_1_Common_DTO型）格納用変数
		//※最終的にreturnするため、tryブロック内で宣言してはいけないことに注意
		Sample4_02_1_Common_DTO dto = null;

		try {
			//接続の確立（Connectionオブジェクトの取得）
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);

			//SQL文の送信 ＆ 結果の取得
			//発行するSQL文の生成（SELECT）
			String sql = "SELECT * FROM employees where id = ?";

			//PreparedStatement（SQL発行用オブジェクト）を生成＆発行するSQLをセット
			ps = con.prepareStatement(sql);

			//パラメータをセット
			ps.setInt(1, pk);

			//SQL文の送信＆戻り値としてResultSet（SQL抽出結果）を取得
			rs = ps.executeQuery();

			//ResultSetオブジェクトから1レコード分のデータをDTOに格納
			if (rs.next()) {
				dto = new Sample4_02_1_Common_DTO();
				dto.setId(rs.getInt("id"));
				dto.setFirstName(rs.getString("first_name"));
				dto.setLastName(rs.getString("last_name"));
				dto.setEmail(rs.getString("email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			//接続の解除
			//ResultSetオブジェクトの接続解除
			if (rs != null) { //接続が確認できている場合のみ実施
				try {
					rs.close(); //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			//PreparedStatementオブジェクトの接続解除
			if (ps != null) { //接続が確認できている場合のみ実施
				try {
					ps.close(); //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			//Connectionオブジェクトの接続解除
			if (con != null) { //接続が確認できている場合のみ実施
				try {
					con.close(); //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		//抽出データを戻す
		return dto;
	}

	/**
	 * insertMemberInfoメソッド
	 * @param dto
	 * @return
	 */
	public boolean insertMemberInfo(Sample4_02_1_Common_DTO dto) {
		//JDBCドライバのロード
		try {
			Class.forName(DRIVER_NAME); //JDBCドライバをロード＆接続先として指定
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		//SQL発行
		//JDBCの接続に使用するオブジェクトを宣言
		//※finallyブロックでも扱うためtryブロック内で宣言してはいけないことに注意
		Connection con = null; // Connection（DB接続情報）格納用変数
		PreparedStatement ps = null; // PreparedStatement（SQL発行用オブジェクト）格納用変数

		//実行結果（真：成功、偽：例外発生）格納用変数
		//※最終的にreturnするため、tryブロック内で宣言してはいけないことに注意
		boolean isSuccess = true;

		try {

			//接続の確立（Connectionオブジェクトの取得）
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);

			//トランザクションの開始
			//オートコミットをオフにする（トランザクション開始）
			con.setAutoCommit(false);

			//SQL文の送信 ＆ 結果の取得
			//発行するSQL文の生成（INSERT）
			String sql = "INSERT INTO employees (id, first_name, last_name, email) VALUES (?, ?, ?, ?)";
			//PreparedStatementオブジェクトを生成＆発行するSQLをセット
			ps = con.prepareStatement(sql);

			//パラメータをセット
			ps.setInt(1, dto.getId());
			ps.setString(2, dto.getFirstName());
			ps.setString(3, dto.getLastName());
			ps.setString(4, dto.getEmail());

			//SQL文の送信＆戻り値として追加件数を取得
			int insCount = ps.executeUpdate();

			//SQL実行結果を表示
			System.out.println("[INFO]" + insCount + "行追加しました");
		} catch (SQLException e) {
			e.printStackTrace();
			
			//実行結果を例外発生として更新
			isSuccess = false;
		} finally {
			//トランザクションの終了
			if (isSuccess) {
				//明示的にコミットを実施
				try {
					con.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				//明示的にロールバックを実施
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			//接続の解除
			//PreparedStatementオブジェクトの接続解除
			if (ps != null) { //接続が確認できている場合のみ実施
				try {
					ps.close(); //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			//Connectionオブジェクトの接続解除
			if (con != null) { //接続が確認できている場合のみ実施
				try {
					con.close(); //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		//実行結果を戻す
		return isSuccess;

	}

	/**
	 * updateMemberInfoメソッド
	 * @param dto
	 * @return
	 */
	public boolean updateMemberInfo(Sample4_02_1_Common_DTO dto) {

		//JDBCドライバのロード
		try {
			Class.forName(DRIVER_NAME); //JDBCドライバをロード＆接続先として指定
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		//JDBCの接続に使用するオブジェクトを宣言
		//※finallyブロックでも扱うためtryブロック内で宣言してはいけないことに注意
		Connection con = null; // Connection（DB接続情報）格納用変数
		PreparedStatement ps = null; // PreparedStatement（SQL発行用オブジェクト）格納用変数

		//実行結果（真：成功、偽：例外発生）格納用変数
		//※最終的にreturnするため、tryブロック内で宣言してはいけないことに注意
		boolean isSuccess = true;

		try {
			//接続の確立（Connectionオブジェクトの取得）
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);

			//トランザクションの開始
			//オートコミットをオフにする（トランザクション開始）
			con.setAutoCommit(false);

			//SQL文の送信 ＆ 結果の取得
			//発行するSQL文の生成（UPDATE）
			String sql = "UPDATE employees SET name = ?, first_name = ?, last_name = ?, email = ?";

			//PreparedStatementオブジェクトを生成＆発行するSQLをセット
			ps = con.prepareStatement(sql);

			//パラメータをセット
			ps.setInt(1, dto.getId());
			ps.setString(2, dto.getFirstName());
			ps.setString(3, dto.getLastName());
			ps.setString(4, dto.getEmail());

			//SQL文の送信＆戻り値として更新件数を取得
			int updCount = ps.executeUpdate();

			//SQL実行結果を表示
			System.out.println("[INFO]" + updCount + "行更新しました");

		} catch (SQLException e) {
			e.printStackTrace();

			//実行結果を例外発生として更新
			isSuccess = false;

		} finally {
			//トランザクションの終了
			if (isSuccess) {
				//明示的にコミットを実施
				try {
					con.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				}

			} else {
				//明示的にロールバックを実施
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			//接続の解除
			//PreparedStatementオブジェクトの接続解除
			if (ps != null) { //接続が確認できている場合のみ実施
				try {
					ps.close(); //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			//Connectionオブジェクトの接続解除
			if (con != null) { //接続が確認できている場合のみ実施
				try {
					con.close(); //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		//実行結果を戻す
		return isSuccess;

	}

	/**
	 * deleteMemberInfoメソッド
	 * @param pk
	 * @return
	 */
	public boolean deleteMemberInfo(int pk) {
		//JDBCドライバのロード
		try {
			Class.forName(DRIVER_NAME); //JDBCドライバをロード＆接続先として指定
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		//SQL発行
		//JDBCの接続に使用するオブジェクトを宣言
		//※finallyブロックでも扱うためtryブロック内で宣言してはいけないことに注意
		Connection con = null; // Connection（DB接続情報）格納用変数
		PreparedStatement ps = null; // PreparedStatement（SQL発行用オブジェクト）格納用変数

		//実行結果（真：成功、偽：例外発生）格納用変数
		//※最終的にreturnするため、tryブロック内で宣言してはいけないことに注意
		boolean isSuccess = true;

		try {
			//接続の確立（Connectionオブジェクトの取得）
			con = DriverManager.getConnection(JDBC_URL, USER_ID, USER_PASS);

			//トランザクションの開始
			//オートコミットをオフにする（トランザクション開始）
			con.setAutoCommit(false);

			//SQL文の送信 ＆ 結果の取得
			//発行するSQL文の生成（DELETE）
			String sql = "DELETE FROM employees where id = ?";

			//PreparedStatementオブジェクトを生成＆発行するSQLをセット
			ps = con.prepareStatement(sql);

			//パラメータをセット
			ps.setInt(1, pk); //第1パラメータ：削除対象ID

			//SQL文の送信＆戻り値として削除件数を取得
			int delCount = ps.executeUpdate();

			//SQL実行結果を表示
			System.out.println("[INFO]" + delCount + "行削除しました");

		} catch (SQLException e) {
			e.printStackTrace();

			//実行結果を例外発生として更新
			isSuccess = false;

		} finally {
			//トランザクションの終了
			if (isSuccess) {
				//明示的にコミットを実施
				try {
					con.commit();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				//明示的にロールバックを実施
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			//接続の解除
			//PreparedStatementオブジェクトの接続解除
			if (ps != null) { //接続が確認できている場合のみ実施
				try {
					ps.close(); //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			//Connectionオブジェクトの接続解除
			if (con != null) { //接続が確認できている場合のみ実施
				try {
					con.close(); //接続の解除
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		//実行結果を戻す
		return isSuccess;
	}
}