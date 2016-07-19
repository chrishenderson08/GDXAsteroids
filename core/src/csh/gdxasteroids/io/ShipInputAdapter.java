package csh.gdxasteroids.io;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

import csh.gdxasteroids.entities.PlayerShip;

public class ShipInputAdapter extends InputAdapter
{
    private PlayerShip playerShip;
    
    public ShipInputAdapter(PlayerShip playerShip)
    {
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
                playerShip.setAcceleration(PlayerShip.ACCELERATION_RATE);
                break;
            case Keys.SPACE:
                playerShip.setFiring(true);
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
                playerShip.setAcceleration(0);
                break;
            case Keys.SPACE:
                playerShip.setFiring(false);
                break;
        }
        
        return true;
    }
}