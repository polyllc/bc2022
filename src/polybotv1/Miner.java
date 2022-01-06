package polybotv1;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.*;
import java.util.*;

public class Miner {
    RobotController rc;
    Navigation nav;

    MapLocation archonCoords;


    public Miner(RobotController r) throws GameActionException {
        rc = r;
        nav = new Navigation(rc);
        startUp();
    }

    void startUp() throws GameActionException {
        for(Direction dir : nav.directions){

            RobotInfo robotInfo = rc.senseRobotAtLocation(rc.getLocation().add(dir));
            if(robotInfo == null) break;
            if(robotInfo.getType() == RobotType.ARCHON){
                archonCoords = rc.getLocation().add(dir);
                break;
            }
        }
    }


    boolean alreadyDoingSomething = false;

    MapLocation leadFocusLocation = null;


    public void takeTurn() throws GameActionException {
        System.out.println("wow");
        if(!alreadyDoingSomething) {
            for (MapLocation location : rc.getAllLocationsWithinRadiusSquared(rc.getLocation(), rc.getType().visionRadiusSquared)) {
                if(rc.senseLead(location) > 1 && archonCoords.distanceSquaredTo(rc.getLocation()) < 100){
                    leadFocusLocation = location;
                    alreadyDoingSomething = true;
                    System.out.println("Found lead at " + location);
                    break;
                }
            }
        }
        else{
            if(leadFocusLocation != Util.nullLocation){
                if(rc.getLocation().isAdjacentTo(leadFocusLocation)){
                    if(rc.canMineLead(leadFocusLocation)){
                        rc.mineLead(leadFocusLocation);
                    }
                }
                else{
                    nav.move(leadFocusLocation);
                }
            }
        }
    }
}
