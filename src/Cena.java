import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;
import textura.Textura;
import java.util.Random;
import java.awt.*;

/**
 *
 * @author ExceedEdits & Izabelle
 */
public class Cena implements GLEventListener{    
    GLU glu;
    TextRenderer textRenderer;
    private float xMin, xMax, yMin, yMax, zMin, zMax;

    // Declaracao de constantes
    double angle = 0;
    int score = 0, hp = 5, level = 1;
    float bX = 0, bY = 0, xFactor = 0, xSpeed = 10, ySpeed = 10, eXFactor = 0, eYFactor = 700, eYSpeed = 10,
            eXSpeed = 20, eRadius = 150;
    double eArea = Math.PI*eRadius*eRadius;
    float bgSpeed = 10, bg1Factor = 700, bg2Factor = 350, bg3Factor = 0, bg4Factor = -350, bg5Factor = -700;
    boolean turn = true, loss = false, menuOn = true, menuOff = false, menuPause = false, boss = false,
            finalLevel = false;
    int mode;
    float ang;
    // Atributos
    float limite;

    // Referencia para classe Textura
    Textura textura = null;
    // Quantidade de Texturas a ser carregada
    private int totalTextura = 1;

    // Constantes para identificar as imagens

    public static final String FACE1 = "assets/Menu.jpg";

    public int filtro = GL2.GL_LINEAR;
    public int wrap = GL2.GL_REPEAT;

    // Traz o fator aleatorio
    Random random = new Random();

    @Override
    public void init(GLAutoDrawable drawable) {
        // Dados iniciais da cena
        glu = new GLU();
        GL2 gl = drawable.getGL().getGL2();
        // Estabelece as coordenadas do SRU
        zMin = xMin = -800;
        zMax = xMax = 800;
        yMin = -450;
        yMax = 450;
        ang = 0;
        limite = 1;

        // Cria uma instancia da Classe Textura indicando a quantidade de texturas
        textura = new Textura(totalTextura);

        // Estabelece o renderizador de textos
        textRenderer = new TextRenderer(new Font("Comic Sans MS Negrito",
                Font.PLAIN, 50));

        // Habilita o zbuffer
        gl.glEnable(GL2.GL_DEPTH_TEST);

        reset();
    }

    public void reset(){
        ang = 0;

        // Preenchimento
        mode = GL2.GL_FILL;
    }

    public void writeText(GL2 gl, int xPosicao, int yPosicao, Color cor, String frase){
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
        // Retorna a largura e altura da janela
        textRenderer.beginRendering(Renderer.screenWidth, Renderer.screenHeight);
        textRenderer.setColor(cor);
        textRenderer.draw(frase, xPosicao, yPosicao);
        textRenderer.endRendering();
    }

