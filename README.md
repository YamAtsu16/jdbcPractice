# JDBCの学習用

## 1. 学習内容
- JDBCドライバのインストール
- DBへの接続と解除方法
- SQL文の発行
- トランザクション管理

## 2. 主な使用クラス
### Connectionクラス
- DriverManager.getConnectionメソッド
- DBのURL、ユーザ名、パスワードを指定し、DBへ接続する

### PreparedStatement
- SQL文の発行
- set〇〇メソッドでパラメータをセット可能
- executeQueryでSELECT文の結果、executeUpdateでSELECT文以外の結果を取得

### ResultSetクラス
- SQLの取得結果を格納
- nextメソッドで1レコードずつ、レコードを取得
