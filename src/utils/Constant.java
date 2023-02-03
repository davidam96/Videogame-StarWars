package utils;

import java.util.Random;

public class Constant {
	
    private static final int HEIGHT_TOPBAR = 29;
    private static final int HEIGHT_MENUBAR = 36;
    private static final int HEIGHT_WINDOWSTASKBAR = 50;
    
    public static final int WIDTH_SCREEN = 1920;
    public static final int HEIGHT_SCREEN = 1080 - HEIGHT_TOPBAR - HEIGHT_MENUBAR - HEIGHT_WINDOWSTASKBAR;

    public static final int WIDTH_BACKGROUND = 1920;
    public static final int HEIGHT_BACKGROUND = 1716;
    
    private static Random rand = new Random();
	public static final int TIEROTATIONSIGN1 = (rand.nextInt() % 2 == 0) ? 0 : 1;
	public static final int TIEROTATIONSIGN2 = (rand.nextInt() % 2 == 0) ? 0 : 1;
}
