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
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;



@Autonomous(name="Autonomous20241")
public class Auton extends LinearOpMode {

    // Declare motors and servos
    private DcMotor frontLeftMotor;
    private DcMotor backLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backRightMotor;
    private Servo lift1;
    private Servo lift2;
    private Servo intakeClaw;
    private Servo extension1;
    private Servo extension2;

    // Movement constants (adjust as needed)
    private static final double DRIVE_POWER = 0.5;
    private static final long STRAIGHT_DURATION_MS = 1000;
   private static final long STRAFE_DURATION_MS = 700;

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();
        // Initialize motors and servos
        frontLeftMotor = hardwareMap.dcMotor.get("upperLeft");
        backLeftMotor = hardwareMap.dcMotor.get("lowerLeft");
        frontRightMotor = hardwareMap.dcMotor.get("upperRight");
        backRightMotor = hardwareMap.dcMotor.get("lowerRight");
        lift1 = hardwareMap.servo.get("lift1");
        lift2 = hardwareMap.servo.get("lift2");
        intakeClaw = hardwareMap.servo.get("intakeClaw");
        extension1 = hardwareMap.servo.get("extension1");
        extension2 = hardwareMap.servo.get("extension2");

        // Reverse right motors if needed
        frontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        backRightMotor.setDirection(DcMotor.Direction.REVERSE);

        // Wait for the game to start


        // Move forward to approach blue pole area
        driveForward(1, 300);

        // Strafe right to align with the blue pole
        //strafeRight(DRIVE_POWER, STRAFE_DURATION_MS);

        // Position lift and hanger to place the pixel on the blue pole
        placePixelOnPole();

        // Move backward to return to the area near the three lines
        driveBackward(1, 150);

        //Strafe right to be parallel with the submersible and parallel with the blue specimen
        strafeRight(.7, 500);

        //Move straight to collect specimen
        driveForward(1, 500);

        //Move right to be in front of the blue speciment to pick it up
        strafeRight(.5, 100);

        //Move backwards to bring the specimen to the observation deck
        driveBackward(1, 750);

        //Turn 180 degrees so that the vertical arm can pick up specimen.
        oneEightyDegreeTurn(1, 500);

        for (int i=0; i<3 i++){
            //Move forward little to latch onto specimen provided by human player.
            driveForward(.5, 50);

            //Turn 180 degrees to push specimen into observation deck
            oneEightyDegreeTurn(1, 500);

            //Strafe left to align with submersible
            strafeLeft(1, 500);

            //Go forward to be in range of the submersible
            driveForward(.5, 200);

            //Move backwards a little
            driveBackward(1, 200);

            //Turn 180 degrees so that the vertical arm can lift specimen from observation deck
            oneEightyDegreeTurn(1, 500);

            //Strafe left to approach the observation deck
            strafeLeft(1, 500);
        }



        // Stop motors
        stopMotors();

        // new auton




    }

    // Function to drive forward
    private void driveForward(double power, long duration) throws InterruptedException {
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(power);
        frontRightMotor.setPower(power);
        backRightMotor.setPower(power);
        sleep(duration);
        stopMotors();
    }

    // Function to drive backward
    private void driveBackward(double power, long duration) throws InterruptedException {
        frontLeftMotor.setPower(-power);
        backLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        backRightMotor.setPower(-power);
        sleep(duration);
        stopMotors();
    }

    // Function to strafe right
    private void strafeRight(double power, long duration) throws InterruptedException {
        frontLeftMotor.setPower(power);
        backLeftMotor.setPower(-power);
        frontRightMotor.setPower(-power);
        backRightMotor.setPower(power);
        sleep(duration);
        stopMotors();
    }

    // Function to place pixel on pole
    private void placePixelOnPole() throws InterruptedException {
        lift1.setPosition(1);   // Adjust position as needed to lift
        lift2.setPo sition(0);   // Adjust position as needed to lift
        extension1.setPosition(0.55);  // Extend to reach the pole
        extension2.setPosition(0.45);
        intakeClaw.setPosition(1); // Open claw to place pixel
        sleep(500);
    }

    // Function to stop all motors
    private void stopMotors() {
        frontLeftMotor.setPower(0);
        backLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backRightMotor.setPower(0);
    }
}
private void oneEightyDegreeTurn(double power, long duration) throws InterruptedException {
    frontLeftMotor.setPower(-power);
    backLeftMotor.setPower(-power);
    frontRightMotor.setPower(power);
    backRightMotor.setPower(power);
    sleep(duration);
    stopMotors();
}
private void strafeLeft(double power, long duration) throws InterruptedException {
    frontLeftMotor.setPower(-power);
    backLeftMotor.setPower(power);
    frontRightMotor.setPower(power);
    backRightMotor.setPower(-power);
    sleep(duration);
    stopMotors();
}