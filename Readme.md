PASOS PARA INSTALAR LA APLICACIÓN EN UN CONTENEDOR DE DOCKER (Ubuntu)

1. INSTALAR DOCKER (Seguir documentación de docker)

2. CREAR UNA RED PERSONALIZADA PARA LOS CONTENEDORES

    2.1 Crear una red personalizada para los contenedores
   
    sudo docker network create sac-network
   
    2.2 Para verificar que la red se creó, se ingresa:
   
    sudo docker network ls

3. CREAR UN VOLUMEN PARA LA BASE DE DATOS

    sudo docker volume create mysql-sac-volume

4. INSTALAR UNA IMAGEN DE MySQL Y CREAR LA BASE DE DATOS Y EL USUARIO

   4.1 Instalar una imagen de MySQL
   
   sudo docker run -d \
  --name mysql-sac-container \
  --network sac-network \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=<CONTRASENHA DESEADA OJALA SUPERSEGURA> \
  -e MYSQL_DATABASE=db_cit \
  -e MYSQL_USER=cituser \
  -e MYSQL_PASSWORD=<CONTRASENHA PARA LA BASE DE DATOS SAC>
  -v mysql-sac-volume:/var/lib/mysql
  mysql:latest
  
    4.2 Iniciamos la imagen:
  
    sudo docker start mysql-sac-container
  
    4.3 Verificar que el contenedor está en ejecución
  
    sudo docker ps -a
  
    4.4 Conectarnos a MySQL 
  
    mysql -h 0.0.0.0 -P 3306 -u root -p 
  
    Enter password: 

    4.5 Ejecutar el Script SQL de db_cit

        4.5.1. Ubicarse en la carpeta db: /cit_management_sac/db/

        4.5.2. Ejecutar: 'mysql> SOURCE SQL Script.sql

5. CREAR LAS LLAVES PÚBLICAS Y PRIVADAS
   
   5.1 Generar la clave privada (.pem)

   openssl genpkey -algorithm RSA -out privyKey.pem -pkeyopt rsa_keygen_bits:2048
   
   5.2 Generar la clave pública (.pem) a partir de la privada
   
   openssl rsa -pubout -in privyKey.pem -out public.pem

   5.3 Ver el contenido de las llaves
   
   cat privyKey.pem
   
   cat public.pem

6. INSTALAR NODEJS 22

   sudo apt-get install && apt-get upgrade -y
   sudo apt-get install -y curl gnupg
   sudo curl -fsSL https://deb.nodesource.com/setup_$NODE_VERSION -o nodesource_setup.sh
   sudo -E bash nodesource_setup.sh
   sudo apt-get install -y nodejs
   node -v
   npm -v

7. HACER BUILD DEL FRONTEND
    
    7.1. ELIMINAR package-lock.json y node_modules/
    
    pwd -> Esto debe arrojar la carpeta management_client

    rm package-lock.json
    rm -rf node_modules/

    7.2. INSTALAR DEPENDENCIAS DEL FRONTEND

    npm i

    7.3. CONSTRUIR LA APLICACION

    npm run build

8. CREAR LA IMAGEN DE DOCKER CON EL Dockerfile

    8.1. TIENE QUE ESTAR EN EL DIRECTORIO RAIZ DEL BACKEND

    8.2. AGREGAR LAS VARIABLES DE ENTORNO
    
        8.2.1. Enfasis en ENCRYPTION_SECRET. Se debe usar la primera linea de la llave privada: privyKey.pem que se creó en el punto 

            Para ver el contenido de la llave privada debe ejecutar el comando:
                cat src/main/resources/privyKey.pem estando en la carpeta cit-sac-back-end
    
    pwd -> cit-sac-back-end

    8.3. CREAR LA IMAGEN

    sudo docker build -t "SAC-APP"

9. CREAR UN VOLUME PARA LA APPLICACION

    sudo docker volume create app-storage

10. CREAR EL CONTENEDOR DE LA APLICACION