    public void ambientLights(GL2 gl) {
        float luzAmbiente[] = {0.5f, 0.5f, 0.5f, 0.5f}; // Cor 1
        float posicaoLuz[] = {100.0f, 100.0f, 50.0f, 0.5f}; // Posicao 1
//        float luzAmbiente[] = {0, 0, 0, 0.5f}; // Cor 2
//        float posicaoLuz[] = {-50.0f, 50.0f, 100.0f, 1.0f}; // Posicao 2

        // Define parametros de luz de numero 0
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, luzAmbiente, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicaoLuz, 0);
    }

    public void lightsOn(GL2 gl) {
        // Habilita a definicao da cor do material a partir da cor corrente
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        // Habilita o uso da iluminacao na cena
        gl.glEnable(GL2.GL_LIGHTING);
        // Habilita a luz de numero 0
        gl.glEnable(GL2.GL_LIGHT0);
        // Especifica o tipo de tonalizacao a ser utilizado: GL_FLAT -> Reto | GL_SMOOTH -> Suave
        gl.glShadeModel(gl.GL_SMOOTH);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        // Obtem o contexto OpenGL
        GL2 gl = drawable.getGL().getGL2();
        // Objeto para desenho 3D
        GLUT glut = new GLUT();
        // Define a cor da janela (R, G, G, alpha)
        gl.glClearColor(1, 1, 1, 1);
        // Limpa a janela com a cor especificada
        // Limpa o buffer de profundidade
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity(); // Le a matriz identidade
        gl.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL); // Modo dos objetos 3d

        // DESENHO DA CENA

        // Menu
        if (menuOn || menuPause) {
            gl.glMatrixMode(GL2.GL_TEXTURE);
            gl.glLoadIdentity();
            gl.glRotatef(180, 1, 0, 0);
            gl.glMatrixMode(GL2.GL_MODELVIEW);

            // Nao gera textura automatica
            textura.setAutomatica(false);

            // Configura os filtros
            textura.setFiltro(filtro);
            textura.setModo(GL2.GL_DECAL);
            textura.setWrap(wrap);

            // Cria a textura indicando o local da imagem e o índice
            textura.gerarTextura(gl, FACE1, 0);
            if(!menuOff){
                gl.glBegin (GL2.GL_QUADS );
                // Coordenadas da Textura            // Coordenadas do quads
                gl.glTexCoord2f(0.0f, limite);     gl.glVertex3f(xMin, yMin,  zMax);
                gl.glTexCoord2f(limite, limite);     gl.glVertex3f(xMax, yMin,  zMax);
                gl.glTexCoord2f(limite, 0.0f);     gl.glVertex3f(xMax,  yMax,  zMax);
                gl.glTexCoord2f(0.0f, 0.0f);     gl.glVertex3f(xMin,  yMax,  zMax);
                gl.glEnd();
            }
            if(menuOn){
                writeText(gl, 720, 1000, Color.RED, "DEAD CLUB CITY PONG");
                writeText(gl, 800, 900, Color.white, "COMO JOGAR:");
                writeText(gl, 200, 800, Color.white, "Para controlar sua nave utilize as setas para esquerda e direita.");
                writeText(gl, 200, 700, Color.white, "Para pausar o jogo pressione a tecla BACKSPACE.");
                writeText(gl, 200, 600, Color.white, "Para sair do jogo pressione a tecla ESC.");
                writeText(gl, 200, 500, Color.white, "Para iniciar o jogo pressione a tecla C.");
                writeText(gl, 200, 400, Color.white, "Para reiniciar o jogo pressione a tecla R.");
                writeText(gl, 200, 300, Color.white, "Bom jogo!");
            }
            if(menuPause){
                writeText(gl, 720, 1000, Color.RED, "DEAD CLUB CITY PONG");
                writeText(gl, 900, 1080/2, Color.WHITE, "PAUSE");
            }
        } else {
            menuOff = true;
        }

        if (turn) {
            ambientLights(gl);
            lightsOn(gl);
        }

        // Desenho do background
        // PREDIO 1
        gl.glColor4f(0, 1, 1, 0.5f);
        gl.glPushMatrix();
        gl.glTranslated(bg1Factor,-50,-700);
        gl.glRotated(25, 0, 1, 0);
        gl.glScaled(3, 8, 0.5);
        glut.glutSolidCube(100);
        gl.glPopMatrix();
        // PREDIO 2
        gl.glColor4f(0, 1, 1, 0.5f);
        gl.glPushMatrix();
        gl.glTranslated(bg2Factor,-300,-600);
        gl.glRotated(25, 0, 1, 0);
        gl.glScaled(3, 8, 0.5);
        glut.glutSolidCube(100);
        gl.glPopMatrix();
        // PREDIO 3
        gl.glColor4f(0, 1, 1, 0.5f);
        gl.glPushMatrix();
        gl.glTranslated(bg3Factor,-150,-500);
        gl.glRotated(25, 0, 1, 0);
        gl.glScaled(3, 8, 0.5);
        glut.glutSolidCube(100);
        gl.glPopMatrix();
        // PREDIO 4
        gl.glColor4f(0, 1, 1, 0.5f);
        gl.glPushMatrix();
        gl.glTranslated(bg4Factor,-200,-400);
        gl.glRotated(25, 0, 1, 0);
        gl.glScaled(3, 8, 0.5);
        glut.glutSolidCube(100);
        gl.glPopMatrix();
        // PREDIO 5
        gl.glColor4f(0, 1, 1, 0.5f);
        gl.glPushMatrix();
        gl.glTranslated(bg5Factor,-100,-300);
        gl.glRotated(25, 0, 1, 0);
        gl.glScaled(3, 8, 0.5);
        glut.glutSolidCube(100);
        gl.glPopMatrix();

        // Desenha a bola
        gl.glColor3f(1,0,0);
        gl.glPushMatrix();
        gl.glTranslated(bX, bY, 0);
        gl.glRotated(angle, 0, 0, 100);
        glut.glutSolidSphere(20, 20, 20);
        gl.glPopMatrix();

        // Desenha a barra
        gl.glColor3f(0,0,1);
        gl.glPushMatrix();
        gl.glTranslated(xFactor,-425,100);
        gl.glRotated(30, 1, 0, 0);
        gl.glScaled(3, 0.4, 0.5);
        glut.glutSolidCube(100);
        gl.glPopMatrix();

        if(boss){
            // Desenho do inimigo
            gl.glPushMatrix();
            gl.glTranslated(eXFactor,eYFactor,100);
            //gl.glRotated(30, 0, 0, 1);
            gl.glColor4f(0,1, 0, 0.5f);
            glut.glutSolidSphere(eRadius, 50, 50);
            gl.glPopMatrix();
            // Entrada do inimigo
            eYFactor-=eYSpeed;
            if(eYFactor<=yMax-200-eRadius){
                eYSpeed = 0;
            }
        }

        // Texto de GUI
        if(menuOff){
            writeText(gl, 100, 1030, Color.BLACK, "Score: " + score);
            writeText(gl, 600, 1030, Color.BLACK, "HP: " + hp);
            writeText(gl, 1000, 1030, Color.BLACK, "Fase: " + level);
        }

        // Texto de Derrota
        if(loss){
            writeText(gl, 800, (1000/2)+100, Color.RED, "Você perdeu!");
            writeText(gl, 700, 1000/2, Color.RED, "Pressione ESC para sair.");
            writeText(gl, 650, (1000/2)-100, Color.RED, "Pressione R para recomeçar.");
        }

        // Texto de Fase final
        if(finalLevel){
            writeText(gl, 800, (1000/2)+200, Color.RED, "Fase Final!");
        }

        gl.glFlush();

        // MECANICA DE JOGO

        // Animacao da bola
        bX += xSpeed;
        bY += ySpeed;
        angle++;

        // Fisica da bola
        if(bX+20>xMax || bX-20<xMin || bY-20<=-390 && bX+20==xFactor-150
                || bY-20<=-390 && bX-20==xFactor+150){
            xSpeed = -xSpeed;
        }
        if(bY+20>yMax){
            ySpeed = -ySpeed;
        }else if(bY>=-400 && bY-20<=-400 && bX>=xFactor-150 && bX<=xFactor+150 && level==1){
            if(xFactor>bX && xSpeed>0 && bX-20>xFactor-140){
                xSpeed = -random.nextFloat(10)+1;
            } else if (xFactor<bX && bX+20<xFactor+140 && xSpeed<0){
                xSpeed = random.nextFloat(10)+1;
            }
            ySpeed = random.nextFloat(10)+1;
        } else if(bY>=-400 && bY-20<=-400 && bX>=xFactor-150 && bX<=xFactor+150 && level==2){
            if(xFactor>bX && xSpeed>0 && bX-20>xFactor-140){
                xSpeed = -20;
            } else if (xFactor<bX && bX+20<xFactor+140 && xSpeed<0){
                xSpeed = 20;
            }
            ySpeed = random.nextFloat(15)+1;
        }else if (bY-20<yMin+10){
            if(hp > 1){
                hp--;
                if(level == 1){
                    bX=0;
                }else{
                    bX=350;
                }
                bY=0;
                ySpeed = -ySpeed;
                xSpeed = -xSpeed;
            } else{  // Derrota
                hp = 0;
                xSpeed = 0;
                ySpeed = 0;
                loss = true;
            }
        }

        // Contagem de pontos
        if(bY>=-400 && bY-20<=-400 && bX>=xFactor-150 && bX<=xFactor+150){
            score+=10;
        }
        // Controle de Level
        if(score>=200){
            finalLevel = score == 200;
            level = 2;
        }
        if(level == 1){
            bgSpeed = 10;
        } else if(level == 2){
            bgSpeed = 20;
        }

        // Inimigo
        if(level == 2){
            boss = true;
            // Fisica do Inimigo
            float distance = (float) Math.sqrt(Math.pow(bX - eXFactor, 2) + Math.pow(bY - eYFactor, 2));
            if(distance<20+eRadius){
                xSpeed=-xSpeed;
                ySpeed=-ySpeed;
            }

            // Saida do inimigo
            if(score>=400){
                eXFactor-=eXSpeed;
                if(eXFactor+eRadius+100<=xMin){
                    eXSpeed = 0;
                    boss = false;
                }
            }
        }

        // Desabilita a textura indicando o indice
        textura.desabilitarTextura(gl, 0);

        // BACKGROUND

        // Animacao dos predios
        bg1Factor-=bgSpeed;
        if(bg1Factor+150<xMin){
            bg1Factor=-bg1Factor;
        }
        bg2Factor-=bgSpeed;
        if(bg2Factor+150<xMin){
            bg2Factor=-bg2Factor;
        }
        bg3Factor-=bgSpeed;
        if(bg3Factor+150<xMin){
            bg3Factor=-bg3Factor;
        }
        bg4Factor-=bgSpeed;
        if(bg4Factor+150<xMin){
            bg4Factor=-bg4Factor;
        }
        bg5Factor-=bgSpeed;
        if(bg5Factor+150<xMin){
            bg5Factor=-bg5Factor;
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {    
        // Obtem o contexto grafico Opengl
        GL2 gl = drawable.getGL().getGL2();
        
        // Evita a divisao por zero
        if(height == 0) 
			height = 1;
                
        // Ativa a matriz de projecao
        gl.glMatrixMode(GL2.GL_PROJECTION);      
        gl.glLoadIdentity(); // Le a matriz identidade

        // Projecao ortogonal sem a correcao do aspecto
        gl.glOrtho(xMin, xMax, yMin, yMax, zMin, zMax);
        
        // Ativa a matriz de modelagem
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity(); // Le a matriz identidade
        System.out.println("Reshape: " + width + ", " + height);
    }    
       
    @Override
    public void dispose(GLAutoDrawable drawable) {}

}
