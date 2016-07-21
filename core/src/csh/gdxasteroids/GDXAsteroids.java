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

import csh.gdxasteroids.entities.Asteroid;
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
    private List<Entity> entitiesToAdd;
    private PlayerShip player;
	
	@Override
	public void create ()
	{
	    camera = new OrthographicCamera();
	    shapeRenderer = new ShapeRenderer();
	    entities = new ArrayList<Entity>();
	    entitiesToAdd = new ArrayList<Entity>();
	    
	    //Create player's ship.
	    player = new PlayerShip(this);
	    entities.add(player);
	    
	    //TEST ASTEROID
	    Entity asteroid = new Asteroid(this, 75f, 25f);
	    asteroid.setVelocity(new float[]{0.0f, 0.1f});
	    asteroid.setAngularVelocity(2);
	    entities.add(asteroid);
	    
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
			
		List<Integer> entityRemovalIndices = new ArrayList<Integer>();
		for (int i = 0; i < entities.size(); i++)
        {
		    Entity curEntity = entities.get(i);
            
            boolean removeEntity = curEntity.evaluateMovement();
            if(removeEntity)
            {
                entityRemovalIndices.add(i);
            }
        }
		
		for (int i = 0; i < entities.size(); i++)
		{
		    Entity curEntity = entities.get(i);
		    boolean removeEntity = entityRemovalIndices.contains(i);
		    
		    if (!removeEntity)
		    {
		        removeEntity = checkCollisions(curEntity);
		    }
		    
		    if (!removeEntity)
		    {
		        curEntity.render(shapeRenderer);
		    }
		    else if(!entityRemovalIndices.contains(i))
		    {
		        entityRemovalIndices.add(i);
		    }
		}
		
		for (int i = entityRemovalIndices.size() - 1; i >= 0; i--)
		{
		    Integer curRemovalIndex = entityRemovalIndices.get(i);
		    entities.remove(curRemovalIndex.intValue());
		}
		
		for (Entity curEntity : entitiesToAdd)
		{
		    entities.add(curEntity);
		}
		
		entitiesToAdd.clear();
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
	
	public void addEntity(Entity entity)
	{
	    if (entity != null)
	    {
	        entitiesToAdd.add(entity);
	    }
	}
	
	private boolean checkCollisions(Entity entity)
	{
	    boolean collision = false;
	    
	    for (Entity curCheckedEntity : entities)
	    {   
	        if (entity != curCheckedEntity &&
	            entity.canCollide(curCheckedEntity) &&
	            curCheckedEntity.canCollide(entity))
	        {
	            double centerDistance = Math.sqrt(Math.pow(curCheckedEntity.getX() - entity.getX(), 2) +
	                Math.pow(curCheckedEntity.getY() - entity.getY(), 2));
	            
	            double noCollisionDistance = entity.getBoundingRadius() + curCheckedEntity.getBoundingRadius();
	            collision = centerDistance <= noCollisionDistance;
	        }
	        
	        if (collision)
	        {
	            break;
	        }
	    }
	    
	    if (collision)
	    {
	        entity.collisionAction();
	    }
	    
	    return collision;
	}
}