package renderEngine;

import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.opengl.ContextAttribs;


public class DisplayManager {
	//DEFINI��O DE VARI�VEIS DO VIEWING
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 60;
	
	public static void createDisplay() {
		
		ContextAttribs attribs = new ContextAttribs(3,2);
		//define a vers�o do opengl

		attribs.withForwardCompatible(true);
		attribs.withProfileCore(true);
		
		
		try {//checa por exce��es no viewing
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Game01");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		
	}
	public static void updateDisplay() {
		Display.sync(FPS_CAP);//sincroniza o jogo em fps fixo
		Display.update();
		//define a taxa de fps e define a atualiza��o de tela a frequ�ncia
		
	}
	public static void closeDisplay() {
	
}
}
