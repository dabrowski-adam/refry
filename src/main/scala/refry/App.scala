package refry


import cats.effect.{ExitCode, IO, IOApp}
import cats.effect.std.Console
import cats.syntax.all.*
import fs2.io.file.{Files, Path}


object App extends IOApp:

    override def run(args: List[String]): IO[ExitCode] =
        args match
            case Help()       => Console[IO].println(help).as(ExitCode.Success)
            case Paths(paths) =>
                for
                    sources <- paths.parFlatTraverse(files)
                    _       <- Console[IO].println(sources.mkString("\n"))
                yield ExitCode.Success
            case _            => Console[IO].errorln(help).as(ExitCode.Error)


    private object Help:
        def unapply(args: List[String]): Boolean =
            args match
                case Nil | ("--help" | "-help" | "help") :: Nil => true
                case _                                          => false


    private object Paths:
        def unapply(args: List[String]): Option[List[String]] =
            val positionalArgs =
                args.filterNot(arg => arg.startsWith("--") || arg.startsWith("-"))

            positionalArgs match
                case Nil   => None
                case paths => Some(paths)


    private[refry] val help: String =
        """|Usage: refry PATH...
           |""".stripMargin


    private[refry] def files(path: String): IO[List[Path]] =
        Files[IO]
            .walk(Path(path))
            .filter(file => file.extName == ".scala" || file.extName == ".tasty")
            .compile
            .toList
