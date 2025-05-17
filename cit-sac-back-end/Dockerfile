#IMAGEN MODELO
FROM eclipse-temurin:21.0.7_6-jdk

# PUERTO DE LA APLICACION
EXPOSE 8080

# Variables necesarias para instalación
ENV NODE_VERSION=22.x
ENV INSCRIPTIONS_STORAGE_DIR=/storage/inscriptions/
ENV QUESTIONS_STORAGE_DIR=/storage/questions/
ENV DATASOURSE_URL=jdbc:mysql://mysql-citsac:3306/db_cit
ENV DATASOURSE_USERNAME=''
ENV DATASOURSE_PASSWORD=''
ENV JWT_PUBLIC_KEY=classpath:pubKey.pem
ENV JWT_PRIVATE_KEY=classpath:privyKey.pem
ENV CIT_APP_DEFAULT_PASSWORD=''
ENV ENCRYPTION_SECRET=''

#INSTALAR NODEJS Y NPM EN EL CONTENEDOR
RUN sudo apt-get install && apt-get upgrade -y
RUN sudo apt-get install -y curl gnupg
RUN sudo curl -fsSL https://deb.nodesource.com/setup_$NODE_VERSION -o nodesource_setup.sh
RUN sudo -E bash nodesource_setup.sh
RUN sudo apt-get install -y nodejs

# DEFINIR DIRECTORIO RAIZ DEL CONTENEDOR
WORKDIR /app

# COPIAR ARCHIVOS DEL PROYECTO AL CONTENEDOR
COPY ./pom.xml /root
COPY ./.mvn /app/.mvn
COPY ./mvnw /app

# DESCARGAR DEPENDENCIAS DEL PROYECTO
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# COPIAR EL CODIGO FUENTE DEL PROYECTO AL CONTENEDOR
COPY ./src /app/src

# COMPILAR EL PROYECTO
RUN ./mvnw clean install

# LEVANTAR EL SERVIDOR CUANDO SE INICIE EL CONTENEDOR
ENTRYPOINT [ "java", "-jar", "/app/target/SAC-APP-1.0.jar" ]
