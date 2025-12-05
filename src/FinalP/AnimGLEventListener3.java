package FinalP;

import FinalP.Texture.TextureReader;

import java.awt.event.*;
import java.io.IOException;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import java.util.BitSet;

public class AnimGLEventListener3 extends AnimListener {

    int maxWidth = 100, maxHeight = 100;
    int x = 20, y = 20;

    // -------------------------
    // TEXTURES (Option A)
    // -------------------------
    // 8 walking frames + 6 idle frames + 1 background
    String textureNames[] = {
            // WALK FRAMES (0–7)
            "Walk1.png", "Walk2.png", "Walk3.png", "Walk4.png",
            "Walk5.png", "Walk6.png", "Walk7.png", "Walk8.png",

            // IDLE FRAMES (8–13)
            "Idle1.png", "Idle2.png", "Idle3.png",
            "Idle4.png", "Idle5.png", "Idle6.png",

            // BACKGROUND (14)
            "7.png"
    };

    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    int[] textures = new int[textureNames.length];

    // ------- Animation Settings -------
    int walkIndex = 0;
    int idleIndex = 0;
    int walkFrames = 8;
    int idleFrames = 6;

    int frameDelay = 0;
    boolean facingLeft = false;

    public void init(GLAutoDrawable gld) {

        GL gl = gld.getGL();

        gl.glClearColor(1, 1, 1, 1);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture(
                        assetsFolderName + "//" + textureNames[i], true);

                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels()
                );

            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    public void display(GLAutoDrawable gld) {

        GL gl = gld.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();

        DrawBackground(gl);

        frameDelay++;

        // Detect movement
        boolean moving =
                isKeyPressed(KeyEvent.VK_LEFT) ||
                        isKeyPressed(KeyEvent.VK_RIGHT);

        handleKeyPress(moving);

        boolean animate = frameDelay % 7 == 0;

        if (animate) {
            if (moving) {
                walkIndex = (walkIndex + 1) % walkFrames;
            } else {
                idleIndex = (idleIndex + 1) % idleFrames;
            }
        }

        // DRAW CHARACTER
        if (moving) {
            DrawSprite(gl, x, y, walkIndex, 2.4f, facingLeft);
        } else {
            DrawSprite(gl, x, y, 8 + idleIndex, 2.4f, facingLeft);
        }
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }

    // ------------------------------------------------------------
    // Draw Sprite
    // ------------------------------------------------------------
    public void DrawSprite(GL gl, int x, int y, int index, float scale, boolean flip) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[index]);

        gl.glPushMatrix();
        gl.glTranslated(x / (maxWidth / 2.0) - 0.9, y / (maxHeight / 2.0) - 0.9, 0);

        if (flip)
            gl.glScaled(-0.1 * scale, 0.1 * scale, 1);
        else
            gl.glScaled(0.1 * scale, 0.1 * scale, 1);

        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-1, -1, -1);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(1, -1, -1);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(1, 1, -1);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-1, 1, -1);
        gl.glEnd();

        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }

    // ------------------------------------------------------------
    // Draw Background (index 14)
    // ------------------------------------------------------------
    public void DrawBackground(GL gl) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[14]);

        gl.glPushMatrix();
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0, 0);
        gl.glVertex3f(-1, -1, -1);
        gl.glTexCoord2f(1, 0);
        gl.glVertex3f(1, -1, -1);
        gl.glTexCoord2f(1, 1);
        gl.glVertex3f(1, 1, -1);
        gl.glTexCoord2f(0, 1);
        gl.glVertex3f(-1, 1, -1);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    // ------------------------------------------------------------
    // Movement + Facing Direction
    // ------------------------------------------------------------
    public void handleKeyPress(boolean moving) {

        if (isKeyPressed(KeyEvent.VK_LEFT)) {
            facingLeft = true;
            if (x > 0) x--;
        }

        if (isKeyPressed(KeyEvent.VK_RIGHT)) {
            facingLeft = false;
            if (x < maxWidth - 10) x++;
        }

        // Optional Y movement
        if (isKeyPressed(KeyEvent.VK_UP)) {
            // if (y < maxHeight - 10) y++;
        }

        if (isKeyPressed(KeyEvent.VK_DOWN)) {
            // if (y > 0) y--;
        }
    }

    // ----------------- Keyboard Handling -----------------
    BitSet keyBits = new BitSet(256);

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyBits.set(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyBits.clear(e.getKeyCode());
    }

    public boolean isKeyPressed(int keyCode) {
        return keyBits.get(keyCode);
    }
}
