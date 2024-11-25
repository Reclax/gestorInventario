/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProyectoFinal;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author HP
 */
public class ProyectoFinal {
    public static void main(String[] args) throws IOException {
        Login();
    }
    
    public static void Login() throws IOException{
        Scanner tec = new Scanner(System.in);
        int intentos = 0;
        boolean usuarioValido = false;
        String user;
        int permitido=3;
        do {
            System.out.print("Usuario: ");
            String usuario = tec.nextLine();
            user=usuario;
            System.out.print("Contraseña: ");
            String contraseña = tec.nextLine();

            if (verificarCredenciales(usuario, contraseña)) {
                usuarioValido = true;
            } else {
                intentos++;
                System.out.println("Credenciales incorrectas. Intento #" + intentos);

                if (intentos >= permitido) {
                    bloquearUsuario();
                    System.out.println("Usuario bloqueado. Por favor, cambie las credenciales en el archivo txt.");
                    break;
                }
            }
        } while (!usuarioValido);

        if (usuarioValido) {
            mostrarMenu(user);
        }
    }

    public static boolean verificarCredenciales(String usuario, String contraseña) {
        try {
            File usuarios= new File("archivo/credenciales.txt");
            BufferedReader ur = new BufferedReader(new FileReader(usuarios));
            String lineau;
            while ((lineau = ur.readLine()) != null) {
                String[] credenciales = lineau.split("/");
                if (credenciales.length == 2 && credenciales[0].equals(usuario) && credenciales[1].equals(contraseña)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de credenciales.");
        }
        return false;
    }

    public static void bloquearUsuario() {
        try {
            File usuarios= new File("archivo/credenciales.txt");
            BufferedWriter uw= new BufferedWriter(new FileWriter(usuarios, true));
            uw.write("/bloqueado");
            uw.close();
        } catch (IOException e) {
            System.out.println("Error al bloquear el usuario.");
        }
    }

    public static void mostrarMenu(String user) throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        do {
            System.out.println("Bienvenido, " + user + "!");
            System.out.println("--- Menú de opciones ---");
            System.out.println("a. Productos");
            System.out.println("b. Clientes");
            System.out.println("c. Proveedor");
            System.out.println("d. Factura");
            System.out.println("e. Compra");
            System.out.println("f. Provincia");
            System.out.println("g. Ciudad");
            System.out.println("h. Listado de facturas generadas");
            System.out.println("i. Cambio de usuario");
            System.out.println("j. Salir");

            System.out.print("Ingrese la opción deseada: ");
            String opcion = sc.nextLine();

            switch (opcion) {
                case "a":
                    gestionarProductos();
                    break;
                case "b":
                    gestionarClientes();
                    break;
                case "c":
                    gestionarProveedores();
                    break;
                case "d":
                    generarFactura();
                    break;
                case "e":
                    generarCompra();
                    break;
                case "f":
                    gestionarProvincias();
                    break;
                case "g":
                    gestionarCiudades();
                    break;
                case "h":
                    listarFacturasGeneradas();
                    break;
                case "i":
                    cambiarUsuario();
                    break;
                case "j":
                    salir = true;
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, ingrese una opción válida.");
                    break;
            }
        }while (!salir);
    }

    public static void gestionarProductos() throws IOException {
          Scanner tec = new Scanner(System.in);

        try {
            File proveedores = new File("archivo/proveedores.txt");
            File productos = new File("archivo/productos.txt");
            File DatosFactura = new File("archivo/DatosFactura.txt");
            BufferedWriter pdw = new BufferedWriter(new FileWriter(productos, true));
            BufferedReader pvr = new BufferedReader(new FileReader(proveedores));
            BufferedReader pdr = new BufferedReader(new FileReader(productos));
            String lineapd;
            boolean productoEncontrado = false;
            String opcion;
            boolean salir = false;

            do {
                System.out.println("a. Agregar producto");
                System.out.println("b. Eliminar producto");
                System.out.println("c. Modificar producto");
                System.out.println("d. Salir");
                System.out.println("Ingrese la opción deseada:");
                opcion = tec.nextLine();

                switch (opcion) {
                    case "a":
                        boolean codigoExistente = false;
                        String codigop;
                        do {
                            System.out.println("Ingrese el codigo del producto:");
                            codigop = tec.nextLine();
                            codigoExistente = false;
                            BufferedReader pdr3 = new BufferedReader(new FileReader(productos));
                            String lineaProducto;
                            while ((lineaProducto = pdr3.readLine()) != null) {
                                String[] producto = lineaProducto.split("/");
                                if (producto.length == 4 && producto[0].equals(codigop)) {
                                    codigoExistente = true;
                                    break;
                                }
                            }
                            pdr3.close();
                            if (codigoExistente) {
                                System.out.println("El código de producto ya existe en el archivo de productos.");
                            }
                        } while (codigoExistente);
                        System.out.println("Ingrese el nombre del producto:");
                        String nombrep = tec.nextLine();
                        boolean stockValido = false;
                        String stock;
                        do {
                            System.out.println("Ingrese el stock del producto:");
                            stock = tec.nextLine();

                            if (!stock.matches("\\d+")) {
                                System.out.println("El stock se debe ingresar en números, no en letras.");
                            } else {
                                stockValido = true;
                            }
                        } while (!stockValido);
                        String codigoProveedor;
                        boolean proveedorExiste;
                        do {
                            System.out.println("Ingrese el código del proveedor del producto:");
                            codigoProveedor = tec.nextLine();
                            proveedorExiste = false;

                            String lineapv;
                            BufferedReader pvr2 = new BufferedReader(new FileReader(proveedores));
                            while ((lineapv = pvr2.readLine()) != null) {
                                String[] pvv = lineapv.split("/");
                                if (pvv.length == 6 && pvv[0].equals(codigoProveedor)) {
                                    proveedorExiste = true;
                                    break;
                                }
                            }
                            pvr2.close();

                            if (!proveedorExiste) {
                                System.out.println("El código del proveedor no existe en el archivo de proveedores.");
                            }
                        } while (!proveedorExiste);
                        String producto = codigop + "/" + nombrep + "/" + stock + "/"
                                + codigoProveedor;
                        pdw.write(producto);
                        pdw.newLine();
                        pdw.flush();
                        System.out.println("El producto ha sido agregado correctamente.");
                        break;
                    case "b":
                        String codigoEliminar;
                        boolean productoEncontrado2 = false;
                        boolean productoEnUso = false;
                        do {
                            System.out.println("Ingrese el código del producto a eliminar:");
                            codigoEliminar = tec.nextLine();
                            productoEnUso = false;
                            // Verificar si el código existe en el archivo de productos
                            BufferedReader prdReader = new BufferedReader(new FileReader(productos));
                            String lineaprd;
                            while ((lineaprd = prdReader.readLine()) != null) {
                                String[] prdv = lineaprd.split("/");
                                if (prdv.length == 4 && prdv[0].equals(codigoEliminar)) {
                                    productoEncontrado2 = true;
                                    String nombreProducto = prdv[0];

                                    // Verificar si el producto está en uso en el archivo de DatosFactura
                                    BufferedReader df = new BufferedReader(new FileReader(DatosFactura));
                                    String linedf;
                                    while ((linedf = df.readLine()) != null) {
                                        String[] dfv = linedf.split("/");
                                        if (dfv.length == 6 && dfv[2].equals(nombreProducto)||(dfv.length == 7 && dfv[2].equals(nombreProducto))) {
                                            productoEnUso = true;
                                            break;
                                        }
                                    }
                                    df.close();
                                    break;
                                }
                            }
                            prdReader.close();

                            if (!productoEncontrado2) {
                                System.out.println("El código del producto no existe en el archivo. Intente nuevamente.");
                            } else if (productoEnUso) {
                                System.out.println("No se puede eliminar el producto porque está asociado a facturas existentes. Intente nuevamente.");
                            }
                        } while (!productoEncontrado2 || productoEnUso);

                        // Eliminar el producto del archivo de productos
                        StringBuilder contenidoActualizado = new StringBuilder();
                        BufferedReader prdReader2 = new BufferedReader(new FileReader(productos));
                        String lineaprd2;
                        while ((lineaprd2 = prdReader2.readLine()) != null) {
                            String[] prdv = lineaprd2.split("/");
                            if (prdv.length == 4 && prdv[0].equals(codigoEliminar)) {
                                continue; // Omitir la línea correspondiente al producto que se eliminará
                            }
                            contenidoActualizado.append(lineaprd2).append("\n");
                        }
                        prdReader2.close();

                        FileWriter prdWriter = new FileWriter(productos);
                        prdWriter.write(contenidoActualizado.toString());
                        prdWriter.close();

                        System.out.println("El producto ha sido eliminado correctamente.");
                        break;
                    case "c":
                        String codigoModificar;
                        boolean productoEncontrado5 = false;
                        StringBuilder productoModificado = new StringBuilder();

                        do {
                            System.out.println("Ingrese el código del producto a modificar:");
                            codigoModificar = tec.nextLine();
                            productoEncontrado5 = false;

                            BufferedReader pdr5 = new BufferedReader(new FileReader(productos));
                            String lineapd5;
                            while ((lineapd5 = pdr5.readLine()) != null) {
                                String[] productoSplit = lineapd5.split("/");
                                if (productoSplit.length == 4 && productoSplit[0].equals(codigoModificar)) {
                                    productoEncontrado5 = true;

                                    System.out.println("Ingrese el nuevo nombre del producto:");
                                    String nuevoNombre = tec.nextLine();

                                    boolean nuevoStockValido = false;
                                    String nuevoStock;
                                    do {
                                        System.out.println("Ingrese el nuevo stock del producto:");
                                        nuevoStock = tec.nextLine();

                                        if (!nuevoStock.matches("\\d+")) {
                                            System.out.println("El stock se debe ingresar en números, no en letras.");
                                        } else {
                                            nuevoStockValido = true;
                                        }
                                    } while (!nuevoStockValido);

                                    String nuevoCodigoProveedor;
                                    boolean proveedorExistente;
                                    do {
                                        proveedorExistente = false;
                                        System.out.println("Ingrese el nuevo código del proveedor del producto:");
                                        nuevoCodigoProveedor = tec.nextLine();

                                        BufferedReader pv = new BufferedReader(new FileReader(proveedores));
                                        String lineapv;
                                        while ((lineapv = pv.readLine()) != null) {
                                            String[] pvv = lineapv.split("/");
                                            if (pvv.length == 6 && pvv[0].equals(nuevoCodigoProveedor)) {
                                                proveedorExistente = true;
                                                break;
                                            }
                                        }
                                        pv.close();

                                        if (!proveedorExistente) {
                                            System.out.println("El código del proveedor no existe en el archivo de proveedores.");
                                        }
                                    } while (!proveedorExistente);

                                    String productoNuevo = codigoModificar + "/" + nuevoNombre + "/" + nuevoStock + "/" + nuevoCodigoProveedor;
                                    productoModificado.append(productoNuevo).append("\n");
                                } else {
                                    productoModificado.append(lineapd5).append("\n");
                                }
                            }

                            pdr5.close();

                            if (!productoEncontrado5) {
                                System.out.println("El código del producto no existe en el archivo. Intente nuevamente.");
                            }
                        } while (!productoEncontrado5);

                        if (productoEncontrado5) {
                            BufferedWriter bw = new BufferedWriter(new FileWriter(productos));
                            bw.write(productoModificado.toString());
                            bw.close();
                            System.out.println("El producto ha sido modificado correctamente.");
                        } else {
                            System.out.println("El código del producto no existe en el archivo.");
                        }
                        break;
                    case "d":
                        // Salir
                        salir = true;
                        break;

                    default:
                        System.out.println("Opción inválida.");
                        break;
                }
            } while (!salir);

            pdr.close();
            pvr.close();
        } catch (IOException e) {
            System.out.println("Error al registrar, eliminar o modificar productos.");
        } 
    }

    public static void gestionarClientes() {
        Scanner tec = new Scanner(System.in);
        try {
            File provincias = new File("archivo/provincias.txt");
            File ciudades = new File("archivo/ciudades.txt");
            File clientes = new File("archivo/clientes.txt");
            File DatosFactura = new File("archivo/DatosFactura.txt");
            BufferedReader pr = new BufferedReader(new FileReader(provincias));
            BufferedReader cr = new BufferedReader(new FileReader(ciudades));
            BufferedReader clr = new BufferedReader(new FileReader(clientes));
            String lineacl;
            boolean clienteEncontrado = false;
            boolean salir = false;
            while (!salir) {
                System.out.println("a. Agregar cliente");
                System.out.println("b. Eliminar cliente");
                System.out.println("c. Modificar cliente");
                System.out.println("d. Regresar");
                System.out.println("Ingrese la opción deseada:");
                String opcion = tec.nextLine();
                switch (opcion) {
                    case "a": // Agregar cliente
                        String cedula;
                        boolean cedulaExistente;
                        boolean cedulaValida;

                        do {
                            cedulaExistente = false;
                            cedulaValida = false;

                            System.out.println("Ingrese la cédula del cliente (10 dígitos):");
                            cedula = tec.nextLine();

                            if (!cedula.matches("\\d+")) {
                                System.out.println("La cédula debe contener solo números.");
                            } else if (cedula.length() != 10) {
                                System.out.println("La cédula debe tener 10 dígitos.");
                            } else {
                                // Verificar si la cédula ya está <registrada en el archivo de clientes
                                BufferedReader clr2 = new BufferedReader(new FileReader(clientes));
                                String lineacl2;
                                while ((lineacl2 = clr2.readLine()) != null) {
                                    String[] clienteSplit = lineacl2.split("/");
                                    if (clienteSplit.length == 5 && clienteSplit[0].equals(cedula)) {
                                        cedulaExistente = true;
                                        System.out.println("La cédula del cliente ya está registrada. Intente nuevamente.");
                                        break;
                                    }
                                }
                                clr2.close();

                                if (!cedulaExistente) {
                                    cedulaValida = true;
                                }
                            }
                        } while (!cedulaValida || cedulaExistente);

                        String nombresApellidos;
                        do {
                            System.out.println("Ingrese el nombre y apellidos del cliente (al menos dos palabras):");
                            nombresApellidos = tec.nextLine();
                            if (!nombresApellidos.trim().contains(" ")) {
                                System.out.println("El nombre y apellidos deben tener al menos dos palabras.");
                            }
                        } while (!nombresApellidos.trim().contains(" "));

                        String direccion;
                        do {
                            System.out.println("Ingrese la dirección del cliente (al menos tres palabras):");
                            direccion = tec.nextLine();
                            if (direccion.trim().split(" ").length < 3) {
                                System.out.println("La dirección debe tener al menos tres palabras.");
                            }
                        } while (direccion.trim().split(" ").length < 3);

                        String codigoProvincia;
                        boolean provinciaValida = false;
                        String lineapr;
                        boolean existeProvincia;

                        do {
                            System.out.println("Ingrese el código de la provincia del cliente:");
                            codigoProvincia = tec.nextLine();
                            pr = new BufferedReader(new FileReader(provincias));
                            existeProvincia = false;
                            while ((lineapr = pr.readLine()) != null) {
                                String[] provinciaSplit = lineapr.split("/");
                                if (provinciaSplit.length == 2 && provinciaSplit[0].equals(codigoProvincia)) {
                                    existeProvincia = true;
                                    break;
                                }
                            }
                            if (!existeProvincia) {
                                System.out.println("El código de la provincia no existe en el archivo de provincias.");
                            } else {
                                provinciaValida = true;
                            }
                        } while (!provinciaValida);

                        String codigoCiudad;
                        boolean ciudadValida = false;
                        boolean ciudadAsociada = false;

                        do {
                            System.out.println("Ingrese el código de la ciudad del cliente:");
                            codigoCiudad = tec.nextLine();

                            // Verificar si el código de la ciudad existe en el archivo de ciudades
                            BufferedReader ciud = new BufferedReader(new FileReader(ciudades));
                            String lineacs;
                            while ((lineacs = ciud.readLine()) != null) {
                                String[] vc = lineacs.split("/");
                                if (vc.length == 3 && vc[0].equals(codigoCiudad)) {
                                    ciudadValida = true;
                                    break;
                                }
                            }
                            ciud.close();

                            if (!ciudadValida) {
                                System.out.println("El código de la ciudad no existe en el archivo de ciudades.");
                                continue;
                            }

                            // Verificar si el código de la ciudad está asociado a la provincia ingresada
                            BufferedReader ciud2 = new BufferedReader(new FileReader(ciudades));
                            String lineac2;
                            while ((lineac2 = ciud2.readLine()) != null) {
                                String[] vc2 = lineac2.split("/");
                                if (vc2.length == 3 && vc2[0].equals(codigoCiudad) && vc2[2].equals(codigoProvincia)) {
                                    ciudadAsociada = true;
                                    break;
                                }
                            }
                            ciud2.close();

                            if (!ciudadAsociada) {
                                System.out.println("El código de la ciudad no está asociado a la provincia ingresada.");
                                ciudadValida = false; // Reiniciar la validación de la ciudad
                            }
                        } while (!ciudadValida);
                        BufferedWriter clw = new BufferedWriter(new FileWriter(clientes, true));
                        String cliente = cedula + "/" + nombresApellidos + "/" + direccion + "/"
                                + codigoProvincia + "/" + codigoCiudad;

                        clw.write(cliente);
                        clw.newLine();
                        clw.flush();
                        clw.close();
                        System.out.println("El cliente ha sido agregado correctamente.");
                        break;

                    case "b": // Eliminar cliente
                        String cedulaEliminar;
                        boolean clienteEncontrado2 = false;
                        boolean clienteEnUso = false;
                        do {
                            System.out.println("Ingrese la cédula del cliente a eliminar:");
                            cedulaEliminar = tec.nextLine();
                            clienteEnUso=false;
                            // Verificar si la cédula existe en el archivo de clientes
                            BufferedReader clr2 = new BufferedReader(new FileReader(clientes));
                            String lineacl2;
                            while ((lineacl2 = clr2.readLine()) != null) {
                                String[] clv = lineacl2.split("/");
                                if (clv.length == 5 && clv[0].equals(cedulaEliminar)) {
                                    clienteEncontrado2 = true;
                                    String nombreCliente= clv[1];

                                    // Verificar si el cliente está en uso en el archivo de DatosFactura
                                    BufferedReader df = new BufferedReader(new FileReader(DatosFactura));
                                    String linedf;
                                    while ((linedf = df.readLine()) != null) {
                                        String[] dfv = linedf.split("/");
                                        if (dfv.length == 6 && dfv[1].equals(nombreCliente)||(dfv.length == 7 && dfv[1].equals(nombreCliente))) {
                                            clienteEnUso = true;
                                            break;
                                        }
                                    }
                                    df.close();
                                    break;
                                }
                            }
                            clr2.close();

                            if (!clienteEncontrado2) {
                                System.out.println("La cédula del cliente no existe en el archivo. Intente nuevamente.");
                            } else if (clienteEnUso) {
                                System.out.println("No se puede eliminar el cliente porque está asociado a facturas existentes. Intente nuevamente.");
                            }
                        } while (!clienteEncontrado2 || clienteEnUso);
                        // Eliminar el cliente del archivo de clientes
                        StringBuilder contenidoActualizado = new StringBuilder();
                        BufferedReader clr3 = new BufferedReader(new FileReader(clientes));
                        String lineacl3;
                        while ((lineacl3 = clr3.readLine()) != null) {
                            String[] clv = lineacl3.split("/");
                            if (clv.length == 5 && clv[0].equals(cedulaEliminar)) {
                                continue; // Omitir la línea correspondiente al cliente que se eliminará
                            }
                            contenidoActualizado.append(lineacl3).append("\n");
                        }
                        clr3.close();

                        FileWriter clf = new FileWriter(clientes);
                        clf.write(contenidoActualizado.toString());
                        clf.close();

                        System.out.println("El cliente ha sido eliminado correctamente.");
                        break;
                    case "c":
                        String cedulaModificar;
                        boolean clienteEncontrados=false;
                        boolean cedulaValida3=false;

                        do {
                            System.out.println("Ingrese la cédula del cliente a modificar:");
                            cedulaModificar = tec.nextLine();
                            clienteEncontrados = false;
                            cedulaValida3 = false;

                            if (!cedulaModificar.matches("\\d+")) {
                                System.out.println("La cédula debe contener solo números.");
                            } else if (cedulaModificar.length() != 10) {
                                System.out.println("La cédula debe tener 10 dígitos.");
                            } else {
                                BufferedReader clr4 = new BufferedReader(new FileReader(clientes));
                                String lineacl4;
                                while ((lineacl4 = clr4.readLine()) != null) {
                                    String[] clv = lineacl4.split("/");
                                    if (clv.length == 5 && clv[0].equals(cedulaModificar)) {
                                        clienteEncontrados = true;
                                        break;
                                    }
                                }
                                clr4.close();

                                if (clienteEncontrados) {
                                    cedulaValida3 = true;
                                } else {
                                    System.out.println("La cédula del cliente no existe en el archivo. Intente nuevamente.");
                                }
                            }
                        } while (!cedulaValida3 || !clienteEncontrados);

                        String nuevosNombresApellidos;
                        do {
                            System.out.println("Ingrese el nuevo nombre y apellidos del cliente (al menos dos palabras):");
                            nuevosNombresApellidos = tec.nextLine();
                            if (!nuevosNombresApellidos.trim().contains(" ")) {
                                System.out.println("El nombre y apellidos deben tener al menos dos palabras.");
                            }
                        } while (!nuevosNombresApellidos.trim().contains(" "));

                        String nuevaDireccion;
                        do {
                            System.out.println("Ingrese la nueva dirección del cliente (al menos tres palabras):");
                            nuevaDireccion = tec.nextLine();
                            if (nuevaDireccion.trim().split(" ").length < 3) {
                                System.out.println("La dirección debe tener al menos tres palabras.");
                            }
                        } while (nuevaDireccion.trim().split(" ").length < 3);

                        String nuevoCodigoProvincia;
                        boolean provinciaValida4 = false;
                        String lineapr4;
                        boolean existeProvincia4;

                        do {
                            System.out.println("Ingrese el nuevo código de la provincia del cliente:");
                            nuevoCodigoProvincia = tec.nextLine();
                            BufferedReader pr4 = new BufferedReader(new FileReader(provincias));
                            existeProvincia4 = false;
                            while ((lineapr4 = pr4.readLine()) != null) {
                                String[] provinciaSplit = lineapr4.split("/");
                                if (provinciaSplit.length == 2 && provinciaSplit[0].equals(nuevoCodigoProvincia)) {
                                    existeProvincia4 = true;
                                    break;
                                }
                            }
                            pr4.close();
                            if (!existeProvincia4) {
                                System.out.println("El código de la provincia no existe en el archivo de provincias.");
                            } else {
                                provinciaValida4 = true;
                            }
                        } while (!provinciaValida4);

                        String nuevoCodigoCiudad;
                        boolean ciudadValida5 = false;
                        boolean ciudadAsociada5 = false;

                        do {
                            System.out.println("Ingrese el nuevo código de la ciudad del cliente:");
                            nuevoCodigoCiudad = tec.nextLine();

                            // Verificar si el código de la ciudad existe en el archivo de ciudades
                            BufferedReader ciud = new BufferedReader(new FileReader(ciudades));
                            String lineacs;
                            while ((lineacs = ciud.readLine()) != null) {
                                String[] vc = lineacs.split("/");
                                if (vc.length == 3 && vc[0].equals(nuevoCodigoCiudad)) {
                                    ciudadValida5 = true;
                                    break;
                                }
                            }
                            ciud.close();

                            if (!ciudadValida5) {
                                System.out.println("El código de la ciudad no existe en el archivo de ciudades.");
                                continue;
                            }

                            // Verificar si el código de la ciudad está asociado a la provincia ingresada
                            BufferedReader ciud2 = new BufferedReader(new FileReader(ciudades));
                            String lineac2;
                            while ((lineac2 = ciud2.readLine()) != null) {
                                String[] vc2 = lineac2.split("/");
                                if (vc2.length == 3 && vc2[0].equals(nuevoCodigoCiudad) && vc2[2].equals(nuevoCodigoProvincia)) {
                                    ciudadAsociada5 = true;
                                    break;
                                }
                            }
                            ciud2.close();

                            if (!ciudadAsociada5) {
                                System.out.println("El código de la ciudad no está asociado a la provincia ingresada.");
                                ciudadValida = false; // Reiniciar la validación de la ciudad
                            }
                        } while (!ciudadValida5);

                        StringBuilder clienteModificado = new StringBuilder();

                        BufferedReader br = new BufferedReader(new FileReader(clientes));
                        String lineacl5;
                        boolean clienteModificado5 = false;

                        while ((lineacl5 = br.readLine()) != null) {
                            String[] clv = lineacl5.split("/");
                            if (clv.length == 5 && clv[0].equals(cedulaModificar)) {
                                clienteModificado5 = true;
                                String clienteNuevo = cedulaModificar + "/" + nuevosNombresApellidos + "/"
                                        + nuevaDireccion + "/" + nuevoCodigoProvincia + "/" + nuevoCodigoCiudad;
                                clienteModificado.append(clienteNuevo).append("\n");
                            } else {
                                clienteModificado.append(lineacl5).append("\n");
                            }
                        }

                        br.close();

                        if (clienteModificado5) {
                            FileWriter clf2 = new FileWriter(clientes);
                            clf2.write(clienteModificado.toString());
                            clf2.close();
                            System.out.println("El cliente ha sido modificado correctamente.");
                        } else {
                            System.out.println("La cédula del cliente no existe en el archivo.");
                        }
                        break;
                    case "d":
                        salir=true;
                        break;

                    default:
                        System.out.println("Opción inválida.");
                        break;
                }
            }    
            pr.close();
            cr.close();
            clr.close();
        } catch (IOException e) {
            System.out.println("Error al registrar, eliminar o modificar cliente.");
        }
    }

    public static void gestionarProveedores() {
        Scanner tec = new Scanner(System.in);
        try {
            File ciudades = new File("archivo/ciudades.txt");
            File provincias = new File("archivo/provincias.txt");
            File proveedores = new File("archivo/proveedores.txt");
            File productos = new File("archivo/productos.txt");
            BufferedWriter pvw = new BufferedWriter(new FileWriter(proveedores, true));
            BufferedReader pvr = new BufferedReader(new FileReader(proveedores));
            BufferedReader pr = new BufferedReader(new FileReader(provincias));
            BufferedReader cr = new BufferedReader(new FileReader(ciudades));
            String lineap,lineac,lineapv;
            boolean proveedorEncontrado = false;
            boolean salir = false;
            while (!salir) {
                System.out.println("a. Registrar proveedor");
                System.out.println("b. Eliminar proveedor");
                System.out.println("c. Modificar proveedor");
                System.out.println("d. Salir");
                System.out.println("Ingrese la opción deseada:");
                String opcion = tec.nextLine();

                switch (opcion) {
                    case "a": // Registro de proveedor
                        String ruc;
                        boolean rucExistente = false;
                        boolean rucValido = false;

                        do {
                            System.out.println("Ingrese el RUC del proveedor (13 dígitos):");
                            ruc = tec.nextLine();
                            if (!ruc.matches("\\d+")) {
                                System.out.println("El RUC debe contener solo números.");
                            } else if (ruc.length() != 13) {
                                System.out.println("El RUC debe tener 13 dígitos.");
                            } else {
                                rucValido = true;

                                // Verificar si el RUC ya está registrado en el archivo de proveedores
                                BufferedReader pvr2 = new BufferedReader(new FileReader(proveedores));
                                String lineapv2;
                                while ((lineapv2 = pvr2.readLine()) != null) {
                                    String[] proveedorSplit = lineapv2.split("/");
                                    if (proveedorSplit.length == 6 && proveedorSplit[0].equals(ruc)) {
                                        rucExistente = true;
                                        break;
                                    }
                                }
                                if (rucExistente) {
                                    System.out.println("El RUC del proveedor ya está registrado. Intente nuevamente.");
                                    rucValido = false;
                                }
                            }
                        } while (!rucValido || rucExistente);

                        String nombre;
                        do {
                            System.out.println("Ingrese el nombre del proveedor (al menos dos palabras):");
                            nombre = tec.nextLine();
                            if (!nombre.trim().contains(" ")) {
                                System.out.println("El nombre debe tener al menos dos palabras.");
                            }
                        } while (!nombre.trim().contains(" "));

                        String telefono;
                        do {
                            System.out.println("Ingrese el teléfono del proveedor (solo números):");
                            telefono = tec.nextLine();
                            if (!telefono.matches("\\d+")) {
                                System.out.println("El teléfono debe contener solo números.");
                            }
                        } while (!telefono.matches("\\d+"));

                        String direccion;
                        do {
                            System.out.println("Ingrese la dirección del proveedor (al menos dos palabras):");
                            direccion = tec.nextLine();
                            if (!direccion.trim().contains(" ")) {
                                System.out.println("La dirección debe tener al menos dos palabras.");
                            }
                        } while (!direccion.trim().contains(" "));

                        String codigoProvincia;
                        boolean provinciaValida = false;
                        String lineapr;
                        boolean existeProvincia;
                        do {
                            System.out.println("Ingrese el código de la provincia del proveedor:");
                            codigoProvincia = tec.nextLine();
                            pr = new BufferedReader(new FileReader(provincias));
                            existeProvincia = false;
                            while ((lineapr = pr.readLine()) != null) {
                                String[] provinciaSplit = lineapr.split("/");
                                if (provinciaSplit.length == 2 && provinciaSplit[0].equals(codigoProvincia)) {
                                    existeProvincia = true;
                                    break;
                                }
                            }
                            if (!existeProvincia) {
                                System.out.println("El código de la provincia no existe en el archivo de provincias.");
                            } else {
                                provinciaValida = true;
                            }
                        } while (!provinciaValida);
                
                        String codigoCiudad;
                        boolean ciudadValida = false;
                        boolean ciudadAsociada = false;

                        do {
                            System.out.println("Ingrese el código de la ciudad del proveedor:");
                            codigoCiudad = tec.nextLine();

                            // Verificar si el código de la ciudad existe en el archivo de ciudades
                            BufferedReader ciud = new BufferedReader(new FileReader(ciudades)); // Crear una copia del lector del archivo de ciudades
                            String lineacs;
                            while ((lineacs = ciud.readLine()) != null) {
                                String[] vc = lineacs.split("/");
                                if (vc.length == 3 && vc[0].equals(codigoCiudad)) {
                                    ciudadValida = true;
                                    break;
                                }
                            }
                            ciud.close();

                            if (!ciudadValida) {
                                System.out.println("El código de la ciudad no existe en el archivo de ciudades.");
                                continue;
                            }

                            // Verificar si el código de la ciudad está asociado a la provincia ingresada
                            BufferedReader ciud2 = new BufferedReader(new FileReader(ciudades)); // Crear otra copia del lector del archivo de ciudades
                            String lineac2;
                            while ((lineac2 = ciud2.readLine()) != null) {
                                String[] vc2 = lineac2.split("/");
                                if (vc2.length == 3 && vc2[0].equals(codigoCiudad) && vc2[2].equals(codigoProvincia)) {
                                    ciudadAsociada = true;
                                    break;
                                }
                            }
                            ciud2.close();

                            if (!ciudadAsociada) {
                                System.out.println("El código de la ciudad no está asociado a la provincia ingresada.");
                                ciudadValida = false; // Reiniciar la validación de la ciudad
                            }

                        } while (!ciudadValida);

                        String proveedor = ruc + "/" + nombre + "/" + telefono + "/" + direccion + "/"
                                + codigoProvincia + "/" + codigoCiudad;

                        pvw.write(proveedor);
                        pvw.newLine();
                        pvw.flush();
                        System.out.println("El proveedor ha sido registrado correctamente.");
                        break;


                    case "b": // Eliminación de proveedor
                        String rucEliminar;
                        boolean rucExistente2 = false;

                        do {
                            System.out.println("Ingrese el RUC del proveedor a eliminar:");
                            rucEliminar = tec.nextLine();
                            rucExistente2 = false;

                            // Verificar si el RUC existe en el archivo de proveedores
                            pvr = new BufferedReader(new FileReader(proveedores));
                            while ((lineapv = pvr.readLine()) != null) {
                                String[] proveedorSplit = lineapv.split("/");
                                if (proveedorSplit.length == 6 && proveedorSplit[0].equals(rucEliminar)) {
                                    rucExistente2 = true;
                                    break;
                                }
                            }

                            if (!rucExistente2) {
                                System.out.println("El RUC del proveedor no existe en el archivo. Intente nuevamente.");
                            } else {
                                boolean proveedorEnUso = false;

                                // Verificar si el proveedor está en uso en el archivo de productos
                                BufferedReader prd = new BufferedReader(new FileReader(productos));
                                String lineaprd;
                                while ((lineaprd = prd.readLine()) != null) {
                                    String[] productoSplit = lineaprd.split("/");
                                    if (productoSplit.length == 4 && productoSplit[3].equals(rucEliminar)) {
                                        proveedorEnUso = true;
                                        break;
                                    }
                                }

                                if (proveedorEnUso) {
                                    System.out.println("El proveedor está en uso en el archivo de productos y no se puede eliminar.");
                                    rucExistente = false; // Volver a pedir el ingreso del RUC
                                } else {
                                    StringBuilder contenidoActualizado = new StringBuilder();

                                    // Eliminar el proveedor del archivo de proveedores
                                    pvr = new BufferedReader(new FileReader(proveedores));
                                    while ((lineapv = pvr.readLine()) != null) {
                                        String[] proveedorSplit = lineapv.split("/");
                                        if (proveedorSplit.length == 6 && proveedorSplit[0].equals(rucEliminar)) {
                                            proveedorEncontrado = true;
                                        } else {
                                            contenidoActualizado.append(lineapv).append("\n");
                                        }
                                    }

                                    if (proveedorEncontrado) {
                                        FileWriter pvf = new FileWriter(proveedores);
                                        pvf.write(contenidoActualizado.toString());
                                        pvf.close();
                                        System.out.println("El proveedor ha sido eliminado correctamente.");
                                    } else {
                                        System.out.println("El RUC del proveedor no existe en el archivo.");
                                    }
                                }
                            }
                        } while (!rucExistente2);
                        break;
                    
                    case "c":
                        String rucModificar;
                        boolean rucExistente3 = false;
                        boolean rucValido3 = false;

                        do {
                            System.out.println("Ingrese el RUC del proveedor a modificar:");
                            rucModificar = tec.nextLine();
                            if (!rucModificar.matches("\\d+")) {
                                System.out.println("El RUC debe contener solo números.");
                            } else if (rucModificar.length() != 13) {
                                System.out.println("El RUC debe tener 13 dígitos.");
                            } else {
                                rucValido3 = true;

                                // Verificar si el RUC ya está registrado en el archivo de proveedores
                                BufferedReader pvr2 = new BufferedReader(new FileReader(proveedores));
                                String lineapv2;
                                while ((lineapv2 = pvr2.readLine()) != null) {
                                    String[] proveedorSplit = lineapv2.split("/");
                                    if (proveedorSplit.length == 6 && proveedorSplit[0].equals(rucModificar)) {
                                        rucExistente3 = true;
                                        break;
                                    }
                                }
                                pvr2.close();

                                if (!rucExistente3) {
                                    System.out.println("El RUC del proveedor no existe en el archivo. Intente nuevamente.");
                                    rucValido3 = false;
                                }
                            }
                        } while (!rucValido3 || !rucExistente3);

                        String nombreModificar;
                        boolean nombreValido = false;
                        do {
                            System.out.println("Ingrese el nuevo nombre del proveedor (al menos dos palabras):");
                            nombreModificar = tec.nextLine();
                            if (!nombreModificar.trim().contains(" ")) {
                                System.out.println("El nombre debe tener al menos dos palabras.");
                            } else {
                                nombreValido = true;
                            }
                        } while (!nombreValido);

                        String telefonoModificar;
                        boolean telefonoValido = false;
                        do {
                            System.out.println("Ingrese el nuevo teléfono del proveedor (solo números):");
                            telefonoModificar = tec.nextLine();
                            if (!telefonoModificar.matches("\\d+")) {
                                System.out.println("El teléfono debe contener solo números.");
                            } else {
                                telefonoValido = true;
                            }
                        } while (!telefonoValido);

                        String direccionModificar;
                        boolean direccionValida = false;
                        do {
                            System.out.println("Ingrese la nueva dirección del proveedor (al menos dos palabras):");
                            direccionModificar = tec.nextLine();
                            if (!direccionModificar.trim().contains(" ")) {
                                System.out.println("La dirección debe tener al menos dos palabras.");
                            } else {
                                direccionValida = true;
                            }
                        } while (!direccionValida);

                        String codigoProvinciaModificar;
                        boolean provinciaValida2 = false;
                        String lineapr2;
                        boolean existeProvincia2;
                        do {
                            System.out.println("Ingrese el nuevo código de la provincia del proveedor:");
                            codigoProvinciaModificar = tec.nextLine();
                            pr = new BufferedReader(new FileReader(provincias));
                            existeProvincia2 = false;
                            while ((lineapr2 = pr.readLine()) != null) {
                                String[] provinciaSplit = lineapr2.split("/");
                                if (provinciaSplit.length == 2 && provinciaSplit[0].equals(codigoProvinciaModificar)) {
                                    existeProvincia2 = true;
                                    break;
                                }
                            }
                            if (!existeProvincia2) {
                                System.out.println("El código de la provincia no existe en el archivo de provincias.");
                            } else {
                                provinciaValida2 = true;
                            }
                        } while (!provinciaValida2);

                        String codigoCiudadModificar;
                        boolean ciudadValida2 = false;
                        boolean ciudadAsociada2 = false;

                        do {
                            System.out.println("Ingrese el nuevo código de la ciudad del proveedor:");
                            codigoCiudadModificar = tec.nextLine();

                            // Verificar si el código de la ciudad existe en el archivo de ciudades
                            BufferedReader ciud = new BufferedReader(new FileReader(ciudades));
                            String lineacs;
                            while ((lineacs = ciud.readLine()) != null) {
                                String[] vc = lineacs.split("/");
                                if (vc.length == 3 && vc[0].equals(codigoCiudadModificar)) {
                                    ciudadValida2 = true;
                                    break;
                                }
                            }
                            ciud.close();

                            if (!ciudadValida2) {
                                System.out.println("El código de la ciudad no existe en el archivo de ciudades.");
                                continue;
                            }

                            // Verificar si el código de la ciudad está asociado a la provincia ingresada
                            BufferedReader ciud2 = new BufferedReader(new FileReader(ciudades));
                            String lineac2;
                            while ((lineac2 = ciud2.readLine()) != null) {
                                String[] vc2 = lineac2.split("/");
                                if (vc2.length == 3 && vc2[0].equals(codigoCiudadModificar) && vc2[2].equals(codigoProvinciaModificar)) {
                                    ciudadAsociada2 = true;
                                    break;
                                }
                            }
                            ciud2.close();

                            if (!ciudadAsociada2) {
                                System.out.println("El código de la ciudad no está asociado a la provincia ingresada.");
                                ciudadValida2 = false; // Reiniciar la validación de la ciudad
                            }
                        } while (!ciudadValida2);

                        String proveedorModificado = rucModificar + "/" + nombreModificar + "/" + telefonoModificar + "/"
                                + direccionModificar + "/" + codigoProvinciaModificar + "/" + codigoCiudadModificar;

                        // Realizar la modificación del proveedor en el archivo de proveedores
                        StringBuilder contenidoModificado = new StringBuilder();
                        String lineaspv; 
                        BufferedReader pvr2 = new BufferedReader(new FileReader(proveedores));
                        while ((lineaspv = pvr2.readLine()) != null) {
                            String[] proveedorSplit = lineaspv.split("/");
                            if (proveedorSplit.length == 6 && proveedorSplit[0].equals(rucModificar)) {
                                contenidoModificado.append(proveedorModificado).append("\n");
                            } else {
                                contenidoModificado.append(lineaspv).append("\n");
                            }
                        }
                        pvr2.close();
                        BufferedWriter pvf = new BufferedWriter(new FileWriter(proveedores));
                        pvf.write(contenidoModificado.toString());
                        pvf.flush();
                        pvf.close();
                        System.out.println("El proveedor ha sido modificado correctamente.");
                        break;
                    case "d":
                        salir=true;
                        break;

                    default:
                        System.out.println("Opción inválida.");
                        break;
                }
            }
            pvr.close();
            pr.close();
            cr.close();
        } catch (IOException e) {
            System.out.println("Error al registrar o eliminar proveedor.");
        }
    }

    public static void generarFactura() {
        Scanner tec = new Scanner(System.in);
        int numeroFactura = obtenerProximoNumeroFactura();
        try (BufferedWriter facturaWriter = new BufferedWriter(new FileWriter("archivo/facturas.txt", true));
            BufferedWriter datosFacturaWriter = new BufferedWriter(new FileWriter("archivo/DatosFactura.txt", true))) {
            String codigoCliente;
            while (true) {
                System.out.println("Ingrese el código del cliente (o 'q' para salir):");
                codigoCliente = tec.next();
                tec.nextLine();

                if (codigoCliente.equals("q")) {
                    break;
                }

                String cliente = obtenerDatosCliente(codigoCliente);

                if (cliente == null) {
                    System.out.println("Cliente no encontrado. Intente nuevamente.");
                    continue;
                }

                List<String[]> detallesFactura = new ArrayList<>();

                while (true) {
                    System.out.println("Ingrese el código del producto (o 'q' para finalizar la factura):");
                    String codigoProducto = tec.nextLine();

                    if (codigoProducto.equals("q")) {
                        break;
                    }
                    
                    if (productoYaCompradoEnFactura(codigoProducto, detallesFactura)) {
                        System.out.println("Producto ya comprado en esta factura. Intente nuevamente.");
                        continue;
                    }

                    String datosProducto = obtenerDatosProducto(codigoProducto);

                    if (datosProducto == null) {
                        System.out.println("Producto no encontrado. Intente nuevamente.");
                        continue;
                    }
                    
                    double precioVenta;
                    do {
                        System.out.println("Ingrese el precio de venta:");
                        String precio = tec.nextLine();

                        // Verificar si el input es un número entero o decimal
                        if (precio.matches("\\d+(\\.\\d+)?")) {
                            precioVenta = Double.parseDouble(precio);
                            break;
                        } else {
                            System.out.println("El valor ingresado no es un número válido. Intente nuevamente.");
                        }
                    } while (true);

                    int cantidad;
                    do {
                        System.out.println("Ingrese la cantidad a vender:");
                        String canti = tec.nextLine();

                        // Verificar si el input es un número entero
                        if (canti.matches("\\d+")) {
                            cantidad = Integer.parseInt(canti);
                            break;
                        } else {
                            System.out.println("El valor ingresado no es un número entero válido. Intente nuevamente.");
                        }
                    } while (true);
                    
                    int stockActual = obtenerStockProducto(codigoProducto);

                    if (cantidad > stockActual) {
                        System.out.println("No hay suficiente stock disponible. Intente nuevamente.");
                        continue;
                    }

                    double total = precioVenta * cantidad;
                    String[] detalleFactura = {codigoProducto, String.valueOf(cantidad), String.valueOf(precioVenta)};
                    detallesFactura.add(detalleFactura);

                    // Actualizar el stock del producto
                    restarStockProducto(codigoProducto, cantidad);
                }

                // Imprimir la factura
                System.out.println("Factura #" + numeroFactura);
                System.out.println("Cliente: " + cliente);
                System.out.println("----------------------------------");
                System.out.println("| CODIGO PRODUCTO | CANTIDAD | PRECIO DE VENTA |");
                System.out.println("----------------------------------");

                double totalFactura = 0.0;
                for (String[] detalle : detallesFactura) {
                    String codigoProducto = detalle[0];
                    int cantidad = Integer.parseInt(detalle[1]);
                    double precioVenta = Double.parseDouble(detalle[2]);
                    double totalProducto = cantidad * precioVenta;

                    totalFactura += totalProducto;

                    System.out.printf("| %-15s | %8d | %15.2f |\n", codigoProducto, cantidad, precioVenta);
                }

                System.out.println("----------------------------------");
                System.out.printf("| %-15s |                %15.2f |\n", "TOTAL", totalFactura);
                System.out.println("----------------------------------");
                System.out.println();

                // Guardar la factura en el archivo
                facturaWriter.write("Factura #" + numeroFactura + "\n");
                facturaWriter.write("Cliente: " + cliente + "\n");
                facturaWriter.write("----------------------------------\n");
                facturaWriter.write("| CODIGO PRODUCTO | CANTIDAD | PRECIO DE VENTA |\n");
                facturaWriter.write("----------------------------------\n");

                for (String[] detalle : detallesFactura) {
                    String codigoProducto = detalle[0];
                    int cantidad = Integer.parseInt(detalle[1]);
                    double precioVenta = Double.parseDouble(detalle[2]);

                    facturaWriter.write(String.format("| %-15s | %8d | %15.2f |\n", codigoProducto, cantidad, precioVenta));
                }

                facturaWriter.write("----------------------------------\n");
                facturaWriter.write(String.format("| %-15s |                %15.2f |\n", "TOTAL", totalFactura));
                facturaWriter.write("----------------------------------\n\n");
                
                for (String[] detalle : detallesFactura) {
                    String codigoProducto = detalle[0];
                    int cantidad = Integer.parseInt(detalle[1]);
                    double precioVenta = Double.parseDouble(detalle[2]);

                    String facturaLine = String.format("%d/%s/%s/%d/%.2f/%.2f\n", numeroFactura, cliente, codigoProducto, cantidad, precioVenta,totalFactura);
                    datosFacturaWriter.write(facturaLine);
                }
                numeroFactura++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String obtenerDatosCliente(String codigoCliente) {
        try (BufferedReader reader = new BufferedReader(new FileReader("archivo/clientes.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] datosCliente = line.split("/");
                if (datosCliente[0].equals(codigoCliente)) {
                    return datosCliente[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String obtenerDatosProducto(String codigoProducto) {
        try (BufferedReader reader = new BufferedReader(new FileReader("archivo/productos.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] datosProducto = line.split("/");
                if (datosProducto[0].equals(codigoProducto)) {
                    return line;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int obtenerStockProducto(String codigoProducto) {
        try (BufferedReader reader = new BufferedReader(new FileReader("archivo/productos.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] datosProducto = line.split("/");
                if (datosProducto[0].equals(codigoProducto)) {
                    return Integer.parseInt(datosProducto[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void restarStockProducto(String codigoProducto, int cantidad) {
        try {
            File oficial = new File("archivo/productos.txt");
            File temporal = new File("archivo/productos_temp.txt");
            BufferedReader lectura = new BufferedReader(new FileReader(oficial));
            BufferedWriter escribir = new BufferedWriter(new FileWriter(temporal));
            String line;

            while ((line = lectura.readLine()) != null) {
                String[] datosProducto = line.split("/");
                if (datosProducto[0].equals(codigoProducto)) {
                    int stockActual = Integer.parseInt(datosProducto[2]);
                    int nuevoStock = stockActual - cantidad;
                    datosProducto[2] = String.valueOf(nuevoStock);
                    line = String.join("/", datosProducto);
                }

                escribir.write(line + "\n");
            }

            lectura.close();
            escribir.close();

            if (!oficial.delete()) {
                System.out.println("No se pudo actualizar el stock del producto.");
                return;
            }

            if (!temporal.renameTo(oficial)) {
                System.out.println("No se pudo actualizar el stock del producto.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean productoYaCompradoEnFactura(String codigoProducto, List<String[]> detallesFactura) {
        for (String[] detalle : detallesFactura) {
            String productoFactura = detalle[0];
            if (productoFactura.equals(codigoProducto)) {
                return true;
            }
        }
        return false;
    }
    
    public static int obtenerProximoNumeroFactura() {
        int maxNumeroFactura = 0;
        try (BufferedReader datosFacturasReader = new BufferedReader(new FileReader("archivo/DatosFactura.txt"))) {
            String line;
            while ((line = datosFacturasReader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;  // Ignorar líneas vacías
                }
                String[] datosFactura = line.split("/");
                if (datosFactura.length > 0) {
                    try {
                        int numeroFactura = Integer.parseInt(datosFactura[0]);
                        if (numeroFactura > maxNumeroFactura) {
                            maxNumeroFactura = numeroFactura;
                        }
                    } catch (NumberFormatException e) {
                        // Manejar un número de factura no válido
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return maxNumeroFactura + 1;
    }

    public static void generarCompra() {
        Scanner tec = new Scanner(System.in);
        int numeroFactura = 1;

        try (BufferedWriter facturaWriter = new BufferedWriter(new FileWriter("archivo/facturas_compra.txt", true))) {
            while (true) {
                System.out.println("Ingrese el código del proveedor (o 'q' para salir):");
                String codigoProveedor = tec.nextLine();

                if (codigoProveedor.equals("q")){
                    break;
                }
                
                boolean producto = validarProveedor(codigoProveedor);

                if (!producto) {
                    System.out.println("Proveedor no encontrado. Intente nuevamente.");
                    continue;
                }
                
                List<String[]> detallesFactura = new ArrayList<>();

                while (true) {
                    System.out.println("Ingrese el código del producto (o 'q' para finalizar la compra):");
                    String codigoProducto = tec.nextLine();

                    if (codigoProducto.equals("q")) {
                        break;
                    }

                    if (!validarProductoProveedor(codigoProducto, codigoProveedor)) {
                        System.out.println("Producto no válido para el proveedor. Intente nuevamente.");
                        continue;
                    }
                    double precioCompra;
                    do {
                        System.out.println("Ingrese el precio de compra:");
                        String precio = tec.nextLine();

                        // Verificar si el input es un número entero o decimal
                        if (precio.matches("\\d+(\\.\\d+)?")) {
                            precioCompra = Double.parseDouble(precio);
                            break;
                        } else {
                            System.out.println("El valor ingresado no es un número válido. Intente nuevamente.");
                        }
                    } while (true);

                    int cantidad;
                    do {
                        System.out.println("Ingrese la cantidad a comprar:");
                        String canti = tec.nextLine();

                        // Verificar si el input es un número entero
                        if (canti.matches("\\d+")) {
                            cantidad = Integer.parseInt(canti);
                            break;
                        } else {
                            System.out.println("El valor ingresado no es un número entero válido. Intente nuevamente.");
                        }
                    } while (true);

                    String[] detalleFactura = {codigoProducto, String.valueOf(cantidad), String.valueOf(precioCompra)};
                    detallesFactura.add(detalleFactura);
                }

                double totalFactura = calcularTotalFactura(detallesFactura);

                // Imprimir la factura de compra
                System.out.println("Factura de Compra #" + numeroFactura);
                System.out.println("Proveedor: " + obtenerDatosProveedor(codigoProveedor));
                System.out.println("----------------------------------");
                System.out.println("| CODIGO PRODUCTO | CANTIDAD | PRECIO DE COMPRA |");
                System.out.println("----------------------------------");

                for (String[] detalle : detallesFactura) {
                    String codigoProducto = detalle[0];
                    int cantidad = Integer.parseInt(detalle[1]);
                    double precioCompra = Double.parseDouble(detalle[2]);

                    System.out.printf("| %-15s | %8d | %16.2f |\n", codigoProducto, cantidad, precioCompra);
                }

                System.out.println("----------------------------------");
                System.out.printf("| %-15s |                 %16.2f |\n", "TOTAL", totalFactura);
                System.out.println("----------------------------------");
                System.out.println();

                // Guardar la factura de compra en el archivo
                facturaWriter.write("Factura de Compra #" + numeroFactura + "\n");
                facturaWriter.write("Proveedor: " + obtenerDatosProveedor(codigoProveedor) + "\n");
                facturaWriter.write("----------------------------------\n");
                facturaWriter.write("| CODIGO PRODUCTO | CANTIDAD | PRECIO DE COMPRA |\n");
                facturaWriter.write("----------------------------------\n");

                for (String[] detalle : detallesFactura) {
                    String codigoProducto = detalle[0];
                    int cantidad = Integer.parseInt(detalle[1]);
                    double precioCompra = Double.parseDouble(detalle[2]);

                    facturaWriter.write(String.format("| %-15s | %8d | %16.2f |\n", codigoProducto, cantidad, precioCompra));
                }

                facturaWriter.write("----------------------------------\n");
                facturaWriter.write(String.format("| %-15s |                 %16.2f |\n", "TOTAL", totalFactura));
                facturaWriter.write("----------------------------------\n\n");

                // Aumentar el stock de los productos respectivos
                aumentarStockProductos(detallesFactura);

                numeroFactura++;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean validarProveedor(String codigoProveedor) {
        try (BufferedReader reader = new BufferedReader(new FileReader("archivo/proveedores.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] datosProveedor = line.split("/");
                if (datosProveedor[0].equals(codigoProveedor)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String obtenerDatosProveedor(String codigoProveedor) {
        try (BufferedReader reader = new BufferedReader(new FileReader("archivo/proveedores.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] datosProveedor = line.split("/");
                if (datosProveedor[0].equals(codigoProveedor)) {
                    return datosProveedor[1];
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean validarProductoProveedor(String codigoProducto, String codigoProveedor) {
        try (BufferedReader reader = new BufferedReader(new FileReader("archivo/productos.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] datosProducto = line.split("/");
                if (datosProducto[0].equals(codigoProducto) && datosProducto[3].equals(codigoProveedor)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static double calcularTotalFactura(List<String[]> detallesFactura) {
        double totalFactura = 0.0;

        for (String[] detalle : detallesFactura) {
            int cantidad = Integer.parseInt(detalle[1]);
            double precioCompra = Double.parseDouble(detalle[2]);
            double totalProducto = cantidad * precioCompra;

            totalFactura += totalProducto;
        }

        return totalFactura;
    }

    public static void aumentarStockProductos(List<String[]> detallesFactura) {
        try {
            File inputFile = new File("archivo/productos.txt");
            File tempFile = new File("archivo/productos_temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;

            while ((line = reader.readLine()) != null) {
                String[] datosProducto = line.split("/");

                for (String[] detalle : detallesFactura) {
                    String codigoProducto = detalle[0];
                    int cantidad = Integer.parseInt(detalle[1]);

                    if (datosProducto[0].equals(codigoProducto)) {
                        int stockActual = Integer.parseInt(datosProducto[2]);
                        int nuevoStock = stockActual + cantidad;
                        datosProducto[2] = String.valueOf(nuevoStock);
                        line = String.join("/", datosProducto);
                        break;
                    }
                }

                writer.write(line + "\n");
            }

            writer.close();
            reader.close();

            if (!inputFile.delete()) {
                System.out.println("No se pudo actualizar el stock de los productos.");
                return;
            }

            if (!tempFile.renameTo(inputFile)) {
                System.out.println("No se pudo actualizar el stock de los productos.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void gestionarProvincias() {
        Scanner tec = new Scanner(System.in);
        
        try {
            File provincias = new File("archivo/provincias.txt");
            File ciudades = new File("archivo/ciudades.txt");
            BufferedWriter pw = new BufferedWriter(new FileWriter(provincias, true));
            BufferedReader pr = new BufferedReader(new FileReader(provincias));
            BufferedReader cr = new BufferedReader(new FileReader(ciudades));
            String lineap;
            
            boolean salir = false;
            
            while (!salir) {
                System.out.println("a. Agregar provincia");
                System.out.println("b. Eliminar provincia");
                System.out.println("c. Modificar provincia");
                System.out.println("d. Salir");
                System.out.println("Ingrese la opción deseada:");
                String opcion = tec.nextLine();
                
                switch (opcion) {
                    case "a": // Agregar provincia
                        String codigoP;
                        String nombreP;
                        boolean provinciaExistente;
                        
                        do {
                            System.out.println("Ingrese el código de la provincia:");
                            codigoP = tec.nextLine();
                            
                            // Verificar si el código ya existe en el archivo
                            provinciaExistente = false;
                            BufferedReader pr2 = new BufferedReader(new FileReader(provincias));
                            String lineap2;
                            while ((lineap2 = pr2.readLine()) != null) {
                                String[] vp = lineap2.split("/");
                                if (vp.length == 2 && vp[0].equals(codigoP)) {
                                    provinciaExistente = true;
                                    break;
                                }
                            }
                            
                            if (provinciaExistente) {
                                System.out.println("El código de la provincia ya existe. Intente nuevamente.");
                            }
                        } while (provinciaExistente);

                        do {
                            System.out.println("Ingrese el nombre de la provincia:");
                            nombreP = tec.nextLine();
                            
                            // Verificar si el nombre de la provincia ya existe en el archivo
                            provinciaExistente = false;
                            BufferedReader pr3 = new BufferedReader(new FileReader(provincias)); // Volver a leer el archivo
                            String lineap3;
                            while ((lineap3 = pr3.readLine()) != null) {
                                String[] vp = lineap3.split("/");
                                if (vp.length == 2 && vp[1].equals(nombreP)) {
                                    provinciaExistente = true;
                                    break;
                                }
                            }
                            
                            if (provinciaExistente) {
                                System.out.println("El nombre de la provincia ya existe. Intente nuevamente.");
                            }
                        } while (provinciaExistente);
                        
                        pw.write(codigoP + "/" + nombreP);
                        pw.newLine();
                        pw.flush();
                        System.out.println("La provincia ha sido agregada correctamente.");
                        break;
                        
                    case "b": // Eliminar provincia
                        boolean eliminarProvincia = false;
                        do {
                            System.out.println("Ingrese el código de la provincia a eliminar:");
                            String codigoEliminar = tec.nextLine();

                            boolean provinciaEncontrada = false;
                            boolean provinciaEnUso = false;
                            StringBuilder contenidoActualizado = new StringBuilder();

                            BufferedReader cr2 = new BufferedReader(new FileReader(ciudades));
                            String lineap2;
                            while ((lineap2 = cr2.readLine()) != null) {
                                String[] vc = lineap2.split("/");
                                if (vc.length == 3 && vc[2].equals(codigoEliminar)) {
                                    provinciaEnUso = true;
                                    break;
                                }
                            }
                            // Si la provincia está en uso, no se permite eliminarla
                            if (provinciaEnUso) {
                                System.out.println("La provincia está en uso en el archivo de ciudades. No se puede eliminar.");
                            } else {
                                BufferedReader pr2 = new BufferedReader(new FileReader(provincias));
                                String lineap3;
                                while ((lineap3 = pr2.readLine()) != null) {
                                    String[] vp = lineap3.split("/");
                                    if (vp.length == 2 && vp[0].equals(codigoEliminar)) {
                                        provinciaEncontrada = true;
                                    } else {
                                        contenidoActualizado.append(lineap3).append("\n");
                                    }
                                }

                                if (provinciaEncontrada) {
                                    FileWriter pf = new FileWriter(provincias);
                                    pf.write(contenidoActualizado.toString());
                                    pf.close();
                                    System.out.println("La provincia ha sido eliminada correctamente.");
                                    eliminarProvincia = true;
                                } else {
                                    System.out.println("El código de la provincia no existe en el archivo.");
                                }
                            }
                        } while (!eliminarProvincia);
                        break;

                    case "c":
                        boolean modificarProvincia = false;
                        
                        do {
                            System.out.println("Ingrese el código de la provincia a modificar:");
                            String codigoM = tec.nextLine();
                            boolean provinciaE = false;
                            StringBuilder contenidoA = new StringBuilder();

                            // Verificar si el código de provincia existe en el archivo
                            boolean codigoExistente = false;
                            BufferedReader pr4 = new BufferedReader(new FileReader(provincias)); // Abrirlo nuevamente para leer desde el inicio
                            String lineap4;
                            while ((lineap4 = pr4.readLine()) != null) {
                                String[] vp = lineap4.split("/");
                                if (vp.length == 2 && vp[0].equals(codigoM)) {
                                    codigoExistente = true;
                                    break;
                                }
                            }

                            if (!codigoExistente) {
                                System.out.println("El código de la provincia no existe en el archivo. Intente nuevamente.");
                                continue;
                            }

                            pr.close(); // Cerrar el BufferedReader antes de abrirlo nuevamente
                            BufferedReader pr5 = new BufferedReader(new FileReader(provincias)); // Abrirlo nuevamente para leer desde el inicio
                            String lineap5;
                            while ((lineap5 = pr5.readLine()) != null) {
                                String[] vp = lineap5.split("/");
                                if (vp.length == 2 && vp[0].equals(codigoM)) {
                                    provinciaE = true;

                                    String nuevoNombreP;
                                    boolean nombreExistente;

                                    do {
                                        System.out.println("Ingrese el nuevo nombre de la provincia:");
                                        nuevoNombreP = tec.nextLine();

                                        // Verificar si el nombre de la provincia ya existe en el archivo
                                        nombreExistente = false;
                                        BufferedReader pr6 = new BufferedReader(new FileReader(provincias)); // Leer en un nuevo BufferedReader
                                        String lineap6;
                                        while ((lineap6 = pr6.readLine()) != null) {
                                            String[] vn = lineap6.split("/");
                                            if (vn.length == 2 && vn[1].equals(nuevoNombreP)) {
                                                nombreExistente = true;
                                                break;
                                            }
                                        }

                                        if (nombreExistente) {
                                            System.out.println("El nombre de la provincia ya existe en el archivo. Intente nuevamente.");
                                        }
                                    } while (nombreExistente);

                                    contenidoA.append(vp[0]).append("/").append(nuevoNombreP).append("\n");
                                } else {
                                    contenidoA.append(lineap5).append("\n");
                                }
                            }

                            if (provinciaE) {
                                FileWriter pf = new FileWriter(provincias);
                                pf.write(contenidoA.toString());
                                pf.close();
                                System.out.println("La provincia ha sido modificada correctamente.");
                                modificarProvincia = true;
                            } else {
                                System.out.println("El código de la provincia no existe en el archivo.");
                            }
                        } while (!modificarProvincia);
                        
                        break;

                    case "d": // Salir
                        salir = true;
                        break;

                    default:
                        System.out.println("Opción inválida.");
                        break;
                }
            }
            
            pr.close();
            cr.close();
            pw.close();
        } catch (IOException e) {
            System.out.println("Error al registrar, eliminar o modificar provincia.");
        }
    }    

    public static void gestionarCiudades() {
        Scanner sec = new Scanner(System.in);
        
        try {
            File ciudades = new File("archivo/ciudades.txt");
            File provincias = new File("archivo/provincias.txt");
            File proveedores = new File("archivo/proveedores.txt");
            File clientes = new File("archivo/clientes.txt");
            BufferedWriter cw = new BufferedWriter(new FileWriter(ciudades,true));
            BufferedReader pr = new BufferedReader(new FileReader(provincias));
            BufferedReader cr = new BufferedReader(new FileReader(ciudades));
            BufferedReader prr;
            BufferedReader clr;
            String lineac, lineap, codigoC,nombreC,lineapv,lineacv,nuevoNombreC ;
            boolean provinciaRegistrada = false;
            boolean codigoExistente = false;
            boolean registrarCiudad = false;
            boolean salir = false;
            while (!salir) {
                System.out.println("a. Registrar ciudad");
                System.out.println("b. Eliminar ciudad");
                System.out.println("c. Modificar ciudad");
                System.out.println("d. Regresar");
                System.out.println("Ingrese la opción deseada:");
                String opcion = sec.nextLine();
                
                switch (opcion) {
                    case "a": // Registro de ciudad
                        do {
                            System.out.println("Ingrese el código de la provincia:");
                            String codigoP = sec.nextLine();
                            provinciaRegistrada = false;
                            pr = new BufferedReader(new FileReader(provincias)); // Volver a leer el archivo
                            while ((lineap = pr.readLine()) != null) {
                                String[] vp = lineap.split("/");
                                if (vp.length == 2 && vp[0].equals(codigoP)) {
                                    provinciaRegistrada = true;
                                    break;
                                }
                            }

                            if (!provinciaRegistrada) {
                                System.out.println("El código de la provincia no está registrado. Intente nuevamente.");
                                continue;
                            }

                            do {
                                codigoExistente = false;
                                System.out.println("Ingrese el código de la ciudad:");
                                codigoC = sec.nextLine();
                                cr = new BufferedReader(new FileReader(ciudades)); // Volver a leer el archivo
                                while ((lineac = cr.readLine()) != null) {
                                    String[] vc = lineac.split("/");
                                    if (vc.length == 3 && vc[0].equals(codigoC)) {
                                        codigoExistente = true;
                                        break;
                                    }
                                }
                                if (codigoExistente) {
                                    System.out.println("El código de la ciudad ya existe en el archivo. Intente nuevamente.");
                                }
                            } while (codigoExistente);
                            do {
                                codigoExistente = false;
                                System.out.println("Ingrese el nombre de la ciudad:");
                                nombreC = sec.nextLine();
                                cr = new BufferedReader(new FileReader(ciudades)); // Volver a leer el archivo
                                while ((lineac = cr.readLine()) != null) {
                                    String[] vc = lineac.split("/");
                                    if (vc.length == 3 && vc[1].equals(nombreC)) {
                                        codigoExistente = true;
                                        break;
                                    }
                                }
                                if (codigoExistente) {
                                    System.out.println("El nombre de la ciudad ya existe en el archivo. Intente nuevamente.");
                                }
                            } while (codigoExistente);

                            cw.write(codigoC + "/" + nombreC + "/" + codigoP);
                            cw.newLine();
                            cw.flush();
                            System.out.println("La ciudad ha sido registrada correctamente.");
                            registrarCiudad = true;
                        } while (!registrarCiudad);

                        break;
                    case "b": // Eliminación de ciudad
                        boolean eliminarCiudad = false;
                        do {
                            System.out.println("Ingrese el código de la ciudad a eliminar:");
                            String codigoEliminar = sec.nextLine();
                            boolean ciudadEncontrada = false;
                            boolean ciudadEnUso = false;
                            StringBuilder contenidoActualizado = new StringBuilder();

                            // Verificar si la ciudad existe en el archivo de ciudades
                            cr = new BufferedReader(new FileReader(ciudades)); // Volver a leer el archivo
                            while ((lineac = cr.readLine()) != null) {
                                String[] vc = lineac.split("/");
                                if (vc.length == 3 && vc[0].equals(codigoEliminar)) {
                                    ciudadEncontrada = true;
                                    break;
                                }
                            }

                            if (!ciudadEncontrada) {
                                System.out.println("El código de la ciudad no existe en el archivo. Intente nuevamente.");
                                continue;
                            }

                            // Verificar si la ciudad está en uso en el archivo de proveedores
                            prr = new BufferedReader(new FileReader(proveedores)); // Volver a leer el archivo
                            while ((lineapv = prr.readLine()) != null) {
                                String[] vpv = lineapv.split("/");
                                if (vpv.length == 6 && vpv[5].equals(codigoEliminar)) {
                                    ciudadEnUso = true;
                                    break;
                                }
                            }

                            // Verificar si la ciudad está en uso en el archivo de clientes
                            clr = new BufferedReader(new FileReader(clientes)); // Volver a leer el archivo
                            while ((lineacv = clr.readLine()) != null) {
                                String[] vcv = lineacv.split("/");
                                if (vcv.length == 5 && vcv[4].equals(codigoEliminar)) {
                                    ciudadEnUso = true;
                                    break;
                                }
                            }

                            if (ciudadEnUso) {
                                System.out.println("La ciudad está en uso en los archivos de Proveedores o Clientes. No se puede eliminar.");
                            } else {
                                cr = new BufferedReader(new FileReader(ciudades)); // Volver a leer el archivo
                                while ((lineac = cr.readLine()) != null) {
                                    String[] vc = lineac.split("/");
                                    if (vc.length == 3 && !vc[0].equals(codigoEliminar)) {
                                        contenidoActualizado.append(lineac).append("\n");
                                    }
                                }

                                FileWriter cf = new FileWriter(ciudades);
                                cf.write(contenidoActualizado.toString());
                                cf.close();
                                System.out.println("La ciudad ha sido eliminada correctamente.");
                                eliminarCiudad = true;
                            }
                        } while (!eliminarCiudad);
                        break;

                    case "c": // Modificar ciudad
                        boolean modificarCiudad = false;
                        do {
                            System.out.println("Ingrese el código de la ciudad a modificar:");
                            String codigoModificar = sec.nextLine();
                            boolean ciudadE = false;
                            StringBuilder contenidoA = new StringBuilder();

                            // Verificar si el código de la ciudad existe en el archivo de ciudades
                            cr = new BufferedReader(new FileReader(ciudades)); // Volver a leer el archivo
                            while ((lineac = cr.readLine()) != null) {
                                String[] vc = lineac.split("/");
                                if (vc.length == 3 && vc[0].equals(codigoModificar)) {
                                    ciudadE = true;
                                    break;
                                }
                            }

                            if (!ciudadE) {
                                System.out.println("El código de la ciudad no existe en el archivo. Intente nuevamente.");
                                continue;
                            }

                            boolean nombreExistente;
                            do {
                                nombreExistente = false;
                                System.out.println("Ingrese el nuevo nombre de la ciudad:");
                                nuevoNombreC = sec.nextLine();

                                cr = new BufferedReader(new FileReader(ciudades)); // Volver a leer el archivo
                                while ((lineac = cr.readLine()) != null) {
                                    String[] vc = lineac.split("/");
                                    if (vc.length == 3 && vc[1].equals(nuevoNombreC)) {
                                        nombreExistente = true;
                                        break;
                                    }
                                }

                                if (nombreExistente) {
                                    System.out.println("El nombre de la ciudad ya existe en el archivo. Intente nuevamente.");
                                }
                            } while (nombreExistente);

                            cr = new BufferedReader(new FileReader(ciudades)); // Volver a leer el archivo
                            while ((lineac = cr.readLine()) != null) {
                                String[] vc = lineac.split("/");
                                if (vc.length == 3 && vc[0].equals(codigoModificar)) {
                                    contenidoA.append(vc[0]).append("/").append(nuevoNombreC).append("/").append(vc[2]).append("\n");
                                } else {
                                    contenidoA.append(lineac).append("\n");
                                }
                            }

                            BufferedWriter cf = new BufferedWriter(new FileWriter(ciudades));
                            cf.write(contenidoA.toString());
                            cf.flush();
                            cf.close();
                            System.out.println("La ciudad ha sido modificada correctamente.");
                            modificarCiudad = true;
                        } while (!modificarCiudad);

                        break;

                    case "d": // Regresar
                        salir = true;
                        break;

                    default:
                        System.out.println("Opción inválida.");
                        break;
                }
            }
            
            pr.close();
            cr.close();
            cw.close();
        } catch (IOException e) {
            System.out.println("Error al registrar o eliminar ciudad.");
        }
    }

    public static void listarFacturasGeneradas() {
        List<String> facturas = cargarDatosFactura("archivo/DatosFactura.txt");

        mostrarTablaFacturas(facturas);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el número de factura a eliminar (o 'q' para salir): ");
        String input = scanner.nextLine();

        while (!input.equalsIgnoreCase("q")) {
            if (input.matches("\\d+")) {
                int numeroFactura = Integer.parseInt(input);
                boolean facturaEncontrada = false;

                for (int i = 0; i < facturas.size(); i++) {
                    String factura = facturas.get(i);
                    String[] tokens = factura.split("/");
                    if (tokens.length == 6 && Integer.parseInt(tokens[0]) == numeroFactura) {
                        StringBuilder builder = new StringBuilder();
                        builder.append(tokens[0]).append("/").append(tokens[1]).append("/").append(tokens[2]).append("/").append(tokens[3]).append("/").append(tokens[4]).append("/").append(tokens[5]).append("/").append("eliminada");
                        facturas.set(i, builder.toString());
                        facturaEncontrada = true;
                        aumentarStockProductos(tokens[2], Integer.parseInt(tokens[3]));
                    }
                }

                if (!facturaEncontrada) {
                    System.out.println("Factura no encontrada");
                }
            } else {
                System.out.println("Entrada inválida. Intente nuevamente.");
            }

            System.out.print("Ingrese el número de factura a eliminar (o 'q' para salir): ");
            input = scanner.nextLine();
        }

        guardarDatosFactura(facturas, "archivo/DatosFactura.txt");
    }

    public static List<String> cargarDatosFactura(String path) {
        List<String> facturas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                facturas.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return facturas;
    }

    public static void mostrarTablaFacturas(List<String> facturas) {
        System.out.println("Tabla de Facturas:");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-15s %-20s %-15s%n", "Número", "Cliente", "Total Vendido");
        System.out.println("--------------------------------------------------");
        List<Integer> numerosFactura = new ArrayList<>();
        for (String factura : facturas) {
            String[] tokens = factura.split("/");
            if (tokens.length == 6) {
                int numeroFactura = Integer.parseInt(tokens[0]);
                if (!numerosFactura.contains(numeroFactura)) {
                    numerosFactura.add(numeroFactura);
                    System.out.printf("%-15s %-20s %-15s%n", tokens[0], tokens[1], tokens[5]);
                }
            }
        }
        System.out.println("--------------------------------------------------");
    }

    public static void guardarDatosFactura(List<String> facturas, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (String factura : facturas) {
                writer.write(factura);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void aumentarStockProductos(String codigoProducto, int cantidadVendida) {
        String path = "archivo/productos.txt";
        List<String> productos = cargarDatosProducto(path);

        for (int i = 0; i < productos.size(); i++) {
            String producto = productos.get(i);
            String[] tokens = producto.split("/");
            if (tokens.length == 4 && tokens[0].equals(codigoProducto)) {
                int cantidadActual = Integer.parseInt(tokens[2]);
                int nuevaCantidad = cantidadActual + cantidadVendida;
                tokens[2] = String.valueOf(nuevaCantidad);
                productos.set(i, String.join("/", tokens));
            }
        }

        guardarDatosProducto(productos, path);
    }

    public static List<String> cargarDatosProducto(String path) {
        List<String> productos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                productos.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return productos;
    }

    public static void guardarDatosProducto(List<String> productos, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            for (String producto : productos) {
                writer.write(producto);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public static void cambiarUsuario() throws IOException {
        Login2();
        
    }
    public static void Login2() throws IOException{
        Scanner tec = new Scanner(System.in);
        int intentos = 0;
        boolean usuarioValido = false;
        String user;
        int permitido=3;
        do {
            System.out.print("Usuario: ");
            String usuario = tec.nextLine();
            user=usuario;
            System.out.print("Contraseña: ");
            String contraseña = tec.nextLine();

            if (verificarCredenciales2(usuario, contraseña)) {
                usuarioValido = true;
            } else {
                intentos++;
                System.out.println("Credenciales incorrectas. Intento #" + intentos);

                if (intentos >= permitido) {
                    bloquearUsuario2();
                    System.out.println("Usuario bloqueado. Por favor, cambie las credenciales en el archivo txt.");
                    System.out.println("Agotaste los intentos. El programa se cerrará.");
                    System.exit(0);
                }
            }
        } while (!usuarioValido);

        if (usuarioValido) {
            mostrarMenu(user);
        }
    }

    public static boolean verificarCredenciales2(String usuario, String contraseña) {
        try {
            File usuarios= new File("archivo/credenciales2.txt");
            BufferedReader ur = new BufferedReader(new FileReader(usuarios));
            String lineau;
            while ((lineau = ur.readLine()) != null) {
                String[] credenciales = lineau.split("/");
                if (credenciales.length == 2 && credenciales[0].equals(usuario) && credenciales[1].equals(contraseña)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de credenciales.");
        }
        return false;
    }

    public static void bloquearUsuario2() {
        try {
            File usuarios= new File("archivo/credenciales2.txt");
            BufferedWriter uw= new BufferedWriter(new FileWriter(usuarios, true));
            uw.write("/bloqueado");
            uw.close();
        } catch (IOException e) {
            System.out.println("Error al bloquear el usuario.");
        }
    }
}
    
