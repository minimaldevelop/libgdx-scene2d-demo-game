package com.minimaldevelop.libgdxgame.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.minimaldevelop.libgdxgame.Assets;
import com.minimaldevelop.libgdxgame.GameItem;

public class Platforms extends Group {
	
	final int gameUnitConst = 32;
	private World world;
	
	public Platforms(World world) {
		this.world = world;
		FileHandle file =  Gdx.files.internal("data/level3.json");
		Json json = new Json();
		@SuppressWarnings("unchecked")
		Array<GameItem> items = json.fromJson(Array.class, GameItem.class, file);	
		
		for (GameItem item : items) {
			if (item.getItemType().equals("platform")) {
				Vector2 position = new Vector2(item.getX()/gameUnitConst, item.getY()/gameUnitConst);
				int blocks = (int) (item.getWidth() / gameUnitConst);
				
				Image platform = new Image(Assets.platform);
				platform.setPosition(position.x, position.y);
				platform.setWidth(blocks);
				platform.setHeight(item.getHeight() / gameUnitConst);
				
				createPlatformBody(position.x, position.y, platform.getWidth(), platform.getHeight());
				
				addActor(platform);
			}
		}
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
	}
	
	private void createPlatformBody(float x, float y, float width, float height) {
		// Create our body definition
		BodyDef groundBodyDef = new BodyDef();
		// Set its world position
//		groundBodyDef.position.set(position);// treba da se sredi jer je ovo pozicija u sredini

		groundBodyDef.position.set(x + width/2, y + height/2);
		
		// Create a body from the defintion and add it to the world
		Body platformBody = world.createBody(groundBodyDef);

		// Create a polygon shape
		PolygonShape groundBox = new PolygonShape();
		// Set the polygon shape as a box which is twice the size of our view
		// port and 10 high
		groundBox.setAsBox(width/2, 0.5f);
		// Create a fixture from our polygon shape and add it to our ground body
		platformBody.createFixture(groundBox, 0.0f);
	}
	
}
