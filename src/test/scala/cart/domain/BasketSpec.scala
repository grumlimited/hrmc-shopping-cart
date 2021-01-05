package cart.domain

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BasketSpec extends AnyFlatSpec with Matchers {
  "An empty basket" should "have no items" in {
    Basket.EMPTY.items shouldBe empty
  }
}
