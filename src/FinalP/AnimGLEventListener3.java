package FinalP;

import FinalP.Texture.TextureReader;
import com.sun.opengl.util.j2d.TextRenderer;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import java.awt.*;
import java.awt.event.*;
import java.util.BitSet;
import java.util.Random;

public class AnimGLEventListener3 extends AnimListener implements KeyListener {

    Player player1;
    Player enemy;

    static BitSet keyBits = new BitSet(256);

    enum Difficulty {EASY, MEDIUM, HARD}
    Difficulty currentDifficulty = Difficulty.MEDIUM;

    Random globalRand = new Random();

    int myCharIndex = 0;
    int enemyCharIndex = 1;

    // --- متغيرات الجولات ---
    int currentRound = 1;
    final int MAX_ROUNDS = 3;
    boolean isRoundOver = false;
    long roundOverStartTime = 0;

    // --- متغيرات الهايسكور والوقت ---
    float totalTimeTaken = 0;   // لتجميع الوقت المستغرق في كل الجولات
    boolean scoreSaved = false; // لضمان حفظ السكور مرة واحدة فقط

    // --- متغيرات الأسماء ---
    String player1Name = "Player";
    String enemyNameDisplay = "CPU";
    TextRenderer nameRenderer;

    // دالة استقبال الشخصيات
    public void setCharacters(int p1, int p2) {
        this.myCharIndex = p1;
        this.enemyCharIndex = p2;
    }

    // دالة استقبال الصعوبة
    public void setDifficulty(String diff) {
        if (diff == null) return;
        switch (diff.toUpperCase()) {
            case "EASY": currentDifficulty = Difficulty.EASY; break;
            case "HARD": currentDifficulty = Difficulty.HARD; break;
            default: currentDifficulty = Difficulty.MEDIUM; break;
        }
        System.out.println("Difficulty set to: " + currentDifficulty);
    }

    // دالة استقبال اسم اللاعب
    public void setPlayerNames(String n1, String n2_ignored) {
        this.player1Name = n1;
        // اسم العدو سيتم تحديده أوتوماتيكياً
    }

    // --- Texture Arrays ---
    String[] shinobiTextures = {
            "Assets/Shinobi/Walk1.png", "Assets/Shinobi/Walk2.png", "Assets/Shinobi/Walk3.png", "Assets/Shinobi/Walk4.png", "Assets/Shinobi/Walk5.png", "Assets/Shinobi/Walk6.png", "Assets/Shinobi/Walk7.png", "Assets/Shinobi/Walk8.png",
            "Assets/Shinobi/Idle1.png", "Assets/Shinobi/Idle2.png", "Assets/Shinobi/Idle3.png", "Assets/Shinobi/Idle4.png", "Assets/Shinobi/Idle5.png", "Assets/Shinobi/Idle6.png",
            "Assets/Shinobi/IAttack1.png", "Assets/Shinobi/IAttack2.png", "Assets/Shinobi/IAttack3.png", "Assets/Shinobi/IAttack4.png", "Assets/Shinobi/IAttack5.png",
            "Assets/Shinobi/IIAttack1.png", "Assets/Shinobi/IIAttack2.png", "Assets/Shinobi/IIAttack3.png",
            "Assets/Shinobi/IIIAttack1.png", "Assets/Shinobi/IIIAttack2.png", "Assets/Shinobi/IIIAttack3.png", "Assets/Shinobi/IIIAttack4.png",
            "Assets/Shinobi/Jump1.png", "Assets/Shinobi/Jump2.png", "Assets/Shinobi/Jump3.png", "Assets/Shinobi/Jump4.png", "Assets/Shinobi/Jump5.png", "Assets/Shinobi/Jump6.png", "Assets/Shinobi/Jump7.png", "Assets/Shinobi/Jump8.png", "Assets/Shinobi/Jump9.png", "Assets/Shinobi/Jump10.png", "Assets/Shinobi/Jump11.png",
            "Assets/Shinobi/Run1.png", "Assets/Shinobi/Run2.png", "Assets/Shinobi/Run3.png", "Assets/Shinobi/Run4.png", "Assets/Shinobi/Run5.png", "Assets/Shinobi/Run6.png", "Assets/Shinobi/Run7.png", "Assets/Shinobi/Run8.png",
            "Assets/Shinobi/Hurt1.png", "Assets/Shinobi/Hurt2.png",
            "Assets/Shinobi/Dead1.png", "Assets/Shinobi/Dead2.png", "Assets/Shinobi/Dead3.png", "Assets/Shinobi/Dead4.png",
            "Assets/Shinobi/Shield1.png", "Assets/Shinobi/Shield2.png", "Assets/Shinobi/Shield3.png", "Assets/Shinobi/Shield4.png",
            "Assets/Shinobi/7.png"
    };

