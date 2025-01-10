//package org.firstinspires.ftc.teamcode;
//
//import android.util.Size;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.firstinspires.ftc.vision.VisionPortal;
//import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
//import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
//
//public class Auton extends LinearOpMode {
//
//    @Override
//    public void runOpMode() {
//
//        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
//                .setDrawAxes(true)
//                .setDrawCubeProjection(true)
//                .setDrawTagID(true)
//                .setDrawTagOutline(true)
//                .build();
//
//        VisionPortal visionPortal = new VisionPortal.Builder()
//                .addProcessor(tagProcessor)
//                .setCamera(hardwareMap.get(WebcamName.class, "Webcam"))
//                .setCameraResolution(new Size(640, 480))
//                .build();
//
//        waitForStart();
//
//        while (!isStopRequested() && opModeIsActive()) {
//
//            if (tagProcessor.getDetections().size() > 0) {
//                AprilTagDetection tag = tagProcessor.getDetections().get(0);
//
//                telemetry.addData("x", tag.ftcPose.x);
//                telemetry.addData("y", tag.ftcPose.y);
//                telemetry.addData("z", tag.ftcPose.z);
//                telemetry.addData("pitch", tag.ftcPose.pitch);
//                telemetry.addData("roll", tag.ftcPose.roll);
//                telemetry.addData("yaw", tag.ftcPose.yaw);
//
//                telemetry.update();
//            }
//        }
//    }
//}

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;



