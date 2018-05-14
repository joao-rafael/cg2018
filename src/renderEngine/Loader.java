//pacote o qual esta classe pertence
package renderEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
//importações
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
 * Contém listas com os IDs de VAOs,
 * VBOs e texturas;
 * 
 * 
 * 
 */
public class Loader {
	private List<Integer> vaos = new ArrayList<Integer>();
	private List<Integer> vbos = new ArrayList<Integer>();
	private List<Integer> textures = new ArrayList<Integer>();
	
	/**Cria um VAO e armazena as posições do vertice no seu slot 0 de
	 * atributos;
	 * 
	 * @param positions
	 * 			Denota as posições de cada vértice
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
	versão antiga 
	*
	*
	*/
	/**
	 * isto carrega um  raw model do VAO
	 * 
	 * 
	 * @param positions
	 * 		posições dos vértices
	 * @param textureCoords
	 * 		coordesnadas das texturas 
	 * @param indices
	 * 		vértices unificados por faces(mesmo que formem mais de um triângulo)
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
	 * como a maioria dos objetos opengl, um vao é gerado
	 * usando um método generador que retorna o seu id
	 * 
	 * Para que esse vao seja utilizado, deve ser tornado ativo;
	 * Apenas um vao pode ser ativado por vez;
	 * 
	 * Para que um novo vao seja ativo(portanto, seja passível de
	 * armazenamento de dados), temos que ligá-lo;
	 * 
	 * @return ID do VAO recém criado
	 */
	private int createVAO() {
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);
		return vaoID;
	}
	
	//deleta os VAOs e VBOs quando o jogo for fechado
	//assim, eles não ficarão alocados em memória de vídeo
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
	 * Armazena os dados de posição dos vértices no atributo 0 do VAO;
	 * Para isso, esses dados devem ser primeiramente armazenados em um
	 * VBO
	 * 
	 * usando métodos da lwjgl:
	 * glBufferData armazena os dados das posições no VBO
	 * GL_STATIC_DRAW indica que esses dados não precisam de alteração
	 * 
	 * Caso fosse requerido a utilização de objetos animados, mudariámos
	 * as posições em cada frame, portanto utilizaríamos:
	 * GL_DYNAMIC_DRAW
	 * 
	 * glVertexAttribPointer() conecta o VBO ao VAO;
	 * Para isso, precisamos:
	 * 	Saber qual atributo(Slot) do VAO será  usado para armazenar os dados do VBO;
	 * 	Que tipo de dados o VBO está guardando;
	 * 	No caso, está guardando vértices;
	 * 	Há de sabser o número de floats usados em cada vértice;
	 * 	Todo vértice tem uma posição 3d denotada por (x, y, z);
	 * 	 
	 * Boa prática:
	 * 	O VBO é desligado depois de utilizado; Não é necessário mas é 
	 * 	considerado um ato de boa organização;
	 * 
	 * 
	 * @param attributeNumber
	 * 		O Número deo atributo do VAO em que os dados do VBO serão
	 * 		armazenados;
	 * @param data
	 * 		Os dados de geometria que serão armazenados no VAO(os tipos
	 * 		de dados que esse VBO em específico carrega);
	 * 		No caso, a posição dos vértices;
	 * @param coordinateSize
	 * 		Tamanho das coordenadas que serão armazenadas no atributo do VAO
	 */
	public void storeDataAttributeList(int attributeNumber, int coordinateSize, float[] data) {
		int vboID = GL15.glGenBuffers();
		vbos.add(vboID);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		//alvo, dado, uso
		
		GL20.glVertexAttribPointer(attributeNumber, coordinateSize, GL11.GL_FLOAT, false, 0, 0);
		//EXPLICAÇÃO
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
	}
	
	/**
	 * Desliga o VAO depois que termina de o utilizar;
	 * caso haja necessidade ou querer de voltar a utilizar e/ou
	 * editar esse vao, temos que ligá-lo novamente
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
	 * Neste caso, usaremos o float buffer porque as variáveis armazenadas
	 * são em formato float(números decimais/pontos flutuantes);
	 * 
	 * Um buffer é, basicamente, um array com ponteiro;
	 * Uma vez que se obtém os dados necessários no buffer, o ponteiro
	 * será incrementado então, 
	 * 
	 * 
	 * @param data
	 * 			-dados de números flutuantes que serão armazenados no Buffer;
	 * 
	 * @return o buffer de float - Esse buffer está pronto a ser carregado
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



