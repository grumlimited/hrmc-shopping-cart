package flow

import cart.algebra.CartCalculations
import cart.domain.{Basket, Item}
import cats.effect.IO

object CartFlow {

  def add(cart: fs2.Stream[IO, Basket], item: Item): fs2.Stream[IO, Basket] = {
    val sItem = fs2.Stream.emit(item).covary[IO]

    cart.zipWith(sItem)((b, i) => {
      b.copy(items = i :: b.items)
    })
  }

  def checkout(cart: fs2.Stream[IO, Basket]): fs2.Stream[IO, BigDecimal] = {
    cart.map(CartCalculations.checkout)
  }

  implicit def toOps(s: fs2.Stream[IO, Basket]): CartFlowOps = new CartFlowOps(s)
}

final class CartFlowOps(private val s: fs2.Stream[IO, Basket]) extends AnyVal {
  def add(item: Item): fs2.Stream[IO, Basket] = {
    CartFlow.add(s, item)
  }

  def checkout: fs2.Stream[IO, BigDecimal] = {
    CartFlow.checkout(s)
  }
}