package polybotv1;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class Lab {
    RobotController rc;
    Navigation nav;
    public Lab(RobotController r) {
        rc = r;
        nav = new Navigation(rc);
    }

    public void takeTurn() throws GameActionException {

    }
}
