import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GL2;

import java.util.Random;

/**
 *
 * @author ExceedEdits & Izabelle
 */
public class KeyBoard implements KeyListener{
    private Cena cena;
    
    public KeyBoard(Cena cena){
        this.cena = cena;
    }

    // Habilita os comandos do teclado
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed: " + e.getKeyCode()+" "+e.getKeyChar());
        switch (e.getKeyCode()){
            case KeyEvent.VK_C: // Comeca o jogo
                cena.menuOn = false;
                break;
            case KeyEvent.VK_S:
                cena.reset();
                break;
            case KeyEvent.VK_LEFT: // Move a barra para a esquerda
                if(cena.xFactor-150 > -800 && cena.bY>=-400){
                    cena.xFactor -= 50;
                }
                break;
            case KeyEvent.VK_RIGHT: // Move a barra para a direita
                if(cena.xFactor+150 < 800 && cena.bY>=-400){
                    cena.xFactor += 50;
                }
                break;
            case KeyEvent.VK_R: // Reinicia o jogo
                cena.hp=3;
                cena.score=0;
                if(cena.level==1){
                    cena.bX=0;
                }else{
                    cena.bX=350;
                }
                cena.bY=0;
                cena.bgSpeed=10;
                if(cena.xSpeed>0){
                    cena.xSpeed = -10;
                } else{
                    cena.xSpeed = 10;
                }
                cena.ySpeed = 5;
                cena.loss = false;
                cena.boss = false;
                cena.finalLevel = false;
                break;
            case KeyEvent.VK_BACK_SPACE: // Pausa o jogo
                if(!cena.menuOn){
                    if(cena.xSpeed!=0 && cena.ySpeed!=0){
                        cena.xSpeed=0;
                        cena.ySpeed=0;
                        cena.bgSpeed=0;
                    }else{
                        if(cena.level==1){
                            cena.xSpeed=10;
                            cena.ySpeed=10;
                        }else if(cena.level==2){
                            cena.xSpeed=15;
                            cena.ySpeed=10;
                        }
                    }
                    cena.menuOff=!cena.menuOff;
                    cena.menuPause=!cena.menuPause;
                    cena.boss = false;
                    cena.finalLevel = false;
                    break;
                } else {
                    break;
                }
            case KeyEvent.VK_ESCAPE: // Fecha o jogo
                System.exit(0);
                break;
        }
    }

    public void keyReleased(KeyEvent e) {}

}
