package cart.domain

import cart.domain.Discounts.Discount
import cats.effect.IO
import flow.CartFlow

import scala.languageFeature.implicitConversions

// using a List[] on purpose
case class Basket(items: List[Item], discounts: Set[Discounts.Discount])

case object Basket {
  val EMPTY = Basket(List.empty, Set.empty)

  implicit def toOps(b: Basket): BasketOps = new BasketOps(b)
}

final class BasketOps(private val b: Basket) extends AnyVal {
  def add(item: Item): fs2.Stream[IO, Basket] = CartFlow.add(fs2.Stream.emit(b).covary[IO], item)

  def discounted(discount: Discount): fs2.Stream[IO, Basket] = CartFlow.discounted(fs2.Stream.emit(b).covary[IO], discount)

  def checkout: fs2.Stream[IO, BigDecimal] = CartFlow.checkout(fs2.Stream.emit(b).covary[IO])
}