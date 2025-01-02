package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.Robot;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import java.security.cert.TrustAnchor;

@TeleOp(name="Teleop2024")
public class Teleop2024 extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        // configure all motors and servos
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("upperLeft");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("lowerLeft");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("upperRight");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("lowerRight");

        DcMotor activeIntake = hardwareMap.dcMotor.get("activeIntake");
        Servo intakeStopper = hardwareMap.servo.get("stopper");
        Servo intakeLift1 = hardwareMap.servo.get("lift1");
        Servo intakeLift2 = hardwareMap.servo.get("lift2");
        Servo extension1 = hardwareMap.servo.get("extension1");
        Servo extension2 = hardwareMap.servo.get("extension2");

        Servo outtakeClaw = hardwareMap.servo.get("VClaw");
        Servo outtakeLift1 = hardwareMap.servo.get("SlidePivot1");
        Servo outtakeLift2 = hardwareMap.servo.get("SlidePivot2");
        DcMotor vslide1 = hardwareMap.dcMotor.get("VSlide1");
        DcMotor vslide2 = hardwareMap.dcMotor.get("VSlide2");

        waitForStart();

        if (isStopRequested()) return;

        // define all constants
//        boolean FORWARD = true;
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
        double OUTTAKE_LIFT1_DOWN = 0;
        double OUTTAKE_LIFT2_DOWN = 1;

        // default positions and init
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeLift1.setPosition(INTAKE_LIFT1_UP);
        intakeLift2.setPosition(INTAKE_LIFT2_UP);
        extension1.setPosition(EXTENSION1_IN);
        extension2.setPosition(EXTENSION2_IN);

        outtakeClaw.setPosition(OUTTAKE_CLAW_OPENED);
        outtakeLift1.setPosition(OUTTAKE_LIFT1_DOWN);
        outtakeLift2.setPosition(OUTTAKE_LIFT2_DOWN);

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
            // reverse direction
            backRightMotor.setPower(-frontLeftPower*SPEED_MODIFIER);
            backLeftMotor.setPower(-frontRightPower*SPEED_MODIFIER);
            frontRightMotor.setPower(-backLeftPower*SPEED_MODIFIER);
            frontLeftMotor.setPower(-backRightPower*SPEED_MODIFIER);

            // right buttons
            if (gamepad1.a) { // pick up
                intakeLift1.setPosition(INTAKE_LIFT1_DOWN);
                intakeLift2.setPosition(INTAKE_LIFT2_DOWN);
            }
            if (gamepad1.b) { // toggle intake lift
                intakeLift1.setPosition(INTAKE_LIFT1_UP);
                intakeLift2.setPosition(INTAKE_LIFT2_UP);
            }
            if (gamepad1.x) { // transfer
                outtakeLift1.setPosition(OUTTAKE_LIFT1_DOWN);
                outtakeLift2.setPosition(OUTTAKE_LIFT2_DOWN);
                outtakeClaw.setPosition(OUTTAKE_CLAW_OPENED);
                extension1.setPosition(EXTENSION1_IN);
                extension2.setPosition(EXTENSION2_IN);
                intakeLift1.setPosition(INTAKE_LIFT1_UP);
                intakeLift2.setPosition(INTAKE_LIFT2_UP);
                intakeStopper.setPosition(INTAKE_STOPPER_DOWN);
                outtakeClaw.setPosition(OUTTAKE_CLAW_CLOSED);
                sleep(1000);
                outtakeLift1.setPosition(OUTTAKE_LIFT1_UP);
                outtakeLift2.setPosition(OUTTAKE_LIFT2_UP);
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

            // bumper - active intake
            if (gamepad1.left_bumper) { // intake
                intakeStopper.setPosition(INTAKE_STOPPER_UP);
                activeIntake.setPower(INTAKE_POWER);
                sleep(INTAKE_DURATION);
                activeIntake.setPower(0);
            }
            if (gamepad1.right_bumper) { // release
                intakeStopper.setPosition(INTAKE_STOPPER_DOWN);
                activeIntake.setPower(-RELEASE_POWER);
                sleep(RELEASE_DURATION);
                activeIntake.setPower(0);
            }

        }
    }

}
