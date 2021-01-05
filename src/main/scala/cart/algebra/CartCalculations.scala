package cart.algebra

import cart.domain.Basket

case object CartCalculations {

  def checkout(basket: Basket): BigDecimal =
    if (basket.items.isEmpty) BigDecimal(0) // or use fold
    else basket.items.map(_.price).reduce(_ + _)
}
