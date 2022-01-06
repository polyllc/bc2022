package polybotv1;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class Sage {
    RobotController rc;
    Navigation nav;
    public Sage(RobotController r){
        rc = r;
        nav = new Navigation(rc);
    }

    public void takeTurn() throws GameActionException {

    }
}
