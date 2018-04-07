public abstract class BattleScreen  {
    protected Party party;
    protected Enemy enemy;
    protected EntityManager em;

    public BattleScreen(Party party, Enemy enemy, EntityManager em) {
        this.party = party;
        this.enemy = enemy;
        this.em = em;
    }

    public abstract void battle();





}
