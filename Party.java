import javax.swing.text.html.parser.Entity;
import java.lang.reflect.Member;
import java.util.ArrayList;

public abstract class Party {
    protected ArrayList<EntityCharacter> partyMembers = new ArrayList<>();
    protected Player player;

    /**
     * NPCs that are found can be recruited to become party of your team to defeat the enemy
     * Party allows you to add and remove team members along with modifying their layout
     * @param player
     */
    Party(Player player){
        this.player = player;
        partyMembers.add(player);
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


    public EntityCharacter getMember(String name){
        for(EntityCharacter member: partyMembers){
            if(member.getName().equals(name)) return member;
        }
        return null;
    }




    /**
     * returns all members that have hp > 0
     * @return
     */
    public ArrayList<EntityCharacter> getLivePartyMembers(){
        ArrayList<EntityCharacter> clone = new ArrayList<>(partyMembers);
        ArrayList<EntityCharacter> aliveList = new ArrayList<>();
        for(EntityCharacter member: clone){
            if(member.getHp() >= 0){
                aliveList.add(member);
            }
        }
        return aliveList;
    }

    public boolean isPartyDead(){
        boolean dead = true;
        for(EntityCharacter member: partyMembers){
            if(member.getHp() > 0){
                dead = false;
            }
        }
        return dead;
    }



    public boolean attemptEscape(){
        double escapeChance = escapeChance();
        return Math.random() >= 1.0 - (escapeChance);
    }


    /**
     * get the chance they have to escape
     * @return
     */
    public double escapeChance(){
        double sum = 0;
        for(EntityCharacter member: partyMembers){
            sum += member.getAccuracy() / 100;
        }
        return sum/partyMembers.size();
    }

    /**
     * adds a new entity to the party
     * @param member
     */
    public void addMember(NPC member){
        partyMembers.add(member);
        member.setVisable(false);
    }

    /**
     * damage a member of a party
     * @param character
     * @param damage
     */
    public void damageCharacter(EntityCharacter character, int damage){
        for(EntityCharacter members: partyMembers){
            if(members.getName().equals(character.getName())){
                members.damage(damage);
                System.out.println(members.name + " was Damaged");
            }
        }
    }

    /**
     * removes members of party that have less than 0 hp
     */
    public void removeDeadMembers(){
        for(EntityCharacter npc: partyMembers){
            if(npc.getHp() <= 0){
                npc.setDead(true);
            }
        }
    }

    /**
     * toggles the member to blocking
     * @param index
     */
    public void setToBlock(int index){
        partyMembers.get(index).setBlocking(true);
        System.out.println(partyMembers.get(index).getName() + " is blocking "+ partyMembers.get(index).isBlocking());
    }

    public void setToBlock(EntityCharacter member){
        for(EntityCharacter partMember: partyMembers){
            if(member.getName().equalsIgnoreCase(partMember.name)){
                partMember.setBlocking(true);
            }
        }
    }


    /**
     * resets all members to stop blocking
     */
    public void resetBlock(){
        for(EntityCharacter member: partyMembers){
            member.setBlocking(false);
        }
    }

    /**
     * removes a member from the party
     * @param member
     */
    public void removeMember(EntityCharacter member){
        partyMembers.remove(member);
    }

    /**
     * returns a clone of the party list. any modifications should be done through instance of party class
     * @return
     */
    public ArrayList<EntityCharacter> getPartyList(){
        ArrayList<EntityCharacter> clone = new ArrayList<>();

        for(EntityCharacter member: partyMembers){
            clone.add(member.clone());

        }
        return new ArrayList<>(partyMembers);
    }




}
