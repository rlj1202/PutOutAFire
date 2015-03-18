package redlaboratory.putOutAFire.game;

import static org.lwjgl.opengl.GL11.*;

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
	
	private Game game;
	private Player player;
	private SubtitleManager subtitleManager;
	
	private int fadeInTicksWhenGameStart;
	private int fireDamageEffectTicks;
	
	public PutOutAFireGameRenderer(Game game, Player player) {
		this.game = game;
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
	public void pre(Render render) {
		Entity interactingEntity = player.getInteractingEntity();
		
		if (interactingEntity != null) {
			if (interactingEntity instanceof Fire) {
				fireDamageEffectTicks = 60;
			}
		}
		
		glTranslatef((float) ((Math.random() - 0.5d) * fireDamageEffectTicks * 20.0f / 60), (float) ((Math.random() - 0.5d) * fireDamageEffectTicks * 20.0f / 60), 0);
	}
	
	@Override
	public void post(Render render) {
		render.drawText(16, Display.getHeight() - 32, 16, Color.WHITE, Core.FPS_1 + " fps", TextRenderAttribute.LEFT);
		render.drawText(16, Display.getHeight() - 48, 16, Color.WHITE, Core.FPS_2 + " fps", TextRenderAttribute.LEFT);
		render.drawText(16, Display.getHeight() - 64, 16, Color.WHITE, Display.getWidth() + " * " + Display.getHeight(), TextRenderAttribute.LEFT);
		
		subtitleManager.tick(render);
		
		Entity interactingEntity = player.getInteractingEntity();
		
		if (interactingEntity != null) {
			if (interactingEntity instanceof ConsumableEntity) {
				render.drawText(Display.getWidth() / 2, Display.getHeight() / 2 - player.getHeight() * game.getCamera().ZOOM,
						16, Color.WHITE, "Press F to get", TextRenderAttribute.CENTER);
				render.drawText(Display.getWidth() / 2, Display.getHeight() / 2 - player.getHeight() * game.getCamera().ZOOM - 16,
						16, Color.WHITE, interactingEntity.getClass().getSimpleName(), TextRenderAttribute.CENTER);
			}
		}
		
		if (fireDamageEffectTicks > 0) fireDamageEffectTicks--;
		if (fadeInTicksWhenGameStart > 0) fadeInTicksWhenGameStart--;
		
		render.drawQuad(0, 0, 0, Display.getWidth(), Display.getHeight(), new Color(1.0f, 0.0f, 0.0f, fireDamageEffectTicks * 0.7f / 60));
		render.drawQuad(0, 0, 0, Display.getWidth(), Display.getHeight(), new Color(0.0f, 0.0f, 0.0f, fadeInTicksWhenGameStart * 1.0f / 120));
		
		FontModule.renderString("�ѱ� ������ �׽�Ʈ �Դϴ�.", 30, 20, 100, "Nanum");
		FontModule.renderString("This is english rendering test.", 30, 20, 130, "Nanum");
		FontModule.renderString("(������) ���� �Ϻ��ϰ� ����.", 30, 20, 160, "Nanum");
	}

}
