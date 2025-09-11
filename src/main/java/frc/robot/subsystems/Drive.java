package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {
    public final VictorSPX L_Motor = new VictorSPX(Constants.L_Motor);
    public final VictorSPX L_Motor2 = new VictorSPX(Constants.L_Motor2);
    public final VictorSPX R_Motor = new VictorSPX(Constants.R_Motor);
    public final VictorSPX R_Motor2 = new VictorSPX(Constants.R_Motor2);

    // ======================
    // Encoder (apenas 1 lado)
    // ======================
    private final Encoder driveEncoder = new Encoder(0, 1);

    private static final int countsPerRevolution = 2048;
    private static final double wheelDiameter = 0.06;
    private static final double distancePerPulse = (Math.PI * wheelDiameter) / countsPerRevolution;

    public Drive() {
        reqDrive();

        // Configura encoder
        driveEncoder.setDistancePerPulse(distancePerPulse);
        driveEncoder.setReverseDirection(false); // troca pra true se contar ao contrário
    }

    @Override
    public void periodic() {

    }

    public void reqDrive() {
        L_Motor.setInverted(false);
        L_Motor2.setInverted(false);
        R_Motor.setInverted(true);
        R_Motor2.setInverted(true);

        L_Motor.configNeutralDeadband(Constants.deadZone);
        L_Motor2.configNeutralDeadband(Constants.deadZone);
        R_Motor.configNeutralDeadband(Constants.deadZone);
        R_Motor2.configNeutralDeadband(Constants.deadZone);

        L_Motor.setNeutralMode(NeutralMode.Brake);
        L_Motor2.setNeutralMode(NeutralMode.Brake);
        R_Motor.setNeutralMode(NeutralMode.Brake);
        R_Motor2.setNeutralMode(NeutralMode.Brake);
    }

    // ======================
    // Métodos do encoder
    // ======================
    public double getDistance() {
        return driveEncoder.getDistance(); // metros percorridos
    }

    public double getRate() {
        return driveEncoder.getRate(); // metros/segundo
    }

    public void resetEncoder() {
        driveEncoder.reset();
    }
}