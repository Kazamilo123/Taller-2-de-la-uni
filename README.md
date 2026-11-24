# Sistema de Gestión de Inventario (Java + POO)

Programa de consola para administrar el inventario de una pequeña empresa, aplicando
**encapsulamiento, herencia, polimorfismo y clases abstractas**.

## Estructura del proyecto

```
src/
├── modelo/      Producto (abstracta), ProductoPerecedero, ProductoNoPerecedero
├── negocio/     Inventario (lista de productos con ArrayList)
└── interfaz/    SistemaInventario (menú interactivo en consola)
docs/            Diagrama UML y capturas de las pruebas
```

## Compilar y ejecutar

Requiere JDK 8 o superior.

```bash
mkdir out
javac -encoding UTF-8 -d out src/modelo/*.java src/negocio/*.java src/interfaz/*.java
java -cp out interfaz.SistemaInventario
```

## Funcionalidades

1. Registrar producto (perecedero o no perecedero)
2. Mostrar todos los productos
3. Buscar producto por ID
4. Actualizar cantidad
5. Eliminar producto
6. Salir

## Validaciones

- ID y nombre obligatorios (no vacíos)
- Cantidad mayor que cero al registrar; nunca negativa al actualizar
- Precio no negativo
- No se permiten IDs duplicados
- Entradas no numéricas y fechas con formato incorrecto (AAAA-MM-DD)

## Diagrama UML

Ver `docs/diagrama_uml.png`.
