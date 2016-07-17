package csh.gdxasteroids;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import csh.gdxasteroids.entities.Entity;
import csh.gdxasteroids.entities.PlayerShip;
import csh.gdxasteroids.io.ShipInputAdapter;

public class GDXAsteroids extends ApplicationAdapter
{
    public static final int TARGET_RES_WIDTH = 1024;
    public static final int TARGET_RES_HEIGHT = 728;
    public static final float WORLD_WIDTH = 150f;
    public static final float WORLD_HEIGHT = 150f;
    public static final float CAM_WIDTH = 100f;
    public static final float CAM_HEIGHT = 100f;
    
    private InputProcessor inputProcessor;
    private Camera camera;
    private ShapeRenderer shapeRenderer;
    private List<Entity> entities;
    private PlayerShip player;
	
	@Override
	public void create ()
	{
	    camera = new OrthographicCamera();
	    shapeRenderer = new ShapeRenderer();
	    entities = new ArrayList<Entity>();
	    
	    //Create player's ship.
	    player = new PlayerShip();
	    entities.add(player);
	    
	    //Input.
	    inputProcessor = new ShipInputAdapter(player);
	    Gdx.input.setInputProcessor(inputProcessor);
	}

	@Override
	public void render ()
	{
	    shapeRenderer.setProjectionMatrix(camera.combined);
	    
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		for (Entity curEntity : entities)
		{
		    curEntity.evaluateMovement();
		    curEntity.render(shapeRenderer);
		}
	}
	
	@Override
	public void dispose ()
	{
	    shapeRenderer.dispose();
	}
	
	@Override
	public void resize(int width, int height)
	{
	    float winWidth = Gdx.graphics.getWidth();
	    float winHeight = Gdx.graphics.getHeight();
	    
	    camera.viewportWidth = CAM_WIDTH;
	    camera.viewportHeight = CAM_HEIGHT * (winHeight / winWidth);
	    camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);	    
	    camera.update();
	}
}