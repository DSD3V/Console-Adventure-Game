package adventure;

import org.junit.Test;

public class GameEngineTests {

    //Test for trying to initialize a Game Engine with null data
    @Test(expected = NullPointerException.class)
    public void testGameEngineWithNullData() {
        Data gameData = new Data();
        GameEngine gameEngine = new GameEngine(gameData.getGameData());
    }

    //Test for trying to initialize a Game Engine with data with an incorrect schema
    @Test(expected = NullPointerException.class)
    public void testGameEngineWithBadData() {
        Data gameData = ParseData.parseData("tests/test_data/incorrect_schema_file.json");
        GameEngine gameEngine = new GameEngine(gameData.getGameData());
    }
}