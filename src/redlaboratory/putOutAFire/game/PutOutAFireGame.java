package redlaboratory.putOutAFire.game;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import redlaboratory.putOutAFire.Block;
import redlaboratory.putOutAFire.Core;
import redlaboratory.putOutAFire.Map;
import redlaboratory.putOutAFire.entity.ConsumableEntity;
import redlaboratory.putOutAFire.entity.Entity;
import redlaboratory.putOutAFire.entity.Fire;
import redlaboratory.putOutAFire.entity.FireExtinguisherHolder;
import redlaboratory.putOutAFire.entity.MedicKit;
import redlaboratory.putOutAFire.entity.Player;
import redlaboratory.putOutAFire.graphics.Renderer;

public class PutOutAFireGame extends Game {
	
	private Player player;
	private Map round_1;
	private Renderer round_1_renderer;
	
	@Override
	public void initialize() {
		super.initialize();
		
		round_1 = new Map(20, 20, 2);
		player = new Player(1, 1, 0.5f, 100.0f, 100.0f);
		round_1_renderer = new PutOutAFireGameRenderer(player);
		
		{
			for (int y = 0; y < round_1.getLength(); y++) {
				for (int x = 0; x < round_1.getWidth(); x++) {
					round_1.setBlock(x, y, 0, Block.STONE_TILE_1);
				}
			}
			
			for (int x = 0; x < 15; x++) {
				round_1.setBlock(x, 3, 1, Block.BLOCK_2);
			}
		}
		
		round_1.addEntity(player);
		round_1.addEntity(new FireExtinguisherHolder(0.5f, 19.3f, 0.2f, 0.5f));
		round_1.addEntity(new Fire(1.5f, 7.0f, 0.0f, 0.5f));
		round_1.addEntity(new Fire(1.0f, 4.0f, 2.0f, 0.5f));
		round_1.addEntity(new MedicKit(0.5f, 19.3f, 3.2f, 0.5f, 500));
		
		setMap(round_1);
		setViewEntity(player);
		
		addRenderer(round_1_renderer);
	}
	
	@Override
	public void tick(Core core) {
		getMap().tick(this);
		
		{
			List<Entity> interactingEntities = player.getInteractingEntities();
			
			if (interactingEntities == null) return;
			
			for (Entity entity : interactingEntities) {
				if (entity instanceof ConsumableEntity) {
					if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
						ConsumableEntity ce = (ConsumableEntity) entity;
						
						if (ce.consume(player)) {
							getMap().removeEntity(ce);
							
							break;
						}
					}
				}
			}
		}
		
		{
			int angle = 0;
			int xD = 0;// Direction
			int yD = 0;
			if (Keyboard.isKeyDown(Keyboard.KEY_W))
				yD += 1;
			if (Keyboard.isKeyDown(Keyboard.KEY_S))
				yD -= 1;
			if (Keyboard.isKeyDown(Keyboard.KEY_A))
				xD -= 1;
			if (Keyboard.isKeyDown(Keyboard.KEY_D))
				xD += 1;

			if (!(xD == 0 && yD == 0)) {
				if (yD == 0) {
					if (xD == -1) {
						angle = 180;
					} else if (xD == 0) {
						angle = 0;
					} else if (xD == 1) {
						angle = 0;
					}
				} else {
					if (xD == -1) {
						angle = 135 * yD;
					} else if (xD == 0) {
						angle = 90 * yD;
					} else if (xD == 1) {
						angle = 45 * yD;
					}
				}

				if (!Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
					player.setXS((float) (player.getXS() + Math.cos(Math.toRadians(angle)) * 0.01f));
					player.setYS((float) (player.getYS() + Math.sin(Math.toRadians(angle)) * 0.01f));
				} else {
					player.setXS((float) (player.getXS() + Math.cos(Math.toRadians(angle)) * 0.002f));
					player.setYS((float) (player.getYS() + Math.sin(Math.toRadians(angle)) * 0.002f));
				}
			}
		}
		
		getCamera().X = getCamera().ZOOM * player.getX() - Display.getWidth() / 2 + getCamera().ZOOM * player.getWidth() / 2;
		getCamera().Y = getCamera().ZOOM * player.getY() - Display.getHeight() / 2 + getCamera().ZOOM * player.getHeight() / 2;
		
		if (player.getHealth() == 0) {
			initialize();
		}
	}
	
}
