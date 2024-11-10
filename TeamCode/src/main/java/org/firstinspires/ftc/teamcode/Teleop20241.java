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


@TeleOp(name="Teleop20241")
public class Teleop20241 extends LinearOpMode {
//    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("upperLeft");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("lowerLeft");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("upperRight");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("lowerRight");
        Servo lift1 = hardwareMap.servo.get("lift1");
        Servo lift2 = hardwareMap.servo.get("lift2");
        Servo intakeClaw = hardwareMap.servo.get("intakeClaw");
        Servo clawWrist = hardwareMap.servo.get("clawWrist");
        Servo extension1 = hardwareMap.servo.get("extension1");
        Servo extension2 = hardwareMap.servo.get("extension2");

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            if (gamepad1.a) {
                lift1.setPosition(-1);
            }
            else if (gamepad1.b) {
                lift1.setPosition(0);
            }
            if (gamepad1.a) {
                lift2.setPosition(0);
            }
            else if (gamepad1.b) {
                lift2.setPosition(-1);
            }

            if (gamepad1.left_bumper) {
                intakeClaw.setPosition(1);
            }
            else if (gamepad1.right_bumper) {
                intakeClaw.setPosition(0.1);
            }

            if (gamepad1.dpad_up) {
                clawWrist.getPosition();
                clawWrist.setPosition(clawWrist.getPosition()+0.1);
            }
            else if (gamepad1.dpad_down) {
                clawWrist.getPosition();
                clawWrist.setPosition(clawWrist.getPosition()-0.1);
            }

            if (gamepad1.dpad_left) {
                extension1.setPosition(0.55);
            }
            else if (gamepad1.dpad_right) {
                extension1.setPosition(0.35);
            }

            if (gamepad1.dpad_left) {
                extension2.setPosition(0.35);
            }
            else if (gamepad1.dpad_right) {
                extension2.setPosition(0.55);
            }



        }
    }
}