package org.firstinspires.ftc.teamcode.OpMode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous
public class Team_testpp_031126 extends OpMode {

    private Follower follower;

    private Timer pathTimer, opModeTimer;
    private DcMotorSimple FrontLeftWheel;
    private DcMotorSimple FrontRightWheel;
    private DcMotorSimple BackLeftWheel;
    private DcMotorSimple BackRightWheel;


    public enum PathState{
        // START POSITION_END POSITION
        //DRIVE > MOVEMENT STATE
        //SHOOT > ATTEMPT TO SCORE THE ARTIFACT(will change for next year)

        DRIVE_STARTPOS_FIRST_POS,

        SHOOT_PRELOAD,

        DRIVE_FIRST_POS_SECOND_POS,

        STOP

    }

    PathState pathState;

//this is the starting position of your inital position at start of match
    private final Pose startPose = new Pose(124.11231101511878,122.76457883369329, Math.toRadians(45));//coord position pedropathing visualizer
    //Shooting position is where you will be doing your primary objective
    private final Pose firstPose = new Pose(84.61339092872569,83.8963282937365,Math.toRadians(45));

    private final Pose secondPose = new Pose(71.78833693304537,23.03239740820734, Math.toRadians(270));;
private PathChain driveStartPosFirstPos, driveFirstPosSecondPos;  //path 1
    public void buildPaths(){
        //put in coordinates for starting pose > ending pose
        driveStartPosFirstPos = follower.pathBuilder()
                .addPath(new BezierLine(startPose, firstPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), firstPose.getHeading())
                .build();
        driveFirstPosSecondPos = follower.pathBuilder()
                .addPath(new BezierLine(firstPose, secondPose))
                .setLinearHeadingInterpolation(firstPose.getHeading(), secondPose.getHeading())
                .build();
    }

    public void statePathUpdate(){
        switch(pathState){
            case DRIVE_STARTPOS_FIRST_POS:
                follower.followPath(driveStartPosFirstPos, true );
                setPathState(PathState.SHOOT_PRELOAD);//reset the timer and make new state
                break;
            case SHOOT_PRELOAD:
                //todo add logic to primary objective scoring element limb
                //check is follower done with path?
                if (!follower.isBusy() && pathTimer.getElapsedTimeSeconds() > 5){
                    follower.followPath(driveFirstPosSecondPos, true);
                    setPathState(PathState.DRIVE_FIRST_POS_SECOND_POS);
                    //transition to nect state
                }
                break;
            case DRIVE_FIRST_POS_SECOND_POS:
                //last movement
                if(!follower.isBusy()) {
                    telemetry.addLine("Done all paths");
                }
            case STOP:
                FrontLeftWheel.setPower(0);
                FrontRightWheel.setPower(0);
                BackLeftWheel.setPower(0);
                BackRightWheel.setPower(0);
                break;
            default:
                telemetry.addLine("No State Commanded");
                break;
        }
    }
    public void setPathState(PathState newState){
        pathState = newState;
        pathTimer.resetTimer();
    }
    @Override
    public void init() {
        pathState = PathState.DRIVE_STARTPOS_FIRST_POS;
        pathTimer = new Timer();
        opModeTimer =  new Timer();
        opModeTimer.resetTimer();
        follower = Constants.createFollower(hardwareMap);
        //TODO any other init mechinsums such as camrea and scoring mechanisms maybe sensors

        buildPaths();
        follower.setPose(startPose);
}

public void start(){
        opModeTimer.resetTimer();
        setPathState(pathState);

}
    @Override
    public void loop() {
        follower.update();
        statePathUpdate();

        telemetry.addData("path state", pathState.toString());
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.addData("path time", pathTimer.getElapsedTimeSeconds());

    }
}