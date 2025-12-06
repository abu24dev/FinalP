package FinalP;

import FinalP.Texture.TextureReader;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import java.awt.event.*;
import java.util.BitSet;

public class AnimGLEventListener3 extends AnimListener implements KeyListener {

    Player player1;

    // we use the same keyBits mechanism as in AnimGLEventListener2
    static BitSet keyBits = new BitSet(256);

    // --- Texture Arrays (only Shinobi is actually used here) ---
    String[] shinobiTextures = {
            // Walk (8)
            "Assets/Shinobi/Walk1.png","Assets/Shinobi/Walk2.png","Assets/Shinobi/Walk3.png","Assets/Shinobi/Walk4.png",
            "Assets/Shinobi/Walk5.png","Assets/Shinobi/Walk6.png","Assets/Shinobi/Walk7.png","Assets/Shinobi/Walk8.png",
            // Idle (6)
            "Assets/Shinobi/Idle1.png","Assets/Shinobi/Idle2.png","Assets/Shinobi/Idle3.png",
            "Assets/Shinobi/Idle4.png","Assets/Shinobi/Idle5.png","Assets/Shinobi/Idle6.png",
            // Attack1
            "Assets/Shinobi/IAttack1.png","Assets/Shinobi/IAttack2.png","Assets/Shinobi/IAttack3.png",
            "Assets/Shinobi/IAttack4.png","Assets/Shinobi/IAttack5.png",
            // Attack2
            "Assets/Shinobi/IIAttack1.png","Assets/Shinobi/IIAttack2.png","Assets/Shinobi/IIAttack3.png",
            // Attack3
            "Assets/Shinobi/IIIAttack1.png","Assets/Shinobi/IIIAttack2.png","Assets/Shinobi/IIIAttack3.png","Assets/Shinobi/IIIAttack4.png",
            // Jump (11)
            "Assets/Shinobi/Jump1.png","Assets/Shinobi/Jump2.png","Assets/Shinobi/Jump3.png","Assets/Shinobi/Jump4.png",
            "Assets/Shinobi/Jump5.png","Assets/Shinobi/Jump6.png","Assets/Shinobi/Jump7.png","Assets/Shinobi/Jump8.png",
            "Assets/Shinobi/Jump9.png","Assets/Shinobi/Jump10.png","Assets/Shinobi/Jump11.png",
            // Run (8)
            "Assets/Shinobi/Run1.png","Assets/Shinobi/Run2.png","Assets/Shinobi/Run3.png","Assets/Shinobi/Run4.png",
            "Assets/Shinobi/Run5.png","Assets/Shinobi/Run6.png","Assets/Shinobi/Run7.png","Assets/Shinobi/Run8.png",
            // Hurt (2)
            "Assets/Shinobi/Hurt1.png","Assets/Shinobi/Hurt2.png",
            // Dead (4)
            "Assets/Shinobi/Dead1.png","Assets/Shinobi/Dead2.png","Assets/Shinobi/Dead3.png","Assets/Shinobi/Dead4.png",
            // Shield (4)
            "Assets/Shinobi/Shield1.png","Assets/Shinobi/Shield2.png","Assets/Shinobi/Shield3.png","Assets/Shinobi/Shield4.png",
            // Background
            "Assets/Shinobi/7.png"
    };

    // We keep the same frame counts as in multiplayer (index 0 = Shinobi)
    int MAX_WALK[]   = {8,8,8}, MAX_IDLE[]   = {6,6,6}, MAX_ATTACK1[] = {5,4,6},
            MAX_ATTACK2[] = {3,3,4}, MAX_ATTACK3[] = {4,4,3}, MAX_JUMP[]   = {11,10,12},
            MAX_RUN[]     = {8,8,8}, MAX_HURT[]   = {2,3,2}, MAX_DEAD[]   = {4,3,3}, MAX_SHIELD[] = {4,2,2};

    int[][] shinobiIDs;
    int[] bgIDs = new int[3];

    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClearColor(1,1,1,1);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        super.initUI(gl); // if your AnimListener has UI; safe to keep

        shinobiIDs = loadCharacter(gl, shinobiTextures, 0);

        // Single player = same as player1 in multiplayer:
        // start at (15,20), use shinobiIDs, charIndex=0, facingRight (false = facing left? we set to false here)
        player1 = new Player(15, 20, shinobiIDs, 0, false);

        // same controls as your multiplayer player1
        player1.setControls(
                KeyEvent.VK_W,  // up  (jump)
                KeyEvent.VK_S,  // down (shield)
                KeyEvent.VK_A,  // left
                KeyEvent.VK_D,  // right
                KeyEvent.VK_Z,  // attack1
                KeyEvent.VK_X,  // attack2
                KeyEvent.VK_C   // attack3
        );

