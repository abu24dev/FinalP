package FinalP;

import FinalP.Texture.TextureReader;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import java.awt.event.*;
import java.io.IOException;

// الكلاس الأب هينفذ MouseListener عشان يشغل الزراير في أي مود
public abstract class AnimListener implements GLEventListener, KeyListener, MouseListener {

    protected String assetsFolderName = "Assets";

    // =============================================================
    // متغيرات مشتركة (الصوت، الحالة، صور الزراير)
    // =============================================================
    protected AudioPlayer audioPlayer; // الصوت
    public boolean isPaused = false;   // عشان الكلاسات التانية تشوفها وتوقف اللعب

    // IDs وأبعاد الزراير
    int pauseID, playID, soundOnID, soundOffID;
    int width, height; // أبعاد الشاشة

    // مكان وحجم الزراير
    int btnSize = 60;
    int margin = 30;
    int btn1X, btn1Y, btn2X, btn2Y;

    // دالة لاستقبال كائن الصوت من الـ Main
    public void setAudioPlayer(AudioPlayer audio) {
        this.audioPlayer = audio;
    }

    // =============================================================
    // دالة تحميل صور الزراير (هنناديها جوه init في الكلاسات الابن)
    // =============================================================
    public void initUI(GL gl) {
        try {
            pauseID = loadOneTexture(gl, "Assets/pause.png");
            playID = loadOneTexture(gl, "Assets/play.png");
            soundOnID = loadOneTexture(gl, "Assets/sound_on.png");
            soundOffID = loadOneTexture(gl, "Assets/sound_off.png");
        } catch (Exception e) {
            System.out.println("Error loading UI buttons: " + e.getMessage());
        }
    }

    // =============================================================
    // دالة رسم الواجهة (هنناديها جوه display في الكلاسات الابن)
    // =============================================================
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
        btn1X = width - margin - btnSize;
        btn1Y = height - margin - btnSize;
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

    // =============================================================
    // التعامل مع الماوس (مشترك للكل)
    // =============================================================
    @Override
    public void mouseClicked(MouseEvent e) {
        int mx = e.getX();
        int my = height - e.getY(); // عكس الـ Y

        if (mx >= btn1X && mx <= btn1X + btnSize && my >= btn1Y && my <= btn1Y + btnSize) {
            isPaused = !isPaused; // عكس الحالة
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