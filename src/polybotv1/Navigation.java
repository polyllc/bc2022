package polybotv1;

import battlecode.common.*;

import java.util.Random;

public class Navigation {

    RobotController rc;

    boolean finishedPath = false;
    boolean startedPath = false;
    static final Random rng = new Random(6969);
    Comms comms;

    static public final Direction[] directions = {
            Direction.NORTH,
            Direction.NORTHEAST,
            Direction.EAST,
            Direction.SOUTHEAST,
            Direction.SOUTH,
            Direction.SOUTHWEST,
            Direction.WEST,
            Direction.NORTHWEST,
    }; //for use in foreach(Direction dir in directions)
    // navigation shit here
    public Navigation(RobotController r){
        rc = r;
        comms = new Comms(rc);
    }
    //better nav, try not to move on low movement area

    boolean tryMove(Direction dir) throws GameActionException {
        //if(rc.isMovementReady()) {
            if(rc.canMove(dir) && rc.senseRubble(rc.getLocation().add(dir)) <= 40){
                rc.move(dir);
            }
            else if(rc.canMove(dir.rotateLeft()) && rc.senseRubble(rc.getLocation().add(dir.rotateLeft())) <= 40){
                rc.move(dir.rotateLeft());
            }
            else if(rc.canMove(dir.rotateRight()) && rc.senseRubble(rc.getLocation().add(dir.rotateRight())) <= 40){
                rc.move(dir.rotateRight());
            }
            else if(rc.canMove(dir)){
                rc.move(dir);
            }
            else if(rc.canMove(dir.rotateLeft())){
                rc.move(dir.rotateLeft());
            }
            else if(rc.canMove(dir.rotateRight())){
                rc.move(dir.rotateRight());
            }
            else if(rc.canMove(dir.rotateLeft().rotateLeft())){
                rc.move(dir.rotateLeft().rotateLeft());
            }
            else if(rc.canMove(dir.rotateRight().rotateRight())){
                rc.move(dir.rotateRight().rotateRight());
            }
            else if(rc.canMove(dir.rotateLeft().rotateLeft().rotateLeft())){
                rc.move(dir.rotateLeft().rotateLeft().rotateLeft());
            }
            else if(rc.canMove(dir.rotateRight().rotateRight().rotateRight())){
                rc.move(dir.rotateRight().rotateRight().rotateRight());
            }
            else{
                return false;
            }
            return true;
       // }
       // return false;
    }

    boolean move(MapLocation coords) throws GameActionException {
        if(tryMove(rc.getLocation().directionTo(coords))){
            return true;
        }
        return false;
    }

    boolean moveRandomly() throws GameActionException {
        Direction dir = directions[rng.nextInt(directions.length)];
        if (rc.canMove(dir)) {
            rc.move(dir);
            return true;
        }
        return false;
    }

    boolean moveAwayFromCoord(MapLocation loc) throws GameActionException {
        if(tryMove(loc.directionTo(rc.getLocation()))){
            return true;
        }
        return false;
    }


    Direction dirGoingTowards = Direction.CENTER;
    int zigzag = 0; //steps of zagging
    boolean flipzigzag = false; //false = zig to the right, true = zag to the left
    boolean pauseZigzag = false;
    Direction initDirection = Direction.CENTER;
    int turnsInDir = 0;

    void clearInit(){
        initDirection = Direction.CENTER;
    }


    //todo, fix the fact that they for some reason want to go to the left side of map test small, maybe something to do with the direction array???
    void explore(int spreadOut, int zaglen, int ziglen, Direction initialDirection, int moveAfterTurns) throws GameActionException {
        if(initialDirection != Direction.CENTER && dirGoingTowards == Direction.CENTER){
            dirGoingTowards = initialDirection;
            initDirection = initialDirection;
        }
        //--zigzag
        //spread out that many units as specified
        //*if you hit a wall, then zigzag along it
        //if you find a soldier, move away

        if(!pauseZigzag){
            if(zigzag > zaglen){
                flipzigzag = !flipzigzag;
                zigzag = 0;
            }
            if(flipzigzag == false){
                tryMove(dirGoingTowards.rotateRight());
            }
            else{
                tryMove(dirGoingTowards.rotateLeft());
            }
            turnsInDir++;
            zigzag++;
        }

        if(moveAfterTurns > 0 && turnsInDir >= moveAfterTurns){
            if(Math.floor(Math.random()*2) == 1){
                dirGoingTowards = dirGoingTowards.rotateRight();
            }
            else{
                dirGoingTowards = dirGoingTowards.rotateLeft();
            }
        }

        for(RobotInfo robot : rc.senseNearbyRobots()){
            if(robot.getLocation().distanceSquaredTo(rc.getLocation()) > spreadOut) {
                Direction dirtorobot = rc.getLocation().directionTo(robot.getLocation());
                if (dirtorobot == dirGoingTowards.opposite().rotateRight() || dirtorobot == dirGoingTowards.opposite().rotateRight().rotateRight()) {
                    dirGoingTowards = dirGoingTowards.rotateRight();
                    break;
                }
                if (dirtorobot == dirGoingTowards.opposite().rotateLeft() || dirtorobot == dirGoingTowards.opposite().rotateLeft().rotateLeft()) {
                    dirGoingTowards = dirGoingTowards.rotateLeft();
                    break;
                }
            }
        }
    }


}
