package refry


import cats.effect.{ExitCode, IO, IOApp}
import cats.syntax.all.*


object App extends IOApp:

    override def run(args: List[String]): IO[ExitCode] =
        args match
            case name :: Nil => IO.println(greet(name)).as(ExitCode.Success)
            case _           => ExitCode.Error.pure[IO]


    def greet(name: String): String = s"Hello, $name!!!"
