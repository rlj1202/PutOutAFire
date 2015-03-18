package redlaboratory.putOutAFire;

import org.lwjgl.openal.AL10;

import redlaboratory.putOutAFire.game.Game;
import redlaboratory.putOutAFire.game.PutOutAFireGame;
import redlaboratory.putOutAFire.graphics.Render;

public class Core {
	
	public static int FPS_1 = 0;
	public static float FPS_2 = 0.0f;
	
	private static Game curGame;
	private static GameState state;
	
	private static Render render;
	
	static {
		curGame = new PutOutAFireGame();
		curGame.initialize();
		
		state = GameState.IN_GAME;
		
		render = new Render();
	}
	
	public static Game getCurrentGame() {
		return curGame;
	}
	
	public static GameState getGameState() {
		return state;
	}
	
	public static void setCurrentGame(Game curGame) {
		Core.curGame = curGame;
	}
	
	public static void setGameState(GameState state) {
		Core.state = state;
	}
	
	public static void tick() {
		if (state.equals(GameState.MAIN_MENU)) {
			
		} else if (state.equals(GameState.IN_GAME)) {
			curGame.tick();
			render.drawGame(curGame);
			
			AL10.alListener3f(AL10.AL_POSITION, curGame.getViewEntity().getCenterX(), curGame.getViewEntity().getCenterY(), curGame.getViewEntity().getZ());
		}
	}
	
}
