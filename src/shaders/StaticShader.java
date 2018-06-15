package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

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
	
	private int location_shineDamper;
	private int location_reflectivity;
	
	private int location_useFakeLighting;
	
	private int location_skyColor;
	
	
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
		location_lightColor = super.getUniformVariable("lightColor");
		location_lightPosition = super.getUniformVariable("lightPosition");
		location_shineDamper = super.getUniformVariable("shineDamper");
		location_reflectivity = super.getUniformVariable("reflectivity");
		location_useFakeLighting = super.getUniformVariable("useFakeLighting");
		location_skyColor = super.getUniformVariable("skyColor");
	}
	
	public void loadSkyColor(float r, float g, float b) {
		super.LoadVector(location_skyColor, new Vector3f(r,g,b));
	}
	public void loadFakeLightVariable(boolean useFake) {
		super.loadBoolean(location_useFakeLighting, useFake);
	}
	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
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
