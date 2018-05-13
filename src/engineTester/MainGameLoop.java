package engineTester;

//Importações
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
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;
import renderEngine.DisplayManager;

/**
 * Contém a lógica, o processo de renderização e o loop de jogo
 * instancia classes para criação de display, loader de objetos
 * renderizadores e shaders
 * 
 * contém o loop de execução do jogo
 *
 */
public class MainGameLoop {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Cria o processo de display
		DisplayManager.createDisplay();
		
		//cria um objeto carregador
		Loader carregador = new Loader();
		
		//instancia um shader
		StaticShader shader = new StaticShader();
		
		//cria um objeto renderer
		Renderer renderer = new Renderer(shader);
		
		/**
		 * instancia o rawmodel usando o carregador de OBJ
		 */
		
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
		
		rawModel model = OBJLoader.loadOBJModel("stall", carregador);
		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(carregador.loadTexture("texture")));
		
		Entity entity = new Entity(staticModel,new Vector3f(0,0,-25),0,0,0,1);
		
		Light light = new Light(new Vector3f(0, 0, -20), new Vector3f(1,1,1));
		/**
		 * Instancia uma câmera
		 */
		Camera camera = new Camera();
		
		//continuar na instancia de entidade Entidade entity = new Entidade(static)
		/**
		 * executa os métodos abaixo todo frame
		 */
		while(!Display.isCloseRequested()) {
			//enquanto o método de fechar o display for falso:

			//game logic
			
			//movimentação e transformações geométiricas
			/*entity.increasePosition(0  , 0, -0.1f);
			entity.increaseRotation(0, 1, 0); */ 
			entity.increaseRotation(0, 1, 0);
			camera.move();
			renderer.prepare();
			shader.start();
			shader.LoadLight(light);
			shader.loadViewMatrix(camera);
			renderer.render(entity, shader);
			shader.stop();
			DisplayManager.updateDisplay();
			//invoca o método que deixa o display ativo
		}
		
		shader.cleanUp();
		carregador.cleanUP();
		DisplayManager.closeDisplay();
	}

}
