package com.minimaldevelop.libgdxgame.actors;

import java.util.ArrayList;

import aurelienribon.bodyeditor.BodyEditorLoader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointEdge;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.minimaldevelop.libgdxgame.Assets;
import com.minimaldevelop.libgdxgame.screens.GameScreen;

public class FallingMan extends Actor {

	private float stateTime = 0f;
	private World world;
	private Body fallingManBody = null;

	public enum FallingManState {
		Falling, Splashed
	}

	FallingManState fallingManState = FallingManState.Falling;

	public FallingMan(World world) {
		this.world = world;
		
		addListener(listener);		
		restartFallingMan();
	}
	
	@Override
	public void act(float delta) {
		// TODO Auto-generated method stub
		super.act(delta);		
		stateTime += delta;
		
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub

		setRotation(MathUtils.radiansToDegrees * fallingManBody.getAngle());
		setPosition(fallingManBody.getPosition().x, fallingManBody.getPosition().y);
		
		//Not go over camera by X
		if (getX() < 0) setX(0);
		else if (getX() > GameScreen.WORLD_WIDTH) setX(GameScreen.WORLD_WIDTH - getWidth());

		if (fallingManState == FallingManState.Falling) {

			TextureRegion keyFrame = Assets.fallingManAnim
					.getKeyFrame(stateTime, true);

			batch.draw(keyFrame, getX(), getY(), 0, 0, getWidth(), getHeight(),
					1, 1, getRotation());

		} else if (fallingManState == FallingManState.Splashed) {

			batch.draw(Assets.fallingManSplash, getX(), getY(), 0, 0, getWidth(),
					getHeight(), 1, 1, getRotation());

		}

	}
	
	private void restartFallingMan() {
		
		fallingManState = FallingManState.Falling;
		setWidth(2);
		setHeight(2);
		setPosition(GameScreen.WORLD_WIDTH / 2, GameScreen.WORLD_HEIGHT
				- GameScreen.FRUSTUM_HEIGHT / 2);
		 createFallingManBody();		 
	}

	private void createFallingManBody() {

		// 0. Create a loader for the file saved from the editor.
		BodyEditorLoader loader = new BodyEditorLoader(
				Gdx.files.internal("data/felix.json"));

		// CircleShape circle = new CircleShape();
		// circle.setRadius(1f);

		// 1. Create a BodyDef, as usual.
		BodyDef bd = new BodyDef();
		bd.position.set(getX(), getY());
		bd.type = BodyType.DynamicBody;

		// 2. Create a FixtureDef, as usual.
		FixtureDef fd = new FixtureDef();
		// fd.shape = circle;
		fd.density = 0.1f;
		fd.friction = 0.5f; // trenje
		fd.restitution = 0.3f; // odbijanje

		if (fallingManBody!=null) removeBodySafely(fallingManBody);
		
		// 3. Create a Body, as usual.
		fallingManBody = world.createBody(bd);
		fallingManBody.setLinearVelocity(0f, -2f);
		
		// 4. Create the body fixture automatically by using the loader.
		loader.attachFixture(fallingManBody, "felix", fd, 2);


	}
	
	private void createSplashedFallingManBody() {

		// 0. Create a loader for the file saved from the editor.
		BodyEditorLoader loader = new BodyEditorLoader(
				Gdx.files.internal("data/splash.json"));

		// CircleShape circle = new CircleShape();
		// circle.setRadius(1f);

		// 1. Create a BodyDef, as usual.
		BodyDef bd = new BodyDef();
		bd.position.set(getX(), getY());
		bd.type = BodyType.DynamicBody;

		// 2. Create a FixtureDef, as usual.
		FixtureDef fd = new FixtureDef();
		// fd.shape = circle;
		fd.density = 0.1f;
		fd.friction = 0.5f; // trenje
		fd.restitution = 0.3f; // odbijanje
		
		removeBodySafely(fallingManBody);		
		// 3. Create a Body, as usual.
		fallingManBody = world.createBody(bd);

		// 4. Create the body fixture automatically by using the loader.
		loader.attachFixture(fallingManBody, "rip", fd, 2);


	}
	
	public Rectangle getBounds() {		
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	
	public void splashFallingMan() {
		if (fallingManState != FallingManState.Splashed) {
			fallingManState = FallingManState.Splashed;
			fallingManBody.setLinearVelocity(0f, 0f);
			createSplashedFallingManBody();
			
		}		
	}
	
	public void moveFallingManUpDown(float yDir) {
		if (fallingManState != FallingManState.Splashed) {
			fallingManBody.setLinearVelocity(fallingManBody.getLinearVelocity().x, yDir);
		}
	}
	
	public void moveFallingManLeftRight(float xDir) {
		if (fallingManState != FallingManState.Splashed) {
			fallingManBody.setLinearVelocity(xDir, fallingManBody.getLinearVelocity().y);
		}
	}

	InputListener listener = new InputListener() {

		public boolean touchDown(InputEvent event, float x, float y,
				int pointer, int button) {
			System.out.println("down");
			
			restartFallingMan();
			return true;
		}

		public void touchUp(InputEvent event, float x, float y, int pointer,
				int button) {
			System.out.println("up");
		}

	};
	
	/**
	 * Safe way to remove body from the world. Remember that you cannot have any
	 * references to this body after calling this
	 *
	 * @param body
	 *            that will be removed from the physic world
	 */

	public void removeBodySafely(Body body) {
	    //to prevent some obscure c assertion that happened randomly once in a blue moon
	    final ArrayList<JointEdge> list = body.getJointList();
	    while (list.size() > 0) {
	        world.destroyJoint(list.get(0).joint);
	    }
	    // actual remove
	    world.destroyBody(body);
	}

}
