package Game;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package project;
//import static Gae.ChangeLevel.go;
import static Game.MainMenu.glcanvas;
import static Game.MainMenu.lst;
import static Game.MainMenu.panel;
import com.sun.opengl.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import javax.media.opengl.*;
import javax.swing.*;

import java.util.BitSet;
import javax.media.opengl.glu.GLU;

public class MainMenu extends JFrame {

//List<String> lst = new ArrayList<String>();
    //lst.add(listener);
    //lst.add( );
    public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            //  java.util.logging.Logger.getLogger(Chicken.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        MainMenu g = new MainMenu();
        QuitGame.menu = g;
    }

    //score panel
    static JLabel score = new JLabel("score");
    static JTextField scoret = new JTextField("");
    static JLabel timer = new JLabel("timer");
    static JTextField timert = new JTextField("");
    static JLabel esc = new JLabel("                                    To Pause press ESC ^_^", FlowLayout.RIGHT);
    static JLabel level = new JLabel("Level: ");
    static JTextField levelt = new JTextField("");
    static JLabel eggs = new JLabel("Eggs to collect: ");
    static JTextField eggst = new JTextField("");
    static JPanel panel = new JPanel();

    static GLCanvas glcanvas;
    //listeners
    beginGLEventListener listener1 = new beginGLEventListener();
    ChangeLevel listener2 = new ChangeLevel();
    PlayGame listener3 = new PlayGame();
    QuitGame listener4 = new QuitGame();
    GameOver listener5 = new GameOver();
    Instructions listener6 = new Instructions();
    Winner listener7 = new Winner();
    Medal listener8 = new Medal();
//list of listeners
    static ArrayList<Object> lst = new ArrayList<Object>();
    Animator animator;
    JButton jButton;

    public MainMenu() {
        //score panel optimization
        scoret.setColumns(2);
        timert.setColumns(2);
        eggst.setColumns(2);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        esc.setForeground(Color.red);
        Font myFont = new Font("Serif", Font.BOLD, 15);
        esc.setFont(myFont);
        scoret.setEditable(false);
        timert.setEditable(false);
        levelt.setEditable(false);
        eggst.setEditable(false);
        // add objects to panel
        panel.add(score);
        panel.add(scoret);
        panel.add(timer);
        panel.add(timert);
        panel.add(level);
        panel.add(levelt);
        panel.add(eggs);
        panel.add(eggst);
        panel.add(esc, BorderLayout.EAST);
        getContentPane().add(panel, BorderLayout.SOUTH);
        //hide
        panel.setVisible(false);
//add listeners
        lst.add(listener1);
        lst.add(listener2);
        lst.add(listener3);
        lst.add(listener4);
        lst.add(listener5);
        lst.add(listener6);
        lst.add(listener7);
        lst.add(listener8);

        glcanvas = new GLCanvas();
        glcanvas.addGLEventListener((beginGLEventListener) lst.get(0));
        glcanvas.addKeyListener((beginGLEventListener) lst.get(0));

        getContentPane().add(glcanvas, BorderLayout.CENTER);
        animator = new FPSAnimator(10);
        animator.add(glcanvas);
        animator.start();

        setTitle("");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        glcanvas.requestFocus();
    }

}

class beginGLEventListener implements GLEventListener, KeyListener {

    Animator animator;
    // PlayGame eventListener1;
    // public ChangeLevel eventListener2;
    int animationIndex = 0;
    int maxWidth = 100;
    int maxHeight = 100;
    int[][] pos = new int[maxWidth][maxHeight];
    int x = 10, y = 10;

