package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.Torpedo;
import model.Shooter;

public class KeyController implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
        Shooter shooter = (Shooter) Main.gameData.friendFigures.get(0);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                shooter.translate(-15, 0);
                break;
            case KeyEvent.VK_RIGHT:
                shooter.translate(15, 0);
                break;
            case KeyEvent.VK_UP:
                shooter.translate(0, -15);
                break;
            case KeyEvent.VK_DOWN:
                shooter.translate(0, 15);
                break;
            case KeyEvent.VK_SPACE:
                Shooter shooty = (Shooter) Main.gameData.friendFigures.get(0);

            Torpedo m = new Torpedo(
                shooter.getXofMissileShoot(),
                shooter.getYofMissileShoot());

            Main.gameData.friendFigures.add(m);
            break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
