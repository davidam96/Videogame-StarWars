package sprites;

import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import game.GamePanel;
import utils.Assets;
import utils.Constant;

public class XWing {
	
	private int x,y;
	private Image xwing;
	private int ANCHO, ALTO;
	private final static int WS = Constant.WIDTH_SCREEN;
	private final static int HS = Constant.HEIGHT_SCREEN;
	private final static int WD = Assets.deathstar.getIconWidth();
	private final static int HD = Assets.deathstar.getIconHeight();
	private Random rand = new Random();
	
	private final static int COLUMNS = 4;
	private final static int ROWS = 1;
	private int columnFrame;
	private int rowFrame;
	
	private Image xwing_life;
	private int xlife, ylife;
	private int LIFEANCHO, LIFEALTO;
	private int lifeRowFrame;
	private int lifeColumnFrame;
	private final static int LIFECOLUMNS = 1;
	private final static int LIFEROWS = 9;
	public int lastKeyPressed;
	
	private static AudioClip xwing_explosion;
	
	
	public XWing() {
		
		xwing = Assets.xwing.getImage();
		ANCHO = Assets.xwing.getIconWidth() / COLUMNS;
		ALTO = Assets.xwing.getIconHeight() / ROWS;
		
		columnFrame = (int) (Math.random()*4);
		rowFrame = 0;
		
		//Divido la coordenada 'x' en dos rangos, para que el XWing 
		//no aparezca encima (o debajo) de la estrella de la muerte.
		
		int x1, x2;
		
		do {
			x1 = (int) (Math.random()*((WS-WD)/2)) - 100;
			x2 = (WS+WD)/2 + (int) (Math.random()*((WS-WD)/2)) + 100;
		} while (x1 < 0 || x1 > WS-ANCHO || x2 < 0 || x2 > WS-ANCHO);

		x = (rand.nextInt() % 2 == 0) ? x1 : x2;
		
		//Divido la coordenada 'y' en dos rangos, para que el XWing 
		//no aparezca encima (o debajo) de la estrella de la muerte.
		
		int y1, y2;
		
		do {
			y1 = (int) (Math.random()*((HS-HD)/2)) - 100;
			y2 = (HS+HD)/2 + (int) (Math.random()*((HS-HD)/2)) + 100;
		} while (y1 < 0 || y1 > HS-ALTO || y2 < 0 || y2 > HS-ALTO);
		
		y = (rand.nextInt() % 2 == 0) ? y1 : y2;
		
		//Configuro el sprite de la vida del XWing
		
		xwing_life = Assets.xwing_life.getImage();
		LIFEANCHO = Assets.xwing_life.getIconWidth() / LIFECOLUMNS;
		LIFEALTO = Assets.xwing_life.getIconHeight() / LIFEROWS;
		
		xlife = 20;
		ylife = 20;
		
		lifeRowFrame = 0;
		lifeColumnFrame = 0;
		
		xwing_explosion = Assets.xwing_explosion;
		
	}
	
	public void update() {
		
		//Condiciones de periodicidad
		//(ayudan a que no haya BUGs)
		
		if (Math.abs(x)>WS) {
			x = 0;
		}
		
		if (x<0) {
			x = WS;
		}
		
		if (Math.abs(y)>HS) {
			y = 0;
		}
		
		if (y<0) {
			y = HS;
		}
		
	}
	
	public void draw(Graphics g) {
		
		int frameX = columnFrame * ANCHO;
		int frameY = rowFrame * ALTO;
		
		//Default
		g.drawImage(xwing, x, y, x+ANCHO, y+ALTO, frameX, frameY, frameX+ANCHO, frameY+ALTO, null);
		
		//Caso limite: borde izquierdo
		if (x<0) { 
			g.drawImage(xwing, WS-Math.abs(x), y, WS-Math.abs(x)+ANCHO, y+ALTO,
				frameX, frameY, frameX+ANCHO, frameY+ALTO, null); 
			}
		
		//Caso limite: borde derecho
		if (x>WS-ANCHO) { 
			g.drawImage(xwing, 0, y, ANCHO-(WS-x), y+ALTO, 
				frameX+WS-x, frameY, frameX+ANCHO, frameY+ALTO, null); 
			}
		
		//Caso limite: borde superior
		if (y<0) { 
			g.drawImage(xwing, x, HS-Math.abs(y), x+ANCHO, HS-Math.abs(y)+ALTO,
				frameX, frameY, frameX+ANCHO, frameY+ALTO, null); 
			}
		
		//Caso limite: borde inferior
		if (y>HS-ALTO) { 
			g.drawImage(xwing, x, 0, x+ANCHO, ALTO-(HS-y), 
				frameX, frameY+HS-y, frameX+ANCHO, frameY+ALTO, null); 
			}
		
		//Caso limite: esquina superior-izquierda
		if (x<0 && y<0) { 
			g.drawImage(xwing, WS-Math.abs(x), HS-Math.abs(y), WS, HS, 
				frameX, frameY, frameX+Math.abs(x), frameY+Math.abs(y), null); 
			}
		
		//Caso limite: esquina superior-derecha
		if (x>WS-ANCHO && y<0) { 
			g.drawImage(xwing, 0, HS-Math.abs(y), ANCHO-(WS-x), HS, 
				frameX+WS-x, frameY, frameX+ANCHO, frameY+Math.abs(y), null); 
			}
		
		//Caso limite: esquina inferior-izquierda
		if (x<0 && y>HS-ALTO) { 
			g.drawImage(xwing, WS-Math.abs(x), 0, WS, ALTO-(HS-y),
				frameX, frameY+HS-y, frameX+Math.abs(x), frameY+ALTO, null); 
		}
		
		//Caso limite: esquina inferior-derecha
		if (x>WS-ANCHO && y>HS-ALTO) { 
			g.drawImage(xwing, 0, 0, ANCHO-(WS-x), ALTO-(HS-y),
				frameX+WS-x, frameY+HS-y, frameX+ANCHO, frameY+ALTO, null); 
			}
		
		int lifeFrameX = lifeColumnFrame * LIFEANCHO;
		int lifeFrameY = lifeRowFrame * LIFEALTO;
		
		//DIBUJO LA VIDA
		g.drawImage(xwing_life, xlife, ylife, xlife+LIFEANCHO, ylife+LIFEALTO,
				lifeFrameX, lifeFrameY, lifeFrameX+LIFEANCHO, lifeFrameY+LIFEALTO, null);
		
	}
	
