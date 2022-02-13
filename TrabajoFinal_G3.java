package Trabajo_Final_FdP;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class TrabajoFinal_G3 {
    //Constantes públicas
    public static final int N = 4;
    public static final LocalTime HORAINGRESO = LocalTime.parse("08:00");
    public static final LocalTime HORASALIDA = LocalTime.parse("16:00");
    public static final int TIEMPOTRABAJO = 9;   //Tiempo de trabajo en minutos
    //Arreglos públicos
    public static String [] trabajadores = {"Nombre 1", "Nombre 2", "Nombre 3", "Nombre 4"}; //Nombre de los trabajadores
    public static int[] asistencia = {0,0,0,0}; //Días asistidos
    public static int[] tardanzas = {0,0,0,0};  //Tardanzas
    public static int[] minutosTotales = {0,0,0,0}; //Minutos totales laborados
    public static int[] horasExtras = {0,0,0,0};    //Horas Extra acumuladas
    public static LocalTime[] horaEntrada = new LocalTime[N];   //Hora de entrada del día
    public static LocalTime[] horaSalida = new LocalTime[N];    //Hora de salida del día

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
        for (int i = 0; i < N; i++) {
            String tiempoTrabajado = obtenerTiempoTrabajado(i);
            String horasExtraTotal = obtenerHorasExtraTotal(i);

            System.out.println("El trabajador " + trabajadores[i] + ":");
            System.out.println("- Tiene " + tiempoTrabajado + " horas de trabajo realizadas hasta la fecha.");
            System.out.println("- Ha asistido " + asistencia[i] + " días hasta ahora.");
            System.out.println("- Presenta " + tardanzas[i] + " tardanzas.");
            System.out.println("- Tiene acumuladas " + horasExtraTotal + " horas extra.");
            System.out.println("");
        }
    }

    private static void resumenTrabajador() {
        Scanner lector = new Scanner(System.in);
        String respuesta = "S";
        int indice = 0;

        do {
            do {
                System.out.println("Ingrese el nombre del trabajador del que desea obtener el resumen (caso contrario escriba SALIR): ");
                String nombre = lector.nextLine();

                if (nombre.equals("SALIR")) return; //Si el usuario escribe salir acaba el método

                indice = existeTrabajador(nombre);
            } while (indice < 0);    //Este bucle valida que el trabajador exista, si no existe vuelve a preguntar

            String tiempoTrabajado = obtenerTiempoTrabajado(indice);
            String horasExtraTotal = obtenerHorasExtraTotal(indice);

            System.out.println("El trabajador " + trabajadores[indice] + ":");
            System.out.println("- Tiene " + tiempoTrabajado + " horas de trabajo realizadas hasta la fecha.");
            System.out.println("- Ha asistido " + asistencia[indice] + " días hasta ahora.");
            System.out.println("- Presenta " + tardanzas[indice] + " tardanzas.");
            System.out.println("- Tiene acumuladas " + horasExtraTotal + " horas extra.");
            System.out.println("");

            System.out.println("");
            System.out.print("¿Desea ver el resumen de asistencia de otro trabajador? (S/N): ");
            respuesta = lector.nextLine().toUpperCase();
        }while (respuesta.equals("S"));
    }

    private static String obtenerHorasExtraTotal(int indice) {
        String horaExtraTotal = "00:00";
        if (horasExtras[indice] == 0) System.out.println("El trabajador no presenta registro de horas extra");
        else {
            int horas = horasExtras[indice] / 60;
            int minutos = horasExtras[indice] % 60;

            if (minutos < 10 && horas < 10) horaExtraTotal = "0" + horas + ":0" + minutos;
            else if (minutos < 10 && horas >= 10) horaExtraTotal = horas + ":0" + minutos;
            else if (minutos >= 10 && horas < 10) horaExtraTotal = "0" + horas + ":" + minutos;
            else if (minutos >= 10 && horas >= 10) horaExtraTotal = horas + ":" + minutos;
        }
        return horaExtraTotal;
    }

    private static String obtenerTiempoTrabajado(int indice) {
        String tiempoTrabajado = "00:00";
        if (minutosTotales[indice] == 0) System.out.println("El trabajador no presenta registro de tiempo trabajado");
        else {
            int horas = minutosTotales[indice] / 60;
            int minutos = minutosTotales[indice] % 60;

            if (minutos < 10 && horas < 10) tiempoTrabajado = "0" + horas + ":0" + minutos;
            else if (minutos < 10 && horas >= 10) tiempoTrabajado = horas + ":0" + minutos;
            else if (minutos >= 10 && horas < 10) tiempoTrabajado = "0" + horas + ":" + minutos;
            else if (minutos >= 10 && horas >= 10) tiempoTrabajado = horas + ":" + minutos;
        }
        return tiempoTrabajado;
    }

    private static void registrarSalida() {
        Scanner lector = new Scanner(System.in);
        String respuesta = "S";
        int indice = 0;

        do {
            do {
                System.out.println("Ingrese el nombre del trabajador para generar su salida (caso contrario escriba SALIR): ");
                String nombre = lector.nextLine();

                if (nombre.equals("SALIR")) return; //Si el usuario escribe salir acaba el método

                indice = existeTrabajador(nombre);
            } while (indice < 0);    //Este bucle valida que el trabajador exista, si no existe vuelve a preguntar

            horaSalida[indice] = capturarHora();    //Registra la hora de salida

            if (horaEntrada[indice] == null) System.out.println("El trabajador aún no registra un ingreso.");
            else {
                String horasTrabajadas = obtenerHorasTrabajadasDia(indice);
                minutosTotales[indice] += ChronoUnit.MINUTES.between(horaEntrada[indice], horaSalida[indice]); //Contabiliza los minutos totales trabajados

                String horasExtraDia = obtenerHorasExtraDia(indice);

                System.out.println("Hoy trabajó: " + horasTrabajadas + " horas.");
                System.out.println("Lleva acumuladas: " + obtenerTiempoTrabajado(indice) + " horas hasta la fecha.");
                System.out.println("Horas extra del día: " + horasExtraDia);
            }

            System.out.println("");
            System.out.print("¿Desea registrar la salida de otro trabajador? (S/N): ");
            respuesta = lector.nextLine().toUpperCase();
        }while (respuesta.equals("S"));
    }

    private static String obtenerHorasTrabajadasDia(int indice) {
        String horasTrabajadasDia = "00:00";
        int minutosTrabajados = 0;
        minutosTrabajados += ChronoUnit.MINUTES.between(horaEntrada[indice], horaSalida[indice]);
        if (minutosTrabajados == 0) System.out.println("El trabajador no presenta registro de tiempo trabajado");
        else {
            int horas = minutosTrabajados / 60;
            int minutos = minutosTrabajados % 60;

            if (minutos < 10 && horas < 10) horasTrabajadasDia = "0" + horas + ":0" + minutos;
            else if (minutos < 10 && horas >= 10) horasTrabajadasDia = horas + ":0" + minutos;
            else if (minutos >= 10 && horas < 10) horasTrabajadasDia = "0" + horas + ":" + minutos;
            else if (minutos >= 10 && horas >= 10) horasTrabajadasDia = horas + ":" + minutos;
        }
        return horasTrabajadasDia;
    }

    private static String obtenerHorasExtraDia(int indice) {
        String horasExtraDia = "00:00";
        System.out.println("Hora: " + horaSalida[indice]);
        int minutosTrabajados = 0;
        minutosTrabajados += ChronoUnit.MINUTES.between(horaEntrada[indice],horaSalida[indice]);
        if (minutosTrabajados > (TIEMPOTRABAJO)) {
            horasExtras[indice] += ChronoUnit.MINUTES.between(HORASALIDA,horaSalida[indice]);
            if (horasExtras[indice] == 0) System.out.println("El trabajador no presenta registro de horas extra");
            else {
                int horas = horasExtras[indice] / 60;
                int minutos = horasExtras[indice] % 60;

                if (minutos < 10 && horas < 10) horasExtraDia = "0" + horas + ":0" + minutos;
                else if (minutos < 10 && horas >= 10) horasExtraDia = horas + ":0" + minutos;
                else if (minutos >= 10 && horas < 10) horasExtraDia = "0" + horas + ":" + minutos;
                else if (minutos >= 10 && horas >= 10) horasExtraDia = horas + ":" + minutos;
            }
        }
        return horasExtraDia;
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
        System.out.println("Hora: " + horaEntrada[indice]);
        if (horaEntrada[indice].isAfter(HORAINGRESO)) {
            tardanzas[indice]++;
        }
    }

    private static LocalTime capturarHora() {
        int hora, minuto;
        Calendar calendario = new GregorianCalendar();

        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minuto = calendario.get(Calendar.MINUTE);

        String horaIngreso = hora + ":" + minuto;
        return LocalTime.parse(horaIngreso);
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
