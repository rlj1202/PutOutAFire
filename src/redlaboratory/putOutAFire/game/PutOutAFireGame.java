package redlaboratory.putOutAFire.game;

import org.lwjgl.opengl.Display;

import redlaboratory.putOutAFire.Block;
import redlaboratory.putOutAFire.Map;
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
		
		round_1 = new Map(20, 20, 2, this);
		player = new Player(1, 1, 0.5f, 100.0f, 100.0f, round_1);
		round_1_renderer = new PutOutAFireGameRenderer(this, player);
		
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
		round_1.addEntity(new FireExtinguisherHolder(0.5f, 19.3f, 0.2f, 0.5f, round_1));
		round_1.addEntity(new Fire(1.5f, 7.0f, 0.0f, 0.5f, round_1));
		round_1.addEntity(new Fire(1.0f, 4.0f, 2.0f, 0.5f, round_1));
		round_1.addEntity(new MedicKit(0.5f, 19.3f, 3.2f, 0.5f, 500, round_1));
		
		setMap(round_1);
		setViewEntity(player);
		
		addRenderer(round_1_renderer);
	}
	
	@Override
	public void tick() {
		round_1.tick();
		
		getCamera().X = getCamera().ZOOM * player.getX() - Display.getWidth() / 2 + getCamera().ZOOM * player.getWidth() / 2;
		getCamera().Y = getCamera().ZOOM * player.getY() - Display.getHeight() / 2 + getCamera().ZOOM * player.getHeight() / 2;
		
		if (player.getHealth() == 0) {
			initialize();
		}
	}
	
}
