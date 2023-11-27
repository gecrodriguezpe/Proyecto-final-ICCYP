import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

import java.awt.event.KeyEvent;

public class personaje {
    laberinto lab = new laberinto();
    
    public static int escala = 30;
    private int x = escala;
    private int y = escala;
    private final int movimiento = escala;
    private BufferedImage caballeroderecha;
    private BufferedImage caballeroizquierda;
    private boolean reflejo = false;
    private Clip caminar;

    public personaje() {
        try {
            Image originalImage = ImageIO.read(new File("caballeroderecha.png"));
            Image scaledImage = originalImage.getScaledInstance(escala  , escala, Image.SCALE_DEFAULT);
            caballeroderecha = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = caballeroderecha.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            originalImage = ImageIO.read(new File("caballeroizquierda.png"));
            scaledImage = originalImage.getScaledInstance(escala, escala, Image.SCALE_DEFAULT);
            caballeroizquierda = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            g2d = caballeroizquierda.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("pasto.wav"));
            caminar = AudioSystem.getClip();
            caminar.open(audioInputStream);

            caminar.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        caminar.setFramePosition(0);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void paint(Graphics grafico){
        if (reflejo) {
            grafico.drawImage(caballeroizquierda, x, y, null);
        } else {
            grafico.drawImage(caballeroderecha, x, y, null);
        }
    }

    public void teclaPresionada(KeyEvent evento){
        int[][] laberinto = lab.obtieneLaberinto();

        if (evento.getKeyCode() == 37){ //Tecla izquierda
            if (laberinto[y/escala][(x/escala) - 1] != 1){
                x = x - movimiento;
                reflejo = true;
                reproducirSonido();
            }    
        }
        if (evento.getKeyCode() == 39){ //Tecla derecha
            if (laberinto[y/escala][(x/escala) + 1] != 1){
                x = x + movimiento;
                reflejo = false;
                reproducirSonido();
            }  
        }
        if (evento.getKeyCode() == 40){ //Tecla abajo
            if (laberinto[(y/escala) + 1][x/escala] != 1){
                y = y + movimiento;
                reproducirSonido();
            }              
        }
        if (evento.getKeyCode() == 38){ //Tecla arriba
            if (laberinto[(y/escala) - 1][x/escala] != 1){
                y = y - movimiento;
                reproducirSonido();
            }  
        }
    }
    private void reproducirSonido() {
        try {
            caminar.stop();
            caminar.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
}
