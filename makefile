dev:
	npx dotenv-cli -e .env.local -- npx concurrently --kill-others -n Vue,Java -c green,red --pad-prefix "npm run dev --prefix ./frontend" "mvn spring-boot:run -f ./backend/pom.xml"
install:
	npm install --prefix ./frontend
	mvn install -f ./backend/pom.xml
test-frontend-unit:
	npm run test:unit --prefix ./frontend
test-backend:
	mvn test -f ./backend/pom.xml
test-e2e-dev:
	npx dotenv-cli -v SPRING_PROFILES_ACTIVE=test-e2e -v DATABASE_URL=jdbc:h2:mem:testdb -v DATABASE_USER=h2 -v DATABASE_PASSWORD=h2 -v SPRING_DATABASE_DRIVER=org.h2.Driver -- npx concurrently --raw --kill-others "npm run test:e2e:dev --prefix ./frontend" "mvn spring-boot:run -f ./backend/pom.xml -Ptest-e2e"
test-e2e-prod:
	npx dotenv-cli -v SPRING_PROFILES_ACTIVE=test-e2e -v DATABASE_URL=jdbc:h2:mem:testdb -v DATABASE_USER=h2 -v DATABASE_PASSWORD=h2 -v SPRING_DATABASE_DRIVER=org.h2.Driver -- npx concurrently --raw --kill-others "npm run test:e2e --prefix ./frontend" "mvn spring-boot:run -f ./backend/pom.xml -Ptest-e2e"
build:
	npm run build --prefix ./frontend
	mvn clean package -f ./backend/pom.xml
serve:
	npx dotenv-cli -e .env.local -- npx concurrently --kill-others -n Vue,Java -c green,red --pad-prefix "npx serve /frontend/dist" "java -jar ./backend/target/prosjektoppgave-0.0.1-SNAPSHOT.jar"