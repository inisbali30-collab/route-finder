# route-finder
Service that is able to calculate the shortest land route from one country to another.

Build & Run Instructions

Make sure you have installed:

Java 22+

Maven 3.13+

Check versions:

java -version
mvn -version

▶ 1. Clone the Repository

git clone https://github.com/inisbali30-collab/route-finder.git
cd route-finder

▶ 2. Build the Application
mvn clean install

This will:

Download dependencies

Compile the project

Run tests

Package the application into a JAR file

The JAR file will be located in:

target/route-finder-1.0.0.jar

▶ 3. Run the Application

Option A – Using Maven
mvn spring-boot:run

Option B – Using the JAR file
java -jar target/route-finder-1.0.0.jar

The application will start on: http://localhost:8080
