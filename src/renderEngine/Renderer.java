package renderEngine;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Entity;
import models.TexturedModel;
import models.rawModel;
import shaders.StaticShader;
import toolbox.Maths;

//Declaração da classe - Renderiza um modelo em um viewing
public class Renderer {
	
	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE  = 100;
	private Matrix4f projectionMatrix;
	
	public Renderer(StaticShader shader) {
		createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	/**
	 * Método que prepara o viewing para que o próximo rendering seja chamado;
	 * deve ser chamado sempre, todo frame
	 * glClear color definirá que cor será exibida ao limpar a tela no início de
	 * cada frame
	 * 
	 */
	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(1, 0, 0, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	
	/**Antes que um VAO seja renderizado, ele precisa estar ativo
	 * para ser ativo, devemos ligá-lo(bind)
	 * 
	 * Também é necessário de se indicar e habilitar os atributos relevantes
	 * do VAO, neste caso, o atributo 0 o qual indica as posições do vértice;
	 * 
	 * o VAO será renderizado usando GLDrawArrays
	 * Dizemos qual é o tipo de shapes e o número de vértices que serão renderizados;
	 * 
	 * após a renderização, desligamos o VAO  e desabilitamos o atributo(nesse caso, 0);
	 * 
	 * @param model
	 * 		Recebe o objeto de classe rawmodel que será renderizado
	 */
	public void render(Entity entidade, StaticShader shader) {
		TexturedModel model = entidade.getModel();
		rawModel rawmodel = model.getRawModel();
		GL30.glBindVertexArray(rawmodel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		Matrix4f transformationMatrix = 
				Maths.createTransformationMatrix(entidade.getPosition(), entidade.getRotx(), entidade.getRoty(), entidade.getRotz(), entidade.getScale());
		//cria uma matriz de transformação da entidade
		
		shader.loadTransformationMatrix(transformationMatrix);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
		//indica: o tipo de shape, o número do primeiro atributo e o número de vértices;
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, rawmodel.getVertexCount());
		//Neste caso, o número de vértices é cedido pelo método get vertex count da classe model;
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	/**
	 * Cria uma matriz de projeção - será usada para ajustar e configurar
	 * o processo de viewing
	 */
	private void createProjectionMatrix() {
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
 
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
	}
		
}
