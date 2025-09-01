package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Arm;
import frc.robot.commands.Locomotion;
import frc.robot.commands.Autonomous;

public class RobotContainer {
  
  private final Drive drivesubsystem = new Drive();
  private final Autonomous autonomo;
  public final Joystick joystick = new Joystick(Constants.controller);
  private final Arm armSubsystem = new Arm();

  public RobotContainer() {
    drivesubsystem.setDefaultCommand(new Locomotion(drivesubsystem, joystick));
    armSubsystem.setDefaultCommand(new ArmControl(armSubsystem, joystick));
    autonomo = new Autonomous(drivesubsystem);
  }

  public Command getAutonomousCommand() {
    return autonomo;
  }
}