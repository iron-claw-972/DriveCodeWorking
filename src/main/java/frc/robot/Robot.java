package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import javax.naming.ldap.Control;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;

public class Robot extends TimedRobot {

  Joystick Controller;
  Compressor c = new Compressor(17);
  double motorPowerLeft;
  double powerMultiplier = .6;
  int channelA = 2;
  int channelB = 3;
  boolean buttonA = false;
  DoubleSolenoid doubleSolenoid = new DoubleSolenoid(17, channelA, channelB);
  Solenoid motorCooling = new Solenoid(17,5);
  @Override
  public void robotInit() {
    Controller = new Joystick(0); 
    c.setClosedLoopControl(true);
    

    
  }

  @Override
  public void teleopPeriodic() {
    //leftJoyValue = Controller.getRawAxis(1) * powerMultiplier;
    
    buttonA = Controller.getRawButton(1);
    motorCooling.set(false);
    if (buttonA){
      doubleSolenoid.set(DoubleSolenoid.Value.kForward);
      System.out.println("button");
      //motorCooling.set(true);
    }
    else{
      doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
      //motorCooling.set(true);
    }
  }

}