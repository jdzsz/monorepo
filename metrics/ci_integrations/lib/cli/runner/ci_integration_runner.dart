import 'package:args/command_runner.dart';
import 'package:ci_integration/cli/command/sync_command.dart';
import 'package:ci_integration/cli/logger/logger.dart';

/// A [CommandRunner] for the CI integrations CLI.
class CiIntegrationsRunner extends CommandRunner<void> {
  /// A name of the flag that enables noisy logging.
  static const String _verboseFlagName = 'verbose';

  /// Creates an instance of command runner and registers sub-commands available.
  CiIntegrationsRunner()
      : super('ci_integrations', 'Metrics CI integrations CLI.') {
    argParser.addFlag(
      _verboseFlagName,
      abbr: 'v',
      help: 'Enables noisy logging',
    );

    addCommand(SyncCommand());
  }

  @override
  Future<void> run(Iterable<String> args) {
    final result = argParser.parse(args);
    final verbose = result[_verboseFlagName] as bool;

    Logger.setup(verbose: verbose);

    return super.run(args);
  }
}
