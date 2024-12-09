import cats.effect.IO
import com.pg.bigdata.neighborhoodanalytics.aoc.fp.Exercise

object Day5 extends Exercise(2024, 5) {

  private def parseInput(input: List[String]): (List[String], List[String]) = {
    val blankLineIndex = input.indexWhere(_.trim.isEmpty)

    val rules   = input.take(blankLineIndex).filter(_.nonEmpty)
    val updates = input.drop(blankLineIndex + 1).filter(_.nonEmpty)

    (rules, updates)
  }

  private def parseRules(rules: List[String]): Seq[(Int, Int)] =
    rules.map { rule =>
      val Array(x, y) = rule.split("\\|")
      (x.trim.toInt, y.trim.toInt)
    }

  private def isUpdateCorrect(update: List[Int], rules: Seq[(Int, Int)]): Boolean = {
    val positions = update.zipWithIndex.toMap

    val applicableRules = rules.filter { case (x, y) =>
      positions.contains(x) && positions.contains(y)
    }

    applicableRules.forall { case (x, y) =>
      positions(x) < positions(y)
    }
  }

  private def getMiddlePage(update: List[Int]): Int = update(update.length / 2)

  override def part1(input: List[String]): IO[String] = {
    val (rules, updates) = parseInput(input)

    val orderRules    = parseRules(rules)
    val parsedUpdates = updates.map(u => u.split(",").map(_.trim.toInt).toList)

    val correctUpdates   = parsedUpdates.filter(isUpdateCorrect(_, orderRules))
    val sumOfMiddlePages = correctUpdates.map(getMiddlePage).sum

    IO.pure(sumOfMiddlePages.toString)
  }

  override def part2(input: List[String]): IO[String] = {
    val (rules, updates) = parseInput(input)

    val orderRules    = parseRules(rules)
    val parsedUpdates = updates.map(u => u.split(",").map(_.trim.toInt).toList)

    val correctUpdates   = parsedUpdates.filter(isUpdateCorrect(_, orderRules))
    val sumOfMiddlePages = correctUpdates.map(getMiddlePage).sum

    IO.pure(sumOfMiddlePages.toString)
  }
}
