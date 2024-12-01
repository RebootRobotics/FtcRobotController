package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.HardwarePushbot;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Teleop2024")
public class Teleop2024 extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        // configure all motors and servos
        DcMotor frontLeftDrive = hardwareMap.dcMotor.get("upperLeft");
        DcMotor frontRightDrive = hardwareMap.dcMotor.get("upperRight");
        DcMotor backLeftDrive = hardwareMap.dcMotor.get("lowerLeft");
        DcMotor backRightDrive = hardwareMap.dcMotor.get("lowerRight");

        Servo intakeClaw = hardwareMap.servo.get("intakeClaw");
//        Servo intakeWrist = hardwareMap.servo.get("clawWrist");
        Servo intakeLift = hardwareMap.servo.get("lift2"); // lift 1 and 2

        Servo outtakeClaw = hardwareMap.servo.get("VClaw");
        Servo outtakeWrist = hardwareMap.servo.get("LiftWrist");
//        Servo outtakeLift1 = hardwareMap.servo.get("SlidePivot1");
//        Servo outtakeLift2 = hardwareMap.servo.get("SlidePivot2");
        DcMotor vslide1 = hardwareMap.dcMotor.get("VSlide1");
        DcMotor vslide2 = hardwareMap.dcMotor.get("VSlide2");

        Servo extension1 = hardwareMap.servo.get("extension1");
        Servo extension2 = hardwareMap.servo.get("extension2");

        waitForStart();

        if (isStopRequested()) return;

        while(opModeIsActive() & !isStopRequested()) {
            // joystick - drive and turn
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x; //* 1.1; // Counteract imperfect strafing
            double rx = -gamepad1.right_stick_x;

            telemetry.addData("y", y);
            telemetry.addData("x", x);
            telemetry.addData("rx", rx);
            telemetry.addData("intake claw position", intakeClaw.getPosition());
            telemetry.update();

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double speedScale = 0.75;
            double frontLeftPower = ((y + x + rx) / denominator)*speedScale;
            double backLeftPower = ((y - x + rx) / denominator)*speedScale;
            double frontRightPower = ((y - x - rx) / denominator)*speedScale;
            double backRightPower = ((y + x - rx) / denominator)*speedScale;

            frontLeftDrive.setPower(frontLeftPower);
            backLeftDrive.setPower(backLeftPower);
            frontRightDrive.setPower(frontRightPower);
            backRightDrive.setPower(backRightPower);

            // right buttons
            if (gamepad1.a) { // pick up
                intakeClaw.setPosition(1);
            }
            if (gamepad1.b) { // release
                intakeClaw.setPosition(0);
            }
            if (gamepad1.x) { // transfer
                // already picked up
                // turn intake arm around
                // open outtake claw
                // turn outtake claw around
                // grab with outtake claw
                // release with intake claw
                // turn outtake arm around
                // turn intake arm around
            }
            if (gamepad1.y) { // drop or hang
                outtakeClaw.setPosition(1);
                sleep(500);
                outtakeClaw.setPosition(0);
            }

            // dpad - vslide and extension
            if (gamepad1.dpad_up) {
                // vslide up
            }
            if (gamepad1.dpad_down) {
                // vslide down
            }
            if (gamepad1.dpad_left) {
                extension1.setPosition(0);
                extension2.setPosition(1);
            }
            if (gamepad1.dpad_right) {
                extension1.setPosition(1);
                extension2.setPosition(0);
            }
        }
    }

}