PASOS PARA INSTALAR LA APLICACIÓN EN UN CONTENEDOR DE DOCKER (Ubuntu)

1. INSTALAR DOCKER

2. CREAR UNA RED PERSONALIZADA PARA LOS CONTENEDORES

3. INSTALAR UNA IMAGEN DE MySQL

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
