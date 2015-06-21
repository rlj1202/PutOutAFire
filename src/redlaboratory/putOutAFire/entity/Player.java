package redlaboratory.putOutAFire.entity;

import java.util.ArrayList;

import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.game.Game;
import redlaboratory.putOutAFire.graphics.Texture;
import redlaboratory.putOutAFire.item.Item;

public class Player extends LivingEntity {
	
	private ArrayList<Item> items;
	private Item itemOnHand;
	
	public Player(float x, float y, float z, float health, float maxHealth) {
		super(0.99f, 0.99f, x, y, z, 0, 0, 0, health, maxHealth, Texture.CHAR_TEX, true);
		
		items = new ArrayList<Item>();
		itemOnHand = null;
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}
	
	public Item getItemOnHand() {
		return itemOnHand;
	}
	
	public void setItemOnhand(Item item) {
		itemOnHand = item;
	}
	
	@Override
	public void tick(Game game, Map map) {
		if (itemOnHand != null) itemOnHand.tick(game, map, this);
	}
	
}
