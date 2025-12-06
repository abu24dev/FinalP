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

    // --- Texture Arrays ---
    String[] shinobiTextures = {
            "Assets/Shinobi/Walk1.png","Assets/Shinobi/Walk2.png","Assets/Shinobi/Walk3.png","Assets/Shinobi/Walk4.png","Assets/Shinobi/Walk5.png","Assets/Shinobi/Walk6.png","Assets/Shinobi/Walk7.png","Assets/Shinobi/Walk8.png",
            "Assets/Shinobi/Idle1.png","Assets/Shinobi/Idle2.png","Assets/Shinobi/Idle3.png","Assets/Shinobi/Idle4.png","Assets/Shinobi/Idle5.png","Assets/Shinobi/Idle6.png",
            "Assets/Shinobi/IAttack1.png","Assets/Shinobi/IAttack2.png","Assets/Shinobi/IAttack3.png","Assets/Shinobi/IAttack4.png","Assets/Shinobi/IAttack5.png",
            "Assets/Shinobi/IIAttack1.png","Assets/Shinobi/IIAttack2.png","Assets/Shinobi/IIAttack3.png",
            "Assets/Shinobi/IIIAttack1.png","Assets/Shinobi/IIIAttack2.png","Assets/Shinobi/IIIAttack3.png","Assets/Shinobi/IIIAttack4.png",
            "Assets/Shinobi/Jump1.png","Assets/Shinobi/Jump2.png","Assets/Shinobi/Jump3.png","Assets/Shinobi/Jump4.png","Assets/Shinobi/Jump5.png","Assets/Shinobi/Jump6.png","Assets/Shinobi/Jump7.png","Assets/Shinobi/Jump8.png","Assets/Shinobi/Jump9.png","Assets/Shinobi/Jump10.png","Assets/Shinobi/Jump11.png",
            "Assets/Shinobi/Run1.png","Assets/Shinobi/Run2.png","Assets/Shinobi/Run3.png","Assets/Shinobi/Run4.png","Assets/Shinobi/Run5.png","Assets/Shinobi/Run6.png","Assets/Shinobi/Run7.png","Assets/Shinobi/Run8.png",
            "Assets/Shinobi/Hurt1.png","Assets/Shinobi/Hurt2.png",
            "Assets/Shinobi/Dead1.png","Assets/Shinobi/Dead2.png","Assets/Shinobi/Dead3.png","Assets/Shinobi/Dead4.png",
            "Assets/Shinobi/Shield1.png","Assets/Shinobi/Shield2.png","Assets/Shinobi/Shield3.png","Assets/Shinobi/Shield4.png",
            "Assets/Shinobi/7.png"
    };

    String[] fighterTextures = {
            "Assets/Fighter/Walk1.png","Assets/Fighter/Walk2.png","Assets/Fighter/Walk3.png","Assets/Fighter/Walk4.png","Assets/Fighter/Walk5.png","Assets/Fighter/Walk6.png","Assets/Fighter/Walk7.png","Assets/Fighter/Walk8.png",
            "Assets/Fighter/Idle1.png","Assets/Fighter/Idle2.png","Assets/Fighter/Idle3.png","Assets/Fighter/Idle4.png","Assets/Fighter/Idle5.png","Assets/Fighter/Idle6.png",
            "Assets/Fighter/IAttack1.png","Assets/Fighter/IAttack2.png","Assets/Fighter/IAttack3.png","Assets/Fighter/IAttack4.png",
            "Assets/Fighter/IIAttack1.png","Assets/Fighter/IIAttack2.png","Assets/Fighter/IIAttack3.png",
            "Assets/Fighter/IIIAttack1.png","Assets/Fighter/IIIAttack2.png","Assets/Fighter/IIIAttack3.png","Assets/Fighter/IIIAttack4.png",
            "Assets/Fighter/Jump1.png","Assets/Fighter/Jump2.png","Assets/Fighter/Jump3.png","Assets/Fighter/Jump4.png","Assets/Fighter/Jump5.png","Assets/Fighter/Jump6.png","Assets/Fighter/Jump7.png","Assets/Fighter/Jump8.png","Assets/Fighter/Jump9.png","Assets/Fighter/Jump10.png",
            "Assets/Fighter/Run1.png","Assets/Fighter/Run2.png","Assets/Fighter/Run3.png","Assets/Fighter/Run4.png","Assets/Fighter/Run5.png","Assets/Fighter/Run6.png","Assets/Fighter/Run7.png","Assets/Fighter/Run8.png",
            "Assets/Fighter/Hurt1.png","Assets/Fighter/Hurt2.png","Assets/Fighter/Hurt3.png",
            "Assets/Fighter/Dead1.png","Assets/Fighter/Dead2.png","Assets/Fighter/Dead3.png",
            "Assets/Fighter/Shield1.png","Assets/Fighter/Shield2.png",
            "Assets/Fighter/7.png"
    };

    String[] samuraiTextures = {
            "Assets/Samurai/Walk1.png","Assets/Samurai/Walk2.png","Assets/Samurai/Walk3.png","Assets/Samurai/Walk4.png","Assets/Samurai/Walk5.png","Assets/Samurai/Walk6.png","Assets/Samurai/Walk7.png","Assets/Samurai/Walk8.png",
            "Assets/Samurai/Idle1.png","Assets/Samurai/Idle2.png","Assets/Samurai/Idle3.png","Assets/Samurai/Idle4.png","Assets/Samurai/Idle5.png","Assets/Samurai/Idle6.png",
            "Assets/Samurai/IAttack1.png","Assets/Samurai/IAttack2.png","Assets/Samurai/IAttack3.png","Assets/Samurai/IAttack4.png","Assets/Samurai/IAttack5.png","Assets/Samurai/IAttack6.png",
            "Assets/Samurai/IIAttack1.png","Assets/Samurai/IIAttack2.png","Assets/Samurai/IIAttack3.png","Assets/Samurai/IIAttack4.png",
            "Assets/Samurai/IIIAttack1.png","Assets/Samurai/IIIAttack2.png","Assets/Samurai/IIIAttack3.png",
            "Assets/Samurai/Jump1.png","Assets/Samurai/Jump2.png","Assets/Samurai/Jump3.png","Assets/Samurai/Jump4.png","Assets/Samurai/Jump5.png","Assets/Samurai/Jump6.png","Assets/Samurai/Jump7.png","Assets/Samurai/Jump8.png","Assets/Samurai/Jump9.png","Assets/Samurai/Jump10.png","Assets/Samurai/Jump11.png","Assets/Samurai/Jump12.png",
            "Assets/Samurai/Run1.png","Assets/Samurai/Run2.png","Assets/Samurai/Run3.png","Assets/Samurai/Run4.png","Assets/Samurai/Run5.png","Assets/Samurai/Run6.png","Assets/Samurai/Run7.png","Assets/Samurai/Run8.png",
            "Assets/Samurai/Hurt1.png","Assets/Samurai/Hurt2.png",
            "Assets/Samurai/Dead1.png","Assets/Samurai/Dead2.png","Assets/Samurai/Dead3.png",
            "Assets/Samurai/Shield1.png","Assets/Samurai/Shield2.png",
            "Assets/Samurai/7.png"
    };

    int[][] shinobiIDs;
    int[][] fighterIDs;
    int[][] samuraiIDs;
    int[] bgIDs = new int[3];

    // Frame counts
    int MAX_WALK[] = {8,8,8}, MAX_IDLE[] = {6,6,6}, MAX_ATTACK1[] = {5,4,6},
            MAX_ATTACK2[] = {3,3,4}, MAX_ATTACK3[] = {4,4,3}, MAX_JUMP[] = {11,10,12},
            MAX_RUN[] = {8,8,8}, MAX_HURT[] = {2,3,2}, MAX_DEAD[] = {4,3,3}, MAX_SHIELD[] = {4,2,2};

    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClearColor(1,1,1,1);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        super.initUI(gl);

        shinobiIDs = loadCharacter(gl, shinobiTextures, 0);
        fighterIDs = loadCharacter(gl, fighterTextures, 1);
        samuraiIDs = loadCharacter(gl, samuraiTextures, 2);

        // Player 1 (Shinobi)
        player1 = new Player(15, 20, shinobiIDs, 0, false);
        player1.setControls(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D,
                KeyEvent.VK_Z, KeyEvent.VK_X, KeyEvent.VK_C);

        // Player 2 (Samurai)
        player2 = new Player(35, 20, samuraiIDs, 2, true);
        player2.setControls(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT,
                KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L);

        System.out.println("Game Initialized!");
    }

    int[][] loadCharacter(GL gl, String[] texFiles, int charIdx) {
        int[][] ids = new int[10][15]; // [State][Frame]
        int offset = 0;

        for(int i=0; i<MAX_WALK[charIdx]; i++) ids[0][i] = genTex(gl, texFiles[offset++]);   // 0
        for(int i=0; i<MAX_IDLE[charIdx]; i++) ids[1][i] = genTex(gl, texFiles[offset++]);   // 1
        for(int i=0; i<MAX_ATTACK1[charIdx]; i++) ids[2][i] = genTex(gl, texFiles[offset++]);// 2
        for(int i=0; i<MAX_ATTACK2[charIdx]; i++) ids[3][i] = genTex(gl, texFiles[offset++]);// 3
        for(int i=0; i<MAX_ATTACK3[charIdx]; i++) ids[4][i] = genTex(gl, texFiles[offset++]);// 4
        for(int i=0; i<MAX_JUMP[charIdx]; i++) ids[5][i] = genTex(gl, texFiles[offset++]);   // 5
        for(int i=0; i<MAX_RUN[charIdx]; i++) ids[6][i] = genTex(gl, texFiles[offset++]);    // 6
        for(int i=0; i<MAX_HURT[charIdx]; i++) ids[7][i] = genTex(gl, texFiles[offset++]);   // 7
        for(int i=0; i<MAX_DEAD[charIdx]; i++) ids[8][i] = genTex(gl, texFiles[offset++]);   // 8
        for(int i=0; i<MAX_SHIELD[charIdx]; i++) ids[9][i] = genTex(gl, texFiles[offset++]); // 9

        bgIDs[charIdx] = genTex(gl, texFiles[offset]);
        return ids;
    }

    int genTex(GL gl, String path){
        try {
            TextureReader.Texture tex = TextureReader.readTexture(path, true);
            int[] id = new int[1];
            gl.glGenTextures(1, id, 0);
            gl.glBindTexture(GL.GL_TEXTURE_2D, id[0]);
            new GLU().gluBuild2DMipmaps(GL.GL_TEXTURE_2D, GL.GL_RGBA,
                    tex.getWidth(), tex.getHeight(),
                    GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, tex.getPixels());
            return id[0];
        } catch(Exception e){
            System.out.println("Err: " + path);
            return -1;
        }
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();

        DrawBackground(gl, bgIDs[0]);

        if (!isPaused) {
            player1.update();
            player2.update();

            checkAttackInternal(player1, player2);
            checkAttackInternal(player2, player1);
        }
        player1.draw(gl);
        player2.draw(gl);

        super.drawUI(gl, drawable.getWidth(), drawable.getHeight());
    }

    void checkAttackInternal(Player attacker, Player victim) {
        boolean isAttacking = (attacker.state >= 2 && attacker.state <= 4);
        if (!isAttacking) return;

        int distance = Math.abs((int)attacker.x - (int)victim.x);
        boolean closeEnough = distance < 6;

        boolean facingVictim = false;
        if (attacker.facingLeft && attacker.x > victim.x) facingVictim = true;
        if (!attacker.facingLeft && attacker.x < victim.x) facingVictim = true;

        if (closeEnough && facingVictim && attacker.animIndex == 2) {
            // !!! التعديل هنا !!!
            // إذا كان الخصم يصد (state 9) أو يقفز (isJumping)، لا يحدث ضرر
            if (victim.state == 9 || victim.isJumping) return;

            victim.takeDamage();
        }
    }

    void DrawBackground(GL gl, int tex){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, tex);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0,0); gl.glVertex2f(-1,-1);
        gl.glTexCoord2f(1,0); gl.glVertex2f(1,-1);
        gl.glTexCoord2f(1,1); gl.glVertex2f(1,1);
        gl.glTexCoord2f(0,1); gl.glVertex2f(-1,1);
        gl.glEnd();
        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        gl.glViewport(0,0,width,height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(-1,1,-1,1,-1,1);
        gl.glMatrixMode(GL.GL_MODELVIEW);
    }

    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

    @Override
    public void keyPressed(KeyEvent e) { keyBits.set(e.getKeyCode(), true); }
    @Override
    public void keyReleased(KeyEvent e) { keyBits.set(e.getKeyCode(), false); }
    @Override
    public void keyTyped(KeyEvent e) {}


    // --- Player Class with Physics ---
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

        // --- Physics Vars ---
        float velocityY = 0;
        float gravity = 0.1f;
        float jumpPower = 1.4f;
        float groundY = 20;
        boolean isJumping = false;

        int kUp, kDown, kLeft, kRight, kAtt1, kAtt2, kAtt3;

        public Player(float startX, float startY, int[][] ids, int cIndex, boolean startFaceLeft){
            this.x = startX;
            this.y = startY;
            this.groundY = startY;
            this.textureIDs = ids;
            this.charIndex = cIndex;
            this.facingLeft = startFaceLeft;
        }

        public void setControls(int up, int down, int left, int right, int a1, int a2, int a3){
            this.kUp = up; this.kDown = down; this.kLeft = left; this.kRight = right;
            this.kAtt1 = a1; this.kAtt2 = a2; this.kAtt3 = a3;
        }

        public void update(){
            // --- Dead Logic ---
            if (state == 8) {
                if (animIndex < MAX_DEAD[charIndex] - 1) frameDelay++;
                return;
            }
            // --- Hurt Logic ---
            if (state == 7) {
                frameDelay++;
                if (frameDelay >= MAX_HURT[charIndex] * 3) {
                    state = 1; animIndex = 0; frameDelay = 0;
                }
                return;
            }

            // --- Physics (Gravity) ---
            y += velocityY;
            velocityY -= gravity;

            if (y <= groundY) {
                y = groundY;
                velocityY = 0;
                isJumping = false;
            } else {
                isJumping = true;
            }

            lastState = state;

            // --- Controls ---
            if(keyBits.get(kUp) && !isJumping) {
                velocityY = jumpPower;
                isJumping = true;
            }

            if (isJumping) {
                state = 5; // Jump
            } else if (keyBits.get(kDown)) {
                state = 9; // Shield
            } else if (keyBits.get(kAtt1)) {
                state = 2;
            } else if (keyBits.get(kAtt2)) {
                state = 3;
            } else if (keyBits.get(kAtt3)) {
                state = 4;
            } else if (keyBits.get(kLeft) || keyBits.get(kRight)) {
                state = 0; // Walk
            } else {
                state = 1; // Idle
            }

            // Movement
            if(keyBits.get(kLeft)) {
                if(x > 0) x -= 0.5f;
                facingLeft = true;
            }
            if(keyBits.get(kRight)) {
                if(x < 50) x += 0.5f;
                facingLeft = false;
            }

            if(state != lastState){
                animIndex = 0; frameDelay = 0;
            }
            frameDelay++;
        }

        public void takeDamage() {
            if(state == 8 || state == 7 || state == 9) return;

            health -= 20;
            state = 7; animIndex = 0; frameDelay = 0;

            if (health <= 0) {
                health = 0; state = 8;
                System.out.println("Player Died");
            }
        }

        public void draw(GL gl){
            int texID = getCurrentFrame();
            gl.glEnable(GL.GL_BLEND);
            gl.glBindTexture(GL.GL_TEXTURE_2D, texID);
            gl.glPushMatrix();

            gl.glTranslated(x/25.0 - 1, y/25.0 - 1, 0);

            gl.glScaled(facingLeft ? -0.3 : 0.3, 0.3, 1);
            gl.glBegin(GL.GL_QUADS);
            gl.glTexCoord2f(0,0); gl.glVertex3f(-1,-1,0);
            gl.glTexCoord2f(1,0); gl.glVertex3f(1,-1,0);
            gl.glTexCoord2f(1,1); gl.glVertex3f(1,1,0);
            gl.glTexCoord2f(0,1); gl.glVertex3f(-1,1,0);
            gl.glEnd();
            gl.glPopMatrix();
            gl.glDisable(GL.GL_BLEND);
        }

        int getCurrentFrame(){
            int maxFrames = 1;
            switch(state){
                case 0: maxFrames = MAX_WALK[charIndex]; break;
                case 1: maxFrames = MAX_IDLE[charIndex]; break;
                case 2: maxFrames = MAX_ATTACK1[charIndex]; break;
                case 3: maxFrames = MAX_ATTACK2[charIndex]; break;
                case 4: maxFrames = MAX_ATTACK3[charIndex]; break;
                case 5: maxFrames = MAX_JUMP[charIndex]; break;
                case 7: maxFrames = MAX_HURT[charIndex]; break;
                case 8: maxFrames = MAX_DEAD[charIndex]; break;
                case 9: maxFrames = MAX_SHIELD[charIndex]; break;
                default: maxFrames = MAX_IDLE[charIndex]; break;
            }

            if(frameDelay % 3 == 0) animIndex++;

            if ((state == 8 || state == 9) && animIndex >= maxFrames) {
                animIndex = maxFrames - 1;
            }
            else if(animIndex >= maxFrames) {
                animIndex = 0;
            }
            return textureIDs[state][animIndex];
        }
    }
}