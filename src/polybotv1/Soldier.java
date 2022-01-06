package polybotv1;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class Soldier {
    RobotController rc;
    Navigation nav;
    public Soldier(RobotController r){
        rc = r;
        nav = new Navigation(rc);
    }

    public void takeTurn() throws GameActionException {

    }
}
