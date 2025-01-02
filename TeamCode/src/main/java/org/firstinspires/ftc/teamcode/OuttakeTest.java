package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Unit Test - OUTTAKE")
public class OuttakeTest extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        Servo outtakeClaw = hardwareMap.servo.get("VClaw");
        Servo outtakeLift1 = hardwareMap.servo.get("SlidePivot1");
        Servo outtakeLift2 = hardwareMap.servo.get("SlidePivot2");

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            if (gamepad1.a) {
                outtakeLift1.setPosition(0);
            }
            if (gamepad1.b) {
                outtakeLift1.setPosition(1);
            }
            if (gamepad1.x) {
                outtakeLift2.setPosition(1);
            }
            if (gamepad1.y) {
                outtakeLift2.setPosition(0);
            }

            if (gamepad1.dpad_left) {
                outtakeClaw.setPosition(0.6);
            }
            if (gamepad1.dpad_right) {
                outtakeClaw.setPosition(1);
            }
        }
    }
}
