//pacote o qual esta classe pertence
package renderEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
//importa��es
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import models.rawModel;



/**
 * Classe que define o carregamento de modelos
 * Cont�m listas com os IDs de VAOs,
 * VBOs e texturas;
 * 
 * 
 * 
 */
public class Loader {
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();
	
	/**Cria um VAO e armazena as posi��es do vertice no seu slot 0 de
	 * atributos;
	 * 
	 * @param positions
	 * 			Denota as posi��es de cada v�rtice
	 * @return modelo carregado - rawModel;
	 */
	
	/*
	 * public rawModel loadtoVAO(float[] positions float[] textureCoords ,int[] indices){
		
		int vaoID = createVAO();
		storeDataAttributeList(0, 3, positions);
		storeDataAttributeList(1, 2, textureCoords);
		
		unbindVAO();
		
		//retorna novo objeto de classe rawModel;
		return new rawModel(vaoID,positions.length/3);	
	}
	vers�o antiga 
	*
	*
	*/
	/**
	 * isto carrega um  raw model do VAO
	 * 
	 * 
	 * @param positions
	 * 		posi��es dos v�rtices
	 * @param textureCoords
	 * 		coordesnadas das texturas 
	 * @param indices
	 * 		v�rtices unificados por faces(mesmo que formem mais de um tri�ngulo)
	 * @param normals
	 * 		vetor normals
	 * @return
	 */
	 public rawModel loadtoVAO(float[] positions,float[] textureCoords,float[] normals,int[] indices){
	        int vaoID = createVAO();
	        bindIndicesBuffer(indices);
	        storeDataAttributeList(0,3,positions);
	        storeDataAttributeList(1,2,textureCoords);
	        storeDataAttributeList(2,3,normals);
	        unbindVAO();
	        return new rawModel(vaoID,indices.length);
	    }
	/**
	 * Cria um novo VAO e retorna o ID do mesmo
	 * 
	 * como a maioria dos objetos opengl, um vao � gerado
	 * usando um m�todo generador que retorna o seu id
	 * 
	 * Para que esse vao seja utilizado, deve ser tornado ativo;
	 * Apenas um vao pode ser ativado por vez;
	 * 
	 * Para que um novo vao seja ativo(portanto, seja pass�vel de
	 * armazenamento de dados), temos que lig�-lo;
	 * 
	 * @return ID do VAO rec�m criado
	 */
	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	//deleta os VAOs e VBOs quando o jogo for fechado
	//assim, eles n�o ficar�o alocados em mem�ria de v�deo
	public void cleanUP() {
		for(int vao:vaos) {
			GL30.glDeleteVertexArrays(vao);
		}
		for(int vbo:vbos) {
			GL15.glDeleteBuffers(vbo);
		}
		for(int textures:textures) {
			GL11.glDeleteTextures(textures);

		}
	}
	
	/**
	 * Armazena os dados de posi��o dos v�rtices no atributo 0 do VAO;
	 * Para isso, esses dados devem ser primeiramente armazenados em um
	 * VBO
	 * 
	 * usando m�todos da lwjgl:
	 * glBufferData armazena os dados das posi��es no VBO
	 * GL_STATIC_DRAW indica que esses dados n�o precisam de altera��o
	 * 
	 * Caso fosse requerido a utiliza��o de objetos animados, mudari�mos
	 * as posi��es em cada frame, portanto utilizar�amos:
	 * GL_DYNAMIC_DRAW
	 * 
	 * glVertexAttribPointer() conecta o VBO ao VAO;
	 * Para isso, precisamos:
	 * 	Saber qual atributo(Slot) do VAO ser�  usado para armazenar os dados do VBO;
	 * 	Que tipo de dados o VBO est� guardando;
	 * 	No caso, est� guardando v�rtices;
	 * 	H� de sabser o n�mero de floats usados em cada v�rtice;
	 * 	Todo v�rtice tem uma posi��o 3d denotada por (x, y, z);
	 * 	 
	 * Boa pr�tica:
	 * 	O VBO � desligado depois de utilizado; N�o � necess�rio mas � 
	 * 	considerado um ato de boa organiza��o;
	 * 
	 * 
	 * @param attributeNumber
	 * 		O N�mero deo atributo do VAO em que os dados do VBO ser�o
	 * 		armazenados;
	 * @param data
	 * 		Os dados de geometria que ser�o armazenados no VAO(os tipos
	 * 		de dados que esse VBO em espec�fico carrega);
	 * 		No caso, a posi��o dos v�rtices;
	 * @param coordinateSize
	 * 		Tamanho das coordenadas que ser�o armazenadas no atributo do VAO
	 */
	public void storeDataAttributeList(int attributeNumber, int coordinateSize, float[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		//alvo, dado, uso
		
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		//EXPLICA��O
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
	}
	
	/**
	 * Desliga o VAO depois que termina de o utilizar;
	 * caso haja necessidade ou querer de voltar a utilizar e/ou
	 * editar esse vao, temos que lig�-lo novamente
	 */
	private void unbindVAO() {
		GL30.glBindVertexArray(0);
	}
	
	/*para um index buffer:
	 * 
	}*/
    private void bindIndicesBuffer(int[] indices){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }
     
    private IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
	
	//private intBuffer storeDataInIntBuffer
	
	/**Antes de se armazenar dados no VBO, ele precisa estar apto;
	 * Precisa de se estar como buffer;
	 * 
	 * 
	 * Neste caso, usaremos o float buffer porque as vari�veis armazenadas
	 * s�o em formato float(n�meros decimais/pontos flutuantes);
	 * 
	 * Um buffer �, basicamente, um array com ponteiro;
	 * Uma vez que se obt�m os dados necess�rios no buffer, o ponteiro
	 * ser� incrementado ent�o, 
	 * 
	 * 
	 * @param data
	 * 			-dados de n�meros flutuantes que ser�o armazenados no Buffer;
	 * 
	 * @return o buffer de float - Esse buffer est� pronto a ser carregado
	 * em um VBO;
	 * 
	 */
	private FloatBuffer storeDataInFloatBuffer(float[] data) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	/**
	 * Carrega a textura
	 */
	public int loadTexture(String fileName) {
		Texture texture = null;
		try {
			texture =  TextureLoader.getTexture("PNG", new FileInputStream("res/"+fileName+".png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int textureID = texture.getTextureID();
		textures.add(textureID);
		return textureID;
	}
	
}