@Autonomous(name="Autonomous20241")
public class Auton extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        // Declare our motors
        // Make sure your ID's match your configuration
        // configure all motors and servos
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("upperLeft");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("lowerLeft");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("upperRight");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("lowerRight");


        DcMotor activeIntake = hardwareMap.dcMotor.get("activeIntake"); //Sucks samples in
        Servo intakeStopper = hardwareMap.servo.get("stopper"); //Stops sample
        Servo intakeLift1 = hardwareMap.servo.get("lift1"); //Rotates intake
        Servo intakeLift2 = hardwareMap.servo.get("lift2"); //Rotates intake

        //Servo intakeWrist = hardwareMap.servo.get("lift1"); // rotate wrist
        //Servo intakeClaw = hardwareMap.servo.get("intakeClaw");
        //Servo clawWrist = hardwareMap.servo.get("clawWrist");
        Servo extension1 = hardwareMap.servo.get("extension1");
        Servo extension2 = hardwareMap.servo.get("extension2");

        Servo outtakeLift1 = hardwareMap.servo.get("SlidePivot2");
        Servo outtakeLift2 = hardwareMap.servo.get("SlidePivot1");
        //Servo outtakeWrist = hardwareMap.servo.get("LiftWrist");
        Servo outtakeClaw = hardwareMap.servo.get("VClaw");
        DcMotor vslide1 = hardwareMap.dcMotor.get("VSlide1");
        DcMotor vslide2 = hardwareMap.dcMotor.get("VSlide2");

        // define all constants
        boolean FORWARD = true;
        double SPEED_MODIFIER = 0.6;

        boolean INTAKE_UP = true;
        double INTAKE_POWER = 0.5;
        long INTAKE_DURATION = 250;
        double RELEASE_POWER = 0.8;
        long RELEASE_DURATION = 250;

        double VSLIDE_POWER = 1;
        long VSLIDE_DURATION = 100;

        double INTAKE_LIFT1_UP = 0;
        double INTAKE_LIFT2_UP = 1;
        double INTAKE_LIFT1_DOWN = 0.6;
        double INTAKE_LIFT2_DOWN = 0.4;
        double INTAKE_STOPPER_UP = 0.5;
        double INTAKE_STOPPER_DOWN = 0;
        double EXTENSION1_IN = 0.35;
        double EXTENSION1_OUT = 0;
        double EXTENSION2_IN = 0.65;
        double EXTENSION2_OUT = 1;

        double OUTTAKE_CLAW_CLOSED = 0.6;
        double OUTTAKE_CLAW_OPENED = 1;
        double OUTTAKE_LIFT1_UP = 1;
        double OUTTAKE_LIFT2_UP = 0;
        double OUTTAKE_LIFT1_DOWN = 0.045;
        double OUTTAKE_LIFT2_DOWN = 0.955;

        // default positions and init
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeLift1.setPosition(INTAKE_LIFT1_UP);
        intakeLift2.setPosition(INTAKE_LIFT2_UP);
        extension1.setPosition(EXTENSION1_IN);
        extension2.setPosition(EXTENSION2_IN);

        outtakeClaw.setPosition(OUTTAKE_CLAW_CLOSED);
        outtakeLift1.setPosition(OUTTAKE_LIFT1_DOWN);
        outtakeLift2.setPosition(OUTTAKE_LIFT2_DOWN);

        waitForStart();

        // insert auton here

        //Preloaded sample is brought out
        outtakeLift1.setPosition(OUTTAKE_LIFT1_UP);
        outtakeLift2.setPosition(OUTTAKE_LIFT2_UP);

        //Robot goes forward
        frontLeftMotor.setPower(0.5);
        frontRightMotor.setPower(0.5);
        backLeftMotor.setPower(0.5);
        backRightMotor.setPower(0.5);
        sleep(1000);

        //Robot Pauses
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        sleep(250);

        //Slides go up
        vslide1.setPower(VSLIDE_POWER);
        vslide2.setPower(-VSLIDE_POWER);
        sleep(VSLIDE_DURATION*1.5);

        //Robot comes right up to the submersible
        frontLeftMotor.setPower(0.5);
        frontRightMotor.setPower(0.5);
        backLeftMotor.setPower(0.5);
        backRightMotor.setPower(0.5);
        sleep(500);

        //Slides lowers preloaded specimen onto the bar
        vslide1.setPower(-VSLIDE_POWER);
        vslide2.setPower(VSLIDE_POWER);
        sleep(VSLIDE_DURATION*0.30);

        //Outtake claw lets go of specimen

        outtakeClaw.setPosition(OUTTAKE_CLAW_OPENED);
        sleep(500);
        outtakeLift1.setPosition(OUTTAKE_LIFT1_DOWN);
        outtakeLift2.setPosition(OUTTAKE_LIFT2_DOWN);

        //Backs up
        frontLeftMotor.setPower(-0.5);
        frontRightMotor.setPower(-0.5);
        backLeftMotor.setPower(-0.5);
        backRightMotor.setPower(-0.5);
        sleep(400);

        //Slides lower
        vslide1.setPower(-VSLIDE_POWER);
        vslide2.setPower(VSLIDE_POWER);
        sleep(VSLIDE_DURATION);

        //Strafes Left
        frontLeftMotor.setPower(-0.5);
        frontRightMotor.setPower(0.5);
        backLeftMotor.setPower(0.5);
        backRightMotor.setPower(-0.5);
        sleep(500);

        /*
        vslide1.setPower(-1);
        vslide2.setPower(1);
        sleep(1000);
        vslide1.setPower(0);
        vslide2.setPower(0);
        sleep(100);
        outtakeClaw.setPosition(0.1);
        sleep(200);
        outtakeClaw.setPosition(0.9);
        sleep(200);
        vslide1.setPower(-1);
        vslide2.setPower(1);
        sleep(1000);
        vslide1.setPower(0);
        vslide2.setPower(0);

        frontLeftMotor.setPower(0.5);
        frontRightMotor.setPower(-0.5);
        backLeftMotor.setPower(-0.5);
        backRightMotor.setPower(0.5);
        sleep(750);
        frontLeftMotor.setPower(0.5);
        frontRightMotor.setPower(0.5);
        backLeftMotor.setPower(0.5);
        backRightMotor.setPower(0.5);
        sleep(1000);
        frontLeftMotor.setPower(0.5);
        frontRightMotor.setPower(-0.5);
        backLeftMotor.setPower(-0.5);
        backRightMotor.setPower(0.5);
        sleep(300);
        */

    }
}
