package polybotv1;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.RobotType;

public class Archon {
    RobotController rc;
    Navigation nav;
    public Archon(RobotController r){
        rc = r;
        nav = new Navigation(rc);
    }

    public void takeTurn() throws GameActionException {
        for(Direction dir : nav.directions) {
            if (rc.canBuildRobot(RobotType.MINER, dir)) {
                rc.buildRobot(RobotType.MINER, dir);
                break;
            }
        }
    }
}
