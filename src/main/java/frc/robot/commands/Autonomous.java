package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drive;

public class Autonomous extends Command {
    private final Drive driveSubsystem;
    private final Timer timer = new Timer();
    private final double duration = 1.0; // tempo de 1 segundo
    private final double speed = 0.5;    // velocidade de 50%

    public Autonomous(Drive driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
        addRequirements(driveSubsystem); // indica que este comando usa o subsystem
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        // inicia os motores para frente
        driveSubsystem.L_Motor.set(ControlMode.PercentOutput, speed);
        driveSubsystem.L_Motor2.set(ControlMode.PercentOutput, speed);
        driveSubsystem.R_Motor.set(ControlMode.PercentOutput, speed);
        driveSubsystem.R_Motor2.set(ControlMode.PercentOutput, speed);
    }

    @Override
public void execute() {
    // Se o tempo ainda não passou, mantemos a velocidade
    if (timer.get() < duration) {
        driveSubsystem.L_Motor.set(ControlMode.PercentOutput, speed);
        driveSubsystem.L_Motor2.set(ControlMode.PercentOutput, speed);
        driveSubsystem.R_Motor.set(ControlMode.PercentOutput, speed);
        driveSubsystem.R_Motor2.set(ControlMode.PercentOutput, speed);
        SmartDashboard.putNumber("Velocidade", speed);
    } else {
        // Após 1 segundo, zera motores e dashboard
        driveSubsystem.L_Motor.set(ControlMode.PercentOutput, 0);
        driveSubsystem.L_Motor2.set(ControlMode.PercentOutput, 0);
        driveSubsystem.R_Motor.set(ControlMode.PercentOutput, 0);
        driveSubsystem.R_Motor2.set(ControlMode.PercentOutput, 0);
        SmartDashboard.putNumber("Velocidade", 0);
    }
}


    @Override
    public void end(boolean interrupted) {
        // para os motores ao terminar
        driveSubsystem.L_Motor.set(ControlMode.PercentOutput, 0);
        driveSubsystem.L_Motor2.set(ControlMode.PercentOutput, 0);
        driveSubsystem.R_Motor.set(ControlMode.PercentOutput, 0);
        driveSubsystem.R_Motor2.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public boolean isFinished() {
        return timer.get() >= duration; // termina após 1 segundo
    }
}
