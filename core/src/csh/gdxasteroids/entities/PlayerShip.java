package csh.gdxasteroids.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import csh.gdxasteroids.GDXAsteroids;

public class PlayerShip extends Entity
{
    public static final float ROTATION_RATE = 4f;
    public static final float ACCELERATION_RATE = 0.02f;
    public static final float SHIP_RESISTANCE = 0.01f;
    
    public PlayerShip()
    {
        super(GDXAsteroids.CAM_WIDTH / 2f, GDXAsteroids.CAM_HEIGHT / 2f * 0.75f);
        setResistanceFactor(SHIP_RESISTANCE);
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
    
    public void fire()
    {
        
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
}
