package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;


@TeleOp(name="Teleop202041")
public class Teleop20241 extends LinearOpMode {
//    @Override


    // Reverse the right side motors. This may be wrong for your setup.
    // If your robot moves backwards when commanded to go forwards,
    // reverse the left side instead.
    // See the note about this earlier on this page.

    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("upperLeft");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("lowerLeft");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("upperRight");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("lowerRight");

        Servo intakeWrist = hardwareMap.servo.get("lift1"); // rotate wrist
        Servo intakeLift = hardwareMap.servo.get("lift2"); // rotate arm
        Servo intakeClaw = hardwareMap.servo.get("intakeClaw");
        Servo clawWrist = hardwareMap.servo.get("clawWrist");
        Servo extension1 = hardwareMap.servo.get("extension1");
        Servo extension2 = hardwareMap.servo.get("extension2");
        DcMotor newIntake = hardwareMap.dcMotor.get("newIntake");

        Servo outtakeLift1 = hardwareMap.servo.get("SlidePivot2");
        Servo outtakeLift2 = hardwareMap.servo.get("SlidePivot1");
        Servo outtakeWrist = hardwareMap.servo.get("LiftWrist");
        Servo outtakeClaw = hardwareMap.servo.get("VClaw");
        DcMotor VSlide1 = hardwareMap.dcMotor.get("VSlide1");
        DcMotor VSlide2 = hardwareMap.dcMotor.get("VSlide2");

        // define all constants
        double SPEED_MODIFIER = 0.6;
        double VSLIDE_POWER = 1;
        long VSLIDE_DURATION = 200;

        double INTAKE_CLAW_CLOSED = 0.4;
        double INTAKE_CLAW_OPENED = 0.9;
        double CLAW_WRIST_STRAIGHT = 0;
        double CLAW_WRIST_SIDEWAYS = 0.3;
        double INTAKE_WRIST_DOWN = 0; // lift 1
        double INTAKE_WRIST_UP = 0.6;
        double INTAKE_WRIST_DEFAULT = 0;
        double INTAKE_LIFT_READY = 0.42; // lift 2
        double INTAKE_LIFT_PICKUP = 0.5;
        double INTAKE_LIFT_TRANSFER = 0.13;
        double INTAKE_LIFT_DEFAULT = 0;
        double EXTENSION1_IN = 0.1;
        double EXTENSION1_OUT = 0.7;
        double EXTENSION2_IN = 0.7;
        double EXTENSION2_OUT = 0.1;

        double OUTTAKE_CLAW_CLOSED = 0.3;
        double OUTTAKE_CLAW_OPENED = 0.7;
        double OUTTAKE_WRIST_DEFAULT = 0.1;
        double OUTTAKE_LIFT1_UP = 0.95;
        double OUTTAKE_LIFT2_UP = 0.05;
        double OUTTAKE_LIFT1_DOWN = 0.15;
        double OUTTAKE_LIFT2_DOWN = 0.85;

        // default positions and init
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

//        intakeWrist.setPosition(INTAKE_WRIST_DEFAULT);
//        intakeLift.setPosition(INTAKE_LIFT_DEFAULT);
//        intakeClaw.setPosition(INTAKE_CLAW_OPENED);
        extension1.setPosition(EXTENSION1_IN);
        extension2.setPosition(EXTENSION2_IN);

        outtakeClaw.setPosition(OUTTAKE_CLAW_CLOSED);
//        sleep(500);
//        outtakeLift1.setPosition(OUTTAKE_LIFT1_UP);
//        outtakeLift2.setPosition(OUTTAKE_LIFT2_UP);
        outtakeWrist.setPosition(OUTTAKE_WRIST_DEFAULT);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x; //* 1.1; // Counteract imperfect strafing
            double rx = -gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower*SPEED_MODIFIER);
            backLeftMotor.setPower(backLeftPower*SPEED_MODIFIER);
            frontRightMotor.setPower(frontRightPower*SPEED_MODIFIER);
            backRightMotor.setPower(backRightPower*SPEED_MODIFIER);

            // buttons
            if (gamepad1.a) { // extend out
                intakeWrist.setPosition(INTAKE_WRIST_DOWN);
                intakeLift.setPosition(INTAKE_LIFT_READY);
                intakeClaw.setPosition(INTAKE_CLAW_OPENED);
                extension1.setPosition(EXTENSION1_OUT);
                extension2.setPosition(EXTENSION2_OUT);
            }
            if (gamepad1.b) { // extend in
                intakeWrist.setPosition(INTAKE_WRIST_UP);
                intakeLift.setPosition(INTAKE_LIFT_TRANSFER);
                sleep(100);
//                clawWrist.setPosition(CLAW_WRIST_STRAIGHT);
                outtakeClaw.setPosition(OUTTAKE_CLAW_CLOSED);
                sleep(200);
                outtakeLift1.setPosition(OUTTAKE_LIFT1_DOWN);
                outtakeLift2.setPosition(OUTTAKE_LIFT2_DOWN);
                sleep(500);
//                outtakeClaw.setPosition(OUTTAKE_CLAW_OPENED);
                sleep(1000);
                extension1.setPosition(EXTENSION1_IN);
                extension2.setPosition(EXTENSION2_IN);
                sleep(500);
                outtakeWrist.setPosition(OUTTAKE_WRIST_DEFAULT);
            }
            if (gamepad1.x) { // pickup
                intakeLift.setPosition(INTAKE_LIFT_PICKUP);
                sleep(100);
                intakeClaw.setPosition(INTAKE_CLAW_CLOSED);
            }

            // dpad
            if (gamepad1.dpad_up) {
                // vslide up
                VSlide1.setPower(VSLIDE_POWER);
                VSlide2.setPower(-VSLIDE_POWER);
                sleep(VSLIDE_DURATION);
                VSlide1.setPower(0);
                VSlide2.setPower(0);
            }
            if (gamepad1.dpad_down) {
                VSlide1.setPower(-VSLIDE_POWER);
                VSlide2.setPower(VSLIDE_POWER);
                sleep(VSLIDE_DURATION);
                VSlide1.setPower(0);
                VSlide2.setPower(0);
            }
            if (gamepad1.dpad_left) {
//                clawWrist.setPosition(CLAW_WRIST_STRAIGHT);
                newIntake.setPower(0.5);
                sleep(250);
                newIntake.setPower(0);
            }
            if (gamepad1.dpad_right) {
//                clawWrist.setPosition(CLAW_WRIST_SIDEWAYS);
                newIntake.setPower(-0.5);
                sleep(250);
                newIntake.setPower(0);
            }

            // bumpers
            if (gamepad1.right_bumper) {
                outtakeClaw.setPosition(OUTTAKE_CLAW_OPENED);
                sleep(400);
                outtakeClaw.setPosition(OUTTAKE_CLAW_CLOSED);
                sleep(500);
                intakeClaw.setPosition(INTAKE_CLAW_OPENED);
                outtakeWrist.setPosition(0.3);
            }
            if (gamepad1.left_bumper) {
                sleep(300);
                outtakeLift1.setPosition(0);
                outtakeLift2.setPosition(1);
                intakeClaw.setPosition(INTAKE_CLAW_OPENED);
            }

        }

    }
}
