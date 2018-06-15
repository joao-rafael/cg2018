package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Light;
import toolbox.Maths;

public class TerrainShader extends ShaderProgram {
	private static final String VERTEX_FILE = "src/shaders/terrainvertexShader.txt";
	private static final String FRAGMENT_FILE = "src/shaders/terrainfragmentShader.txt";
	
    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColour;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_skyColor;
 
    public TerrainShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoordinates");
        super.bindAttribute(2, "normal");
    }
    @Override
    protected void getAllUvLocations() {
        location_transformationMatrix = super.getUniformVariable("transformationMatrix");
        location_projectionMatrix = super.getUniformVariable("projectionMatrix");
        location_viewMatrix = super.getUniformVariable("viewMatrix");
        location_lightPosition = super.getUniformVariable("lightPosition");
        location_lightColour = super.getUniformVariable("lightColour");
        location_shineDamper = super.getUniformVariable("shineDamper");
        location_reflectivity = super.getUniformVariable("reflectivity");
        location_skyColor = super.getUniformVariable("skyColor");
         
    }
     
    public void loadShineVariables(float damper,float reflectivity){
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }
     
    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }
     
    public void loadLight(Light light){
        super.LoadVector(location_lightPosition, light.getPosition());
        super.LoadVector(location_lightColour, light.getColour());
    }
     
    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }
     
    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(location_projectionMatrix, projection);
    }
     
    public void loadSkyColor(float r, float g, float b) {
		super.LoadVector(location_skyColor, new Vector3f(r,g,b));
	}
}
