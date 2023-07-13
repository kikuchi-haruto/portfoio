package shooting;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

/*Mainクラス*/
public class shooting {
	
	/*フィールド*/
	//shootingFrameの変数を作成
	public static shootingFrame shootingFrame;
	//ループを終了出来る様にする変数
	public static boolean loop;
	
	
	public static void main(String[] args) {
		//shootingFrameのインスタンスを生成
		shootingFrame = new shootingFrame();
		
		loop = true;
		
		Graphics gra = shootingFrame.panel.image.getGraphics();
		
		/* FPS */
		long startTime;
		long fpsTime = 0;
		int fps = 30;
		int FPS = 0;
		int FPSCount = 0;
		
		/* GAME */
		int playerX=0,playerY=0;
		//弾の発射感覚
		int bulletInterval=0; 
		//敵と弾を配列に保存
		ArrayList<Bullet> bullets_player = new ArrayList<>();
		ArrayList<Bullet> bullets_enemy = new ArrayList<>();
		ArrayList<Enemy> enemies = new ArrayList<>();
		//敵をランダムに発生させる
		Random random = new Random();
		//スコア
		int score = 0;
	
		
		//列挙型を利用
		EnumShootingScreen  screen = EnumShootingScreen.START;
		while(loop){
			
			//FPSを計測（1秒間に何回表示されたか）
			if((System.currentTimeMillis() - fpsTime) >= 1000) {
				fpsTime = System.currentTimeMillis();
				FPS = FPSCount;
				FPSCount = 0;
				System.out.println(FPS);
			}
			FPSCount++;
			//背景
			gra.setColor(Color.WHITE);
			gra.fillRect(0, 0, 500, 500);
			
			switch(screen) {
			//Strat画面の処理
				case START:
				//タイトル
				gra.setColor(Color.BLACK);	
				Font font = new Font("SansSelif",Font.PLAIN,40);
				gra.setFont(font);
				FontMetrics metrics = gra.getFontMetrics(font);
				gra.drawString( "Shooting",250-(metrics.stringWidth("Shooting")/2),100);
				
				//ルール説明
				font = new Font("SansSelif",Font.PLAIN,20);
				gra.setFont(font);
				metrics = gra.getFontMetrics(font);
				gra.drawString( "方向キーで移動、spaceキーで発射",250-(metrics.stringWidth("方向キーで移動、spaceキーで発射")/2),200);
				
				
				//画面遷移の説明	
				font = new Font("SansSelif",Font.PLAIN,20);
				gra.setFont(font);
				metrics = gra.getFontMetrics(font);
				gra.drawString( "Press SPACE to START",250-(metrics.stringWidth("Press SPACE to START")/2),300);
				
				//スペースキーが押されているか判定　（押されていれば画面遷移）
				if(Keyboard.iskeyPressd(KeyEvent.VK_SPACE)) {
					screen = EnumShootingScreen.GAME;
					//Game開始時に配列を初期化
					bullets_player = new ArrayList<>();
					bullets_enemy = new ArrayList<>();
					enemies = new ArrayList<>();
					playerX = 250;
					playerY = 350;
					//スコアの初期化
					score = 0;
				}
				break;
				
				
				//GAME画面の処理
				case GAME:
					//playerの表示
					gra.setColor(Color.BLUE);
					//playerの形と初期位置
					gra.fillRect(playerX+10, playerY, 10,10);
					gra.fillRect(playerX, playerY+10, 30,10);
					
					//弾の可視化 配列のループ
					for(int i= 0; i<bullets_player.size(); i++) {
						Bullet bullet =bullets_player.get(i);
						
						//弾を描写
						gra.setColor(Color.BLUE);
						gra.fillRect(bullet.x,bullet.y,5,5);
						//弾を飛ばす
						bullet.y -= 10;
						//弾が画面外に行ったら消す処理
						if(bullet.y<0) bullets_player.remove(i);
						
						//敵への当たり判定
						for(int l= 0; l<enemies.size(); l++) {
							Enemy enemy = enemies.get(l);
							if(bullet.x >= enemy.x && bullet.x <= enemy.x+30 && 
									bullet.y >= enemy.y && bullet.y <= enemy.y+20) {
								enemies.remove(l);
								score += 10;
								
							}
						}
						
						

					}
					
					//Enemyの表示
					gra.setColor(Color.RED);					
					//Enemyの移動処理
					for(int i= 0; i<enemies.size(); i++) {
						Enemy enemy = enemies.get(i);
						gra.fillRect(enemy.x,enemy.y,30,10);
						gra.fillRect(enemy.x+10,enemy.y+10,10,10);						
						enemy.y += 5;					
						if(enemy.y > 500) enemies.remove(i);

						//1なら弾を発射
						if(random.nextInt(30)==1) 
							bullets_enemy.add(new Bullet(enemy.x,enemy.y));
						
						//敵の自分への当たり判定
						if(enemy.x >= playerX && enemy.x <=playerX+30 && 
								enemy.y >= playerY && enemy.y <= playerY+20) {
							//画面遷移
							screen = EnumShootingScreen.GAMEOVER;
						}

					}
					//敵の発生処理
					if(random.nextInt(10)==1) 
						enemies.add(new Enemy(random.nextInt(470),10));
					
					//弾の可視化 配列のループ
					
					for(int i= 0; i<bullets_enemy.size(); i++) {
						Bullet bullet =bullets_enemy.get(i);
						//弾を描写
						gra.setColor(Color.RED);
						gra.fillRect(bullet.x,bullet.y,5,5);
						//弾を飛ばす
						bullet.y += 10;
						//弾が画面外に行ったら消す処理
						if(bullet.y > 500) bullets_enemy.remove(i);
						//弾の自分への当たり判定
						if(bullet.x >= playerX && bullet.x <=playerX+30 && 
								bullet.y >= playerY && bullet.y <= playerY+20) {
							//画面遷移
							screen = EnumShootingScreen.GAMEOVER;
						}
					}
					
					
					//playerの移動処理
					if(Keyboard.iskeyPressd(KeyEvent.VK_LEFT)&&playerX>0) {
						//←キー押下時playerX座標はマイナスに動く
						playerX-=10;
					}
					//他のキーも追加
					if(Keyboard.iskeyPressd(KeyEvent.VK_RIGHT)&&playerX<450) playerX+=10;
					if(Keyboard.iskeyPressd(KeyEvent.VK_UP)&&playerY>30) playerY-=10;
					if(Keyboard.iskeyPressd(KeyEvent.VK_DOWN)&&playerY<450) playerY+=10;
					
					//弾発射の処理
					if(Keyboard.iskeyPressd(KeyEvent.VK_SPACE)&&bulletInterval==0) {
						//弾の配列に弾のインスタンスを格納
						bullets_player.add(new Bullet(playerX + 10,playerY));
						bulletInterval=5; 
						
					}
					//弾の発射感覚
					if(bulletInterval>0)bulletInterval--;
					
					//スコアの表示
					gra.setColor(Color.BLACK);
					font = new  Font("SansSerif",Font.PLAIN, 20);
					metrics = gra.getFontMetrics(font);
					gra.setFont(font);				
					gra.drawString("SCORE:"+score, 470-metrics.stringWidth("SCORE:"+score), 430);
					
				break;
				
				//GAMEOVER画面の処理						
				case GAMEOVER:
					//Shootingを描写
					gra.setColor(Color.BLACK);	
					font = new Font("SansSelif",Font.PLAIN,40);
					gra.setFont(font);
					//Fontのサイズを取得
					metrics = gra.getFontMetrics(font);
					//中央に文字を描写( ここが配置位置の設定)
					gra.drawString( "Game Over",250-(metrics.stringWidth("Game Over")/2),100);
					
					//retry.Scoreを描写	
					font = new Font("SansSelif",Font.PLAIN,20);
					gra.setFont(font);
					//Fontのサイズを取得スコア
					metrics = gra.getFontMetrics(font);
					//スコアの表示
					gra.drawString("SCORE:"+score, 470-metrics.stringWidth("SCORE:"+score), 430);
					
					gra.drawString( "Press Esc to RETRY",250-(metrics.stringWidth("Press SPACE to START")/2),300);
					//スペースキーが押されているか判定　（押されていれば画面遷移）
					if(Keyboard.iskeyPressd(KeyEvent.VK_ESCAPE)) {
						screen = EnumShootingScreen.START;
					}	
					break;

			
			}
			
			//FPSの処理
			gra.setFont(new Font("SansSelif",Font.PLAIN,10));
			gra.drawString( fps + "FPS",0,450);			
			shootingFrame.panel.draw();
			
			//UNIX時間の取得			
			startTime = System.currentTimeMillis();
			
			//プログラムを停止
			try {
				Thread.sleep(1000/fps-(System.currentTimeMillis() - startTime));
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			//FPSの時間を計測(1フレーム表示されるのにかかった実行＆待機時間)
			//System.out.println(System.currentTimeMillis() - startTime);
			
		}
		
		
	}

}
