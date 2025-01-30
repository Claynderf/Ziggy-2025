// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
//import frc.robot.Constants.LauncherConstants;
import frc.robot.Constants.OperatorConstants;
//import frc.robot.commands.Autos;
import frc.robot.commands.LaunchNote;
import frc.robot.commands.PrepareLaunch;
import frc.robot.commands.intakeCommand;
import frc.robot.commands.setClimbPower;
import frc.robot.subsystems.CANDrivetrain;
import frc.robot.subsystems.CANLauncher;
import frc.robot.subsystems.ClimbSubsystem;



/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems are defined here.
   private final CANDrivetrain m_drivetrain = new CANDrivetrain();
  private final CANLauncher m_launcher = new CANLauncher();
  private final ClimbSubsystem m_ClimbSubsystem = new ClimbSubsystem();

  SendableChooser<Command> autoChooser = new SendableChooser<>();

  /*The gamepad provided in the KOP shows up like an XBox controller if the mode switch is set to X mode using the
   * switch on the top.*/
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be accessed via the
   * named factory methods in the Command* classes in edu.wpi.first.wpilibj2.command.button (shown
   * below) or via the Trigger constructor for arbitary conditions
   */
  private void configureBindings() {
    // Set the default command for the drivetrain to drive using the joysticks
     m_drivetrain.setDefaultCommand(
         new RunCommand(
             () ->
                 m_drivetrain.driveCartesian(
                     -m_driverController.getLeftY(), -m_driverController.getLeftX(),-m_driverController.getRightX() ),
             m_drivetrain));
    
      Command shootCommand = makeShootCommand();
     

      Command auto0 = makeDoNothingCommand();
      Command auto1 = makeShootCommand().withTimeout(2);
      Command auto2 = makeShootCommand().withTimeout(2).andThen(makeDriveBackwardCommand());
      Command auto3 = makeDriveBackwardCommand().withTimeout(2);
      Command auto4 = makeShootCommand().alongWith(makeDriveBackwardCommand()).withTimeout(2).andThen(makehibobCommand());

      autoChooser.setDefaultOption("Just Shoot", auto1);
      autoChooser.addOption("shoot and back up" , auto2);
      autoChooser.addOption("just back up", auto3);
      autoChooser.addOption("do nothing", auto0);
      autoChooser.addOption("drive while shooting then say hi to Bob", auto4);

      SmartDashboard.putData("AUTO" , autoChooser);
    
m_driverController
        .x()
        .whileTrue(shootCommand);
    /*Create an inline sequence to run when the operator presses and holds the A (green) button. Run the PrepareLaunch
     * command for 1 seconds and then run the LaunchNote command */
    // m_driverController
    //     .rightBumper()
    //     .whileTrue(
    //             (new LaunchNote(m_launcher))
    //             .handleInterrupt(() -> m_launcher.stop()));

    // Set up a binding to run the intake command while the operator is pressing and holding the
    // left Bumper
    m_driverController.leftBumper().whileTrue(new intakeCommand(m_launcher));

    m_driverController
    .a()
    .whileTrue(
      new setClimbPower(m_ClimbSubsystem, m_driverController)
    );
    
  }

  Command makeShootCommand() {
    return new PrepareLaunch(m_launcher) 
            .alongWith(new WaitCommand(1))
            .andThen(new LaunchNote(m_launcher))
            .handleInterrupt(() -> m_launcher.stop());
  }

  Command makeDriveBackwardCommand() {
    return new RunCommand(() -> m_drivetrain.driveCartesian(-0.5, 0 , 0), m_drivetrain)
      .withTimeout(1)
      .andThen(new RunCommand(() -> m_drivetrain.driveCartesian(0, 0, 0), m_drivetrain));
  }
  Command makeDoNothingCommand() {
    return new RunCommand(() -> m_drivetrain.driveCartesian(0, 0 , 0), m_drivetrain);
  }

  Command makehibobCommand() {
    return new   PrintCommand( "Hi, Bob" );
 
  }
      


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    //return  Autos.exampleAuto(m_drivetrain);
    return autoChooser.getSelected();
  }
}
