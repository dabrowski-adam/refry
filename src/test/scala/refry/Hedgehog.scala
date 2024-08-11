package refry


import cats.effect.*
import hedgehog.core.{OK, PropertyConfig, Report, Seed}
import hedgehog.runner.{property as prop, Prop, SeedSource, Test}
import hedgehog.Property
import weaver.*


trait Hedgehog:
    this: BaseCatsSuite & MutableIOSuite =>

    export hedgehog.{Gen, Range}
    export hedgehog.Syntax


    def propertyTest(name: String)(property: Property): Unit =
        val seed = Seed.fromLong(SeedSource.fromEnvOrTime().seed)

        test(name)(run(prop(name, property), seed))


    private def run(prop: Prop, seed: Seed): IO[Expectations] =
        for
            report <-
                IO.delay:
                    Property.check(
                        prop.withConfig(PropertyConfig.default),
                        prop.result,
                        seed,
                    )
            outcome =
                report.status match
                    case OK => success
                    case _  => failure(render(prop, report))
        yield outcome


    private def render(prop: Prop, report: Report) =
        Test.renderReport(
            this.getSuite.name,
            prop,
            report,
            ansiCodesSupported = true,
        )
