package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import frc.robot.subsystems.Drive;
import frc.robot.commands.Locomotion;
import frc.robot.commands.Autonomous;

public class RobotContainer {
  
  private final Drive drivesubsystem = new Drive();
  private final Autonomous autonomo;
  public final Joystick joystick = new Joystick(Constants.controller);

  public RobotContainer() {
    drivesubsystem.setDefaultCommand(new Locomotion(drivesubsystem, joystick));
    autonomo = new Autonomous(drivesubsystem);
    
  }
}