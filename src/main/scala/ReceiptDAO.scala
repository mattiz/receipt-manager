import scala.slick.driver.H2Driver.simple._


case class Receipt(id: Option[Int], vendor: String, description: String, filename: String)


object ReceiptDAO {
  object Receipts extends Table[Receipt]("Receipts") {
    def id = column[Int]("Id", O.PrimaryKey, O.AutoInc)

    def vendor = column[String]("Vendor")

    def description = column[String]("Description")

    def filename = column[String]("Filename")

    def * = id.? ~ vendor ~ description ~ filename <>(Receipt, Receipt.unapply _)
  }


  implicit val session = Database.forURL("jdbc:h2:mem:test1;IGNORECASE=TRUE", driver = "org.h2.Driver").createSession()


  Receipts.ddl.create

  Receipts.insert(Receipt(None, "Jernia ", "Stekepanne", "file1.png"))
  Receipts.insert(Receipt(None, "Clas Ohlson ", "Merkemaskin", "file2.png"))
  Receipts.insert(Receipt(None, "Clas Ohlson ", "Printerpapir.", "file3.png"))


  def get(id: Int): Receipt = {
    (for {
      c <- Receipts where(_.id === id)
    } yield c).list.head
  }


  def search(filter: String): List[Receipt] = {
    (for {
      c <- Receipts where(_.vendor.like("%" + filter + "%"))
    } yield c).list
  }


  def all: List[Receipt] = {
    (for {
      c <- Receipts
    } yield c).list
  }


  def create(receipt: Receipt): Unit = {
    Receipts.insert(receipt)
  }


  def delete(id: Int): Unit = {
    (for {
      c <- Receipts where(_.id === id)
    } yield c).delete
  }

}
