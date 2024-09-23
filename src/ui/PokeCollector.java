package ui;

import java.util.Scanner;
import model.Carta;

public class PokeCollector {

    public static Carta[] memoria;
    public static Scanner reader;

    public static void main(String[] args) {
        inicializarGlobales();
        menu();
    }

    /**
     * Descripcion: Este metodo se encarga de iniciar las variables globales
     * pre: El Scanner reader debe estar declarado
     * pos: El Scanner reader queda inicializado
    */
    public static void inicializarGlobales() {
        reader = new Scanner(System.in);
        memoria = new Carta[50];
    }

    /**
     * Descripcion: Este metodo se encarga de desplegar el menu al usuario y mostrar los mensajes de resultado para cada funcionalidad
     * pre: El Scanner reader debe estar inicializado
    */
    public static void menu() {
        System.out.println("Bienvenido a PokeCollector!");

        boolean salir = false;

        do {
            System.out.println("\nMenu Principal:");
            System.out.println("1. Registrar una Carta (Create)");
            System.out.println("2. Consultar una Carta (Read)");
            System.out.println("3. Modificar una Carta (Update)");
            System.out.println("4. Eliminar una Carta (Delete)");
            System.out.println("0. Salir");
            System.out.println("\nDigite la opcion a ejecutar");
            int opcion = reader.nextInt();

            switch (opcion) {
                case 1:
                    solicitarDatosCarta();
                    break;
                case 2:
                    consultarCarta();
                    break;
                case 3:
                    modificarCarta();
                    break;
                case 4:
                    eliminarCarta();
                    break;
                case 0:
                    System.out.println("\nGracias por usar nuestros servicios!");
                    salir = true;
                    reader.close();
                    break;

                default:
                    System.out.println("\nOpción inválida!");
                    break;
            }

        } while (!salir);

    }

    
    /**
     * Descripcion: Este metodo se encarga de verificar si existe una carta con el mismo ID
     * pre: El arreglo de cartas memoria debe estar inicializado
     * pos: retorna true si el ID ya existe, de lo contrario retorna false
     * @param id el id de la carta a buscar
     * @return true si el ID ya existe, de lo contrario retorna false
     */
    public static boolean validarId(String id) {
        for (Carta carta : memoria) {
            if (carta != null && carta.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Descripcion: Este metodo se encarga de solicitar los datos de la carta al usuario
     * pre: El Scanner reader debe estar inicializado
     * pos: Se crea una nueva instancia de la clase Carta
     */
    public static void solicitarDatosCarta() {
        reader.nextLine(); // Limpieza de buffer

        String id;
        do {
            System.out.println("Ingrese el ID de la carta (recuerde que el ID debe ser único):");
            id = reader.nextLine();

            if (validarId(id)) {
                System.out.println("Error: Ya existe una carta con ese ID. Intente nuevamente.");
            }
        } while (validarId(id)); 

        System.out.println("Ingrese el nombre de la carta");
        String nombre = reader.nextLine();

        System.out.println("Ingrese los puntos de vida de la carta");
        int puntosVida = reader.nextInt();

        reader.nextLine(); // Limpieza de buffer
        System.out.println("Ingrese el tipo de pokemon en la carta (Agua, Fuego, Viento, etc...):");
        String tipo = reader.nextLine();

        System.out.println("Ingrese la rareza de la carta (Basico, Raro, Mitico o Legendario):");
        String rareza = reader.nextLine();

        boolean respuesta = guardarCartaEnMemoria(id, nombre, puntosVida, tipo, rareza);
        if (respuesta) {
            System.out.println("La carta se registró exitosamente.");
        } else {
            System.out.println("Error 501: La carta no se pudo registrar (memoria llena).");
        }
    }

    /**
     * Descripcion: Este metodo se encarga de guardar la carta en la memoria
     * pre: El arreglo memoria debe estar inicializado
     * pos: La carta se guarda en la memoria
     * @param id el id de la carta
     * @param nombre el nombre de la carta
     * @param puntosVida los puntos de vida de la carta
     * @param tipo el tipo de la carta
     * @param rareza la rareza de la carta
     * @return true si la carta se guardo correctamente, de lo contrario retorna false
     */
    public static boolean guardarCartaEnMemoria(String id, String nombre, int puntosVida, String tipo, String rareza) {
        Carta nuevaCarta = new Carta(id, nombre, puntosVida, tipo, rareza);

        for (int i = 0; i < memoria.length; i++) {
            if (memoria[i] == null) {
                memoria[i] = nuevaCarta;
                return true;
            }
        }
        return false;
    }

    /**
     * Descripcion: Este metodo se encarga de ordenar las cartas por HP
     * pre: El arreglo memoria debe estar inicializado
     * pos: El arreglo memoria queda ordenado por HP
     */
    public static void ordenarCartasporHP() {
        for (int i = 0; i < memoria.length - 1; i++) {
            for (int j = 0; j < memoria.length - i - 1; j++) {
                if (memoria[j] != null && memoria[j + 1] != null && memoria[j].getPuntosVida() < memoria[j + 1].getPuntosVida()) {
                    
                    Carta temp = memoria[j];
                    memoria[j] = memoria[j + 1];
                    memoria[j + 1] = temp;
                }
            }
        }
        System.out.println("Cartas ordenadas por HP.");
    }
    
    /**
     * Descripcion: Este metodo se encarga de consultar una carta en la memoria
     * pre: El arreglo memoria debe estar inicializado
     * pos: Se muestra la informacion de la carta consultada
     */
    public static void consultarCarta() {
        ordenarCartasporHP();
        reader.nextLine(); // Limpieza de buffer
        System.out.println("|Id||Nombre||Puntos de vida||Tipo|");
        System.out.println(construirListaDeCartas());

        System.out.println("Ingrese el ID de la carta a consultar:");
        String idAConsultar = reader.nextLine();

        Carta consulta = buscarCarta(idAConsultar);

        if (consulta == null) {
            System.out.println("Error 404: No existe una carta con ese ID.");
        } else {
            System.out.println("La carta es: " + consulta.getNombre() + " - " + consulta.getPuntosVida() + " - " + consulta.getTipo());
        }
    }

    /**
     * Descripcion: Este metodo se encarga de buscar una carta en la memoria
     * pre: El arreglo memoria debe estar inicializado
     * pos: Retorna la carta si la encuentra, de lo contrario retorna null
     * @param idAConsultar el id de la carta a buscar
     * @return la carta si la encuentra, de lo contrario retorna null
     */
    public static Carta buscarCarta(String idAConsultar) {
        for (Carta carta : memoria) {
            if (carta != null && carta.getId().equals(idAConsultar)) {
                return carta;
            }
        }
        return null;
    }

    /**
     * Descripcion: Este metodo se encarga de construir una lista con las cartas de la memoria
     * pre: El arreglo memoria debe estar inicializado
     * pos: Retorna una cadena con la lista de cartas
     * @return una cadena con la lista de cartas
     */
    public static String construirListaDeCartas() {
        String lista = "";

        for (Carta carta : memoria) {
            if (carta != null) {
                lista += "\n" + "|" + carta.getId() + " | " + carta.getNombre() + " | " + carta.getPuntosVida() + " | " + carta.getTipo() + "|";
            }
        }

        return lista;
    }

    /**
     * Descripcion: Este metodo se encarga de modificar una carta en la memoria
     * pre: El arreglo memoria debe estar inicializado
     * pos: La carta se modifica en la memoria
     */
    public static void modificarCarta() {
        reader.nextLine(); // Limpieza del buffer

        System.out.println("|Id||Nombre||Puntos de vida||Tipo|");
        System.out.println(construirListaDeCartas() + "\n");
        System.out.println("Ingrese el ID de la carta a modificar:");
        String idAModificar = reader.nextLine();

        Carta consulta = buscarCarta(idAModificar);

        if (consulta == null) {
            System.out.println("Error 404: No existe una carta con ese ID.");
        } else {
            System.out.println("Ingrese el nuevo nombre de la carta:");
            String nuevoNombre = reader.nextLine();

            System.out.println("Ingrese el nuevo tipo de pokemon en la carta (Agua, Fuego, Viento, etc.):");
            String nuevoTipo = reader.nextLine();

            System.out.println("Ingrese la nueva cantidad de puntos de vida del pokemon:");
            int nuevoPuntosVida = reader.nextInt();
            reader.nextLine(); // Limpieza del buffer

            consulta.setNombre(nuevoNombre);
            consulta.setTipo(nuevoTipo);
            consulta.setPuntosVida(nuevoPuntosVida);

            System.out.println("La carta ha sido modificada exitosamente.");
        }
    }

    /**
     * Descripcion: Este metodo se encarga de eliminar una carta en la memoria
     * pre: El arreglo memoria debe estar inicializado
     * pos: La carta se elimina de la memoria
     */
    public static void eliminarCarta() {
        reader.nextLine(); // Limpieza de buffer
        System.out.println("Ingrese el ID de la carta a eliminar:");
        String deleteID = reader.nextLine();

        for (int l = 0; l < memoria.length; l++) {
            if (memoria[l] != null && memoria[l].getId().equals(deleteID)) {
                memoria[l] = null;
                System.out.println("La carta ha sido eliminada exitosamente.");
                return; 
            }
        }

        System.out.println("Error 404: No existe una carta con ese ID.");
    }

} 
