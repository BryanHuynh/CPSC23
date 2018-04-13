Hello there! Thank you for trying out our project "Rogue"! Follow these instructions to get it up and running smoothly.

System requirements:
- Java 8 (including JDK 8)
#Source code can be found in the src folder

How to run:
1. After downloading the file you should get a compressed folder in whichever destination you chose to download it in
2. Unzip this file by simply right clicking it and choosing "Extract here"
3. This will create a new folder called "Rogue" that contains all the files needed for the game
4. navigating to the bin folder, run_gui.sh & run_text.sh will run the games Gui version or Text version respectively
5. to ensure the game runs smoothly, DO NOT DELETE the gamesaves or addition's folder

Main menu:
        - a menu will prompt you to select [Start, Load, Exit]
        [Start]:
            next you will be prompted to pick from [Easy, Medium, Hard]
            the difficultly will adjust the number of NPC's and enemies that will be spawned
        [Load]:
            reload the game from the previous save
        [exit]:
            closes the game

General
    - In both the GUI version and the text version, the following symbols are used to represent entities:
        - 'X' : the player
        - 'e' : the enemies
        - 'c' : NPCs (Non-Player Characters)
        - '#' : obstacles
    - In both the text version and the GUI version, the controls for movement are the same.
    - The WASD keys move the player around the map (Though, in the text version you must press the return key after pressing any of the WASD keys)

Party:
    When the player character is 1 unit away from an NPC (c), the player character can recruit them into their party.
    This gives the benefit of having an extra character option in combat.

Saving:
    while on the map screen, The user can save the current state of the game.
    In the text version, the user can type "save".
    in the GUI version, the button labeled save will save the game
    * note that only one instance of save can happen. All saves will override the previous

Combat general:
    - If the player finds themselves next to an enemy, they can enter combat by typing a number assigned to an enemy surrounding them and pressing Enter
        * (Based on the number of enemies the player encounters at once, this can be any number from 0 - 3
    - Once they've chosen an enemy to battle. A battle sequence will begin. The combat begins with by running through all party members (including the player)
      followed by the enemy
    - when it's a party members turn, there will be a list of their possible battle options.
        - The member can attack the enemy, but with a chance to miss based on the player's "accuracy" stat.
        - Blocking gives that member the ability to block incoming attacks, negating all damage, for a single turn
        - The member can skip their turn entirely
        - the member can run, bringing their entire team, which gives the party a chance to escape the enemy, returning to the main screen
    - Once the enemy's health reaches 0 the battle is over.
    - If the main player character dies, the battle is over, and the game as well
    - If a party member dies, their turn will be skipped every time.
    - When leaving combat, the parties health is partially boosted

Combat (TEXT)
    when it's a party members turn, there will be a list of their possible battle options.
            - typing 'a', The member can attack the enemy, but with a chance to miss based on the player's "accuracy" stat.
            - typing 'b', Blocking gives that member the ability to block incoming attacks, negating all damage, for a single turn
            - typing 's', The member can skip their turn entirely
            - typing 'r', the member can run, bringing their entire team, which gives the party a chance to escape the enemy, returning to the main screen


Combat (GUI)
    when it's a party members turn, there will be a list of their possible battle options.
            - clicking the attack button, The member can attack the enemy, but with a chance to miss based on the player's "accuracy" stat.
            - clicking the block button, Blocking gives that member the ability to block incoming attacks, negating all damage, for a single turn
            - clicking the skip button, The member can skip their turn entirely
            - clicking the run button, the member can run, bringing their entire team, which gives the party a chance to escape the enemy, returning to the main screen
