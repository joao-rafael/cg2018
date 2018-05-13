package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;

/**
 * Classe que ser� usada para a matem�tica bruta do projeto
 * Conter� m�todos que realizar�o opera��es matem�ticas
 */
public class Maths {
	
	/**
	 * recebe propriedades de uma affine transformation
	 * as aplica e retorna uma matriz de transforma��o pronta
	 * 
	 * m�todo p�blico e est�tico
	 * 
	 * @param translation
	 * 	vetor a ser adicionado com o vetor fixo
	 * @param rx
	 * 	rota��o eixo x
	 * @param ry
	 * 	rota��o eixo y
	 * @param rz
	 * 	rota��o eixo z
	 * @param scale
	 * 	escala nova do objeto
	 * @return
	 * 	matriz de transforma��o
	 */
	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz,
			float scale) {
		//cria um novo objeto tipo matrix4f e retira a idMatrix dele
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		
		//Transforma��es geom�gtricas:
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		
		//retorna uma matriz de transforma��o pronta
		return matrix;
	}
	
	/**
	 * cria uma matriz de view
	 * 
	 * as posi��es da camera s�o negativas uma vez que
	 * o ponto de vista das coordenadas n�o muda
	 * @param camera
	 * 		inst�ncia da classe Camera a qual cont�m
	 * @return
	 */
	public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix,
                viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
        Vector3f cameraPos = camera.getPosition();
        Vector3f negativeCameraPos = new Vector3f(-cameraPos.x,-cameraPos.y,-cameraPos.z);
        Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
        return viewMatrix;
    }
	
}
