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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import csh.gdxasteroids.GDXAsteroids;
import csh.gdxasteroids.GameSFX;

public class Asteroid extends Entity
{
    private static final float SIZE_MEDIUM = 0.75f;
    private static final float SIZE_SMALL = 0.5f;
    
    private float scaleFactor;
    
    public Asteroid(GDXAsteroids engine, float x, float y)
    {
        this(engine, x, y, 1f);
    }
    
    public Asteroid(GDXAsteroids engine, float x, float y, float scaleFactor)
    {
        super(engine, x, y);
        this.scaleFactor = scaleFactor;
        setBoundingRadius(1.6f);
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
        
        Vector2 vert0 = new Vector2(-1.6f, 0);
        vert0.scl(scaleFactor);
        Vector2 vert1 = new Vector2(-1.5f, -1.5f);
        vert1.scl(scaleFactor);
        Vector2 vert2 = new Vector2(1.35f, -1.5f);
        vert2.scl(scaleFactor);
        Vector2 vert3 = new Vector2(1.6f, 0f);
        vert3.scl(scaleFactor);
        Vector2 vert4 = new Vector2(1.45f, 1.5f);
        vert4.scl(scaleFactor);
        Vector2 vert5 = new Vector2(0f, 1.6f);
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
    
    @Override
    public float getBoundingRadius()
    {
        float radius = super.getBoundingRadius();
        radius *= scaleFactor;
        
        return radius;
    }

    @Override
    public void collisionAction()
    {
        GDXAsteroids engine = getEngine();
        
        if (scaleFactor != SIZE_SMALL)
        {
            float newScaleFactor = scaleFactor;
            if (scaleFactor > SIZE_MEDIUM)
            {
                newScaleFactor = SIZE_MEDIUM;
            }
            else if (scaleFactor > SIZE_SMALL)
            {
                newScaleFactor = SIZE_SMALL;
            }
            
            float x = getX();
            float y = getY();
            float orientation = getOrientation();
            float[] velocity = getVelocity();
            float speed = Math.abs(velocity[0]) + Math.abs(velocity[1]);
            float angularVelocity = getAngularVelocity();
        
            Entity newAsteroid1 = new Asteroid(engine, x, y, newScaleFactor);
            float[] asteroid1Velocity = new float[2];
            asteroid1Velocity[0] = -(MathUtils.sinDeg(orientation - 22.5f)) * speed;
            asteroid1Velocity[1] = -(MathUtils.cosDeg(orientation - 22.5f)) * speed;
            newAsteroid1.setVelocity(asteroid1Velocity);
            newAsteroid1.setAngularVelocity(angularVelocity);
            
            Entity newAsteroid2 = new Asteroid(engine, x, y, newScaleFactor);
            float[] asteroid2Velocity = new float[2];
            asteroid2Velocity[0] = MathUtils.sinDeg(orientation + 22.5f) * speed;
            asteroid2Velocity[1] = MathUtils.cosDeg(orientation + 22.5f) * speed;
            newAsteroid2.setVelocity(asteroid2Velocity);
            newAsteroid2.setAngularVelocity(angularVelocity);
            
            engine.addEntity(newAsteroid1);
            engine.addEntity(newAsteroid2);
        }
        
        GameSFX sfx = engine.getSFX();
        sfx.playExplosion();
    }

    @Override
    public boolean canCollide(Entity entity)
    {
        boolean canCollide = !(entity instanceof Asteroid);
        return canCollide;
    }
}