package shooting;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/*GameのWindowクラス*/
public class shootingFrame extends JFrame {
	
	/*フィールド*/
	public shootingPanel panel;
	
	/*コンストラクタ*/
	public shootingFrame() {
		
		//インスタンスを生成
		panel = new shootingPanel();
		//JFrameに挿入(Windowに追加)
		this.add(panel);
		
		//WIndowイベントを設定
		this.addWindowListener(new WindowAdapter() {
			
			//処理内容
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO 自動生成されたメソッド・スタブ
				super.windowClosed(e);
				//ループがtrueの間は描写をする
				shooting.loop=true;
			}			
		});
		
		//keyboardのインスタンスを追加
		this.addKeyListener(new Keyboard());
	
		
		/*下記全てWindowクラスのメソッド*/		
		//ウィンドウを閉じたときにプログラムを終了
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//タブに表示される
		this.setTitle("Shooting");
		//Windowのsize
		this.setSize(500,500);
		//ReSize不可能
		this.setResizable(false);
		// ウィンドウの表示場所を規定
		this.setLocationRelativeTo(null);
		//Windowの可視化
		this.setVisible(true);
	} 

}
