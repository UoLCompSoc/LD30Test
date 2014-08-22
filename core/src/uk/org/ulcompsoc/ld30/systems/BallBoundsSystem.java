package uk.org.ulcompsoc.ld30.systems;

import uk.org.ulcompsoc.ld30.LD30Test;
import uk.org.ulcompsoc.ld30.LD30Test.VictoryType;
import uk.org.ulcompsoc.ld30.components.Ball;
import uk.org.ulcompsoc.ld30.components.Position;
import uk.org.ulcompsoc.ld30.components.Renderable;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Ashley Davis (SgtCoDFish)
 */
public class BallBoundsSystem extends IteratingSystem {
	private ComponentMapper<Position>	posMapper		= null;
	private ComponentMapper<Renderable>	renderMapper	= null;

	@SuppressWarnings("unchecked")
	public BallBoundsSystem(int priority) {
		this(Family.getFor(Position.class, Renderable.class, Ball.class), priority);
	}

	protected BallBoundsSystem(Family family, int priority) {
		super(family, priority);

		posMapper = ComponentMapper.getFor(Position.class);
		renderMapper = ComponentMapper.getFor(Renderable.class);
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		Vector2 pos = posMapper.get(entity).position.cpy();
		float radius = renderMapper.get(entity).dimension.x;
		pos.add(radius / 2.0f, radius / 2.0f);

		if (pos.x < 0.0f) {
			LD30Test.victoryType = VictoryType.RED;
		} else if (pos.x > Gdx.graphics.getWidth()) {
			LD30Test.victoryType = VictoryType.BLUE;
		}
	}
}
