package adventure;

import java.util.Scanner;

public class UI {
    private GameEngine gameEngine;

    UI(GameEngine gameEngine) { this.gameEngine = gameEngine; }

    public void promptUser() {
        Scanner userInput = new Scanner(System.in);

        //loop of displaying info -> prompting user -> checking input continues until GameEngine says to end the game
        while (!gameEngine.getShouldEndTheGame()) {
            //only display info if the GameEngine says to
            if (gameEngine.getShouldDisplayInfo()) System.out.println(gameEngine.displayCurrentRoomInfo());

            //prompt the user
            System.out.print("> ");

            //pass user input into GameEngine
            gameEngine.checkInput(userInput.nextLine());
        }
        System.out.println(gameEngine.gameOver());
    }
}