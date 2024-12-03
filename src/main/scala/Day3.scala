import cats.effect.IO
import com.pg.bigdata.neighborhoodanalytics.aoc.fp.Exercise

import scala.math.abs
import scala.util.matching.Regex

object Day3 extends Exercise(2024, 3) {

  private def prepareInput(input: List[String]): String = input.mkString(" ")

  private def calculateSumOfMultiplications(corruptedMemory: String): Int = {
    val mulPattern: Regex = """mul\s*\(\s*(\d{1,3})\s*,\s*(\d{1,3})\s*\)""".r

    var totalSum = 0

    for (matchResult <- mulPattern.findAllIn(corruptedMemory)) {
      matchResult match {
        case mulPattern(xStr, yStr) =>
          val x = xStr.toInt
          val y = yStr.toInt
          totalSum += x * y
        case _ => ()
      }
    }

    totalSum
  }

  override def part1(input: List[String]): IO[String] =
    for {
      corruptedMemory <- IO.pure(prepareInput(input))
      total = calculateSumOfMultiplications(corruptedMemory)
    } yield total.toString

  private def calculateSumOfMultiplicationsWithConditions(corruptedMemory: String): Int = {
    val mulPattern: Regex = """mul\s*\(\s*(\d{1,3})\s*,\s*(\d{1,3})\s*\)""".r

    val doPattern: Regex   = """do\s*\(\s*\)""".r
    val dontPattern: Regex = """don't\s*\(\s*\)""".r

    var totalSum   = 0
    var mulEnabled = true

    val commandPattern: Regex = s"${mulPattern}|${doPattern}|${dontPattern}".r

    for (command <- commandPattern.findAllIn(corruptedMemory)) {
      command.trim match {
        case doPattern() =>
          mulEnabled = true
        case dontPattern() =>
          mulEnabled = false
        case mulPattern(xStr, yStr) if mulEnabled =>
          val x = xStr.toInt
          val y = yStr.toInt
          totalSum += x * y
        case _ => ()
      }
    }

    totalSum
  }

  override def part2(input: List[String]): IO[String] =
    for {
      corruptedMemory <- IO.pure(prepareInput(input))
      total = calculateSumOfMultiplicationsWithConditions(corruptedMemory)
    } yield total.toString
}
