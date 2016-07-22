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