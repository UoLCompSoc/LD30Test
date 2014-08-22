package uk.org.ulcompsoc.ld30.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Ashley Davis (SgtCoDFish)
 */
public class Renderable extends Component {
	public static enum Shape {
		RECTANGLE, CIRCLE
	}

	public Color	color		= null;
	public Shape	shape		= null;

	// x = width, y = height for rect, x = radius for circle
	public Vector2	dimension	= null;

	public Renderable() {
		color = Color.WHITE;
		shape = Shape.RECTANGLE;
		dimension = new Vector2(8.0f, 8.0f);
	}

	public static float getHeightFor(Renderable r) {
		return (r.shape == Shape.RECTANGLE ? r.dimension.y : (r.shape == Shape.CIRCLE ? r.dimension.x : -1.0f));
	}
}
