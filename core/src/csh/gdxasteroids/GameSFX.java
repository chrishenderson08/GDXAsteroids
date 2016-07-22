package csh.gdxasteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

public class GameSFX implements Disposable
{
    private Sound laserSFX;
    private Sound explosion;
    private Sound shipExplosion;
    private Sound thruster;
    
    public GameSFX()
    {
        laserSFX = Gdx.audio.newSound(Gdx.files.internal("sfx/laser.mp3"));
        explosion = Gdx.audio.newSound(Gdx.files.internal("sfx/explosion.mp3"));
        shipExplosion = Gdx.audio.newSound(Gdx.files.internal("sfx/shipExplosion.mp3"));
        thruster = Gdx.audio.newSound(Gdx.files.internal("sfx/thruster.mp3"));
    }

    public void playLaser()
    {
        laserSFX.play();
    }
    
    public void playExplosion()
    {
        explosion.play();
    }
    
    public void playShipExplosion()
    {
        shipExplosion.play();
    }
    
    public void playThruster()
    {
        thruster.loop();
    }
    
    public void stopThruster()
    {
        thruster.stop();
    }
    
    @Override
    public void dispose()
    {
        laserSFX.dispose();
        explosion.dispose();
    }
}