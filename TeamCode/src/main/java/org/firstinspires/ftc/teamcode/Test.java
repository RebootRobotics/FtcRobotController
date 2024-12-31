package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import java.security.cert.TrustAnchor;

@TeleOp(name="Test2024")
public class Test extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        // configure all motors and servos
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("upperLeft");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("lowerLeft");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("upperRight");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("lowerRight");

        DcMotor activeIntake = hardwareMap.dcMotor.get("");
        Servo intakeLift1 = hardwareMap.servo.get("lift2");
        Servo intakeLift2 = hardwareMap.servo.get("");
        Servo intakeStopper = hardwareMap.servo.get("");
        Servo extension1 = hardwareMap.servo.get("extension1");
        Servo extension2 = hardwareMap.servo.get("extension2");

        Servo outtakeClaw = hardwareMap.servo.get("VClaw");
        Servo outtakeWrist = hardwareMap.servo.get("LiftWrist");
        Servo outtakeLift1 = hardwareMap.servo.get("SlidePivot1");
        Servo outtakeLift2 = hardwareMap.servo.get("SlidePivot2");
        DcMotor vslide1 = hardwareMap.dcMotor.get("VSlide1");
        DcMotor vslide2 = hardwareMap.dcMotor.get("VSlide2");

        waitForStart();

        if (isStopRequested()) return;

        // define all constants
        boolean FORWARD = true;
        double SPEED_MODIFIER = 0.6;

        double ACTIVE_INTAKE_POWER = 0.5;
        long ACTIVE_INTAKE_DURATION = 1;
        double VSLIDE_POWER = 1;
        long VSLIDE_DURATION = 200;

        double INTAKE_LIFT1_DEFAULT = 0;
        double INTAKE_LIFT2_DEFAULT = 1;
        double INTAKE_LIFT1_PICKUP = 1;
        double INTAKE_LIFT2_PICKUP = 0;
        double INTAKE_STOPPER_DOWN = 0;
        double INTAKE_STOPPER_UP = 1;
        double EXTENSION1_IN = 0.1;
        double EXTENSION1_OUT = 0.7;
        double EXTENSION2_IN = 0.7;
        double EXTENSION2_OUT = 0.1;

        double OUTTAKE_CLAW_CLOSED = 0.3;
        double OUTTAKE_CLAW_OPENED = 0.7;
        double OUTTAKE_WRIST_DEFAULT = 0.1;
        double OUTTAKE_WRIST_TRANSFER = 0.1;
        double OUTTAKE_WRIST_LOADED = 0.4;
        double OUTTAKE_LIFT1_UP = 0.95;
        double OUTTAKE_LIFT2_UP = 0.05;
        double OUTTAKE_LIFT1_DOWN = 0.15;
        double OUTTAKE_LIFT2_DOWN = 0.85;

        // default positions and init
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        extension1.setPosition(EXTENSION1_IN);
        extension2.setPosition(EXTENSION2_IN);

        outtakeClaw.setPosition(OUTTAKE_CLAW_CLOSED);
//        outtakeLift1.setPosition(OUTTAKE_LIFT1_UP);
//        outtakeLift2.setPosition(OUTTAKE_LIFT2_UP);
        outtakeWrist.setPosition(OUTTAKE_WRIST_DEFAULT);

        while (opModeIsActive() & !isStopRequested()) {
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

            if (FORWARD) {
                frontLeftMotor.setPower(frontLeftPower*SPEED_MODIFIER);
                frontRightMotor.setPower(frontRightPower*SPEED_MODIFIER);
                backLeftMotor.setPower(backLeftPower*SPEED_MODIFIER);
                backRightMotor.setPower(backRightPower*SPEED_MODIFIER);
            } else {
                backRightMotor.setPower(backRightPower*SPEED_MODIFIER);
                backLeftMotor.setPower(backLeftPower*SPEED_MODIFIER);
                frontRightMotor.setPower(frontRightPower*SPEED_MODIFIER);
                frontLeftMotor.setPower(frontLeftPower*SPEED_MODIFIER);
            }

            // right buttons
            if (gamepad1.a) { // intake down
                intakeLift1.setPosition(INTAKE_LIFT1_PICKUP);
                intakeLift2.setPosition(INTAKE_LIFT2_PICKUP);
            }
            if (gamepad1.b) { // intake up
                intakeLift1.setPosition(INTAKE_LIFT1_DEFAULT);
                intakeLift2.setPosition(INTAKE_LIFT2_DEFAULT);
            }
            if (gamepad1.x) { // transfer
                extension1.setPosition(EXTENSION1_IN);
                extension2.setPosition(EXTENSION2_IN);
                intakeLift1.setPosition(INTAKE_LIFT1_DEFAULT);
                intakeLift2.setPosition(INTAKE_LIFT2_DEFAULT);
                intakeStopper.setPosition(INTAKE_STOPPER_DOWN);
                // outtake claw down
                outtakeLift1.setPosition(OUTTAKE_LIFT1_DOWN);
                outtakeLift2.setPosition(OUTTAKE_LIFT2_DOWN);
            }
            if (gamepad1.y) { // drop or hang
                outtakeClaw.setPosition(OUTTAKE_CLAW_OPENED);
                sleep(500);
                outtakeClaw.setPosition(OUTTAKE_CLAW_CLOSED);
            }

            // dpad - vslide and extension
            if (gamepad1.dpad_up) {
                vslide1.setPower(-VSLIDE_POWER);
                vslide2.setPower(VSLIDE_POWER);
                sleep(VSLIDE_DURATION);
                vslide1.setPower(0);
                vslide2.setPower(0);
            }
            if (gamepad1.dpad_down) {
                vslide1.setPower(VSLIDE_POWER);
                vslide2.setPower(-VSLIDE_POWER);
                sleep(VSLIDE_DURATION);
                vslide1.setPower(0);
                vslide2.setPower(0);
            }
            if (gamepad1.dpad_left) { // extend in
                extension1.setPosition(EXTENSION1_IN);
                extension2.setPosition(EXTENSION2_IN);
            }
            if (gamepad1.dpad_right) { // extend out
                extension1.setPosition(EXTENSION1_OUT);
                extension2.setPosition(EXTENSION2_OUT);
            }

            if (gamepad1.left_bumper) { // pick up
                intakeStopper.setPosition(INTAKE_STOPPER_UP);
                activeIntake.setPower(ACTIVE_INTAKE_POWER);
                sleep(ACTIVE_INTAKE_DURATION);
                activeIntake.setPower(0);
            }
            if (gamepad1.right_bumper) { // release
                activeIntake.setPower(-ACTIVE_INTAKE_POWER);
                sleep(ACTIVE_INTAKE_DURATION);
                activeIntake.setPower(0);
            }

            // gamepad2
            if (gamepad2.right_bumper) {
                if (FORWARD) {
                    FORWARD = false;
                } else {
                    FORWARD = true;
                }
            }

        }
    }

}
