package refry


import cats.effect.{ExitCode, IO, IOApp}
import cats.effect.std.Console


object App extends IOApp:

    override def run(args: List[String]): IO[ExitCode] =
        args match
            case Help()      =>
                Console[IO].print(help).as(ExitCode.Success)
            case name :: Nil =>
                Console[IO].print(greet(name)).as(ExitCode.Success)
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


    private[refry] def greet(name: String): String =
        s"Hello, $name!!!"
