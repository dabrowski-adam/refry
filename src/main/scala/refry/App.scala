package refry


import cats.effect.{ExitCode, IO, IOApp}
import cats.effect.std.Console


object App extends IOApp:

    override def run(args: List[String]): IO[ExitCode] =
        args match
            case "--help" :: Nil =>
                Console[IO].print(help).as(ExitCode.Success)
            case name :: Nil     =>
                Console[IO].print(greet(name)).as(ExitCode.Success)
            case _               =>
                Console[IO].error(help).as(ExitCode.Error)


    private[refry] def greet(name: String): String =
        s"Hello, $name!!!"


    private[refry] val help: String =
        """|Usage: refry PATH...
           |""".stripMargin
