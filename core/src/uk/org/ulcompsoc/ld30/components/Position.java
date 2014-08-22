package uk.org.ulcompsoc.ld30.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Ashley Davis (SgtCoDFish)
 */
public class Position extends Component {
	public Vector2	position	= null;

	public float	origX		= 0.0f;
	public float	origY		= 0.0f;

	public Position() {
		this(0.0f, 0.0f);
	}

	public Position(float x, float y) {
		position = new Vector2(x, y);
		this.origX = x;
		this.origY = y;
	}

	public void reset() {
		position.set(origX, origY);
	}

}
