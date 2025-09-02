package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
    private final SparkMax armMotor;

    
    public Arm() {
        // Inicializa o motor SparkMAX com o ID 5
        armMotor = new SparkMax(Constants.kArmMotorCanId, MotorType.kBrushless);
    }

    @Override
    public void periodic() {
        // Este método é chamado uma vez por ciclo do Scheduler
    }

    /**
     * Define a velocidade do motor do braço.
     * @param speed A velocidade a ser aplicada, de -1.0 a 1.0.
     */
    public void setArmSpeed(double speed) {
        armMotor.set(speed);
    }
}