package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Unit Test - INTAKE")
public class IntakeTest extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        DcMotor activeIntake = hardwareMap.dcMotor.get("activeIntake");
        Servo intakeStopper = hardwareMap.servo.get("stopper");
        Servo intakeLift1 = hardwareMap.servo.get("lift1");
        Servo intakeLift2 = hardwareMap.servo.get("lift2");
        Servo extension1 = hardwareMap.servo.get("extension1");
        Servo extension2 = hardwareMap.servo.get("extension2");

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
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
                activeIntake.setPower(0.7);
                sleep(250);
                activeIntake.setPower(0);
            }
            if (gamepad1.right_bumper) { // release
                activeIntake.setPower(-0.7);
                activeIntake.setPower(0);
            }
        }
    }
}
