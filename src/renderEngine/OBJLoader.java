package renderEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import models.rawModel;


public class OBJLoader {
	
	
	public static rawModel loadOBJModel(String FileName, Loader loader) {
		FileReader fr = null;
		try {
			fr = new FileReader(new File("res/"+FileName+".obj"));
		}catch (FileNotFoundException e){
			System.err.println("Impossivel carregar o arquivo!");
			e.printStackTrace();
		}
		
		//o buffered reader permite ler o arquivo
		BufferedReader reader = new BufferedReader(fr);
		
		//a string se chama line porqu� receber� uma linha inteira
		String line;
		
		//lista de atributos que ser�o lidos
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		
		//o loader precisa dos dados em arrays
		float[] verticesArray = null;
		float[] normalsArray = null;
		float[] textureArray = null;
		int[] indicesArray = null;
		
		//try catch para ler o arquivo
		try {
			while(true) {
				line = reader.readLine();
				String[] currentLine = line.split(" ");
				//vertices
				if(line.startsWith("v ")) {
					//vertice
					//pega os pr�ximos 3 valores e p�e em um vetor de 3 floats
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					//adiciona a lista com os v�rtices
					vertices.add(vertex);
				//texturas
				}else if(line.startsWith("vt ")){
					//coordenada de textura
					//pega os pr�ximos 2 valores e p�e em um vetor de 2 floats
					Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]));
					//adicona a lista com as texturas
					textures.add(texture);
				//normals
				}else if(line.startsWith("vn ")) {
					//pega os pr�ximos 3 valores e p�e em um vetor de 3 floats
					//textura - n�o sei o que fazer
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]),
							Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]));
					normals.add(normal);
				}else if (line.startsWith("f ")) {
					//defini��es de faces
					textureArray = new float[vertices.size() * 2];
					normalsArray = new float[vertices.size() * 3];
					break;
				}
			}
			
			while(line!=null) {
				if(!line.startsWith("f ")) {
					line = reader.readLine();
					continue;
				}
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				
				processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
				processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
				processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
				line = reader.readLine();
			}
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		verticesArray = new float[vertices.size()*3];
		indicesArray = new int[indices.size()];
		
		int vertexPointer = 0;
		for(Vector3f vertex:vertices) {
			verticesArray[vertexPointer++] = vertex.x;
			verticesArray[vertexPointer++] = vertex.y;
			verticesArray[vertexPointer++] = vertex.z;
		}
		
		for(int i = 0; i < indices.size(); i++) {
			indicesArray[i] = indices.get(i);
		}
		
		return loader.loadtoVAO(verticesArray, textureArray, normalsArray, indicesArray);
	}
	
	
	private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures, List<Vector3f> normals,
			float[] textureArray, float[] normalsArray){
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currentVertexPointer);
		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
		textureArray[currentVertexPointer*2] = currentTex.x;
		textureArray[currentVertexPointer*2+1] = 1 - currentTex.y;
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
		normalsArray[currentVertexPointer*3] = currentNorm.x;
		normalsArray[currentVertexPointer*3+1] = currentNorm.y;
		normalsArray[currentVertexPointer*3+2] = currentNorm.z;
	}
	
	
}
