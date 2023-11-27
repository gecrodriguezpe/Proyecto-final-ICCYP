import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class juego extends JPanel{

    laberinto laberinto = new laberinto();
    personaje personaje = new personaje();
    private Clip clip;

    public juego(){
        addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                personaje.teclaPresionada(e);
            }
            
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        setFocusable(true);

        try {
            File file = new File("mysteriousDungeon.wav"); // Cambia la ruta al archivo de tu música
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (LineUnavailableException | IOException | javax.sound.sampled.UnsupportedAudioFileException ex) {
            Logger.getLogger(juego.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  

    @Override
    public void paint(Graphics grafico){
        laberinto.paint(grafico);
        personaje.paint(grafico);
    }

    public static void main(String[] args){


        JFrame miventana = new JFrame("Mi primer laberinto");
        juego game = new juego();
        miventana.add(game);

        miventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        miventana.setUndecorated(false);
        miventana.setVisible(true);

        miventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game.clip.loop(Clip.LOOP_CONTINUOUSLY);

        while(true){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            miventana.repaint();
        }
    }
}