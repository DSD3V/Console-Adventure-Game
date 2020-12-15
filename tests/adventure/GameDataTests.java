package adventure;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GameDataTests {
    private Gson gson;
    private Data gameData;

    @Before
    public void setUp() {
        gson = new Gson();
        gameData = ParseData.parseData("tests/test_data/modified_data.json");
    }

    //checks to see if number of rooms is correct
    @Test
    public void testGetRooms() {
        assertEquals(gameData.getGameData().getRooms().size(), 2);
    }

    //checks if beginning, middle, and end of first room is correct
    @Test
    public void testGetNameRoom1() {
        assertEquals(gameData.getGameData().getRooms().get(0).getName(), "Room 1");
    }

    @Test
    public void testGetDirectionsRoom1() {
        assertEquals(gameData.getGameData().getRooms().get(0).getDirections().get(0).getDirection(), "North");
    }

    @Test
    public void testGetImageLinkRoom1() {
        assertEquals(gameData.getGameData().getRooms().get(0).getRoomImageLink(), "testlink1.jpg");
    }

    //checks if beginning, middle, and end of second room is correct
    @Test
    public void testGetNameRoom2() {
        assertEquals(gameData.getGameData().getRooms().get(1).getName(), "Room 2");
    }

    @Test
    public void testGetItemsRoom2() {
        //there are 2 items in the items array
        assertEquals(gameData.getGameData().getRooms().get(1).getItems().size(), 2);
    }

    @Test
    public void testGetImageLinkRoom2() {
        assertEquals(gameData.getGameData().getRooms().get(1).getRoomImageLink(), "testlink2.jpg");
    }
}