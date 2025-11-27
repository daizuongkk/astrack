# AssetTrack

A simple Java Swing application to track assets (AssetTrack). This repository contains a small educational project organized with a basic MVC layout.

## Structure

- `src/` - Java source code, organized into packages: `controller`, `model`, `view`, `util`.
- `data/` - CSV files and user data.
- `assets/`, `images/` - static images used by the UI.

## Requirements

- Java 11 or later (JDK installed)

## Run (development)

Open a Windows `cmd` in the project root and run:

```
mkdir bin
javac -d bin -sourcepath src src\\Main.java
java -cp bin Main
```

Or import the project into an IDE (IntelliJ IDEA, Eclipse) and run the `Main` class.

## Recommendations

- Add a build tool (Maven or Gradle) for dependency management and reproducible builds.
- Add unit tests and a CI pipeline for quality.

## License

This project is educational. Add a license if you plan to publish it.
