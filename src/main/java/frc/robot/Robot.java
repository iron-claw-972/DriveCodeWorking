package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
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

  TalonFX TalonFXMotorLeft;
  TalonFX TalonFXMotorRight;
  TalonFX TalonFXMotorLeftSlave;
  TalonFX TalonFXMotorRightSlave;
  TalonFX Shooter1;
  TalonFX Shooter2;
  TalonSRX intake;

  Joystick Controller;
  double motorPowerLeft;
  double motorPowerRight;
  double powerMultiplier = .6;
  double leftJoyValue;
  double rightJoyValue;
  boolean outtakeHelperA;
  boolean intakeB = false;
  CANSparkMax nmfMotor;
  CANSparkMax outtakeHelper;
  

  @Override
  public void robotInit() {
    Controller = new Joystick(0); 
    TalonFXMotorLeft = new TalonFX(1);
    TalonFXMotorRight = new TalonFX(14);

    TalonFXMotorLeftSlave = new TalonFX(18);
    TalonFXMotorRightSlave = new TalonFX(15);

    intake = new TalonSRX(8);

    Shooter1 = new TalonFX(12);
    Shooter2 = new TalonFX(13);

    nmfMotor = new CANSparkMax(4, MotorType.kBrushless);
    outtakeHelper = new CANSparkMax(5, MotorType.kBrushless);

    
  }

  @Override
  public void teleopPeriodic() {
    leftJoyValue = Controller.getRawAxis(1) * powerMultiplier; //get the -1 to 1 values from the left joystick and half it so the motor runs at a maximum of half speed

    rightJoyValue = -Controller.getRawAxis(4) * powerMultiplier; //get the -1 to 1 values from the right joystick and half it
    
    motorPowerLeft = -rightJoyValue * powerMultiplier + leftJoyValue * powerMultiplier*1.5;

    motorPowerRight =  -rightJoyValue * powerMultiplier - leftJoyValue * powerMultiplier*1.5;
    
    outtakeHelperA = Controller.getRawButton(1);
    if (Controller.getRawButtonPressed(2)){
      intakeB = !intakeB;
    }
    TalonFXMotorLeft.set(ControlMode.PercentOutput, motorPowerLeft); //moves according to left joystick
    TalonFXMotorRight.set(ControlMode.PercentOutput, motorPowerRight); //moves according to right joystick

    TalonFXMotorLeftSlave.follow(TalonFXMotorLeft); //rest of the motors follow along
    TalonFXMotorRightSlave.follow(TalonFXMotorRight); //follow method works for victors, talons, and the talonFX. SparkMaxs can follow other SparkMaxs
    
    if (outtakeHelperA){
      nmfMotor.set(.2);
      outtakeHelper.set(.6);
      Shooter1.set(ControlMode.PercentOutput,.6);
      Shooter2.set(ControlMode.PercentOutput,-.6);
    }
    else{
      nmfMotor.set(.1);
      outtakeHelper.set(0);
      Shooter1.set(ControlMode.PercentOutput,.1);
      Shooter2.set(ControlMode.PercentOutput,-.1);
    }

    if (intakeB){
      intake.set(ControlMode.PercentOutput,-.9);
    }
    else{
      intake.set(ControlMode.PercentOutput,0);
    }
    

  }

}