package cart.domain

trait Item {
  def name: String
  def price: BigDecimal
}

object Item {
  def from(_name: String, _price: BigDecimal): Item = {
    new Item {
      override val name: String = _name

      override val price: BigDecimal = _price
    }
  }

  //for sake of convenience for exercise
  val APPLE = from("apple", 0.60);
  val ORANGE = from("orange", 0.25);
}