# Sistema de Reservas y Control de Habitaciones de un Hotel

Proyecto básico de escritorio en **Java 17 + Swing**, preparado para **Apache NetBeans con ANT**. Usa archivos CSV para persistencia y aplica SOLID, GoF y GRASP sin depender de una base de datos externa.

## GitHub

Repositorio a publicar: **https://github.com/T1T0-1/HotelReservasPatrones**  
Crea el repositorio con el nombre `HotelReservasPatrones` en la cuenta `T1T0-1` y sube el contenido completo de esta carpeta.

## Funciones mínimas

- Registrar, listar y eliminar habitaciones.
- Crear reservas validando fechas y disponibilidad.
- Cancelar reservas y finalizar estadías.
- Liberar automáticamente la habitación al cancelar o finalizar.
- Calcular el costo según tipo de habitación.
- Persistir habitaciones y reservas en CSV.
- Generar reporte de ocupación e ingresos.
- Registrar eventos en `data/auditoria.log`.

## Ejecución

```bash
ant clean jar
ant run
```

Prueba rápida sin dependencias externas:

```bash
ant smoke
```

Pruebas JUnit 5 + Mockito:

```bash
ant test
```

La primera ejecución de `ant test` descarga las librerías de prueba y requiere conexión a internet.

## Datos semilla

`data/habitaciones.csv` incluye tres habitaciones: simple, doble y suite.

## Patrones aplicados

- Creacionales: **Builder** y **Factory Method**.
- Estructurales: **Adapter** y **Facade**.
- Comportamiento: **Strategy** y **Observer**.
- Extra: **Command** para cancelar reservas.

## GRASP

- **Controller:** `HotelController` recibe acciones de la interfaz.
- **Information Expert:** `Reserva` calcula noches y mantiene su estado.
- También se evidencia bajo acoplamiento y alta cohesión.

## Documentación

La carpeta `docs` contiene el informe, guía de demostración, propuesta de diapositivas y tabla de antipatrones.
