package refry


import cats.effect.ExitCode
import weaver.*


object AppTest extends SimpleIOSuite with Hedgehog:

    test("find .tasty files"):
        val source = "src/test/resources/sources/hello-world/"
        for exitCode <- App.run(List(source))
        yield expect(exitCode == ExitCode.Success)
