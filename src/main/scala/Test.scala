/**
 *
 *
 * @author Mathias Bjerke
 */
object Test extends App {
  ReceiptDAO.all.foreach(println)

  println("=======")

  ReceiptDAO.search("clas").foreach(println)

  println("=======")

  println( ReceiptDAO.get(2) )

  println("=======")

  ReceiptDAO.delete(2)
  ReceiptDAO.all.foreach(println)
}
