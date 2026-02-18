
**Primera Slice vertical:**

Implementar una API que cree objetos de tipo Learning Artifact (compuesto de descripcion de ejercicio de programacion, leccion aprendida y URL hacia el repositorio). Las llamadas recibidas se almacenan en una base de datos permanente. Tenemos los endpoints: get all, post y delete by id.

Estado: implementado


**Segunda slice vertical:**

Me gustaria que el servicio cuando recibe una peticion para crear un Learning Artifact añada 3 etiquetas al objeto, generadas a partir de la descripcion del objeto  learning artifact mediante el uso de un agente de LLM.

Estado: desprioritizado

**Tercera slice vertical: Validación y reglas de dominio**

Reglas de negocio y validaciones fuertes al crear artifacts.

Practicas:
Bean Validation · validadores custom · reglas de dominio · errores HTTP correctos


--------


1️⃣ Búsqueda y filtros

Endpoints con query params para buscar artifacts por texto, fechas o campos.

Practicas:
query params · consultas dinámicas · repositorio avanzado · paginación · diseño de búsqueda



7️⃣ Manejo global de errores

Formato estándar de errores para toda la API.

Practicas:
@ControllerAdvice · exception mapping · contratos de error


Mas adelante
--------

4️⃣ Paginación y ordenación

Listados paginados y ordenados.

Practicas:
PageRequest · Page vs Slice · performance básica · respuestas paginadas

5️⃣ Sistema de tags manual (sin IA)

Entidad Tag + relación con artifacts.

Practicas:
many-to-many · modelado de dominio · joins · endpoints relacionales






2️⃣ Update parcial (PATCH)

Actualizar solo algunos campos del artifact.

Practicas:
PUT vs PATCH · DTOs de update · merge de datos · control de nulls


6️⃣ Auditoría automática

Campos createdAt / updatedAt gestionados automáticamente.

Practicas:
entity lifecycle · @PrePersist · @PreUpdate · auditoría JPA
