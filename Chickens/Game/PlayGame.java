package Game;

import static Game.MainMenu.glcanvas;
import static Game.MainMenu.lst;
import com.sun.opengl.util.*;
import java.awt.event.*;
import java.io.IOException;
import javax.media.opengl.*;
import javax.swing.*;
import javax.swing.Timer;
import java.util.BitSet;
import java.util.Random;
import javax.media.opengl.glu.GLU;

public class PlayGame implements GLEventListener, KeyListener {

    Animator animator;
    int animationIndex = 0;
    int maxWidth = 100;
    int maxHeight = 100;
    int[][] pos = new int[maxWidth][maxHeight];
    int x = 10, y = 10;
    int score;
    int sec = 56;
    String textureNames[] = {"iages\\chicken3.png", "iages\\chicken2.png",
        "iages\\chicken1.png", "iages\\_background.png", "iages\\egg.png",
        "iages\\bas4.png", "iages\\win.png", "iages\\win2.png", "iages\\1.png", "iages\\2.png", 
        "iages\\3.png", "iages\\4.png", "iages\\5.png", "iages\\6.png", "iages\\resume button.png",
        "iages\\Main-Menu-Button.png", "iages\\arro.png", "iages\\bgg.png"};
    TextureReader.Texture texture[] = new TextureReader.Texture[textureNames.length];
    int textures[] = new int[textureNames.length];
    JLabel label = new JLabel();

