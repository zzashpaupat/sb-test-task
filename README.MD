# How to build

To build project on Linux/Mac ```./gradlew build ```\
on Windows ```gradlew.bat build```

#How to build Docker image
```docker build -t covid-stats-app .```

# How to run
To run project from Gradle on Linux/Mac
```./gradlew bootRun```\
on Windows
```gradlew.bat bootRun```

To run project from Docker ```docker run -p 8080:8080 covid-stats-app```

# API docs

To view REST API after launch visit\
http://localhost:8080/swagger-ui.html

To view GraphiQL in-browser IDE visit\
http://localhost:8080/graphiql