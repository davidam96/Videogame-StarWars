package game;

import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import utils.Assets;
import utils.Constant;

@SuppressWarnings("serial")
public class GameMain extends JFrame implements ActionListener {
	
	//Un container sirve para añadir
	//varios JPanel a la misma ventana.
	private Container container;
	
	private GamePanel gamePanel;
	private JPanel footerPanel;
	private JButton btnStart, btnPlayPause, btnExit;
	
	private AudioClip soundtrack;
	private boolean isPaused = false;
	
	
	public static void main(String[] args) {
		new GameMain();
		for (int i=0; i<20;i++) {}
	}
	
	public GameMain() {
		
		//Características esenciales de la ventana
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(-10, 0, Constant.WIDTH_SCREEN, Constant.HEIGHT_SCREEN);
		setExtendedState(JFrame.MAXIMIZED_BOTH); //Línea para maximizar la ventana
		container = getContentPane();
		container.setLayout(new BorderLayout(0,0));
		
		//gamePanel
		gamePanel = new GamePanel();	
		
		//panelFooter
		footerPanel = new JPanel();
		footerPanel.setBackground(Color.decode("#4C4A48"));
		
		//btnStart
		btnStart = new JButton("Start");
		btnStart.addActionListener(this);
		footerPanel.add(btnStart);

		//btnPlayPause
		btnPlayPause = new JButton("Pause / Continue");
		btnPlayPause.addActionListener(this);
		footerPanel.add(btnPlayPause);
		
		//btnExit
		btnExit = new JButton("Exit");
		btnExit.addActionListener(this);
		footerPanel.add(btnExit);
		
		//Añadimos todo al container
		container.add(gamePanel, BorderLayout.CENTER);
		container.add(footerPanel, BorderLayout.SOUTH);
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object boton = e.getSource();
		if (boton == btnStart) {
			btnStartOnClick();
		} else if (boton == btnPlayPause) {
			btnPlayPlauseOnClick();
		} else if (boton == btnExit) {
			btnExitOnClick();
		}
		
	}

	private void btnStartOnClick() {
		gamePanel.start();
		gamePanel.requestFocus();
		btnStart.setEnabled(false);
		btnPlayPause.setEnabled(true);
		
		soundtrack = Assets.soundtrack;
		synchronized (soundtrack) {
			soundtrack.loop();
		}
	}
	
	private void btnPlayPlauseOnClick() {
		gamePanel.isPaused = !(gamePanel.isPaused);
		gamePanel.requestFocus();
		
		//AudioClip control on Play - Pause
		isPaused = !isPaused;
		if (isPaused) {
			soundtrack.stop();
		} else {
			soundtrack.loop();
		}
	}
	
	private void btnExitOnClick() {
		gamePanel.isRunning = false;
		System.exit(0);
	}


}
