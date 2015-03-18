package redlaboratory.putOutAFire;

import redlaboratory.putOutAFire.graphics.Texture;

public class Block {
	
	public static Block STONE_TILE_1;
	
	public static Block BLOCK_1;
	public static Block BLOCK_2;
	
	static {
		STONE_TILE_1 = new Block(Texture.STONE_TILE_1, true, 0.95f);
		
		BLOCK_1 = new Block(Texture.TILE_TEX_1, true, 0.95f);
		BLOCK_2 = new Block(Texture.TILE_TEX_2, false, 0.95f);
	}
	
	private final Texture texture;
	private final boolean throughable;
	private final float frictionalForce;
	
	public Block(Texture texture, boolean throughable, float frictionalForce) {
		this.texture = texture;
		this.throughable = throughable;
		this.frictionalForce = frictionalForce;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public boolean isThroughable() {
		return throughable;
	}
	
	public float getFrictionalForce() {
		return frictionalForce;
	}
	
}
