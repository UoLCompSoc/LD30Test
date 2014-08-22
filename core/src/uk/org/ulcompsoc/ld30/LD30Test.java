package uk.org.ulcompsoc.ld30;

import uk.org.ulcompsoc.ld30.components.Position;
import uk.org.ulcompsoc.ld30.components.Renderable;
import uk.org.ulcompsoc.ld30.components.Renderable.Shape;
import uk.org.ulcompsoc.ld30.components.Solid;
import uk.org.ulcompsoc.ld30.components.Velocity;
import uk.org.ulcompsoc.ld30.systems.MovementSystem;
import uk.org.ulcompsoc.ld30.systems.RenderingSystem;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

public class LD30Test extends ApplicationAdapter {
	Engine				engine	= null;
	Entity				rect	= null;
	Entity				circ	= null;

	OrthographicCamera	camera	= null;

	@Override
	public void create() {
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		engine = new Engine();
		rect = new Entity();
		circ = new Entity();

		rect.add(new Position(5.0f, 10.0f));
		Renderable r1 = new Renderable();
		r1.color = Color.CYAN;
		r1.shape = Shape.RECTANGLE;
		rect.add(r1);
		rect.add(new Solid());

		circ.add(new Position(30.0f, 30.0f));
		Renderable r2 = new Renderable();
		r2.color = Color.MAROON;
		r2.shape = Shape.CIRCLE;
		circ.add(r2);
		circ.add(new Solid());
		circ.add(new Velocity(10.0f, 10.0f));

		engine.addEntity(rect);
		engine.addEntity(circ);

		engine.addSystem(new MovementSystem(
				new Rectangle(0.0f, 0.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), 0));
		engine.addSystem(new RenderingSystem(camera, 10));
	}

	@Override
	public void render() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		Gdx.gl.glClearColor(0.6f, 0.6f, 0.6f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		engine.update(deltaTime);
	}
}
