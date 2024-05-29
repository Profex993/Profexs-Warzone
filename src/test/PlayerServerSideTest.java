package test;

import org.junit.Before;
import org.junit.Test;
import server.CollisionManager;
import server.ServerCore;
import server.ServerUpdateManager;
import server.SpawnLocation;
import server.entity.PlayerServerSide;
import server.enums.ServerMatchState;
import shared.object.objectClasses.MapObject;
import shared.packets.Packet_PlayerInputToServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerServerSideTest {

    private final CollisionManager collisionManager = new CollisionManager(new ArrayList<>());
    private final ArrayList<MapObject> mapObjectList = new ArrayList<>();
    private final ServerCore core = new ServerCore();
    private final ArrayList<SpawnLocation> spawnLocations = new ArrayList<>(Collections.singleton(new SpawnLocation(1, 1)));
    private final Random random = new Random();
    private PlayerServerSide player;

    @Before
    public void setUp() {
        ServerUpdateManager updateManager = new ServerUpdateManager(new ArrayList<>(), new ArrayList<>(), core, new ArrayList<>(), new ArrayList<>(), new Random());
        player = new PlayerServerSide(updateManager, collisionManager, mapObjectList, core, spawnLocations, random);
    }

    @Test
    public void testIsWalking_MatchStateIsMatch_AndNotDeath() {
        // Set up the conditions
        core.setMatchState(ServerMatchState.MATCH);
        player.updateFromPlayerInput(new Packet_PlayerInputToServer(true, true, true, true, 0, 0, 0, 0,
                true, false, false));

        // Act and Assert
        assertTrue(player.isWalking());
    }

    @Test
    public void testIsWalking_NotMatchState() {
        // Set up the conditions
        core.setMatchState(ServerMatchState.MATCH_OVER);
        player.updateFromPlayerInput(new Packet_PlayerInputToServer(true, true, true, true, 0, 0, 0, 0,
                true, false, false));

        // Act and Assert
        assertFalse(player.isWalking());
    }

    @Test
    public void testIsWalking_PlayerIsDead() {
        // Set up the conditions
        core.setMatchState(ServerMatchState.MATCH);
        player.removeHealth(100, player); // Simulate death

        // Act and Assert
        assertFalse(player.isWalking());
    }

    @Test
    public void testIsShooting_MatchStateIsMatch_AndNotDeath() {
        // Set up the conditions
        core.setMatchState(ServerMatchState.MATCH);
        player.updateFromPlayerInput(new Packet_PlayerInputToServer(true, true, true, true, 0, 0, 0, 0,
                true, false, false));

        // Act and Assert
        assertTrue(player.isShooting());
    }

    @Test
    public void testIsShooting_PlayerIsDead() {
        // Set up the conditions
        core.setMatchState(ServerMatchState.MATCH);
        player.removeHealth(100, player); // Simulate death

        // Act and Assert
        assertFalse(player.isShooting());
    }
}
