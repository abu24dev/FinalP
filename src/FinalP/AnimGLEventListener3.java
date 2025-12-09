package FinalP;

import FinalP.Texture.TextureReader;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import java.awt.event.*;
import java.util.BitSet;
import java.util.Random;

public class AnimGLEventListener3 extends AnimListener implements KeyListener {

    // =========================================
    // Single-player: Human vs AI Enemy
    // =========================================
    Player player1;
    Player enemy;

    // keyboard state
    static BitSet keyBits = new BitSet(256);

    // Difficulty
    enum Difficulty {EASY, MEDIUM, HARD}
    Difficulty currentDifficulty = Difficulty.MEDIUM; // default

    Random globalRand = new Random();

    // --- متغيرات جديدة لحفظ اختيارات اللاعبين ---
    int myCharIndex = 0;    // الافتراضي
    int enemyCharIndex = 1; // الافتراضي

    // --- دالة لاستقبال الاختيارات من القائمة ---
    public void setCharacters(int p1, int p2) {
        this.myCharIndex = p1;
        this.enemyCharIndex = p2;
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

    // Frame counts
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

        super.initUI(gl); // UI bar

        super.initTimer(gl);

        // load 3 characters
        shinobiIDs = loadCharacter(gl, shinobiTextures, 0);
        fighterIDs = loadCharacter(gl, fighterTextures, 1);
        samuraiIDs = loadCharacter(gl, samuraiTextures, 2);

        // --- تعديل: استخدام myCharIndex بدلاً من التثبيت ---
        int[][] p1Textures = getTextureByIndex(myCharIndex);
        player1 = new Player(15, 20, p1Textures, myCharIndex, false, false);

        player1.setControls(
                KeyEvent.VK_W,  // jump
                KeyEvent.VK_S,  // shield
                KeyEvent.VK_A,  // left
                KeyEvent.VK_D,  // right
                KeyEvent.VK_Z,  // attack1
                KeyEvent.VK_X,  // attack2
                KeyEvent.VK_C   // attack3
        );

        // --- تعديل: استخدام enemyCharIndex بدلاً من الـ Random ---
        int[][] enemyTextures = getTextureByIndex(enemyCharIndex);
        enemy = new Player(40, 20, enemyTextures, enemyCharIndex, true, true);

        System.out.println("Single Player Initialized: Me=" + myCharIndex + " vs AI=" + enemyCharIndex);
        System.out.println("Default Difficulty: MEDIUM");
    }

    // دالة مساعدة لاختيار المصفوفة
    private int[][] getTextureByIndex(int index) {
        if (index == 0) return shinobiIDs;
        if (index == 1) return fighterIDs;
        return samuraiIDs;
    }

