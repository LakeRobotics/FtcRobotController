package org.firstinspires.ftc.teamcode.OpMode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.util.Timer;

@Autonomous
public class Team_testpp_031126 extends OpMode {

    private Follower follower;

    private Timer pathtimer, opModeTimer;


    public enum PathState{
        // START POSITION_END POSITION
        //DRIVE > MOVEMENT STATE
        //SHOOT > ATTEMPT TO SCORE THE ARTIFACT(will change for next year)

        DRIVE_STARTPOS_SHOOT_POS,

        SHOOT_PRELOAD

    }

    PathState pathState;

    private final Pose startPose = new Pose();
    private final Pose shootPose = new Pose();


    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }
}