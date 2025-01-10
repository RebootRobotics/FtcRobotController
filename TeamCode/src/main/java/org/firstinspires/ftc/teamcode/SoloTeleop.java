package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="TeleopSolo")
public class SoloTeleop extends LinearOpMode {

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
        boolean FORWARD = true;
        double SPEED_MODIFIER = 0.6;

        boolean INTAKE_UP = true;
        double INTAKE_POWER = 0.5;
        long INTAKE_DURATION = 250;
        double RELEASE_POWER = 0.8;
        long RELEASE_DURATION = 250;

        double VSLIDE_POWER = 1;
        long VSLIDE_DURATION = 50;

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

        outtakeClaw.setPosition(OUTTAKE_CLAW_OPENED);
        outtakeLift1.setPosition(OUTTAKE_LIFT1_DOWN);
        outtakeLift2.setPosition(OUTTAKE_LIFT2_DOWN);

        while (opModeIsActive() & !isStopRequested()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x; //* 1.1; // Counteract imperfect strafing
            double rx = -gamepad1.right_stick_x;

            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower, backLeftPower, frontRightPower, backRightPower;

            if (FORWARD) {
                frontLeftPower = -((y + x - rx) / denominator);
                backLeftPower = -((y - x - rx) / denominator);
                frontRightPower = -((y - x + rx) / denominator);
                backRightPower = -((y + x + rx) / denominator);
            } else {
                frontLeftPower = (y + x + rx) / denominator;
                backLeftPower = (y - x + rx) / denominator;
                frontRightPower = (y - x - rx) / denominator;
                backRightPower = (y + x - rx) / denominator;
            }

            frontLeftMotor.setPower(frontLeftPower*SPEED_MODIFIER);
            backLeftMotor.setPower(backLeftPower*SPEED_MODIFIER);
            frontRightMotor.setPower(frontRightPower*SPEED_MODIFIER);
            backRightMotor.setPower(backRightPower*SPEED_MODIFIER);

            // right buttons
            if (gamepad1.a) { // pick up
                INTAKE_UP = !INTAKE_UP;
                sleep(100);
                if (INTAKE_UP) {
                    SPEED_MODIFIER = 0.3;
                    intakeLift1.setPosition(INTAKE_LIFT1_DOWN);
                    intakeLift2.setPosition(INTAKE_LIFT2_DOWN);
                } else {
                    SPEED_MODIFIER = 0.6;
                    intakeLift1.setPosition(INTAKE_LIFT1_UP);
                    intakeLift2.setPosition(INTAKE_LIFT2_UP);
                }
            }

            if (gamepad1.b) { // toggle intake lift
                FORWARD = !FORWARD;
            }
            if (gamepad1.x) { // transfer  ,  ps4 control; square
//                FORWARD = false;
                SPEED_MODIFIER = 0.6;
                outtakeLift1.setPosition(OUTTAKE_LIFT1_DOWN);
                outtakeLift2.setPosition(OUTTAKE_LIFT2_DOWN);
                outtakeClaw.setPosition(OUTTAKE_CLAW_OPENED);
                sleep(250);
                extension1.setPosition(EXTENSION1_IN);
                extension2.setPosition(EXTENSION2_IN);
                intakeLift1.setPosition(INTAKE_LIFT1_UP);
                intakeLift2.setPosition(INTAKE_LIFT2_UP);
                sleep(500);
                intakeStopper.setPosition(INTAKE_STOPPER_DOWN);
                sleep(250);
                outtakeClaw.setPosition(OUTTAKE_CLAW_CLOSED);
                sleep(1000);
                outtakeLift1.setPosition(OUTTAKE_LIFT1_UP);
                outtakeLift2.setPosition(OUTTAKE_LIFT2_UP);
            }
            if (gamepad1.y) { // drop or hang
//                FORWARD = true;
                outtakeClaw.setPosition(OUTTAKE_CLAW_OPENED);
                sleep(500);
                outtakeLift1.setPosition(OUTTAKE_LIFT1_DOWN);
                outtakeLift2.setPosition(OUTTAKE_LIFT2_DOWN);
            }

            // dpad - vslide and extension
            if (gamepad1.dpad_up) {
                vslide1.setPower(VSLIDE_POWER);
                vslide2.setPower(-VSLIDE_POWER);
                sleep(VSLIDE_DURATION);
                vslide1.setPower(0);
                vslide2.setPower(0);
            }
            if (gamepad1.dpad_down) {
                vslide1.setPower(-VSLIDE_POWER);
                vslide2.setPower(VSLIDE_POWER);
                sleep(VSLIDE_DURATION);
                vslide1.setPower(0);
                vslide2.setPower(0);
            }
            if (gamepad1.dpad_left) { // extend in
                extension1.setPosition(EXTENSION1_OUT);
                extension2.setPosition(EXTENSION2_OUT);
            }
            if (gamepad1.dpad_right) { // extend out
                extension1.setPosition(EXTENSION1_IN);
                extension2.setPosition(EXTENSION2_IN);
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
