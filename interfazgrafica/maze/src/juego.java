// Importamos las librerías necesarias
import java.awt.Graphics; // Importa la clase Graphics para dibujar en el panel
import java.awt.event.KeyEvent; // Importa la clase KeyEvent para manejar eventos de teclado
import java.awt.event.KeyListener; // Importa la clase KeyListener para escuchar eventos de teclado
import java.io.File; // Importa la clase File para manejar archivos
import java.io.IOException; // Importa la clase IOException para manejar excepciones de entrada/salida
import java.util.logging.Level; // Importa la clase Level para manejar niveles de logging
import java.util.logging.Logger; // Importa la clase Logger para hacer logging
import javax.sound.sampled.*; // Importa las clases para manejar audio
import javax.swing.JFrame; // Importa la clase JFrame para crear la ventana del juego
import javax.swing.JPanel; // Importa la clase JPanel para crear el panel del juego

// Definimos la clase juego que extiende de JPanel
public class juego extends JPanel{

    // Creamos los objetos laberinto y personaje
    laberinto laberinto = new laberinto(); // Crea una instancia de la clase laberinto
    personaje personaje = new personaje(); // Crea una instancia de la clase personaje
    private Clip clip; // Variable para almacenar el sonido

    // Constructor de la clase juego
    public juego(){
        // Añadimos un KeyListener para detectar las teclas presionadas
        addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {}

            // Cuando se presiona una tecla, llamamos al método teclaPresionada del objeto personaje
            @Override
            public void keyPressed(KeyEvent e) {
                personaje.teclaPresionada(e);
            }
            
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        setFocusable(true);

        // Intentamos cargar el archivo de audio
        try {
            File file = new File("mysteriousDungeon.wav"); // Cambia la ruta al archivo de tu música
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (LineUnavailableException | IOException | javax.sound.sampled.UnsupportedAudioFileException ex) {
            Logger.getLogger(juego.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Método para pintar los elementos en el panel
    @Override
    public void paint(Graphics grafico){
        laberinto.paint(grafico); // Pintamos el laberinto
        personaje.paint(grafico); // Pintamos el personaje
    }

    // Método principal
    public static void main(String[] args){

        // Creamos la ventana del juego
        JFrame miventana = new JFrame("Mi primer laberinto");
        juego game = new juego();
        miventana.add(game);

        // Configuramos la ventana
        miventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        miventana.setUndecorated(false);
        miventana.setVisible(true);

        miventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Reproducimos el sonido en bucle
        game.clip.loop(Clip.LOOP_CONTINUOUSLY);

        // Bucle principal del juego
        while(true){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            miventana.repaint(); // Repintamos la ventana
        }
    }
}
