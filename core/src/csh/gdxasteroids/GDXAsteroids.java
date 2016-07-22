package csh.gdxasteroids;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import csh.gdxasteroids.entities.Asteroid;
import csh.gdxasteroids.entities.Entity;
import csh.gdxasteroids.entities.PlayerShip;
import csh.gdxasteroids.entities.Renderable;
import csh.gdxasteroids.io.GameOverInputAdapter;
import csh.gdxasteroids.io.ShipInputAdapter;

public class GDXAsteroids extends ApplicationAdapter
{
    public static final int TARGET_RES_WIDTH = 1024;
    public static final int TARGET_RES_HEIGHT = 728;
    public static final float WORLD_WIDTH = 150f;
    public static final float WORLD_HEIGHT = 150f;
    public static final float CAM_WIDTH = 100f;
    public static final float CAM_HEIGHT = 100f;
    
    public enum GameState{ACTIVE, PAUSED, GAME_OVER};
    
    private GameState currentState;
    private Camera camera;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch spriteBatch;
    private List<Entity> entities;
    private List<Entity> entitiesToAdd;
    private PlayerShip player;
    private StageManager stageManager;
    private GameOverScreen gameOverScreen;
	
	@Override
	public void create ()
	{
	    currentState = GameState.ACTIVE;
	    
	    camera = new OrthographicCamera();
	    shapeRenderer = new ShapeRenderer();
	    spriteBatch = new SpriteBatch();
	    entities = new ArrayList<Entity>();
	    entitiesToAdd = new ArrayList<Entity>();
	    
	    //Create player's ship.
	    player = new PlayerShip(this);
	    entities.add(player);
	    
	    stageManager = new StageManager(this);
	    
	    gameOverScreen = new GameOverScreen(this);
	    
	    Gdx.input.setInputProcessor(new ShipInputAdapter(this, player));
	}

	@Override
	public void render ()
	{
	    switch(currentState)
	    {
	        case ACTIVE:
        		execActiveState();
        		break;
	            
	        case GAME_OVER:
	            execGameOverState();
	            break;
	            
	        default:
	            break;
	    }
	}
	
	@Override
	public void dispose ()
	{
	    shapeRenderer.dispose();
	    spriteBatch.dispose();
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
	
	public void togglePause()
	{
	    if (currentState == GameState.ACTIVE)
	    {
	        currentState = GameState.PAUSED;
	    }
	    else if (currentState == GameState.PAUSED)
	    {
	        currentState = GameState.ACTIVE;
	    }
	}
	
	public void toggleGameOver()
	{
	    if (currentState == GameState.ACTIVE)
	    {
	        currentState = GameState.GAME_OVER;
	        gameOverScreen.init();
	        Gdx.input.setInputProcessor(new GameOverInputAdapter(gameOverScreen));
	    }
	    else if (currentState == GameState.GAME_OVER)
	    {
	        currentState = GameState.ACTIVE;
	        initNewGame();
	        Gdx.input.setInputProcessor(new ShipInputAdapter(this, player));
	    }
	}
	
	private void initNewGame()
	{
	    entities.clear();
	    player = new PlayerShip(this);
	    entities.add(player);
	    stageManager.initNewGame();
	}
	
	private void execActiveState()
	{
	    shapeRenderer.setProjectionMatrix(camera.combined);
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        boolean allAsteroidsSpawned = stageManager.allAsteroidsSpawned();
        if (allAsteroidsSpawned)
        {
            boolean asteroidFound = false;
            for (Entity curEntity : entities)
            {
                asteroidFound = curEntity instanceof Asteroid;
                if (asteroidFound)
                {
                    break;
                }
            }
            
            if (!asteroidFound)
            {
                stageManager.initNextStage();
            }
        }
        
        stageManager.evaluate();
        
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
	
	private void execGameOverState()
	{
	    spriteBatch.setProjectionMatrix(camera.combined);
        
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        gameOverScreen.render(shapeRenderer, spriteBatch);
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