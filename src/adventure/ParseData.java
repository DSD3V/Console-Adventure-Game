package adventure;

import com.google.gson.Gson;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ParseData {
    /**
     * Method for parsing JSON file with gson and converting it to a Java object
     * @param filePath to JSON data as a string, from repository root
     * @return posts object if parsed successfully, otherwise return null
     */
    public static Data parseData(String filePath) {
        Data data = new Data();
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get(filePath));
            data = gson.fromJson(reader, Data.class);
            reader.close();
        } catch (Exception e) {
            System.out.println("Failed to parse file.");
        }
        return data;
    }
}