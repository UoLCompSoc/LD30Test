package uk.org.ulcompsoc.ld30.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Ashley Davis (SgtCoDFish)
 */
public class Position extends Component {
	public Vector2	position	= null;

	public Position() {
		this(0.0f, 0.0f);
	}

	public Position(float x, float y) {
		position = new Vector2(x, y);
	}

}
