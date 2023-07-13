package shooting;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/*Keyboard入力受け付けクラス*/
public class Keyboard extends KeyAdapter {
	
	//入力しているキーを配列で保存
	public static ArrayList<Integer> presedtButton = new ArrayList<>();
	
//	/*コンストラクタ*/
//	public Keyboard() {
//		presedtButton = new ArrayList<>();
//	}
	
	//キーが押されているか判断するメソッド
	public static boolean iskeyPressd(int keyCode) {
		return presedtButton.contains(keyCode) ;
		
	}

	@Override //キー押下時
	public void keyPressed(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		super.keyPressed(e);
		//リストにキーコードがあるか確認
		if(!presedtButton.contains(e.getKeyCode()))
			//キー押下時配列にそのキー番号が入る様にする
			presedtButton.add(e.getKeyCode());
			
	}

	

	@Override //キー引き上げ時
	public void keyReleased(KeyEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		super.keyReleased(e);
		//キーを離した時は消すだけ
		presedtButton.remove((Integer)e.getKeyCode());
	}

}
