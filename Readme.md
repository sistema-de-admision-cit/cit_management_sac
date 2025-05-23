<h1>Instalación del Sistema de Admisión CIT en Contenedor Docker (Ubuntu)</h1>

<h2>Instalación de Docker en Ubuntu</h2>

<p>Para instalar Docker en Ubuntu, sigue estos pasos:</p>

<pre><code># Add Docker's official GPG key:
sudo apt-get update
sudo apt-get install ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc

# Add the repository to Apt sources:
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "${UBUNTU_CODENAME:-$VERSION_CODENAME}") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update
</code></pre>

<p>Para instalar la última versión, ejecuta:</p>

<pre><code>sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
</code></pre>

<p>Verifica que la instalación fue exitosa ejecutando la imagen hello-world:</p>

<pre><code>sudo docker run hello-world
</code></pre>

<h2>Crear Claves Pública y Privada</h2>
<pre><code>
openssl genpkey -algorithm RSA -out privyKey.pem -pkeyopt rsa_keygen_bits:2048
openssl rsa -pubout -in privyKey.pem -out public.pem
cat privyKey.pem
cat pubKey.pem
</code></pre>

<h2>Instalar Node.js 22</h2>
<pre><code>
sudo apt-get update && sudo apt-get upgrade -y
sudo apt-get install -y curl gnupg
curl -fsSL https://deb.nodesource.com/setup_22.x -o nodesource_setup.sh
sudo -E bash nodesource_setup.sh
sudo apt-get install -y nodejs
node -v
npm -v
</code></pre>

<h2>Build del Frontend</h2>
<ol>
  <li>Eliminar archivos previos:
    <pre><code>
cd management_client
rm package-lock.json
rm -rf node_modules/
    </code></pre>
  </li>
  <li>Instalar dependencias:
    <pre><code>npm i</code></pre>
  </li>
  <li>Construir aplicación:
    <pre><code>npm run build</code></pre>
  </li>
</ol>

<h2>Configuración de la Imagen Docker</h2>

<p>El Dockerfile para la aplicación utiliza Eclipse Temurin JDK 21 y configura el entorno necesario:</p>

<pre><code># IMAGEN MODELO
FROM eclipse-temurin:21.0.7_6-jdk

# PUERTO DE LA APLICACION
EXPOSE 8080

VOLUME /storage

# Variables necesarias para instalación
ENV NODE_VERSION=22.x
ENV INSCRIPTIONS_STORAGE_DIR=/storage/inscriptions/
ENV QUESTIONS_STORAGE_DIR=/storage/questions/
ENV DATASOURSE_URL=jdbc:mysql://sac-mysql-container:3306/db_cit
ENV DATASOURSE_USERNAME=''
ENV DATASOURSE_PASSWORD=''
ENV JWT_PUBLIC_KEY=classpath:pubKey.pem
ENV JWT_PRIVATE_KEY=classpath:privyKey.pem
ENV CIT_APP_DEFAULT_PASSWORD=''
ENV ENCRYPTION_SECRET=''

# DEFINIR DIRECTORIO RAIZ DEL CONTENEDOR
WORKDIR /app

# COPIAR ARCHIVOS DEL PROYECTO AL CONTENEDOR
COPY ./pom.xml /app
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
</code></pre>

<h3>Variables de Entorno Requeridas</h3>
<ul>
  <li><strong>DATASOURSE_USERNAME</strong>: Usuario de la base de datos MySQL</li>
  <li><strong>DATASOURSE_PASSWORD</strong>: Contraseña de la base de datos MySQL</li>
  <li><strong>CIT_APP_DEFAULT_PASSWORD</strong>: Contraseña por defecto para la aplicación</li>
  <li><strong>ENCRYPTION_SECRET</strong>: Secreto para encriptación</li>
</ul>

<h2>Configuración con Docker Compose</h2>

<p>El archivo docker-compose.yml define los servicios necesarios para la aplicación:</p>

<pre><code>services:
  sac-app:
    image: sac-app
    build:
      context: .
      dockerfile: Dockerfile
    container_name: sac-app-container
    ports:
      - "8080:8080"
    depends_on:
      mysql:
        condition: service_healthy
    networks:
      - sac-network
    volumes:
      - sac-app-volume:/storage

  mysql:
    image: mysql:latest
    container_name: sac-mysql-container
    ports:
      - "3306:3306"
    networks:
      - sac-network
    volumes:
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql
      - sac-mysql-volume:/var/lib/mysql
    healthcheck:
      test: ["CMD", "mysqladmin", "ping", "-h", "localhost", "-u", "root", "-proot"]
      interval: 10s
      timeout: 5s
      retries: 5
    environment:
      MYSQL_ROOT_PASSWORD: &lt;root_password&gt;
      MYSQL_DATABASE: db_cit
      MYSQL_USER: cituser
      MYSQL_PASSWORD: &lt;cituser_password&gt;

networks:
  sac-network:

volumes:
  sac-mysql-volume:
  sac-app-volume:
</code></pre>

<h3>Configuración Requerida</h3>
<ul>
  <li><strong>init.sql</strong>: Archivo SQL con la inicialización de la base de datos (debe colocarse en el directorio raíz)</li>
  <li><strong>Variables de entorno MySQL</strong>:
    <ul>
      <li>Reemplazar <code>&lt;root_password&gt;</code> con la contraseña root de MySQL</li>
      <li>Reemplazar <code>&lt;cituser_password&gt;</code> con la contraseña del usuario cituser</li>
    </ul>
  </li>
</ul>

<h3>Uso Básico</h3>
<ol>
  <li>Construir y levantar los contenedores: <code>docker-compose up -d --build</code></li>
  <li>Detener los contenedores: <code>docker-compose down</code></li>
</ol>
