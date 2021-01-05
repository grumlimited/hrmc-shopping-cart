package cart.algebra

import cart.domain.{Basket, Item}
import cats.effect.unsafe.implicits.global
import flow.CartFlow._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CartCalculationsSpec extends AnyFlatSpec with Matchers {

  "An empty basket" should "have a check out value of 0" in {
    val checkout = Basket.EMPTY.checkout.compile.lastOrError.unsafeRunSync()

    0 should be(checkout)
  }

  "A basket containing an apple and an orange" should "have a check out value of 0.85" in {
    val checkout = Basket.EMPTY
      .add(Item.APPLE)
      .add(Item.ORANGE)
      .checkout
      .compile.lastOrError.unsafeRunSync()

    0.85 should be(checkout)
  }

  "The example from test specs" should "have a check out value of 2.05" in {
    val checkout = Basket.EMPTY
      .add(Item.APPLE)
      .add(Item.APPLE)
      .add(Item.ORANGE)
      .add(Item.APPLE)
      .checkout
      .compile.lastOrError.unsafeRunSync()

    2.05 should be(checkout)
  }
}
