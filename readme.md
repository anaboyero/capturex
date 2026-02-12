Propósito del proyecto Capturex

Implementar una vertical slice mínima de una API REST que permita crear un recurso mediante un endpoint POST.

El objetivo es practicar backend con una estructura simple, limpia y testeable, evitando complejidad innecesaria.

La slice debe cubrir el flujo completo: request HTTP → lógica de negocio → persistencia en base de datos.

Requisitos funcionales

Exponer un endpoint POST

Recibir un objeto LearningArtifact con 3 campos:

- descripcion

- leccion

- enlace

Validar datos básicos

Guardar el objeto en base de datos

Devolver el objeto creado con su id