package org.firstinspires.ftc.teamcode;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@Autonomous(name="CameraInit")
public class CameraInit extends LinearOpMode {

    @Override
    public void runOpMode() {

        waitForStart();

//        AprilTagProcessor tagProcessor = new AprilTagProcessor.Builder()
//                .setDrawAxes(true)
//                .setDrawCubeProjection(true)
//                .setDrawTagID(true)
//               .setDrawTagOutline(true)
//                .build();
//
//        VisionPortal visionPortal = new VisionPortal.Builder()
//                .addProcessor(tagProcessor)
//                .setCamera(hardwareMap.get(WebcamName.class, "Webcam"))
//                .setCameraResolution(new Size(640, 480))
//                .build();
//
//        boolean webcam_attached = hardwareMap.get(WebcamName.class, "Webcam").isAttached();
//        if (webcam_attached) {
//            telemetry.addData("webcam status", "connected");
//        } else {
//            telemetry.addData("webcam status", "not connected");
//        }
//        telemetry.update();
//
//        waitForStart();
//
//        while (!isStopRequested() && opModeIsActive()) {
//
//            if (!tagProcessor.getDetections().isEmpty()) {
//                AprilTagDetection tag = tagProcessor.getDetections().get(0);
//
//                telemetry.addData("x", tag.ftcPose.x);
//                telemetry.addData("y", tag.ftcPose.y);
//                telemetry.addData("z", tag.ftcPose.z);
//                telemetry.addData("pitch", tag.ftcPose.pitch);
//                telemetry.addData("roll", tag.ftcPose.roll);
//                telemetry.addData("yaw", tag.ftcPose.yaw);
//
//                telemetry.update();
//            }
//        }
    }
}