    String[] fighterTextures = {
            "Assets/Fighter/Walk1.png", "Assets/Fighter/Walk2.png", "Assets/Fighter/Walk3.png", "Assets/Fighter/Walk4.png", "Assets/Fighter/Walk5.png", "Assets/Fighter/Walk6.png", "Assets/Fighter/Walk7.png", "Assets/Fighter/Walk8.png",
            "Assets/Fighter/Idle1.png", "Assets/Fighter/Idle2.png", "Assets/Fighter/Idle3.png", "Assets/Fighter/Idle4.png", "Assets/Fighter/Idle5.png", "Assets/Fighter/Idle6.png",
            "Assets/Fighter/IAttack1.png", "Assets/Fighter/IAttack2.png", "Assets/Fighter/IAttack3.png", "Assets/Fighter/IAttack4.png",
            "Assets/Fighter/IIAttack1.png", "Assets/Fighter/IIAttack2.png", "Assets/Fighter/IIAttack3.png",
            "Assets/Fighter/IIIAttack1.png", "Assets/Fighter/IIIAttack2.png", "Assets/Fighter/IIIAttack3.png", "Assets/Fighter/IIIAttack4.png",
            "Assets/Fighter/Jump1.png", "Assets/Fighter/Jump2.png", "Assets/Fighter/Jump3.png", "Assets/Fighter/Jump4.png", "Assets/Fighter/Jump5.png", "Assets/Fighter/Jump6.png", "Assets/Fighter/Jump7.png", "Assets/Fighter/Jump8.png", "Assets/Fighter/Jump9.png", "Assets/Fighter/Jump10.png",
            "Assets/Fighter/Run1.png", "Assets/Fighter/Run2.png", "Assets/Fighter/Run3.png", "Assets/Fighter/Run4.png", "Assets/Fighter/Run5.png", "Assets/Fighter/Run6.png", "Assets/Fighter/Run7.png", "Assets/Fighter/Run8.png",
            "Assets/Fighter/Hurt1.png", "Assets/Fighter/Hurt2.png", "Assets/Fighter/Hurt3.png",
            "Assets/Fighter/Dead1.png", "Assets/Fighter/Dead2.png", "Assets/Fighter/Dead3.png",
            "Assets/Fighter/Shield1.png", "Assets/Fighter/Shield2.png",
            "Assets/Fighter/7.png"
    };

    String[] samuraiTextures = {
            "Assets/Samurai/Walk1.png", "Assets/Samurai/Walk2.png", "Assets/Samurai/Walk3.png", "Assets/Samurai/Walk4.png", "Assets/Samurai/Walk5.png", "Assets/Samurai/Walk6.png", "Assets/Samurai/Walk7.png", "Assets/Samurai/Walk8.png",
            "Assets/Samurai/Idle1.png", "Assets/Samurai/Idle2.png", "Assets/Samurai/Idle3.png", "Assets/Samurai/Idle4.png", "Assets/Samurai/Idle5.png", "Assets/Samurai/Idle6.png",
            "Assets/Samurai/IAttack1.png", "Assets/Samurai/IAttack2.png", "Assets/Samurai/IAttack3.png", "Assets/Samurai/IAttack4.png", "Assets/Samurai/IAttack5.png", "Assets/Samurai/IAttack6.png",
            "Assets/Samurai/IIAttack1.png", "Assets/Samurai/IIAttack2.png", "Assets/Samurai/IIAttack3.png", "Assets/Samurai/IIAttack4.png",
            "Assets/Samurai/IIIAttack1.png", "Assets/Samurai/IIIAttack2.png", "Assets/Samurai/IIIAttack3.png",
            "Assets/Samurai/Jump1.png", "Assets/Samurai/Jump2.png", "Assets/Samurai/Jump3.png", "Assets/Samurai/Jump4.png", "Assets/Samurai/Jump5.png", "Assets/Samurai/Jump6.png", "Assets/Samurai/Jump7.png", "Assets/Samurai/Jump8.png", "Assets/Samurai/Jump9.png", "Assets/Samurai/Jump10.png", "Assets/Samurai/Jump11.png", "Assets/Samurai/Jump12.png",
            "Assets/Samurai/Run1.png", "Assets/Samurai/Run2.png", "Assets/Samurai/Run3.png", "Assets/Samurai/Run4.png", "Assets/Samurai/Run5.png", "Assets/Samurai/Run6.png", "Assets/Samurai/Run7.png", "Assets/Samurai/Run8.png",
            "Assets/Samurai/Hurt1.png", "Assets/Samurai/Hurt2.png",
            "Assets/Samurai/Dead1.png", "Assets/Samurai/Dead2.png", "Assets/Samurai/Dead3.png",
            "Assets/Samurai/Shield1.png", "Assets/Samurai/Shield2.png",
            "Assets/Samurai/7.png"
    };

