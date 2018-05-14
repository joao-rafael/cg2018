package engineTester;

//Importa��es
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import models.rawModel;
import objloader.ModelData;
import objloader.OBJfileLoader;
import renderEngine.Loader;
import renderEngine.MainRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import textures.ModelTexture;
import renderEngine.DisplayManager;

/**
 * Cont�m a l�gica, o processo de renderiza��o e o loop de jogo
 * instancia classes para cria��o de display, loader de objetos
 * renderizadores e shaders
 * 
 * cont�m o loop de execu��o do jogo
 *
 */
public class MainGameLoop {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Cria o processo de display
		DisplayManager.createDisplay();
		
		//cria um objeto carregador
		Loader carregador = new Loader();
		
		/**
		 * OBJ.fileLoader.LoadOBJ("tree");
		 * 
		 * ModelData data = OBJFileLoader.loadOBJ("nome");
		 * 
		 * rawModel model = loader.loadtoVAO(data.getvertices
		 * 
		 * ModelData data = OBJfileLoader.loadOBJ("nome");
		 * rawModel novo_model= carregador.loadtoVAO(positions, textureCoords, indices);
		 * TexturedModel staticModel = new TexturedModel(d, new ModelTexture(carregador.loadTexture("texture")));
		 * 
		 * 
		 */
		MainRenderer renderer = new MainRenderer();
		
		rawModel model = OBJLoader.loadOBJModel("dragon", carregador);
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(carregador.loadTexture("stalltexture")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);
		
		Entity entity = new Entity(staticModel,new Vector3f(0,0,-25),0,0,0,1);
		
		Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1,1,1));
		/**
		 * Instancia uma c�mera
		 */
		Camera camera = new Camera();
		
		//continuar na instancia de entidade Entidade entity = new Entidade(static)
		/**
		 * executa os m�todos abaixo todo frame
		 */
		while(!Display.isCloseRequested()) {
			//enquanto o m�todo de fechar o display for falso:
			camera.move();
			//game logic
			renderer.processEntity(entity);
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
			//movimenta��o e transforma��es geom�tiricas
			/*entity.increasePosition(0  , 0, -0.1f);
			entity.increaseRotation(0, 1, 0); */ 
			entity.increaseRotation(0, 1, 0);
			DisplayManager.updateDisplay();
			//invoca o m�todo que deixa o display ativo
		}
		renderer.cleanUp();
		carregador.cleanUP();
		DisplayManager.closeDisplay();
	}

}
