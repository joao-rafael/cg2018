package shaders;

import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

//classe extendida de outra classe
/**
 * Parte concreta da implementação dos programas de shaders
 * contém métodos de execução dos shaders e variáveis uniformes;
 *
 */
public class StaticShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "src/shaders/vertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/fragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	
	/**
	 * Propriedades de Luz
	 */
	private int location_lightPosition;
	private int location_lightColor;
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stu
		super.bindAttribute(0, "position");
	}

	@Override
	protected void bindAttributes() {
		// TODO Auto-generated method stub
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	/**
	 * Pega a localização de todas as variáveis uniformes
	 * 
	 */
	protected void getAllUvLocations() {
		// TODO Auto-generated method stub
		location_transformationMatrix = super.getUniformVariable("transformationMatrix");
		location_projectionMatrix = super.getUniformVariable("projectionMatrix");
		location_viewMatrix = super.getUniformVariable("viewMatrix");
		location_lightColor = super.getUniformVariable("LightColor");
		location_lightPosition = super.getUniformVariable("LightPosition");
	}
	
	/**
	 * Métodos de câmera e projeção
	 * @param matrix
	 */
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
		
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
		
	}
	
	/**
	 * Carrega as variáveis de luz para os shaders
	 */
	public void LoadLight(Light light) {
		super.LoadVector(location_lightPosition, light.getPosition());
		super.LoadVector(location_lightColor, light.getColour());
	}
}
