package adventure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameEngine {
    private final GameData gameData;
    private Room currentRoom;
    private ArrayList<String> inventory = new ArrayList<>();
    private boolean shouldDisplayInfo = true;
    private boolean shouldEndTheGame = false;
    private boolean wonTheGame = false;
    private final Map<String, Room> roomNamesToRoomsMap = new HashMap<>();

    public GameEngine(GameData gameData) {
        if (gameData != null) {
            this.gameData = gameData;
            currentRoom = gameData.getRooms().get(0);
            for (Room room : gameData.getRooms()) {
                roomNamesToRoomsMap.put(room.getName(), room);
                room.initDirectionsToRoomNamesMap();
            }
        } else {
            throw new NullPointerException("Game engine failed to load in game data.");
        }
    }

    /**
     * Void method that starts the game: prints opening dialogue, sets the starting room,
     * and creates UI object which will call public GameEngine methods as needed
     */
    public void startGame() {
        System.out.println("You just woke up in a spaceship and have no idea how you got here. \n" +
                "Your goal is to find the portal and return back to Earth. \n");
        setCurrentRoom(gameData.getRooms().get(0));
        UI ui = new UI(this);
        ui.promptUser();
    }

    /**
     * Method that displays all relevant information of the current room,
     * called by the UI if shouldDisplayInfo is true
     * @return String of all relevant information about the current room
     */
    public StringBuilder displayCurrentRoomInfo() {
        //string that will be returned
        StringBuilder roomInfo = new StringBuilder();

        //add all relevant information to roomInfo
        addRoomNameAndDescription(roomInfo, currentRoom.getName(), currentRoom.getDescription());
        addPossibleRoomDirections(roomInfo, currentRoom.getDirections());
        addRoomItems(roomInfo, currentRoom.getItems());
        roomInfo.append("Type \"view room\" to view the room");

        return roomInfo;
    }

    /* Helper Methods for building roomInfo string */

    public void addRoomNameAndDescription(StringBuilder roomInfo, String name, String description) {
        roomInfo.append(name.equals("Cell") ? "You are in your cell. " : "You are in the " + name + ". ");
        roomInfo.append(description).append("\n");
    }

    public void addPossibleRoomDirections(StringBuilder roomInfo, List<Direction> directions) {
        roomInfo.append("From here, you can go: ");
        for (int i = 0; i < directions.size(); ++i) {
            String direction = directions.get(i).getDirection();
            roomInfo.append(i < directions.size() - 1 ? direction + ", " : direction + "\n");
        }
    }

    public void addRoomItems(StringBuilder roomInfo, ArrayList<String> items) {
        if (items.size() > 0) {
            roomInfo.append("Items visible: ");
            for (int i = 0; i < items.size(); ++i) {
                roomInfo.append(i < items.size() - 1 ? items.get(i) + ", " : items.get(i) + "\n");
            }
        }
    }

    /**
     * Checks user input and calls helper methods to update game state as needed
     * @param input from CLI or API
     */
    public void checkInput(String input) {
        //make sure input is not null
        if (!(input != null && !input.trim().isEmpty())) {
            shouldDisplayInfo = false;
            System.out.println("Please enter a valid command.");
            return;
        }

        //make input lowercase and remove all extra whitespace
        input = input.toLowerCase().trim();
        input = input.replaceAll("\\s+", " ");

        //grab the first word from the input
        String firstWord = input.split(" ")[0];

        //separates words into an array
        String[] wordsArray = input.split(" ");

        /* check input and call corresponding helper methods, otherwise
        tell the user the input was not understood */
        switch(firstWord) {
            case "go":
                updateRoom(firstWord, input);
                break;
            case "take":
            case "grab":
            case "drop":
                if (firstWord.equals("drop")) {
                    updateItemsDrop(firstWord, input);
                } else {
                    updateItemsTakeOrGrab(firstWord, input);
                }
                break;
            case "examine":
                if (wordsArray.length == 1) {
                    setShouldDisplayInfo(true);
                    displayCurrentRoomInfo();
                } else {
                    setShouldDisplayInfo(false);
                    System.out.println("I don't understand \"" + input + "\", did you mean to type just \"examine\"?");
                }
                break;
            case "quit":
            case "exit":
                if (wordsArray.length == 1) {
                    setShouldEndTheGame(true);
                } else {
                    setShouldDisplayInfo(false);
                    System.out.println("I don't understand \"" + input + "\", did you mean to type " +
                            "just \"quit\" or \"exit\"?");
                }
                break;
            //default case is either view room or not understood
            default:
                if (input.equals("view room")) {
                    setShouldDisplayInfo(false);
                    DisplayRoomImage.displayRoomImage(getCurrentRoom().getRoomImageLink());
                } else {
                    setShouldDisplayInfo(false);
                    System.out.println("I don't understand \"" + input + "\", please enter a valid command.");
                }
                break;
        }
    }

    /* Helper Methods for Checking Input and Updating Game State accordingly */

    /**
     * updates the room based on the direction travelled from the current room
     * @param firstWord "go"
     * @param directionInput everything after "go"
     */
    private void updateRoom(String firstWord, String directionInput) {
        final String direction;

        //makes sure user specified somewhere to go
        if (!directionInput.equals("go")) {
            direction = directionInput.substring(firstWord.length() + 1);
        } else {
            System.out.println("Please specify somewhere to go.");
            setShouldDisplayInfo(false);
            return;
        }

        /* performs 2 hash table look ups to set the new current room to the room corresponding to the room name
        corresponding to the direction entered, otherwise direction is invalid */
        if (currentRoom.getDirectionsToRoomNames().containsKey(direction)) {
            setShouldDisplayInfo(true);
            String newRoomName = currentRoom.getDirectionsToRoomNames().get(direction);
            if (!newRoomName.equals("Earth")) {
                setCurrentRoom(roomNamesToRoomsMap.get(newRoomName));
            } else {
                setWonTheGame(true);
                setShouldEndTheGame(true);
            }
        } else {
            setShouldDisplayInfo(false);
            System.out.println("I can't " + directionInput + "..");
        }
    }

    /**
     * takes in input when the user takes or grabs something
     * and updates inventory and visible items accordingly
     * @param firstWord "take" or "grab"
     * @param takeString the full input string
     */
    private void updateItemsTakeOrGrab(String firstWord, String takeString) {
        setShouldDisplayInfo(false);

        //makes sure there is something to actually take or grab
        if (currentRoom.getItems().size() == 0) {
            System.out.println("There are no items to take or grab.");
        } else {
            if (firstWord.equals("take") || firstWord.equals("grab")) {
                //makes sure something is being taken or grabbed
                final String takeSubString;
                if (!takeString.equals("take") && !takeString.equals("grab")) {
                    takeSubString = takeString.substring(firstWord.length() + 1);
                } else {
                    System.out.println("Please specify an item to take or grab.");
                    return;
                }
                /* compares everything after the first word to the list of the current room's items, and adds
                it to user's inventory and removes it from the room's list of items if it is in the room */
                for (int i = 0; i < currentRoom.getItems().size(); i++) {
                    if (takeSubString.equals(currentRoom.getItems().get(i).toLowerCase())) {
                        inventory.add(currentRoom.getItems().get(i));
                        currentRoom.getItems().remove(currentRoom.getItems().get(i));
                        currentRoom.setItems(currentRoom.getItems());
                        setInventory(inventory);
                        return;
                    }
                }
                //if take or grab isn't valid
                System.out.println("There is no " + takeSubString + " to take.");
            }
        }
    }

    /**
     * takes in input when the user drops something
     * and updates inventory and visible items accordingly
     * @param firstWord "drop"
     * @param dropString the full input string
     */
    private void updateItemsDrop(String firstWord, String dropString) {
        setShouldDisplayInfo(false);

        //makes sure something is being dropped
        final String dropSubString;
        if (!dropString.equals("drop")) {
            dropSubString = dropString.substring(firstWord.length() + 1);
        } else {
            System.out.println("Please specify an item to drop.");
            return;
        }
        /* compares everything after the first word to the user's inventory, and removes it
           from inventory and adds it to the list of current room's items if there is a match */
        for (int i = 0; i < inventory.size(); ++i) {
            if (dropSubString.equals(inventory.get(i).toLowerCase())) {
                currentRoom.getItems().add(inventory.get(i));
                inventory.remove(inventory.get(i));
                currentRoom.setItems(currentRoom.getItems());
                setInventory(inventory);
                return;
            }
        }
        //if drop isn't valid
        System.out.println("You don't have a " + dropSubString + " to drop.");
    }

    /**
     * Method that gets called by the UI when shouldEndTheGame is true,
     * (when the user quits or has won), and displays the correct message
     * @return losing or winning message when game ends
     */
    public String gameOver() {
        if (wonTheGame) {
            return "You activated the portal and returned to Earth! The game has been won.";
        } else {
            return "You failed to make it back to Earth. Game over.";
        }
    }

    /* Getters and Setters */
    public Room getCurrentRoom() { return currentRoom; }
    public ArrayList<String> getInventory() { return inventory; }
    public boolean getShouldDisplayInfo() { return shouldDisplayInfo; }
    public boolean getShouldEndTheGame() { return shouldEndTheGame; }
    public void setCurrentRoom(Room currentRoom) { this.currentRoom = currentRoom; }
    public void setInventory(ArrayList<String> inventory) { this.inventory = inventory; }
    public void setShouldDisplayInfo(boolean shouldDisplayInfo) { this.shouldDisplayInfo = shouldDisplayInfo; }
    public void setShouldEndTheGame(boolean shouldEndTheGame) { this.shouldEndTheGame = shouldEndTheGame; }
    public void setWonTheGame(boolean wonTheGame) { this.wonTheGame = wonTheGame; }
}