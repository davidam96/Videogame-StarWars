package sprites;

import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;

import utils.Assets;

public class ShotXW {
	
	private Image shot_xw;
	public static int ANCHO, ALTO;
	private int x,y;
	private int lastKeyPressed;
	private AudioClip xwinghootsClip;
	
	public ShotXW(int x, int y, int lastKeyPressed) {
		
		if (lastKeyPressed == 0 || lastKeyPressed == 2) {
			
			shot_xw = Assets.shot_xw_ver.getImage();
			ANCHO = Assets.shot_xw_ver.getIconWidth();
			ALTO = Assets.shot_xw_ver.getIconHeight();
			
		} else if (lastKeyPressed == 1 || lastKeyPressed == 3) {
			
			shot_xw = Assets.shot_xw_hor.getImage();
			ANCHO = Assets.shot_xw_hor.getIconWidth();
			ALTO = Assets.shot_xw_hor.getIconHeight();
		}
		
		this.x = x;
		this.y = y;
		this.lastKeyPressed = lastKeyPressed;
		
		xwinghootsClip = Assets.xwing_shoots;
		xwinghootsClip.play();
	}
	
	//Change shot velocity depending on the direction of the XWing
	public void update() {
		switch (lastKeyPressed) {
			case 0: 
				y -= 20;
				break;
			case 1: 
				x -= 20;
				break;
			case 2: 
				y += 20;
				break;
			case 3: 
				x += 20;
				break;
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(shot_xw, x, y, x+ANCHO, y+ALTO, 0, 0, ANCHO, ALTO, null);
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
}