    int[] MAX_WALK = {8, 8, 8};
    int[] MAX_IDLE = {6, 6, 6};
    int[] MAX_ATTACK1 = {5, 4, 6};
    int[] MAX_ATTACK2 = {3, 3, 4};
    int[] MAX_ATTACK3 = {4, 4, 3};
    int[] MAX_JUMP = {11, 10, 12};
    int[] MAX_RUN = {8, 8, 8};
    int[] MAX_HURT = {2, 3, 2};
    int[] MAX_DEAD = {4, 3, 3};
    int[] MAX_SHIELD = {4, 2, 2};

    int[][] shinobiIDs;
    int[][] fighterIDs;
    int[][] samuraiIDs;
    int[] bgIDs = new int[3];

    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClearColor(1, 1, 1, 1);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        super.initUI(gl);
        super.initTimer(gl);

        nameRenderer = new TextRenderer(new Font("SansSerif", Font.BOLD, 24));

        shinobiIDs = loadCharacter(gl, shinobiTextures, 0);
        fighterIDs = loadCharacter(gl, fighterTextures, 1);
        samuraiIDs = loadCharacter(gl, samuraiTextures, 2);

        int[][] p1Textures = getTextureByIndex(myCharIndex);
        player1 = new Player(15, 20, p1Textures, myCharIndex, false, false);

        player1.setControls(
                KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D,
                KeyEvent.VK_Z, KeyEvent.VK_X, KeyEvent.VK_C
        );

        int[][] enemyTextures = getTextureByIndex(enemyCharIndex);
        enemy = new Player(40, 20, enemyTextures, enemyCharIndex, true, true);

