PASOS PARA INSTALAR LA APLICACIÓN EN UN CONTENEDOR DE DOCKER (Ubuntu)

1. INSTALAR DOCKER (Seguir documentación de docker)

2. CREAR UNA RED PERSONALIZADA PARA LOS CONTENEDORES

    2.1 Crear una red personalizada para los contenedores
   
    sudo docker network create app-cit-network
   
    2.2 Para verificar que la red se creó, se ingresa:
   
    sudo docker network ls

3. INSTALAR UNA IMAGEN DE MySQL Y CREAR LA BASE DE DATOS Y EL USUARIO

   3.1 Instalar una imagen de MySQL
   
   docker run -d \
  --name mysql-citsac \
  --network app-cit-network \
  -p 3306:3306 \
  -e MYSQL_ROOT_PASSWORD=campus12 \
  -e MYSQL_DATABASE=db_cit \
  -e MYSQL_USER=cituser \
  -e MYSQL_PASSWORD=21506Mnr \
  mysql:latest
  
    3.2 Iniciamos la imagen:
  
    sudo docker start mysql-citsac
  
  
    3.3 Verificar que el contenedor está en ejecución
  
    sudo docker ps -a
  
    3.4 Conectarnos a MySQL 
  
    mysql -h 0.0.0.0 -P 3306 -u root -p 
  
    Enter password: campus12
  

    3.5 Crear el usuario en MySQL manualmente
  
    CREATE USER 'cituser'@'%' IDENTIFIED BY '21506Mnr';
  
    3.6 Dar Privilegios para la base de datos db_cit
  
    GRANT ALL PRIVILEGES ON `db_cit`.* TO 'cituser'@'%';
    FLUSH PRIVILEGES;
  
    3.7 Ver los permisos del usuario creado
  
    SHOW GRANTS FOR 'cituser'@'%';
  
    3.8 Ejecutar el Script SQL de db_cit
  
    SOURCE /cit_management_sac/db/SQL Script.sql
	
4. CREAR LAS LLAVES PÚBLICAS Y PRIVADAS
   
   4.1 Generar la clave privada (.pem)

   openssl genpkey -algorithm RSA -out privyKey.pem -pkeyopt rsa_keygen_bits:2048
   
   4.2 Generar la clave pública (.pem) a partir de la privada
   
   openssl rsa -pubout -in privyKey.pem -out public.pem

   4.3 Ver el contenido de las llaves
   
   cat privyKey.pem
   
   cat public.pem

5. INSTALAR NODEJS 22

   sudo apt-get install && apt-get upgrade -y
   sudo apt-get install -y curl gnupg
   sudo curl -fsSL https://deb.nodesource.com/setup_$NODE_VERSION -o nodesource_setup.sh
   sudo -E bash nodesource_setup.sh
   sudo apt-get install -y nodejs
   node -v
   npm -v

6. HACER BUILD DEL FRONTEND
    
    6.1. ELIMINAR package-lock.json y node_modules/
    
    pwd -> Esto debe arrojar la carpeta management_client

    rm package-lock.json
    rm -rf node_modules/

    6.2. INSTALAR DEPENDENCIAS DEL FRONTEND

    npm i

    6.3. CONSTRUIR LA APLICACION

    npm run build

7. CREAR LA IMAGEN DE DOCKER CON EL Dockerfile

    7.1. TIENE QUE ESTAR EN EL DIRECTORIO RAIZ DEL BACKEND

    7.2. AGREGAR LAS VARIABLES DE ENTORNO
    
        6.2.1. Enfasis en ENCRYPTION_SECRET. Se debe usar la primera linea de la llave privada: privyKey.pem que se creó en el punto 

            Para ver el contenido de la llave privada debe ejecutar el comando:
                cat src/main/resources/privyKey.pem estando en la carpeta cit-sac-back-end
    
    pwd -> cit-sac-back-end

    7.3 CREAR LA IMAGEN

    sudo docker build -t "SAC-APP"
