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

4. INSTALAR NODEJS 22

   sudo apt-get install && apt-get upgrade -y
   sudo apt-get install -y curl gnupg
   sudo curl -fsSL https://deb.nodesource.com/setup_$NODE_VERSION -o nodesource_setup.sh
   sudo -E bash nodesource_setup.sh
   sudo apt-get install -y nodejs
   node -v
   npm -v

5. HACER BUILD DEL FRONTEND
    
    5.1. ELIMINAR package-lock.json y node_modules/
    
    pwd -> Esto debe arrojar la carpeta management_client

    rm package-lock.json
    rm -rf node_modules/

    5.2. INSTALAR DEPENDENCIAS DEL FRONTEND

    npm i

    5.3. CONSTRUIR LA APLICACION

    npm run build

6. CREAR LA IMAGEN DE DOCKER CON EL Dockerfile

    6.1. TIENE QUE ESTAR EN EL DIRECTORIO RAIZ DEL BACKEND
    
    pwd -> cit-sac-back-end

    6.2. CREAR LA IMAGEN

    sudo docker build -t "SAC-APP"
