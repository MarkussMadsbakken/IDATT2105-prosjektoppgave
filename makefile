dev:
	npx concurrently -n Vue,Java -c green,red --pad-prefix "npm run dev --prefix ./frontend" "mvn spring-boot:run -f ./backend/pom.xml"
install:
	npm install --prefix ./frontend