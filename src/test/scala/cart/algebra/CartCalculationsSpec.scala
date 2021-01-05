package cart.algebra

import cart.domain.Discounts.{Apples, Oranges}
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

  "An empty basket with discounts" should "have a check out value of 0" in {
    val checkout = Basket.EMPTY
      .discounted(Apples)
      .discounted(Oranges)
      .checkout
      .compile.lastOrError.unsafeRunSync()

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

  "A basket containing an apple and an orange and no applicable discounts" should "have a check out value of 0.85" in {
    val checkout = Basket.EMPTY
      .discounted(Apples)
      .add(Item.APPLE)
      .add(Item.ORANGE)
      .discounted(Oranges)
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

  "The example from test specs with now an apple discount" should "have a check out value of 2.05" in {
    val checkout = Basket.EMPTY
      .discounted(Apples)
      .add(Item.APPLE)
      .add(Item.APPLE)
      .add(Item.ORANGE)
      .add(Item.APPLE)
      .discounted(Oranges)
      .checkout
      .compile.lastOrError.unsafeRunSync()

    1.45 should be(checkout)
  }

  "3 oranges with an orange discount" should "have a check out value of 0.50" in {
    val checkout = Basket.EMPTY
      .add(Item.ORANGE)
      .add(Item.ORANGE)
      .add(Item.ORANGE)
      .discounted(Oranges)
      .checkout
      .compile.lastOrError.unsafeRunSync()

    0.50 should be(checkout)
  }

  "Repeated discounts" should "have no bearing on correctness of calculations" in {
    val checkout = Basket.EMPTY
      .add(Item.ORANGE)
      .add(Item.ORANGE)
      .discounted(Oranges)
      .add(Item.APPLE)
      .discounted(Apples)
      .discounted(Oranges)
      .discounted(Apples)
      .add(Item.ORANGE)
      .add(Item.APPLE)
      .discounted(Oranges)
      .discounted(Apples)
      .discounted(Oranges)
      .discounted(Oranges)
      .discounted(Apples)
      .discounted(Oranges)
      .discounted(Apples)
      .discounted(Oranges)
      .discounted(Oranges)
      .discounted(Apples)
      .discounted(Oranges)
      .checkout
      .compile.lastOrError.unsafeRunSync()

    1.10 should be(checkout)
  }
}
