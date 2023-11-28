import java.awt.*; // Importa la biblioteca de Java para la interfaz gráfica de usuario (GUI)
import java.awt.image.BufferedImage; // Importa la clase BufferedImage para manejar imágenes
import java.io.File; // Importa la clase File para manejar archivos
import javax.imageio.ImageIO; // Importa la clase ImageIO para leer y escribir imágenes
import java.io.BufferedReader; // Importa la clase BufferedReader para leer texto de una entrada de caracteres
import java.io.FileReader; // Importa la clase FileReader para leer archivos de texto

// Define la clase laberinto
public class laberinto {
    private int fila = 0; // Contador para las filas
    private int columna = 0; // Contador para las columnas

    private final int numeroFilas = 25; // Número total de filas en el laberinto
    private final int numeroColumnas = 45; // Número total de columnas en el laberinto

    int escala = personaje.escala; // Escala de los personajes en el laberinto

    // Imágenes para los elementos del laberinto
    private BufferedImage muro;
    private BufferedImage pasillo;
    private BufferedImage dragon;

    // Constructor de la clase laberinto
    public laberinto() {
        try {
            // Lee y escala la imagen del muro
            Image originalImage = ImageIO.read(new File("muro.jpg"));
            Image scaledImage = originalImage.getScaledInstance(escala, escala, Image.SCALE_DEFAULT);
            muro = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = muro.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            // Lee y escala la imagen del pasillo
            originalImage = ImageIO.read(new File("pasillo.jpg"));
            scaledImage = originalImage.getScaledInstance(escala, escala, Image.SCALE_DEFAULT);
            pasillo = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            g2d = pasillo.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            // Lee y escala la imagen del dragón
            originalImage = ImageIO.read(new File("dragon.png"));
            scaledImage = originalImage.getScaledInstance(escala, escala, Image.SCALE_DEFAULT);
            dragon = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            g2d = dragon.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la pila de llamadas de la excepción
        }
    }

    // Método para pintar el laberinto
    public void paint (Graphics grafico){
        // Obtiene la matriz del laberinto
        int[][] laberinto = obtieneLaberinto();
        // Recorre cada celda de la matriz
        for (fila = 0; fila < numeroFilas; fila++){
            for (columna = 0; columna < numeroColumnas; columna++){
                // Si la celda es un muro, dibuja la imagen del muro
                if (laberinto[fila][columna] == 1){
                    grafico.drawImage(muro, columna * escala, fila * escala, null);  
                }
                // Si la celda es un pasillo, dibuja la imagen del pasillo
                else{
                    grafico.drawImage(pasillo, columna * escala, fila * escala, null);
                }
            }
        }

        // Dibuja la imagen del dragón en la posición especificada
        grafico.drawImage(dragon, (numeroColumnas - 2) * escala, (numeroFilas - 2) * escala, null);
    }

    // Método para obtener la matriz del laberinto desde un archivo de texto
    public int[][] obtieneLaberinto() {
        // Inicializa la matriz del laberinto
        int[][] laberinto = new int[numeroFilas][numeroColumnas];
    
        try {
            // Abre el archivo de texto
            BufferedReader br = new BufferedReader(new FileReader("grilla.txt"));
            String line;
    
            int fila = 0;
            // Lee cada línea del archivo de texto
            while ((line = br.readLine()) != null && fila < numeroFilas) {
                // Recorre cada caracter de la línea
                for (int columna = 0; columna < Math.min(line.length(), numeroColumnas); columna++) {
                    char c = line.charAt(columna);
                    // Convierte el caracter a un número y lo guarda en la matriz
                    laberinto[fila][columna] = Character.getNumericValue(c);
                }
                fila++;
            }
    
            br.close(); // Cierra el archivo de texto
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la pila de llamadas de la excepción
        }
    
        return laberinto; // Retorna la matriz del laberinto
    }
}
