package uk.org.ulcompsoc.ld30.systems;

import uk.org.ulcompsoc.ld30.components.CheatingAI;
import uk.org.ulcompsoc.ld30.components.Position;
import uk.org.ulcompsoc.ld30.components.Renderable;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

/**
 * @author Ashley Davis (SgtCoDFish)
 */
public class CheatingAISystem extends IteratingSystem {
	private ComponentMapper<Position>	posMapper		= null;
	private ComponentMapper<CheatingAI>	cheatMapper		= null;
	private ComponentMapper<Renderable>	renderMapper	= null;

	@SuppressWarnings("unchecked")
	public CheatingAISystem(int priority) {
		this(Family.getFor(Position.class, CheatingAI.class), priority);
	}

	public CheatingAISystem(Family family, int priority) {
		super(family, priority);

		posMapper = ComponentMapper.getFor(Position.class);
		cheatMapper = ComponentMapper.getFor(CheatingAI.class);
		renderMapper = ComponentMapper.getFor(Renderable.class);
	}

	@Override
	public void processEntity(Entity entity, float deltaTime) {
		float heightOffset = (renderMapper.has(entity) ? Renderable.getHeightFor(renderMapper.get(entity)) / 2.0f
				: 64.0f);
		posMapper.get(entity).position.y = posMapper.get(cheatMapper.get(entity).followMe).position.y - heightOffset;
	}
}
