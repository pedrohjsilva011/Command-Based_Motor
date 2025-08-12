package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import frc.robot.Constants;
import frc.robot.commands.Locomotion;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Drive extends SubsystemBase {
    public final VictorSPX L_Motor = new VictorSPX(Constants.L_Motor);
    public final VictorSPX L_Motor2 = new VictorSPX(Constants.L_Motor2);
    public final VictorSPX R_Motor = new VictorSPX(Constants.R_Motor);
    public final VictorSPX R_Motor2 = new VictorSPX(Constants.R_Motor2);

    public Drive() {
        reqDrive();
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
}