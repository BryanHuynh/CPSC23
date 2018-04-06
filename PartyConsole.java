import java.util.ArrayList;

public class PartyConsole extends Party {

    public PartyConsole(Player player) {
        super(player);
    }


    /**
     * draws the party members and their stuff
     */
    @Override
    public void render() {
        ArrayList<EntityCharacter> partyList = super.getPartyList();
        if (partyList.size() == 0) {
            return;
        }
        int padding = 20;
        System.out.print(padLeft(" ", 10));
        for (EntityCharacter member : partyList) {   //print out the names of the npcs
            System.out.print("|" + padLeft(member.getName(), padding) + "|");


        }
        System.out.println();

        System.out.print(padLeft("HP ", 10));
        for (EntityCharacter member : partyList) {   //print out the attack damage
            if (member.isDead()) {
                System.out.print("|" + padLeft("DEAD!", padding) + "|");
            } else {
                System.out.print("|" + padLeft(String.valueOf(member.getHp()), padding) + "|");
            }
        }

        System.out.println();
        System.out.print(padLeft("attack ", 10));
        for (EntityCharacter member : partyList) {   //print out the attack damage
            System.out.print("|" + padLeft(String.valueOf(member.getAtk()), padding) + "|");
        }

        System.out.println();
        System.out.print(padLeft("weapon ", 10));
        for (EntityCharacter member : partyList) {   //weapon
            System.out.print("|" + padLeft(member.getWeapon(), padding) + "|");
        }
        System.out.println();
        System.out.print(padLeft("accuracy ", 10));
        for (EntityCharacter member : partyList) {   //accuracy
            System.out.print("|" + padLeft(String.valueOf(member.getAccuracy()) + "%", padding) + "|");
        }

        System.out.println();
    }


    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }
}
