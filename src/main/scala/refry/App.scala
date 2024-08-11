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
                    groups   = group(sources)
                    output   = groups.map((s, t) => s"$s ⇄ $t").mkString("\n")
                    _       <- Console[IO].println(output)
                yield ExitCode.Success
            case _            => Console[IO].errorln(help).as(ExitCode.Error)


    private object Help:
        def unapply(args: List[String]): Boolean =
            args match
                case Nil | ("--help" | "-help" | "help") :: Nil => true
                case _                                          => false


    private object Paths:
        def unapply(args: List[String]): Option[List[String]] =
            val positionalArgs = args.filterNot(_.startsWith("-"))

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


    private[refry] def group(files: List[Path]): Map[Path, Path] =
        files
            .groupBy: file =>
                // TODO: Get .scala path from @SourceFile in .tasty
                val bareFileName = Path(file.fileName.toString.dropRight(6))

                file.normalize.absolute.parent match
                    case Some(dir) => dir.resolve(bareFileName)
                    case _         => file.fileName
            .flatMap: (_key, value) =>
                val maybeScala = value.find(_.extName == ".scala")
                val maybeTasty = value.find(_.extName == ".tasty")

                (maybeScala, maybeTasty) match
                    case (Some(source), Some(tasty)) => Map(source -> tasty)
                    case _                           => Map.empty

end App
