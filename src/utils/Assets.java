package utils;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;

public class Assets {
	public static ImageIcon fondo;
	public static ImageIcon xwing;
	public static ImageIcon deathstar;
	public static ImageIcon tiefighter;
	public static ImageIcon shot_xw_hor;
	public static ImageIcon shot_xw_ver;
	public static ImageIcon shot_tie_hor;
	public static ImageIcon shot_tie_ver;
	public static ImageIcon shot_tie_diag1;
	public static ImageIcon shot_tie_diag2;
	public static ImageIcon xwing_life;
	public static ImageIcon deathstar_life_full;
	public static ImageIcon deathstar_life_empty;
	
	public static AudioClip xwing_shoots;
	public static AudioClip soundtrack;
	public static AudioClip xwing_explosion;
	public static AudioClip tie_explosion;
	
	public static Font starjedi;
	
	//Cargamos aquí todos los recursos...
	public static void loadAssets() {
		
		fondo = new ImageIcon("assets/background.png");
		xwing = new ImageIcon("assets/xwing spritesheet 64p.png");
		deathstar = new ImageIcon("assets/death star 400p.png");
		tiefighter = new ImageIcon("assets/tiefighter 64p.png");
		
		shot_xw_hor = new ImageIcon("assets/shot_xw_hor.png");
		shot_xw_ver = new ImageIcon("assets/shot_xw_ver.png");
		shot_tie_hor = new ImageIcon("assets/shot_tie_hor.png");
		shot_tie_ver = new ImageIcon("assets/shot_tie_ver.png");
		shot_tie_diag1 = new ImageIcon("assets/shot_tie_diag1.png");
		shot_tie_diag2 = new ImageIcon("assets/shot_tie_diag2.png");
		xwing_life = new ImageIcon("assets/xwing_life spritesheet.png");
		deathstar_life_full = new ImageIcon("assets/deathstar_life_full.png");
		deathstar_life_empty = new ImageIcon("assets/deathstar_life_empty.png");
		
		File font_file = new File("assets/Starjedi.ttf");
		
		try {
			
			xwing_shoots = Applet.newAudioClip(new File("assets/xwing_shoots.wav").toURI().toURL());
			soundtrack = Applet.newAudioClip(new File("assets/soundtrack.wav").toURI().toURL());
			xwing_explosion = Applet.newAudioClip(new File("assets/xwing_explosion.wav").toURI().toURL());
			tie_explosion = Applet.newAudioClip(new File("assets/tie_explosion_loud.wav").toURI().toURL());
			
			starjedi = Font.createFont(Font.TRUETYPE_FONT, font_file);
			
		} catch (FontFormatException e1) {
				e1.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}

	}
}
