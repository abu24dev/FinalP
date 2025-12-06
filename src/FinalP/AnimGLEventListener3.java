package FinalP;

import FinalP.Texture.TextureReader;
import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import java.awt.event.*;
import java.util.BitSet;

public class AnimGLEventListener3 extends AnimListener implements KeyListener {

    int x = 20, y = 20;

    // CHARACTER SELECTION
    String currentCharacter = "Shinobi";
    int charIndex = 0;
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
            "Assets/Shinobi/IIIAttack1.png","Assets/Shinobi/IIIAttack2.png","Assets/Shinobi/IIIAttack3.png",
            "Assets/Shinobi/IIIAttack4.png",
            // Jump (11)
            "Assets/Shinobi/Jump1.png","Assets/Shinobi/Jump2.png","Assets/Shinobi/Jump3.png","Assets/Shinobi/Jump4.png",
            "Assets/Shinobi/Jump5.png", "Assets/Shinobi/Jump6.png","Assets/Shinobi/Jump7.png","Assets/Shinobi/Jump8.png"
            ,"Assets/Shinobi/Jump9.png","Assets/Shinobi/Jump10.png","Assets/Shinobi/Jump11.png",
            // Run (8)
            "Assets/Shinobi/Run1.png","Assets/Shinobi/Run2.png","Assets/Shinobi/Run3.png","Assets/Shinobi/Run4.png",
            "Assets/Shinobi/Run5.png","Assets/Shinobi/Run6.png","Assets/Shinobi/Run7.png","Assets/Shinobi/Run8.png",
            // Hurt (2)
            "Assets/Shinobi/Hurt1.png","Assets/Shinobi/Hurt2.png",
            // Dead (4)
            "Assets/Shinobi/Dead1.png","Assets/Shinobi/Dead2.png","Assets/Shinobi/Dead3.png","Assets/Shinobi/Dead4.png",
            // Shield (4)
            "Assets/Shinobi/Shield1.png","Assets/Shinobi/Shield2.png","Assets/Shinobi/Shield3.png",
            "Assets/Shinobi/Shield4.png",
            // Background
            "Assets/Shinobi/7.png"
    };

    // Fighter
    String[] fighterTextures = {
            // Walk (8)
            "Assets/Fighter/Walk1.png","Assets/Fighter/Walk2.png","Assets/Fighter/Walk3.png","Assets/Fighter/Walk4.png",
            "Assets/Fighter/Walk5.png","Assets/Fighter/Walk6.png","Assets/Fighter/Walk7.png","Assets/Fighter/Walk8.png",
            // Idle (6)
            "Assets/Fighter/Idle1.png","Assets/Fighter/Idle2.png","Assets/Fighter/Idle3.png",
            "Assets/Fighter/Idle4.png","Assets/Fighter/Idle5.png","Assets/Fighter/Idle6.png",
            // Attack1
            "Assets/Fighter/IAttack1.png","Assets/Fighter/IAttack2.png","Assets/Fighter/IAttack3.png",
            "Assets/Fighter/IAttack4.png",
            // Attack2
            "Assets/Fighter/IIAttack1.png","Assets/Fighter/IIAttack2.png","Assets/Fighter/IIAttack3.png",
            // Attack3
            "Assets/Fighter/IIIAttack1.png","Assets/Fighter/IIIAttack2.png","Assets/Fighter/IIIAttack3.png",
            "Assets/Fighter/IIIAttack4.png",
            // Jump (10)
            "Assets/Fighter/Jump1.png","Assets/Fighter/Jump2.png","Assets/Fighter/Jump3.png","Assets/Fighter/Jump4.png",
            "Assets/Fighter/Jump5.png", "Assets/Fighter/Jump6.png","Assets/Fighter/Jump7.png","Assets/Fighter/Jump8.png",
            "Assets/Fighter/Jump9.png","Assets/Fighter/Jump10.png",
            // Run (8)
            "Assets/Fighter/Run1.png","Assets/Fighter/Run2.png","Assets/Fighter/Run3.png","Assets/Fighter/Run4.png",
            "Assets/Fighter/Run5.png","Assets/Fighter/Run6.png","Assets/Fighter/Run7.png","Assets/Fighter/Run8.png",
            // Hurt (3)
            "Assets/Fighter/Hurt1.png","Assets/Fighter/Hurt2.png","Assets/Fighter/Hurt3.png",
            // Dead (3)
            "Assets/Fighter/Dead1.png","Assets/Fighter/Dead2.png","Assets/Fighter/Dead3.png",
            // Shield (2)
            "Assets/Fighter/Shield1.png","Assets/Fighter/Shield2.png",
            // Background
            "Assets/Fighter/7.png"
    };

    // Samurai
    String[] samuraiTextures = {
            // Walk (8)
            "Assets/Samurai/Walk1.png","Assets/Samurai/Walk2.png","Assets/Samurai/Walk3.png","Assets/Samurai/Walk4.png",
            "Assets/Samurai/Walk5.png","Assets/Samurai/Walk6.png","Assets/Samurai/Walk7.png","Assets/Samurai/Walk8.png",
            // Idle (6)
            "Assets/Samurai/Idle1.png","Assets/Samurai/Idle2.png","Assets/Samurai/Idle3.png",
            "Assets/Samurai/Idle4.png","Assets/Samurai/Idle5.png","Assets/Samurai/Idle6.png",
            // Attack1 (6)
            "Assets/Samurai/IAttack1.png","Assets/Samurai/IAttack2.png","Assets/Samurai/IAttack3.png",
            "Assets/Samurai/IAttack4.png","Assets/Samurai/IAttack5.png","Assets/Samurai/IAttack6.png",
            // Attack2
            "Assets/Samurai/IIAttack1.png","Assets/Samurai/IIAttack2.png","Assets/Samurai/IIAttack3.png",
            "Assets/Samurai/IIAttack4.png",
            // Attack3
            "Assets/Samurai/IIIAttack1.png","Assets/Samurai/IIIAttack2.png","Assets/Samurai/IIIAttack3.png",
            // Jump (12)
            "Assets/Samurai/Jump1.png","Assets/Samurai/Jump2.png","Assets/Samurai/Jump3.png","Assets/Samurai/Jump4.png"
            ,"Assets/Samurai/Jump5.png", "Assets/Samurai/Jump6.png","Assets/Samurai/Jump7.png","Assets/Samurai/Jump8.png"
            ,"Assets/Samurai/Jump9.png","Assets/Samurai/Jump10.png", "Assets/Samurai/Jump11.png","Assets/Samurai/Jump12.png",
            // Run (8)
            "Assets/Samurai/Run1.png","Assets/Samurai/Run2.png","Assets/Samurai/Run3.png","Assets/Samurai/Run4.png",
            "Assets/Samurai/Run5.png","Assets/Samurai/Run6.png","Assets/Samurai/Run7.png","Assets/Samurai/Run8.png",
            // Hurt (2)
            "Assets/Samurai/Hurt1.png","Assets/Samurai/Hurt2.png",
            // Dead (3)
            "Assets/Samurai/Dead1.png","Assets/Samurai/Dead2.png","Assets/Samurai/Dead3.png",
            // Shield (2)
            "Assets/Samurai/Shield1.png","Assets/Samurai/Shield2.png",
            // Background
            "Assets/Samurai/7.png"
    };

    // ANIMATION STATES
    enum State {IDLE, WALK, JUMP, ATTACK1, ATTACK2, ATTACK3}
    State state = State.IDLE;
    State lastState = State.IDLE;

    int animIndex = 0;
    int frameDelay = 0;
    boolean facingLeft = false;

    BitSet keys = new BitSet(256);

    // MAX FRAMES
    int MAX_WALK[] = {8,8,8}, MAX_IDLE[] = {6,6,6}, MAX_ATTACK1[] = {5,4,6},
            MAX_ATTACK2[] = {3,3,4}, MAX_ATTACK3[] = {4,4,3}, MAX_JUMP[] = {11,10,12},
            MAX_RUN[] = {8,8,8}, MAX_HURT[] = {2,3,2}, MAX_DEAD[] = {4,3,3}, MAX_SHIELD[] = {4,2,2};
