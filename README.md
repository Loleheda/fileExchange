<h1>Пинчук Роман</h1>
Перед запуском приложения необходимо в командной строке запустить докер команду, которая указана ниже

docker run 
   -p 9000:9000 
   -p 9001:9001 
   --name minio1 
   -v D:\minio\data:/data 
   -e "MINIO_ROOT_USER=MINIOADMIN" 
   -e "MINIO_ROOT_PASSWORD=MINIOADMIN" 
   quay.io/minio/minio server /data --console-address ":9001"