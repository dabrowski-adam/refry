package refry

import weaver.*


object AppTest extends SimpleIOSuite with Hedgehog:

    propertyTest("greets everyone"):
        for name <- Gen.string(Gen.unicode, Range.linear(1, 10)).forAll
        yield App.greet(name) ==== s"Hello, $name!!!"
