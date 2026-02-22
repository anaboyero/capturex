

🧾 Historia de Usuario — Slice 3 (versión sencilla)

🎯 Historia

Como usuaria de la API
quiero que al crear un Learning Artifact se validen los datos de entrada
para evitar guardar artifacts inválidos en el sistema.

✅ Criterios de aceptación (simples y concretos)
Cuando hago:
POST /artifacts

✅ Caso válido

Si me llega una peticion con:

description ≥ 30 caracteres

lessonLearned no vacía

repoUrl con formato URL válido

Entonces:

→ se guarda
→ responde 201 Created

❌ Caso inválido — description corta

Si description < 30 caracteres:

→ NO se guarda
→ responde 400 Bad Request
→ mensaje indica campo inválido


❌ Caso inválido — lesson vacía
→ 400 Bad Request

❌ Caso inválido — repoUrl no es URL
→ 400 Bad Request

📦 Alcance técnico de esta slice (limitado)

Incluye:

✔ DTO con Bean Validation
✔ @Valid en controller
✔ error 400 automático
✔ test de controller


No incluye todavía:

❌ reglas de unicidad
❌ validación contra BD
❌ reglas de dominio complejas
❌ error handler global sofisticado
❌ códigos 409


Eso sería slice 4.

🧠 Qué aprendes con ESTA slice (sin sobrecarga)

DTO vs entidad

validación declarativa

@Valid en controller

contrato de entrada

error HTTP automático

tests de validación

Fundamentos puros.
