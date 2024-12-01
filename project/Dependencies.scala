import sbt.*

object Dependencies {
  object Versions {
    val scala    = "3.5.1"
    val aocScala = "2.0.2"
  }

  object Libraries {
    val aocScala = "com.pg.bigdata" %% "da-ap-pda-nas-aoc-scala" % Versions.aocScala
  }
}
