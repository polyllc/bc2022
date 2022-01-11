package polybotv1;

import battlecode.common.RobotController;

public class Comms {

    RobotController rc;

    public Comms(RobotController r){
        rc = r;
    }

    public void msg(String msg){
        System.out.println("[" + rc.getType().name() + "] [id:" + rc.getID() + "]" + " " + msg);
    }
}
