package cart.domain

object Discounts {

  trait Discount {

    def discount(items: List[Item]): BigDecimal
  }

  object Apples extends Discount {

    val APPLE_REF = Item.APPLE

    override def discount(items: List[Item]): BigDecimal =
      items.count(_.name == APPLE_REF.name) / 2 * APPLE_REF.price
  }

  object Oranges extends Discount {

    val ORANGE_REF = Item.ORANGE

    override def discount(items: List[Item]): BigDecimal =
      items.count(_.name == ORANGE_REF.name) / 3 * ORANGE_REF.price
  }

}
