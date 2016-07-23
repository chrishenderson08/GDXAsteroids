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

package csh.gdxasteroids.io;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

import csh.gdxasteroids.GameOverScreen;

public class GameOverInputAdapter extends InputAdapter
{
    private GameOverScreen gameOverScreen;
    
    public GameOverInputAdapter(GameOverScreen gameOverScreen)
    {
        this.gameOverScreen = gameOverScreen;
    }
    
    @Override
    public boolean keyDown(int keycode)
    {
        switch (keycode)
        {
            case Keys.W:
            case Keys.S:
                gameOverScreen.toggleSelection();
                break;
            case Keys.SPACE:
            case Keys.ENTER:
                gameOverScreen.pickSelection();                
                break;
        }
        
        return true;
    }
}