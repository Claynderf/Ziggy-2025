// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

//import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.ClimbSubsystem;

public class setClimbPower extends Command {
  /** Creates a new ClimbExtendCommand. */
private ClimbSubsystem m_climbSubsystem;
private CommandXboxController m_Controller;
  public setClimbPower(ClimbSubsystem climbSubsystem, CommandXboxController controller) {
    m_climbSubsystem = climbSubsystem;
    m_Controller = controller;
    // Use addRequirements() here to declare subsystem dependenc
    addRequirements(m_climbSubsystem);
  }

  public setClimbPower(int i) {
    //TODO Auto-generated constructor stub
}

// Called when the command is initially scheduled.
  @Override
  public void initialize() 
  {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double power = m_Controller.getRightTriggerAxis() - m_Controller.getLeftTriggerAxis();
  
      
    
    m_climbSubsystem.setPower(power/2);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
   m_climbSubsystem.setPower(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
