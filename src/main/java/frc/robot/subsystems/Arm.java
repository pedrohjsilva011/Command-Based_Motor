package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
    private final SparkMax armMotor;

    
    public Arm() {
        armMotor = new SparkMax(Constants.kArmMotorCanId, MotorType.kBrushless);
    }

    @Override
    public void periodic() {}

    /**
     * @param speed
     */
    public void setArmSpeed(double speed) {
        armMotor.set(speed);
    }
}