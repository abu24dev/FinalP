package FinalP;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioPlayer {

    private Clip clip;
    private boolean isMuted = false;
    private long clipTimePosition = 0; // متغير لحفظ توقيت الأغنية عند الإيقاف

    public AudioPlayer(String filePath) {
        try {
            File audioFile = new File(filePath);

            // التأكد أن الملف موجود قبل محاولة تشغيله
            if (audioFile.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
            } else {
                System.out.println("خطأ: ملف الصوت غير موجود في المسار: " + filePath);
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("حدث خطأ أثناء تحميل الصوت: " + e.getMessage());
        }
    }

    // دالة التشغيل لأول مرة
    public void playMusic() {
        if (clip != null && !isMuted) {
            clip.setFramePosition(0); // ابدأ من الأول
            clip.loop(Clip.LOOP_CONTINUOUSLY); // كرر الأغنية لما تخلص
            clip.start();
        }
    }

    // دالة التبديل بين الصامت والشغال (المسؤولة عن الزرار)
    public void toggleMute() {
        if (clip == null) return;

        if (isMuted) {
            // الحالة: كان صامت -> هنشغله
            // 1. رجع الشريط للمكان اللي وقف فيه
            clip.setMicrosecondPosition(clipTimePosition);
            // 2. شغل الصوت
            clip.start();
            // 3. تأكد إنه لسه بيعمل Loop
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            // 4. غير الحالة
            isMuted = false;
        } else {
            // الحالة: كان شغال -> هنكتمه
            // 1. احفظ هو وقف فين بالظبط
            clipTimePosition = clip.getMicrosecondPosition();
            // 2. وقف الصوت
            clip.stop();
            // 3. غير الحالة
            isMuted = true;
        }
    }

    // دالة عشان كلاس Anim يعرف حالياً الصوت شغال ولا لا
    // عشان يغير صورة الزرار بناءً عليها
    public boolean isMuted() {
        return isMuted;
    }
}