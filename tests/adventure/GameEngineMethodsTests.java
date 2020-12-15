package adventure;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class GameEngineMethodsTests {
    private Data gameData;
    private GameEngine gameEngine;

    @Before
    public void setUp() {
        gameData = ParseData.parseData("tests/test_data/modified_data.json");
        gameEngine = new GameEngine(gameData.getGameData());
        gameEngine.setCurrentRoom(gameData.getGameData().getRooms().get(0));
    }

    //Tests for GameEngine methods (checkInput is tested in InputOutputTests and startGame is tested implicitly)

    //implicitly checks addRoomNameAndDescription, addPossibleRoomDirections, and addRoomItems
    @Test
    public void testRoomInfoPrinting() {
        String expected = "You are in the Room 1. Modified Description\nFrom here, you can go: North\nType \"view room\" to view the room";
        assertEquals(expected, gameEngine.displayCurrentRoomInfo().toString());
    }

    @Test
    public void testGameOver() {
        gameEngine.setWonTheGame(true);
        String expected = "You activated the portal and returned to Earth! The game has been won.";
        assertEquals(expected, gameEngine.gameOver());
    }
}