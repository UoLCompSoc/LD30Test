package uk.org.ulcompsoc.ld30.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * @author Ashley Davis (SgtCoDFish)
 */
public class CheatingAI extends Component {
	public Entity	followMe	= null;

	public CheatingAI(Entity followMe) {
		if (!ComponentMapper.getFor(Position.class).has(followMe)) {
			throw new GdxRuntimeException(
					"Created cheating ai with invalid ball: must have Position component already added.");
		}

		this.followMe = followMe;
	}
}
