package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.autonomous.commands.AutoTest;
import org.firstinspires.ftc.teamcode.commands.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.commands.DriveRawJoystickCommand;
import org.firstinspires.ftc.teamcode.commands.ElevatorCommand;
import org.firstinspires.ftc.teamcode.commands.ElevatorRateCommand;
import org.firstinspires.ftc.teamcode.commands.LinearSlideCommand;
import org.firstinspires.ftc.teamcode.commands.ForceIntakeModeCommand;
import org.firstinspires.ftc.teamcode.commands.LinearSlideRateCommand;
import org.firstinspires.ftc.teamcode.commands.ShooterCommand;
import org.firstinspires.ftc.teamcode.commands.WristCommand;
import org.firstinspires.ftc.teamcode.commands.WristRateCommand;
import org.firstinspires.ftc.teamcode.opmodes.ParkAuto;
import org.firstinspires.ftc.teamcode.subsystems.DrivebaseSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ElevatorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem;

import java.util.Optional;

public class RobotContainer {

    public enum AutonomousSelection {
        TEST, PARK
    }

    private GamepadEx brandon, danny;
    private Gamepad gamepad1, gamepad2;

    private final DrivebaseSubsystem drivebaseSubsystem;
    private final ElevatorSubsystem elevatorSubsystem;
    private final IntakeSubsystem intakeSubsystem;
    private final ShooterSubsystem shooterSubsystem;

    public RobotContainer(HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        drivebaseSubsystem  = new DrivebaseSubsystem(hardwareMap);
        elevatorSubsystem = new ElevatorSubsystem(hardwareMap);
        intakeSubsystem = new IntakeSubsystem(hardwareMap);
        shooterSubsystem = new ShooterSubsystem(hardwareMap);

        CommandScheduler.getInstance().registerSubsystem(drivebaseSubsystem);
        CommandScheduler.getInstance().registerSubsystem(elevatorSubsystem);
        CommandScheduler.getInstance().registerSubsystem(intakeSubsystem);
        CommandScheduler.getInstance().registerSubsystem(shooterSubsystem);

        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;
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
                elevatorSubsystem,
                new ElevatorRateCommand(
                        elevatorSubsystem,
                        danny::getLeftY,
                        danny::getRightY));

        configureButtonBindings();
    }

    private void configureButtonBindings() {

//        new ButtonObject(danny, GamepadKeys.Button.Y)
//                .whenActive(new LinearSlideCommand(elevatorSubsystem, Constants.Elevator.Setpoints.MIN_EXTENSION_INCHES));
//
//        new ButtonObject(danny, GamepadKeys.Button.B)
//                .whenActive(new LinearSlideCommand(elevatorSubsystem, Constants.Elevator.Setpoints.MID_HEIGHT_INCHES));
//
//        new ButtonObject(danny, GamepadKeys.Button.A)
//                .whenActive(new LinearSlideCommand(elevatorSubsystem, Constants.Elevator.Setpoints.MIN_EXTENSION_INCHES));

        new ButtonObject(danny, GamepadKeys.Button.Y)
                .whenActive(new ElevatorCommand(elevatorSubsystem, Constants.Elevator.ScoreStates.CLIMB));

        new ButtonObject(danny, GamepadKeys.Button.B)
                .whenActive(new ElevatorCommand(elevatorSubsystem, Constants.Elevator.ScoreStates.SCORE));

        new ButtonObject(danny, GamepadKeys.Button.A)
                .whenActive(new ElevatorCommand(elevatorSubsystem, Constants.Elevator.ScoreStates.STOWED));

        new ButtonObject(danny, GamepadKeys.Button.LEFT_BUMPER)
                .whenActive(new WristCommand(elevatorSubsystem, Constants.Wrist.Setpoints.STOWED));

        new ButtonObject(danny, GamepadKeys.Button.RIGHT_BUMPER)
                .whenActive(new WristCommand(elevatorSubsystem, Constants.Wrist.Setpoints.SCORE));

        new ButtonObject(brandon, GamepadKeys.Button.LEFT_BUMPER)
                .whenActive(new ForceIntakeModeCommand(intakeSubsystem, IntakeSubsystem.RunModes.INTAKE));

        new ButtonObject(brandon, GamepadKeys.Button.RIGHT_BUMPER)
                .whenActive(new ForceIntakeModeCommand(intakeSubsystem, IntakeSubsystem.RunModes.OUTTAKE));

        new Trigger(() -> danny.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) > 0.3)
                .whenActive(new ForceIntakeModeCommand(intakeSubsystem, IntakeSubsystem.RunModes.INTAKE));

        new Trigger(() -> danny.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.3)
                .whenActive(new ForceIntakeModeCommand(intakeSubsystem, IntakeSubsystem.RunModes.OUTTAKE));

        new ButtonObject(danny, GamepadKeys.Button.DPAD_UP)
                .whenActive(new ShooterCommand(shooterSubsystem, ShooterSubsystem.Modes.LAUNCH));
    }

    public Command getAutonomousCommand(AutonomousSelection autonomousSelection) {
        switch(autonomousSelection) {
            case TEST:
                return new AutoTest();
            case PARK:
            default:
                return null;
        }
    }
}
