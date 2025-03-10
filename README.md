# Requirements

## Linux & Mac:

- SDKMAN: https://sdkman.io/install/

## Windows:

- If you don't have a life, can try to install skman using WSL or Git Bash
- If you have a life:
  - Java JDK 21
  - Maven 3.9.8

Probably, if you have IntelliJ or Eclipse installed, you can easily choose your JDK and Maven version.

# Installation:

## Linux & Mac:

```declarative
sdk env
mvn clean install
```
## Windows

Have you tried to use Linux?

# Exercise:

See file [KATA_INSTRUCTIONS.md](KATA_INSTRUCTIONS.md)

# CI/CD instructions

## How to test application:

```mvn test```

## How to build a docker image

For building a dockerfile, firstly you must package the project into an executable jar file. If you executed the tests in a previous step, you can skip tests execution:

```mvn package -DskipTests=true```

Then, you'll have a executable jar file in ```target``` folder.

Then, you can run your docker build command. You must run it on root folde:

```docker build -t <app-name> .```

Then you can run your docker image:

```docker run -p 8080:8080 <app-name>```

Or deploy in a K8 environment. The application port is 8080

You can verify your app is up in the url [http://localhost:8080/swagger-ui/index.html]()


  


