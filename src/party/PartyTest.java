package party;

import entity.EntityCharacter;
import entity.NPC;
import entity.Player;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PartyTest {


    @Test
    public void _get_party_list_privacy_leak_changing_NPC_attack(){
        /**
         * the user should not be able to change the values of the members from the partyList
         */
        Party party = new PartyConsole(new Player(0,0,'x'));
        NPC npc1 = new NPC(5,5,'c');
        NPC npc2 = new NPC(6,6,'c');
        party.addMember(npc1);
        party.addMember(npc2);
        ArrayList<EntityCharacter> partyList = party.getPartyList();
        EntityCharacter hacker = partyList.get(0);
        hacker.setAtk(1000000);
        System.out.println(npc1.getAtk());
        Assert.assertNotEquals(1000000, npc1.getAtk());
    }

    @Test
    public void cloneTest(){
        Party party = new PartyConsole(new Player(0,0,'x'));
        NPC npc1 = new NPC(5,5,'c');
        party.addMember(npc1);
        npc1.setAtk(5);
        ArrayList<EntityCharacter> partyList = party.getPartyList();
        System.out.println(partyList.get(0).getAtk());

        Assert.assertEquals(5, npc1.getAtk());
        System.out.println(partyList.get(0).getAtk());
        Assert.assertEquals(5, npc1.getAtk());
    }

    @Test
    public void add_member(){
        /**
         * adding a member to the party
         */

        Party party = new PartyConsole(new Player(0,0,'x'));
        NPC npc = new NPC(5,5,'c');
        npc.setName("Name");
        npc.setAtk(50);
        npc.setAccuracy(50);
        party.addMember(npc);
        ArrayList<EntityCharacter> partyList = party.getPartyList();
        EntityCharacter member = partyList.get(1);
        assertEquals("npc name ", "Name", member.getName());
        assertEquals("npc attack ", 50, member.getAtk());
        assertEquals("npc accuracy ", 50, (int)member.getAccuracy());

    }

    @Test
    public void npc_should_be_invisible_recruit(){
        Party party = new PartyConsole(new Player(0,0,'x'));
        NPC npc = new NPC(5,5,'c');
        party.lookForRecruitment(npc, "r");
        assertEquals("npc should not be visible ", false, npc.isVisable());
    }


    @Test
    public void npc_should_be_invisible_add(){
        Party party = new PartyConsole(new Player(0,0,'x'));
        NPC npc = new NPC(5,5,'c');
        party.addMember(npc);
        assertEquals("npc should not be visible ", false, npc.isVisable());
    }


    public static void main(String[] args){
        Result result = JUnitCore.runClasses(PartyTest.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.getTrace());
        }

        //System.out.println(result.getRunCount());
    }





}
