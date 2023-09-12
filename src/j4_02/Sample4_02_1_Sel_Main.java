package j4_02;

/**
 * Sample4_02_1_Sel_Mainクラス
 * @author atsu_yyy
 */
public class Sample4_02_1_Sel_Main {
	public static void main (String[] args) {

		//コマンドラインから情報抽出したいユーザーのIDを受け取る
		int extractionId = Integer.parseInt(args[0]);

		//ビジネスロジック（ユーザーの情報抽出）クラスのインスタンス化＆メソッド起動
		Sample4_02_1_Sel_BusinessLogic blSel = new Sample4_02_1_Sel_BusinessLogic();
		blSel.extract( extractionId );

	}
}