        updateEnemyName(); // تحديث اسم العدو
        System.out.println("Started Round " + currentRound + " Difficulty: " + currentDifficulty);
    }

    private void updateEnemyName() {
        String charName = "";
        switch(enemyCharIndex) {
            case 0: charName = "Shinobi"; break;
            case 1: charName = "Fighter"; break;
            case 2: charName = "Samurai"; break;
        }
        enemyNameDisplay = "CPU (" + charName + ")";
    }

    private int[][] getTextureByIndex(int index) {
        if (index == 0) return shinobiIDs;
        if (index == 1) return fighterIDs;
        return samuraiIDs;
    }

    int[][] loadCharacter(GL gl, String[] texFiles, int charIdx) {
        int[][] ids = new int[10][15];
        int offset = 0;
        for (int i = 0; i < MAX_WALK[charIdx]; i++) ids[0][i] = genTex(gl, texFiles[offset++]);
        for (int i = 0; i < MAX_IDLE[charIdx]; i++) ids[1][i] = genTex(gl, texFiles[offset++]);
        for (int i = 0; i < MAX_ATTACK1[charIdx]; i++) ids[2][i] = genTex(gl, texFiles[offset++]);
        for (int i = 0; i < MAX_ATTACK2[charIdx]; i++) ids[3][i] = genTex(gl, texFiles[offset++]);
        for (int i = 0; i < MAX_ATTACK3[charIdx]; i++) ids[4][i] = genTex(gl, texFiles[offset++]);
        for (int i = 0; i < MAX_JUMP[charIdx]; i++) ids[5][i] = genTex(gl, texFiles[offset++]);
        for (int i = 0; i < MAX_RUN[charIdx]; i++) ids[6][i] = genTex(gl, texFiles[offset++]);
        for (int i = 0; i < MAX_HURT[charIdx]; i++) ids[7][i] = genTex(gl, texFiles[offset++]);
        for (int i = 0; i < MAX_DEAD[charIdx]; i++) ids[8][i] = genTex(gl, texFiles[offset++]);
        for (int i = 0; i < MAX_SHIELD[charIdx]; i++) ids[9][i] = genTex(gl, texFiles[offset++]);
        bgIDs[charIdx] = genTex(gl, texFiles[offset]);
        return ids;
    }

    int genTex(GL gl, String path) {
        try {
            TextureReader.Texture tex = TextureReader.readTexture(path, true);
            int[] id = new int[1];
            gl.glGenTextures(1, id, 0);
            gl.glBindTexture(GL.GL_TEXTURE_2D, id[0]);
            new GLU().gluBuild2DMipmaps(GL.GL_TEXTURE_2D, GL.GL_RGBA,
                    tex.getWidth(), tex.getHeight(),
                    GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, tex.getPixels());
            return id[0];
        } catch (Exception e) {
            System.out.println("Err loading texture: " + path + " -> " + e);
            return -1;
        }
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glColor3f(1f, 1f, 1f);
        DrawBackground(gl, bgIDs[0]);

        if (!isPaused && !isTimeOver && !isGameOver && !isRoundOver) {
            player1.update(enemy);
            enemy.update(player1);
        } else {
            if(player1.state == 8) player1.update(null);
            if(enemy.state == 8) enemy.update(null);
        }

        // --- منطق الجولات والفوز والهايسكور ---

        if (player1.state == 8 && !isGameOver) {
            isGameOver = true;
            setGameOverMessage("GAME OVER - YOU LOSE!");
        }
        else if (enemy.state == 8 && !isRoundOver && !isGameOver) {
            isRoundOver = true;
            roundOverStartTime = System.currentTimeMillis();

            if (currentRound < MAX_ROUNDS) {
                setGameOverMessage("ROUND " + currentRound + " CLEARED!");
            } else {
                isGameOver = true;
                setGameOverMessage("YOU ARE THE CHAMPION!");

                // --- حفظ الهاي سكور عند الفوز ---
                if (!scoreSaved) {
                    // الوقت المستغرق في آخر جولة
                    float timeSpentInLastRound = 120.0f - timeRemaining;
                    // إضافته للوقت الكلي
                    totalTimeTaken += timeSpentInLastRound;

                    // الحفظ في الملف
                    HighScoreManager.saveScore(player1Name, (int)totalTimeTaken, currentDifficulty.toString());
                    System.out.println("New Score Saved: " + (int)totalTimeTaken + " seconds");
                    scoreSaved = true;
                }
                // -----------------------------
            }
        }
        else if (isTimeOver && !isGameOver) {
            isGameOver = true;
            if (player1.health > enemy.health) setGameOverMessage("YOU WIN (TIME)!");
            else setGameOverMessage("GAME OVER (TIME)!");
        }

        // --- الانتقال للجولة التالية ---
        if (isRoundOver && !isGameOver) {
            long now = System.currentTimeMillis();
            if (now - roundOverStartTime > 3000) {
                startNextRound();
            }
        }

        player1.draw(gl);
        enemy.draw(gl);

        super.drawUI(gl, drawable.getWidth(), drawable.getHeight());

        gl.glMatrixMode(GL.GL_PROJECTION); gl.glPushMatrix(); gl.glLoadIdentity();
        new GLU().gluOrtho2D(0, drawable.getWidth(), 0, drawable.getHeight());
        gl.glMatrixMode(GL.GL_MODELVIEW); gl.glPushMatrix(); gl.glLoadIdentity();

        drawHealthBar(gl, player1.health, 20, drawable.getHeight() - 60);
        drawHealthBar(gl, enemy.health, drawable.getWidth() - 220, drawable.getHeight() - 60);

        // تحديث ورسم الأسماء
        updateEnemyName();
        drawPlayerNames(drawable.getWidth(), drawable.getHeight());

        gl.glMatrixMode(GL.GL_PROJECTION); gl.glPopMatrix();
        gl.glMatrixMode(GL.GL_MODELVIEW); gl.glPopMatrix();
        super.drawTimer(drawable, drawable.getWidth(), drawable.getHeight());
    }

    private void drawPlayerNames(int width, int height) {
        nameRenderer.beginRendering(width, height);

        // اسم اللاعب (سماوي)
        nameRenderer.setColor(Color.CYAN);
        nameRenderer.draw(player1Name, 20, height - 80); // نفس الإحداثي المضبوط

        // اسم العدو (أحمر)
        nameRenderer.setColor(Color.RED);
        nameRenderer.draw(enemyNameDisplay, width - 250, height - 80);

        nameRenderer.endRendering();
    }

    private void startNextRound() {
        // حساب الوقت المستغرق في الجولة المنتهية وإضافته للكلي
        float timeSpent = 120.0f - timeRemaining;
        totalTimeTaken += timeSpent;

        currentRound++;
        isRoundOver = false;
        setGameOverMessage("");
        timeRemaining = 120.0f; // إعادة التايمر

        // تدوير الأعداء
        enemyCharIndex = (enemyCharIndex + 1) % 3;
        updateEnemyName();

        System.out.println("Starting Round " + currentRound + " vs Enemy " + enemyCharIndex);

        player1.reset(15, 20, false);

        enemy.textureIDs = getTextureByIndex(enemyCharIndex);
        enemy.charIndex = enemyCharIndex;
        enemy.reset(40, 20, true);
    }

    void DrawBackground(GL gl, int tex) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, tex);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0, 0); gl.glVertex2f(-1, -1);
        gl.glTexCoord2f(1, 0); gl.glVertex2f(1, -1);
        gl.glTexCoord2f(1, 1); gl.glVertex2f(1, 1);
        gl.glTexCoord2f(0, 1); gl.glVertex2f(-1, 1);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION); gl.glLoadIdentity();
        gl.glOrtho(-1, 1, -1, 1, -1, 1);
        gl.glMatrixMode(GL.GL_MODELVIEW);
    }

    @Override
    public void resetGame() {
        setGameOverMessage("");
        isGameOver = false;
        isTimeOver = false;
        isRoundOver = false;
        timeRemaining = 120.0f;
        currentRound = 1;

        // تصفير عداد الهايسكور عند إعادة اللعب
        totalTimeTaken = 0;
        scoreSaved = false;

        player1.reset(15, 20, false);
        enemy.textureIDs = getTextureByIndex(enemyCharIndex);
        enemy.charIndex = enemyCharIndex;
        enemy.reset(40, 20, true);
        keyBits.clear();
        System.out.println("Game Reset.");
    }

    @Override public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        keyBits.set(code, true);
        if (code == player1.kAtt1) player1.queueAttack(2);
        if (code == player1.kAtt2) player1.queueAttack(3);
        if (code == player1.kAtt3) player1.queueAttack(4);
    }

    @Override public void keyReleased(KeyEvent e) { keyBits.set(e.getKeyCode(), false); }
    @Override public void keyTyped(KeyEvent e) {}

    // =========================================================
    //  Player Class
    // =========================================================
    class Player {
        float x, y;
        boolean facingLeft;
        int charIndex;
        int[][] textureIDs;
        int health = 100;
        int animIndex = 0;
        int frameDelay = 0;
        int state = 1;
        int lastState = 1;

        float velocityY = 0;
        float gravity = 0.1f;
        float jumpPower = 1.3f;
        float groundY = 20;
        boolean isJumping = false;

        int kUp, kDown, kLeft, kRight, kAtt1, kAtt2, kAtt3;
        int queuedAttackType = 0;
        boolean hasHitThisAttack = false;
        float hitRange = 6.5f;
        boolean wasHit = false;
        int damage = 20;

        boolean isAI;
        Random rand = new Random();

        public Player(float startX, float startY, int[][] ids, int cIndex, boolean startFaceLeft, boolean ai) {
            this.x = startX; this.y = startY; this.groundY = startY;
            this.textureIDs = ids; this.charIndex = cIndex;
            this.facingLeft = startFaceLeft; this.isAI = ai;
        }

        public void setControls(int up, int down, int left, int right, int a1, int a2, int a3) {
            this.kUp = up; this.kDown = down; this.kLeft = left; this.kRight = right;
            this.kAtt1 = a1; this.kAtt2 = a2; this.kAtt3 = a3;
        }

        public void queueAttack(int attackState) {
            if (isAttacking() || state == 7 || state == 8) return;
            if (queuedAttackType == 0) queuedAttackType = attackState;
        }

        boolean isAttacking() { return state == 2 || state == 3 || state == 4; }

        public void reset(float startX, float startY, boolean startFaceLeft) {
            this.x = startX; this.y = startY; this.groundY = startY;
            this.facingLeft = startFaceLeft; this.health = 100;
            this.state = 1; this.lastState = 1;
            this.animIndex = 0; this.frameDelay = 0;
            this.velocityY = 0; this.isJumping = false;
            this.queuedAttackType = 0; this.hasHitThisAttack = false;
            this.wasHit = false;
        }

        public void update(Player target) {
            if (state == 8) {
                y += velocityY;
                velocityY -= gravity;
                if (y <= groundY) { y = groundY; velocityY = 0; }

                frameDelay++;
                if (frameDelay % 3 == 0) animIndex++;
                if (animIndex >= MAX_DEAD[charIndex]) {
                    animIndex = MAX_DEAD[charIndex] - 1;
                }
                return;
            }

            if (state == 7) {
                frameDelay++;
                if (frameDelay % 3 == 0) animIndex++;
                if (animIndex >= MAX_HURT[charIndex]) {
                    state = 1; animIndex = 0; frameDelay = 0;
                }
                return;
            }

            y += velocityY;
            velocityY -= gravity;

            if (y <= groundY) {
                y = groundY; velocityY = 0; isJumping = false;
            } else {
                isJumping = true;
            }

            lastState = state;

            if (isAI) updateAI(target);
            else handleHumanInput();

            if (x < 0) x = 0;
            if (x > 50) x = 50;

            if (target != null && target.state != 8) checkHit(target);

            if (state != lastState) { animIndex = 0; frameDelay = 0; }

            frameDelay++;
            if (frameDelay % 3 == 0) {
                animIndex++;
                int maxFrames = getMaxFramesForState();
                if (isAttacking() && animIndex >= maxFrames) {
                    state = 1; animIndex = 0; frameDelay = 0; hasHitThisAttack = false;
                } else if ((state == 9) && animIndex >= maxFrames) {
                    animIndex = maxFrames - 1;
                } else if (animIndex >= maxFrames) {
                    animIndex = 0;
                }
            }
        }

        private int getMaxFramesForState() {
            switch (state) {
                case 0: return MAX_WALK[charIndex];
                case 1: return MAX_IDLE[charIndex];
                case 2: return MAX_ATTACK1[charIndex];
                case 3: return MAX_ATTACK2[charIndex];
                case 4: return MAX_ATTACK3[charIndex];
                case 5: return MAX_JUMP[charIndex];
                case 7: return MAX_HURT[charIndex];
                case 8: return MAX_DEAD[charIndex];
                case 9: return MAX_SHIELD[charIndex];
                default: return MAX_IDLE[charIndex];
            }
        }

        private void handleHumanInput() {
            if (keyBits.get(kUp) && !isJumping) { velocityY = jumpPower; isJumping = true; }
            if (isAttacking()) { }
            else if (queuedAttackType != 0) {
                state = queuedAttackType; animIndex = 0; frameDelay = 0; hasHitThisAttack = false; queuedAttackType = 0;
            } else {
                if (isJumping) state = 5;
                else if (keyBits.get(kDown)) state = 9;
                else if (keyBits.get(kLeft) || keyBits.get(kRight)) state = 0;
                else state = 1;
            }
            if (keyBits.get(kLeft)) { x -= 0.5f; if (x < 0) x = 0; facingLeft = true;}
            if (keyBits.get(kRight)) { x += 0.5f; if (x > 50) x = 50; facingLeft = false;}
        }

        private void updateAI(Player target) {
            if (target == null || target.state == 8) { state = 1; return; }

            float attackRange;
            float moveSpeed;
            float aggression;
            float jumpChance;
            float shieldChance;

            switch (currentDifficulty) {
                case EASY:
                    attackRange = 6.0f; moveSpeed = 0.45f; aggression = 0.25f;
                    jumpChance = 0.05f; shieldChance = 0.05f;
                    break;
                case HARD:
                    attackRange = 7.0f; moveSpeed = 0.60f; aggression = 0.80f;
                    jumpChance = 0.15f; shieldChance = 0.20f;
                    break;
                default:
                    attackRange = 6.5f; moveSpeed = 0.50f; aggression = 0.50f;
                    jumpChance = 0.10f; shieldChance = 0.15f;
                    break;
            }

            if (isAttacking()) return;

            float dx = target.x - this.x;
            float dist = Math.abs(dx);
            facingLeft = (dx < 0);

            float r = rand.nextFloat();
            if (dist < attackRange) {
                if (r < aggression) {
                    int which = 2 + rand.nextInt(3);
                    state = which; animIndex = 0; frameDelay = 0; hasHitThisAttack = false;
                    return;
                }
                if (r < aggression + shieldChance) { state = 9; return; }
            }

            if (dist > attackRange + 0.5f) {
                if (dx > 0) x += moveSpeed; else x -= moveSpeed;
                state = 0;
            } else if (dist < attackRange - 0.5f) {
                if (dx > 0) x -= moveSpeed * 0.6f; else x += moveSpeed * 0.6f;
                state = 0;
            } else {
                state = 1;
            }

            if (!isJumping && rand.nextFloat() < jumpChance) {
                velocityY = jumpPower; isJumping = true; state = 5;
            }
            if (isJumping) state = 5;
        }

        public void checkHit(Player target) {
            if (target == null || target.state == 8) return;

            boolean inAttackFrame = (state == 2 && animIndex >= 1) || (state == 3 && animIndex >= 1) || (state == 4 && animIndex >= 1);
            if (!inAttackFrame || hasHitThisAttack) return;

            float dx = target.x - this.x;
            float dist = Math.abs(dx);
            boolean facingCorrect = (facingLeft && dx < 0) || (!facingLeft && dx > 0);

            if (dist <= hitRange && facingCorrect) {
                hasHitThisAttack = true;
                if (target.state == 9) {
                    target.health -= 5;
                    if (target.health <= 0) {
                        target.health = 0; target.state = 8; target.animIndex = 0; target.frameDelay = 0;
                    } else {
                        target.wasHit = true;
                    }
                    return;
                }
                target.health -= damage;
                if (target.health <= 0) {
                    target.health = 0;
                    target.state = 8; target.animIndex = 0; target.frameDelay = 0;
                } else {
                    target.state = 7; target.animIndex = 0; target.frameDelay = 0; target.wasHit = true;
                }
            }
        }

        public void draw(GL gl) {
            int texID = getCurrentFrame();
            gl.glEnable(GL.GL_BLEND);
            gl.glBindTexture(GL.GL_TEXTURE_2D, texID);
            gl.glPushMatrix();
            gl.glTranslated(x / 25.0 - 1, y / 25.0 - 1, 0);
            gl.glScaled(facingLeft ? -0.3 : 0.3, 0.3, 1);
            gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(0, 0); gl.glVertex3f(-1, -1, 0);
            gl.glTexCoord2f(1, 0); gl.glVertex3f(1, -1, 0);
            gl.glTexCoord2f(1, 1); gl.glVertex3f(1, 1, 0);
            gl.glTexCoord2f(0, 1); gl.glVertex3f(-1, 1, 0);
            gl.glEnd();
            gl.glPopMatrix();
            gl.glDisable(GL.GL_BLEND);
        }

        int getCurrentFrame() {
            int maxFrames = getMaxFramesForState();
            if (animIndex >= maxFrames) {
                if (state == 8 || state == 9) animIndex = maxFrames - 1;
                else animIndex = 0;
            }
            return textureIDs[state][animIndex];
        }
    }
}