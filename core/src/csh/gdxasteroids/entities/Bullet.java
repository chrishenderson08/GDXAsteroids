package csh.gdxasteroids.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Bullet extends Entity
{
    public Bullet(float x, float y)
    {
        super(x, y);
        setWrappable(false);
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
        
        Vector2 vert0 = new Vector2(-0.1f, -0.1f);
        Vector2 vert1 = new Vector2(0.1f, -0.1f);
        Vector2 vert2 = new Vector2(0f, 0.1f);
        
        Gdx.gl.glLineWidth(2);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.identity();
        shapeRenderer.setColor(1f, 1f, 1f, 0f);
        shapeRenderer.translate(x, y, 0);
        shapeRenderer.rotate(0f, 0f, 1f, getOrientation());
        shapeRenderer.line(vert0, vert1);
        shapeRenderer.line(vert1, vert2);
        shapeRenderer.line(vert2, vert0);
        shapeRenderer.end();
    }
}