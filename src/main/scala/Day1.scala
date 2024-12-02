import cats.effect.IO
import com.pg.bigdata.neighborhoodanalytics.aoc.fp.Exercise

import scala.math.abs

// Copied from JJ to check how it works, because I couldn't make examples from README to work
object Day1 extends Exercise(2024, 1) {
  case class Pair(el1: Long, el2: Long)
  private object Pair {
    def apply(el: String): Pair = {
      val split = el.split(" {3}")
      Pair(split(0).toLong, split(1).toLong)
    }
  }

  private def prepInput(input: List[String]): List[Pair] = input.map(el => Pair(el))

  private def distance(el1: List[Long], el2: List[Long]): Long = (el1 zip el2).map(el => abs(el._1 - el._2)).sum

  override def part1(input: List[String]): IO[String] =
    for {
      prepped <- IO.pure(prepInput(input))
      list1 = prepped.map(_.el1).sorted
      list2 = prepped.map(_.el2).sorted
    } yield distance(list1, list2).toString

  private def similarity(el1: List[Long], el2: List[Long]): Long = {
    val counts = el2.groupMapReduce(identity)(_ => 1L)(_ + _)
    el1.map(el => el * counts.getOrElse(el, 0L)).sum
  }

  override def part2(input: List[String]): IO[String] =
    for {
      prepped <- IO.pure(prepInput(input))
      list1 = prepped.map(_.el1)
      list2 = prepped.map(_.el2)
    } yield similarity(list1, list2).toString
}
