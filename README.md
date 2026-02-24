# Java Virtual World Simulator (Swing GUI)

A graphical, turn-based ecosystem simulator built with Java and the Swing UI toolkit. This project extends core Object-Oriented Programming (OOP) concepts by integrating event-driven programming, graphical user interfaces, and interchangeable spatial structures.



## Core Architecture and OOP Principles

This application is strictly modeled using advanced OOP paradigms:
* **Abstraction & Polymorphism:** Relies on an abstract `Organism` base class, further specialized into `Animal` and `Plant` abstractions. All interactions (collisions, combat, reproduction) are resolved dynamically at runtime.
* **Event-Driven UI:** Utilizes Java Swing (`JFrame`, `JPanel`, `JButton`, `KeyBindings`/`KeyListeners`) to handle user inputs, turn progression, and world rendering.
* **Interchangeable Board Implementations:** The environment relies on a high-level abstraction for the spatial grid, allowing the game to seamlessly switch between two distinct implementations:
  1. A standard 2D Cartesian grid (Square tiles).
  2. A Hexagonal grid (Hex tiles).

## Implemented Features

1. **Graphical User Interface (GUI):** The entire simulation is rendered visually using Java Swing. Menus and buttons are used to control the flow of the game, including advancing turns and managing game states.
2. **Interactive Spawning:** Users can click on any empty tile on the grid to open a context menu (or dialog) and dynamically spawn any specific organism into the world.
3. **Turn-Based Ecosystem:** Organisms act sequentially based on their `initiative` and `age`.
4. **Complex Entity Interactions:** * **Combat & Survival:** Higher strength organisms survive collisions. Specialized behaviors (like the Fox's avoidance or the Turtle's defense) dynamically alter standard rules.
   * **Propagation & Reproduction:** Same-species animals breed upon collision, while plants passively spread to neighboring tiles.
5. **State Persistence:** The current state of the entire ecosystem (including grid geometry, organisms, and turn count) can be saved to a file and loaded via UI controls.
6. **The Human Entity:** A unique player-controlled organism manipulated via keyboard arrow keys, featuring a specialized, cooldown-based active ability (e.g., Immortality, Magic Potion, or Alzur's Shield).

## Organisms Roster

* **Animals:** Wolf, Sheep, Fox, Turtle, Antelope, Cyber-sheep (specializing in hogweed extermination).
* **Plants:** Grass, Dandelion, Guarana (strength booster), Deadly Nightshade (lethal), Sosnowsky's Hogweed (area-of-effect lethal plant).

## Setup and Execution

### Using an IDE (IntelliJ IDEA / Eclipse)
1. Clone the repository.
2. Open the project folder in your preferred Java IDE.
3. Ensure the project SDK is set to Java 8+.
4. Locate your main class and run the project using the IDE's built-in execution tool.
