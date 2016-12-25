package Game;

import static Game.MainMenu.*;
import com.sun.opengl.util.*;
import java.awt.event.*;
import java.io.IOException;
import javax.media.opengl.*;
import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.glu.GLU;

public class GameOver implements GLEventListener, KeyListener {

    Animator animator;
    int animationIndex = 0;
    int maxWidth = 100;
    int maxHeight = 100;
    int[][] pos = new int[maxWidth][maxHeight];
    int x = 10, y = 10;

    String textureNames[] = {"iages\\los.png","iages\\goer.png","iages\\bakk.png","iages\\arro.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];

    /*
     5 means gun in array pos
     x and y coordinate for gun 
     */
    double ranx, ranx3, rany, ranx2, rany2, rany3;

    public void init(GLAutoDrawable gld) {
        keyBits.clear();
        MainMenu.panel.setVisible(false);
        
        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(textureNames[i], true);
            } catch (IOException ex) {
                Logger.getLogger(GameOver.class.getName()).log(Level.SEVERE, null, ex);
            }
            gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);
            new GLU().gluBuild2DMipmaps(
                    GL.GL_TEXTURE_2D,
                    GL.GL_RGBA, // Internal Texel Format,
                    texture[i].getWidth(), texture[i].getHeight(),
                    GL.GL_RGBA, // External format from image,
                    GL.GL_UNSIGNED_BYTE,
                    texture[i].getPixels() // Imagedata
            );
        }
    }
//    double sp1 = 0.4, sp2 = 0.3, sp3 = 0.5;

    public void display(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();
        DrawBackground(gl);
        pos[x][y] = 5;
        DrawSprite(gl, 23, 20, 0, 1);
        DrawSprite(gl, 80, 10, 2, (float) 0.2);
       // DrawSprite(gl, 65, 10, 3, (float) 0.1);
        handleKeyPress();
    }
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void DrawGraph(GL gl) {
        for (int i = 0; i < maxWidth; i++) {
            for (int j = 0; j < maxHeight; j++) {
                if (pos[i][j] == 5) {
                    DrawSprite(gl, i, j, animationIndex, 1);
                }
            }
        }
    }

    public void DrawSprite(GL gl, double x, double y, int index, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On
//try to recall with index 5 and x,y random
        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.6* scale, 0.6 * scale, 1);
        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);

    }

    public void Drawbasket(GL gl, double x, double y, int index, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On
//try to recall with index 5 and x,y random
        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.2 * scale, 0.2 * scale, 1);
        //System.out.println(x +" " + y);
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }
public void handleKeyPress() {
        if (isKeyPressed(KeyEvent.VK_ENTER)) {           
            GLContext context = glcanvas.getContext();
            context.makeCurrent();
            beginGLEventListener eventListener1=(beginGLEventListener) lst.get(0);
            eventListener1.init(glcanvas);
            eventListener1.display(glcanvas);
            glcanvas.removeGLEventListener((GameOver)lst.get(4));
            glcanvas.removeKeyListener((GameOver)lst.get(4));
            glcanvas.addGLEventListener(eventListener1);
            glcanvas.addKeyListener(eventListener1);
        }
    }

    public void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[1]);	// Turn Blending On

        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        // Front Face
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    /*
     * KeyListener
     */
 /* public void handleKeyPress() {

        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (x > 0) {//if an egg is being released increase index
                pos[x][y] = 0;
                x--;
            }
            animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (x < maxWidth - 10) {
                pos[x][y] = 0;
                x++;
            }
            animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (y > 0) {
                pos[x][y] = 0;
                y--;
            }
            animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_UP)) {
            if (y < maxHeight - 10) {
                pos[x][y] = 0;
                y++;
            }
            animationIndex++;
        }
        
    }*/
    public BitSet keyBits = new BitSet(256);

    @Override
    public void keyPressed(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.set(keyCode);
    }

    @Override
    public void keyReleased(final KeyEvent event) {
        int keyCode = event.getKeyCode();
        keyBits.clear(keyCode);
    }

    @Override
    public void keyTyped(final KeyEvent event) {
        // don't care 
    }

    public boolean isKeyPressed(final int keyCode) {
        return keyBits.get(keyCode);
    }
}
