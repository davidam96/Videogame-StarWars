package game;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import input.InputHandler;
import sprites.DeathStar;
import sprites.Fondo;
import sprites.ShotTIE;
import sprites.ShotXW;
import sprites.TIEFighter;
import sprites.XWing;
import utils.Assets;
import utils.Constant;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
	//En esta clase vamos a tener todo el contenido de la ventana del juego
	//de la ventana del juego 'GameMain', ya que extiende de JPanel.
	
	public boolean isRunning = true;
	public boolean isPaused = false;
	
	public GameEngine gameEngine;	
	private InputHandler input;
	
	private final int WS = Constant.WIDTH_SCREEN;
	private final int HS = Constant.HEIGHT_SCREEN;
	
	private Fondo fondo;
	private XWing xwing;
	private DeathStar deathStar;
	
	private double angle;
	private int nacetie = 0;
	private final static int VECESNACETIE = 5;
	private ArrayList<TIEFighter> ties1;
	private ArrayList<TIEFighter> ties2;
	private final double PHASE1 = 92.39978392911156583713657;
	private final double PHASE2 = 92.88186975830693052846076;
	private ArrayList<Integer> lista17;
	private ArrayList<Integer> lista23;

	private ShotXW shotXW1, shotXW2;
	private ArrayList<ShotXW> shotsXW;
	public int lastKeyPressed;
	private MouseListener mouseListener;
	
	private final static int VECESNACEDISPAROTIE = 1;
	private int nacedisparotie;
	private ArrayList<ShotTIE> shotsTIE;
	
	private final static int VECESQUITARVIDA = 25;
	private int vecesQuitarVida;
	public static boolean puedesQuitarVida = true;
	
	private boolean gameover = false;
	private boolean win = false;
	
	public Font starjedi;
	
	public GamePanel() {
		super();
		
		gameEngine = new GameEngine(this);
		input = new InputHandler(this);
		
		//Load resources
		Assets.loadAssets();
		starjedi = Assets.starjedi;
		
		//Create sprites
		fondo = new Fondo();
		xwing = new XWing();
		deathStar = new DeathStar();
		
		lastKeyPressed = xwing.getDirectionKey();
		
		ties1 = new ArrayList<TIEFighter>();
		ties2 = new ArrayList<TIEFighter>();
		lista17 = new ArrayList<Integer>();		
		lista23 = new ArrayList<Integer>();
		
		shotsXW = new ArrayList<ShotXW>();
		shotsTIE = new ArrayList<ShotTIE>();
		
		nacetie = 0;
		nacedisparotie = 0;
		
		//MouseListener for firing XWing shots
		createMouseListener();
		addMouseListener(mouseListener);
		
	}

	//WHAT TO DO WHEN XWING FIRES SHOTS
	private void createMouseListener() {
		mouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				
				int WX = Assets.xwing.getIconWidth() / 4;
				int HX = Assets.xwing.getIconHeight();
				
				if ( lastKeyPressed == 0 || lastKeyPressed == 2) {
				
					int WS = Assets.shot_xw_ver.getIconWidth();
					
					shotXW1 = new ShotXW(xwing.getX(), xwing.getY(), lastKeyPressed);
					shotXW2 = new ShotXW(xwing.getX()+(WX-WS), xwing.getY(), lastKeyPressed);
					
				} else if (lastKeyPressed == 1 || lastKeyPressed == 3) {
					
					int HS = Assets.shot_xw_hor.getIconHeight();
					
					shotXW1 = new ShotXW(xwing.getX(), xwing.getY(), lastKeyPressed);
					shotXW2 = new ShotXW(xwing.getX(), xwing.getY()+(HX-HS), lastKeyPressed);
				}

				shotsXW.add(shotXW1);
				shotsXW.add(shotXW2);
			}
		};
	}

	public void update() {
		
		updateSprites();
		
		if (!gameover) {
			handleInputs();
			handleShotsXW();
		}
		
		handleTIEs();
		
		if (!win && !gameover) {
			handleShotsTIEs();
			handleColisions();
		}
	}
	
	//UPDATE SPRITE POSITIONS
	private void updateSprites() {
		
		//Update sprites
		fondo.update();
		xwing.update();
		
		//Update Shots XWing
		for(ShotXW shotXW : shotsXW) {
			shotXW.update();
		}
		
		if(!win) {
			
			deathStar.update();
			
			//Update TIE Fighters
			for (TIEFighter tie : ties1) {
				tie.update();
			}
			for (TIEFighter tie : ties2) {
				tie.update();
			}
			
			//Update Shots TIE Fighters
			for(ShotTIE shotTIE : shotsTIE) {
				shotTIE.update();
			}
		}
	}
	
	//HANDLE MOUSE AND KEYBOARD INPUT EVENTS
	private void handleInputs() {
		
		//Handle keyboard inputs (up, down, left, right)
		if (input.isKeyDown(KeyEvent.VK_W)) {
			xwing.up();
			lastKeyPressed = 0;
		}
		if (input.isKeyDown(KeyEvent.VK_A)) {
			xwing.left();
			lastKeyPressed = 1;
		}
		if (input.isKeyDown(KeyEvent.VK_S)) {
			xwing.down();
			lastKeyPressed = 2;
		}
		if (input.isKeyDown(KeyEvent.VK_D)) {
			xwing.right();
			lastKeyPressed = 3;
		}		
		
	}
	
	private void handleShotsXW() {
		
		//Remuevo los disparos que se salen de la pantalla...
		
		ArrayList<ShotXW> shotsDel = new ArrayList<ShotXW>();
		
		for (ShotXW shotXW : shotsXW) {
			if ( !(shotXW.getX() > 0 && shotXW.getX() < WS)
				 || !(shotXW.getY() > 0 && shotXW.getY() < HS) ) {
				
				shotsDel.add(shotXW);
			}
		}
		
		shotsXW.removeAll(shotsDel);	
	}
	
	//HANDLE TIE FIGHTERS BORN, ALONG WITH
	//THEIR POSITIONS AND EVEN ANGLE SPACING
	private void handleTIEs() {
		
		nacetie = ++nacetie % VECESNACETIE;
		
		if (nacetie > 10000) {
			nacetie = 0;
		}
		
		if (nacetie == 0) {
			
			TIEFighter tie = new TIEFighter();
			Integer c;
			
			if (lista17.size() == 0) {
				for (int i=0; i<17; i++) {
					Integer n = i;
					lista17.add(n);
				}
			}
			
			if (lista23.size() == 0 ) {
				for (int i=0; i<23; i++) {
					Integer n = i;
					lista23.add(n);
				}
			}
			
			if (tie.RADIUS == tie.RADIUS1 && ties1.size() < 17) {	
				
				ties1.add(tie);
				
				c = lista17.get(0);
				lista17.remove(c);	
				
				angle = ties1.get(0).getAngle() + c*PHASE1;		
				ties1.get(ties1.indexOf(tie)).setAngle(angle);
					
			} else if (tie.RADIUS == tie.RADIUS2 && ties2.size() < 23) {
				
				ties2.add(tie);	
				
				c = lista23.get(0);
				lista23.remove(c);
				
				angle = ties2.get(0).getAngle() + c*PHASE2;
				tie.setAngle(angle);
			}
		}
	}
	
	//HANDLE TIE FIGHTER SHOTS BORN
	private void handleShotsTIEs() {
		
		nacedisparotie = ++nacedisparotie % VECESNACEDISPAROTIE;
		
		if (nacedisparotie > 10000) {
			nacedisparotie = 0;
		}
		
		if (nacedisparotie == 0 && ties1.size() > 0 && ties2.size() > 0) {
			
			Random rand = new Random();
			int index1 = rand.nextInt(ties1.size());
			int index2 = rand.nextInt(ties2.size());
			
			TIEFighter tie = (rand.nextInt() % 2 == 0)? ties1.get(index1) : ties2.get(index2);		
			ShotTIE shotTIE = new ShotTIE(tie.getX(), tie.getY());
			shotsTIE.add(shotTIE);
			
		}
		
		//Remuevo los disparos que se salen de la pantalla...
		
		ArrayList<ShotTIE> shotsDel = new ArrayList<ShotTIE>();
		
		for (ShotTIE shotTIE : shotsTIE) {
			if ( !(shotTIE.getX() > 0 && shotTIE.getX() < WS)
				 || !(shotTIE.getY() > 0 && shotTIE.getY() < HS) ) {
				
				shotsDel.add(shotTIE);
			}
		}
		
		shotsTIE.removeAll(shotsDel);
	}
	
	//HANDLE ALL COLISIONS
	private void handleColisions() {
		
		//XWING - TIE SHOTS COLISIONS
		
		xwing.lastKeyPressed = lastKeyPressed;
		
		ArrayList<ShotTIE> shotsTIEDel = new ArrayList<ShotTIE>();
		
		for (ShotTIE shotTIE : shotsTIE) {
			if (xwing.colision(shotTIE)) {
				
				shotsTIEDel = new ArrayList<ShotTIE>();
				shotsTIEDel.add(shotTIE);
				
				if (puedesQuitarVida) {
					
					boolean isGameOver = xwing.removeLife(1);
					puedesQuitarVida = false;
					
					if (isGameOver) {
						gameover = true;
						removeMouseListener(mouseListener); //esto no funciona...
					}
				}
				
			}
		}
		
		
		//Delay para quitar vidas
		delayQuitarVida();
		
		
		//XWING - TIE COLISIONS
		
		ArrayList<TIEFighter> tiesDel = new ArrayList<TIEFighter>();
		
		for (TIEFighter tie : ties1) {
			if (xwing.colision(tie)) {
				
				tiesDel = new ArrayList<TIEFighter>();
				tiesDel.add(tie);
				
				if (puedesQuitarVida) {
					
					boolean isGameOver = xwing.removeLife(3);
					puedesQuitarVida = false;
					
					if (isGameOver) {
						gameover = true;
						removeMouseListener(mouseListener); //esto no funciona...
					}
				}
				
			}
		}
		
		for (TIEFighter tie : ties2) {
			if (xwing.colision(tie)) {
				
				tiesDel.add(tie);
				
				if (puedesQuitarVida) {
					
					boolean isGameOver = xwing.removeLife(3);
					puedesQuitarVida = false;
					
					if (isGameOver) {
						gameover = true;
						removeMouseListener(mouseListener); //esto no funciona...
					}
				}
				
			}
		}
		
		//Delay para quitar vidas
		delayQuitarVida();
		
		
		//XWING SHOTS - (TIE || DEATHSTAR)  COLISIONS
		
		ArrayList<ShotXW> shotsXWDel = new ArrayList<ShotXW>();
		
		for (ShotXW shotXW : shotsXW) {
			
			for (TIEFighter tie : ties1) {
				if (tie.colision(shotXW)) {
					
					tiesDel.add(tie);
					shotsXWDel.add(shotXW);
					
				}
			}
			
			for (TIEFighter tie : ties2) {
				if (tie.colision(shotXW)) {
					
					tiesDel.add(tie);
					shotsXWDel.add(shotXW);
					
				}
			}
			
			if (deathStar.colision(shotXW)) {
				shotsXWDel.add(shotXW);
			}
			
			if (deathStar.dl >= 900) {
				win = true;
				ties1.removeAll(ties1);
				ties2.removeAll(ties2);
				shotsTIE.removeAll(shotsTIE);
				
			}
			
		}
		
		
		//XWING - DEATHSTAR COLISION
		
		if (xwing.colision(deathStar)) {
			gameover = true;
		}

		
		//Finalmente borramos todos disparos y TIEs que hayan colisionado
		//aqui falla algo...
		if (tiesDel.size() != 0) {
			ties1.removeAll(tiesDel);
			ties2.removeAll(tiesDel);
		}
		
		if (shotsXWDel.size() != 0) {
			shotsXW.removeAll(shotsXWDel);
		}
		if (shotsTIEDel.size() != 0) {
			shotsTIE.removeAll(shotsTIEDel);
		}	
		
	}

	private void delayQuitarVida() {
		if (!puedesQuitarVida) {	
			
			vecesQuitarVida = ++vecesQuitarVida % VECESQUITARVIDA;
			
			if (vecesQuitarVida > 10000) {
				vecesQuitarVida = 0;
			}
			
			if (vecesQuitarVida == 0) {
				puedesQuitarVida = true;
			}
		}	
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2D = (Graphics2D) g;
		
		fondo.draw(g2D);
		
		if (!gameover) {
			//Paint XWing
			xwing.draw(g2D);
			
			//Paint Shots XWing
			for(ShotXW shotXW : shotsXW) {
				shotXW.draw(g2D);
			}
		}
		
		if (!win) {	
			//Paint DeathStar
			deathStar.draw(g2D);
			
			//Paint TIE Fighters
			for (TIEFighter tie : ties1) {
				tie.draw(g2D);
			}
			for (TIEFighter tie : ties2) {
				tie.draw(g2D);
			}
			
			//Paint Shots TIE Fighters
			for (ShotTIE shotTIE : shotsTIE) {
				shotTIE.draw(g2D);
			}
		}
	}
	
	public void start() {
		gameEngine.start();
	}
	
	
}
