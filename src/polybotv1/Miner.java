package polybotv1;

import battlecode.common.GameActionException;
import battlecode.common.RobotController;
import battlecode.common.*;
import java.util.*;

public class Miner {
    RobotController rc;
    Navigation nav;
    Comms comms;
    Util util;
    MapLocation archonCoords;


    //0 for exclusive mining
    //1 for exploration
    //2 for going with the heard
    int minerType = 0;

    Map<MapLocation, Integer> leadCooldowns = new HashMap<MapLocation, Integer>();


    public Miner(RobotController r) throws GameActionException {
        rc = r;
        nav = new Navigation(rc);
        comms = new Comms(rc);
        archonCoords = Util.nullLocation;
        util = new Util(rc);
        startUp();
    }

    void startUp() throws GameActionException {
        for(RobotInfo robot : rc.senseNearbyRobots()){
            if(robot == null) break;
            if(robot.getType() == RobotType.ARCHON){
                archonCoords = robot.getLocation();
                break;
            }
        }

    }


    boolean alreadyDoingSomething = false;

    MapLocation leadFocusLocation = Util.nullLocation;


    public void takeTurn() throws GameActionException {
        if(minerType == 0) {
            miningMiner();
        }
        if(minerType == 1){
            explorerMiner();
        }
    }

    private void explorerMiner() {

    }


    private void miningMiner() throws GameActionException {

        RobotInfo[] robots = rc.senseNearbyRobots();

        for(Direction dir : nav.directions){
            if(rc.getLocation().add(dir) == archonCoords){
                nav.tryMove(dir.opposite());
            }
        }


        for(RobotInfo robot : robots){
            if(robot.getType() == RobotType.ARCHON){
                if(robot.getTeam() != rc.getTeam()){
                    if(util.enemyArchon() == Util.nullLocation){
                        util.setEnemyArchon(robot.getLocation().x, robot.getLocation().y);
                    }
                }
            }
        }

        if(!alreadyDoingSomething) {
            MapLocation [] leads = rc.senseNearbyLocationsWithLead(rc.getType().visionRadiusSquared);
            if(robots.length < leads.length) {
                for (MapLocation location : leads) {
                    if (rc.senseLead(location) > 15) {
                        if (leadCooldowns.containsKey(location)) {
                            if (leadCooldowns.get(location) + 50 <= rc.getRoundNum()) {
                                leadCooldowns.remove(location);
                            }
                        }
                        leadFocusLocation = location;
                        alreadyDoingSomething = true;
                        break;
                    }
                    if (rc.senseLead(location) == 1 && !leadCooldowns.containsKey(location)) {
                        leadCooldowns.put(location, rc.getRoundNum());
                    }
                }
            }
            nav.explore(25, 4, 0, rc.getRoundNum() > 300 ? util.normalizedVectorDirection(rc.getLocation()) : archonCoords.directionTo(rc.getLocation()), 50);
        }
        else{
            nav.clearInit();
            if(leadFocusLocation != Util.nullLocation){
                if(rc.canSenseLocation(leadFocusLocation)) {
                    if (rc.senseLead(leadFocusLocation) <= 1 || rc.getLocation().distanceSquaredTo(archonCoords) > 30*Math.sqrt(rc.getMapHeight()*rc.getMapWidth())) {
                        leadFocusLocation = Util.nullLocation;
                        alreadyDoingSomething = false;
                    }
                }
                if(rc.getLocation().isAdjacentTo(leadFocusLocation)){
                    if(rc.canMineLead(leadFocusLocation)) {
                        rc.mineLead(leadFocusLocation);
                    }
                }
                else{
                    nav.tryMove(rc.getLocation().directionTo(leadFocusLocation));
                }
            }
            else {
                alreadyDoingSomething = false;
            }
        }
    }
}
