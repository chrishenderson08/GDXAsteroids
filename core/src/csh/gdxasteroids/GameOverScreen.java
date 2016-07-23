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

package csh.gdxasteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class GameOverScreen
{
    private boolean continueSelected;
    private GDXAsteroids engine;
    
    public GameOverScreen(GDXAsteroids engine)
    {
        this.continueSelected = true;
        this.engine = engine;
    }
    
    public void init()
    {
        continueSelected = true;
    }
    
    public void toggleSelection()
    {
        continueSelected = !continueSelected;
    }
    
    public void pickSelection()
    {
        if (continueSelected)
        {
            engine.toggleGameOver();
        }
        else
        {
            Gdx.app.exit();
        }
    }
    
    public void render(ShapeRenderer shapeRenderer, SpriteBatch spriteBatch)
    {   
        CharSequence gameOver = "Game Over!";
        CharSequence continueMsg = "Continue?";
        CharSequence yes = "yes";
        CharSequence no = "no";
        
        BitmapFont font = new BitmapFont();
        font.getData().setScale(0.4f);

        float x = GDXAsteroids.CAM_WIDTH * 0.35f;
        float y0 = GDXAsteroids.CAM_HEIGHT * 0.8f * ((float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());
        float y1 = GDXAsteroids.CAM_HEIGHT * 0.7f * ((float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());
        float y2 = GDXAsteroids.CAM_HEIGHT * 0.6f * ((float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());
        float y3 = GDXAsteroids.CAM_HEIGHT * 0.5f * ((float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());
        
        spriteBatch.begin();
        font.draw(spriteBatch, gameOver, x, y0);
        font.draw(spriteBatch, continueMsg, x, y1);
        font.draw(spriteBatch, yes, x, y2);
        font.draw(spriteBatch, no, x, y3);
        spriteBatch.end();
        
        font.dispose();
        
        Gdx.gl.glLineWidth(4);
        
        float arrowX = GDXAsteroids.CAM_WIDTH * 0.3f;
        float arrowY = GDXAsteroids.CAM_HEIGHT * 0.55f * ((float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());
        if (!continueSelected)
        {
            arrowY = GDXAsteroids.CAM_HEIGHT * 0.45f * ((float)Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth());
        }
        
        Vector2 vert0 = new Vector2(-1.5f, 0f);
        Vector2 vert1 = new Vector2(1.5f, 0f);
        Vector2 vert2 = new Vector2(-0.3f, 1.2f);
        Vector2 vert3 = new Vector2(-0.3f, -1.2f);
        
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.identity();
        shapeRenderer.setColor(1f, 1f, 1f, 0f);
        shapeRenderer.translate(arrowX, arrowY, 0);
        shapeRenderer.line(vert0, vert1);
        shapeRenderer.line(vert2, vert1);
        shapeRenderer.line(vert3, vert1);
        shapeRenderer.end();
    }
}