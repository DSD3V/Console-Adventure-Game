import adventure.Data;
import adventure.GameEngine;
import adventure.ParseData;

public class Main {
    public static void main(String[] args) {
        //Parses JSON file and stores data in Data object
        Data gameData = ParseData.parseData("src/data/game_data.json");

        //Creates GameEngine object which will use data
        GameEngine gameEngine = new GameEngine(gameData.getGameData());

        //Starts the game
        gameEngine.startGame();
    }
}