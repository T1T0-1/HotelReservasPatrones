# Tabla de antipatrones evitados

| Antipatrón | Cómo se evitó |
|---|---|
| God Class | La interfaz, los servicios, repositorios y dominio están separados. |
| Condicionales extensos | El cálculo usa Strategy y Factory Method. |
| Dependencias rígidas | Los servicios dependen de interfaces Repository. |
| Singleton global | Las dependencias se crean en AppConfig y se inyectan. |
| Lógica de negocio en la vista | MainFrame delega a HotelController. |
| Duplicación | Persistencia común concentrada en LegacyCsvStore y adaptadores. |
