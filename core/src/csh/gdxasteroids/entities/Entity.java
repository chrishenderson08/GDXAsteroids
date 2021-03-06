/**
*    Copyright 2016, see AUTHORS file.
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
import com.badlogic.gdx.math.MathUtils;

import csh.gdxasteroids.GDXAsteroids;

public abstract class Entity implements Renderable, Collider
{
    public static final float MAX_SPEED = 3;
    
    private GDXAsteroids engine;
    private float x;
    private float y;
    private float boundingRadius;
    private float orientation;
    private float angularVelocity;
    private float[] velocity;
    private float[] acceleration;
    private float accelerationRate;
    private float resistanceFactor;
    private boolean wrappable;
    
    public Entity(GDXAsteroids engine, float x, float y)
    {
        this(engine, x, y, 0f, 0f, new float[]{0f, 0f}, new float[]{0f, 0f});
    }
    
    public Entity(GDXAsteroids engine, float x, float y, float orientation, float angularVelocity, float[] velocity, float[] acceleration)
    {
        this.engine = engine;
        this.x = x;
        this.y = y;
        this.boundingRadius = 0f;
        this.orientation = orientation;
        this.angularVelocity = angularVelocity;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.resistanceFactor = 0f;
        this.wrappable = true;
        this.accelerationRate = 0f;
    }
    
    public boolean evaluateMovement()
    {
        applyAcceleration();
        applyResistance();
        rotate(angularVelocity);
        
        x += velocity[0];
        y += velocity[1];
        
        boolean removeEntity = wrapEntity();
        
        return removeEntity;
    }
    
    public GDXAsteroids getEngine()
    {
        return engine;
    }
    
    public float getX()
    { 
        return x;
    }
    
    public float getY()
    { 
        return y;
    }
    
    public float getBoundingRadius()
    {
        return boundingRadius;
    }
    
    public float getOrientation()
    {
        return orientation;
    }
    
    public float getAngularVelocity()
    {
        return angularVelocity;
    }
    
    public float[] getVelocity()
    {
        return velocity;
    }
    
    public float[] getAcceleration()
    {
        return acceleration;
    }
    
    public float getResistanceFactor()
    {
        return resistanceFactor;
    }
    
    public boolean isWrappable()
    {
        return wrappable;
    }
    
    public float getAccelerationRate()
    {
        return accelerationRate;
    }
    
    public void setX(float x)
    {
        this.x = x;
    }
    
    public void setY(float y)
    {
        this.y = y;
    }
    
    public void setBoundingRadius(float boundingRadius)
    {
        this.boundingRadius = boundingRadius;
    }
    
    public void setOrientation(float orientation)
    {
        //Only use positive degree values [0, 360) to express orientation.
        while(orientation < 0)
        {
            orientation += 360;
        }
        
        orientation %= 360; //[0, 360).
        
        this.orientation = orientation;
    }
    
    public void setAngularVelocity(float angularVelocity)
    {
        this.angularVelocity = angularVelocity;
    }
    
    public void setVelocity(float[] velocity)
    {
        this.velocity = velocity;
    }
    
    public void setAcceleration(float[] acceleration)
    {
        this.acceleration = acceleration;
    }
    
    public void setResistanceFactor(float resistanceFactor)
    {
        this.resistanceFactor = resistanceFactor;
    }
    
    public void setWrappable(boolean wrappable)
    {
        this.wrappable = wrappable;
    }
    
    public void setAccelerationRate(float accelerationRate)
    {
        this.accelerationRate = accelerationRate;
    }
    
    public void rotate(float orientationChange)
    {
        float newOrientation = orientation + orientationChange;
        setOrientation(newOrientation);
    }
    
    /**
     * Applies acceleration to current velocity and ensures max speed
     * is obeyed.
     */
    private void applyAcceleration()
    {
        acceleration[0] = MathUtils.sinDeg(-orientation) * accelerationRate;
        acceleration[1] = MathUtils.cosDeg(-orientation) * accelerationRate;
        
        velocity[0] = (velocity[0] + acceleration[0]);
        velocity[1] = (velocity[1] + acceleration[1]);
        
        if(velocity[0] > MAX_SPEED)
        {
            velocity[0] = MAX_SPEED;
        }
        
        if(velocity[1] > MAX_SPEED)
        {
            velocity[1] = MAX_SPEED;
        }
    }
    
    /**
     * Applies resistance factor (if any) to entity if it is not
     * currently accelerating in any direction.
     */
    private void applyResistance()
    {
        if(acceleration[0] == 0 && acceleration[1] == 0)
        {
            velocity[0] = velocity[0] - (velocity[0] * resistanceFactor);
            velocity[1] = velocity[1] - (velocity[1] * resistanceFactor);
        }
    }
    
    /**
     * If entity travels off screen and it is set to be wrappable, "wrap" it to the other side
     * of the screen (e.g. - if entity travels beyond left side of screen, have it reappear on the right side).
     * If entity is not wrappable, it will be removed from the game and disposed of.
     */
    private boolean wrapEntity()
    {
        boolean removeEntity = false;
        
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        
        float leftBound = 0; 
        float rightBound = GDXAsteroids.CAM_WIDTH;
        float lowerBound = 0;
        float upperBound = GDXAsteroids.CAM_HEIGHT * (h / w); //Correcting for aspect ratio.
        
        //Add a bit of "buffer" to the bounds to make wrapping look/feel right. Calculate buffer as a factor
        //of the upper and right bounds.
        float bufferFactor = 0.05f;
        leftBound = leftBound - (bufferFactor * rightBound);
        rightBound = rightBound + (bufferFactor * rightBound);
        lowerBound = lowerBound - (bufferFactor * upperBound);
        upperBound = upperBound + (bufferFactor * upperBound);
        
        if (x < leftBound && wrappable)
        {
            x = rightBound;
        }
        else if (x > rightBound && wrappable)
        {
            x = leftBound;
        }
        else if (x < leftBound || x > rightBound)
        {
            removeEntity = true;
        }
        
        
        if (y < lowerBound && wrappable)
        {
            y = upperBound;
        }
        else if (y > upperBound && wrappable)
        {
            y = lowerBound;
        }
        else if (y < lowerBound || y > upperBound)
        {
            removeEntity = true;
        }
        
        return removeEntity;
    }
}