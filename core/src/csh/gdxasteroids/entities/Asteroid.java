package csh.gdxasteroids.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Asteroid extends Entity
{
    private static final float SIZE_MEDIUM = 0.75f;
    private static final float SIZE_SMALL = 0.5f;
    
    private float scaleFactor;
    
    public Asteroid(float x, float y)
    {
        this(x, y, 1f);
    }
    
    public Asteroid(float x, float y, float scaleFactor)
    {
        super(x, y);
        this.scaleFactor = scaleFactor;
    }

    @Override
    public void render(ShapeRenderer shapeRenderer)
    {
        if(shapeRenderer == null)
        {
            return;
        }
        
        float x = this.getX();
        float y = this.getY();
        
        Vector2 vert0 = new Vector2(-1.2f, 0);
        vert0.scl(scaleFactor);
        Vector2 vert1 = new Vector2(-1.1f, -1.1f);
        vert1.scl(scaleFactor);
        Vector2 vert2 = new Vector2(0.95f, -1.1f);
        vert2.scl(scaleFactor);
        Vector2 vert3 = new Vector2(1.2f, 0f);
        vert3.scl(scaleFactor);
        Vector2 vert4 = new Vector2(1.05f, 1.1f);
        vert4.scl(scaleFactor);
        Vector2 vert5 = new Vector2(0f, 1.2f);
        vert5.scl(scaleFactor);
        
        Gdx.gl.glLineWidth(2);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.identity();
        shapeRenderer.setColor(1f, 1f, 1f, 0f);
        shapeRenderer.translate(x, y, 0);
        shapeRenderer.rotate(0f, 0f, 1f, getOrientation());
        shapeRenderer.line(vert0, vert1);
        shapeRenderer.line(vert1, vert2);
        shapeRenderer.line(vert2, vert3);
        shapeRenderer.line(vert3, vert4);
        shapeRenderer.line(vert4, vert5);
        shapeRenderer.line(vert4, vert5);
        shapeRenderer.line(vert5, vert0);
        shapeRenderer.end();
    }
}