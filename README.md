# Game Theory Simulation: Iterated Prisoner's Dilemma
A Java implementation of the Iterated Prisoner's Dilemma game theory simulation, featuring multiple strategies competing against each other.

## 📋 Project Overview
This project simulates the classic Iterated Prisoner's Dilemma from game theory, where multiple strategies compete in a tournament format. Each strategy is tested against all others (and itself) over multiple iterations with randomized round counts.

## 🎮 Game Rules
**Both Cooperate:** Each player gets 3 points

**Both Defect:** Each player gets 1 point

**One Cooperates, One Defects:** Defector gets 5 points, Cooperator gets 0 points

## 🧠 Available Strategies
### 1. AlwaysCooperate
Behavior: Always chooses COOPERATE

Type: Naive cooperative

### 2. AlwaysDefect
Behavior: Always chooses DEFECT

Type: Selfish exploitative

### 3. Random
Behavior: Randomly chooses COOPERATE or DEFECT
Type: Unpredictable

## 🚀 How to Run
Prerequisites
Java JDK 8 or higher

Any Java IDE or command line

Compilation and Execution
Using Command Line:

#### Compile all Java files
```bash
javac -d out src/main/java/game/*.java src/main/java/game/strategies/*.java
java -cp out game.Main
```

### Run the simulation
java Main
Using IDE:

Import the project into your preferred Java IDE

Run the Main.java file

## 📊 Tournament Rules
Each strategy plays against every other strategy **AND itself**

**5 repetitions** of each matchup

**180-240 random rounds** per game (uniform distribution)

Final scores are **averaged** across all repetitions

## 🛠️ Adding New Strategies
To create a new strategy, implement the Strategy interface:
```java
import java.util.List;

public class YourStrategy implements Strategy {
    
    @Override
    public Action decideMove(List<Action> myHistory, List<Action> opponentHistory) {
        // Your strategy logic here
        // Return either Action.COOPERATE or Action.DEFECT
    }
    
    @Override
    public String getName() {
        return "YourStrategyName";
    }
}
```
Then add it to the strategies list in Main.java:
```java
List<Strategy> strategies = Arrays.asList(
    new AlwaysCooperate(),
    new AlwaysDefect(), 
    new Random(),
    new YourStrategy()  // Add your new strategy here
);
```

## 📈 Expected Output
The program will display tournament results like:
```
Tournament Results:
1. StrategyName1 - Average Score: XX.XX
2. StrategyName2 - Average Score: XX.XX
...
```

## 🔧 Configuration
You can modify these constants in Game.java:
```text
MIN_ROUNDS: Minimum rounds per game (default: 180)

MAX_ROUNDS: Maximum rounds per game (default: 240)

REPETITIONS: Number of repetitions per matchup (default: 5)
```

## 📚 Game Theory Background
The Prisoner's Dilemma demonstrates why two completely rational individuals might not cooperate, even if it appears to be in their best interest. The iterated version allows for the evolution of cooperation through reciprocal strategies.

## 🤝 Contributing
Feel free to contribute by adding new strategies, improving the simulation, or enhancing the output formatting.