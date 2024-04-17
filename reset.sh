#!/bin/bash
cd keycloak-test
docker compose down
rm -r h2
rm -r volumes
docker rmi keycloak-keycloak
cd ..
mvn package
cp ./target/keycloakFaceAuthPlugin-1.0-SNAPSHOT.jar keycloak-test/plugins/
cd keycloak-test
docker compose up -d