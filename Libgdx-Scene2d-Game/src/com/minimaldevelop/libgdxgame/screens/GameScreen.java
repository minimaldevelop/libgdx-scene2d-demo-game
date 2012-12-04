package com.minimaldevelop.libgdxgame.screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.minimaldevelop.libgdxgame.Assets;
import com.minimaldevelop.libgdxgame.InputHandler;
import com.minimaldevelop.libgdxgame.LibGdxGame;
import com.minimaldevelop.libgdxgame.OverlapTester;
import com.minimaldevelop.libgdxgame.actors.FallingMan;
import com.minimaldevelop.libgdxgame.actors.Platforms;

public class GameScreen implements Screen{
	
	World world;
	LibGdxGame game;
	Stage stage;
	Stage staticStage;
	public static final float WORLD_WIDTH = 15;
	public static final float FRUSTUM_HEIGHT = 25;
	public static final float WORLD_HEIGHT = FRUSTUM_HEIGHT * 20;
	public static final float FRUSTUM_WIDTH = WORLD_WIDTH;
	FallingMan fallingMan;
	Platforms platforms;
	InputHandler inputHandler;
	
	enum GameState {
		Play, Pause
	}
	
	GameState gameState = GameState.Play;
	
	Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
	
	public GameScreen(LibGdxGame game) {
		this.game = game;
	}

	@Override
	public void render(float delta) {
		
		if (Gdx.app.getType() == Application.ApplicationType.Android) {
			inputHandler.accelerometerChange(Gdx.input.getAccelerometerX());
		}
		
		// TODO Auto-generated method stub
		Camera camera = stage.getCamera();
		camera.position.y = fallingMan.getY() - 5f;
		checkCollision();
		
		Gdx.gl.glClearColor(1f, 0f, 1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		staticStage.act(delta);
        staticStage.draw();
        stage.act(delta);
        stage.draw();
        
        world.step(1/60f, 6, 2);
//        debugRenderer.render(world, camera.combined);
        
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.setViewport(FRUSTUM_WIDTH, FRUSTUM_HEIGHT, false);
		
		//Uncomment for keep aspect ratio
//		stage.setViewport(FRUSTUM_WIDTH, FRUSTUM_HEIGHT, true);
//		stage.getCamera().position.set(FRUSTUM_WIDTH/2 - stage.getGutterWidth(), WORLD_HEIGHT
//				- FRUSTUM_HEIGHT / 2 - stage.getGutterHeight(), 0);
	}

	@Override
	public void show() {
		
		// TODO Auto-generated method stub
		world = new World(new Vector2(0f, -1), true);
		
		stage = new Stage();
		staticStage = new Stage();	
		
		fallingMan = new FallingMan(world);
		inputHandler = new InputHandler(fallingMan);
		
		platforms = new Platforms(world);
		stage.addListener(inputHandler);
		stage.addActor(fallingMan);
		stage.addActor(platforms);
		
		Image testPlatform = new Image(Assets.backgroundTexture);
		testPlatform.setPosition(0,  0);
		staticStage.addActor(testPlatform);
		
		InputMultiplexer im = new InputMultiplexer(staticStage, stage);
		
		Gdx.input.setInputProcessor(im);
		
		testPlatform.addListener(new InputListener() {
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				System.out.println("down platform");
				return false;
			}

			public void touchUp(InputEvent event, float x, float y, int pointer,
					int button) {
				System.out.println("up platform");
			}
		});
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
	}
	
	
	
	private void checkCollision() {
		
		Rectangle pigBounds = fallingMan.getBounds();
		
		for (Actor platform : platforms.getChildren()) {
			
			Rectangle platformBounds = new Rectangle(platform.getX(), platform.getY(), 
					platform.getWidth(), platform.getHeight());
			
			if (OverlapTester.overlapRectangles(pigBounds, platformBounds)) {
				fallingMan.splashFallingMan();
			}
			
			
		}
		
		
	}

}
