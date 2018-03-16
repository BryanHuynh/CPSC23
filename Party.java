import java.util.ArrayList;

public abstract class Party {
    private ArrayList<NPC> partyMembers = new ArrayList<>();
    private Player player;

    Party(Player player){
        this.player = player;

    }

    public abstract void render();

    /**
     * recruit an npc
     * @param npc
     * @param action
     * @return
     */
    public boolean lookForRecruitment(NPC npc, String action){
        NPC recruit = npc;
        if(npc == null){
            return false;
        }
        if(action.equalsIgnoreCase("r")){
            addMember(npc);
            return true;
        }
        return false;
    }

    /**
     * adds a new entity to the party
     * @param member
     */
    public void addMember(NPC member){
        partyMembers.add(member);
    }

    /**
     * removes a member from the party
     * @param member
     */
    public void removeMember(NPC member){
        partyMembers.remove(member);
    }

    /**
     * returns a clone of the party list. any modifications should be done through instance of party class
     * @return
     */
    public ArrayList<NPC> getPartyList(){
        ArrayList<NPC> clone = new ArrayList<>();

        for(NPC member: partyMembers){
            NPC memberClone = member.clone();
            memberClone.setAtk(member.getAtk());
            memberClone.setName(member.getName());
            memberClone.setAccuracy(member.getAccuracy());
            clone.add(memberClone);

        }
        return clone;
    }

}
