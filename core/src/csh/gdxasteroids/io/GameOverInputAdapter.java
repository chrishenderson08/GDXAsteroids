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