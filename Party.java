import java.util.ArrayList;

public abstract class Party {
    private ArrayList<NPC> partyMembers = new ArrayList<>();
    private Player player;

    /**
     * NPCs that are found can be recruited to become party of your team to defeat the enemy
     * Party allows you to add and remove team members along with modifying their layout
     * @param player
     */
    Party(Player player){
        this.player = player;

    }

    public abstract void render();

    /**
     * recruit an npc into your party.
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
            npc.setVisable(false);                      //remove them from the map
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
        member.setVisable(false);
    }

    public void damageCharacter(EntityCharacter character, int damage){
        for(EntityCharacter members: partyMembers){
            if(members.getName().equals(character.getName())){
                members.damage(damage);
                System.out.println(members.name + " was Damaged");
            }
        }
    }

    public void removeDeadMembers(){
        for(NPC npc: partyMembers){
            if(npc.getHp() <= 0){
                removeMember(npc);
            }
        }
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
            memberClone.setAtk(member.getAtk());                //cloning things over that are random upon creation
            memberClone.setName(member.getName());
            memberClone.setAccuracy(member.getAccuracy());
            memberClone.setHp(member.getHp());
            clone.add(memberClone);

        }
        return clone;
    }




}
