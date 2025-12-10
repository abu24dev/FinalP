package FinalP;

import FinalP.Texture.TextureReader;
import com.sun.opengl.util.j2d.TextRenderer;
import java.awt.geom.Rectangle2D;
import javax.media.opengl.*;
import javax.swing.*;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public abstract class AnimListener implements GLEventListener, KeyListener, MouseListener {

    protected String assetsFolderName = "Assets";
    protected AudioPlayer audioPlayer;
    public boolean isPaused = false;

    // === متغيرات التايمر ونهاية اللعبة ===
    TextRenderer timeRenderer;
    float timeRemaining = 120.0f;
    boolean isTimeOver = false;
    public boolean isGameOver = false;
    protected String gameOverMessage = "";

    // === متغيرات UI والقوائم ===
    int pauseID, playID, soundOnID, soundOffID;
    int[] healthTexIDs = new int[11];
    int width, height;
    JPanel pausePanel;

    // +++ متغيرات قائمة النهاية الجديدة +++
    JPanel gameOverPanel;
    JLabel gameOverLabel;
    // +++++++++++++++++++++++++++++++++++

    int btnSize = 60;
    int margin = 30;
    int topMargin = 90;
    int btn1X, btn1Y, btn2X, btn2Y;

    public void setAudioPlayer(AudioPlayer audio) {
        this.audioPlayer = audio;
    }

    public void setPausePanel(JPanel panel) {
        this.pausePanel = panel;
    }

    // +++ دالة ربط قائمة النهاية +++
    public void setGameOverPanel(JPanel panel, JLabel label) {
        this.gameOverPanel = panel;
        this.gameOverLabel = label;
    }

    public void setGameOverMessage(String msg) {
        this.gameOverMessage = msg;
    }

    public abstract void resetGame();

    public void initTimer(GL gl) {
        timeRenderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 40));
    }

    public void drawTimer(GLAutoDrawable drawable, int width, int height) {
        if (!isPaused && timeRemaining > 0 && !isGameOver) {
            timeRemaining -= (1.0f / 24.0f);
            if (timeRemaining <= 0) {
                timeRemaining = 0;
                isTimeOver = true;
            }
        }

        String timeString = String.format("%02d", (int) Math.ceil(timeRemaining));
        Rectangle2D timeBounds = timeRenderer.getBounds(timeString);
        int x = (int) ((width / 2.0) - (timeBounds.getWidth() / 2.0));
        int y = height - 50;

        timeRenderer.beginRendering(width, height);
        timeRenderer.setColor(Color.BLACK);
        timeRenderer.draw(timeString, x + 2, y - 2);
        if (timeRemaining <= 10) timeRenderer.setColor(Color.RED);
        else timeRenderer.setColor(Color.WHITE);
        timeRenderer.draw(timeString, x, y);
        timeRenderer.endRendering();

        // ---------------------------------------------------------
        // التعديل: إظهار قائمة النهاية عند انتهاء اللعبة
        // ---------------------------------------------------------
        if (isGameOver) {
            // لو القائمة موجودة ومش ظاهرة، أظهرها وحدث النص
            if (gameOverPanel != null && !gameOverPanel.isVisible()) {
                if (gameOverLabel != null) gameOverLabel.setText("<html><center>" + gameOverMessage + "</center></html>");
                gameOverPanel.setVisible(true);
            }
        }
        // ---------------------------------------------------------

        // رسم الرسائل العادية (مثل Round Cleared) لو اللعبة لسه مخلصتش
        else if (!gameOverMessage.isEmpty()) {
            Rectangle2D bounds = timeRenderer.getBounds(gameOverMessage);
            int msgX = (int) ((width / 2.0) - (bounds.getWidth() / 2.0));
            int msgY = (int) ((height / 2.0) - (bounds.getHeight() / 2.0));

            timeRenderer.beginRendering(width, height);
            timeRenderer.setColor(new Color(0, 0, 0, 180));
            timeRenderer.draw(gameOverMessage, msgX + 4, msgY - 4);
            timeRenderer.setColor(Color.YELLOW);
            timeRenderer.draw(gameOverMessage, msgX, msgY);
            timeRenderer.endRendering();
        }
    }

    // ... (باقي الدوال initUI, drawHealthBar, drawUI, mouseClicked كما هي تماماً) ...

    public void initUI(GL gl) {
        try {
            pauseID = loadOneTexture(gl, "Assets/pause.png");
            playID = loadOneTexture(gl, "Assets/play.png");
            soundOnID = loadOneTexture(gl, "Assets/sound_on.png");
            soundOffID = loadOneTexture(gl, "Assets/sound_off.png");
            for (int hp = 0; hp <= 100; hp += 10) {
                String imgName = "Assets/health" + hp + ".png";
                int index = hp / 10;
                healthTexIDs[index] = loadOneTexture(gl, imgName);
            }
        } catch (Exception e) {
            System.out.println("Error loading UI textures: " + e.getMessage());
        }
    }

    public void drawHealthBar(GL gl, int hp, int x, int y) {
        if (hp < 0) hp = 0;
        if (hp > 100) hp = 100;
        int index = hp / 10;
        if (index == 0 && hp > 0) index = 1;

        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, healthTexIDs[index]);
        gl.glPushMatrix();
        gl.glTranslated(x, y, 0);
        gl.glScaled(0.5, 0.5, 1);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0, 0); gl.glVertex2f(0, 0);
        gl.glTexCoord2f(1, 0); gl.glVertex2f(400, 0);
        gl.glTexCoord2f(1, 1); gl.glVertex2f(400, 80);
        gl.glTexCoord2f(0, 1); gl.glVertex2f(0, 80);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }

    public void drawUI(GL gl, int w, int h) {
        this.width = w;
        this.height = h;
        updateBtnPositions();

        gl.glMatrixMode(GL.GL_PROJECTION); gl.glPushMatrix(); gl.glLoadIdentity();
        new GLU().gluOrtho2D(0, width, 0, height);
        gl.glMatrixMode(GL.GL_MODELVIEW); gl.glPushMatrix(); gl.glLoadIdentity();
        gl.glEnable(GL.GL_BLEND);

        int currentPauseTex = isPaused ? playID : pauseID;
        drawIcon(gl, currentPauseTex, btn1X, btn1Y, btnSize);

        int currentSoundTex = (audioPlayer != null && audioPlayer.isMuted()) ? soundOffID : soundOnID;
        drawIcon(gl, currentSoundTex, btn2X, btn2Y, btnSize);

        gl.glDisable(GL.GL_BLEND);
        gl.glMatrixMode(GL.GL_PROJECTION); gl.glPopMatrix();
        gl.glMatrixMode(GL.GL_MODELVIEW); gl.glPopMatrix();
    }

    private void updateBtnPositions() {
        btn1X = width - margin - btnSize;
        btn1Y = height - topMargin - btnSize;
        btn2X = btn1X - margin - btnSize;
        btn2Y = btn1Y;
    }

    private void drawIcon(GL gl, int texID, int x, int y, int size) {
        gl.glBindTexture(GL.GL_TEXTURE_2D, texID);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0, 0); gl.glVertex2f(x, y);
        gl.glTexCoord2f(1, 0); gl.glVertex2f(x + size, y);
        gl.glTexCoord2f(1, 1); gl.glVertex2f(x + size, y + size);
        gl.glTexCoord2f(0, 1); gl.glVertex2f(x, y + size);
        gl.glEnd();
    }

    private int loadOneTexture(GL gl, String path) throws IOException {
        TextureReader.Texture tex = TextureReader.readTexture(path, true);
        int[] id = new int[1];
        gl.glGenTextures(1, id, 0);
        gl.glBindTexture(GL.GL_TEXTURE_2D, id[0]);
        new GLU().gluBuild2DMipmaps(GL.GL_TEXTURE_2D, GL.GL_RGBA, tex.getWidth(), tex.getHeight(), GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, tex.getPixels());
        return id[0];
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mx = e.getX();
        int my = height - e.getY();
        // منع الضغط على أزرار التحكم لو اللعبة خلصانة
        if (isGameOver) return;

        if (mx >= btn1X && mx <= btn1X + btnSize && my >= btn1Y && my <= btn1Y + btnSize) {
            isPaused = !isPaused;
            if (pausePanel != null) pausePanel.setVisible(isPaused);
        } else if (mx >= btn2X && mx <= btn2X + btnSize && my >= btn2Y && my <= btn2Y + btnSize) {
            if (audioPlayer != null) audioPlayer.toggleMute();
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}