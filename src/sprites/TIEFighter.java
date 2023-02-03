package sprites;

import java.applet.AudioClip;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Random;

import utils.Assets;
import utils.Constant;

public class TIEFighter {
	
	private Image tiefighter;
	public static int ANCHO;
	public static int ALTO;
	private double angle;
	public final int RADIUS;
	public final int RADIUS1 = 250;
	public final int RADIUS2 = 340;
	private int x, y;
	private int signo1 = Constant.TIEROTATIONSIGN1;
	private int signo2 = Constant.TIEROTATIONSIGN2;
	private final static int WS = Constant.WIDTH_SCREEN;
	private final static int HS = Constant.HEIGHT_SCREEN;
	private AudioClip tie_explosion;
	
	public TIEFighter() {
		tiefighter = Assets.tiefighter.getImage();
		ANCHO = Assets.tiefighter.getIconWidth();
		ALTO = Assets.tiefighter.getIconHeight();
		
		Random rand = new Random();
		angle = Math.toRadians(Math.random()*360);
		RADIUS = (rand.nextInt() % 2 == 0) ? RADIUS1 : RADIUS2;
		
		tie_explosion = Assets.tie_explosion;
	}
	
	//Move TIE Fighters radially around the Death Star
	public void update() {
		
		if (RADIUS == RADIUS1) {
			if (signo1 == 0) {
				angle += 0.004;
			} else if (signo1 == 1) {
				angle -= 0.004;
			}
			
		} else if (RADIUS == RADIUS2) {
			if (signo2 == 0) {
				angle += 0.006;
			} else if (signo2 == 1) {
				angle -= 0.006;
			}	
		}
	}
	
	public void draw(Graphics g) {	
		x = (int) (WS/2 + RADIUS*Math.cos(angle) - ANCHO/2);
		y = (int) (HS/2 - RADIUS*Math.sin(angle) - ALTO/2);
		g.drawImage(tiefighter, x, y, x+ANCHO, y+ALTO, 0, 0, ANCHO, ALTO, null);
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean colision(ShotXW shotXW) {
		
		//Rectangulo Ala Izquierda TIE Fighter
		Rectangle recTIEAlaIzq = new Rectangle(x+8, y+8, ANCHO-61, ALTO-16);
		//Rectangulo Ala Izquierda TIE Fighter
		Rectangle recTIEAlaDer = new Rectangle(x+53, y+8, ANCHO-61, ALTO-16);
		//Rectangulo cuerpo TIE Fighter
		Rectangle recTIEBody = new Rectangle(x+5, y+27, ANCHO-10, ALTO-54);
		//Rectangulo cabina TIE Fighter
		Rectangle recTIECockpit = new Rectangle(x+22, y+21, ANCHO-44, ALTO-42);
	
		//Rectangulo disparo XWing
		Rectangle recShotXW = new Rectangle(shotXW.getX(), shotXW.getY(), ShotXW.ANCHO, ShotXW.ALTO);
		
		boolean isColision = (recTIEAlaIzq.intersects(recShotXW))
						|| (recTIEAlaDer.intersects(recShotXW))
						|| (recTIEBody.intersects(recShotXW))
						|| (recTIECockpit.intersects(recShotXW));
		
		if (isColision) {
			tie_explosion.play();
		}
		
		return isColision;
	}

}
