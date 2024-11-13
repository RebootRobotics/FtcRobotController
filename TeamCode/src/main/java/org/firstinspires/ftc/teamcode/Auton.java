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
        driveForward(DRIVE_POWER, STRAIGHT_DURATION_MS);

        // Strafe right to align with the blue pole
        strafeRight(DRIVE_POWER, STRAFE_DURATION_MS);

        // Position lift and hanger to place the pixel on the blue pole
        placePixelOnPole();

        // Move backward to return to the area near the three lines
        driveBackward(DRIVE_POWER, STRAIGHT_DURATION_MS);

        // Stop motors
        stopMotors();
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
        lift2.setPosition(0);   // Adjust position as needed to lift
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
