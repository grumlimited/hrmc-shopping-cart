package cart.domain

import cats.effect.IO
import flow.CartFlow

// using a List[] on purpose
case class Basket(items: List[Item])

case object Basket {
  val EMPTY = Basket(List.empty)

  implicit def toOps(b: Basket): BasketOps = new BasketOps(b)
}

final class BasketOps(private val b: Basket) extends AnyVal {
  def add(item: Item): fs2.Stream[IO, Basket] = CartFlow.add(fs2.Stream.emit(b).covary[IO], item)

  def checkout = CartFlow.checkout(fs2.Stream.emit(b).covary[IO])
}