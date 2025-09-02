package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Arm;
import frc.robot.Constants;

public class ArmControl extends Command {
    private final Arm armSubsystem;
    private final Joystick joystick;

    public ArmControl(Arm armSubsystem, Joystick joystick) {
        this.armSubsystem = armSubsystem;
        this.joystick = joystick;
        // Requisita o subsistema para que nenhum outro comando possa usá-lo ao mesmo tempo
        addRequirements(armSubsystem);
    }

    @Override
    public void initialize() {
        // Este método é chamado uma vez quando o comando é iniciado
    }

    @Override
    public void execute() {
        // Pega o valor dos triggers
        double rightTriggerValue = joystick.getRawAxis(Constants.R_Trigger);
        double leftTriggerValue = joystick.getRawAxis(Constants.L_Trigger);

        if (rightTriggerValue > Constants.deadZone) {
            // Se o trigger direito estiver pressionado, sobe o braço
            armSubsystem.setArmSpeed(Constants.kArmUpSpeed);
        } else if (leftTriggerValue > Constants.deadZone) {
            // Se o trigger esquerdo estiver pressionado, desce o braço
            armSubsystem.setArmSpeed(Constants.kArmDownSpeed);
        } else {
            // Se nenhum dos triggers estiver pressionado, para o braço
            armSubsystem.setArmSpeed(0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        // Para o braço quando o comando termina
        armSubsystem.setArmSpeed(0);
    }

    @Override
    public boolean isFinished() {
        return false; // Este comando nunca termina (até que outro comando o interrompa)
    }
}