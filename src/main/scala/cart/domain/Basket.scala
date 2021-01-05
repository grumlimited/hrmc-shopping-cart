package cart.domain

import cart.algebra.CartFlow
import cart.domain.Discounts.Discount
import cats.effect.IO

import scala.languageFeature.implicitConversions

// using a List[] on purpose
case class Basket(items: List[Item], discounts: Set[Discounts.Discount]) {
  def amount: BigDecimal = {
    val fullPrice =
      if (items.isEmpty) BigDecimal(0) // or use fold
      else items.map(_.price).sum

    val discount =
      if (discounts.isEmpty) BigDecimal(0)
      else discounts.map(_.discount(items)).sum

    fullPrice - discount
  }
}

case object Basket {
  val EMPTY = Basket(List.empty, Set.empty)

  implicit def toOps(b: Basket): BasketOps = new BasketOps(b)
}

final class BasketOps(private val b: Basket) extends AnyVal {
  def add(item: Item): fs2.Stream[IO, Basket] = CartFlow.add(fs2.Stream.emit(b).covary[IO], item)

  def discounted(discount: Discount): fs2.Stream[IO, Basket] = CartFlow.discounted(fs2.Stream.emit(b).covary[IO], discount)

  def checkout: fs2.Stream[IO, BigDecimal] = CartFlow.checkout(fs2.Stream.emit(b).covary[IO])
}