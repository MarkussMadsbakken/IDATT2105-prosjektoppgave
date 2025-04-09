dev:
	set -o allexport; . .env.local; set +o allexport; npx concurrently --kill-others -n Vue,Java -c green,red --pad-prefix "npm run dev --prefix ./frontend" "mvn spring-boot:run -f ./backend/pom.xml"
install:
	npm install --prefix ./frontend
test-all:
	npm run test:unit --prefix ./frontend
	npm run test:e2e --prefix ./frontend
	mvn test -f ./backend/pom.xml
test-frontend-unit:
	npm run test:unit --prefix ./frontend
test-backend:
	mvn test -f ./backend/pom.xml
test-e2e-dev:
	npx dotenv-cli -v SPRING_PROFILES_ACTIVE=test-e2e -v DATABASE_URL=jdbc:h2:mem:testdb -v DATABASE_USER=h2 -v DATABASE_PASSWORD=h2 -v SPRING_DATABASE_DRIVER=org.h2.Driver -- npx concurrently --raw --kill-others "npm run test:e2e:dev --prefix ./frontend" "mvn spring-boot:run -f ./backend/pom.xml -Ptest-e2e"
