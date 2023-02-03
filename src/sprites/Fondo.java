package sprites;

import java.awt.Graphics;
import java.awt.Image;

import utils.Assets;
import utils.Constant;

public class Fondo {
	
	private static final int WS = Constant.WIDTH_SCREEN;
	private static final int HS = Constant.HEIGHT_SCREEN;
	private static final int WB = Constant.WIDTH_BACKGROUND;
	
	private Image fondo = Assets.fondo.getImage();
	private int dx = 0;
	private int signo = (int) (Math.random()*2);
	private boolean outOfLeft = false;
	private boolean outOfRight = false;
	
	public Fondo() {	
	}
	
	public void update() {
		
		//Hago que el fondo se mueva con una velocidad
		if (signo == 0) {
			dx += 1;
		} else {
			dx -= 1;
		}
		
		//Rango posible de 'dx'
		//Con 'dx=0' añadimos periodicidad al fondo
		if (dx < 0 && Math.abs(dx) > WB) {
			dx = 0;
		}
		if (dx > 0 && dx > WB) {
			dx = 0;
		}
		
		//Caso límite borde izquierdo
		if (dx < 0) {
			outOfLeft = true;
		} else {
			outOfLeft = false;
		}	
		
		//Caso límite borde derecho
		if (dx > 0 && dx + Constant.WIDTH_SCREEN > WB) {
			outOfRight = true;
		} else {
			outOfRight = false;
		}
		
	}
	
	public void draw(Graphics g) {
		
		//Pinto el fondo
		g.drawImage(fondo, 0, 0, WS, HS, dx, 0, dx+WS, HS, null);
		
		//Caso límite borde izquierdo
		if (outOfLeft) {
			g.drawImage(fondo, 0, 0, Math.abs(dx), HS, WB-Math.abs(dx), 0, WB, HS, null);
		}
		
		//Caso límite borde derecho	
		if (outOfRight) {
			g.drawImage(fondo, WB-dx, 0, WS, HS, 0, 0, WS-(WB-dx), HS, null);
		}
		
	}
	
}
