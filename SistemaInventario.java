import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

// Clase principal: guarda la informacion general de cada producto.
abstract class Producto {
    private String id;
    private String nombre;
    private int cantidad;
    private double precio;

    public Producto(String id, String nombre, int cantidad, double precio) {
        if (id == null || id.trim().isEmpty()
                || nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID y el nombre son obligatorios.");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }

        this.id = id.trim();
        this.nombre = nombre.trim();
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setCantidad(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
        this.cantidad = cantidad;
    }

    public abstract String getTipo();

    @Override
    public String toString() {
        return "ID: " + id
                + " | Nombre: " + nombre
                + " | Cantidad: " + cantidad
                + " | Precio: $" + String.format("%.2f", precio)
                + " | Tipo: " + getTipo();
    }
}

// Clase hija para productos que tienen fecha de vencimiento.
class ProductoPerecedero extends Producto {
    private LocalDate fechaVencimiento;

    public ProductoPerecedero(String id, String nombre, int cantidad,
            double precio, LocalDate fechaVencimiento) {
        super(id, nombre, cantidad, precio);

        if (fechaVencimiento == null) {
            throw new IllegalArgumentException("La fecha de vencimiento es obligatoria.");
        }
        this.fechaVencimiento = fechaVencimiento;
    }

    @Override
    public String getTipo() {
        return "Perecedero";
    }

    @Override
    public String toString() {
        return super.toString() + " | Vencimiento: " + fechaVencimiento;
    }
}

// Clase hija para productos que no tienen fecha de vencimiento.
class ProductoNoPerecedero extends Producto {
    public ProductoNoPerecedero(String id, String nombre, int cantidad, double precio) {
        super(id, nombre, cantidad, precio);
    }

    @Override
    public String getTipo() {
        return "No perecedero";
    }
}

// Clase que administra la lista de productos.
class Inventario {
    private ArrayList<Producto> productos = new ArrayList<>();

    public void agregarProducto(Producto producto) {
        if (buscarProducto(producto.getId()) != null) {
            throw new IllegalArgumentException("Ya existe un producto con ese ID.");
        }
        productos.add(producto);
        System.out.println("Producto registrado correctamente.");
    }

    public void mostrarProductos() {
        if (productos.isEmpty()) {
            System.out.println("El inventario esta vacio.");
            return;
        }

        System.out.println("\n--- PRODUCTOS REGISTRADOS ---");
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }

    public Producto buscarProducto(String id) {
        for (Producto producto : productos) {
            if (producto.getId().equalsIgnoreCase(id.trim())) {
                return producto;
            }
        }
        return null;
    }

    public void actualizarCantidad(String id, int nuevaCantidad) {
        Producto producto = buscarProducto(id);
        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        producto.setCantidad(nuevaCantidad);
        System.out.println("Cantidad actualizada correctamente.");
    }

    public void eliminarProducto(String id) {
        Producto producto = buscarProducto(id);
        if (producto == null) {
            System.out.println("Producto no encontrado.");
            return;
        }

        productos.remove(producto);
        System.out.println("Producto eliminado correctamente.");
    }
}

// Programa principal. El archivo debe llamarse SistemaInventario.java.
public class SistemaInventario {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Inventario inventario = new Inventario();
        int opcion = 0;

        do {
            System.out.println("\n===== SISTEMA DE INVENTARIO =====");
            System.out.println("1. Registrar producto");
            System.out.println("2. Mostrar productos");
            System.out.println("3. Buscar producto");
            System.out.println("4. Actualizar cantidad");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opcion: ");

            String entrada = sc.nextLine().trim();

            try {
                opcion = Integer.parseInt(entrada);

                switch (opcion) {
                    case 1:
                        registrarProducto(inventario);
                        break;

                    case 2:
                        inventario.mostrarProductos();
                        break;

                    case 3:
                        buscarProducto(inventario);
                        break;

                    case 4:
                        actualizarCantidad(inventario);
                        break;

                    case 5:
                        System.out.print("Ingrese el ID del producto: ");
                        String idEliminar = sc.nextLine().trim();
                        if (idEliminar.isEmpty()) {
                            System.out.println("El ID es obligatorio.");
                        } else {
                            inventario.eliminarProducto(idEliminar);
                        }
                        break;

                    case 6:
                        System.out.println("Saliendo del sistema...");
                        break;

                    default:
                        System.out.println("Opcion no valida. Elija del 1 al 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un numero valido.");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcion != 6);

        sc.close();
    }

    private static void registrarProducto(Inventario inventario) {
        try {
            System.out.print("Ingrese ID: ");
            String id = sc.nextLine();

            System.out.print("Ingrese nombre: ");
            String nombre = sc.nextLine();

            System.out.print("Ingrese cantidad: ");
            String textoCantidad = sc.nextLine().trim();
            if (textoCantidad.isEmpty()) {
                System.out.println("Error: la cantidad es obligatoria.");
                return;
            }
            int cantidad = Integer.parseInt(textoCantidad);

            System.out.print("Ingrese precio: ");
            String textoPrecio = sc.nextLine().trim();
            if (textoPrecio.isEmpty()) {
                System.out.println("Error: el precio es obligatorio.");
                return;
            }
            double precio = Double.parseDouble(textoPrecio);

            System.out.println("Tipo de producto:");
            System.out.println("1. Perecedero");
            System.out.println("2. No perecedero");
            System.out.print("Seleccione: ");
            int tipo = Integer.parseInt(sc.nextLine().trim());

            Producto producto;

            if (tipo == 1) {
                System.out.print("Fecha de vencimiento (AAAA-MM-DD): ");
                String textoFecha = sc.nextLine().trim();
                if (textoFecha.isEmpty()) {
                    System.out.println("La fecha de vencimiento es obligatoria.");
                    return;
                }

                LocalDate fecha = LocalDate.parse(textoFecha);
                producto = new ProductoPerecedero(
                        id, nombre, cantidad, precio, fecha);

            } else if (tipo == 2) {
                producto = new ProductoNoPerecedero(
                        id, nombre, cantidad, precio);

            } else {
                System.out.println("Tipo de producto no valido.");
                return;
            }

            inventario.agregarProducto(producto);

        } catch (NumberFormatException e) {
            System.out.println("Error: cantidad, precio o tipo deben ser numericos.");
        } catch (DateTimeParseException e) {
            System.out.println("Error: use el formato de fecha AAAA-MM-DD.");
        }
    }

    private static void buscarProducto(Inventario inventario) {
        System.out.print("Ingrese el ID del producto: ");
        String id = sc.nextLine().trim();

        if (id.isEmpty()) {
            System.out.println("El ID es obligatorio.");
            return;
        }

        Producto producto = inventario.buscarProducto(id);
        if (producto == null) {
            System.out.println("Producto no encontrado.");
        } else {
            System.out.println(producto);
        }
    }

    private static void actualizarCantidad(Inventario inventario) {
        System.out.print("Ingrese el ID del producto: ");
        String id = sc.nextLine().trim();

        if (id.isEmpty()) {
            System.out.println("El ID es obligatorio.");
            return;
        }

        System.out.print("Ingrese la nueva cantidad: ");
        String textoCantidad = sc.nextLine().trim();

        if (textoCantidad.isEmpty()) {
            System.out.println("La cantidad es obligatoria.");
            return;
        }

        try {
            int nuevaCantidad = Integer.parseInt(textoCantidad);
            inventario.actualizarCantidad(id, nuevaCantidad);
        } catch (NumberFormatException e) {
            System.out.println("La cantidad debe ser un numero entero.");
        }
    }
}
