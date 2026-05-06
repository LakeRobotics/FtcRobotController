package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
                    .mass(6.24)
                    .forwardZeroPowerAcceleration(-29.56)
                    .lateralZeroPowerAcceleration(-50.63)
                    .translationalPIDFCoefficients(new PIDFCoefficients(0.05, 0.0, 0.001, 0.025))
                    .headingPIDFCoefficients(new PIDFCoefficients(0.5, 0.0, 0.002, 0.025))
                    .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.6, 0, 0.001, 0.6, 0.025))
                    .centripetalScaling(0.0005)
                     ;//change values here

    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1.0)
            //change motor name
            .rightFrontMotorName("FrontRightWheel")
            .rightRearMotorName("BackRightWheel")
            .leftRearMotorName("BackLeftWheel")
            .leftFrontMotorName("FrontLeftWheel")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .xVelocity(78.9)
            .yVelocity(83.1);
    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(1.06299)//forward pod offset. must be 0 on x axis, units in inches
            .strafePodX(-0.11811)//sideways pod offset. must be zero on y axis to the right/back of the robot was negative
            .distanceUnit(DistanceUnit.INCH)//these units must match movement units
            .hardwareMapName("Pinpoint")//this must match change name
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)//need to set encoder direction.
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED);
    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pinpointLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .build();
    }
}
