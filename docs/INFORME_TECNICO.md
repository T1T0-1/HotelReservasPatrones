# Informe técnico - Sistema de Reservas y Control de Habitaciones

## Enlace GitHub
https://github.com/T1T0-1/HotelReservasPatrones

## Resumen
Aplicación Java Swing para registrar habitaciones, crear/cancelar/finalizar reservas, calcular tarifas, persistir en CSV y generar reportes.

## Arquitectura
Presentación -> Controller -> Facade -> Services -> Repository interfaces -> CSV Adapters.

## SOLID
- SRP: clases separadas por responsabilidad.
- OCP: nuevas tarifas mediante Strategy/Factory sin cambiar ReservaService.
- LSP: estrategias y adaptadores respetan sus interfaces.
- ISP: repositorios de lectura y escritura separados.
- DIP: servicios reciben interfaces por constructor.

## GoF
- Creacionales: Builder, Factory Method.
- Estructurales: Adapter, Facade.
- Comportamiento: Strategy, Observer.
- Extra: Command.

## GRASP
- Controller: HotelController.
- Information Expert: Reserva.calcularNoches().
- Low Coupling y High Cohesion: capas y servicios específicos.

## Pruebas
- Unitarias: TarifaStrategyTest y ReservaServiceTest con Mockito.
- Integración: CsvRepositoryIntegrationTest y FlujoReservaIntegrationTest.
- Comando offline: `ant smoke`.
- Comando JUnit: `ant test`.

## Pendientes externos
Crear el repositorio en la cuenta T1T0-1, subir el proyecto, grabar el video y añadir su enlace.
