package redlaboratory.putOutAFire;

import redlaboratory.putOutAFire.game.Game;
import redlaboratory.putOutAFire.graphics.Render;

public class Core {
	
	public int FPS_1 = 0;
	public float FPS_2 = 0.0f;
	
	private Game curGame;
	private Render render;
	
	public Core() {
		render = new Render();
	}
	
	public Game getCurrentGame() {
		return curGame;
	}
	
	public float[] getListenerPosition() {
		return curGame.getListenerPosition();
	}
	
	public void setCurrentGame(Game curGame, boolean initialize) {
		this.curGame = curGame;
		if (initialize) curGame.initialize();
	}
	
	public void tick() {
		if (curGame != null) {
			curGame.tick(this);
			render.drawGame(curGame, this);
		}
	}
	
}
