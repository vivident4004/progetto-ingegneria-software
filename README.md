# Sensor monitoring system

University project for the Software Engineering course.

A small Java console application that simulates a monitoring station with heterogeneous sensors (two thermometers, a hygrometer and a barometer). A control unit (*Centralina*) receives the readings and shows the latest value of each sensor, with the percentage change from the previous reading.

## How to run

Requires **Java 16+**. From the project root:

```sh
javac -d out src/Main.java src/data/*.java src/domain/*.java src/observer/*.java src/sensors/*.java
java -cp out Main
```

A text menu lets you show the latest readings, update a single sensor or update all sensors.

Unit tests are in `src/test` and use **JUnit 5**.

## Architecture

The project is built around three design patterns:

- **Adapter** – `sensors/A`, `B`, `C` are simulated third-party sensors with different interfaces; `domain/AdapterA`, `AdapterB`, `AdapterC` adapt them to the common `Component` interface.
- **Composite** – `domain/Composite` groups sensors (the monitoring station) and treats them as a single `Component`.
- **Observer** – adapters are `Observable`; `observer/Centralina` is the `Observer` that receives each `data/Misurazione` (value, unit, sensor name, variation).

```
src/
├── Main.java        # console menu, system setup
├── data/            # Misurazione (measurement)
├── domain/          # Component, Composite, Adapters
├── observer/        # Centralina (control unit)
├── sensors/         # simulated sensors A, B, C
└── test/            # JUnit tests
```

A full report (in Italian) is in `relazione_progetto.pdf`.

---

*No AI was used to write the code of this project.*
