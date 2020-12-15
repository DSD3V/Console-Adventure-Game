package adventure;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class ParsingTests {
    /* Checks to see if a file was parsed successfully.
      returns true if it was, otherwise false. */
    private boolean checkParse(String filePath) {
        return ParseData.parseData(filePath).getGameData() != null;
    }

    /* Tests for successful parsing: the original data and the original data modified
    while still matching original schema */
    @Test
    public void testOriginalDataParse() {
        String filePath = "tests/test_data/original_data.json";
        assertTrue(checkParse(filePath));
    }

    @Test
    public void testModifiedOriginalDataParse() {
        String filePath = "tests/test_data/modified_data.json";
        assertTrue(checkParse(filePath));
    }

    /* Tests for unsuccessful parsing: an empty file, a file with incorrect schema, a file with incorrect values,
   a file with incorrect syntax, and a file that doesn't exist */
    @Test(expected = NullPointerException.class)
    //tests a completely empty file
    public void testEmptyFile() {
        String filePath = "tests/test_data/empty_file.json";
        assertFalse(checkParse(filePath));
    }

    @Test
    //tests a file with an incorrect schema
    public void testIncorrectSchemaFile() {
        String filePath = "tests/test_data/incorrect_schema_file.json";
        assertFalse(checkParse(filePath));
    }

    @Test
    //tests a file that is missing a comma
    public void testIncorrectSyntax() {
        String filePath = "tests/test_data/incorrect_syntax_file.json";
        assertFalse(checkParse(filePath));
    }

    @Test
    //tests a file that doesn't exist
    public void testFileThatDoesntExist() {
        String filePath = "tests/test_data/doesntExist.json";
        assertFalse(checkParse(filePath));
    }
}