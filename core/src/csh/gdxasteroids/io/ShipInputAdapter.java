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

package csh.gdxasteroids.io;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

import csh.gdxasteroids.GDXAsteroids;
import csh.gdxasteroids.GameSFX;
import csh.gdxasteroids.entities.PlayerShip;

public class ShipInputAdapter extends InputAdapter
{
    private GDXAsteroids engine;
    private PlayerShip playerShip;
    
    public ShipInputAdapter(GDXAsteroids engine, PlayerShip playerShip)
    {
        this.engine = engine;
        this.playerShip = playerShip;
    }
    
    @Override
    public boolean keyDown(int keycode)
    {
        switch (keycode)
        {
            //Start counter-clockwise rotation.
            case Keys.A:
            case Keys.LEFT:
                playerShip.setAngularVelocity(PlayerShip.ROTATION_RATE);
                break;
                
            //Start clockwise rotation.
            case Keys.D:
            case Keys.RIGHT:
                playerShip.setAngularVelocity(-PlayerShip.ROTATION_RATE);
                break;
                
            //Start acceleration.
            case Keys.W:
            case Keys.UP:
                playerShip.setAccelerationRate(PlayerShip.ACCELERATION_RATE);
                GameSFX sfx = engine.getSFX();
                sfx.playThruster();
                break;
            case Keys.SPACE:
                playerShip.shoot();
                break;
            case Keys.ESCAPE:
                engine.togglePause();
                break;
        }
        
        return true;
    }

    @Override
    public boolean keyUp(int keycode)
    {   
        switch (keycode)
        {
            //Stop counter-clockwise rotation.
            case Keys.A:
            case Keys.LEFT:
                if (playerShip.getAngularVelocity() > 0) //If currently rotating counter-clockwise.
                {
                    playerShip.setAngularVelocity(0);
                }
                break;
                
            //Stop clockwise rotation.
            case Keys.D:
            case Keys.RIGHT:
                if (playerShip.getAngularVelocity() < 0) //If currently rotating clockwise.
                {
                    playerShip.setAngularVelocity(0);
                }
                
                break;
                
            //Stop acceleration.
            case Keys.W:
            case Keys.UP:
                playerShip.setAccelerationRate(0);
                GameSFX sfx = engine.getSFX();
                sfx.stopThruster();
                break;
        }
        
        return true;
    }
}