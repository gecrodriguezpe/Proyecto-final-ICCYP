import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.FileReader;


public class laberinto {
    private int fila = 0; //Contador
    private int columna = 0; //Contador

    private final int numeroFilas = 25;
    private final int numeroColumnas = 46;

    int escala = personaje.escala;

    private BufferedImage muro;
    private BufferedImage pasillo;
    private BufferedImage dragon;

    public laberinto() {
        try {
            Image originalImage = ImageIO.read(new File("muro.jpg"));
            Image scaledImage = originalImage.getScaledInstance(escala, escala, Image.SCALE_DEFAULT);
            muro = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = muro.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            originalImage = ImageIO.read(new File("pasillo.jpg"));
            scaledImage = originalImage.getScaledInstance(escala, escala, Image.SCALE_DEFAULT);
            pasillo = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            g2d = pasillo.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            originalImage = ImageIO.read(new File("dragon.png"));
            scaledImage = originalImage.getScaledInstance(escala, escala, Image.SCALE_DEFAULT);
            dragon = new BufferedImage(scaledImage.getWidth(null), scaledImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            g2d = dragon.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void paint (Graphics grafico){

        int[][] laberinto = obtieneLaberinto();
        for (fila = 0; fila < numeroFilas; fila++){
            for (columna = 0; columna < numeroColumnas; columna++){
                if (laberinto[fila][columna] == 1){
                    grafico.drawImage(muro, columna * escala, fila * escala, null);  
                }
                else{
                    grafico.drawImage(pasillo, columna * escala, fila * escala, null);
                }
            }
        }

        grafico.drawImage(dragon, (numeroColumnas - 2) * escala, (numeroFilas - 2) * escala, null);
    }

    public int[][] obtieneLaberinto() {
        int[][] laberinto = new int[numeroFilas][numeroColumnas];
    
        try {
            BufferedReader br = new BufferedReader(new FileReader("grilla.txt"));
            String line;
    
            int fila = 0;
            while ((line = br.readLine()) != null && fila < numeroFilas) {
                for (int columna = 0; columna < Math.min(line.length(), numeroColumnas); columna++) {
                    char c = line.charAt(columna);
                    laberinto[fila][columna] = Character.getNumericValue(c);
                }
                fila++;
            }
    
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        return laberinto;
    }
}
