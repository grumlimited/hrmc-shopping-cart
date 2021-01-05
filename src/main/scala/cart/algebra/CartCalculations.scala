package cart.algebra

import cart.domain.Basket

case object CartCalculations {

  def checkout(basket: Basket): BigDecimal = {
    val fullPrice =
      if (basket.items.isEmpty) BigDecimal(0) // or use fold
      else basket.items.map(_.price).sum

    val discount =
      if (basket.discounts.isEmpty) BigDecimal(0)
      else basket.discounts.map(_.discount(basket.items)).sum

    fullPrice - discount
  }
}
