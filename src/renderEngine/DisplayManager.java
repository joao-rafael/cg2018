package renderEngine;

import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.opengl.ContextAttribs;


public class DisplayManager {
	//DEFINIÇÃO DE VARIÁVEIS DO VIEWING
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final int FPS_CAP = 60;
	
	
	private static long Lastframetime;
	private static float deltat;
	
	public static void createDisplay() {
		
		ContextAttribs attribs = new ContextAttribs(3,2);
		//define a versão do opengl

		attribs.withForwardCompatible(true);
		attribs.withProfileCore(true);
		
		
		try {//checa por exceções no viewing
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("Game01");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		Lastframetime = getCurrentTime();
		
	}
	public static void updateDisplay() {
		Display.sync(FPS_CAP);//sincroniza o jogo em fps fixo
		Display.update();
		long currentFrametime = getCurrentTime();
		deltat = (currentFrametime - Lastframetime)/1000f;
		Lastframetime = currentFrametime;
		
		//define a taxa de fps e define a atualização de tela a frequência
		
	}
	
	public static float getFrameTimeSeconds() {
		return deltat;
	}
	public static void closeDisplay() {
		Display.destroy();
	}
	private static long getCurrentTime() {
		return Sys.getTime()*1000/Sys.getTimerResolution();
	}
}
