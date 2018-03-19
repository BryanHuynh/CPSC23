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
        ArrayList<NPC> partyList = super.getPartyList();
        if(partyList.size() == 0){
            return;
        }
        int padding = 15;
        System.out.print(padLeft(" ", 10));
        for(NPC npc:  partyList){   //print out the names of the npcs
            System.out.print( "|"+padLeft(npc.getName(), padding)+"|");

        }
        System.out.println();
        System.out.print(padLeft("attack ", 10));
        for(NPC npc:  partyList){   //print out the attack damage
            System.out.print( "|"+padLeft(String.valueOf(npc.getAtk()), padding)+"|");
        }

        System.out.println();
        System.out.print(padLeft("weapon ",10));
        for(NPC npc:  partyList){   //weapon
            System.out.print( "|"+padLeft(npc.getWeapon(), padding)+"|");
        }
        System.out.println();
        System.out.print(padLeft("accuracy ", 10));
        for(NPC npc:  partyList){   //accuracy
            System.out.print( "|"+padLeft(String.valueOf(npc.getAccuracy()) + "%", padding)+"|");
        }

        System.out.println();
    }


    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }
}
