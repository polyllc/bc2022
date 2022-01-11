package polybotv1;

import battlecode.common.*;

public class Util {
    public static MapLocation nullLocation = new MapLocation(420,420);
    RobotController rc;


    public static final int enemyArchonXIndex = 0;
    public static final int enemyArchonYIndex = 1;


    public Util(RobotController r){
        rc = r;
    }

    //gets a normalized vector in terms of direction based on a location, always points to the center of the map
    public Direction normalizedVectorDirection(MapLocation loc){
        MapLocation center = new MapLocation(rc.getMapWidth()/2, rc.getMapHeight()/2);
        return loc.directionTo(center); //oops forgot this existed
    }

    public MapLocation enemyArchon() throws GameActionException {
        int x = rc.readSharedArray(0);
        int y = rc.readSharedArray(1);
        if(x == nullLocation.x && y == nullLocation.y){
            return nullLocation;
        }
        else{
            return new MapLocation(x,y);
        }
    }

    public void clearEnemyArchon() throws GameActionException {
        rc.writeSharedArray(0,nullLocation.x);
        rc.writeSharedArray(1,nullLocation.y);
    }

    public void setEnemyArchon(int x, int y) throws GameActionException {
        rc.writeSharedArray(0,x);
        rc.writeSharedArray(1,y);
    }
}
