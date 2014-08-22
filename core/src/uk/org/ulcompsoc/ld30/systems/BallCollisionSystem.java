package uk.org.ulcompsoc.ld30.systems;

import java.util.ArrayList;

import uk.org.ulcompsoc.ld30.components.Position;
import uk.org.ulcompsoc.ld30.components.Renderable;
import uk.org.ulcompsoc.ld30.components.Renderable.Shape;
import uk.org.ulcompsoc.ld30.components.Solid;
import uk.org.ulcompsoc.ld30.components.Velocity;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * @author Ashley Davis (SgtCoDFish)
 */
public class BallCollisionSystem extends EntitySystem {
	private Engine						engine			= null;
	boolean								processing		= false;

	private ComponentMapper<Position>	posMapper		= null;
	private ComponentMapper<Velocity>	velMapper		= null;
	private ComponentMapper<Renderable>	renderMapper	= null;
	private ComponentMapper<Solid>		solidMapper		= null;

	Vector2								dimCache		= new Vector2();

	public BallCollisionSystem(int priority) {
		super(priority);

		posMapper = ComponentMapper.getFor(Position.class);
		velMapper = ComponentMapper.getFor(Velocity.class);
		renderMapper = ComponentMapper.getFor(Renderable.class);
		solidMapper = ComponentMapper.getFor(Solid.class);
	}

	@Override
	public void addedToEngine(Engine engine) {
		this.engine = engine;
		processing = true;
	}

	@Override
	public void removedFromEngine(Engine engine) {
		this.engine = null;
		processing = false;
	}

	@Override
	public boolean checkProcessing() {
		return processing;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(float deltaTime) {
		ImmutableArray<Entity> ents = engine.getEntitiesFor(Family.getFor(Position.class, Solid.class, Velocity.class,
				Renderable.class));
		ArrayList<Rectangle> rects = new ArrayList<Rectangle>();

		for (int i = 0; i < ents.size(); i++) {
			Vector2 pos = posMapper.get(ents.get(i)).position;
			Renderable r = renderMapper.get(ents.get(i));
			dimCache = r.dimension.cpy();

			if (r.shape == Shape.RECTANGLE) {
				rects.add(i, new Rectangle(pos.x, pos.y, dimCache.x, dimCache.y));
			} else if (r.shape == Shape.CIRCLE) {
				rects.add(i, new Rectangle(pos.x, pos.y, dimCache.x, dimCache.x));
			} else {
				throw new GdxRuntimeException("Illegal shape.");
			}

		}

		for (int i = 0; i < rects.size() - 1; i++) {
			Entity one = ents.get(i);
			Rectangle oneRect = rects.get(i);

			for (int j = i + 1; j < ents.size(); j++) {
				Entity other = ents.get(j);
				Rectangle otherRect = rects.get(j);

				if (oneRect.overlaps(otherRect)) {
					float oneMass = solidMapper.get(one).mass;
					float otherMass = solidMapper.get(other).mass;

					if (oneMass > otherMass) {
						Vector2 othVel = velMapper.get(other).velocity;
						othVel.x = -othVel.x;
						posMapper.get(other).position.x += othVel.x;
					} else if (otherMass > oneMass) {
						Vector2 oneVel = velMapper.get(one).velocity;
						oneVel.x = -oneVel.x;
						posMapper.get(one).position.x += oneVel.x;
					}
				}
			}
		}
	}
}
