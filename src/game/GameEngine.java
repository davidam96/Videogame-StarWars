package game;

public class GameEngine extends Thread {
	
	private static final int FPS = 30;
	private GamePanel gamePanel;
	
	public GameEngine(GamePanel gamePanel) {
		super();
		this.gamePanel = gamePanel;
	}
	
	public void run() {
		long time;
		
		try {
			while (gamePanel.isRunning) {
				
				//Marcamos un timestamp antes de renderizar el gamePanel, que luego
				//más abajo vamos a necesitar para corregir el delay que haya habido.
				time = System.currentTimeMillis();
				
				//Renderizamos el gamePanel
				if (!gamePanel.isPaused) {
					gamePanel.update();
					gamePanel.repaint();
				}
				
				// delay for each frame - time it took for one frame
				time = (1000/FPS) - (System.currentTimeMillis() - time);
				
				//Dormimos durante un frame
				if (time>0) {
					try {
						Thread.sleep(time);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
