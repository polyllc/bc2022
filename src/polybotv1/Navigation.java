package polybotv1;

import battlecode.common.Direction;
import battlecode.common.GameActionException;
import battlecode.common.MapLocation;
import battlecode.common.RobotController;

public class Navigation {

    RobotController rc;

    boolean finishedPath = false;
    boolean startedPath = false;

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
    }
    //better nav, try not to move on low movement area

    boolean tryMove(Direction dir) throws GameActionException {
        if(rc.isMovementReady()) {
            if(rc.canMove(dir) && rc.senseRubble(rc.getLocation().add(dir)) >= 0.8){
                rc.move(dir);
            }
            else if(rc.canMove(dir.rotateLeft()) && rc.senseRubble(rc.getLocation().add(dir.rotateLeft())) >= 80){
                rc.move(dir.rotateLeft());
            }
            else if(rc.canMove(dir.rotateRight()) && rc.senseRubble(rc.getLocation().add(dir.rotateRight())) >= 80){
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
                System.out.println("oh no");
                return false;
            }
            return true;
        }
        return false;
    }

    boolean move(MapLocation coords) throws GameActionException {
        if(tryMove(rc.getLocation().directionTo(coords))){
            return true;
        }
        return false;
    }

}
