package Game;
import static Game.MainMenu.glcanvas;
import static Game.MainMenu.lst;
import static Game.MainMenu.panel;
import com.sun.opengl.util.Animator;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.BitSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLContext;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author esrae
 */


public class QuitGame implements GLEventListener, KeyListener {
static MainMenu menu;
    Animator animator;
    int animationIndex = 0;
    int maxWidth = 100;
    int maxHeight = 100;
    int[][] pos = new int[maxWidth][maxHeight];
    int x = 10, y = 10;

    String textureNames[] = {"iages\\endd2.png","iages\\endd3.png","iages\\bak3.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];

    /*
     5 means gun in array pos
     x and y coordinate for gun 
     */
    double ranx, ranx3, rany, ranx2, rany2, rany3;

    public void init(GLAutoDrawable gld) {

keyBits.clear();
panel.setVisible(false);
quit=0;
        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(textureNames[i], true);
            } catch (IOException ex) {
                Logger.getLogger(QuitGame.class.getName()).log(Level.SEVERE, null, ex);
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
int quit=0;
public void noQuit(){
    if(quit==1)
    {
        GLContext context = glcanvas.getContext();
           context.makeCurrent();
           
            beginGLEventListener eventListener1=(beginGLEventListener) lst.get(0);
            
            eventListener1.init(glcanvas);
            eventListener1.display(glcanvas);
            
           glcanvas.removeGLEventListener((QuitGame)lst.get(3));
        glcanvas.removeKeyListener((QuitGame)lst.get(3));
         
         glcanvas.addGLEventListener(eventListener1);
        glcanvas.addKeyListener(eventListener1);
    }
}
    public void display(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();
        
        DrawBackground(gl);
        handleKeyPress();
        noQuit();
    }
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void handleKeyPress() {
        if (isKeyPressed(KeyEvent.VK_Y)) {
           menu.dispose();
           System.exit(0);
        }
        if (isKeyPressed(KeyEvent.VK_N)) {
            quit=1;
        }
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
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
