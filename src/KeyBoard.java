import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
/**
 *
 * @author ExceedEdits
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
            case KeyEvent.VK_LEFT: // Move a barra para a esquerda
                if(cena.xFactor-20 > -100){
                    cena.xFactor -= 10;
                }
                break;
            case KeyEvent.VK_RIGHT: // Move a barra para a direita
                if(cena.xFactor+20 < 100){
                    cena.xFactor += 10;
                }
                break;
            case KeyEvent.VK_R: // Reinicia o jogo
                cena.hp=3;
                cena.score=0;
                cena.bX=0;
                cena.bY=0;
                cena.xSpeed = 2;
                cena.ySpeed = 1;
                cena.loss = false;
                break;
            case KeyEvent.VK_ESCAPE: // Fecha o jogo
                System.exit(0);
                break;
        }
    }

    public void keyReleased(KeyEvent e) {}

}
