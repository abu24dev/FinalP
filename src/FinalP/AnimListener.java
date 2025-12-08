package FinalP;

import FinalP.Texture.TextureReader;
import javax.media.opengl.*;
import javax.swing.*;
import javax.media.opengl.glu.GLU;
import java.awt.event.*;
import java.io.IOException;

public abstract class AnimListener implements GLEventListener, KeyListener, MouseListener {

    protected String assetsFolderName = "Assets";

    protected AudioPlayer audioPlayer;
    public boolean isPaused = false;

    int pauseID, playID, soundOnID, soundOffID;
    int[] healthTexIDs = new int[11];
    int width, height;
    JPanel pausePanel;
    // مكان وحجم الزراير
    int btnSize = 60;
    int margin = 30;
    int topMargin = 90;
    int btn1X, btn1Y, btn2X, btn2Y;


    public void setAudioPlayer(AudioPlayer audio) {
        this.audioPlayer = audio;
    }

    public void setPausePanel(JPanel panel) {this.pausePanel = panel;}

    public abstract void resetGame();

    public void initUI(GL gl) {
        try {
            pauseID = loadOneTexture(gl, "Assets/pause.png");
            playID = loadOneTexture(gl, "Assets/play.png");
            soundOnID = loadOneTexture(gl, "Assets/sound_on.png");
            soundOffID = loadOneTexture(gl, "Assets/sound_off.png");
            for (int hp = 0; hp <= 100; hp += 10) {

                String imgName = "Assets/health" + hp + ".png";

                // بنقسم على 10 عشان نجيب مكان التخزين في المصفوفة (0, 1, 2...10)
                int index = hp / 10;

                healthTexIDs[index] = loadOneTexture(gl, imgName);
            }
            System.out.println("Health bars loaded successfully (0 to 100).");

        } catch (Exception e) {
            System.out.println("Error loading UI textures: " + e.getMessage());
        }
    }

    // دالة رسم البار (تم تعديلها لحل مشكلة الـ Shield)
    public void drawHealthBar(GL gl, int hp, int x, int y) {
        // حماية الحدود
        if (hp < 0) hp = 0;
        if (hp > 100) hp = 100;

        // الحساب العادي
        int index = hp / 10;

        // --- الحل السحري هنا ---
        // لو الصحة فيها رقم (أكبر من 0) بس القسمة طلعت 0 (يعني الصحة بين 1 و 9)
        // لازم نجبره يعرض الصورة رقم 1 (health10) عشان اللاعب يعرف إنه لسه عايش
        if (index == 0 && hp > 0) {
            index = 1;
        }
        // -----------------------

        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, healthTexIDs[index]);

        gl.glPushMatrix();
        gl.glTranslated(x, y, 0);

        // تصغير الحجم للنص
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
        updateBtnPositions(); // تحديث الأماكن لو الشاشة اتغيرت

        // إعداد الكاميرا للرسم 2D فوق اللعبة
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        new GLU().gluOrtho2D(0, width, 0, height);

        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glLoadIdentity();

        gl.glEnable(GL.GL_BLEND);

        // رسم زرار Pause/Play
        int currentPauseTex = isPaused ? playID : pauseID;
        drawIcon(gl, currentPauseTex, btn1X, btn1Y, btnSize);

        // رسم زرار الصوت
        int currentSoundTex = (audioPlayer != null && audioPlayer.isMuted()) ? soundOffID : soundOnID;
        drawIcon(gl, currentSoundTex, btn2X, btn2Y, btnSize);

        gl.glDisable(GL.GL_BLEND);

        // إرجاع الكاميرا لوضعها الأصلي
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glPopMatrix();
    }

    // دوال مساعدة
    private void updateBtnPositions() {
        // حساب الإحداثي السيني (X) للمكان الأيمن باستخدام الهامش الجانبي العادي
        btn1X = width - margin - btnSize;

        // حساب الإحداثي الصادي (Y) باستخدام الهامش العلوي الجديد (topMargin)
        // ده هينزل الزراير لتحت أكتر
        btn1Y = height - topMargin - btnSize;

        // الزر التاني جنبه على الشمال
        btn2X = btn1X - margin - btnSize;
        // الزر التاني في نفس المستوى الرأسي
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

    // =============================================================
    // التعامل مع الماوس (مشترك للكل)
    // =============================================================
    @Override
    public void mouseClicked(MouseEvent e) {
        int mx = e.getX();
        int my = height - e.getY(); // عكس الـ Y

        if (mx >= btn1X && mx <= btn1X + btnSize && my >= btn1Y && my <= btn1Y + btnSize) {
            isPaused = !isPaused; // عكس الحالة

            if (pausePanel != null) {
                pausePanel.setVisible(isPaused);
            }

        }
        else if (mx >= btn2X && mx <= btn2X + btnSize && my >= btn2Y && my <= btn2Y + btnSize) {
            if(audioPlayer != null) audioPlayer.toggleMute();
        }
    }

    // دوال الماوس الإجبارية (فارغة)
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}