package shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public abstract class ShaderProgram {
	
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	//cria um float buffer de 16 posições uma vez que as matrizes são 4x4
	
	//construtor do shader
	public ShaderProgram(String vertexFile,String fragmentFile){
		//usa-se os métodos de ibtenção do shaderid para os VS e FS
        vertexShaderID = loadShader(vertexFile,GL20.GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFile,GL20.GL_FRAGMENT_SHADER);
        
        //define um id para os dois shaders unidos
        //depois liga os dois shaders em um só programa
        programID = GL20.glCreateProgram();
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        
        //liga-os e valida-os
        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
        getAllUvLocations();
    }
     
	protected abstract void getAllUvLocations();
	
	protected int getUniformVariable(String uniVarName) {
		//
		return GL20.glGetUniformLocation(programID, uniVarName);
	}
    public void start(){
        GL20.glUseProgram(programID);
        //inicia o programa 
    }
     
    public void stop(){
        GL20.glUseProgram(0);
        //para o programa usando o mesmo método do GL20
    }
     
    public void cleanUp(){
    	//desfaz e limpa os dois shaders
    	//e deleta os dois shaders para depois deletar o programa
        stop();
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }
     
    protected abstract void bindAttributes();
     
    protected void bindAttribute(int attribute, String variableName){
    	//liga o atributo x do VAO e pega o nome da vriável do shader
    	//e posteriormente os liga
        GL20.glBindAttribLocation(programID, attribute, variableName);
    }
     
    protected void loadFloat(int location, float value) {
    	//carrega a uniform variable de valor float
    	GL20.glUniform1f(location, value);
    	
    }
    
    protected void LoadVector(int location, Vector3f vector) {
    	
    	GL20.glUniform3f(location, vector.x, vector.y, vector.z);
    }
    
    protected void loadBoolean(int location, boolean value) {
    	float toLoad = 0;
    	if(value) {
    		toLoad = 1;
    	}
    	GL20.glUniform1f(location, toLoad);
    }
    
    /**
     * LoadMatrix
     * Carrega uma matriz(a matriz da variável uniforme) no código do shader
     * primeiramente, armazena a matriz no buffer de matrizes
     * dá-se flip no float buffer(matrixBuffer) para prepará-lo a utilização(leitura)
     * 
     * o método do OpenGL 2.0 Uniform Matrix 4 
     * pega a matrix na localização da Uniform Variable;
     * 
     * @param location
     * 		Denota a localização 
     * @param matrix
     * 
     * 
     */
    protected void loadMatrix(int location, Matrix4f matrix) {
    	matrix.store(matrixBuffer);	
    	matrixBuffer.flip();
    	GL20.glUniformMatrix4(location, false, matrixBuffer);
    	//
    }
    
    private static int loadShader(String file, int type){
        StringBuilder shaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null){
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        }catch(IOException e){
            e.printStackTrace();
            System.exit(-1);
        }
        
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS )== GL11.GL_FALSE){
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        
        return shaderID;
    }
}
