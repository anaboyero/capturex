En el caso de que la url sea de github, el usuario puede ahorrarse escribir la descripcion, ya que esta puede generarse de manera automática de lo que venga dado en el read me. 

Historia de Usuario — Generación automática de descripción desde GitHub

Título
Generar automáticamente la descripción de un Learning Artifact cuando la URL pertenece a GitHub

🧑‍💻 Historia de Usuario

Como usuario de Capturex

Quiero que la descripción de un Learning Artifact se genere automáticamente cuando llamo a la extension del navegador desde una URL de un repositorio de GitHub

Para ahorrar tiempo y capturar conocimiento sin tener que redactar manualmente la descripción.

✅ Criterios de aceptación
1. Detección de URL de GitHub

Cuando el usuario pulsa la extension de capturex desde una URL

Y la URL pertenece a un repositorio válido de GitHub (github.com/{owner}/{repo})

El sistema debe detectar automáticamente que se puede generar una descripcion automatica y llamar al endpoint de la api para sugerencias.

2. Obtención de información del repositorio

El sistema debe obtener el contenido del README del repositorio usando la API de GitHub.

Si existe README:

Se utilizará como fuente para generar la descripción.

Si no existe README:

No se generará descripción automática. 

3. Generación automática de descripción

El sistema debe generar automáticamente una descripción basada en el README.

La descripción generada debe:

Resumir el propósito del repositorio.

Ser breve y legible.

Estar orientada a aprendizaje (qué hace / qué se aprende).

4. Comportamiento por defecto

La descripción automática debe rellenarse por defecto.

El usuario debe ver la descripción generada antes de guardar el artefacto.

5. Edición manual por el usuario

El usuario puede:

modificar la descripción generada, o

eliminarla completamente y escribir una propia.

El sistema debe guardar siempre la versión final editada por el usuario.

6. Compatibilidad API y Plugin Chrome

Debe funcionar igual cuando el artefacto se crea desde:

✅ API directa

✅ Plugin de Chrome

7. Fallback seguro

Si ocurre cualquier error:

fallo al acceder a GitHub,

README inexistente,

límite de API alcanzado,

👉 el artefacto debe poder crearse normalmente sin descripción automática.




