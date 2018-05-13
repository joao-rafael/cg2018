package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;

/**
 * Classe que será usada para a matemática bruta do projeto
 * Conterá métodos que realizarão operações matemáticas
 */
public class Maths {
	
	/**
	 * recebe propriedades de uma affine transformation
	 * as aplica e retorna uma matriz de transformação pronta
	 * 
	 * método público e estático
	 * 
	 * @param translation
	 * 	vetor a ser adicionado com o vetor fixo
	 * @param rx
	 * 	rotação eixo x
	 * @param ry
	 * 	rotação eixo y
	 * @param rz
	 * 	rotação eixo z
	 * @param scale
	 * 	escala nova do objeto
	 * @return
	 * 	matriz de transformação
	 */
	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz,
			float scale) {
		//cria um novo objeto tipo matrix4f e retira a idMatrix dele
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		
		//Transformações geomégtricas:
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		
		//retorna uma matriz de transformação pronta
		return matrix;
	}
	
	/**
	 * cria uma matriz de view
	 * 
	 * as posições da camera são negativas uma vez que
	 * o ponto de vista das coordenadas não muda
	 * @param camera
	 * 		instância da classe Camera a qual contém
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
