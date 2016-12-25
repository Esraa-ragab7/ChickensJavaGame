package Game;


import static Game.MainMenu.glcanvas;
import static Game.MainMenu.lst;
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


public class Winner implements GLEventListener, KeyListener {
    int l=0;
    Animator animator;
    int animationIndex = 0;
    int maxWidth = 100;
    int maxHeight = 100;
    int[][] pos = new int[maxWidth][maxHeight];
    int x = 10, y = 10;

    String textureNames[] = {"iages\\winner.png","iages\\win.png","iages\\win2.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];

    /*
     5 means gun in array pos
     x and y coordinate for gun 
     */
    double ranx, ranx3, rany, ranx2, rany2, rany3;

    public void init(GLAutoDrawable gld) {
        l=0;
            keyBits.clear();

        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(textureNames[i], true);
            } catch (IOException ex) {
                Logger.getLogger(Winner.class.getName()).log(Level.SEVERE, null, ex);
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
int ani=1;
int t=0;
    public void display(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();
        DrawBackground(gl);
        handleKeyPress();
        
        DrawSprite(gl,15,20,ani, (float) 0.8);
        if(ani==1&&t>3){ani=2;t=0;}
        else
            if(ani==2&&t>3){
            ani=1;t=0;}
        t++;
        //DrawSprite(gl,10,10,2,1);
    }
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
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
    public void handleKeyPress() {
        if (isKeyPressed(KeyEvent.VK_ENTER)) {
            if(l==6)
            {
                GLContext context = glcanvas.getContext();
            context.makeCurrent();
            Medal eventListener1 =(Medal)lst.get(7);
            eventListener1.init(glcanvas);
            eventListener1.display(glcanvas);
            glcanvas.removeGLEventListener((Winner) lst.get(6));
            glcanvas.removeKeyListener((Winner) lst.get(6));
            glcanvas.addGLEventListener(eventListener1);
            glcanvas.addKeyListener(eventListener1);
            }
            else{
            GLContext context = glcanvas.getContext();
            context.makeCurrent();
            PlayGame listener3 = new PlayGame();
                    lst.set(2, listener3);
                    PlayGame eventListener1 =(PlayGame)lst.get(2);
                    eventListener1.level=l;
            eventListener1.init(glcanvas);
            
            //System.out.println("Gae.Winner.handleKeyPress()"+l);
            //eventListener1.goto1=0;
            //eventListener.level=2*listener.go-2;
            eventListener1.display(glcanvas);
            glcanvas.removeGLEventListener((Winner) lst.get(6));
            glcanvas.removeKeyListener((Winner) lst.get(6));
            glcanvas.addGLEventListener(eventListener1);
            glcanvas.addKeyListener(eventListener1);
        }}
       
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[0]);	// Turn Blending On

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
