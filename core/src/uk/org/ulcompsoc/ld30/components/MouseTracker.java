package uk.org.ulcompsoc.ld30.components;

import com.badlogic.ashley.core.Component;

/**
 * @author Ashley Davis (SgtCoDFish)
 */
public class MouseTracker extends Component {
	public float	chaseRate	= 1.0f;

	public MouseTracker() {
		this(1.0f);
	}

	public MouseTracker(float chaseRate) {
		this.chaseRate = chaseRate;
	}
}
