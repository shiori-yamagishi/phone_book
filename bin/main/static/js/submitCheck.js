function submitChk() {
	/* 確認ダイアログ表示 */
	var flag = confirm("処理を実行してよろしいですか？");
	/* send_flg が TRUEなら送信、FALSEなら送信しない */
	return flag;
}