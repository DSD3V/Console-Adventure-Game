package adventure;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class DisplayRoomImageTests {
    //test for valid image display
    @Test
    public void testValidImageDisplay() {
        assertTrue(DisplayRoomImage.displayRoomImage("tests/test_data/cell.jpg"));
    }

    //test for invalid image display
    @Test
    public void testInvalidImageDisplay() {
        assertFalse(DisplayRoomImage.displayRoomImage("bad link"));
    }
}