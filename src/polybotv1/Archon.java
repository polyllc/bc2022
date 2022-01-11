package polybotv1;

import battlecode.common.*;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Archon {
    RobotController rc;
    Navigation nav;
    Util util;
    Comms comms;
    int maxMiner;
    public Archon(RobotController r){
        rc = r;
        nav = new Navigation(rc);
        util = new Util(rc);
        comms = new Comms(rc);
        maxMiner = (int) (Math.sqrt(rc.getMapHeight()*rc.getMapWidth()));
    }

    int numMiners = 0;

    int flipFlopUnits = 0; //flip flop units

    Direction[] archonDirs = new Direction[8];
    //use different strategies depending on how big or small the map, like big map == more soliders to crowd and destroy, while small map == watchtowers and defence


    //TODO, defend the archon!
    public void takeTurn() throws GameActionException {

        if(rc.getRoundNum() == 1){
            util.setEnemyArchon(Util.nullLocation.x, Util.nullLocation.y);
        }

        Direction startDir = util.normalizedVectorDirection(rc.getLocation());

        int index = Arrays.asList(nav.directions).indexOf(startDir);
        for(int i = 0; i < 8; i++){
            archonDirs[i] = nav.directions[(i+index) % 8];
        }

        for(RobotInfo robot : rc.senseNearbyRobots(20)){
            if(robot.getTeam() == rc.getTeam()){
             //   comms.msg("not my teammate");
                if(rc.canRepair(robot.getLocation())){
                   // rc.repair(robot.getLocation());
                }
            }
        }

        rc.setIndicatorString("enemy archon: " + util.enemyArchon());


        if(rc.getRoundNum() < 50){
                if(buildMiner()){
                   //yay!
                }
        }
        else if(rc.getRoundNum() > 40){
            if (flipFlopUnits % 2 == 1) {
                if(buildSoldier()) {
                    flipFlopUnits++;
                }
            }
            else{
                if(buildMiner()) {
                    flipFlopUnits++;
                }
            }
        }
        else{
            buildSoldier();
        }
    }

    private boolean buildSoldier() throws GameActionException {
        for(Direction dir : archonDirs) {
            if (rc.canBuildRobot(RobotType.SOLDIER, dir)) {
                rc.buildRobot(RobotType.SOLDIER, dir);
                return true;
            }
        }
        return false;
    }

    private boolean buildMiner() throws GameActionException {
        for(Direction dir : archonDirs) {
            if (rc.canBuildRobot(RobotType.MINER, dir)) {
                rc.buildRobot(RobotType.MINER, dir);
                numMiners++;
                return true;
            }
        }
        return false;
    }
}
