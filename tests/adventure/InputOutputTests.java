package adventure;

import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

public class InputOutputTests {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private GameData gameData;
    private GameEngine gameEngine;

    @Before
    public void setUp() {
        gameData = ParseData.parseData("tests/test_data/modified_data2.json").getGameData();
        gameEngine = new GameEngine(gameData);
        gameEngine.setCurrentRoom(gameData.getRooms().get(0));
        System.setOut(new PrintStream(outputStream));
    }

    /* Tests for invalid inputs */

    //tests for null characters
    @Test
    public void testNewline() {
        gameEngine.checkInput("\n");
        assertEquals("Please enter a valid command.\r\n", outputStream.toString());
    }

    @Test
    public void testSpace() {
        gameEngine.checkInput(" ");
        assertEquals("Please enter a valid command.\r\n", outputStream.toString());
    }

    @Test
    public void testTab() {
        gameEngine.checkInput(" ");
        assertEquals("Please enter a valid command.\r\n", outputStream.toString());
    }

    //test for invalid commands
    @Test
    public void testInvalidCommand() {
        gameEngine.checkInput("invalid command\\");
        assertEquals("I don't understand \"invalid command\\\", please enter a valid command.\r\n", outputStream.toString());
    }

    @Test
    public void testEmptyGo() {
        gameEngine.checkInput("go");
        assertEquals("Please specify somewhere to go.\r\n", outputStream.toString());
    }

    @Test
    public void testInvalidGo() {
        gameEngine.checkInput("go invalid");
        assertEquals("I can't go invalid..\r\n", outputStream.toString());
    }

    @Test
    public void testEmptyTake() {
        gameEngine.checkInput("take");
        assertEquals("Please specify an item to take or grab.\r\n", outputStream.toString());
    }

    @Test
    public void testInvalidTake() {
        gameEngine.checkInput("take invalid");
        assertEquals("There is no invalid to take.\r\n", outputStream.toString());
    }

    @Test
    public void testEmptyDrop() {
        gameEngine.checkInput("drop");
        assertEquals("Please specify an item to drop.\r\n", outputStream.toString());
    }

    @Test
    public void testDropInvalid() {
        gameEngine.checkInput("drop invalid");
        assertEquals("You don't have a invalid to drop.\r\n", outputStream.toString());
    }

    @Test
    public void testInvalidExamine() {
        gameEngine.checkInput("examine invalid");
        assertEquals("I don't understand \"examine invalid\", did you " +
                "mean to type just \"examine\"?\r\n", outputStream.toString());
    }

    @Test
    public void testInvalidQuit() {
        gameEngine.checkInput("quit invalid");
        assertEquals("I don't understand \"quit invalid\", did you" +
                " mean to type just \"quit\" or \"exit\"?\r\n", outputStream.toString());
    }

    /* Tests for valid inputs */

    @Test
    public void testValidGo() {
        gameEngine.checkInput("go north");
        assertEquals("Room 2", gameEngine.getCurrentRoom().getName());
    }

    @Test
    public void testValidTake() {
        gameEngine.checkInput("take item 1");
        ArrayList<String> items = new ArrayList<>();
        items.add("Item 1");
        assertEquals(items, gameEngine.getInventory());
    }

    @Test
    public void testValidDrop() {
        ArrayList<String> items = new ArrayList<>();
        items.add("Item 1");
        gameEngine.setInventory(items);
        gameEngine.checkInput("drop item 1");
        items.remove("Item 1");
        assertEquals(items, gameEngine.getInventory());
    }

    @Test
    public void testValidExamine() {
        gameEngine.checkInput("examine");
        String expected = "You are in the Room Name. Description\nFrom here, you can go: North\nItems visible: Item 1\nType \"view room\" to view the room";
        assertEquals(expected, gameEngine.displayCurrentRoomInfo().toString());
    }

    @Test
    public void testValidQuit() {
        gameEngine.checkInput("quit");
        assertEquals("You failed to make it back to Earth. Game over.", gameEngine.gameOver());
    }
}