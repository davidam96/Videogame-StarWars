package sprites;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import utils.Assets;
import utils.Constant;

public class ShotTIE {
	
	private Image shot_tie;
	public static int ANCHO, ALTO;
	private int x,y;
	private int type;
	private final int WS = Constant.WIDTH_SCREEN;
	private final int HS = Constant.HEIGHT_SCREEN;
	private AudioClip tieshootsClip;
	
	public ShotTIE(int x, int y) {
		
		Random rand = new Random();
		type = (rand.nextInt() % 2 == 0)? 0 : 1;
		type = (rand.nextInt() % 7 == 0)? 2 : type;
		
		if (type == 0) {
			
			shot_tie = Assets.shot_tie_hor.getImage();
			ANCHO = Assets.shot_tie_hor.getIconWidth();
			ALTO = Assets.shot_tie_hor.getIconHeight();
			
		} else if (type == 1) {
			
			if ( (x<=WS/2 && y<HS/2) || (x>WS/2 && y>=HS/2) ) {
				shot_tie = Assets.shot_tie_diag1.getImage();
			} else if ( (x>WS/2 && y<HS/2) || (x<=WS/2 && y>=HS/2) ) {
				shot_tie = Assets.shot_tie_diag2.getImage();
			}
			
			ANCHO = Assets.shot_tie_diag1.getIconWidth();
			ALTO = Assets.shot_tie_diag1.getIconHeight();
			
		} else if (type == 2) {
			
			shot_tie = Assets.shot_tie_ver.getImage();
			ANCHO = Assets.shot_tie_ver.getIconWidth();
			ALTO = Assets.shot_tie_ver.getIconHeight();
			
		}
		
		this.x = x;
		this.y = y;
		
		loadSound();
		boolean playAudio = (rand.nextInt() % 15 == 0)? true : false;		
		if (playAudio) {
			tieshootsClip.play();
		}

	}
	
	
	private void loadSound() {
		try {
			tieshootsClip = Applet.newAudioClip(new File("assets/tie_shoots.wav").toURI().toURL());		
		}  catch (IOException e) {
			e.printStackTrace();
		}	
	}


	public void update() {
		
		//Configuro la dirección y velocidad del disparo
		//según el cuadrante en el que se encuentre su TIE
		
		if (type == 0) {
			
			if (x>=WS/2) {
				x += 15;
			} else if (x<WS/2) {
				x -= 15;
			}
			
		} else if (type == 1) {
			
			if (x>WS/2 && y<HS/2) {
				x += 10 + (int) (Math.random()*3);
				y -= 10 + (int) (Math.random()*3);
			} else if (x<=WS/2 && y<HS/2) {
				x -= 10 + (int) (Math.random()*3);
				y -= 10 + (int) (Math.random()*3);
			} else if (x<=WS/2 && y>=HS/2) {
				x -= 10 + (int) (Math.random()*3);
				y += 10 + (int) (Math.random()*3);
			} else if (x>WS/2 && y>HS/2) {
				x += 10 + (int) (Math.random()*3);
				y += 10 + (int) (Math.random()*3);
			}
			
		} else if (type == 2) {
			
			if (y<=HS/2) {
				y -= 15;
			} else if (y>HS/2) {
				y += 15;
			}
			
		}

	}
	
	public void draw(Graphics g) {
		g.drawImage(shot_tie, x, y, x+ANCHO, y+ALTO, 0, 0, ANCHO, ALTO, null);
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
}
