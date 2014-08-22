package uk.org.ulcompsoc.ld30.systems;

import uk.org.ulcompsoc.ld30.components.Position;
import uk.org.ulcompsoc.ld30.components.Velocity;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Ashley Davis (SgtCoDFish)
 */
public class MovementSystem extends IteratingSystem {
	private Rectangle					bounds		= null;

	private ComponentMapper<Position>	posMapper	= null;
	private ComponentMapper<Velocity>	velMapper	= null;

	@SuppressWarnings("unchecked")
	public MovementSystem(Rectangle bounds, int priority) {
		this(Family.getFor(Position.class, Velocity.class), bounds, priority);
	}

	protected MovementSystem(Family family, Rectangle bounds, int priority) {
		super(family, priority);

		posMapper = ComponentMapper.getFor(Position.class);
		velMapper = ComponentMapper.getFor(Velocity.class);
		this.bounds = bounds;
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		Vector2 p = posMapper.get(entity).position;
		Vector2 v = velMapper.get(entity).velocity;

		p.x += v.x;
		p.y += v.y;

		if (p.x < bounds.x || p.x > bounds.width) {
			v.x = -v.x;
		}

		if (p.y < bounds.y || p.y > bounds.height) {
			v.y = -v.y;
		}
	}
}
