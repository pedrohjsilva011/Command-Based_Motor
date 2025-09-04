package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;
import frc.robot.Constants;

public class ArmControl extends Command {
    private final Arm armSubsystem;
    private final Joystick joystick;

    public ArmControl(Arm armSubsystem, Joystick joystick) {
        this.armSubsystem = armSubsystem;
        this.joystick = joystick;
        addRequirements(armSubsystem);
    }

    @Override
    public void initialize() {}

    @Override
    public void execute() {
        double rightTriggerValue = joystick.getRawAxis(Constants.R_Trigger);
        double leftTriggerValue = joystick.getRawAxis(Constants.L_Trigger);

        if (rightTriggerValue > Constants.deadZone) {
            armSubsystem.setArmSpeed(Constants.kArmUpSpeed);
        } else if (leftTriggerValue > Constants.deadZone) {
            armSubsystem.setArmSpeed(Constants.kArmDownSpeed);
        } else {
            armSubsystem.setArmSpeed(0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        armSubsystem.setArmSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}