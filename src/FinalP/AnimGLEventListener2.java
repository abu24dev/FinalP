package FinalP;

import FinalP.Texture.TextureReader;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import java.awt.event.*;
import java.util.BitSet;

public class AnimGLEventListener2 extends AnimListener implements KeyListener {

    Player player1;
    Player player2;

    static BitSet keyBits = new BitSet(256);

    // --- 1. متغيرات لحفظ اختيارات اللاعبين ---
    // (قيم افتراضية)
    int p1Type = 0;
    int p2Type = 2;

    // --- 2. دالة لاستقبال الاختيارات من Anim ---
    public void setPlayerChoices(int p1, int p2) {
        this.p1Type = p1;
        this.p2Type = p2;
    }

    // --- Texture Arrays ---
    String[] shinobiTextures = {
            "Assets/Shinobi/Walk1.png","Assets/Shinobi/Walk2.png","Assets/Shinobi/Walk3.png","Assets/Shinobi/Walk4.png",
            "Assets/Shinobi/Walk5.png","Assets/Shinobi/Walk6.png","Assets/Shinobi/Walk7.png","Assets/Shinobi/Walk8.png",
            "Assets/Shinobi/Idle1.png","Assets/Shinobi/Idle2.png","Assets/Shinobi/Idle3.png","Assets/Shinobi/Idle4.png",
            "Assets/Shinobi/Idle5.png","Assets/Shinobi/Idle6.png",
            "Assets/Shinobi/IAttack1.png","Assets/Shinobi/IAttack2.png","Assets/Shinobi/IAttack3.png",
            "Assets/Shinobi/IAttack4.png","Assets/Shinobi/IAttack5.png",
            "Assets/Shinobi/IIAttack1.png","Assets/Shinobi/IIAttack2.png","Assets/Shinobi/IIAttack3.png",
            "Assets/Shinobi/IIIAttack1.png","Assets/Shinobi/IIIAttack2.png","Assets/Shinobi/IIIAttack3.png",
            "Assets/Shinobi/IIIAttack4.png",
            "Assets/Shinobi/Jump1.png","Assets/Shinobi/Jump2.png","Assets/Shinobi/Jump3.png","Assets/Shinobi/Jump4.png",
            "Assets/Shinobi/Jump5.png","Assets/Shinobi/Jump6.png","Assets/Shinobi/Jump7.png","Assets/Shinobi/Jump8.png",
            "Assets/Shinobi/Jump9.png","Assets/Shinobi/Jump10.png","Assets/Shinobi/Jump11.png",
            "Assets/Shinobi/Run1.png","Assets/Shinobi/Run2.png","Assets/Shinobi/Run3.png","Assets/Shinobi/Run4.png",
            "Assets/Shinobi/Run5.png","Assets/Shinobi/Run6.png","Assets/Shinobi/Run7.png","Assets/Shinobi/Run8.png",
            "Assets/Shinobi/Hurt1.png","Assets/Shinobi/Hurt2.png",
            "Assets/Shinobi/Dead1.png","Assets/Shinobi/Dead2.png","Assets/Shinobi/Dead3.png","Assets/Shinobi/Dead4.png",
            "Assets/Shinobi/Shield1.png","Assets/Shinobi/Shield2.png","Assets/Shinobi/Shield3.png","Assets/Shinobi/Shield4.png",
            "Assets/Shinobi/7.png"
    };

    String[] fighterTextures = {
            "Assets/Fighter/Walk1.png","Assets/Fighter/Walk2.png","Assets/Fighter/Walk3.png","Assets/Fighter/Walk4.png",
            "Assets/Fighter/Walk5.png","Assets/Fighter/Walk6.png","Assets/Fighter/Walk7.png","Assets/Fighter/Walk8.png",
            "Assets/Fighter/Idle1.png","Assets/Fighter/Idle2.png","Assets/Fighter/Idle3.png","Assets/Fighter/Idle4.png",
            "Assets/Fighter/Idle5.png","Assets/Fighter/Idle6.png",
            "Assets/Fighter/IAttack1.png","Assets/Fighter/IAttack2.png","Assets/Fighter/IAttack3.png","Assets/Fighter/IAttack4.png",
            "Assets/Fighter/IIAttack1.png","Assets/Fighter/IIAttack2.png","Assets/Fighter/IIAttack3.png",
            "Assets/Fighter/IIIAttack1.png","Assets/Fighter/IIIAttack2.png","Assets/Fighter/IIIAttack3.png",
            "Assets/Fighter/IIIAttack4.png",
            "Assets/Fighter/Jump1.png","Assets/Fighter/Jump2.png","Assets/Fighter/Jump3.png","Assets/Fighter/Jump4.png",
            "Assets/Fighter/Jump5.png","Assets/Fighter/Jump6.png","Assets/Fighter/Jump7.png","Assets/Fighter/Jump8.png",
            "Assets/Fighter/Jump9.png","Assets/Fighter/Jump10.png",
            "Assets/Fighter/Run1.png","Assets/Fighter/Run2.png","Assets/Fighter/Run3.png","Assets/Fighter/Run4.png",
            "Assets/Fighter/Run5.png","Assets/Fighter/Run6.png","Assets/Fighter/Run7.png","Assets/Fighter/Run8.png",
            "Assets/Fighter/Hurt1.png","Assets/Fighter/Hurt2.png","Assets/Fighter/Hurt3.png",
            "Assets/Fighter/Dead1.png","Assets/Fighter/Dead2.png","Assets/Fighter/Dead3.png",
            "Assets/Fighter/Shield1.png","Assets/Fighter/Shield2.png",
            "Assets/Fighter/7.png"
    };

