package refry


@main
def app(): Unit = println(greet("World"))


def greet(name: String): String = s"Hello, $name!!!"
