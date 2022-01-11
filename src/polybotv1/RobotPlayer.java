package polybotv1;

import battlecode.common.*;
import java.util.Random;

public strictfp class RobotPlayer {

    static RobotController rc;
    static int turnCount = 0;


    /** Array containing all the possible movement directions. */
    static final Direction[] directions = {
        Direction.NORTH,
        Direction.NORTHEAST,
        Direction.EAST,
        Direction.SOUTHEAST,
        Direction.SOUTH,
        Direction.SOUTHWEST,
        Direction.WEST,
        Direction.NORTHWEST,
    };

    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {

        RobotPlayer.rc = rc;

        Archon ar = null;
        Miner mi = null;
        Soldier so = null;
        Lab la = null;
        Watchtower wa = null;
        Builder bu = null;
        Sage sa = null;

        ar = new Archon(rc);
        mi = new Miner(rc);
        so = new Soldier(rc);
        la = new Lab(rc);
        wa = new Watchtower(rc);
        bu = new Builder(rc);
        sa = new Sage(rc);


        while (true) {
            turnCount += 1;  // We have now been alive for one more turn!
            try {
                switch (rc.getType()) {
                    case ARCHON: ar.takeTurn(); break;
                    case MINER: mi.takeTurn(); break;
                    case SOLDIER: so.takeTurn(); break;
                    case LABORATORY: la.takeTurn(); break;
                    case WATCHTOWER: wa.takeTurn(); break;
                    case BUILDER: bu.takeTurn(); break;
                    case SAGE: sa.takeTurn(); break;
                }
            } catch (GameActionException e) {

                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();

            } catch (Exception e) {

                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();

            } finally {
                Clock.yield();
            }
        }
    }
}