    String[] samuraiTextures = {
            "Assets/Samurai/Walk1.png","Assets/Samurai/Walk2.png","Assets/Samurai/Walk3.png","Assets/Samurai/Walk4.png",
            "Assets/Samurai/Walk5.png","Assets/Samurai/Walk6.png","Assets/Samurai/Walk7.png","Assets/Samurai/Walk8.png",
            "Assets/Samurai/Idle1.png","Assets/Samurai/Idle2.png","Assets/Samurai/Idle3.png","Assets/Samurai/Idle4.png",
            "Assets/Samurai/Idle5.png","Assets/Samurai/Idle6.png",
            "Assets/Samurai/IAttack1.png","Assets/Samurai/IAttack2.png","Assets/Samurai/IAttack3.png",
            "Assets/Samurai/IAttack4.png","Assets/Samurai/IAttack5.png","Assets/Samurai/IAttack6.png",
            "Assets/Samurai/IIAttack1.png","Assets/Samurai/IIAttack2.png","Assets/Samurai/IIAttack3.png",
            "Assets/Samurai/IIAttack4.png",
            "Assets/Samurai/IIIAttack1.png","Assets/Samurai/IIIAttack2.png","Assets/Samurai/IIIAttack3.png",
            "Assets/Samurai/Jump1.png","Assets/Samurai/Jump2.png","Assets/Samurai/Jump3.png","Assets/Samurai/Jump4.png",
            "Assets/Samurai/Jump5.png","Assets/Samurai/Jump6.png","Assets/Samurai/Jump7.png","Assets/Samurai/Jump8.png",
            "Assets/Samurai/Jump9.png","Assets/Samurai/Jump10.png","Assets/Samurai/Jump11.png","Assets/Samurai/Jump12.png",
            "Assets/Samurai/Run1.png","Assets/Samurai/Run2.png","Assets/Samurai/Run3.png","Assets/Samurai/Run4.png",
            "Assets/Samurai/Run5.png","Assets/Samurai/Run6.png","Assets/Samurai/Run7.png","Assets/Samurai/Run8.png",
            "Assets/Samurai/Hurt1.png","Assets/Samurai/Hurt2.png",
            "Assets/Samurai/Dead1.png","Assets/Samurai/Dead2.png","Assets/Samurai/Dead3.png",
            "Assets/Samurai/Shield1.png","Assets/Samurai/Shield2.png",
            "Assets/Samurai/7.png"
    };

    int[][] shinobiIDs;
    int[][] fighterIDs;
    int[][] samuraiIDs;
    int[] bgIDs = new int[3];

    // Frame counts - same as version 3
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


    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClearColor(1,1,1,1);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        super.initUI(gl);

        // تحميل كل الشخصيات
        shinobiIDs = loadCharacter(gl, shinobiTextures, 0);
        fighterIDs = loadCharacter(gl, fighterTextures, 1);
        samuraiIDs = loadCharacter(gl, samuraiTextures, 2);

        // --- 3. إنشاء اللاعب الأول بناءً على الاختيار ---
        int[][] p1Tex = getTextureByIndex(p1Type);
        player1 = new Player(15, 20, p1Tex, p1Type, false);

        player1.setControls(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D,
                KeyEvent.VK_Z, KeyEvent.VK_X, KeyEvent.VK_C);

        // --- 4. إنشاء اللاعب الثاني بناءً على الاختيار ---
        int[][] p2Tex = getTextureByIndex(p2Type);
        player2 = new Player(35, 20, p2Tex, p2Type, true);

        player2.setControls(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT,
                KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L);

