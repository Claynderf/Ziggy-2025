// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANLauncher;

public class intakeCommand extends Command {
  CANLauncher m_Launcher;

  /** Creates a new intakeCamand. */
  public intakeCommand(CANLauncher launcher) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(launcher);
    m_Launcher = launcher;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_Launcher.setFeedWheel(-.5);
    m_Launcher.setLaunchWheel(-1);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
   m_Launcher.setFeedWheel(0);
   m_Launcher.setLaunchWheel(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
