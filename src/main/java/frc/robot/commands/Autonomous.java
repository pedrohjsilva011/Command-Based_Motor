package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drive;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// === NOVO: sensor IR ===
import edu.wpi.first.wpilibj.DigitalInput;

public class Autonomous extends Command {
    private final Drive drive;
    private final Timer timer = new Timer();
    private double LSpeed = 0;
    private double RSpeed = 0;

    // === NOVO: sensor infravermelho no DIO 0 (ajuste conforme sua ligação) ===
    private final DigitalInput irSensor = new DigitalInput(9);

    // === NOVO: máquina de estados do desvio ===
    private enum IRState { IDLE, REVERSING, TURNING, FORWARD_RECOVER }
    private IRState irState = IRState.IDLE;
    private double irStateStart = 0.0;

    // === parâmetros do desvio ===
    private static final double IR_REVERSE_SEC = 0.5;
    private static final double IR_TURN_SEC = 0.7;
    private static final double IR_FORWARD_SEC = 1.0;
    private static final double FWD_SPEED = 0.25;
    private static final double REV_SPEED = -0.25;
    private static final double TURN_LEFT = 0.25;
    private static final double TURN_RIGHT = -0.25;

    public Autonomous(Drive drive) {
        this.drive = drive;
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        LSpeed = 0.25;
        RSpeed = 0.25;
        setSpeed(LSpeed, RSpeed);

        irState = IRState.IDLE;
        irStateStart = timer.get();
    }

    public void TimerSystem() {
        if (timer.get() > 1) {
            // Após 1s: para o robô de vez
            setSpeed(0, 0);
            LSpeed = 0;
            RSpeed = 0;
        } else {
            // Durante o primeiro segundo: anda pra frente
            setSpeed(LSpeed, RSpeed);
        }
    }

    @Override
    public void execute() {
        boolean obstacle = !irSensor.get();
        double now = timer.get();

        System.out.println("Distância: " + drive.getDistance());

        // === prioridade: rotina de desvio ===
        if (irState != IRState.IDLE) {
            switch (irState) {
                case REVERSING:
                    setSpeed(REV_SPEED, REV_SPEED);
                    if (now - irStateStart > IR_REVERSE_SEC) {
                        irState = IRState.TURNING;
                        irStateStart = now;
                    }
                    break;

                case TURNING:
                    setSpeed(TURN_LEFT, TURN_RIGHT);
                    if (now - irStateStart > IR_TURN_SEC) {
                        irState = IRState.FORWARD_RECOVER;
                        irStateStart = now;
                    }
                    break;

                case FORWARD_RECOVER:
                    setSpeed(FWD_SPEED, FWD_SPEED);
                    if (now - irStateStart > IR_FORWARD_SEC) {
                        irState = IRState.IDLE;
                        stop(); // === volta a ficar PARADO depois do desvio ===
                    }
                    break;
            }
            updateDashboard(obstacle, now);
            return;
        }

        // === se detectou obstáculo, inicia desvio ===
        if (obstacle) {
            irState = IRState.REVERSING;
            irStateStart = now;
            updateDashboard(obstacle, now);
            return;
        }

        // === caso normal ===
        TimerSystem();
        updateDashboard(obstacle, now);
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

    // === Dashboard unificado com todos os parâmetros ===
    private void updateDashboard(boolean obstacle, double now) {
        SmartDashboard.putNumber("Velocidade Esquerda", LSpeed);
        SmartDashboard.putNumber("Velocidade Direita", RSpeed);
        SmartDashboard.putString("Autonomous State", irState.toString());
        SmartDashboard.putBoolean("Obstacle Detected", obstacle);
        SmartDashboard.putNumber("Timer", now);
    }

    @Override
    public boolean isFinished() {
        return false; // comando só termina quando o período de auton acaba
    }

    @Override
    public void end(boolean interrupted) {
        stop();
    }
}