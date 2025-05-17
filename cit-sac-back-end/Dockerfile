#IMAGEN MODELO
FROM eclipse-temurin:21.0.7_6-jdk

# PUERTO DE LA APLICACION
EXPOSE 8080

# Variables necesarias para instalación
ENV NODE_VERSION=22.x

#INSTALAR NODEJS Y NPM EN EL CONTENEDOR
RUN sudo apt-get install && apt-get upgrade -y
RUN sudo apt-get install -y curl gnupg
RUN sudo curl -fsSL https://deb.nodesource.com/setup_$NODE_VERSION -o nodesource_setup.sh
RUN sudo -E bash nodesource_setup.sh
RUN sudo apt-get install -y nodejs

# DEFINIR DIRECTORIO RAIZ DEL CONTENEDOR
WORKDIR /root

# COPIAR ARCHIVOS DEL PROYECTO AL CONTENEDOR
COPY ./pom.xml /root
COPY ./.mvn /root/.mvn
COPY ./mvnw /root

# DESCARGAR DEPENDENCIAS DEL PROYECTO
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

# COPIAR EL CODIGO FUENTE DEL PROYECTO AL CONTENEDOR
COPY ./src /root/src

# COMPILAR EL PROYECTO
RUN ./mvnw clean install

# LEVANTAR EL SERVIDOR CUANDO SE INICIE EL CONTENEDOR
ENTRYPOINT [ "java", "-jar", "/root/target/SAC-APP-1.0.jar" ]