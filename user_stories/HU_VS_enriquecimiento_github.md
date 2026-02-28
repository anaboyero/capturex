Definición de vertical slice mínima (ajustada a tu código actual)

Objetivo de la slice
Si projectUrl es exactamente de formato base https://github.com/{owner}/{repo} y description viene vacía, el backend genera una descripción heurística desde el README público.
Si falla cualquier paso (sin README/error GitHub), se usa "".
El artefacto se crea igual (flujo síncrono).
Contrato funcional mínimo
Entrada create: description, lessonLearned, projectUrl.
Regla:
description no vacía: se respeta tal cual (editable por usuario).
description vacía + URL GitHub válida: autogenerar.
Cualquier otro caso con description vacía: "" (o validación, según decidáis; para esta slice, mejor "" para no bloquear).
Salida create: LearningArtifact persistido con la description final (manual o autogenerada).


Cambios mínimos por capa

**CreateLearningArtifactRequest:**
Quitar @NotBlank y mínimo 30 para description.
Mantener max=500.

**Application:**

Añadir orquestación en LearningArtifactService.create(...) para enriquecer descripción cuando aplique.
Dominio/aplicación:
Interface DescriptionEnricher (o ArtifactEnricher) con método enrich(projectUrl) -> String.
Implementación GitHubEnricher (solo caso base URL).
Infra/adapters:
GitHubReadmeClient para obtener README (público, sin token).
HeuristicReadmeSummarizer que:
limpia markdown básico,
toma primeras frases/líneas útiles,
recorta a 500 chars.
Fallback:
try/catch total en enricher y devolver "".
Criterios de aceptación técnicos (DoD)
URL https://github.com/owner/repo + description="" => se intenta README y se guarda descripción <= 500.
Sin README/error/rate limit => se guarda description="" sin romper create.
URL no GitHub + description="" => create exitoso con description="".
description manual no vacía => no llama al enricher.
Flujo síncrono (todo dentro de create).
Tests mínimos para cerrar slice
Unit LearningArtifactServiceTest:
autogenera cuando GitHub + vacía.
no autogenera cuando description manual.
fallback "" cuando enricher falla.
Unit GitHubEnricherTest:
parseo URL base válido.
URL inválida/no base => no enriquecer.
resumen truncado a 500.
Integration controller:
POST con GitHub + description vacía retorna 201 y description generada o "".
POST con description > 500 retorna 400.
Fuera de alcance de esta slice
Repos privados.
URLs GitHub avanzadas (/tree, .git, etc.).
LLM.
Endpoint separado de ArtifactProposal (puede venir en siguiente iteración para “ver antes de guardar” estricto en UX).
Si quieres, te lo convierto ahora en un checklist técnico ejecutable por PR (tareas + orden + nombre de clases exacto).







