package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drive;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;

public class Autonomous extends Command {
    private Drive drive = new Drive();
    private final Timer timer = new Timer();
    
    public Autonomous(Drive drive) {
        this.drive = drive;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        setSpeed(0.5, 0.5);
    }

    public void TimerSystem() {
        timer.start();
        if (timer.get() > 1) {
            timer.stop();
            stop();
        } else {
            setSpeed(0.5, 0.5);
        }
    }

    @Override
    public void execute() {
        TimerSystem();
    }

    public void setSpeed(double leftSpeed, double rightSpeed) {
        drive.L_Motor.set(ControlMode.PercentOutput, leftSpeed);
        drive.L_Motor2.set(ControlMode.PercentOutput, leftSpeed);
        drive.R_Motor.set(ControlMode.PercentOutput, rightSpeed);
        drive.R_Motor2.set(ControlMode.PercentOutput, rightSpeed);
    }

    public void stop() {
        setSpeed(0, 0);
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(1.0); // termina depois de 1 segundo
    }

    @Override
    public void end(boolean interrupted) {
        stop();
    }
}