package csh.gdxasteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import csh.gdxasteroids.entities.Asteroid;
import csh.gdxasteroids.entities.Entity;

public class StageManager
{
    private static final int STARTING_ASTEROID_COUNT = 5;
    private static final float[] SPEED_RANGE = {0.05f, 0.2f};
    private static final float[] ANGULAR_VELOCITY_RANGE = {1f, 3f};
    private static final float[] INIT_X = {-5, 105};
    private static final float[] INIT_Y = {-5, 105 * (Gdx.graphics.getHeight() / Gdx.graphics.getWidth())}; //Correct for aspect ratio.
    
    private GDXAsteroids engine;
    private int stageAsteroidCount;
    private int asteroidsSpawned;
    private long lastEval;
    private long lastSpawn;
    private long spawnRate;
    
    public StageManager(GDXAsteroids engine)
    {
        this.engine = engine;
        this.stageAsteroidCount = STARTING_ASTEROID_COUNT;
        this.asteroidsSpawned = 0;
        this.lastEval = 0;
        this.lastSpawn = 0;
        this.spawnRate = calcSpawnRate();
    }
    
    public void initNewGame()
    {
        asteroidsSpawned = 0;
        stageAsteroidCount = STARTING_ASTEROID_COUNT;
        spawnRate = calcSpawnRate();
    }
    
    public void initNextStage()
    {
        asteroidsSpawned = 0;
        stageAsteroidCount += (int)stageAsteroidCount * 0.5;
        spawnRate = calcSpawnRate();
        lastSpawn += 5000;
    }
    
    public void evaluate()
    {
        lastEval = System.currentTimeMillis();
        if (lastEval - lastSpawn >= spawnRate &&
            asteroidsSpawned < stageAsteroidCount)
        {
            spawnAsteroid();
        }
    }
    
    public boolean allAsteroidsSpawned()
    {
        boolean allAsteroidsSpawned = asteroidsSpawned == stageAsteroidCount;
        return allAsteroidsSpawned;
    }
    
    private long calcSpawnRate()
    {
        long spawnRate = 30000 / stageAsteroidCount;
        return spawnRate;
    }
    
    private void spawnAsteroid()
    {
        lastSpawn = System.currentTimeMillis();
        asteroidsSpawned++;
        
        float orientation = (float)(360 * Math.random());
        float speed = SPEED_RANGE[1] - ((float)Math.random() * (SPEED_RANGE[1] - SPEED_RANGE[0]));
        float[] velocity = new float[2];
        velocity[0] = speed * MathUtils.sinDeg(-orientation);
        velocity[1] = speed * MathUtils.cosDeg(-orientation);
        
        float angularVelocity = ANGULAR_VELOCITY_RANGE[1] - ((float)Math.random() * (ANGULAR_VELOCITY_RANGE[1] - ANGULAR_VELOCITY_RANGE[0]));
        
        int xIndex = (int)Math.round(Math.random());
        int yIndex = (int)Math.round(Math.random());
        
        Entity newAsteroid = new Asteroid(engine, INIT_X[xIndex], INIT_Y[yIndex]);
        newAsteroid.setVelocity(velocity);
        newAsteroid.setAngularVelocity(angularVelocity);
        newAsteroid.setOrientation(orientation);
        engine.addEntity(newAsteroid);
    }
}