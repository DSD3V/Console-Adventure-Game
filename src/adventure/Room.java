package adventure;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Room {
    private String name;
    private String description;
    private ArrayList<String> items;
    private List<Direction> directions;
    private String roomImageLink;
    private String imageURL;
    private Map<String, String> directionsToRoomNamesMap = new HashMap<>();
    private List<String> directionNames = new ArrayList<>();

    //initializes direction : room name HashMap - called from GameEngine constructor
    public void initDirectionsToRoomNamesMap() {
        for (Direction direction : directions) {
            directionsToRoomNamesMap.put(direction.getDirection().toLowerCase(), direction.getRoomInDirection());
            directionNames.add(direction.getDirection());
        }
    }

    /* Getters and Setters */
    public String getName() { return name; }
    public String getDescription() { return description; }
    public ArrayList<String> getItems() { return items; }
    public List<Direction> getDirections() { return directions; }
    public String getRoomImageLink() { return roomImageLink; }
    public String getImageURL() { return imageURL; }
    public Map<String, String> getDirectionsToRoomNames() { return directionsToRoomNamesMap; }
    public List<String> getDirectionNames() { return directionNames; }
    public void setItems(ArrayList<String> items) { this.items = items; }
}