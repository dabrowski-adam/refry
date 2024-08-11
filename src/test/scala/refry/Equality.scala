package refry

import cats.effect.ExitCode

given CanEqual[ExitCode, ExitCode] = CanEqual.derived
