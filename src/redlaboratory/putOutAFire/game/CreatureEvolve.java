package redlaboratory.putOutAFire.game;

import redlaboratory.putOutAFire.Core;

public class CreatureEvolve extends Game {

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick(Core core) {
		// TODO Auto-generated method stub
		
	}
//	
//	private Creature creatureA;
//	private Creature creatureB;
//	private int creatureAScore;
//	private int creatureBScore;
//	private Map map;
//	
//	@Override
//	public void initialize() {
//		map = new Map(20, 20, 2);
//		creatureA = new Creature(0.99f, 0.99f, 1, 1, 1, 100, 100, map, Texture.CHAR_TEX);
//		creatureB = new Creature(0.99f, 0.99f, 18, 18, 1, 100, 100, map, Texture.CHAR_TEX);
//		creatureAScore = 0;
//		creatureBScore = 0;
//		
//		{
//			for (int y = 0; y < map.getLength(); y++) {
//				for (int x = 0; x < map.getWidth(); x++) {
//					map.setBlock(x, y, 0, Block.STONE_TILE_1);
//				}
//			}
//		}
//		
//		map.addEntity(creatureA);
//		map.addEntity(creatureB);
//		
//		setMap(map);
//		setViewEntity(creatureA);
//		
//		addRenderer(new Renderer() {
//			
//			@Override
//			public void pre(Game game, Core core, Render render) {
//				
//			}
//
//			@Override
//			public void post(Game game, Core core, Render render) {
//				render.drawText(16, Display.getHeight() - 32, 16, Color.WHITE, Core.FPS_1 + " fps", TextRenderAttribute.LEFT);
//				render.drawText(16, Display.getHeight() - 48, 16, Color.WHITE, Core.FPS_2 + " fps", TextRenderAttribute.LEFT);
//				render.drawText(16, Display.getHeight() - 64, 16, Color.WHITE, Display.getWidth() + " * " + Display.getHeight(), TextRenderAttribute.LEFT);
//				
//				Creature creature = null;
//				String name = "";
//				if (CreatureEvolve.this.getViewEntity().equals(creatureA)) {
//					creature = creatureA;
//					name = "Creature A";
//				} else if (CreatureEvolve.this.getViewEntity().equals(creatureB)) {
//					creature = creatureB;
//					name = "Creature B";
//				}
//				render.drawText(16, Display.getHeight() - 128, 16, Color.WHITE, "A:" + CreatureEvolve.this.creatureAScore, TextRenderAttribute.LEFT);
//				render.drawText(16, Display.getHeight() - 144, 16, Color.WHITE, "B:" + CreatureEvolve.this.creatureBScore, TextRenderAttribute.LEFT);
//				
//				render.drawText(16, 64, 8, Color.WHITE, Arrays.toString(creature.network.getOutputValues()), TextRenderAttribute.LEFT);
//				render.drawText(16, 48, 8, Color.WHITE, Arrays.toString(creature.network.weights), TextRenderAttribute.LEFT);
//				render.drawText(16, 32, 16, Color.WHITE, name, TextRenderAttribute.LEFT);
//				render.drawText(16, 16, 16, Color.WHITE, "" + creature.angle, TextRenderAttribute.LEFT);
//			}
//			
//		});
//	}
//	
//	public void tick() {
//		creatureA.network.setInputValues(new float[] {creatureB.getCenterX() - creatureA.getCenterX(), creatureB.getCenterY() - creatureA.getCenterY(), 1, 1});
//		creatureB.network.setInputValues(new float[] {creatureA.getCenterX() - creatureB.getCenterX(), creatureA.getCenterY() - creatureB.getCenterY(), 1, 1});
//		
//		while (Keyboard.next()) {
//			if (Keyboard.getEventKey() == Keyboard.KEY_C) {
//				if (!Keyboard.getEventKeyState()) {
//					if (getViewEntity().equals(creatureA)) {
//						setViewEntity(creatureB);
//					} else if (getViewEntity().equals(creatureB)) {
//						setViewEntity(creatureA);
//					}
//				}
//			}
//			
//			if (Keyboard.getEventKey() == Keyboard.KEY_G) {
//				if (!Keyboard.getEventKeyState()) {
//					int[] neuronAmounts = new int[] {4, 4, 4, 4};
//					float[] weights = new float[NeuralNetwork.getWeightAmount(neuronAmounts)];
//					
//					for (int i = 0; i < weights.length; i++) {
//						weights[i] = (float) Math.random() * 2 - 1;
//					}
//					
//					if (getViewEntity().equals(creatureA)) {
//						creatureA.network = new NeuralNetwork(neuronAmounts, weights);
//					} else if (getViewEntity().equals(creatureB)) {
//						creatureB.network = new NeuralNetwork(neuronAmounts, weights);
//					}
//				}
//			}
//		}
//		
//		getCamera().X = getCamera().ZOOM * getViewEntity().getX() - Display.getWidth() / 2 + getCamera().ZOOM * getViewEntity().getWidth() / 2;
//		getCamera().Y = getCamera().ZOOM * getViewEntity().getY() - Display.getHeight() / 2 + getCamera().ZOOM * getViewEntity().getHeight() / 2;
//	}
//	
//	private class Bullet extends MovingEntity {
//		
//		private Creature shooter;
//		
//		public Bullet(float x, float y, float z, float xS, float yS, float zS, Map curMap, Creature shooter) {
//			super(0.4f, 0.4f, x, y, z, xS, yS, zS, curMap, Texture.TEST_TEX_1, true);
//			
//			this.shooter = shooter;
//		}
//		
//		@Override
//		public void tick() {
//			x += xS;
//			y += yS;
//			z += zS;
//			
//			for (Entity entity : curMap.getEntities()) {
//				if (entity instanceof Creature) {
//					Creature creature = (Creature) entity;
//					
//					if (getArea().isCollided(creature.getArea())) {
//						curMap.removeEntity(this);
//						
//						if (!creature.equals(shooter)) {
//							if (shooter.equals(CreatureEvolve.this.creatureA)) {
//								CreatureEvolve.this.creatureAScore++;
//							} else if (shooter.equals(CreatureEvolve.this.creatureB)) {
//								CreatureEvolve.this.creatureBScore++;
//							}
//						}
//						
//						return;
//					}
//				}
//			}
//			
//			if (getCenterX() < 0 || getCenterX() >= curMap.getWidth() ||
//					getCenterY() < 0 || getCenterY() >= curMap.getLength()) {
//				curMap.removeEntity(this);
//				
//				return;
//			}
//		}
//		
//	}
//	
//	private class Creature extends LivingEntity {
//		
//		private NeuralNetwork network;
//		
//		private float angle = 0;
//		
//		public Creature(float width, float height, float x, float y, float z, float health, float maxHealth, Map curMap, Texture texture) {
//			super(width, height, x, y, z, 0, 0, 0, health, maxHealth, curMap, texture, true);
//			
//			int[] neuronAmounts = new int[] {4, 4, 5, 4, 4};
//			float[] weights = new float[NeuralNetwork.getWeightAmount(neuronAmounts)];
//			
//			for (int i = 0; i < weights.length; i++) {
//				weights[i] = (float) Math.random() * 2 - 1;
//			}
//			
//			network = new NeuralNetwork(neuronAmounts, weights);
//		}
//		
//		@Override
//		public void tick() {
//			float[] values = network.getOutputValues();
//			
//			boolean moveForward = (values[0] >= 0.5f) ? (true) : (false);
//			boolean turnRight = (values[1] >= 0.5f) ? (true) : (false);
//			boolean turnLeft = (values[2] >= 0.5f) ? (true) : (false);
//			boolean shoot = (values[3] >= 0.5f) ? (true) : (false);
//			
//			if (turnRight) {
//				angle += 10;
//			}
//			
//			if (turnLeft) {
//				angle -= 10;
//			}
//			
//			if (moveForward) {
//				xS += Math.cos(Math.toRadians(angle)) * 0.01f;
//				yS += Math.sin(Math.toRadians(angle)) * 0.01f;
//			}
//			
//			if (shoot) {
//				curMap.addEntity(new Bullet(x, y, z, 0.05f, 0.05f, 0, curMap, this));
//			}
//			
//			//
//			
//			super.tick();
//			
//			collisionTestMap();
//		}
//		
//		private void collisionTestMap() {
//			if (curMap == null) return;
//			
//			if (x < 0) {
//				x = 0;
//				xS = 0;
//			}
//			
//			if (y < 0) {
//				y = 0;
//				yS = 0;
//			}
//			
//			if (x + width > curMap.getWidth()) {
//				x = curMap.getWidth() - width;
//				xS = 0;
//			}
//			
//			if (y + height > curMap.getLength()) {
//				y = curMap.getLength() - height;
//				yS = 0;
//			}
//		}
//		
//	}
//	
}
