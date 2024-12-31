package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.function.Function;

public class Robot {

    // wheels
    public DcMotor frontLeftDrive;
    public DcMotor frontRightDrive;
    public DcMotor backLeftDrive;
    public DcMotor backRightDrive;

    // intake
    public Servo intakeClaw;
    public Servo intakeWrist;
    public Servo intakeLift;

    // outtake
    public Servo outtakeClaw;
    public Servo outtakeWrist;
    public Servo outtakeLift1;
    public Servo outtakeLift2;
    public DcMotor vslide1;
    public DcMotor vslide2;

    // extension
    public Servo extension1;
    public Servo extension2;

    // dpad - figure out how to make vslide and extension hold to move
    public void vslideUp() {
//        vslide1.setPosition(1);
//        vslide2.setPosition(0);
    }

    public void vslideDown() {
//        vslide1.setPosition(0);
//        vslide2.setPosition(1);
    }

    public void extensionOut() {
        extension1.setPosition(0);
        extension2.setPosition(1);
    }

    public void extensionIn() {
        extension1.setPosition(1);
        extension2.setPosition(0);
    }

    public void sleep(long duration) {}

    // buttons
    public void pickup() {
        intakeClaw.setPosition(1);
    }

    public void release() {
        intakeClaw.setPosition(0);
    }

    public void transfer() {

    }

    public void drop() { // hang or drop pixel
        outtakeClaw.setPosition(0);
        this.sleep(500);
        outtakeClaw.setPosition(1);
    }

}