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
        DcMotor VSlide1 = hardwareMap.dcMotor.get("VSlide1");
        DcMotor VSlide2 = hardwareMap.dcMotor.get("VSlide2");

        Servo intakeLift1 = hardwareMap.servo.get("lift1");
        Servo intakeLift2 = hardwareMap.servo.get("lift2");
        Servo intakeClaw = hardwareMap.servo.get("intakeClaw");

        //Servo clawWrist = hardwareMap.servo.get("clawWrist");
        Servo extension1 = hardwareMap.servo.get("extension1");
        Servo extension2 = hardwareMap.servo.get("extension2");
        Servo outtakeLift1 = hardwareMap.servo.get("SlidePivot2");
        Servo outtakeLift2 = hardwareMap.servo.get("SlidePivot1");
        Servo outtakeWrist = hardwareMap.servo.get("LiftWrist");
        Servo outtakeClaw = hardwareMap.servo.get("VClaw");
//
//
//        // Reverse the right side motors. This may be wrong for your setup.
//        // If your robot moves backwards when commanded to go forwards,
//        // reverse the left side instead.
//        // See the note about this earlier on this page.
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        //VSlide2.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeClaw.setPosition(0.8);
//        clawWrist.setPosition(0.0);

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
            double backLeftPower = (y - x + rx)*0.8 / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx)*0.8 / denominator;


            frontLeftMotor.setPower(frontLeftPower*0.8);
            backLeftMotor.setPower(backLeftPower*0.8);
            frontRightMotor.setPower(frontRightPower*0.8);
            backRightMotor.setPower(backRightPower*0.8);

            if (gamepad1.b) {
                intakeLift1.setPosition(1);
            } else if (gamepad1.a) {
                intakeLift1.setPosition(0.1);
            }
            //init vals: 0.1, 0.6
            if (gamepad1.b) {
                intakeLift2.setPosition(0.15);
            } else if (gamepad1.a) {
                intakeLift2.setPosition(0.42);
            } else if (gamepad1.x) {
                intakeLift2.setPosition(0.5);
                //intakeLift2 is intake claw rn
            }

            /*if (gamepad1.x) {
                lift1.setPosition(0.6);
                lift2.setPosition(0.1);
                sleep(2000);
                VClaw.setPosition(0.1);
                sleep(2000);
                intakeClaw.setPosition(-0.5);
//                transfer();
            } */
            //LIFTWRIST AND VCLAW ARE SWITCH
            if (gamepad1.dpad_left) {
                outtakeClaw.setPosition(0.1);
            } else if (gamepad1.dpad_right) {
                outtakeClaw.setPosition(0.7);
            }

            if (gamepad1.right_bumper) {
                outtakeLift1.setPosition(0);
                outtakeLift2.setPosition(1);
            }
            if (gamepad1.left_bumper) {
                outtakeLift1.setPosition(1);
                outtakeLift2.setPosition(0);
            }

            if (gamepad1.a) {
                intakeClaw.setPosition(0.9);
            } else if (gamepad1.x) {
                sleep(100);
                intakeClaw.setPosition(0.4);
            }

            if (gamepad1.a) {
                extension1.setPosition(0.7);
            } else if (gamepad1.b) {
                extension1.setPosition(0.1);
            }

            if (gamepad1.a) { //extensions out when pressed up
                extension2.setPosition(0.1);
            } else if (gamepad1.b) { //extension in when pressed down
                extension2.setPosition(0.7);
            }

            if (gamepad1.dpad_up) {
                // vslide up
                VSlide1.setPower(0.5);
                VSlide2.setPower(-0.5);
                sleep(500);
                VSlide1.setPower(0);
                VSlide2.setPower(0);
            }
            if (gamepad1.dpad_down) {
                VSlide1.setPower(-0.5);
                VSlide2.setPower(0.5);
                sleep(500);
                VSlide1.setPower(0);
                VSlide2.setPower(0);
            }

            if (gamepad1.left_bumper) {
                outtakeWrist.setPosition(0);
                //lift wrist is outtake wrist
            }
            if (gamepad1.right_bumper) {
                outtakeWrist.setPosition(0.3);
            }

//            float leftTriggerValue = gamepad1.left_trigger;
//            float rightTriggerValue = gamepad1.right_trigger;
//
//            clawWrist.setPosition(leftTriggerValue);
//            clawWrist.setPosition(rightTriggerValue);

            }
            //positive


        }

        }
//    public void transfer(){
//
//        lift1.setPosition(0.6);
//        lift2.setPosition(0.1);
//        sleep(2000);
//        VClaw.setPosition(0.1);
//        sleep(2000);
//        intakeClaw.setPosition(-0.5);
//        }


