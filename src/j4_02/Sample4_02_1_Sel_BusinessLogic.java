package j4_02;

/**
 * Sample4_02_1_Sel_BusinessLogicクラス
 * @author atsu_yyy
 */
public class Sample4_02_1_Sel_BusinessLogic {

	/**
	 * 対象のユーザー情報を抽出し、コマンドライン上に表示する
	 * @param targetUserId 対象のユーザーID
	 */
	public void extract(int targetUserId) {

		// DAOクラスをインスタンス化＆指定のIDと合致するデータを抽出するよう依頼
		Sample4_02_1_Common_DAO dao = new Sample4_02_1_Common_DAO();
		Sample4_02_1_Common_DTO extractedDto = dao.selectMemberInfo(targetUserId);

		// データ出力
		if (extractedDto != null) {
			System.out.println("**************取得結果***************");
			System.out.println("*ID：" + extractedDto.getId());
			System.out.println("*名前：" + extractedDto.getFirstName());
			System.out.println("*苗字：" + extractedDto.getLastName());
			System.out.println("*メール：" + extractedDto.getEmail());
			System.out.println("*************************************");
		} else {
			System.out.println("[INFO]該当のユーザー情報を取得できませんでした");
		}
	}
}