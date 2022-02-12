package Trabajo_Final_FdP;

import java.time.LocalTime;
import java.util.Scanner;

public class TrabajoFinal_G3 {
    //Constantes públicas
    public static final int N = 4;
    public static final LocalTime HORAINGRESO = LocalTime.parse("08:00");
    public static final LocalTime HORASALIDA = LocalTime.parse("16:00");
    //Arreglos públicos
    public static String [] trabajadores = {"Nombre 1", "Nombre 2", "Nombre 3", "Nombre 4"};
    public static int[] asistencia = {0,0,0,0};
    public static int[] tardanzas = {0,0,0,0};
    public static int[] minutosTotales = {0,0,0,0};
    public static LocalTime[] horaEntrada = new LocalTime[N];
    public static LocalTime[] horaSalida = new LocalTime[N];

    public static void main(String[] args) {
        Scanner lector = new Scanner(System.in);
        String seleccion;

        construirInterface();   //Construyendo interfase de usuario

        while (true){   //Ciclo infinito para que no se pierdan los datos registrados
            System.out.print("Ingrese el número de la opción a realizar: ");
            seleccion = lector.next();

            switch (seleccion){
                case "1": registrarEntrada(); break;
                case "2": registrarSalida(); break;
                case "3": resumenTrabajador(); break;
                case "4": resumenGeneral(); break;
                default:
                    System.out.println("Ingrese una opción correcta.");
            }
        }
    }

    private static void resumenGeneral() {
        System.out.println("Resumen General");
    }

    private static void resumenTrabajador() {
        System.out.println("Resumen Trabajador");
    }

    private static void registrarSalida() {
        System.out.println("Registrar Salida");
    }

    private static void registrarEntrada() {
        Scanner lector = new Scanner(System.in);
        String respuesta = "S";
        int indice = 0;

        do {
            do {
                System.out.println("Ingrese el nombre del trabajador para generar su asistencia (caso contrario escriba SALIR): ");
                String nombre = lector.nextLine();

                if (nombre.equals("SALIR")) return; //Si el usuario escribe salir acaba el método

                indice = existeTrabajador(nombre);
            } while (indice < 0);    //Este bucle valida que el trabajador exista, si no existe vuelve a preguntar

            asistencia[indice]++;

            registrarTardanza(indice);
            System.out.println(trabajadores[indice] + ": Asistencias: " + asistencia[indice] + ". Tardanzas: " + tardanzas[indice] + ".");

            System.out.println("");
            System.out.print("¿Desea registrar la asistencia de otro trabajador? (S/N): ");
            respuesta = lector.nextLine().toUpperCase();
        }while (respuesta.equals("S"));
    }

    private static void registrarTardanza(int indice) {
        horaEntrada[indice] = capturarHora();
        if (horaEntrada[indice].isAfter(HORAINGRESO)) {
            tardanzas[indice]++;
        }
    }

    private static LocalTime capturarHora() {
        return LocalTime.parse("08:15");
    }

    private static int existeTrabajador(String nombre) {
        int indice = 0;

        for (indice = N-1; indice >= 0; indice--) {
            if (trabajadores[indice].equals(nombre)) return indice; //Si el nombre del trabajador está en el arreglo devuelve el índice del arreglo
        }

        return indice;  //Si el nombre del trabajador no está en el arreglo devuelve -1
    }

    private static void construirInterface() {
        System.out.println("----------------------------------------------");
        System.out.println("------- TOMA DE ASISTENCIA DE PERSONAL -------");
        System.out.println("----------------------------------------------");
        System.out.println("- Acciones de asistencia disponibles:        -");
        System.out.println("-  1. Registrar entrada                      -");
        System.out.println("-  2. Registrar salida                       -");
        System.out.println("-  3. Visualizar resumen por trabajador      -");
        System.out.println("-  4. Resumen general                        -");
        System.out.println("----------------------------------------------");
        System.out.println("");
    }
}
