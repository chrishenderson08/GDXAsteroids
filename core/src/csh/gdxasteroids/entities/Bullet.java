/**
*    Copyright 2016, Chris Henderson
*
*    Licensed under the Apache License, Version 2.0 (the "License");
*    you may not use this file except in compliance with the License.
*    You may obtain a copy of the License at
*
*        http://www.apache.org/licenses/LICENSE-2.0
*
*    Unless required by applicable law or agreed to in writing, software
*    distributed under the License is distributed on an "AS IS" BASIS,
*    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*    See the License for the specific language governing permissions and
*    limitations under the License.
**/

package csh.gdxasteroids.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import csh.gdxasteroids.GDXAsteroids;

public class Bullet extends Entity
{
    public Bullet(GDXAsteroids engine, float x, float y)
    {
        super(engine, x, y);
        setWrappable(false);
        setBoundingRadius(0.25f);
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

    @Override
    public void collisionAction()
    {
        
    }

    @Override
    public boolean canCollide(Entity entity)
    {
        boolean canCollide = !(entity instanceof Bullet);
        return canCollide;
    }
}