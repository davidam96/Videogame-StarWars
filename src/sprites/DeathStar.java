package sprites;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import utils.Assets;
import utils.Constant;

public class DeathStar {
	
	private Image deathstar;
	private int ANCHO, ALTO;
	private static final int WS = Constant.WIDTH_SCREEN;
	private static final int HS = Constant.HEIGHT_SCREEN;
	private static int x;
	private static int y;
	
	private Image deathstar_life_empty;
	private Image deathstar_life_full;
	private int LIFEANCHO, LIFEALTO;
	public int dl = 0;
	
	public DeathStar() {
		
		deathstar = Assets.deathstar.getImage();
		ANCHO = Assets.deathstar.getIconWidth();
		ALTO = Assets.deathstar.getIconHeight();

		x = (WS - ANCHO)/2;
		y = (HS - ALTO)/2;
		
		deathstar_life_empty = Assets.deathstar_life_empty.getImage();
		deathstar_life_full = Assets.deathstar_life_full.getImage();
		LIFEANCHO = Assets.deathstar_life_empty.getIconWidth();
		LIFEALTO = Assets.deathstar_life_full.getIconHeight();
	}
	
	public void update() {	
	}
	
	public void draw(Graphics g) {
		g.drawImage(deathstar, x, y, x+ANCHO, y+ALTO, 0, 0, ANCHO, ALTO, null);
		
		//DIBUJO LA VIDA
		int xlife = (WS - LIFEANCHO)/2;
		int ylife = (HS - LIFEALTO) - 20;
		
		//Vida llena (queda por encima de la vida vacia)
		g.drawImage(deathstar_life_empty, xlife, ylife, xlife+LIFEANCHO, ylife+LIFEALTO, 0, 0, LIFEANCHO, LIFEALTO, null);
		//Vida vacia (queda por debajo de la vida llena, oculta.
		g.drawImage(deathstar_life_full, xlife, ylife, xlife+LIFEANCHO-dl, ylife+LIFEALTO, 0, 0, LIFEANCHO-dl, LIFEALTO, null);
	}
	
	public boolean colision(ShotXW shotXW) {
		
		//Rectangulo Death Star
		Rectangle recDeathStar = new Rectangle(((WS-ANCHO)/2) + 60, ((HS-ALTO)/2) + 20, ANCHO-120, ALTO-40);
		
		//Rectangulo disparo XWing
		Rectangle recShotXW = new Rectangle(shotXW.getX(), shotXW.getY(), ShotXW.ANCHO, ShotXW.ALTO);

		boolean isColision = (recDeathStar.intersects(recShotXW)) || (recDeathStar.intersects(recShotXW));

		if (isColision) {
			dl += 7;
		}
		
		return isColision;
	}

}