    String textureNames[] = {"iages\\chicken1.png", "iages\\chicken2.png", "iages\\chicken3.png",
        "iages\\_background.png", "iages\\win.png", "iages\\er.png", "iages\\pp.png",
        "iages\\ll.png", "iages\\how1.png", "iages\\quit.png", "iages\\arro2.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];

    /*
     5 means gun in array pos
     x and y coordinate for gun 
     */
    double ranx, ranx3, rany, ranx2, rany2, rany3;
    static int goto1 = 0;

    public void init(GLAutoDrawable gld) {
        panel.setVisible(false);
//clear keybits to go back between pages
        keyBits.clear();

        goto1 = 0;
        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

//                mipmapsFromPNG(gl, new GLU(), texture[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA, // Internal Texel Format,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA, // External format from image,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels() // Imagedata
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
        arrowx = 65;
        arrowy = 76;
    }
    int arrowx, arrowy;

    public void gotoMethod() {
        //play game
        if (goto1 == 1) {
            GLContext context = glcanvas.getContext();
            context.makeCurrent();
            PlayGame eventListener1 = (PlayGame) lst.get(2);
            lst.set(2, eventListener1);
            eventListener1.init(glcanvas);
            eventListener1.display(glcanvas);
            glcanvas.removeGLEventListener((beginGLEventListener) lst.get(0));
            glcanvas.removeKeyListener((beginGLEventListener) lst.get(0));
            glcanvas.addGLEventListener(eventListener1);
            glcanvas.addKeyListener(eventListener1);
        }
        //choose level
        if (goto1 == 2) {
            GLContext context = glcanvas.getContext();
            context.makeCurrent();
            ChangeLevel eventListener1 = (ChangeLevel) lst.get(1);
            eventListener1.init(glcanvas);
            eventListener1.display(glcanvas);
            glcanvas.removeGLEventListener((beginGLEventListener) lst.get(0));
            glcanvas.removeKeyListener((beginGLEventListener) lst.get(0));
            glcanvas.addGLEventListener(eventListener1);
            glcanvas.addKeyListener(eventListener1);
        }
        //instructions
        if (goto1 == 3) {
            GLContext context = glcanvas.getContext();
            context.makeCurrent();
            Instructions eventListener1 = (Instructions) lst.get(5);
            eventListener1.init(glcanvas);
            eventListener1.display(glcanvas);
            glcanvas.removeGLEventListener((beginGLEventListener) lst.get(0));
            glcanvas.removeKeyListener((beginGLEventListener) lst.get(0));
            glcanvas.addGLEventListener(eventListener1);
            glcanvas.addKeyListener(eventListener1);
        }
        //quit
        if (goto1 == 4) {
            GLContext context = glcanvas.getContext();
            context.makeCurrent();
            QuitGame eventListener1 = (QuitGame) lst.get(3);
            eventListener1.init(glcanvas);
            eventListener1.display(glcanvas);
            glcanvas.removeGLEventListener((beginGLEventListener) lst.get(0));
            glcanvas.removeKeyListener((beginGLEventListener) lst.get(0));
            glcanvas.addGLEventListener(eventListener1);
            glcanvas.addKeyListener(eventListener1);
        }

    }

    public void display(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();

        DrawBackground(gl);

        pos[x][y] = 5;
        animationIndex = 1;
        //chicken
        DrawSprite(gl, 70, 36, animationIndex, 1, (float) 0.4, (float) 0.4);
        //winner animation
        DrawSprite(gl, 20, 40, 4, 1, (float) 0.55, (float) 0.55);
        //welcome
        DrawSprite(gl, 45, 87, 5, 1, (float) 0.8, (float) 0.1);
        //play game
        DrawSprite(gl, 50, 74, 6, (float) 0.1, 2, 1);
        //choose level
        DrawSprite(gl, 50, 67, 7, (float) 0.1, 2, 2);
        //how to play
        DrawSprite(gl, 50, 60, 8, (float) 0.1, 3, 1);
        //quit
        DrawSprite(gl, 50, 50, 9, (float) 0.1, 1, 1);
        //arrow
        DrawSprite(gl, arrowx, arrowy, 10, (float) 0.1, 1, 1);
        handleKeyPress();
        gotoMethod();

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
    }

    public void DrawSprite2(GL gl, double x, double y) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[4]);	// Turn Blending On
        gl.glPushMatrix();
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);

        gl.glScalef(0.1f, 0.1f, 0.1f);
        gl.glTranslated(x, y, 0);
        gl.glRotated(90 + 180, 0.0f, 0.0f, 1.0f);
        gl.glBegin(GL.GL_QUADS);
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

        if (isKeyPressed(KeyEvent.VK_UP)) {
            if (arrowy == 50) {
                //pos[x][y] = 0;
                arrowy = 60;
            } else if (arrowy == 60) {
                arrowy = 67;
            } else if (arrowy == 67) {
                arrowy = 76;
            }
            //  animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (arrowy == 76) {
                //  pos[x][y] = 0;
                arrowy = 67;
            } else if (arrowy == 67) {
                arrowy = 60;
            } else if (arrowy == 60) {
                arrowy = 50;
            }
            //animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_ENTER)) {
            if (arrowy == 76) {
//go to play
                goto1 = 1;
            } else if (arrowy == 67) {
                //go to choose level
                goto1 = 2;
            } else if (arrowy == 60) {
                //go to Instructions
                goto1 = 3;
            } else {
                //go to quit
                goto1 = 4;
            }
            //  System.out.println("enter pressed");
            //animationIndex++;
        }
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    public void DrawGraph(GL gl) {

        for (int i = 0; i < maxWidth; i++) {
            for (int j = 0; j < maxHeight; j++) {
                if (pos[i][j] == 5) {
                    DrawSprite(gl, i, j, animationIndex, 1, 1, 1);
                }
            }
        }
    }

    public void DrawSprite(GL gl, double x, double y, int index, float scale, float p, float pp) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On
//try to recall with index 5 and x,y random
        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(p * scale, pp * scale, 1);
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
