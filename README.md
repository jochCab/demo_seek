# Demo Seek - Backend Spring Boot

Este proyecto es una demo backend desarrollado en **Spring Boot** y desplegado en **Google Cloud Run**, con conexión a una base de datos en **Cloud SQL (MySQL)**.

---

## Tecnologías Utilizadas

- Java 17
- Spring Boot 3.3
- Spring Data JPA
- MySQL
- Docker
- Google Cloud Run
- Google Cloud SQL
- Postman
- Swagger (Documentación Online)

---


## Construcción y Ejecución Local con Docker

# Construir imagen Docker
docker build -t seek-app .

# Ejecutar contenedor Docker localmente
docker run -p 8080:8080 seek-app



## Despliegue en Google Cloud Platform (GCP)

### 1. Habilitar servicios necesarios


gcloud services enable artifactregistry.googleapis.com


### 2. Crear repositorio en Artifact Registry *(solo la primera vez)*


gcloud artifacts repositories create springboot-repo \
  --repository-format=docker \
  --location=us-central1


### 3. Construir y subir la imagen con Cloud Build


gcloud builds submit --tag us-central1-docker.pkg.dev/metal-segment-467219-e5/springboot-repo/mi-app


### 4. Desplegar en Cloud Run

gcloud run deploy mi-app \
  --image=us-central1-docker.pkg.dev/metal-segment-467219-e5/springboot-repo/mi-app \
  --platform=managed \
  --region=us-central1 \
  --allow-unauthenticated \
  --add-cloudsql-instances=metal-segment-467219-e5:us-central1:demo-seek-bd


---

## Acceso a la Documentación API

- **Swagger UI (Documentación en línea):**
https://mi-app-441553189349.us-central1.run.app/demo-seek/swagger-ui/index.html

---

## Recursos Adicionales

- **Colección Postman:** Incluye todos los endpoints disponibles para pruebas.
- **Repositorio GitHub:** https://github.com/jochCab/demo_seek

---

## Autor

**José Cabañas**  
Backend Developer - Java & Spring Boot

---

¡Gracias por revisar este proyecto demo!

Para cualquier duda o comentario, no dudes en contactarme.