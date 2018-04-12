package entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EntityManagerTest {
    @Test
    public void move_player_inbounds(){
        /**
         * player in this case should be moved in to placed that would end up changing it's position
         */
        char[][] map = new char[][]{
                {'.','.','.'},
                {'.','.','.'},
                {'.','.','.'},
                {'.','.','.'},
        };
        EntityManager em = new EntityManager();
        em.createPlayer(0,0);

        em.movePlayer(1,0,map);
        assertEquals(1,em.getPlayer().getX());
        assertEquals(0,em.getPlayer().getY());

        em.movePlayer(-1,0,map);
        assertEquals(0,em.getPlayer().getX());
        assertEquals(0,em.getPlayer().getY());

        em.movePlayer(2,0,map);
        assertEquals(2,em.getPlayer().getX());
        assertEquals(0,em.getPlayer().getY());

        em.movePlayer(-2,0,map);
        assertEquals(0,em.getPlayer().getX());
        assertEquals(0,em.getPlayer().getY());


        em.movePlayer(0,1,map);
        assertEquals(0,em.getPlayer().getX());
        assertEquals(1,em.getPlayer().getY());

        em.movePlayer(0,-1,map);
        assertEquals(0,em.getPlayer().getX());
        assertEquals(0,em.getPlayer().getY());

        em.movePlayer(0,2,map);
        assertEquals(0,em.getPlayer().getX());
        assertEquals(2,em.getPlayer().getY());

        em.movePlayer(0,-2,map);
        assertEquals(0,em.getPlayer().getX());
        assertEquals(0,em.getPlayer().getY());
    }

    @Test
    public void move_player_outOfBounds(){
        /**
         * player will be moved into places that are invalid, causing the player to remain in the same location
         */
        char[][] map = new char[][]{
                {'.','.','.'},
                {'.','.','.'},
                {'.','.','.'},
                {'.','.','.'},
        };
        EntityManager em = new EntityManager();
        em.createPlayer(0,0);

        em.movePlayer(-1,0,map);
        assertEquals(0,em.getPlayer().getX());
        assertEquals(0,em.getPlayer().getY());

        em.movePlayer(3,0,map);
        assertEquals(0,em.getPlayer().getX());
        assertEquals(0,em.getPlayer().getY());

        em.movePlayer(0,4,map);
        assertEquals(0,em.getPlayer().getX());
        assertEquals(0,em.getPlayer().getY());

        em.movePlayer(0,-1,map);
        assertEquals(0,em.getPlayer().getX());
        assertEquals(0,em.getPlayer().getY());
    }


    @Test
    public void move_player_obstacles(){
        /**
         * player is surrounded by obstacles at the point (1,1). player should not be able to change it's position
         */
        char[][] map = new char[][]{
                {'#','#','#'},
                {'#','.','#'},
                {'#','#','#'},
                {'.','.','.'},
        };
        EntityManager em = new EntityManager();
        em.createPlayer(1,1);

        em.movePlayer(-1,0,map);
        assertEquals(1,em.getPlayer().getX());
        assertEquals(1,em.getPlayer().getY());

        em.movePlayer(1,0,map);
        assertEquals(1,em.getPlayer().getX());
        assertEquals(1,em.getPlayer().getY());

        em.movePlayer(0,1,map);
        assertEquals(1,em.getPlayer().getX());
        assertEquals(1,em.getPlayer().getY());

        em.movePlayer(0,-1,map);
        assertEquals(1,em.getPlayer().getX());
        assertEquals(1,em.getPlayer().getY());
    }

    @Test
    public void entity_distance(){
        EntityManager em = new EntityManager();
        Player player = em.createPlayer(1,1);
        Enemy enemy = em.createEnemy(1,2,'e');
        double distance = em.getDistanceBetweenEntities(player, enemy);
        assertEquals(1.0, distance, 0.0000001);

        Enemy enemy2 = em.createEnemy(1,3,'e');
        distance = em.getDistanceBetweenEntities(player, enemy2);
        assertEquals(2.0, distance, 0.0000001);
    }
}
