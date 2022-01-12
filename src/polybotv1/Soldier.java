package polybotv1;

import battlecode.common.*;

public class Soldier {
    RobotController rc;
    Navigation nav;
    Comms comms;
    Util util;
    MapLocation archonCoords;
    public Soldier(RobotController r) throws GameActionException {
        rc = r;
        nav = new Navigation(rc);
        comms = new Comms(rc);
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

    MapLocation enemyArchon = Util.nullLocation;


    //todo, defend the archon! (if you're near enough that is)
    public void takeTurn() throws GameActionException {
        RobotInfo[] robots = rc.senseNearbyRobots();

        for(RobotInfo robot : robots){
            if(robot.getType() == RobotType.ARCHON){
                if(robot.getTeam() != rc.getTeam()){
                    if(util.enemyArchon() == Util.nullLocation){
                        util.setEnemyArchon(robot.getLocation().x, robot.getLocation().y);
                    }
                }
            }
        }

        if(util.enemyArchon() != Util.nullLocation){
            enemyArchon = util.enemyArchon();
        }

        if(enemyArchon == Util.nullLocation){
            nav.explore(25, 3, 0, archonCoords.directionTo(rc.getLocation()), 50);
        }
        else{
            nav.tryMove(rc.getLocation().directionTo(enemyArchon));
        }

        if(rc.canSenseLocation(enemyArchon)){
            RobotInfo enemy = rc.senseRobotAtLocation(enemyArchon);
            if(enemy != null) {
                if (enemy.getType() != RobotType.ARCHON) {
                    util.clearEnemyArchon();
                    enemyArchon = Util.nullLocation;
                }
            }
        }

        if(enemyArchon.distanceSquaredTo(rc.getLocation()) <= rc.getType().actionRadiusSquared){ //we can shoot the archon
            for(RobotInfo robot : robots){
                if(robot.getTeam() != rc.getTeam()){
                    if(robot.getType() == RobotType.SOLDIER){
                        if(rc.canAttack(robot.getLocation())) {
                            rc.attack(robot.getLocation());
                            break;
                        }
                    }
                }
            }
            if(rc.canAttack(enemyArchon)){
                rc.attack(enemyArchon);
            }
        }
        else{
            for(RobotInfo robot : robots){
                if(robot.getTeam() != rc.getTeam()){
                    if(robot.getType() == RobotType.SOLDIER){
                        if(rc.canAttack(robot.getLocation())) {
                            rc.attack(robot.getLocation());
                            break;
                        }
                    }
                }
            }
            for(RobotInfo robot : robots){
                if(robot.getTeam() != rc.getTeam()){
                    if(robot.getType() == RobotType.SAGE){
                        if(rc.canAttack(robot.getLocation())) {
                            rc.attack(robot.getLocation());
                        }
                    }
                }
            }
            for(RobotInfo robot : robots){
                if(robot.getTeam() != rc.getTeam()){
                    if(robot.getType() == RobotType.WATCHTOWER){
                        if(rc.canAttack(robot.getLocation())) {
                            rc.attack(robot.getLocation());
                        }
                    }
                }
            }
            for(RobotInfo robot : robots){
                if(robot.getTeam() != rc.getTeam()){
                    if(robot.getType() == RobotType.MINER){
                        if(rc.canAttack(robot.getLocation())) {
                            rc.attack(robot.getLocation());
                        }
                    }
                }
            }
        }
    }
}
