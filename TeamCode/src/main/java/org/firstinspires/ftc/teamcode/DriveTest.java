package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="Unit Test - DRIVE TRAIN")
public class DriveTest extends LinearOpMode {

    public void runOpMode() throws InterruptedException {
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("upperLeft");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("lowerLeft");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("upperRight");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("lowerRight");

        // define all constants
        boolean FORWARD = true;
        double SPEED_MODIFIER = 0.6;

        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
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

            if (gamepad1.a) {
                FORWARD = !FORWARD;
            }

            telemetry.addData("forward", FORWARD);
            telemetry.addData("frontLeftMotor", frontLeftMotor.getPower());
            telemetry.addData("frontRightMotor", frontRightMotor.getPower());
            telemetry.addData("backLeftMotor", backLeftMotor.getPower());
            telemetry.addData("backRightMotor", backRightMotor.getPower());
            telemetry.update();
        }
    }
}
