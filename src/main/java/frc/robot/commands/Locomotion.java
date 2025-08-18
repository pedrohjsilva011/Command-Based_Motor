package frc.robot.commands;

import frc.robot.Calc;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.Drive;
import frc.robot.Calc.Speed;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Locomotion extends Command {
    private final Drive driveSubsystem;
    private final Joystick joystick;
    Speed speeds;

    private double multiplier = 0.5;
    private boolean a;
    private boolean b;
    private boolean x;

    public Locomotion(Drive driveSubsystem, Joystick joystick) {
        this.driveSubsystem = driveSubsystem;
        this.joystick = joystick;
        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        driveSubsystem.reqDrive();
    }

    @Override
    public void execute() {
        button();
        MainControl();
        SmartDashboard();
    }

    private void setSpeed(double leftSpeed, double rightSpeed) {
        driveSubsystem.L_Motor.set(ControlMode.PercentOutput, leftSpeed);
        driveSubsystem.L_Motor2.set(ControlMode.PercentOutput, leftSpeed);
        driveSubsystem.R_Motor.set(ControlMode.PercentOutput, rightSpeed);
        driveSubsystem.R_Motor2.set(ControlMode.PercentOutput, rightSpeed);
    }

    private void stop() {
        setSpeed(0, 0);
    }

    public void button() {
        a = joystick.getRawButton(Constants.button_A);
        b = joystick.getRawButton(Constants.button_B);
        x = joystick.getRawButton(Constants.button_X);

        if (a) {
            multiplier = 0.25;
        } else if (b) {
            multiplier = 0.5;
        } else if (x) {
            multiplier = 1.0;
        }
    }

    public void MainControl() {
        double left_X = joystick.getX();
        double left_Y = joystick.getY();
        double right_X = joystick.getRawAxis(Constants.right_X);
        double right_Y = joystick.getRawAxis(Constants.right_Y);
        double RT = joystick.getRawAxis(Constants.R_Trigger);
        double LT = joystick.getRawAxis(Constants.L_Trigger);
        double POV = joystick.getPOV();

        if (RT > Constants.deadZone || LT > Constants.deadZone) {
            speeds = Calc.calculateTrigger(joystick, multiplier);
        } else if (POV != Constants.povDeadZone) {
            speeds = Calc.calculatePOV(joystick, multiplier);
        } else if (left_X >= Constants.deadZone || left_X < -Constants.deadZone || left_Y >= Constants.deadZone || left_Y < -Constants.deadZone) {
            speeds = Calc.calculateLeftAnalogic(joystick, multiplier);
        } else if (right_X >= Constants.deadZone || right_X < -Constants.deadZone || right_Y >= Constants.deadZone || right_Y < -Constants.deadZone) {
            speeds = Calc.calculateRightAnalogic(joystick, multiplier);
        } else {
            speeds = new Speed(0, 0);
            stop();
            return;
        }
        setSpeed(speeds.m_left, speeds.m_right);
    }

    public void SmartDashboard() {
        if (speeds != null) {
            SmartDashboard.putNumber("Velocidade Esquerda", speeds.m_left);
            SmartDashboard.putNumber("Velocidade Direita", speeds.m_right);
        } else {
            SmartDashboard.putNumber("Velocidade Esquerda", 0);
            SmartDashboard.putNumber("Velocidade Direita", 0);
        }
        
        SmartDashboard.putNumber("Multiplicador", multiplier);
    }

    @Override
    public void end(boolean interrupted) {
        stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}