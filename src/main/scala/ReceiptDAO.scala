import scala.slick.driver.H2Driver.simple._


case class Receipt(id: Option[Int], vendor: String, description: String, filename: String, storageName: String)


object ReceiptDAO {
  object Receipts extends Table[Receipt]("Receipts") {
    def id = column[Int]("Id", O.PrimaryKey, O.AutoInc)

    def vendor = column[String]("Vendor")

    def description = column[String]("Description")

    def filename = column[String]("Filename")

    def storageName = column[String]("StorageName")

    def * = id.? ~ vendor ~ description ~ filename ~ storageName <>(Receipt, Receipt.unapply _)

    def forInsert = vendor ~ description ~ filename ~ storageName <>({ t => Receipt(None, t._1, t._2, t._3, t._4)}, { (u: Receipt) => Some((u.vendor, u.description, u.filename, u.storageName))})
  }


  implicit val session = Database.forURL("jdbc:h2:mem:test1;IGNORECASE=TRUE", driver = "org.h2.Driver").createSession()


  Receipts.ddl.create

  Receipts.insert(Receipt(None, "Jernia ", "Stekepanne", "mattis.jpg", ""))
  Receipts.insert(Receipt(None, "Clas Ohlson ", "Merkemaskin", "simpsons-avatar.jpg", ""))
  Receipts.insert(Receipt(None, "Clas Ohlson ", "Printerpapir.", "smiley.gif", ""))


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


  def create(receipt: Receipt): Int = {
    Receipts.forInsert returning Receipts.id insert receipt
  }


  def delete(id: Int): Unit = {
    (for {
      c <- Receipts where(_.id === id)
    } yield c).delete
  }

}
