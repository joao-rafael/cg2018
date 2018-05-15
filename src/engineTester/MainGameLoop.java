package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import renderEngine.MainRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
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
		
		rawModel model = OBJLoader.loadOBJModel("tree", carregador);
        
        TexturedModel staticModel = new TexturedModel(model,new ModelTexture(carregador.loadTexture("tree")));
         
        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random();
        for(int i=0;i<500;i++){
            entities.add(new Entity(staticModel, new Vector3f(random.nextFloat()*800 - 400,0,random.nextFloat() * -600),0,0,0,3));
        }
        Light light = new Light(new Vector3f(20000,20000,2000),new Vector3f(1,1,1));
        
        Terrain terrain = new Terrain(0, -1,carregador,new ModelTexture(carregador.loadTexture("grass")));
        Terrain terrain2 = new Terrain(1, -1,carregador,new ModelTexture(carregador.loadTexture("grass")));
         
        Camera camera = new Camera();   
        MainRenderer renderer = new MainRenderer();
		/**
		 * Instancia uma câmera
		 */
		
		//continuar na instancia de entidade Entidade entity = new Entidade(static)
		/**
		 * executa os métodos abaixo todo frame
		 */
		while(!Display.isCloseRequested()) {
			//enquanto o método de fechar o display for falso:
			camera.move();
			//game logic
			renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            for(Entity entity:entities){
                renderer.processEntity(entity);
            }
            renderer.render(light, camera);
            DisplayManager.updateDisplay();
			//invoca o método que deixa o display ativo
		}
		renderer.cleanUp();
		carregador.cleanUP();
		DisplayManager.closeDisplay();
	}

}
