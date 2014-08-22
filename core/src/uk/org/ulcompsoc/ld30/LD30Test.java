package uk.org.ulcompsoc.ld30;

import uk.org.ulcompsoc.ld30.components.Ball;
import uk.org.ulcompsoc.ld30.components.CheatingAI;
import uk.org.ulcompsoc.ld30.components.MouseTracker;
import uk.org.ulcompsoc.ld30.components.Position;
import uk.org.ulcompsoc.ld30.components.Renderable;
import uk.org.ulcompsoc.ld30.components.Renderable.Shape;
import uk.org.ulcompsoc.ld30.components.Solid;
import uk.org.ulcompsoc.ld30.components.Velocity;
import uk.org.ulcompsoc.ld30.systems.BallBoundsSystem;
import uk.org.ulcompsoc.ld30.systems.BallCollisionSystem;
import uk.org.ulcompsoc.ld30.systems.CheatingAISystem;
import uk.org.ulcompsoc.ld30.systems.MouseTrackingSystem;
import uk.org.ulcompsoc.ld30.systems.MovementSystem;
import uk.org.ulcompsoc.ld30.systems.RenderingSystem;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class LD30Test extends ApplicationAdapter {
	public enum VictoryType {
		RED, BLUE, NONE;
	}

	public static VictoryType	victoryType	= VictoryType.NONE;

	private Engine				engine		= null;
	private Entity				playerBat	= null;
	private Entity				aiBat		= null;
	private Entity				ball		= null;

	private OrthographicCamera	camera		= null;

	@Override
	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		engine = new Engine();
		ball = makeBall();

		playerBat = makeBat(10.0f, 5.0f, Color.CYAN, false);
		aiBat = makeBat(Gdx.graphics.getWidth() - 5.0f - 8.0f, 5.0f, Color.RED, true);

		engine.addEntity(ball);
		engine.addEntity(playerBat);
		engine.addEntity(aiBat);

		engine.addSystem(new MouseTrackingSystem(camera, 0));
		engine.addSystem(new CheatingAISystem(1));
		engine.addSystem(new BallCollisionSystem(2));
		engine.addSystem(new MovementSystem(
				new Rectangle(0.0f, 0.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), 5));
		engine.addSystem(new RenderingSystem(camera, 10));
		engine.addSystem(new BallBoundsSystem(50));
	}

	@Override
	public void render() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0.6f, 0.6f, 0.6f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		engine.update(deltaTime);

		if (!victoryType.equals(VictoryType.NONE)) {
			String victoryMessage = "The " + (victoryType.equals(VictoryType.BLUE) ? "blue" : "red") + " player wins!";

			Gdx.app.log("VICTORY", victoryMessage);
			LD30Test.victoryType = VictoryType.NONE;

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

			ComponentMapper.getFor(Position.class).get(ball).reset();
			Vector2 vel = ComponentMapper.getFor(Velocity.class).get(ball).velocity;
			vel.x = -vel.x;
		}
	}

	public Entity makeBat(float x, float y, Color color, boolean isAI) {
		Entity e = new Entity();
		e.add(new Position(x, y));
		e.add(new Velocity());
		Renderable r1 = new Renderable();
		r1.color = color;
		r1.shape = Shape.RECTANGLE;
		r1.dimension.set(8.0f, 128.0f);
		e.add(r1);
		e.add(new Solid(10.0f));

		if (isAI) {
			e.add(new CheatingAI(ball));
		} else {
			e.add(new MouseTracker(3.0f));
		}

		return e;
	}

	public Entity makeBall() {
		Entity e = new Entity();

		e.add(new Position((float) Gdx.graphics.getWidth() / 2.0f, (float) Gdx.graphics.getHeight() / 2.0f));
		Renderable r2 = new Renderable();
		r2.color = Color.YELLOW;
		r2.shape = Shape.CIRCLE;
		r2.dimension.set(8.0f, 0.0f);
		e.add(r2);
		e.add(new Solid(1.0f));
		e.add(new Velocity(10.0f, 10.0f));
		e.add(new Ball());

		return e;
	}
}