        System.out.println("Single Player Initialized!");
    }

    int[][] loadCharacter(GL gl, String[] texFiles, int charIdx) {
        int[][] ids = new int[10][15]; // [State][Frame]
        int offset = 0;

        for(int i=0; i<MAX_WALK[charIdx]; i++)   ids[0][i] = genTex(gl, texFiles[offset++]);  // WALK
        for(int i=0; i<MAX_IDLE[charIdx]; i++)   ids[1][i] = genTex(gl, texFiles[offset++]);  // IDLE
        for(int i=0; i<MAX_ATTACK1[charIdx]; i++)ids[2][i] = genTex(gl, texFiles[offset++]);  // ATT1
        for(int i=0; i<MAX_ATTACK2[charIdx]; i++)ids[3][i] = genTex(gl, texFiles[offset++]);  // ATT2
        for(int i=0; i<MAX_ATTACK3[charIdx]; i++)ids[4][i] = genTex(gl, texFiles[offset++]);  // ATT3
        for(int i=0; i<MAX_JUMP[charIdx]; i++)   ids[5][i] = genTex(gl, texFiles[offset++]);  // JUMP
        for(int i=0; i<MAX_RUN[charIdx]; i++)    ids[6][i] = genTex(gl, texFiles[offset++]);  // RUN
        for(int i=0; i<MAX_HURT[charIdx]; i++)   ids[7][i] = genTex(gl, texFiles[offset++]);  // HURT
        for(int i=0; i<MAX_DEAD[charIdx]; i++)   ids[8][i] = genTex(gl, texFiles[offset++]);  // DEAD
        for(int i=0; i<MAX_SHIELD[charIdx]; i++) ids[9][i] = genTex(gl, texFiles[offset++]);  // SHIELD

        // background
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

        DrawBackground(gl, bgIDs[0]);  // shinobi background

        if (!isPaused) {
            player1.update();
        }

        player1.draw(gl);

        super.drawUI(gl, drawable.getWidth(), drawable.getHeight());
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
        gl.glOrtho(-1,1,-1,1,-1,1);  // same as multiplayer
        gl.glMatrixMode(GL.GL_MODELVIEW);
    }
    @Override
    public void resetGame() {
        // 1. إرجاع اللاعب لنقطة البداية (نفس الأرقام اللي في init)
        player1.x = 15;
        player1.y = 20;

        // 2. تصفير الحالة
        player1.state = 1; // Idle
        player1.facingLeft = false;
        player1.velocityY = 0;
        player1.isJumping = false;
        player1.health = 100; // لو بتستخدم الـ Health

        // 3. مسح الأزرار المعلقة عشان اللاعب ميفضلش بيتحرك لوحده
        keyBits.clear();
    }


    @Override
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {}

    // Key listener -> fills keyBits (same as multiplayer)
    @Override
    public void keyPressed(KeyEvent e)  { keyBits.set(e.getKeyCode(), true); }
    @Override
    public void keyReleased(KeyEvent e) { keyBits.set(e.getKeyCode(), false); }
    @Override
    public void keyTyped(KeyEvent e)    {}



    // =========================================================
    //  Inner Player class (copied from your multiplayer version)
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
        float gravity   = 0.1f;
        float jumpPower = 1.3f;
        float groundY   = 20;
        boolean isJumping = false;

        // controls
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
            // dead
            if (state == 8) {
                if (animIndex < MAX_DEAD[charIndex] - 1) frameDelay++;
                return;
            }

            // hurt
            if (state == 7) {
                frameDelay++;
                if (frameDelay >= MAX_HURT[charIndex] * 3) {
                    state = 1; animIndex = 0; frameDelay = 0;
                }
                return;
            }

            // gravity
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

            // Controls -> states
            if(keyBits.get(kUp) && !isJumping) {
                velocityY = jumpPower;
                isJumping = true;
            }

            if (isJumping) {
                state = 5; // jump
            } else if (keyBits.get(kDown)) {
                state = 9; // shield
            } else if (keyBits.get(kAtt1)) {
                state = 2;
            } else if (keyBits.get(kAtt2)) {
                state = 3;
            } else if (keyBits.get(kAtt3)) {
                state = 4;
            } else if (keyBits.get(kLeft) || keyBits.get(kRight)) {
                state = 0; // walk
            } else {
                state = 1; // idle
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

        public void draw(GL gl){
            int texID = getCurrentFrame();
            gl.glEnable(GL.GL_BLEND);
            gl.glBindTexture(GL.GL_TEXTURE_2D, texID);
            gl.glPushMatrix();

            // SAME transformation as multiplayer
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
            int maxFrames;
            switch(state){
                case 0: maxFrames = MAX_WALK[charIndex];   break;
                case 1: maxFrames = MAX_IDLE[charIndex];   break;
                case 2: maxFrames = MAX_ATTACK1[charIndex];break;
                case 3: maxFrames = MAX_ATTACK2[charIndex];break;
                case 4: maxFrames = MAX_ATTACK3[charIndex];break;
                case 5: maxFrames = MAX_JUMP[charIndex];   break;
                case 7: maxFrames = MAX_HURT[charIndex];   break;
                case 8: maxFrames = MAX_DEAD[charIndex];   break;
                case 9: maxFrames = MAX_SHIELD[charIndex]; break;
                default:maxFrames = MAX_IDLE[charIndex];   break;
            }

            if(frameDelay % 3 == 0) animIndex++;

            // dead / shield: stop at last frame
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
