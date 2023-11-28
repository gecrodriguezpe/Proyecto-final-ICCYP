import java.awt.*; // Importa la biblioteca de Java para la interfaz gráfica de usuario (GUI)
import java.awt.image.BufferedImage; // Importa la clase BufferedImage para manejar imágenes
import java.io.File; // Importa la clase File para manejar archivos
import javax.imageio.ImageIO; // Importa la clase ImageIO para leer y escribir imágenes
import javax.sound.sampled.*; // Importa las clases para manejar audio
import java.awt.event.KeyEvent; // Importa la clase KeyEvent para manejar eventos de teclado

// Define la clase personaje
public class personaje {
    laberinto lab = new laberinto(); // Crea una instancia de la clase laberinto
    
    public static int escala = 30; // Define la escala del personaje
    private int x = escala; // Define la posición inicial del personaje en el eje x
    private int y = escala; // Define la posición inicial del personaje en el eje y
    private final int movimiento = escala; // Define la cantidad de movimiento del personaje
    private BufferedImage caballeroderecha; // Define la imagen del personaje mirando a la derecha
    private BufferedImage caballeroizquierda; // Define la imagen del personaje mirando a la izquierda
    private boolean reflejo = false; // Define si el personaje está mirando a la izquierda
    private Clip caminar; // Define el sonido de caminar

    // Constructor de la clase personaje
    public personaje() {
        try {
            // Lee y escala la imagen del personaje mirando a la derecha
            Image originalImage = ImageIO.read(new File("caballeroderecha.png"));
            Image scaledImage = originalImage.getScaledInstance(escala  , escala, Image.SCALE_DEFAULT);
            caballeroderecha = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = caballeroderecha.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            // Lee y escala la imagen del personaje mirando a la izquierda
            originalImage = ImageIO.read(new File("caballeroizquierda.png"));
            scaledImage = originalImage.getScaledInstance(escala, escala, Image.SCALE_DEFAULT);
            caballeroizquierda = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            g2d = caballeroizquierda.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            // Lee el sonido de caminar
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("pasto.wav"));
            caminar = AudioSystem.getClip();
            caminar.open(audioInputStream);

            // Añade un listener al sonido de caminar para reiniciar el sonido cuando se detenga
            caminar.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        caminar.setFramePosition(0);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la pila de llamadas de la excepción
        }
    }
    
    // Método para pintar el personaje
    public void paint(Graphics grafico){
        if (reflejo) {
            grafico.drawImage(caballeroizquierda, x, y, null); // Dibuja el personaje mirando a la izquierda
        } else {
            grafico.drawImage(caballeroderecha, x, y, null); // Dibuja el personaje mirando a la derecha
        }
    }

    // Método para manejar los eventos de teclado
    public void teclaPresionada(KeyEvent evento){
        int[][] laberinto = lab.obtieneLaberinto(); // Obtiene la matriz del laberinto

        // Si se presiona la tecla izquierda
        if (evento.getKeyCode() == 37){
            // Si la celda a la izquierda no es un muro
            if (laberinto[y/escala][(x/escala) - 1] != 1){
                x = x - movimiento; // Mueve el personaje a la izquierda
                reflejo = true; // Cambia la dirección del personaje a la izquierda
                reproducirSonido(); // Reproduce el sonido de caminar
            }    
        }
        // Si se presiona la tecla derecha
        if (evento.getKeyCode() == 39){
            // Si la celda a la derecha no es un muro
            if (laberinto[y/escala][(x/escala) + 1] != 1){
                x = x + movimiento; // Mueve el personaje a la derecha
                reflejo = false; // Cambia la dirección del personaje a la derecha
                reproducirSonido(); // Reproduce el sonido de caminar
            }  
        }
        // Si se presiona la tecla abajo
        if (evento.getKeyCode() == 40){
            // Si la celda abajo no es un muro
            if (laberinto[(y/escala) + 1][x/escala] != 1){
                y = y + movimiento; // Mueve el personaje hacia abajo
                reproducirSonido(); // Reproduce el sonido de caminar
            }              
        }
        // Si se presiona la tecla arriba
        if (evento.getKeyCode() == 38){
            // Si la celda arriba no es un muro
            if (laberinto[(y/escala) - 1][x/escala] != 1){
                y = y - movimiento; // Mueve el personaje hacia arriba
                reproducirSonido(); // Reproduce el sonido de caminar
            }  
        }
    }
    // Método para reproducir el sonido de caminar
    private void reproducirSonido() {
        try {
            caminar.stop(); // Detiene el sonido de caminar
            caminar.start(); // Inicia el sonido de caminar
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la pila de llamadas de la excepción
        }
    }    
}
