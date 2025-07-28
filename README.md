# demo_seek

spring.datasource.url=jdbc:mysql:///nombre_bd?cloudSqlInstance=PROYECTO:REGION:INSTANCIA&socketFactory=com.google.cloud.sql.mysql.SocketFactory
spring.datasource.username=USUARIO

# CONSTRUIR IMAGEN
docker build -t seek-app .

docker run -p 8080:8080 seek-app

# DESPLEGAR EN GCP

## HABILITAR EL SERVICIO

gcloud services enable artifactregistry.googleapis.com


# SOLO LA PRIMERA VEZ
gcloud artifacts repositories create springboot-repo \
  --repository-format=docker \
  --location=us-central1

# CONSTRUIR
gcloud builds submit --tag us-central1-docker.pkg.dev/metal-segment-467219-e5/springboot-repo/mi-app


# DEPLOY GCP
  gcloud run deploy mi-app \
  --image=us-central1-docker.pkg.dev/metal-segment-467219-e5/springboot-repo/mi-app \
  --platform=managed \
  --region=us-central1 \
  --allow-unauthenticated \
  --add-cloudsql-instances=metal-segment-467219-e5:us-central1:demo-seek-bd