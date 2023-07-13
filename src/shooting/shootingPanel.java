package shooting;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


/*GameのPanelクラス（Frameクラスで利用）*/
public class shootingPanel extends JPanel {
	
	/*フィールド*/
	public BufferedImage image;
	
	/*コンストラクタ*/
	public shootingPanel() {
		super();
		//Panelの変数を初期化
		this.image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_BGR);
	}

	//描写クラス
	@Override
	public void paint(Graphics g) {
		// TODO 自動生成されたメソッド・スタブ
		super.paint(g);
		//画像を描写
		g.drawImage(image,0,0,this);
	}
	
	//結果を表示するメソッド(画面更新)
	public void draw() {
		this.repaint();
		
	}
	
	
	
	
}