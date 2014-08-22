package uk.org.ulcompsoc.ld30.systems;

import uk.org.ulcompsoc.ld30.components.MouseTracker;
import uk.org.ulcompsoc.ld30.components.Position;
import uk.org.ulcompsoc.ld30.components.Renderable;
import uk.org.ulcompsoc.ld30.components.Velocity;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * @author Ashley Davis (SgtCoDFish)
 */
public class MouseTrackingSystem extends IteratingSystem {
	private OrthographicCamera				camera		= null;

	private ComponentMapper<Position>		posMapper	= null;
	private ComponentMapper<Velocity>		velMapper	= null;
	private ComponentMapper<MouseTracker>	mtMapper	= null;
	private ComponentMapper<Renderable>		rendMapper	= null;

	@SuppressWarnings("unchecked")
	public MouseTrackingSystem(OrthographicCamera camera, int priority) {
		this(Family.getFor(Position.class, Velocity.class, MouseTracker.class), camera, priority);
	}

	public MouseTrackingSystem(Family family, OrthographicCamera camera, int priority) {
		super(family, priority);
		this.camera = camera;

		posMapper = ComponentMapper.getFor(Position.class);
		velMapper = ComponentMapper.getFor(Velocity.class);
		mtMapper = ComponentMapper.getFor(MouseTracker.class);
		rendMapper = ComponentMapper.getFor(Renderable.class);
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		Vector2 objPosition = posMapper.get(entity).position;
		Vector2 velocity = velMapper.get(entity).velocity;
		float chaseRate = mtMapper.get(entity).chaseRate;

		float height = (rendMapper.has(entity) ? Renderable.getHeightFor(rendMapper.get(entity)) / 2.0f : 8.0f);

		Vector3 screenCoords = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0.0f);
		screenCoords = camera.unproject(screenCoords);
		Vector2 mouseCoords = new Vector2(screenCoords.x, screenCoords.y);

		float heightDiff = mouseCoords.y - (objPosition.y + height);

		velocity.y = (heightDiff / height) * chaseRate;
	}
}
