package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.Drive;
import frc.robot.commands.Autonomous;

public class Robot extends TimedRobot {
    private final SendableChooser<Command> chooser = new SendableChooser<>();
    private Command m_autonomousCommand;
    private Drive driveSubsystem = new Drive();

    @Override
    public void robotInit() {
        Autonomous simpleAuto = new Autonomous(driveSubsystem);
        chooser.setDefaultOption("Simple Auto", simpleAuto);
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        m_autonomousCommand = chooser.getSelected();
        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }
    }

    @Override
    public void teleopInit() {
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }
    }

    @Override
    public void teleopPeriodic() {}
}
