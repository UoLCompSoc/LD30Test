package uk.org.ulcompsoc.ld30.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;

/**
 * @author Ashley Davis (SgtCoDFish)
 */
public class Renderable extends Component {
	public static enum Shape {
		RECTANGLE, CIRCLE
	}

	public Color	color;
	public Shape	shape;

	public Renderable() {
		color = Color.WHITE;
		shape = Shape.RECTANGLE;
	}
}
