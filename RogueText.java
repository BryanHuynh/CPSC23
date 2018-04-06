import java.util.ArrayList;
import java.util.Scanner;

public class RogueText extends Rogue {
    public RogueText(int length, int height, int roomsize, int numOfEnemies, int numOfNpcs) {
        super(length, height, roomsize, numOfEnemies, numOfNpcs);
        scanner = new Scanner(System.in);

    }


    /**
     * game loop used for the text version of the game
     */
    public void textVersionInit() {
        party = new PartyConsole(em.getPlayer());
        mapDisplay = new MapDisplayConsole(height + 1, width + 1, this);
        combat = new CombatConsole(em, party);
        db = new DialogBoxConsole();            // create intance of dialogbox to console
        mapDisplay.clearConsole();                //clear the screen
        mm.update();
        mapDisplay.render(getMm().getEntityMap());//render the screen to show the map
        db.render();                            //render the dialog box
        System.out.println();
    }


    public void textVersionLoop() {


        while (gameScreen) {
            ArrayList<Enemy> inRange = combat.combatCheck();
            mapDisplay.render(getMm().getEntityMap());//render the screen to show the map
            db.render();                            //render the dialog box
            if (inRange.size() > 0) {
                System.out.println("ENEMY IN RANGE!");
                ((CombatConsole) combat).render(em.getPlayer(), inRange);
            }

            party.render();                 //render the party members stats

            System.out.println("Enemies in range: " + inRange.size());
            System.out.println();

            kb.print();                     //keyboard instructions
            if (scanner.hasNext()) {            //check if there is a command
                String action = scanner.nextLine(); //save command to action variable
                if (action.equalsIgnoreCase("i")) {
                    gameScreen = false;
                    inventoryScreen = true;
                    break;
                } else if (inRange.size() > 0 && isNumeric(action)) { //start combat if enemy in range and action is a number
                    if (Integer.valueOf(action) < inRange.size()) {   //does the enemy exist to the corresponding number
                        ((CombatConsole) combat).battle(inRange.get(Integer.valueOf(action)));
                    }
                }

                playerControl(action);      //move the character if it is applicable
                db.setStr(em.playerTalk());     //set the dialog box if there is chance for player to talk to NPC
                mm.update();                    // update the map
                em.update(mm.getCharacterMap());//update the entity manager with the new map
                mapDisplay.clearConsole();        //clear the screen


                mm.update();
                em.update(mm.getCharacterMap());

            }
        }
        while (inventoryScreen) {
            if (scanner.hasNext()) {
                String action = scanner.nextLine(); //save command to action variable
                if (action.equalsIgnoreCase("exit")) {
                    gameScreen = true;
                    inventoryScreen = false;

                    break;
                }
            }

        }
    }


}
