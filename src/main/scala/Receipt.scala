import javax.servlet.http.HttpServletRequest
import scala.io.Source
import unfiltered.request._
import unfiltered.response._
import net.liftweb.json._
import net.liftweb.json.Serialization.{read, write}


/*

 GET    /receipt
 GET    /receipt/123
 POST   /receipt
 PUT    /receipt/123
 DELETE /receipt/123

 GET    /receipt/123/image
 POST   /receipt/123/image
 DELETE /receipt/123/image


 */
object ReceiptService extends unfiltered.filter.Plan {
  implicit val formats = Serialization.formats(NoTypeHints)


  def intent = {
    case req@GET(Path("/receipt")) => {
      req match {
        case Params(p) => p("filter").lift(0) match {
          case Some(filter) => ResponseString(write(Receipts.search(filter)))
          case None => ResponseString(write(Receipts.all))
        }
      }
    }


    case GET(Path(Seg("receipt" :: id :: Nil))) => {
      ResponseString(write(Receipts.get(id.toInt)))
    }


    case req@POST(Path("/receipt")) => {
      val receipt = read[Receipt](param(req))

      Receipts.create(receipt)

      Created
    }


    case DELETE(Path(Seg("receipt" :: id :: Nil))) => {
      Receipts.delete(id.toInt)

      Ok
    }


    case GET(Path(Seg("receipt" :: id :: "image" :: Nil))) => {
      ResponseString("IMG: " + id)
    }
  }


  def param(req: HttpRequest[HttpServletRequest]) =
    Source.fromInputStream(req.inputStream).mkString

}


object Server extends App {
  unfiltered.jetty.Http.local(2010).filter(ReceiptService).run()
}
