package csh.gdxasteroids.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import csh.gdxasteroids.GDXAsteroids;

public class PlayerShip extends Entity
{
    public static final float ROTATION_RATE = 4f;
    public static final float ACCELERATION_RATE = 0.02f;
    public static final float SHIP_RESISTANCE = 0.01f;
    public static final float BULLET_SPEED = 0.75f;
    public static final long SHOT_COOL_DOWN = 150; //Milliseconds
    
    private long lastShotTime;
    
    public PlayerShip(GDXAsteroids engine)
    {
        super(engine, GDXAsteroids.CAM_WIDTH / 2f, GDXAsteroids.CAM_HEIGHT / 2f * 0.75f);
        setResistanceFactor(SHIP_RESISTANCE);
        lastShotTime = 0;
        setBoundingRadius(1.8f);
    }
    
    @Override
    public void render(ShapeRenderer shapeRenderer)
    {
        if (shapeRenderer == null)
        {
            return;
        }
        
        float x = this.getX();
        float y = this.getY();
        
        Gdx.gl.glLineWidth(2);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.identity();
        shapeRenderer.setColor(1f, 1f, 1f, 0f);
        shapeRenderer.translate(x, y, 0);
        shapeRenderer.rotate(0f, 0f, 1f, getOrientation());
        drawShip(shapeRenderer);
        drawExhaust(shapeRenderer);
        shapeRenderer.end();
    }
    
    public void shoot()
    {
        float x0 = this.getX();
        float y0 = this.getY();
        
        float orientation = getOrientation();
        float[] shipSpeed = this.getVelocity();
        float[] v0 = new float[2];
        v0[0] =  (MathUtils.sinDeg(-orientation) * BULLET_SPEED) + shipSpeed[0];
        v0[1] = (MathUtils.cosDeg(-orientation) * BULLET_SPEED) + shipSpeed[1];
        
        Bullet newBullet = new Bullet(getEngine(), x0, y0);
        newBullet.setVelocity(v0);
        
        lastShotTime = System.currentTimeMillis();
        
        GDXAsteroids engine = getEngine();
        engine.addEntity(newBullet);
    }
    
    private void drawShip(ShapeRenderer shapeRenderer)
    {
        Vector2 vert0 = new Vector2(-1f, -1.25f);
        Vector2 vert1 = new Vector2(1f, -1.25f);
        Vector2 vert2 = new Vector2(0f, 1.25f);
        
        shapeRenderer.line(vert0, vert1);
        shapeRenderer.line(vert1, vert2);
        shapeRenderer.line(vert2, vert0);
    }
    
    private void drawExhaust(ShapeRenderer shapeRenderer)
    {
        float[] acceleration = getAcceleration();
        
        //Only draw exhaust if engines are firing.
        if (acceleration[0] != 0f || acceleration[1] != 0f)
        {
            Vector2 vert0 = new Vector2(-0.5f, -1.25f);
            Vector2 vert1 = new Vector2(0f, -2f);
            Vector2 vert2 = new Vector2(0.5f, -1.25f);
            
            shapeRenderer.line(vert0, vert1);
            shapeRenderer.line(vert1, vert2);
            shapeRenderer.line(vert2, vert0);
        }
    }

    @Override
    public void collisionAction()
    {
        GDXAsteroids engine = getEngine();
        engine.toggleGameOver();
    }

    @Override
    public boolean canCollide(Entity entity)
    {
        boolean canCollide = !(entity instanceof Bullet);
        return canCollide;
    }
}
