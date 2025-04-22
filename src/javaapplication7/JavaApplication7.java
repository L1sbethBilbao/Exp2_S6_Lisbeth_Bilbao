package javaapplication7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class JavaApplication7 {

    static Scanner sc = new Scanner(System.in);

    static final int CANTIDAD_ENTRADAS = 5;
    static boolean seguir = true;
    static int entradasVipDisponibles = CANTIDAD_ENTRADAS;
    static int entradasPlateaBajaDisponibles = CANTIDAD_ENTRADAS;
    static int entradasPlateaAltaDisponibles = CANTIDAD_ENTRADAS;
    static int entradasPalcoDisponibles = CANTIDAD_ENTRADAS;

    static double descuento;
    static double iva = 0;
    static String nombre_entrada = "";
    static String nombre_tarifa = "";
    static double precio = 0;
    static double subtotal_con_descuento;
    static int tipo_entrada = 0;
    static int numeroAsientoComprado = 0;
    static int tipo_tarifa;
    static double total = 0;
    static int edad = 0;

    static String[] asientosVIP = {"D", "D", "D", "D", "D"};
    static String[] asientosPlateaBaja = {"D", "D", "D", "D", "D"};
    static String[] asientosPlateaAlta = {"D", "D", "D", "D", "D"};
    static String[] asientosPalco = {"D", "D", "D", "D", "D"};

    static String[] reservaClienteVIP = new String[5];
    static String[] reservaClientePlateaBaja = new String[5];
    static String[] reservaClientePlateaAlta = new String[5];
    static String[] reservaClientePalco = new String[5];

    static long[] tiemposReservaVIP = new long[5];
    static long[] tiemposReservaPlateaBaja = new long[5];
    static long[] tiemposReservaPlateaAlta = new long[5];
    static long[] tiemposReservaPalco = new long[5];

    static final long DURACION_RESERVA_MS = 10000;// PARA MODIFICAR EL TIEMPO DE RESERVA , AGHORA ESTA EN 10 SEGUNDOS => 10000 MILISEGUNDOS
    static String[] tipoEntradaComprada = new String[20];
    static String[] tarifaEntradaComprada = new String[20];
    static int totalEntradasCompradas = 0;
    static List<Double> acumuladorTotales = new ArrayList<>();

    static List<Integer> ubicacionAsiento = new ArrayList<>();

    public static final String RESET = "\u001B[0m";
    public static final String ROJO = "\u001B[31m";
    public static final String VERDE = "\u001B[32m";
    public static final String AMARILLO = "\u001B[33m";
    public static final String AZUL = "\u001B[34m";

    public static void main(String[] args) {
        while (seguir) {
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println(" BIENVENIDOS AL SISTEMA DE VENTAS DEL TEATRO MORO  ");
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println(" 1. Comprar Entrada ");
            System.out.println(" 2. Imprimir boleta Final ");
            System.out.println(" 3. Reserva de Entrada y Confirmacion");
            System.out.println(" 4. Modificar de Entrada ");
            System.out.println(" 5. Salir del Sistema ");

            int opcion;

            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println(ROJO + " *** Opcion Invalida *** " + RESET);
                sc.nextLine();
                continue;
            }

            if (opcion < 1 || opcion > 5) {
                System.out.println(ROJO + " *** Opcion Invalida *** " + RESET);
                continue;
            }

            switch (opcion) {
                case 1 ->
                    comprarEntrada();
                case 2 ->
                    imprimirBoletaFinal();
                case 3 ->
                    reservarEntrada();
                case 4 ->
                    modificarEntrada();
                case 5 ->
                    seguir = salidaSistema();
            }
        }
    }

    public static void comprarEntrada() {
        actualizarReservasExpiradas();

        mostrarPlanoAsientos();
        boolean muestraMenu = true;
        while (muestraMenu) {
            int opcion = 0;

            boolean mostrarMenuPrincipal = true;
            while (mostrarMenuPrincipal) {

                System.out.println(" ------------------------------------------------------------------------------------------ ");
                System.out.println("Ingrese Opciones del 1 al 2");
                System.out.println("¿Desea comprar entradas O volver al menu principal? ");
                System.out.println();

                System.out.println(" 1.- Comprar entrada");
                System.out.println(" 2.- Volver al menu principal ");
                System.out.println(" ------------------------------------------------------------------------------------------ ");

                if (sc.hasNextInt()) {
                    opcion = sc.nextInt();
                    sc.nextLine();
                } else {
                    System.out.println("\u001B[31m *** Opcion Invalida *** \u001B[31m");
                    System.out.println("\u001B[31m *** debe ingresar un numero valido entre 1 al 2 ***.\u001B[31m  ");
                    sc.nextLine();
                }
                //validaciones menu principal
                if (opcion < 1) {
                    System.out.println("\u001B[31m *** Opcion Invalida *** \u001B[31m");
                    System.out.println("\u001B[31m *** debe ingresar un numero valido entre 1 al 2 *** \u001B[31m");
                } else if (opcion > 2) {
                    System.out.println("\u001B[31m *** Opcion Invalida *** \u001B[31m");
                    System.out.println("\u001B[31m *** debe ingresar un numero valido entre 1 al 2 *** \u001B[31m");
                }

                mostrarMenuPrincipal = false;

            }
            switch (opcion) {
                case 1 -> {

                    System.out.println("\u001B[34m Has elegido: OPCION 1 COMPRAR ENTRADA \u001B[31m");

                    muestraMenu = false;

                    if ((entradasVipDisponibles == 0 && entradasPlateaBajaDisponibles == 0)
                            && (entradasPlateaAltaDisponibles == 0 && entradasPalcoDisponibles == 0)) {
                        boolean mostrarSalidaAgotadas = true;
                        while (mostrarSalidaAgotadas) {
                            System.out.println("\u001B[31m *** Entradas agotadas *** \u001B[31m");
                            muestraMenu = true;
                            int salir;
                            System.out.println("- ¿Desea salir del sistema?");
                            System.out.println("1- Si ");
                            System.out.println("2- No ");
                            salir = sc.nextInt();

                            //validaciones menu
                            if (salir < 1) {
                                System.out.println("\u001B[31m *** Opcion Invalida *** \u001B[31m");
                                System.out.println("\u001B[31m *** debe ingresar un numero valido entre 1 al 2 *** \u001B[31m");
                            } else if (salir > 2) {
                                System.out.println("\u001B[31m *** Opcion Invalida *** \u001B[31m");
                                System.out.println("\u001B[31m *** debe ingresar un numero valido entre 1 al 2 *** \u001B[31m");
                            }

                            if (salir == 1) {
                                //al agotarse entradas y usuario quiere salir, se cierra el sistema
                                System.exit(0);
                            }

                            mostrarSalidaAgotadas = false;
                        }
                    }

                    // VARIABLES
                    boolean mostrarMenuTipoEntrada = true;

                    while (mostrarMenuTipoEntrada) {
                        // TIPOS DE ENTRADAS
                        System.out.println("------------------------------------------------------------------------------------------ ");
                        System.out.println(" ");
                        System.out.println("¿Que tipo de entrada desea comprar?");
                        System.out.println("                                                 ");
                        System.out.println("Seleccione un numero de opcion del 1 al 4:       ");
                        System.out.println("                                                 ");
                        System.out.println(" 1.- VIP, entradas disponibles: " + entradasVipDisponibles);
                        System.out.println(" 2.- PLATEA BAJA, entradas disponibles: " + entradasPlateaBajaDisponibles);
                        System.out.println(" 3.- PLATEA ALTA, entradas disponibles: " + entradasPlateaAltaDisponibles);
                        System.out.println(" 4.- PALCO, entradas disponibles: " + entradasPalcoDisponibles);

                        //validaciones menu principal
                        if (sc.hasNextInt()) {
                            tipo_entrada = sc.nextInt();
                            sc.nextLine();
                        } else {
                            System.out.println("\u001B[31m *** Opcion Invalida *** \u001B[31m");
                            System.out.println("\u001B[31m ***Debe ingresar un numero entre el 1 y 4***\u001B[31m  ");
                            sc.nextLine();
                        }

                        if (tipo_entrada < 1) {
                            System.out.println("\u001B[31m *** Opcion Invalida *** \u001B[31m");
                            System.out.println("\u001B[31m *** Debe ingresar un numero valido entre 1 al 4 *** \u001B[31m");
                            mostrarMenuTipoEntrada = true;
                        } else if (tipo_entrada > 4) {
                            System.out.println("\u001B[31m *** Opcion Invalida *** \u001B[31m");
                            System.out.println("\u001B[31m *** Debe ingresar un numero valido entre 1 al 4 *** \u001B[31m");
                            mostrarMenuTipoEntrada = true;
                        } else {
                            mostrarMenuTipoEntrada = false;
                        }
                    }

                    int numAsiento;

                    switch (tipo_entrada) {
                        case 1 -> {
                            System.out.println("------------------------------------------------------------------------------------------ ");
                            System.out.println(" ");
                            System.out.println(" Escoga el numeros de asiento VIP: ");
                            System.out.println(" ");
                            mostrarPlanoZona(asientosVIP);
                            System.out.println(" ");
                            System.out.print("Seleccione el número de asiento (1 a 5): ");
                            numAsiento = sc.nextInt();
                            sc.nextLine(); // limpia buffer

                            ubicacionAsiento.add(numAsiento);
                            numeroAsientoComprado = numAsiento;

                            if (numAsiento >= 0 && numAsiento < asientosVIP.length) {
                                if (asientosVIP[numAsiento].equals("D")) { // DEPURACION: Se uso para revisar asiento disponible y opcion de comprarlo
                                    asientosVIP[numAsiento] = "C";
                                    entradasVipDisponibles--;
                                } else if (asientosVIP[numAsiento].equals("R")) {
                                    System.out.print("Este asiento está reservado. Ingrese su nombre para confirmar si usted hizo la reserva: ");
                                    String nombre = sc.nextLine();
                                    if (nombre.equalsIgnoreCase(reservaClienteVIP[numAsiento])) { // DEPURACION: Se uso para revisar asiento y comprar
                                        asientosVIP[numAsiento] = "C";
                                        entradasVipDisponibles--;
                                        reservaClienteVIP[numAsiento] = null;
                                        System.out.println(VERDE + "Reserva confirmada. Asiento comprado con éxito." + RESET);
                                    } else {
                                        System.out.println(ROJO + "No tiene una reserva válida para este asiento." + RESET); // DEPURACION: Se uso para revisar la validacion del error si ya estaba reservada
                                        muestraMenu = true;
                                        continue;
                                    }
                                } else {
                                    System.out.println(ROJO + "Asiento inválido o ya comprado." + RESET);// DEPURACION: Se uso para revisar la validacion de asiento comprado
                                    muestraMenu = true;
                                    continue;
                                }
                            } else {
                                System.out.println(ROJO + "Número de asiento fuera de rango." + RESET);
                                muestraMenu = true;
                                continue;
                            }
                        }
                        case 2 -> {
                            System.out.println("------------------------------------------------------------------------------------------ ");
                            System.out.println(" ");
                            System.out.println(" Escoga el numeros de asiento de Platea baja: ");
                            System.out.println("  ");
                            mostrarPlanoZona(asientosPlateaBaja);
                            System.out.println(" ");
                            System.out.print("Seleccione el número de asiento (1 a 5): ");
                            numAsiento = sc.nextInt();
                            ;
                            sc.nextLine();

                            ubicacionAsiento.add(numAsiento);
                            numeroAsientoComprado = numAsiento;

                            if (numAsiento >= 0 && numAsiento < asientosPlateaBaja.length) {
                                if (asientosPlateaBaja[numAsiento].equals("D")) {
                                    asientosPlateaBaja[numAsiento] = "C";
                                    entradasPlateaBajaDisponibles--;
                                } else if (asientosPlateaBaja[numAsiento].equals("R")) {
                                    System.out.print("Este asiento está reservado. Ingrese su nombre para confirmar si usted hizo la reserva: ");
                                    String nombre = sc.nextLine();
                                    if (nombre.equalsIgnoreCase(reservaClientePlateaBaja[numAsiento])) {
                                        asientosPlateaBaja[numAsiento] = "C";
                                        entradasPlateaBajaDisponibles--;
                                        reservaClientePlateaBaja[numAsiento] = null;
                                        System.out.println(VERDE + "Reserva confirmada. Asiento comprado con éxito." + RESET);
                                    } else {
                                        System.out.println(ROJO + "No tiene una reserva válida para este asiento." + RESET);
                                        muestraMenu = true;
                                        continue;
                                    }
                                } else {
                                    System.out.println(ROJO + "Asiento inválido o ya comprado." + RESET);
                                    muestraMenu = true;
                                    continue;
                                }
                            } else {
                                System.out.println(ROJO + "Número de asiento fuera de rango." + RESET);
                                muestraMenu = true;
                                continue;
                            }
                        }
                        case 3 -> {
                            System.out.println("------------------------------------------------------------------------------------------ ");
                            System.out.println(" ");
                            System.out.println(" Escoga el numeros de asiento de Platea alta: ");
                            System.out.println(" ");
                            mostrarPlanoZona(asientosPlateaAlta);
                            System.out.println(" ");
                            System.out.print("Seleccione el número de asiento (1 a 5): ");
                            numAsiento = sc.nextInt();
                            sc.nextLine();

                            ubicacionAsiento.add(numAsiento);
                            numeroAsientoComprado = numAsiento;

                            if (numAsiento >= 0 && numAsiento < asientosPlateaAlta.length) {
                                if (asientosPlateaAlta[numAsiento].equals("D")) {
                                    asientosPlateaAlta[numAsiento] = "C";
                                    entradasPlateaAltaDisponibles--;
                                } else if (asientosPlateaAlta[numAsiento].equals("R")) {
                                    System.out.print("Este asiento está reservado. Ingrese su nombre para confirmar si usted hizo la reserva: ");
                                    String nombre = sc.nextLine();
                                    if (nombre.equalsIgnoreCase(reservaClientePlateaAlta[numAsiento])) {
                                        asientosPlateaAlta[numAsiento] = "C";
                                        entradasPlateaAltaDisponibles--;
                                        reservaClientePlateaAlta[numAsiento] = null;
                                        System.out.println(VERDE + "Reserva confirmada. Asiento comprado con éxito." + RESET);
                                    } else {
                                        System.out.println(ROJO + "No tiene una reserva válida para este asiento." + RESET);
                                        muestraMenu = true;
                                        continue;
                                    }
                                } else {
                                    System.out.println(ROJO + "Asiento inválido o ya comprado." + RESET);
                                    muestraMenu = true;
                                    continue;
                                }
                            } else {
                                System.out.println(ROJO + "Número de asiento fuera de rango." + RESET);
                                muestraMenu = true;
                                continue;
                            }
                        }
                        case 4 -> {
                            System.out.println("------------------------------------------------------------------------------------------ ");
                            System.out.println(" ");
                            System.out.println(" Escoga el numeros de asiento de Palco: ");
                            System.out.println(" ");
                            mostrarPlanoZona(asientosPalco);
                            System.out.println(" ");
                            System.out.print("Seleccione el número de asiento (1 a 5): ");
                            numAsiento = sc.nextInt();
                            sc.nextLine();

                            ubicacionAsiento.add(numAsiento);
                            numeroAsientoComprado = numAsiento;

                            if (numAsiento >= 0 && numAsiento < asientosPalco.length) {
                                if (asientosPalco[numAsiento].equals("D")) {
                                    asientosPalco[numAsiento] = "C";
                                    entradasPalcoDisponibles--;
                                } else if (asientosPalco[numAsiento].equals("R")) {
                                    System.out.print("Este asiento está reservado. Ingrese su nombre para confirmar si usted hizo la reserva: ");
                                    String nombre = sc.nextLine();
                                    if (nombre.equalsIgnoreCase(reservaClientePalco[numAsiento])) {
                                        asientosPalco[numAsiento] = "C";
                                        entradasPalcoDisponibles--;
                                        reservaClientePalco[numAsiento] = null;
                                        System.out.println(VERDE + "Reserva confirmada. Asiento comprado con éxito." + RESET);
                                    } else {
                                        System.out.println(ROJO + "No tiene una reserva válida para este asiento." + RESET);
                                        muestraMenu = true;
                                        continue;
                                    }
                                } else {
                                    System.out.println(ROJO + "Asiento inválido o ya comprado." + RESET);
                                    muestraMenu = true;
                                    continue;
                                }
                            } else {
                                System.out.println(ROJO + "Número de asiento fuera de rango." + RESET);
                                muestraMenu = true;
                                continue;
                            }
                        }
                    }
                    if (!muestraMenu) {
                        // TARIFAS
                        boolean muestraMenuEdad = true;
                        while (muestraMenuEdad) {
                            System.out.println("------------------------------------------------------------------------------------------ ");
                            System.out.println(" ");
                            System.out.println("Ingrese su EDAD. En numeros. ");

                            //validaciones menu edad
                            if (sc.hasNextInt()) {
                                edad = sc.nextInt();
                                sc.nextLine();
                            } else {
                                System.out.println("\u001B[31m ***Edad No Valida*** \u001B[31m");
                                System.out.println("\u001B[31m  Entrada invalida. Debe ingresar un numero entre el 1 y 100.\u001B[31m  ");
                                sc.nextLine();
                            }

                            if (edad < 1) {
                                System.out.println("\u001B[31m ***Edad No Valida*** \u001B[31m");
                                System.out.println("\u001B[31m  Entrada invalida. Debe ingresar un numero entre el 1 y 100.\u001B[31m  ");
                            } else if (edad > 100) {
                                System.out.println("\u001B[31m ***Edad No Valida*** \u001B[31m");
                                System.out.println("\u001B[31m  Entrada invalida. Debe ingresar un numero entre el 1 y 100.\u001B[31m  ");
                            } else {
                                muestraMenuEdad = false;
                            }

                        }

                        calculosValoresEntradas();
                    }
                }

                case 2 -> {
                    System.out.println(" ***has elegido la opcion 2: Volver al Menu principal*** ");
                    muestraMenu = false;
                }
                default -> {
                    System.out.println(" ***opcion invalida. Por favor,intenta nuevamente*** ");
                }
            }
        }

    }//final comprar entrada

    public static void calculosValoresEntradas() {

        if (edad < 18) {
            tipo_tarifa = 1; // estudiante
        } else if (edad >= 60) {
            tipo_tarifa = 3; // adulto mayor
        } else {
            tipo_tarifa = 2; // público general
        }

        switch (tipo_tarifa) {
            case 1 -> {
                // Estudiante
                switch (tipo_entrada) {
                    case 1 ->
                        precio = 20000;
                    case 2 ->
                        precio = 10000;
                    case 3 ->
                        precio = 9000;
                    case 4 ->
                        precio = 6500;
                }
            }
            case 2 -> {
                // Público general
                switch (tipo_entrada) {
                    case 1 ->
                        precio = 30000;
                    case 2 ->
                        precio = 15000;
                    case 3 ->
                        precio = 18000;
                    case 4 ->
                        precio = 13000;
                }
            }
            case 3 -> {
                // adulto mayor
                switch (tipo_entrada) {
                    case 1 ->
                        precio = 30000;
                    case 2 ->
                        precio = 15000;
                    case 3 ->
                        precio = 18000;
                    case 4 ->
                        precio = 13000;
                }
            }
            default -> {
            }
        }

        // OPERACIONES ARITMeTICAS 
        double porcentajeIVA = 0.19;//porcentaje iva
        switch (tipo_tarifa) {
            case 1 -> {
                double descuento10porciento = 0.1;//DESCUENTO DEL  10 % A ESTUDIANTES
                descuento = precio * descuento10porciento;
                subtotal_con_descuento = precio - descuento;
                total = subtotal_con_descuento;
                iva = (total) * porcentajeIVA;
            }
            case 3 -> {
                double descuento15porciento = 0.15;
                descuento = precio * descuento15porciento;//DESCUENTO DEL  15 % A ADULTO MAYOR
                subtotal_con_descuento = precio - descuento;
                total = subtotal_con_descuento;
                iva = (total) * porcentajeIVA;
            }
            default -> {
                descuento = 0;//SIN DESCUENTO
                subtotal_con_descuento = precio - descuento;
                total = subtotal_con_descuento;
                iva = (total) * porcentajeIVA;
            }
        }

        acumuladorTotales.add(total);
        totalEntradasCompradas++;

        if (totalEntradasCompradas >= 2) {
            double descuentoMasDe2Entradas = 0.20;

            for (int i = 0; i < acumuladorTotales.size(); i++) {
                double total = acumuladorTotales.get(i);
                double descuento = total * descuentoMasDe2Entradas;
                total = total - descuento;
                acumuladorTotales.set(i, total);
            }
        }

        asignaNombreEntradas(totalEntradasCompradas);//asigna nombre de entrada y nombre de tarifa

        //DETALLE DE COMPRA POR ENTRADA
        System.out.println(AZUL + " ------------------------------------------------------------------------------------------ " + RESET);
        System.out.println(AZUL + "          Detalle de compra por entrada seleccionada:                      " + RESET);
        System.out.println();
        System.out.println(AZUL + " Entrada " + totalEntradasCompradas + ":" + RESET);
        System.out.println();
        System.out.println(AZUL + " Ubicación en el teatro: " + "                                            " + nombre_entrada + RESET);
        System.out.println(AZUL + " Número de asiento: " + "                                                 " + numeroAsientoComprado + RESET);
        System.out.println(AZUL + " Tipo de entrada: " + "                                                   " + nombre_tarifa + RESET);
        System.out.println(AZUL + " Precio Base entrada es de: " + "                                         $" + (int) precio + RESET);
        System.out.println(AZUL + " IVA incluido (19%):    " + "                                             $" + (int) iva + RESET);
        System.out.println(AZUL + " TOTAL A PAGAR POR ENTRADA: " + "                                         $" + (int) total + RESET);

    }

    public static void asignaNombreEntradas(int indice) {

        switch (tipo_entrada) {
            case 1 ->
                nombre_entrada = "VIP";
            case 2 ->
                nombre_entrada = "PLATEA BAJA";
            case 3 ->
                nombre_entrada = "PLATEA ALTA";
            case 4 ->
                nombre_entrada = "PALCO";
        }
        switch (tipo_tarifa) {
            case 1 ->
                nombre_tarifa = "Estudiante";
            case 2 ->
                nombre_tarifa = "Público General";
            case 3 ->
                nombre_tarifa = "Adulto mayor";
        }

        tipoEntradaComprada[indice - 1] = nombre_entrada;
        tarifaEntradaComprada[indice - 1] = nombre_tarifa;
    }

    public static void mostrarPlanoZona(String[] zona) {

        for (int i = 0; i < zona.length; i++) {
            String color;
            switch (zona[i]) {
                case "C" ->
                    color = ROJO;      // Comprado
                case "R" ->
                    color = AMARILLO;  // Reservado
                default ->
                    color = VERDE;      // Disponible
            }
            System.out.print("[" + (i + 1) + ":" + color + zona[i] + RESET + "] ");
        }
        System.out.println(); // Salto de línea final
    }

    public static void mostrarPlanoAsientos() {

        System.out.println("-------------------------- PLANO DE ASIENTOS ---------------------------------------------");
        mostrarZonaConColores("VIP:            ", asientosVIP);
        mostrarZonaConColores("Platea Baja:    ", asientosPlateaBaja);
        mostrarZonaConColores("Platea Alta:    ", asientosPlateaAlta);
        mostrarZonaConColores("Palco:          ", asientosPalco);

    }

    public static void mostrarZonaConColores(String titulo, String[] zona) {
        System.out.print(RESET + titulo);
        for (int i = 0; i < zona.length; i++) {
            String color;

            switch (zona[i]) {
                case "C":
                    color = ROJO;      // Comprado
                    break;
                case "R":
                    color = AMARILLO;  // Reservado
                    break;
                default:
                    color = VERDE;     // Disponible
                    break;
            }

            System.out.print("[" + (i + 1) + ":" + color + zona[i] + RESET + "] ");
        }
        System.out.println();
    }

    public static void imprimirBoletaFinal() {
        if (totalEntradasCompradas == 0) {
            System.out.println("No hay entradas compradas aún.");
            return;
        }

        System.out.println();
        System.out.println(AZUL + "======================================= BOLETA FINAL ================================" + RESET);
        System.out.println();
        System.out.println(AZUL + " Cantidad total de entradas: " + totalEntradasCompradas + " " + RESET);
        System.out.println(AZUL + "--------------------------------------------------------------------------------------------" + RESET);
        double totalGeneral = 0;

        for (int i = 0; i < acumuladorTotales.size(); i++) {
            double TOTAL = acumuladorTotales.get(i);
            System.out.println(AZUL + "- Entrada:               " + (i + 1) + RESET);
            System.out.println();
            System.out.println(AZUL + "- Numero Asiento:        " + ubicacionAsiento.get(i) + RESET);// DEPURACION: Se uso para poder revisar los valores de ubicacionAsiento y ver que contenia el arreglo
            System.out.println(AZUL + "- Publico:               " + tarifaEntradaComprada[i] + " " + RESET); // DEPURACION: Se uso para poder revisar los valores de tarifaEntradaComprada y ver que contenia el arreglo
            System.out.println(AZUL + "- Tipo:                  " + tipoEntradaComprada[i] + " " + RESET);// DEPURACION: Se uso para poder revisar los valores de tarifaEntradaComprada y ver que contenia el arreglo
            System.out.println(AZUL + "- Tarifa:               $" + (int) TOTAL + RESET);
            System.out.println(AZUL + "----------------------------------------------------------------------------------------" + RESET);

            totalGeneral += TOTAL;
        }

        System.out.println(AZUL + " TOTAL GENERAL A PAGAR: $" + (int) totalGeneral + RESET);// DEPURACION: Se uso para poder revisar los valores de totalGeneral y ver que contenia el cumulador por iteracion
        System.out.println(AZUL + " IVA:                   $" + (int) Math.round(totalGeneral * 0.19) + RESET);
        System.out.println(AZUL + "==============================================================================================" + RESET);

    }

    public static void reservarEntrada() {
        iniciarVerificacionReservas(); // Inicia verificación en segundo plano
        mostrarPlanoAsientos();

        int tieneReserva;
        boolean mostrarMenuReserva = true;

        while (mostrarMenuReserva) {
            System.out.println();
            System.out.println(" ------------------------------------------------------------------------------------------ ");
            System.out.println("¿Que desea realizar?");
            System.out.println("1.-Reservar");
            System.out.println("2.-Confirmar Reserva");

            if (sc.hasNextInt()) {
                tieneReserva = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println(ROJO + " *** Opcion Invalida *** " + RESET);
                sc.nextLine();
                continue;
            }

            if (tieneReserva < 1 || tieneReserva > 2) {
                System.out.println(ROJO + " *** Opcion Invalida *** " + RESET);
                continue;
            }

            if (tieneReserva == 2) {
                System.out.print("Ingrese su nombre de reserva: ");
                String nombreReserva = sc.nextLine();

                System.out.println("Seleccione la zona de su reserva:");
                System.out.println("1. VIP");
                System.out.println("2. Platea Baja");
                System.out.println("3. Platea Alta");
                System.out.println("4. Palco");
                int zona = sc.nextInt();
                sc.nextLine();

                System.out.print("Ingrese el número de asiento reservado (1 a 5): ");
                int numAsiento = sc.nextInt() - 1;
                sc.nextLine();

                String[] asientos = null;
                String[] reservas = null;
                long[] tiempos = null;

                switch (zona) {
                    case 1 -> {
                        asientos = asientosVIP;
                        reservas = reservaClienteVIP;
                        tiempos = tiemposReservaVIP;
                    }
                    case 2 -> {
                        asientos = asientosPlateaBaja;
                        reservas = reservaClientePlateaBaja;
                        tiempos = tiemposReservaPlateaBaja;
                    }
                    case 3 -> {
                        asientos = asientosPlateaAlta;
                        reservas = reservaClientePlateaAlta;
                        tiempos = tiemposReservaPlateaAlta;
                    }
                    case 4 -> {
                        asientos = asientosPalco;
                        reservas = reservaClientePalco;
                        tiempos = tiemposReservaPalco;
                    }
                    default -> {
                        System.out.println("Zona inválida.");
                        return;
                    }
                }

                if (numAsiento >= 0 && numAsiento < asientos.length) {
                    if (asientos[numAsiento].equals("R") && nombreReserva.equalsIgnoreCase(reservas[numAsiento])) {
                        if (System.currentTimeMillis() <= tiempos[numAsiento]) {
                            asientos[numAsiento] = "C";
                            reservas[numAsiento] = null;
                            tiempos[numAsiento] = 0;

                            switch (zona) {
                                case 1 ->
                                    entradasVipDisponibles--;
                                case 2 ->
                                    entradasPlateaBajaDisponibles--;
                                case 3 ->
                                    entradasPlateaAltaDisponibles--;
                                case 4 ->
                                    entradasPalcoDisponibles--;
                            }

                            numeroAsientoComprado = numAsiento + 1;
                            tipo_entrada = zona;

                            System.out.println(VERDE + "Reserva confirmada y asiento comprado exitosamente." + RESET);
                        } else {
                            System.out.println(ROJO + "La reserva ha expirado." + RESET);
                            return;
                        }
                    } else {
                        System.out.println(ROJO + "Nombre incorrecto o el asiento no está reservado." + RESET);
                        return;
                    }
                } else {
                    System.out.println(ROJO + "Número de asiento fuera de rango." + RESET);
                    return;
                }
            }//final reserva

            boolean menuResevarEntrada = true;

            while (menuResevarEntrada) {
                System.out.println("-----------------------------------------------------------------------------------------");
                System.out.println(" ");
                System.out.println(" Reserva de entradas");

                System.out.println(" ¿Qué entrada desea buscar para reservar?");
                System.out.println("-----------------------------------------------------------------------------------------");
                System.out.println(" 1. VIP");
                System.out.println(" 2. Platea baja");
                System.out.println(" 3. Platea alta");
                System.out.println(" 4. Palco");
                System.out.println(" 5. Salir de la reserva");

                int opcion = 0;

                if (sc.hasNextInt()) {
                    opcion = sc.nextInt();
                    sc.nextLine();
                } else {
                    System.out.println(ROJO + " *** Opción inválida. Ingrese un número entre 1 y 5. ***" + RESET);
                    sc.nextLine();
                    continue;
                }

                if (opcion < 1 || opcion > 5) {
                    System.out.println(ROJO + " *** Opción fuera de rango. Intente nuevamente. ***" + RESET);
                    continue;
                }

                System.out.println("-----------------------------------------------------------------------------------------");

                String nombreReserva = "";
                int asientoSeleccionado = -1;

                switch (opcion) {
                    case 1 -> {

                        System.out.println("Reserva de asientos Vip:  ");
                        System.out.println("   ");
                        actualizarReservasExpiradas();
                        mostrarPlanoZona(asientosVIP);
                        System.out.println("   ");
                        System.out.print("Ingrese número de asiento a reservar (1 a 5): ");
                        asientoSeleccionado = sc.nextInt() - 1;
                        sc.nextLine();
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.print("Ingrese su nombre para asociar la reserva: ");
                        nombreReserva = sc.nextLine();

                        if (asientoSeleccionado >= 0 && asientoSeleccionado < asientosVIP.length && asientosVIP[asientoSeleccionado].equals("D")) {// DEPURACION: Se uso para revisar asiento seleccionado
                            asientosVIP[asientoSeleccionado] = "R";
                            tiemposReservaVIP[asientoSeleccionado] = System.currentTimeMillis() + DURACION_RESERVA_MS;
                            reservaClienteVIP[asientoSeleccionado] = nombreReserva;
                            System.out.println("   ");
                            System.out.println(AZUL + "*** Reserva realizada. Tiene 10 segundos para confirmar la compra. o sera anulada***" + RESET);
                            System.out.println(AZUL + "Mapa asientos VIP " + RESET);
                            mostrarPlanoZona(asientosVIP);
                            totalEntradasCompradas++;
                        } else {
                            System.out.println(ROJO + "Asiento inválido, ocupado o reservado." + RESET);
                        }
                    }

                    case 2 -> {
                        System.out.println("Reserva de asientos Platea baja:  ");
                        System.out.println("   ");
                        actualizarReservasExpiradas();
                        mostrarPlanoZona(asientosPlateaBaja);
                        System.out.println("   ");
                        System.out.print("Ingrese número de asiento a reservar (1 a 5): ");
                        asientoSeleccionado = sc.nextInt() - 1;
                        sc.nextLine();
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.print("Ingrese su nombre para asociar la reserva: ");
                        nombreReserva = sc.nextLine();

                        if (asientoSeleccionado >= 0 && asientoSeleccionado < asientosPlateaBaja.length && asientosPlateaBaja[asientoSeleccionado].equals("D")) {// DEPURACION: Se uso para revisar asiento seleccionado
                            asientosPlateaBaja[asientoSeleccionado] = "R";
                            tiemposReservaPlateaBaja[asientoSeleccionado] = System.currentTimeMillis() + DURACION_RESERVA_MS;
                            reservaClientePlateaBaja[asientoSeleccionado] = nombreReserva;
                            System.out.println("   ");
                            System.out.println(AZUL + "*** Reserva realizada. Tiene 1 minuto para confirmar la compra. ***" + RESET);
                            System.out.println(AZUL + "Mapa asientos Platea Baja " + RESET);
                            mostrarPlanoZona(asientosPlateaBaja);
                            totalEntradasCompradas++;
                        } else {
                            System.out.println(ROJO + "Asiento inválido, ocupado o reservado." + RESET);
                        }
                    }

                    case 3 -> {
                        System.out.println("Reserva de asientos Platea Alta:  ");
                        System.out.println("   ");
                        actualizarReservasExpiradas();
                        mostrarPlanoZona(asientosPlateaAlta);
                        System.out.println("   ");
                        System.out.print("Ingrese número de asiento a reservar (1 a 5): ");
                        asientoSeleccionado = sc.nextInt() - 1;
                        sc.nextLine();
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.print("Ingrese su nombre para asociar la reserva: ");
                        nombreReserva = sc.nextLine();

                        if (asientoSeleccionado >= 0 && asientoSeleccionado < asientosPlateaAlta.length && asientosPlateaAlta[asientoSeleccionado].equals("D")) {// DEPURACION: Se uso para revisar asiento seleccionado
                            asientosPlateaAlta[asientoSeleccionado] = "R";
                            tiemposReservaPlateaAlta[asientoSeleccionado] = System.currentTimeMillis() + DURACION_RESERVA_MS;
                            reservaClientePlateaAlta[asientoSeleccionado] = nombreReserva;
                            System.out.println("   ");
                            System.out.println(AZUL + "*** Reserva realizada. Tiene 1 minuto para confirmar la compra. ***" + RESET);
                            System.out.println(AZUL + "Mapa asientos Platea Alta " + RESET);
                            mostrarPlanoZona(asientosPlateaAlta);
                            totalEntradasCompradas++;
                        } else {
                            System.out.println(ROJO + "Asiento inválido, ocupado o reservado." + RESET);
                        }
                    }

                    case 4 -> {
                        System.out.println("Reserva de asientos Palco:  ");
                        System.out.println("   ");
                        actualizarReservasExpiradas();
                        mostrarPlanoZona(asientosPalco);
                        System.out.println("   ");
                        System.out.print("Ingrese número de asiento a reservar (1 a 5): ");
                        asientoSeleccionado = sc.nextInt() - 1;
                        sc.nextLine();
                        System.out.println("-----------------------------------------------------------------------------------------");
                        System.out.print("Ingrese su nombre para asociar la reserva: ");
                        nombreReserva = sc.nextLine();

                        if (asientoSeleccionado >= 0 && asientoSeleccionado < asientosPalco.length && asientosPalco[asientoSeleccionado].equals("D")) {// DEPURACION: Se uso para revisar asiento seleccionado
                            asientosPalco[asientoSeleccionado] = "R";
                            tiemposReservaPalco[asientoSeleccionado] = System.currentTimeMillis() + DURACION_RESERVA_MS;
                            reservaClientePalco[asientoSeleccionado] = nombreReserva;
                            System.out.println("   ");
                            System.out.println(AZUL + "*** Reserva realizada. Tiene 1 minuto para confirmar la compra. ***" + RESET);
                            System.out.println(AZUL + "Mapa asientos Palco " + RESET);
                            mostrarPlanoZona(asientosPalco);
                            totalEntradasCompradas++;
                        } else {
                            System.out.println(ROJO + "Asiento inválido, ocupado o reservado." + RESET);
                        }

                    }
                    case 5 -> {
                        menuResevarEntrada = false;
                        mostrarMenuReserva = false;

                    }
                } // fin switch
            } // fin while
        }

    }

    public static void modificarEntrada() {
        if (totalEntradasCompradas == 0) {
            System.out.println(ROJO + "*** No se puede Modificar, No hay entradas que modificar ***" + RESET);
            seguir = true;
            return;
        }

        mostrarPlanoAsientos();
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("   ");
        actualizarReservasExpiradas(); // Asegura que las reservas expiradas se liberen

        System.out.println("¿Qué zona desea modificar?");
        System.out.println("   ");
        System.out.println("1. VIP");
        System.out.println("2. Platea Baja");
        System.out.println("3. Platea Alta");
        System.out.println("4. Palco");

        int zona = sc.nextInt();
        sc.nextLine();

        String[] asientos = null;
        String[] reservas = null;
        int disponibles = 0;
        System.out.println("--------------------------------------------------------------------------");
        switch (zona) {
            case 1 -> {
                System.out.println("Asientos VIP: ");
                asientos = asientosVIP;
                reservas = reservaClienteVIP;
                disponibles = entradasVipDisponibles;
            }
            case 2 -> {
                System.out.println("Asientos de Platea Baja: ");
                asientos = asientosPlateaBaja;
                reservas = reservaClientePlateaBaja;
                disponibles = entradasPlateaBajaDisponibles;
            }
            case 3 -> {
                System.out.println("Asientos de Platea Alta: ");
                asientos = asientosPlateaAlta;
                reservas = reservaClientePlateaAlta;
                disponibles = entradasPlateaAltaDisponibles;
            }
            case 4 -> {
                System.out.println("Asientos de Palco: ");
                asientos = asientosPalco;
                reservas = reservaClientePalco;
                disponibles = entradasPalcoDisponibles;
            }
            default -> {
                System.out.println("Zona inválida.");
                return;
            }
        }

        mostrarPlanoZona(asientos);
        System.out.print("Ingrese el número de asiento a modificar (1 a 5): ");
        int numeroAsiento = sc.nextInt() - 1;
        sc.nextLine();

        if (numeroAsiento < 0 || numeroAsiento >= asientos.length) {
            System.out.println("Número de asiento fuera de rango.");
            return;
        }

        String estado = asientos[numeroAsiento];

        if (estado.equals("C")) {
            System.out.println("El asiento está COMPRADO. ¿Desea anular esta compra? (1: Sí, 2: No)");
            int opcion = sc.nextInt();
            sc.nextLine();

            if (opcion == 1) {
                asientos[numeroAsiento] = "D";
                switch (zona) {
                    case 1 ->
                        entradasVipDisponibles++;
                    case 2 ->
                        entradasPlateaBajaDisponibles++;
                    case 3 ->
                        entradasPlateaAltaDisponibles++;
                    case 4 ->
                        entradasPalcoDisponibles++;
                }
                totalEntradasCompradas--;
                System.out.println(AZUL + "Compra anulada. Asiento ahora disponible." + RESET);
            }

        } else if (estado.equals("R")) {
            System.out.println("El asiento está RESERVADO. ¿Desea anular esta reserva? (1: Sí, 2: No)");
            int opcion = sc.nextInt();
            sc.nextLine();

            if (opcion == 1) {
                asientos[numeroAsiento] = "D";
                reservas[numeroAsiento] = null;

                switch (zona) {
                    case 1 ->
                        entradasVipDisponibles++;
                    case 2 ->
                        entradasPlateaBajaDisponibles++;
                    case 3 ->
                        entradasPlateaAltaDisponibles++;
                    case 4 ->
                        entradasPalcoDisponibles++;
                }

                System.out.println(VERDE + "Reserva anulada. Asiento ahora disponible." + RESET);
            }

        } else {
            System.out.println("El asiento ya está disponible. No hay nada que modificar.");
        }

        mostrarPlanoZona(asientos);
    }

    public static boolean salidaSistema() {
        int opcion = 0;
        boolean mostrarMenuSalida = true;
        while (mostrarMenuSalida) {
            System.out.println(AZUL + "*** Gracias por su compra, disfrute la función ***" + RESET);

            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println(" Confirme si desea salir del sistema AL SISTEMA");
            System.out.println("------------------------------------------------------------------------------------------");
            System.out.println(" 1. Salir ");
            System.out.println(" 2. Volver al menu principal");

            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
                sc.nextLine();
            } else {
                System.out.println(ROJO + " *** Opcion Invalida *** " + RESET);
                System.out.println(ROJO + "*** Debe ingresar un numero valido entre 1 al 2 ***" + RESET);
                sc.nextLine();
                continue;
            }

            if (opcion < 1 || opcion > 2) {
                System.out.println(ROJO + " *** Opcion Invalida *** " + RESET);
                System.out.println(ROJO + " *** Debe ingresar un numero valido entre 1 al 2 *** " + RESET);
                continue;
            }

            switch (opcion) {
                case 1:
                    System.exit(0);
                case 2:
                    return true;
                default:
                    sc.nextLine(); //limpia si el ususario escribio mal
            }
        }
        return false;

    }

    public static void iniciarVerificacionReservas() {
        Thread verificadorReservas = new Thread(() -> {
            while (true) {
                try {
                    actualizarReservasExpiradas();
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println("Hilo de verificación de reserva de entradas interrumpido.");
                    break;
                }
            }
        });

        verificadorReservas.setDaemon(true); // El hilo se detendrá cuando finalice el programa
        verificadorReservas.start();
    }

    public static void actualizarReservasExpiradas() {
        long ahora = System.currentTimeMillis();

        for (int i = 0; i < 5; i++) {
            if (asientosVIP[i].equals("R") && ahora > tiemposReservaVIP[i]) { //15:46 15:48
                asientosVIP[i] = "D";
                System.out.println(ROJO + "Reserva Anulada, expiro el tiempo de la reserva." + RESET);
            }
            if (asientosPlateaBaja[i].equals("R") && ahora > tiemposReservaPlateaBaja[i]) {
                asientosPlateaBaja[i] = "D";
                System.out.println(ROJO + "Reserva Anulada, expiro el tiempo de la reserva." + RESET);
            }
            if (asientosPlateaAlta[i].equals("R") && ahora > tiemposReservaPlateaAlta[i]) {
                asientosPlateaAlta[i] = "D";
                System.out.println(ROJO + "Reserva Anulada, expiro el tiempo de la reserva." + RESET);
            }
            if (asientosPalco[i].equals("R") && ahora > tiemposReservaPalco[i]) {
                asientosPalco[i] = "D";
                System.out.println(ROJO + "Reserva Anulada, expiro el tiempo de la reserva." + RESET);
            }
        }
    }

}
