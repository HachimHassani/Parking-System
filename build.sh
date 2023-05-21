#build front end
cd parking-system-frontend
ng build
cd ..

#build backend
cd parkingsystem
./mvnw spring-boot:build-image
cd ..