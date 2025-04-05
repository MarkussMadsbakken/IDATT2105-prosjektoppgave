dev:
	npx concurrently --kill-others -n Vue,Java -c green,red --pad-prefix "npm run dev --prefix ./frontend" "mvn spring-boot:run -f ./backend/pom.xml"
install:
	npm install --prefix ./frontend
test-all:
	npm run test:unit --prefix ./frontend
	npm run test:e2e --prefix ./frontend
	mvn test -f ./backend/pom.xml
test-frontend-unit:
	npm run test:unit --prefix ./frontend
test-frontend-e2e:
	npm run test:e2e --prefix ./frontend
test-backend:
	mvn test -f ./backend/pom.xml
