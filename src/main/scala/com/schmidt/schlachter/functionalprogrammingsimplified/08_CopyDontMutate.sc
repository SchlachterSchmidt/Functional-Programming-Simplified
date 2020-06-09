// some definitions

case class Name(
  firstName: String,
  lastName: String,
  mi: String
)

case class CreditCard(
  name: Name,
  number: String,
  month: Int,
  year: Int,
  cvv: String
)

case class BillingInfo(
  creditCards: Seq[CreditCard]
)

case class User(
  id: Int,
  name: Name,
  billingInfo: BillingInfo,
  phone: String,
  email: String
)

val name: Name = Name(firstName = "Joe", lastName = "Smith", "Dr.")
val creditCard: CreditCard = CreditCard(name = name, number = "123", month = 12, year = 2024, cvv = "404")
val billingInfo: BillingInfo = BillingInfo(Seq(creditCard))
val user1: User = User(id = 1, name = name, billingInfo = billingInfo, phone = "123-456-789", email = "asd@dsa.com")

// updating an attribute of a user by copying:
val user2 = user1.copy(phone = "555-515-5349")

// it can get messy though when we are dealing with nested objects:
val name2 = user1.name.copy(firstName = "Joseph")
val user3 = user1.copy(name = name2)

val newCC = user1.billingInfo.creditCards(0).copy(cvv = "401")
val newCCs = Seq(newCC)
val user4 = user1.copy(billingInfo = BillingInfo(newCCs))

// there is a better way with lenses, which will be covered later.