//sout
    //sout
    // TEXTURE STORAGE
    TextureReader.Texture[][] walkTex = new TextureReader.Texture[3][12];
    TextureReader.Texture[][] idleTex = new TextureReader.Texture[3][12];
    TextureReader.Texture[][] attack1Tex = new TextureReader.Texture[3][12];
    TextureReader.Texture[][] attack2Tex = new TextureReader.Texture[3][12];
    TextureReader.Texture[][] attack3Tex = new TextureReader.Texture[3][12];
    TextureReader.Texture[][] jumpTex = new TextureReader.Texture[3][12];
    TextureReader.Texture[][] runTex = new TextureReader.Texture[3][12];
    TextureReader.Texture[][] hurtTex = new TextureReader.Texture[3][12];
    TextureReader.Texture[][] deadTex = new TextureReader.Texture[3][12];
    TextureReader.Texture[][] shieldTex = new TextureReader.Texture[3][12];
    TextureReader.Texture[] bgTex = new TextureReader.Texture[3];

    int[][] walkIDs = new int[3][12];
    int[][] idleIDs = new int[3][12];
    int[][] attack1IDs = new int[3][12];
    int[][] attack2IDs = new int[3][12];
    int[][] attack3IDs = new int[3][12];
    int[][] jumpIDs = new int[3][12];
    int[][] runIDs = new int[3][12];
    int[][] hurtIDs = new int[3][12];
    int[][] deadIDs = new int[3][12];
    int[][] shieldIDs = new int[3][12];
    int[] bgIDs = new int[3];

    // INIT
    @Override
    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        gl.glClearColor(1,1,1,1);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        super.initUI(gl);

        loadCharacter(gl, 0, shinobiTextures, 0);
        loadCharacter(gl, 1, fighterTextures, 1);
        loadCharacter(gl, 2, samuraiTextures, 2);

        System.out.println("Textures loaded successfully!");
    }

    void loadCharacter(GL gl, int index, String[] texFiles, int charIdx) {
        try {
            int offset = 0;

            for(int i=0;i<MAX_WALK[charIdx];i++, offset++){
                walkTex[index][i] = TextureReader.readTexture(texFiles[offset], true);
                walkIDs[index][i] = genTex(gl, walkTex[index][i]);
            }

            for(int i=0;i<MAX_IDLE[charIdx];i++, offset++){
                idleTex[index][i] = TextureReader.readTexture(texFiles[offset], true);
                idleIDs[index][i] = genTex(gl, idleTex[index][i]);
            }

            for(int i=0;i<MAX_ATTACK1[charIdx];i++, offset++){
                attack1Tex[index][i] = TextureReader.readTexture(texFiles[offset], true);
                attack1IDs[index][i] = genTex(gl, attack1Tex[index][i]);
            }

            for(int i=0;i<MAX_ATTACK2[charIdx];i++, offset++){
                attack2Tex[index][i] = TextureReader.readTexture(texFiles[offset], true);
                attack2IDs[index][i] = genTex(gl, attack2Tex[index][i]);
            }

            for(int i=0;i<MAX_ATTACK3[charIdx];i++, offset++){
                attack3Tex[index][i] = TextureReader.readTexture(texFiles[offset], true);
                attack3IDs[index][i] = genTex(gl, attack3Tex[index][i]);
            }

            for(int i=0;i<MAX_JUMP[charIdx];i++, offset++){
                jumpTex[index][i] = TextureReader.readTexture(texFiles[offset], true);
                jumpIDs[index][i] = genTex(gl, jumpTex[index][i]);
            }

            for(int i=0;i<MAX_RUN[charIdx];i++, offset++){
                runTex[index][i] = TextureReader.readTexture(texFiles[offset], true);
                runIDs[index][i] = genTex(gl, runTex[index][i]);
            }

            for(int i=0;i<MAX_HURT[charIdx];i++, offset++){
                hurtTex[index][i] = TextureReader.readTexture(texFiles[offset], true);
                hurtIDs[index][i] = genTex(gl, hurtTex[index][i]);
            }

            for(int i=0;i<MAX_DEAD[charIdx];i++, offset++){
                deadTex[index][i] = TextureReader.readTexture(texFiles[offset], true);
                deadIDs[index][i] = genTex(gl, deadTex[index][i]);
            }

            for(int i=0;i<MAX_SHIELD[charIdx];i++, offset++){
                shieldTex[index][i] = TextureReader.readTexture(texFiles[offset], true);
                shieldIDs[index][i] = genTex(gl, shieldTex[index][i]);
            }

            bgTex[index] = TextureReader.readTexture(texFiles[offset], true);
            bgIDs[index] = genTex(gl, bgTex[index]);

        } catch(Exception e){
            System.out.println("Error loading textures: " + e);
        }
    }

    int genTex(GL gl, TextureReader.Texture tex){
        int[] id = new int[1];
        gl.glGenTextures(1, id, 0);
        gl.glBindTexture(GL.GL_TEXTURE_2D, id[0]);
        new GLU().gluBuild2DMipmaps(GL.GL_TEXTURE_2D, GL.GL_RGBA,
                tex.getWidth(), tex.getHeight(),
                GL.GL_RGBA, GL.GL_UNSIGNED_BYTE, tex.getPixels());
        return id[0];
    }

    // DISPLAY LOOP
    @Override
    public void display(GLAutoDrawable drawable){
        GL gl = drawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();

        DrawBackground(gl, bgIDs[charIndex]);

        if (!isPaused) {
            frameDelay++;
            updateState();
            handleMovement();
            handleCharacterSwitch();
        }

        int texID = getCurrentFrame();

        DrawSprite(gl, x, y, texID, 3f, facingLeft);

        super.drawUI(gl, drawable.getWidth(), drawable.getHeight());
    }

    // SHAPE
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

    // STATE UPDATE
    void updateState(){
        lastState = state;

        if(isKeyPressed(KeyEvent.VK_SPACE)) state = State.JUMP;
        else if(isKeyPressed(KeyEvent.VK_A)) state = State.ATTACK1;
        else if(isKeyPressed(KeyEvent.VK_S)) state = State.ATTACK2;
        else if(isKeyPressed(KeyEvent.VK_D)) state = State.ATTACK3;
        else if(isKeyPressed(KeyEvent.VK_LEFT) || isKeyPressed(KeyEvent.VK_RIGHT)) state = State.WALK;
        else state = State.IDLE;

        if(state != lastState){
            animIndex = 0;
        }
    }

    int getCurrentFrame(){
        switch(state){
            case WALK:
                if(frameDelay % 2 == 0) animIndex = (animIndex + 1) % MAX_WALK[charIndex];
                return walkIDs[charIndex][animIndex];

            case IDLE:
                if(frameDelay % 2 == 0) animIndex = (animIndex + 1) % MAX_IDLE[charIndex];
                return idleIDs[charIndex][animIndex];

            case JUMP:
                if(frameDelay % 2 == 0) animIndex = (animIndex + 1) % MAX_JUMP[charIndex];
                return jumpIDs[charIndex][animIndex];

            case ATTACK1:
                if(frameDelay % 2 == 0) animIndex = (animIndex + 1) % MAX_ATTACK1[charIndex];
                return attack1IDs[charIndex][animIndex];

            case ATTACK2:
                if(frameDelay % 2 == 0) animIndex = (animIndex + 1) % MAX_ATTACK2[charIndex];
                return attack2IDs[charIndex][animIndex];

            case ATTACK3:
                if(frameDelay % 2 == 0) animIndex = (animIndex + 1) % MAX_ATTACK3[charIndex];
                return attack3IDs[charIndex][animIndex];
        }

        return idleIDs[charIndex][0];
    }

    void handleMovement(){
        if(isKeyPressed(KeyEvent.VK_LEFT)){ facingLeft = true; x--; }
        if(isKeyPressed(KeyEvent.VK_RIGHT)){ facingLeft = false; x++; }
//        if(isKeyPressed(KeyEvent.VK_UP)) y++;
//        if(isKeyPressed(KeyEvent.VK_DOWN)) y--;
    }

    void handleCharacterSwitch(){
        if(isKeyPressed(KeyEvent.VK_1)){ charIndex = 0; currentCharacter = "Shinobi"; animIndex = 0; }
        if(isKeyPressed(KeyEvent.VK_2)){ charIndex = 1; currentCharacter = "Fighter"; animIndex = 0; }
        if(isKeyPressed(KeyEvent.VK_3)){ charIndex = 2; currentCharacter = "Samurai"; animIndex = 0; }
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

    void DrawSprite(GL gl,int x,int y,int tex,float scale,boolean flip){
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, tex);
        gl.glPushMatrix();

        gl.glTranslated(x/25.0 - 1, y/25.0 - 1, 0);
        gl.glScaled(flip ? -0.1 * scale : 0.1 * scale, 0.1 * scale, 1);

        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0,0); gl.glVertex3f(-1,-1,0);
        gl.glTexCoord2f(1,0); gl.glVertex3f(1,-1,0);
        gl.glTexCoord2f(1,1); gl.glVertex3f(1,1,0);
        gl.glTexCoord2f(0,1); gl.glVertex3f(-1,1,0);
        gl.glEnd();

        gl.glPopMatrix();
        gl.glDisable(GL.GL_BLEND);
    }

    boolean isKeyPressed(int key){ return keys.get(key); }

    @Override
    public void keyPressed(KeyEvent e) {
        keys.set(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys.set(e.getKeyCode(), false);
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
/////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////