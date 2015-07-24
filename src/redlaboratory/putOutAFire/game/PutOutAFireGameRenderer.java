package redlaboratory.putOutAFire.game;

import static org.lwjgl.opengl.GL11.GL_ONE;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.util.List;

import org.lwjgl.opengl.Display;

import redlaboratory.putOutAFire.Core;
import redlaboratory.putOutAFire.entity.ConsumableEntity;
import redlaboratory.putOutAFire.entity.Entity;
import redlaboratory.putOutAFire.entity.Fire;
import redlaboratory.putOutAFire.entity.Player;
import redlaboratory.putOutAFire.graphics.Color;
import redlaboratory.putOutAFire.graphics.FontModule;
import redlaboratory.putOutAFire.graphics.Render;
import redlaboratory.putOutAFire.graphics.Renderer;
import redlaboratory.putOutAFire.graphics.TextRenderAttribute;
import redlaboratory.putOutAFire.utils.SubtitleManager;

public class PutOutAFireGameRenderer implements Renderer {
	
	private Player player;
	private SubtitleManager subtitleManager;
	
	private int fadeInTicksWhenGameStart;
	private int fireDamageEffectTicks;
	
	public PutOutAFireGameRenderer(Player player) {
		this.player = player;
		
		subtitleManager = new SubtitleManager();
		subtitleManager.addSubtitle("", 16, 480);
		subtitleManager.addSubtitle("- Hey, are you right?", 16, 180);
		subtitleManager.addSubtitle("- You have to get out of there!", 16, 120);
		subtitleManager.addSubtitle("- Hurry!", 16, 60);
		
		fadeInTicksWhenGameStart = 120;
		fireDamageEffectTicks = 0;
	}
	
	@Override
	public void pre(Game game, Core core, Render render) {
		List<Entity> interactingEntities = player.getInteractingEntities();
		
		if (interactingEntities != null) {
			for (Entity entity : interactingEntities) {
				if (entity instanceof Fire) {
					fireDamageEffectTicks = 60;
					
					break;
				}
			}
		}
		
		glTranslatef((float) ((Math.random() - 0.5d) * fireDamageEffectTicks * 20.0f / 60), (float) ((Math.random() - 0.5d) * fireDamageEffectTicks * 20.0f / 60), 0);
	}
	
	@Override
	public void post(Game game, Core core, Render render) {
		render.drawText(16, Display.getHeight() - 32, 16, Color.WHITE, core.FPS_1 + " fps", TextRenderAttribute.LEFT);
		render.drawText(16, Display.getHeight() - 48, 16, Color.WHITE, core.FPS_2 + " fps", TextRenderAttribute.LEFT);
		render.drawText(16, Display.getHeight() - 64, 16, Color.WHITE, Display.getWidth() + " * " + Display.getHeight(), TextRenderAttribute.LEFT);
		
		subtitleManager.tick(render);
		
		List<Entity> interactingEntities = player.getInteractingEntities();
		
		if (interactingEntities != null) {
			for (Entity entity : interactingEntities) {
				if (entity instanceof ConsumableEntity) {
					render.drawText(Display.getWidth() / 2, Display.getHeight() / 2 - player.getHeight() * game.getCamera().ZOOM,
							16, Color.WHITE, "Press F to get", TextRenderAttribute.CENTER);
					render.drawText(Display.getWidth() / 2, Display.getHeight() / 2 - player.getHeight() * game.getCamera().ZOOM - 16,
							16, Color.WHITE, entity.getClass().getSimpleName(), TextRenderAttribute.CENTER);
					
					break;
				}
			}
		}
		
		if (fireDamageEffectTicks > 0) fireDamageEffectTicks--;
		if (fadeInTicksWhenGameStart > 0) fadeInTicksWhenGameStart--;
		
		render.drawQuad(0, 0, 0, Display.getWidth(), Display.getHeight(), new Color(1.0f, 0.0f, 0.0f, fireDamageEffectTicks * 0.7f / 60), false);
		render.drawQuad(0, 0, 0, Display.getWidth(), Display.getHeight(), new Color(0.0f, 0.0f, 0.0f, fadeInTicksWhenGameStart * 1.0f / 120), false);
		
		FontModule.renderString("한글 랜더링 테스트 입니다.", 30, 20, 100, "Nanum");
		FontModule.renderString("This is english rendering test.", 30, 20, 130, "Nanum");
		FontModule.renderString("(ㅇㅅㅇ) 정말 완벽하게 멋져.", 30, 20, 160, "Nanum");
		
		{// Light blending test
			glBlendFunc(GL_SRC_ALPHA, GL_ONE);
			
			render.drawQuad(490, 10, 0, 100, 100, Color.valueOf(0.2f, 0, 0), false);
			render.drawQuad(500, 10, 0, 100, 100, Color.valueOf(0, 0.2f, 0), false);
			render.drawQuad(510, 10, 0, 100, 100, Color.valueOf(0, 0, 0.2f), false);
			
//			render.drawCircle(200, 300, 0, 100, Texture.CHAR_TEX, false);
//			render.drawCircle(400, 400, 0, 100, true, 0, Color.valueOf(1, 1, 1, 0.5f), false);
		}
	}

}
