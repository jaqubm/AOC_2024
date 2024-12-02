import cats.effect.IO
import com.pg.bigdata.neighborhoodanalytics.aoc.fp.Exercise

import scala.math.abs

object Day2 extends Exercise(2024, 2) {

  private def prepareInput(input: List[String]): List[List[Int]] = input.map(line => line.split(" ").map(_.toInt).toList)

  private def checkSafety(report: List[Int]): Boolean = {
    if (report.isEmpty) return false

    var isIncreasing = true
    var isDecreasing = true

    for (i <- 0 until report.length - 1) {
      val difference = report(i + 1) - report(i)

      if (abs(difference) < 1 || abs(difference) > 3) return false

      if (difference > 0) isDecreasing = false
      else if (difference < 0) isIncreasing = false
    }

    isIncreasing || isDecreasing
  }

  override def part1(input: List[String]): IO[String] =
    for {
      prepped <- IO.pure(prepareInput(input))
      safeCount = prepped.count(checkSafety)
    } yield safeCount.toString

  private def checkSafetyWithDampener(report: List[Int]): Boolean = {
    if (checkSafety(report)) return true

    for (i <- report.indices) {
      val modifiedReport = report.take(i) ++ report.drop(i + 1)
      if (checkSafety(modifiedReport)) return true
    }

    false
  }

  override def part2(input: List[String]): IO[String] =
    for {
      prepped <- IO.pure(prepareInput(input))
      safeCountWithDampener = prepped.count(checkSafetyWithDampener)
    } yield safeCountWithDampener.toString
}
