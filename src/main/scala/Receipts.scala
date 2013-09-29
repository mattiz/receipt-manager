import scala.collection.mutable

/**
 *
 *
 * @author Mathias Bjerke
 */
case class Receipt(vendor: String, description: String)

object Receipts {
  val rs = mutable.ArrayBuffer[Receipt](
    Receipt("Jernia", "Stekepanne"),
    Receipt("Biltema", "Hammer"),
    Receipt("Clas Ohlsen", "Skrutrekker. Stiftemaskin")
  )


  def get(id: Int) = {
    rs(id)
  }


  def search(filter:String) = {
    rs.filter(r => r.description.toLowerCase.contains(filter.toLowerCase) || r.vendor.toLowerCase.contains(filter.toLowerCase))
  }


  def all = {
    rs
  }


  def create(receipt: Receipt):Unit = {
    rs += receipt
  }


  def delete(id: Int):Unit = {
    rs -= rs(id)
  }
}
