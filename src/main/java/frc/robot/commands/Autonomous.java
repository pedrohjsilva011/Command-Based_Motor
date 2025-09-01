package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drive;

import org.w3c.dom.ls.LSParser;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Autonomous extends Command {
    private Drive drive = new Drive();
    private final Timer timer = new Timer();
    private double LSpeed = 0;
    private double RSpeed = 0;
    
    public Autonomous(Drive drive) {
        this.drive = drive;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        LSpeed = 0.5;
        RSpeed = 0.5;
        setSpeed(LSpeed, RSpeed);
    }

    public void TimerSystem() {
        if (timer.get() > 1) {
            timer.stop();
            stop();
        } else {
            setSpeed(LSpeed, RSpeed);
        }
    }

    @Override
    public void execute() {
        TimerSystem();
        SmartDashboard();
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

    public void SmartDashboard() {
        SmartDashboard.putNumber("Velocidade Esquerda", LSpeed);
        SmartDashboard.putNumber("Velocidade Direita", RSpeed);        
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