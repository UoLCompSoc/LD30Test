package uk.org.ulcompsoc.ld30.components;

import com.badlogic.ashley.core.Component;

/**
 * @author Ashley Davis (SgtCoDFish)
 */
public class Solid extends Component {
	public float	mass	= 10.0f;

	public Solid() {
		this(10.0f);
	}

	public Solid(float mass) {
		this.mass = mass;
	}

}
