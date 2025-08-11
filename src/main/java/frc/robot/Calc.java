package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class Calc {
    Joystick joystick;

    public static class Speed {
        public double m_left = 0;
        public double m_right = 0;
        public Speed(double m_left, double m_right) {
            this.m_left = m_left;
            this.m_right = m_right;
        }
    }

    public static Speed calculateLeftAnalogic(Joystick joystick, double multiplier) {
        double left_X = joystick.getX();
        double left_Y = joystick.getY();

        double magnitudeL = Math.hypot(left_X, left_Y);
        if (magnitudeL < Constants.deadZone) return new Speed(0, 0);
        
        magnitudeL = Math.min(1, Math.max(-1, magnitudeL));
        double left_sen = left_Y / magnitudeL;

        double leftSpeed = 0;
        double rightSpeed = 0;

        if (left_X > 0 && left_Y < 0) {
        // Quadrante 1
        leftSpeed = magnitudeL * multiplier;
        rightSpeed = ((2 * left_sen + 1) * magnitudeL * -1) * multiplier;
        } else if (left_X < -0 && left_Y < -0) {
        // Quadrante 2
        leftSpeed = ((2 * left_sen + 1) * -1) * magnitudeL * multiplier;
        rightSpeed = magnitudeL * multiplier;
        } else if (left_X < -0 && left_Y > 0) {
        // Quadrante 3
        leftSpeed = magnitudeL * multiplier * -1;
        rightSpeed = ((2 * left_sen - 1) * -1) * magnitudeL * multiplier;
        } else if (left_X > 0 && left_Y > 0) {
        // Quadrante 4
        leftSpeed = ((2 * left_sen - 1) * -1) * magnitudeL * multiplier;
        rightSpeed = magnitudeL * multiplier * -1;
        }
        return new Speed(leftSpeed, rightSpeed);
    }

    public static Speed calculateRightAnalogic(Joystick joystick, double multiplier) {
        double right_X = joystick.getX();
        double right_Y = joystick.getY();

        double magnitudeR = Math.hypot(right_X, right_Y);
        if (magnitudeR < Constants.deadZone) return new Speed(0, 0);
        
        magnitudeR = Math.min(1, Math.max(-1, magnitudeR));
        double right_sen = right_Y / magnitudeR;

        double leftSpeed = 0;
        double rightSpeed = 0;

        if (right_X > 0 && right_Y < 0) {
            // Quadrante 1
            rightSpeed = magnitudeR * multiplier;
            rightSpeed = ((2 * right_sen + 1) * magnitudeR * -1) * multiplier;
        } else if (right_X < -0 && right_Y < -0) {
            // Quadrante 2
            rightSpeed = ((2 * right_sen + 1) * -1) * magnitudeR * multiplier;
            rightSpeed = magnitudeR * multiplier;
        } else if (right_X < -0 && right_Y > 0) {
            // Quadrante 3
            rightSpeed = magnitudeR * multiplier * -1;
            rightSpeed = ((2 * right_sen - 1) * -1) * magnitudeR * multiplier;
        } else if (right_X > 0 && right_Y > 0) {
            // Quadrante 4
            rightSpeed = ((2 * right_sen - 1) * -1) * magnitudeR * multiplier;
            rightSpeed = magnitudeR * multiplier * -1;
        }
        return new Speed(leftSpeed, rightSpeed);
    }

    public static Speed calculateTrigger(Joystick joystick, double multiplier) {
        int L_Trigger = Constants.L_Trigger;
        int R_Trigger = Constants.R_Trigger;

        if (R_Trigger > Constants.deadZone) {
            double speed = R_Trigger * multiplier;
            return new Speed(speed, speed);
        } else if (L_Trigger > Constants.deadZone) {
            double speed = L_Trigger * multiplier;
            return new Speed(speed, speed);
        } else if (R_Trigger < Constants.deadZone && L_Trigger < Constants.deadZone) {
            return new Speed(0, 0);
        }

        return new Speed(0, 0);
    }

    public static Speed calculatePOV(Joystick joystick, double multiplier) {
        switch (joystick.getPOV()) {
            case 0:
                return new Speed(0.5, 0.5);
            case 90:
                return new Speed(0.5, -0.5);
            case 180:
                return new Speed(-0.5, -0.5);
            case 270:
                return new Speed(-0.5, 0.5);
            default:
                return new Speed(0, 0);
        }
    }
}