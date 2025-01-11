// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//import com.revrobotics.CANSparkBase.ControlType;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

//import com.revrobotics.SparkPIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class ClimbSubsystem extends SubsystemBase {
  
  private SparkMax m_ClimbMotor;
  

 

  

 

  /** Creates a new ClimbSubsystem. */
  public ClimbSubsystem() {
    m_ClimbMotor = new SparkMax(7, SparkMax.MotorType.kBrushed);
   

    SparkMaxConfig config = new SparkMaxConfig();

config

    .idleMode(IdleMode.kBrake);
    

    // m_ClimbMotor.setSmartCurrentLimit(30);
    
    
  }

  public void setPower(double power) {

    m_ClimbMotor.set(power);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Climber Current", m_ClimbMotor.getOutputCurrent());
  }

}
