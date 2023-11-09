package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.commands.ArmCommand;
import org.firstinspires.ftc.teamcode.commands.ArmRateCommand;
import org.firstinspires.ftc.teamcode.commands.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DrivebaseSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

import java.util.Optional;

public class RobotContainer {
    private GamepadEx brandon, danny;
    private Gamepad gamepad1, gamepad2;

    private final DrivebaseSubsystem drivebaseSubsystem;
    private final ArmSubsystem armSubsystem;
    private final IntakeSubsystem intakeSubsystem;

    public RobotContainer(HardwareMap hardwareMap, Optional<Gamepad> gamepad1, Optional<Gamepad> gamepad2) {
        drivebaseSubsystem  = new DrivebaseSubsystem(hardwareMap);
        armSubsystem = new ArmSubsystem(hardwareMap);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);

        CommandScheduler.getInstance().registerSubsystem(drivebaseSubsystem);
        CommandScheduler.getInstance().registerSubsystem(intakeSubsystem);

        if(gamepad1.isPresent()) {
            this.gamepad1 = gamepad1.get();
            this.gamepad2 = gamepad2.get();
            brandon = new GamepadEx(this.gamepad1);
            danny = new GamepadEx(this.gamepad2);

            CommandScheduler.getInstance().setDefaultCommand(
                    drivebaseSubsystem,
                    new DefaultDriveCommand(
                            drivebaseSubsystem,
                            brandon::getLeftY,
                            brandon::getLeftX,
                            brandon::getRightX));

            CommandScheduler.getInstance().setDefaultCommand(
                    armSubsystem,
                    new ArmRateCommand(
                            armSubsystem,
                            danny::getLeftY,
                            danny::getRightY));

            configureButtonBindings();
        }
    }

    public RobotContainer(HardwareMap hardwareMap) {
        this(hardwareMap, Optional.empty(), Optional.empty());
    }

    private void configureButtonBindings() {

        new ButtonObject(danny, GamepadKeys.Button.Y)
                .whenActive(new ArmCommand(armSubsystem, Constants.Arm.Setpoints.MAXIMUM_ANGLE));

    }
}