        System.out.println("Multiplayer Started: P1 type=" + p1Type + ", P2 type=" + p2Type);
    }
    private int[][] getTextureByIndex(int index) {
        if(index == 0) return shinobiIDs;
        if(index == 1) return fighterIDs;
        if(index == 2) return samuraiIDs;
        return shinobiIDs;
    }

    // Load textures for a character
    int[][] loadCharacter(GL gl, String[] texFiles, int charIdx) {
        int[][] ids = new int[10][15];
        int offset = 0;

        // Load textures for each animation state
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

        // Background texture
        bgIDs[charIdx] = genTex(gl, texFiles[offset]);
        return ids;
    }

    // Generate OpenGL texture from image file
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
            System.out.println("Error loading texture: " + path);
            return -1;
        }
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();

        // Draw background
        DrawBackground(gl, bgIDs[0]);

        // Update players if game not paused
        if (!isPaused) {
            player1.update(player2);
            player2.update(player1);
        }

        // Draw players
        player1.draw(gl);
        player2.draw(gl);

        // Draw UI (health bars, etc.)
        super.drawUI(gl, drawable.getWidth(), drawable.getHeight());
    }

    // Draw background image
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
        // إعادة ضبط اللاعبين بنفس الشخصيات المختارة
        player1.textureIDs = getTextureByIndex(p1Type);
        player1.charIndex = p1Type;
        // القيم دي بترجع اللاعب لمكانه الأصلي وحالته الطبيعية
        player1.x = 15;
        player1.y = 20;
        player1.state = 1;
        player1.health = 100;
        player1.facingLeft = false;

        player2.textureIDs = getTextureByIndex(p2Type);
        player2.charIndex = p2Type;
        player2.x = 35;
        player2.y = 20;
        player2.state = 1;
        player2.health = 100;
        player2.facingLeft = true;

        keyBits.clear();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyBits.set(e.getKeyCode(), true);

        // Player 1 attacks (one-press system from version 3)
        if (e.getKeyCode() == player1.kAtt1) player1.queueAttack(2);
        if (e.getKeyCode() == player1.kAtt2) player1.queueAttack(3);
        if (e.getKeyCode() == player1.kAtt3) player1.queueAttack(4);

        // Player 2 attacks (one-press system from version 3)
        if (e.getKeyCode() == player2.kAtt1) player2.queueAttack(2);
        if (e.getKeyCode() == player2.kAtt2) player2.queueAttack(3);
        if (e.getKeyCode() == player2.kAtt3) player2.queueAttack(4);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyBits.set(e.getKeyCode(), false);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

    // =========================================================
    //  Inner Player Class with physics from version 3
    // =========================================================
    class Player {
        float x, y;
        boolean facingLeft;
        int charIndex;
        int[][] textureIDs;
        int health = 100;
        int animIndex = 0;
        int frameDelay = 0;

        // Animation states: 0 walk, 1 idle, 2 att1, 3 att2, 4 att3, 5 jump, 7 hurt, 8 dead, 9 shield
        int state = 1; // Start with idle
        int lastState = 1;

        // Physics variables (from version 3)
        float velocityY = 0;
        float gravity = 0.1f;
        float jumpPower = 1.3f;
        float groundY = 20;
        boolean isJumping = false;

        // Control keys
        int kUp, kDown, kLeft, kRight, kAtt1, kAtt2, kAtt3;

        // Attack system (from version 3)
        int queuedAttackType = 0; // 0 = no attack, 2/3/4 = attack type
        boolean hasHitThisAttack = false;

        // Combat variables (from version 3)
        float hitRange = 6.5f;
        boolean wasHit = false;
        int damage = 20;

        public Player(float startX, float startY, int[][] ids, int cIndex, boolean startFaceLeft) {
            this.x = startX;
            this.y = startY;
            this.groundY = startY;
            this.textureIDs = ids;
            this.charIndex = cIndex;
            this.facingLeft = startFaceLeft;
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

        // Queue attack for one-press system
        public void queueAttack(int attackState) {
            if (isAttacking() || state == 7 || state == 8) return;
            if (queuedAttackType == 0) {
                queuedAttackType = attackState;
            }
        }

        // Check if player is currently attacking
        boolean isAttacking() {
            return state == 2 || state == 3 || state == 4;
        }

        // Reset player to initial state
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
        }

        // Update player state (called every frame)
        public void update(Player target) {
            // Handle hurt state
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

            // Apply gravity and vertical movement
            y += velocityY;
            velocityY -= gravity;

            // Ground collision
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

            if (state == 8) return; // If dead, don't process further

            lastState = state;

            // Handle player input
            handleInput();

            // Screen boundaries
            if (x < 0) x = 0;
            if (x > 50) x = 50;

            // Check collision with target
            if (target != null && target.state != 8) {
                checkHit(target);
            }

            // Auto-facing logic (from version 3)
            if (target != null) {
                if (this.x < target.x) {
                    this.facingLeft = false; // Face right when target is to the right
                } else {
                    this.facingLeft = true;  // Face left when target is to the left
                }
            }

            // Reset animation if state changed
            if (state != lastState) {
                animIndex = 0;
                frameDelay = 0;
            }

            // Update animation frame
            frameDelay++;
            if (frameDelay % 3 == 0) {
                animIndex++;
                int maxFrames = getMaxFramesForState();

                // Handle animation completion
                if (isAttacking() && animIndex >= maxFrames) {
                    state = 1; // Return to idle after attack
                    animIndex = 0;
                    frameDelay = 0;
                    hasHitThisAttack = false;
                } else if ((state == 8 || state == 9) && animIndex >= maxFrames) {
                    animIndex = maxFrames - 1; // Hold last frame for dead/shield
                } else if (animIndex >= maxFrames) {
                    animIndex = 0; // Loop animation
                }
            }
        }

        // Get maximum frames for current state
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

        // Handle keyboard input
        private void handleInput() {
            // Jump if up key pressed and on ground
            if (keyBits.get(kUp) && !isJumping) {
                velocityY = jumpPower;
                isJumping = true;
            }

            // Attack system (from version 3)
            if (isAttacking()) {
                // Continue current attack animation
            } else if (queuedAttackType != 0) {
                // Start queued attack
                state = queuedAttackType;
                animIndex = 0;
                frameDelay = 0;
                hasHitThisAttack = false;
                queuedAttackType = 0;
            } else {
                // Non-attack states
                if (isJumping) {
                    state = 5; // Jump
                } else if (keyBits.get(kDown)) {
                    state = 9; // Shield
                } else if (keyBits.get(kLeft) || keyBits.get(kRight)) {
                    state = 0; // Walk
                } else {
                    state = 1; // Idle
                }
            }

            // Horizontal movement
            if (keyBits.get(kLeft)) {
                x -= 0.5f;
                if (x < 0) x = 0;
            }
            if (keyBits.get(kRight)) {
                x += 0.5f;
                if (x > 50) x = 50;
            }
        }

        // Check if attack hits target
        public void checkHit(Player target) {
            if (target == null || target.state == 8) return;

            // Only check during active attack frames
            boolean inAttackFrame =
                    (state == 2 && animIndex >= 1) ||
                            (state == 3 && animIndex >= 1) ||
                            (state == 4 && animIndex >= 1);

            if (!inAttackFrame || hasHitThisAttack) return;

            float dx = target.x - this.x;
            float dist = Math.abs(dx);

            // Check if facing the target
            boolean facingCorrect =
                    (facingLeft && dx < 0) ||
                            (!facingLeft && dx > 0);

            // Check if within hit range and facing correctly
            if (dist <= hitRange && facingCorrect) {
                hasHitThisAttack = true;

                // Check if target is shielding
                if (target.state == 9) {
                    // Shield reduces damage
                    target.health -= 5;
                    if (target.health < 0) target.health = 0;
                    target.wasHit = true;
                    return;
                }

                // Apply full damage
                target.health -= damage;
                if (target.health < 0) target.health = 0;
                target.wasHit = true;

                // Check for death
                if (target.health <= 0) {
                    target.health = 0;
                    target.state = 8; // Dead
                    target.animIndex = 0;
                    target.frameDelay = 0;
                    return;
                }

                // Set hurt state
                target.state = 7; // Hurt
                target.animIndex = 0;
                target.frameDelay = 0;
            }
        }

        // Draw player on screen
        public void draw(GL gl) {
            int texID = getCurrentFrame();
            gl.glEnable(GL.GL_BLEND);
            gl.glBindTexture(GL.GL_TEXTURE_2D, texID);
            gl.glPushMatrix();

            // Position and scale player
            gl.glTranslated(x / 25.0 - 1, y / 25.0 - 1, 0);
            gl.glScaled(facingLeft ? -0.3 : 0.3, 0.3, 1);

            // Draw quad with texture
            gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(0, 0); gl.glVertex3f(-1, -1, 0);
            gl.glTexCoord2f(1, 0); gl.glVertex3f(1, -1, 0);
            gl.glTexCoord2f(1, 1); gl.glVertex3f(1, 1, 0);
            gl.glTexCoord2f(0, 1); gl.glVertex3f(-1, 1, 0);
            gl.glEnd();

            gl.glPopMatrix();
            gl.glDisable(GL.GL_BLEND);
        }

        // Get current texture ID based on state and animation frame
        int getCurrentFrame() {
            int maxFrames = getMaxFramesForState();

            // Handle animation looping
            if (animIndex >= maxFrames) {
                if (state == 8 || state == 9) {
                    animIndex = maxFrames - 1; // Hold last frame
                } else {
                    animIndex = 0; // Loop animation
                }
            }

            return textureIDs[state][animIndex];
        }
    }
}