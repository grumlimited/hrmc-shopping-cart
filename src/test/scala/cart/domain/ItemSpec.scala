package cart.domain

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ItemSpec extends AnyFlatSpec with Matchers {

  "An item" should "hold a name and value" in {
    val item = Item.from("x", 0.1)

    item.price should be(0.1)
    item.name should be("x")
  }
}
