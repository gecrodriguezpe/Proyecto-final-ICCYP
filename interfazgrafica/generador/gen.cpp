#include <iostream>
#include <fstream> // Para la clase ofstream
#include <cstdlib> // Para la función rand()
#include <ctime>   // Para la función time()

int main() {
    // Inicializar la semilla del generador de números aleatorios
    srand(static_cast<unsigned>(time(0)));

    // Dimensiones de la grilla
    const int filas = 25;
    const int columnas = 46;

    // Generar la grilla de 1 y 0
    int grilla[filas][columnas];

    for (int fila = 0; fila < filas; ++fila) {
        for (int columna = 0; columna < columnas; ++columna) {
            // Generar 0 o 1 aleatoriamente
            grilla[fila][columna] = rand() % 2;
        }
    }

    // Definir la ruta completa del archivo de texto
    std::string ruta_completa = "../maze/src/grilla.txt";

    // Crear el archivo de texto en la ruta especificada
    std::ofstream archivo(ruta_completa);

    // Redirigir la salida estándar a un archivo de texto
    std::streambuf* respaldo_cout = std::cout.rdbuf();
    std::cout.rdbuf(archivo.rdbuf());

    // Imprimir la grilla
    for (int fila = 0; fila < filas; ++fila) {
        for (int columna = 0; columna < columnas; ++columna) {
            std::cout << grilla[fila][columna];
        }
        std::cout << std::endl;
    }

    // Restaurar la salida estándar
    std::cout.rdbuf(respaldo_cout);

    //std::system("java -cp ../maze/src juego");

    return 0;
}
