package Game;


import com.sun.opengl.util.Animator;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.BitSet;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLContext;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import static Game.MainMenu.glcanvas;
import static Game.MainMenu.lst;
import static Game.MainMenu.panel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangeLevel  implements GLEventListener, KeyListener {
    Animator animator;
    PlayGame eventListener;
    int animationIndex = 0;
    int maxWidth = 100;
    int maxHeight = 100;
    int[][] pos = new int[maxWidth][maxHeight];
    int x = 10, y = 10;

    String textureNames[] = {"iages\\easy.png","iages\\dd.png","iages\\hard.png","iages\\_background.png","iages\\arro.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];

    double arrowx,arrowy;
    static int go;
    public  void gotoMethod() {
        if(go!=-1)
        {
            GLContext context = glcanvas.getContext();
            context.makeCurrent();
             PlayGame eventListener1=(PlayGame) lst.get(2);
             eventListener1.level=go;
            eventListener1.init(glcanvas);
            
            eventListener1.display(glcanvas);
            
            glcanvas.removeGLEventListener((ChangeLevel)lst.get(1));
        glcanvas.removeKeyListener((ChangeLevel)lst.get(1));
            glcanvas.addGLEventListener(eventListener1);
        glcanvas.addKeyListener(eventListener1);
        }
    }
    public void init(GLAutoDrawable gld) {
keyBits.clear();
arrowx=20;arrowy=80;
go=-1;panel.setVisible(false);
        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; i++) {
    try {
        texture[i] = TextureReader.readTexture(textureNames[i], true);
    } catch (IOException ex) {
        Logger.getLogger(ChangeLevel.class.getName()).log(Level.SEVERE, null, ex);
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

    public void display(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();

        DrawBackground(gl);
        handleKeyPress();
        //easy
        DrawSprite(gl, 45, 80, 0, (float) 0.3);
        //medium
        DrawSprite(gl, 45, 65, 1, (float) 0.4);
        //hard
        DrawSprite(gl, 45, 40, 2, (float) 0.4);
        //arrow
        DrawSprite(gl, arrowx, arrowy, 4, (float) 0.3);
        gotoMethod();
    }
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void handleKeyPress() {

        if (isKeyPressed(KeyEvent.VK_UP)) {
            if (arrowy == 60) {
                arrowy = 80;
            }
            else
                if(arrowy==40)
                {
                    arrowy = 60;
                }
        }
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (arrowy== 80) {
                arrowy = 60;
            }
            else
                if(arrowy==60)
                {
                    arrowy=40;
                }
        }
if (isKeyPressed(KeyEvent.VK_ENTER)) {
            if (arrowy== 80) {
                go=0;
            }
            else
               if(arrowy==60)
               {
                   go=2;
               }
            else
               {
                   go=4;
               }
        
        }
if (isKeyPressed(KeyEvent.VK_BACK_SPACE)) {
GLContext context = glcanvas.getContext();
            context.makeCurrent();
            beginGLEventListener eventListener1 = (beginGLEventListener) lst.get(0);
            eventListener1.init(glcanvas);
            eventListener1.display(glcanvas);
            glcanvas.removeGLEventListener((ChangeLevel) lst.get(1));
            glcanvas.removeKeyListener((ChangeLevel) lst.get(1));
            glcanvas.addGLEventListener(eventListener1);
            glcanvas.addKeyListener(eventListener1);

}
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

    public void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[3]);	// Turn Blending On

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
