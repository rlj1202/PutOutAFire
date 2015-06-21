package redlaboratory.putOutAFire.graphics;

import redlaboratory.putOutAFire.Core;
import redlaboratory.putOutAFire.game.Game;

public interface Renderer {
	
	void pre(Game game, Core core, Render render);
	
	void post(Game game, Core core, Render render);
	
}
