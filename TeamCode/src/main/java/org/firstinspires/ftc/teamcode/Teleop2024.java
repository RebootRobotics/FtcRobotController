package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.HardwarePushbot;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Teleop2024")
public class Teleop2024 extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        // configure all motors and servos
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("upperLeft");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("lowerLeft");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("upperRight");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("lowerRight");

        Servo intakeClaw = hardwareMap.servo.get("intakeClaw");
        Servo intakeLift1 = hardwareMap.servo.get("lift1"); // lift
        Servo intakeLift2 = hardwareMap.servo.get("lift2"); // wrist
        Servo extension1 = hardwareMap.servo.get("extension1");

        Servo outtakeClaw = hardwareMap.servo.get("VClaw");
        Servo outtakeWrist = hardwareMap.servo.get("LiftWrist");
        Servo outtakeLift1 = hardwareMap.servo.get("SlidePivot1");
        Servo outtakeLift2 = hardwareMap.servo.get("SlidePivot2");
        DcMotor vslide1 = hardwareMap.dcMotor.get("VSlide1");
        DcMotor vslide2 = hardwareMap.dcMotor.get("VSlide2");

        waitForStart();

        if (isStopRequested()) return;

        // define all constants
        double SPEED_MODIFIER = 0.5;
        double FRONT_SPEED_MODIFIER = 1.1;
        double VSLIDE_POWER = 1;

        double INTAKE_CLAW_CLOSED = 0.4;
        double INTAKE_CLAW_OPENED = 0.9;
//        double INTAKE_LIFT_DOWN = 0.1; // lift 1
//        double INTAKE_LIFT_UP = 1;
//        double INTAKE_WRIST_DOWN = 0.42; // lift 2
//        double INTAKE_WRIST_UP = 0.5;
        double EXTENSION_IN = 0;
        double EXTENSION_OUT = 1;

        double OUTTAKE_CLAW_CLOSED = 0;
        double OUTTAKE_CLAW_OPENED = 1;
        double OUTTAKE_WRIST_DEFAULT = 0.1;
        double OUTTAKE_LIFT1_UP = 0.95;
        double OUTTAKE_LIFT2_UP = 0.05;
        double OUTTAKE_LIFT1_DOWN = 0.15;
        double OUTTAKE_LIFT2_DOWN = 0.85;


        // default positions and init
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeLift1.setPosition(0.1);
        intakeLift2.setPosition(0.42);
        intakeClaw.setPosition(INTAKE_CLAW_OPENED);
        extension1.setPosition(EXTENSION_IN);

        outtakeClaw.setPosition(OUTTAKE_CLAW_CLOSED);
        outtakeLift1.setPosition(OUTTAKE_LIFT1_UP);
        outtakeLift2.setPosition(OUTTAKE_LIFT2_UP);
        outtakeWrist.setPosition(OUTTAKE_WRIST_DEFAULT);

        while(opModeIsActive() & !isStopRequested()) {
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

            frontLeftMotor.setPower(frontLeftPower*SPEED_MODIFIER*FRONT_SPEED_MODIFIER);
            backLeftMotor.setPower(backLeftPower*SPEED_MODIFIER);
            frontRightMotor.setPower(frontRightPower*SPEED_MODIFIER*FRONT_SPEED_MODIFIER);
            backRightMotor.setPower(backRightPower*SPEED_MODIFIER);

            // right buttons
            if (gamepad1.a) { // pick up
//                intakeLift1.setPosition(0.1);
                intakeLift2.setPosition(0.5);
                sleep(100);
                intakeClaw.setPosition(INTAKE_CLAW_CLOSED);
            }
            if (gamepad1.b) { // release
                intakeLift1.setPosition(0.1);
                intakeLift2.setPosition(0.42);
                intakeClaw.setPosition(INTAKE_CLAW_OPENED);
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
                extension1.setPosition(EXTENSION_IN);
                intakeLift1.setPosition(1);
                intakeLift2.setPosition(0.15);
                sleep(1000);
                outtakeLift1.setPosition(0.5);
                outtakeLift2.setPosition(0.5);
                sleep(100);
                outtakeClaw.setPosition(OUTTAKE_CLAW_OPENED);
                sleep(100);
                outtakeLift1.setPosition(OUTTAKE_LIFT1_DOWN);
                outtakeLift2.setPosition(OUTTAKE_LIFT2_DOWN);
                sleep(500);
                outtakeClaw.setPosition(OUTTAKE_CLAW_CLOSED);
                sleep(1000);
                intakeClaw.setPosition(INTAKE_CLAW_OPENED);
                sleep(500);
                outtakeLift1.setPosition(OUTTAKE_LIFT1_UP);
                outtakeLift2.setPosition(OUTTAKE_LIFT2_UP);
                sleep(500);
                intakeLift1.setPosition(0.1);
                intakeLift2.setPosition(0.42);
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
                sleep(500);
                vslide1.setPower(0);
                vslide2.setPower(0);
            }
            if (gamepad1.dpad_down) {
                vslide1.setPower(VSLIDE_POWER);
                vslide2.setPower(-VSLIDE_POWER);
                sleep(500);
                vslide1.setPower(0);
                vslide2.setPower(0);
            }
            if (gamepad1.dpad_left) { // extend in
                extension1.setPosition(EXTENSION_IN);
            }
            if (gamepad1.dpad_right) { // extend out
                intakeLift1.setPosition(0.1);
                intakeLift2.setPosition(0.42);
                extension1.setPosition(EXTENSION_OUT);
            }
        }
    }

}
