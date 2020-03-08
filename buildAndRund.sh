#/bin/bash

docker-compose down -v
mvn -U clean install
mvn package
docker-compose build --no-cache
docker-compose up -d service_db_name
echo "Waiting 10 seconds for mysql to be ready..."
sleep 10
docker-compose up -d service_db_name
echo "Waiting 20 seconds for wildfly to be ready..."
sleep 20
echo "the application is running in localhost on the port 8080"
echo "trying to open the browser..."
sleep 3
start "http://localhost:8080/subh/"


