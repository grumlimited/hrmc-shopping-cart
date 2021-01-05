HMRC Shopping cart
==================

# Compiling and testing

    sbt test

# Assumptions

Test specifications do mention:

    Make reasonable assumptions about the inputs to your solution; for example, many
    candidates take a list of strings as input

I did not think of the exercise as being one of CLI-arguments parsing. The resulting code concentrates on
basket and items operations.

As such there is no user input per se.

# Design considerations

### Dsl

Whilst potentially not strictly required to bring in libraries such as `fs2` and `cats` for this exercise, they nonetheless 
allow for ease of writing simple enough DSL-like operations, ie:

    val checkout = Basket.EMPTY
      .discounted(Apples)
      .add(Item.APPLE)
      .add(Item.ORANGE)
      .discounted(Oranges)
      .checkout
      .compile.lastOrError.unsafeRunSync()

    0.85 should be(checkout)

### Item

    trait Item {
        def name: String
        def price: BigDecimal
    }

`name`, as a `String`, is the principal discriminant for an `Item`. This would not be good enough for production quality code
 and would need some other form of uniquely generated IDs (from db or similar).

### Basket

    case class Basket(items: List[Item], discounts: Set[Discount])

`items` is a simple `List`. Partly because it is assumed such a list would not grow to any extraordinary sizes.
Partly because there are no requirements at present that would require a more complex data structure,
allowing for example for more advanced discount calculations.


### Todos

Add maybe some more control around what a valid item may or may not be.

For example, there are no restrictions on having an empty string for a name. This may not make sense to a user, but is nonetheless as much a valid
discriminant as any.

Similarly, an item's price can be 0 or negative. Maybe it should not be allowed. Or maybe it's a freebie, or a refund ? The specs do not say.