    // Load all state frames + background
    int[][] loadCharacter(GL gl, String[] texFiles, int charIdx) {
        int[][] ids = new int[10][15]; // [state][frame]
        int offset = 0;

        for (int i = 0; i < MAX_WALK[charIdx]; i++) ids[0][i] = genTex(gl, texFiles[offset++]); // WALK
        for (int i = 0; i < MAX_IDLE[charIdx]; i++) ids[1][i] = genTex(gl, texFiles[offset++]); // IDLE
        for (int i = 0; i < MAX_ATTACK1[charIdx]; i++) ids[2][i] = genTex(gl, texFiles[offset++]); // ATT1
        for (int i = 0; i < MAX_ATTACK2[charIdx]; i++) ids[3][i] = genTex(gl, texFiles[offset++]); // ATT2
        for (int i = 0; i < MAX_ATTACK3[charIdx]; i++) ids[4][i] = genTex(gl, texFiles[offset++]); // ATT3
        for (int i = 0; i < MAX_JUMP[charIdx]; i++) ids[5][i] = genTex(gl, texFiles[offset++]); // JUMP
        for (int i = 0; i < MAX_RUN[charIdx]; i++) ids[6][i] = genTex(gl, texFiles[offset++]); // RUN
        for (int i = 0; i < MAX_HURT[charIdx]; i++) ids[7][i] = genTex(gl, texFiles[offset++]); // HURT
        for (int i = 0; i < MAX_DEAD[charIdx]; i++) ids[8][i] = genTex(gl, texFiles[offset++]); // DEAD
        for (int i = 0; i < MAX_SHIELD[charIdx]; i++) ids[9][i] = genTex(gl, texFiles[offset++]); // SHIELD

        bgIDs[charIdx] = genTex(gl, texFiles[offset]); // background
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

        if (!isPaused && !isTimeOver) {
            player1.update(enemy);   // human, target = enemy
            enemy.update(player1);   // AI, target = player1
        }

        if (isTimeOver && !isGameOver) {
            isGameOver = true;
            if (player1.health > enemy.health) setGameOverMessage("YOU WIN!");
            else if (enemy.health > player1.health) setGameOverMessage("YOU LOSE!");
            else setGameOverMessage("DRAW! (TIME OUT)");
        }

        if (!isGameOver) {
            if (player1.state == 8) { // Player died
                isGameOver = true;
                setGameOverMessage("YOU LOSE!");
            } else if (enemy.state == 8) { // Enemy died
                isGameOver = true;
                setGameOverMessage("YOU WIN!");
            }
        }

        player1.draw(gl);
        enemy.draw(gl);

        super.drawUI(gl, drawable.getWidth(), drawable.getHeight());
        // ==========================================
        // 3. رسم شريط الصحة (Health Bars)
        // ==========================================
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        new GLU().gluOrtho2D(0, drawable.getWidth(), 0, drawable.getHeight());
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glLoadIdentity();

        // اللاعب (Human) - يسار
        drawHealthBar(gl, player1.health, 20, drawable.getHeight() - 60);

        // العدو (AI) - يمين
        drawHealthBar(gl, enemy.health, drawable.getWidth() - 220, drawable.getHeight() - 60);

        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glPopMatrix();
        super.drawTimer(drawable, drawable.getWidth(), drawable.getHeight());
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
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-1, 1, -1, 1, -1, 1);
        gl.glMatrixMode(GL.GL_MODELVIEW);
    }

    @Override
    public void resetGame() {
        setGameOverMessage("");
        isGameOver = false;
        isTimeOver = false;
        timeRemaining = 120.0f;
        // إعادة تعيين اللاعب بالشخصية المختارة
        player1.reset(15, 20, false);

        // إعادة تعيين العدو بالشخصية المختارة (بدون تغيير عشوائي)
        enemy.textureIDs = getTextureByIndex(enemyCharIndex);
        enemy.charIndex = enemyCharIndex;
        enemy.reset(40, 20, true);

        keyBits.clear();
        System.out.println("Game Reset. Same Characters.");

    }

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    // ============================
    //    Key Handling
    // ============================
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        keyBits.set(code, true);

        // one-press attacks for player1
        if (code == player1.kAtt1) player1.queueAttack(2); // ATTACK1
        if (code == player1.kAtt2) player1.queueAttack(3); // ATTACK2
        if (code == player1.kAtt3) player1.queueAttack(4); // ATTACK3

        // difficulty keys: 1,2,3
        if (code == KeyEvent.VK_1) {
            currentDifficulty = Difficulty.EASY;
            System.out.println("Difficulty: EASY");
        } else if (code == KeyEvent.VK_2) {
            currentDifficulty = Difficulty.MEDIUM;
            System.out.println("Difficulty: MEDIUM");
        } else if (code == KeyEvent.VK_3) {
            currentDifficulty = Difficulty.HARD;
            System.out.println("Difficulty: HARD");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyBits.set(e.getKeyCode(), false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    // =========================================================
    //  Inner Player class (works for Human and AI Enemy)
    // =========================================================
    class Player {
        float x, y;
        boolean facingLeft;
        int charIndex;
        int[][] textureIDs;
        int health = 100;
        int animIndex = 0;
        int frameDelay = 0;

        // states: 0 walk,1 idle,2 att1,3 att2,4 att3,5 jump,7 hurt,8 dead,9 shield
        int state = 1;
        int lastState = 1;

        // Physics
        float velocityY = 0;
        float gravity = 0.1f;
        float jumpPower = 1.3f;
        float groundY = 20;
        boolean isJumping = false;

        // controls
        int kUp, kDown, kLeft, kRight, kAtt1, kAtt2, kAtt3;

        // Attack queue (one-press)
        int queuedAttackType = 0; // 0 = none, 2/3/4 = attack states
        boolean hasHitThisAttack = false;

        // Combat
        float hitRange = 6.5f; // bigger hit box
        boolean wasHit = false;
        int damage = 20;

        // AI
        boolean isAI;
        Random rand = new Random();
        int aiDecisionTimer = 0;

        public Player(float startX, float startY, int[][] ids, int cIndex, boolean startFaceLeft, boolean ai) {
            this.x = startX;
            this.y = startY;
            this.groundY = startY;
            this.textureIDs = ids;
            this.charIndex = cIndex;
            this.facingLeft = startFaceLeft;
            this.isAI = ai;
        }

        public void setControls(int up, int down, int left, int right, int a1, int a2, int a3) {
            this.kUp = up;
            this.kDown = down;
            this.kLeft = left;
            this.kRight = right;
            this.kAtt1 = a1;
            this.kAtt2 = a2;
            this.kAtt3 = a3;
        }

        public void queueAttack(int attackState) {
            // attackState: 2 or 3 or 4
            if (isAttacking() || state == 7 || state == 8) return;
            if (queuedAttackType == 0) {
                queuedAttackType = attackState;
            }
        }

        boolean isAttacking() {
            return state == 2 || state == 3 || state == 4;
        }

        public void reset(float startX, float startY, boolean startFaceLeft) {
            this.x = startX;
            this.y = startY;
            this.groundY = startY;
            this.facingLeft = startFaceLeft;
            this.health = 100;
            this.state = 1;
            this.lastState = 1;
            this.animIndex = 0;
            this.frameDelay = 0;
            this.velocityY = 0;
            this.isJumping = false;
            this.queuedAttackType = 0;
            this.hasHitThisAttack = false;
            this.wasHit = false;
            this.aiDecisionTimer = 0;
        }

        // target = opponent
        public void update(Player target) {
            if (state == 7) {
                frameDelay++;
                if (frameDelay % 3 == 0) animIndex++;
                if (animIndex >= MAX_HURT[charIndex]) {
                    state = 1;
                    animIndex = 0;
                    frameDelay = 0;
                }
                return;
            }
            y += velocityY;
            velocityY -= gravity;

            if (y <= groundY) {
                y = groundY;
                velocityY = 0;
                isJumping = false;
                if (state == 8) {
                    frameDelay++;
                    if (frameDelay % 3 == 0) animIndex++;
                    if (animIndex >= MAX_DEAD[charIndex]) animIndex = MAX_DEAD[charIndex] - 1;
                    return;
                }
            } else {
                isJumping = true;
            }
            if (state == 8) return;

            lastState = state;

            if (isAI) updateAI(target);
            else handleHumanInput();

            if (x < 0) x = 0;
            if (x > 50) x = 50;

            if (target != null && target.state != 8) checkHit(target);
            if (state != lastState) {
                animIndex = 0;
                frameDelay = 0;
            }
            frameDelay++;
            if (frameDelay % 3 == 0) {
                animIndex++;
                int maxFrames = 1;
                switch (state) {
                    case 0:
                        maxFrames = MAX_WALK[charIndex];
                        break;
                    case 1:
                        maxFrames = MAX_IDLE[charIndex];
                        break;
                    case 2:
                        maxFrames = MAX_ATTACK1[charIndex];
                        break;
                    case 3:
                        maxFrames = MAX_ATTACK2[charIndex];
                        break;
                    case 4:
                        maxFrames = MAX_ATTACK3[charIndex];
                        break;
                    case 5:
                        maxFrames = MAX_JUMP[charIndex];
                        break;
                    case 7:
                        maxFrames = MAX_HURT[charIndex];
                        break;
                    case 8:
                        maxFrames = MAX_DEAD[charIndex];
                        break;
                    case 9:
                        maxFrames = MAX_SHIELD[charIndex];
                        break;
                    default:
                        maxFrames = MAX_IDLE[charIndex];
                        break;
                }

                if (isAttacking() && animIndex >= maxFrames) {
                    state = 1;
                    animIndex = 0;
                    frameDelay = 0;
                    hasHitThisAttack = false;
                } else if ((state == 8 || state == 9) && animIndex >= maxFrames) {
                    animIndex = maxFrames - 1;
                } else if (animIndex >= maxFrames) {
                    animIndex = 0;
                }
            }

        }

        // -------------------- HUMAN INPUT --------------------
        private void handleHumanInput() {
            // jump triggered by keyBits
            if (keyBits.get(kUp) && !isJumping) {
                velocityY = jumpPower;
                isJumping = true;
            }

            if (isAttacking()) {
                // continue current attack until animation finishes
            } else if (queuedAttackType != 0) {
                // start queued attack once
                state = queuedAttackType;
                animIndex = 0;
                frameDelay = 0;
                hasHitThisAttack = false;
                queuedAttackType = 0;
            } else {
                // non-attack decisions
                if (isJumping) {
                    state = 5; // jump
                } else if (keyBits.get(kDown)) {
                    state = 9; // shield
                } else if (keyBits.get(kLeft) || keyBits.get(kRight)) {
                    state = 0; // walk
                } else {
                    state = 1; // idle
                }
            }

            // Movement (same base speed as before)
            if (keyBits.get(kLeft)) {
                if (x > 0) x -= 0.5f;
                facingLeft = true;
            }
            if (keyBits.get(kRight)) {
                if (x < 50) x += 0.5f;
                facingLeft = false;
            }
        }

        // -------------------- AI BRAIN --------------------
        private void updateAI(Player target) {
            if (target == null || target.state == 8) {
                state = 1;
                return;
            }

            // PARAMETERS (Balanced)
            float attackRange;
            float moveSpeed;
            float aggression;
            float jumpChance;
            float shieldChance;

            switch (currentDifficulty) {
                case EASY:
                    attackRange = 6.0f;
                    moveSpeed = 0.45f;
                    aggression = 0.25f;
                    jumpChance = 0.05f;
                    shieldChance = 0.05f;
                    break;

                case HARD:
                    attackRange = 7.0f;
                    moveSpeed = 0.60f;  // FASTER than human
                    aggression = 0.80f;
                    jumpChance = 0.15f;
                    shieldChance = 0.20f;
                    break;

                default: // MEDIUM
                    attackRange = 6.5f;
                    moveSpeed = 0.50f;  // SAME speed as human
                    aggression = 0.50f;
                    jumpChance = 0.10f;
                    shieldChance = 0.15f;
                    break;
            }

            // If attacking → let animation continue
            if (isAttacking()) return;

            // Distance analysis
            float dx = target.x - this.x;
            float dist = Math.abs(dx);
            facingLeft = (dx < 0);

            // ALWAYS MOVE — NO DECISION DELAY ANYMORE
            float r = rand.nextFloat();

            // If close → attack or shield
            if (dist < attackRange) {
                if (r < aggression) {
                    int which = 2 + rand.nextInt(3); // attack 2,3,4
                    state = which;
                    animIndex = 0;
                    frameDelay = 0;
                    hasHitThisAttack = false;
                    return;
                }

                if (r < aggression + shieldChance) {
                    state = 9; // shield
                    return;
                }
            }

            // Movement logic — ALWAYS executes, no delay!
            if (dist > attackRange + 0.5f) {
                if (dx > 0) x += moveSpeed;
                else x -= moveSpeed;
                state = 0; // walk
            } else if (dist < attackRange - 0.5f) {
                if (dx > 0) x -= moveSpeed * 0.6f;
                else x += moveSpeed * 0.6f;
                state = 0; // walk back
            } else {
                state = 1; // idle close to player
            }

            // Random jump
            if (!isJumping && rand.nextFloat() < jumpChance) {
                velocityY = jumpPower;
                isJumping = true;
                state = 5;
            }

            if (isJumping) state = 5;
        }


        // -------------------- COLLISION & DAMAGE --------------------
        public void checkHit(Player target) {
            if (target == null || target.state == 8) return; // لو ميت أصلاً ميعملش حاجة

            // التأكد إن الضربة في الفريم الصح
            boolean inAttackFrame =
                    (state == 2 && animIndex >= 1) ||
                            (state == 3 && animIndex >= 1) ||
                            (state == 4 && animIndex >= 1);

            if (!inAttackFrame || hasHitThisAttack) return;

            float dx = target.x - this.x;
            float dist = Math.abs(dx);
            boolean facingCorrect = (facingLeft && dx < 0) || (!facingLeft && dx > 0);

            if (dist <= hitRange && facingCorrect) {
                hasHitThisAttack = true;

                // --- التعامل مع الـ Shield ---
                if (target.state == 9) {
                    target.health -= 5; // ضرر مخفف
                    if (target.health < 0) target.health = 0;
                    target.wasHit = true;

                    if (target == enemy) System.out.println("Enemy Blocked! HP: " + target.health);

                    // +++ التعديل الجديد هنا +++
                    // لازم نشيك لو مات وهو عامل Shield
                    if (target.health <= 0) {
                        target.state = 8; // تحويل لحالة الموت
                        target.animIndex = 0;
                        target.frameDelay = 0;
                        if (target == enemy) System.out.println(">>> Enemy Died while shielding!");
                        else System.out.println(">>> Player Died while shielding!");
                    }
                    // ++++++++++++++++++++++++++

                    return; // نخرج عشان مننقصش الـ 20 بتوع الضربة العادية
                }

                // --- التعامل مع الضربة العادية (بدون Shield) ---
                target.health -= damage;
                if (target.health < 0) target.health = 0;
                target.wasHit = true;

                if (target == enemy) System.out.println("Enemy Hit! HP: " + target.health);

                // الموت العادي
                if (target.health <= 0) {
                    target.state = 8;
                    target.animIndex = 0;
                    target.frameDelay = 0;
                    if (target == enemy) System.out.println(">>> Enemy DIED!");
                    else System.out.println(">>> Player DIED!");
                } else {
                    // لو لسه عايش يتوجع (Hurt)
                    target.state = 7;
                    target.animIndex = 0;
                    target.frameDelay = 0;
                }
            }
        }

        // -------------------- DRAW --------------------
        public void draw(GL gl) {
            int texID = getCurrentFrame();
            gl.glEnable(GL.GL_BLEND);
            gl.glBindTexture(GL.GL_TEXTURE_2D, texID);
            gl.glPushMatrix();

            // SAME transform / scale as multiplayer
            gl.glTranslated(x / 25.0 - 1, y / 25.0 - 1, 0);
            gl.glScaled(facingLeft ? -0.3 : 0.3, 0.3, 1);

            gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(0, 0);
            gl.glVertex3f(-1, -1, 0);
            gl.glTexCoord2f(1, 0);
            gl.glVertex3f(1, -1, 0);
            gl.glTexCoord2f(1, 1);
            gl.glVertex3f(1, 1, 0);
            gl.glTexCoord2f(0, 1);
            gl.glVertex3f(-1, 1, 0);
            gl.glEnd();

            gl.glPopMatrix();
            gl.glDisable(GL.GL_BLEND);
        }

        // -------------------- ANIMATION FRAMES --------------------
        int getCurrentFrame() {
            int maxFrames = 1;
            switch (state) {
                case 0:
                    maxFrames = MAX_WALK[charIndex];
                    break;
                case 1:
                    maxFrames = MAX_IDLE[charIndex];
                    break;
                case 2:
                    maxFrames = MAX_ATTACK1[charIndex];
                    break;
                case 3:
                    maxFrames = MAX_ATTACK2[charIndex];
                    break;
                case 4:
                    maxFrames = MAX_ATTACK3[charIndex];
                    break;
                case 5:
                    maxFrames = MAX_JUMP[charIndex];
                    break;
                case 7:
                    maxFrames = MAX_HURT[charIndex];
                    break;
                case 8:
                    maxFrames = MAX_DEAD[charIndex];
                    break;
                case 9:
                    maxFrames = MAX_SHIELD[charIndex];
                    break;
                default:
                    maxFrames = MAX_IDLE[charIndex];
                    break;
            }

            if (animIndex >= maxFrames) {
                if (state == 8 || state == 9) animIndex = maxFrames - 1;
                else animIndex = 0;
            }
            return textureIDs[state][animIndex];
        }
    }
}