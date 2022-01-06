package polybotv1;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;

public class Builder {
    RobotController rc;
    Navigation nav;
    public  Builder(RobotController r){
        rc = r;
        nav = new Navigation(rc);
    }

    public void takeTurn() throws GameActionException {

    }
}