	public void up() {
		y -= 17;
		columnFrame = 0;
	}
	
	public void down() {
		y += 17;
		columnFrame = 2;
	}
	
	public void left() {
		x -= 17;
		columnFrame = 1;
	}
	
	public void right() {
		x += 17;
		columnFrame = 3;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getDirectionKey() {
		return columnFrame;
	}
	
	public boolean colision(ShotTIE shotTIE) {
		
		//Rectangulo Alas Xwing
		Rectangle recXWingAlas = getRecXwingAlas();
		//Rectangulo Morro Xwing
		Rectangle recXWingMorro = getRecXwingMorro();
	
		//Rectangulo disparo TIE Fighter
		Rectangle recShot = new Rectangle(shotTIE.getX(), shotTIE.getY(), ShotTIE.ANCHO, ShotTIE.ALTO);
		
		boolean isColision = (recXWingAlas.intersects(recShot)) || (recXWingMorro.intersects(recShot));
		return isColision;
	}
	
	public boolean colision(TIEFighter tie) {
		
		//Rectangulo Alas Xwing
		Rectangle recXWingAlas = getRecXwingAlas();
		//Rectangulo Morro Xwing
		Rectangle recXWingMorro = getRecXwingMorro();
	
		//Rectangulo disparo TIE Fighter
		Rectangle recTIE = new Rectangle(tie.getX(), tie.getY(), TIEFighter.ANCHO, TIEFighter.ALTO);
		
		boolean isColision = (recXWingAlas.intersects(recTIE)) || (recXWingMorro.intersects(recTIE));
		
		if (isColision) {
			xwing_explosion.play();
		}
		
		return isColision;
	}
	
	public boolean colision(DeathStar deathstar) {
		
		//Rectangulo Alas Xwing
		Rectangle recXWingAlas = getRecXwingAlas();
		//Rectangulo Morro Xwing
		Rectangle recXWingMorro = getRecXwingMorro();
	
		//Rectangulo Death Star
		Rectangle recDeathStar = new Rectangle(((WS-WD)/2) + 50, ((HS-HD)/2) + 50, WD-100, HD-100);
		
		boolean isColision = (recXWingAlas.intersects(recDeathStar)) || (recXWingMorro.intersects(recDeathStar));
		
		return isColision;
	}

	private Rectangle getRecXwingAlas() {
		
		Rectangle recXWingAlas = new Rectangle();
		
		if (lastKeyPressed == 1) {
			recXWingAlas = new Rectangle(x+44, y+2, ANCHO-48, ALTO-4);
		} else if (lastKeyPressed == 2) {
			recXWingAlas = new Rectangle(x+2, y+4, ANCHO-4, ALTO-48);
		} else if (lastKeyPressed == 3) {
			recXWingAlas = new Rectangle(x+4, y+2, ANCHO-48, ALTO-4);
		} else {
			recXWingAlas = new Rectangle(x+2, y+44, ANCHO-4, ALTO-48);
		}
		
		return recXWingAlas;
	}
	
	private Rectangle getRecXwingMorro() {
		
		Rectangle recXWingMorro = new Rectangle();
		
		if (lastKeyPressed == 1) {
			recXWingMorro = new Rectangle(x+6, y+28, ANCHO-26, ALTO-56);
		} else if (lastKeyPressed == 2) {
			recXWingMorro = new Rectangle(x+28, y+20, ANCHO-56, ALTO-26);
		} else if (lastKeyPressed == 3) {
			recXWingMorro = new Rectangle(x+20, y+28, ANCHO-26, ALTO-56);
		} else {
			recXWingMorro = new Rectangle(x+28, y+6, ANCHO-56, ALTO-26);
		}
		
		return recXWingMorro;
	}

	public boolean removeLife(int life) {
		
		if (GamePanel.puedesQuitarVida = true) {
			lifeRowFrame += life;
			xwing_explosion.play();
		}	
		
		if ( lifeRowFrame >= 8) {
			lifeRowFrame = 8;
			return true;
		} else {
			return false;
		}	
		
	}
	
	
}
