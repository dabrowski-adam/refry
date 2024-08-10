package refry


import cats.effect.{ExitCode, IO, IOApp}
import cats.effect.std.Console
import fs2.io.file.{Files, Path}


object App extends IOApp:

    override def run(args: List[String]): IO[ExitCode] =
        args match
            case Help()      =>
                Console[IO].print(help).as(ExitCode.Success)
            case path :: Nil =>
                for
                    files <- tastyFiles(path)
                    _     <- Console[IO].print(files.mkString("\n"))
                yield ExitCode.Success
            case _           =>
                Console[IO].error(help).as(ExitCode.Error)


    private object Help:
        def unapply(args: List[String]): Boolean =
            args match
                case Nil | ("--help" | "-help" | "help") :: Nil => true
                case _                                          => false


    private[refry] val help: String =
        """|Usage: refry PATH...
           |""".stripMargin


    private[refry] def tastyFiles(path: String): IO[List[Path]] =
        Files[IO]
            .walk(Path(path))
            .filter(_.extName == ".tasty")
            .compile
            .toList