    double ranx, ranx3, rany, ranx2, rany2, rany3;
    double[][] ran = new double[4][2];
    double[] speed = new double[4];
    //speeds
    double sp1 = 0.4, sp2 = 0.3, sp3 = 0.5;
    int l = 1, r = 0;
    int level = 0;
    boolean pause;
    //eggs required to win
    int eggsnb;
    ActionListener actionlistener2 = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            sec--;
            if (sec == -1) {
                if (score < eggsnb) {
                    GLContext context = glcanvas.getContext();
                    context.makeCurrent();
                    GameOver eventListener1 = (GameOver) lst.get(4);
                    eventListener1.init(glcanvas);
                    eventListener1.display(glcanvas);

                    glcanvas.removeGLEventListener((PlayGame) lst.get(2));
                    glcanvas.removeKeyListener((PlayGame) lst.get(2));
                    glcanvas.addGLEventListener(eventListener1);
                    glcanvas.addKeyListener(eventListener1);
                } else if (score >= eggsnb) {
                    GLContext context = glcanvas.getContext();
                    context.makeCurrent();
                    Winner eventListener1 = (Winner) lst.get(6);
                    eventListener1.init(glcanvas);
                    eventListener1.display(glcanvas);
                    eventListener1.l = level + 1;
                    glcanvas.removeGLEventListener((PlayGame) lst.get(2));
                    glcanvas.removeKeyListener((PlayGame) lst.get(2));
                    glcanvas.addGLEventListener(eventListener1);
                    glcanvas.addKeyListener(eventListener1);
                }
            }
        }
    };
    Timer timer2 = new Timer(1000, actionlistener2);

    public void init(GLAutoDrawable gld) {
        keyBits.clear();
        pause = false;
        MainMenu.panel.setVisible(true);
        MainMenu.levelt.setText(level + 1 + "");
        score = 0;
        if (level == 0) {
            eggsnb = 30;
        }
        MainMenu.eggst.setText(eggsnb + "");
        if (level == 1) {
            eggsnb = 40;
        }
        MainMenu.eggst.setText(eggsnb + "");
        if (level == 2) {
            eggsnb = 50;
        }
        MainMenu.eggst.setText(eggsnb + "");
        if (level == 3) {
            eggsnb = 55;
        }
        MainMenu.eggst.setText(eggsnb + "");
        if (level == 4) {
            eggsnb = 5;
        }
        MainMenu.eggst.setText(eggsnb + "");//65
        if (level == 5) {
            eggsnb = 5;
        }
        MainMenu.eggst.setText(eggsnb + "");//70
        l = 1;
        r = 0;
        sec = 60;

        timer2.start();
        MainMenu.timert.setText("" + sec);

        ranx = -7;
        rany = 6;
        ranx2 = 0;
        rany2 = 6;
        ranx3 = 7;
        rany3 = 6;
        ran[0][0] = -7;
        ran[0][1] = 6;
        ran[1][0] = 0;
        ran[1][1] = 6;
        ran[2][0] = 7;
        ran[2][1] = 6;
        ran[3][0] = 7;
        ran[3][1] = 6;
        if (level == 0) {
     
            speed[0] = 0.3;
            speed[1] = 0.5;
            speed[2] = 0.4;
        }
        if (level == 1) {
         
            speed[0] = 0.4;
            speed[1] = 0.5;
            speed[2] = 0.7;
        }
        if (level == 2) {
        
            speed[0] = 0.6;
            speed[1] = 0.8;
            speed[2] = 0.9;
        }
        if (level == 3) {
            Random r = new Random();
            speed[0] = r.nextInt((8 - 6) + 1) + 6;
            speed[0] = 1;
            speed[1] = r.nextInt((8 - 6) + 1) + 6;
            speed[1] = 0.9;
            speed[2] = r.nextInt((8 - 6) + 1) + 6;
            speed[2] = 0.7;
        }
        if (level == 4) {
   
            speed[0] = 1.1;
            speed[1] = 1;
            speed[2] = 1.2;
        }
        if (level == 5) {
      
            speed[0] = 1.1;
            speed[1] = 1.3;
            speed[2] = 1.2;
        }
//change random range??
        GL gl = gld.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);    //This Will Clear The Background Color To Black

        gl.glEnable(GL.GL_TEXTURE_2D);  // Enable Texture Mapping
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

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
        arrowx = 27;
        arrowy = 57;
    }

    int arrowx, arrowy;

    public void display(GLAutoDrawable gld) {
        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);       //Clear The Screen And The Depth Buffer
        gl.glLoadIdentity();
        DrawBackground(gl, 3);
        if (!pause) {

            timer2.start();
            MainMenu.timert.setText("" + sec);
      
            handleKeyPress1();
            if (level == 0) {
                DrawSpritesp(gl, -2, 91, 8, (float) 0.3, (float) 0.2, (float) 0.3);
            }
            if (level == 1) {
                DrawSpritesp(gl, -2, 91, 9, (float) 0.3, (float) 0.2, (float) 0.3);
            }
            if (level == 2) {
                DrawSpritesp(gl, -2, 91, 10, (float) 0.3, (float) 0.2, (float) 0.3);
            }
            if (level == 3) {
                DrawSpritesp(gl, -2, 91, 11, (float) 0.3, (float) 0.2, (float) 0.3);
            }
            if (level == 4) {
                DrawSpritesp(gl, -2, 91, 12, (float) 0.3, (float) 0.2, (float) 0.3);
            }
            if (level == 5) {
                DrawSpritesp(gl, -2, 91, 13, (float) 0.3, (float) 0.2, (float) 0.3);
            }
           
            if (r == 1) {
                Drawbasket(gl, x - 4, y + 2, 6, (float) 2);//y+5
            }
            if (l == 1) {
                Drawbasket(gl, x + 4, y + 2, 7, (float) 2);//y+5
            }

            Drawbasket(gl, x, y - 3, 5, 1);//y to y-3
            //at each levelup increase speed randomely
            //increase score at collision
            if (level == 0) {
                for (int i = 0; i < 3; i++) {
                    DrawSprite2(gl, ran[i][0], ran[i][1]);
                }
           
                if (ran[0][1] >= -7 && ran[0][1] <= -5.8 && x >= 0 && x <= 20) {
                    ran[0][1] = 6;
                    score += 1;
                    Random r = new Random();
                    speed[0] = r.nextInt((5 - 3) + 1) + 3;
                    speed[0] /= 10.0;
                }
                if (ran[1][1] >= -7 && ran[1][1] <= -5.8 && x >= 40 && x <= 50) {
                    ran[1][1] = 6;
                    score += 1;
                    Random r = new Random();
                    speed[1] = r.nextInt((5 - 3) + 1) + 3;
                    speed[1] /= 10;
                }
                if (ran[2][1] >= -7 && ran[2][1] <= -5.8 && x >= 80 && x <= 100) {
                    ran[2][1] = 6;
                    score += 1;
                    Random r = new Random();
                    speed[2] = r.nextInt((5 - 3) + 1) + 3;
                    speed[2] /= 10;
                }
                ran[0][1] -= speed[0];
                if (ran[0][1] < -10) {
           
                    ran[0][1] = 6;
                }
                ran[1][1] -= speed[1];

                if (ran[1][1] < -10) {
                    ran[1][1] = 6;
                }

                ran[2][1] -= speed[2];

                if (ran[2][1] < -10) {
                    ran[2][1] = 6;
                }
           
            } else if (level == 1) {
                for (int i = 0; i < 3; i++) {
                    DrawSprite2(gl, ran[i][0], ran[i][1]);
                }
           
                if (ran[0][1] >= -7 && ran[0][1] <= -5.8 && x >= 0 && x <= 20) {
                    ran[0][1] = 6;
                    score += 1;
                    Random r = new Random();
                    speed[0] = r.nextInt((7 - 4) + 1) + 4;
                    speed[0] /= 10.0;
                }
                if (ran[1][1] >= -7 && ran[1][1] <= -5.8 && x >= 40 && x <= 50) {
                    ran[1][1] = 6;
                    score += 1;
                    Random r = new Random();
                    speed[1] = r.nextInt((7 - 4) + 1) + 4;
                    speed[1] /= 10;
                }
                if (ran[2][1] >= -7 && ran[2][1] <= -5.8 && x >= 80 && x <= 100) {
                    ran[2][1] = 6;
                    score += 1;
                    Random r = new Random();
                    speed[2] = r.nextInt((7 - 4) + 1) + 4;
                    speed[2] /= 10;
                }
                ran[0][1] -= speed[0];
                if (ran[0][1] < -10) {
               
                    ran[0][1] = 6;
                }
                ran[1][1] -= speed[1];

                if (ran[1][1] < -10) {
                    ran[1][1] = 6;
                }

                ran[2][1] -= speed[2];

                if (ran[2][1] < -10) {
                    ran[2][1] = 6;
                }
          
            } else if (level == 2) {
                for (int i = 0; i < 3; i++) {
                    DrawSprite2(gl, ran[i][0], ran[i][1]);
                }
           
                if (ran[0][1] >= -7 && ran[0][1] <= -5.8 && x >= 0 && x <= 20) {
                    ran[0][1] = 6;
                    score += 1;
                    Random r = new Random();
                    speed[0] = r.nextInt((9 - 6) + 1) + 6;
                    speed[0] /= 10.0;
                }
                if (ran[1][1] >= -7 && ran[1][1] <= -5.8 && x >= 40 && x <= 50) {
                    ran[1][1] = 6;
                    score += 1;
                    Random r = new Random();
                    speed[1] = r.nextInt((9 - 6) + 1) + 6;
                    speed[1] /= 10;
                }
                if (ran[2][1] >= -7 && ran[2][1] <= -5.8 && x >= 80 && x <= 100) {
                    ran[2][1] = 6;
                    score += 1;
                    Random r = new Random();
                    speed[2] = r.nextInt((9 - 6) + 1) + 6;
                    speed[2] /= 10;
                }
                ran[0][1] -= speed[0];
                if (ran[0][1] < -10) {
               
                    ran[0][1] = 6;
                }
                ran[1][1] -= speed[1];

                if (ran[1][1] < -10) {
                    ran[1][1] = 6;
                }

                ran[2][1] -= speed[2];

                if (ran[2][1] < -10) {
                    ran[2][1] = 6;
                }
            

            } else if (level == 3) {
                for (int i = 0; i < 3; i++) {
                    DrawSprite2(gl, ran[i][0], ran[i][1]);
                }
           
                if (ran[0][1] >= -7 && ran[0][1] <= -5.8 && x >= 0 && x <= 20) {
                    ran[0][1] = 6;
                    score += 1;
                    Random r = new Random();
                    speed[0] = r.nextInt((10 - 7) + 1) + 7;
                    speed[0] /= 10.0;
                }
                if (ran[1][1] >= -7 && ran[1][1] <= -5.8 && x >= 40 && x <= 50) {
                    ran[1][1] = 6;
                    score += 1;
                    Random r = new Random();
                    speed[1] = r.nextInt((10 - 7) + 1) + 7;
                    speed[1] /= 10;
                }
                if (ran[2][1] >= -7 && ran[2][1] <= -5.8 && x >= 80 && x <= 100) {
                    ran[2][1] = 6;
                    score += 1;
                    Random r = new Random();
                    speed[2] = r.nextInt((10 - 7) + 1) + 7;
                    speed[2] /= 10;
                }
                ran[0][1] -= speed[0];
                if (ran[0][1] < -10) {
             
                    ran[0][1] = 6;
                }
                ran[1][1] -= speed[1];

                if (ran[1][1] < -10) {
                    ran[1][1] = 6;
                }

                ran[2][1] -= speed[2];

                if (ran[2][1] < -10) {
                    ran[2][1] = 6;
                }
           
            } else {
                if (level == 4) {
                    for (int i = 0; i < 3; i++) {
                        DrawSprite2(gl, ran[i][0], ran[i][1]);
                    }
              
                    if (ran[0][1] >= -7 && ran[0][1] <= -5.8 && x >= 0 && x <= 20) {
                        ran[0][1] = 6;
                        score += 1;
                        Random r = new Random();
                        speed[0] = r.nextInt((12 - 9) + 1) + 9;
                        speed[0] /= 10.0;
                    }
                    if (ran[1][1] >= -7 && ran[1][1] <= -5.8 && x >= 40 && x <= 50) {
                        ran[1][1] = 6;
                        score += 1;
                        Random r = new Random();
                        speed[1] = r.nextInt((12 - 9) + 1) + 9;
                        speed[1] /= 10;
                    }
                    if (ran[2][1] >= -7 && ran[2][1] <= -5.8 && x >= 80 && x <= 100) {
                        ran[2][1] = 6;
                        score += 1;
                        Random r = new Random();
                        speed[2] = r.nextInt((12 - 9) + 1) + 9;
                        speed[2] /= 10;
                    }
                    ran[0][1] -= speed[0];
                    if (ran[0][1] < -10) {
                   
                        ran[0][1] = 6;
                    }
                    ran[1][1] -= speed[1];

                    if (ran[1][1] < -10) {
                        ran[1][1] = 6;
                    }

                    ran[2][1] -= speed[2];

                    if (ran[2][1] < -10) {
                        ran[2][1] = 6;
                    }
               
                } else {
                    if (level == 5) {
                        for (int i = 0; i < 3; i++) {
                            DrawSprite2(gl, ran[i][0], ran[i][1]);
                        }
                   
                        if (ran[0][1] >= -7 && ran[0][1] <= -5.8 && x >= 0 && x <= 20) {
                            ran[0][1] = 6;
                            score += 1;
                            Random r = new Random();
                            speed[0] = r.nextInt((14 - 10) + 1) + 10;
                            speed[0] /= 10.0;
                        }
                        if (ran[1][1] >= -7 && ran[1][1] <= -5.8 && x >= 40 && x <= 50) {
                            ran[1][1] = 6;
                            score += 1;
                            Random r = new Random();
                            speed[1] = r.nextInt((14 - 10) + 1) + 10;
                            speed[1] /= 10.0;
                        }
                        if (ran[2][1] >= -7 && ran[2][1] <= -5.8 && x >= 80 && x <= 100) {
                            ran[2][1] = 6;
                            score += 1;
                            Random r = new Random();
                            speed[2] = r.nextInt((14 - 10) + 1) + 10;
                            speed[2] /= 10.0;
                        }
                        ran[0][1] -= speed[0];
                        if (ran[0][1] < -10) {
                      
                            ran[0][1] = 6;
                        }
                        ran[1][1] -= speed[1];

                        if (ran[1][1] < -10) {
                            ran[1][1] = 6;
                        }

                        ran[2][1] -= speed[2];

                        if (ran[2][1] < -10) {
                            ran[2][1] = 6;
                        }
                
                    }
                }
            }
            MainMenu.scoret.setText(score + "");
            //chicken
            pos[x][y] = 5;
            animationIndex = animationIndex % 3;
            DrawSprite(gl, 10, 80, animationIndex, 1);
            DrawSprite(gl, 45, 80, animationIndex, 1);
            DrawSprite(gl, 80, 80, animationIndex, 1);
            animationIndex++;

        } else {
            timer2.stop();
            //resume, main menu, arrow
            DrawSpritesp(gl, 50, 55, 14, 1, (float) 1, (float) 0.5);
            DrawSpritesp(gl, 50, 40, 15, 1, (float) 1, (float) 0.7);
            DrawSprite(gl, arrowx, arrowy, 16, (float) 0.4);
            handleKeyPress2();
        }
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

    public void handleKeyPress2() {
        if (isKeyPressed(KeyEvent.VK_ESCAPE)) {
            pause = !pause;
        }
        if (isKeyPressed(KeyEvent.VK_UP)) {
            if (arrowy == 40) {
                arrowy = 57;
            }
        }
        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            if (arrowy == 57) {
                arrowy = 40;
            }
        }
        if (isKeyPressed(KeyEvent.VK_ENTER) && arrowy == 57) {
            pause = !pause;
        }
        if (isKeyPressed(KeyEvent.VK_ENTER) && arrowy == 40) {
            GLContext context = glcanvas.getContext();
            context.makeCurrent();
            beginGLEventListener eventListener1 = (beginGLEventListener) lst.get(0);
            eventListener1.init(glcanvas);
            eventListener1.display(glcanvas);
            glcanvas.removeGLEventListener((PlayGame) lst.get(2));
            glcanvas.removeKeyListener((PlayGame) lst.get(2));
            glcanvas.addGLEventListener(eventListener1);
            glcanvas.addKeyListener(eventListener1);
        }

    }

    public void handleKeyPress1() {
        if (isKeyPressed(KeyEvent.VK_ESCAPE)) {
            pause = !pause;
        }
        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            if (x > 11) {
                pos[x][y] = 0;
                x -= 12;
                l = 1;
                r = 0;
            }
            animationIndex++;
        }
        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            if (x < 80) {
                pos[x][y] = 0;
                x += 12;
                l = 0;
                r = 1;
            }
            animationIndex++;
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

    public void DrawSpritesp(GL gl, double x, double y, int index, float scale, float t1, float t2) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On
//try to recall with index 5 and x,y random
        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.3 * t1, 0.3 * t2, 1);
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

    public void DrawSprite(GL gl, double x, double y, int index, float scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);	// Turn Blending On
//try to recall with index 5 and x,y random
        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);
        gl.glScaled(0.3 * scale, 0.3 * scale, 1);
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

    public void DrawBackground(GL gl, int in) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[in]);	// Turn Blending On

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
