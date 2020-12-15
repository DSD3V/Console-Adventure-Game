package adventure;

import org.junit.Test;

public class UITests {

    //Test for initializing UI with a null gameEngine
    @Test(expected = NullPointerException.class)
    public void testUIWithEmptyGameEngine() {
        Data gameData = new Data();
        GameEngine gameEngine = new GameEngine(gameData.getGameData());
        UI ui = new UI(gameEngine);
    }

    //Test for trying to initialize a UI with a GameEngine with data with an incorrect schema
    @Test(expected = NullPointerException.class)
    public void testGameEngineWithBadData() {
        Data gameData = ParseData.parseData("tests/test_data/incorrect_schema_file.json");
        GameEngine gameEngine = new GameEngine(gameData.getGameData());
        UI ui = new UI(gameEngine);
    }
}