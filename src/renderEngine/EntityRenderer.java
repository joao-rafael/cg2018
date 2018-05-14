package renderEngine;

import java.util.List;
import java.util.Map;

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
import textures.ModelTexture;
import toolbox.Maths;

/**
 * Renderiza uma entidade no viewing
 * 
 *
 */
public class EntityRenderer {

	private StaticShader shader;
	
	public EntityRenderer(StaticShader shader, Matrix4f projectionMatrix) {
		this.shader = shader;
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		//todas as faces de tr�s n�o ser�o renderizadas
		
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}
	
	
	
	
	/**Antes que um VAO seja renderizado, ele precisa estar ativo
	 * para ser ativo, devemos lig�-lo(bind)
	 * 
	 * Tamb�m � necess�rio de se indicar e habilitar os atributos relevantes
	 * do VAO, neste caso, o atributo 0 o qual indica as posi��es do v�rtice;
	 * 
	 * o VAO ser� renderizado usando GLDrawArrays
	 * Dizemos qual � o tipo de shapes e o n�mero de v�rtices que ser�o renderizados;
	 * 
	 * ap�s a renderiza��o, desligamos o VAO  e desabilitamos o atributo(nesse caso, 0);
	 * 
	 * @param model
	 * 		Recebe o objeto de classe rawmodel que ser� renderizado
	 */
	
	
	public void render(Map<TexturedModel, List<Entity>> entities) {
		for(TexturedModel model:entities.keySet()) {
			prepareTexturedModel(model);
			List<Entity> batch = entities.get(model);
			for(Entity entity:batch) {
				prepareInstance(entity);
				GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			}
			unbindTexturedModel();
		}
	}
	
	private void prepareTexturedModel(TexturedModel model) {
		rawModel rawmodel = model.getRawModel();
		GL30.glBindVertexArray(rawmodel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		ModelTexture texture = model.getTexture();
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
	}
	
	private void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void prepareInstance(Entity entidade) {
		Matrix4f transformationMatrix = 
				Maths.createTransformationMatrix(entidade.getPosition(),
						entidade.getRotx(), entidade.getRoty(), entidade.getRotz(), entidade.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
	}
	
	/*
	public void render(Entity entidade, StaticShader shader) {
		TexturedModel model = entidade.getModel();
		rawModel rawmodel = model.getRawModel();
		GL30.glBindVertexArray(rawmodel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		Matrix4f transformationMatrix = 
				Maths.createTransformationMatrix(entidade.getPosition(), entidade.getRotx(), entidade.getRoty(), entidade.getRotz(), entidade.getScale());
		//cria uma matriz de transforma��o da entidade
		
		shader.loadTransformationMatrix(transformationMatrix);
		ModelTexture texture = model.getTexture();
		shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
		//indica: o tipo de shape, o n�mero do primeiro atributo e o n�mero de v�rtices;
		//mostrar a professora :3
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawmodel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		//Neste caso, o n�mero de v�rtices � cedido pelo m�todo get vertex count da classe model;
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}*/
	
}
