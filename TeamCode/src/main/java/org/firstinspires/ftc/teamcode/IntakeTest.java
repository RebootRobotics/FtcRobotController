package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Unit Test - INTAKE")
public class IntakeTest extends LinearOpMode {

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

        // default positions and init
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        double SPEED_MODIFIER = 0.6;

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
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


            if (gamepad1.a) {
                intakeLift1.setPosition(0);
                intakeLift2.setPosition(1);
            }
            if (gamepad1.b) {
                intakeLift1.setPosition(0.6);
                intakeLift2.setPosition(0.4);
            }
            if (gamepad1.x) {
//                intakeLift2.setPosition(1);
            }
            if (gamepad1.y) {
//                intakeLift2.setPosition(0.4);
            }

            if (gamepad1.dpad_left) { // out
                extension1.setPosition(0);
                extension2.setPosition(1);
            }
            if (gamepad1.dpad_right) { // in
                extension1.setPosition(0.35);
                extension2.setPosition(0.65);
            }
            if (gamepad1.dpad_up) {
                intakeStopper.setPosition(0.5);
//                extension2.setPosition(1);
            }
            if (gamepad1.dpad_down) {
                intakeStopper.setPosition(0);
//                extension2.setPosition(0);
            }

            if (gamepad1.left_bumper) { // intake
                activeIntake.setPower(0.5);
                sleep(250);
                activeIntake.setPower(0);
            }
            if (gamepad1.right_bumper) { // release
                activeIntake.setPower(-0.8);
                activeIntake.setPower(0);
            }
        }
    }
}
