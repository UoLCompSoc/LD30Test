package uk.org.ulcompsoc.ld30.systems;

import uk.org.ulcompsoc.ld30.components.Position;
import uk.org.ulcompsoc.ld30.components.Renderable;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Ashley Davis (SgtCoDFish)
 */
public class RenderingSystem extends IteratingSystem {
	private OrthographicCamera			camera			= null;
	private ShapeRenderer				renderer		= null;

	private ComponentMapper<Position>	posMapper		= null;
	private ComponentMapper<Renderable>	renderMapper	= null;

	@SuppressWarnings("unchecked")
	public RenderingSystem(OrthographicCamera camera, int priority) {
		this(Family.getFor(Renderable.class, Position.class), camera, priority);
	}

	protected RenderingSystem(Family family, OrthographicCamera camera, int priority) {
		super(family, priority);

		posMapper = ComponentMapper.getFor(Position.class);
		renderMapper = ComponentMapper.getFor(Renderable.class);

		renderer = new ShapeRenderer();
		this.camera = camera;
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		Renderable r = renderMapper.get(entity);
		Vector2 pos = posMapper.get(entity).position;

		renderer.setProjectionMatrix(camera.combined);
		renderer.begin(ShapeType.Filled);
		renderer.setColor(r.color);

		switch (r.shape) {
		case RECTANGLE: {
			renderer.rect(pos.x, pos.y, r.dimension.x, r.dimension.y);
			break;
		}

		case CIRCLE: {
			renderer.circle(pos.x, pos.y, r.dimension.x);
			break;
		}
		}

		renderer.end();
	}
}
