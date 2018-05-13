package models;

import textures.ModelTexture;

public class TexturedModel {
	private rawModel Model;
	private ModelTexture texture;
	
	public TexturedModel(rawModel model, ModelTexture texture) {
		this.Model = model;
		this.texture = texture;
	}

	public rawModel getRawModel() {
		return Model;
	}

	public ModelTexture getTexture() {
		return texture;
	}
	
	
}
