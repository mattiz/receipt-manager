import java.nio.file.Files
import javax.servlet.http.HttpServletRequest
import scala.io.Source
import unfiltered.jetty.ContextBuilder
import unfiltered.request._
import unfiltered.response
import unfiltered.response._
import net.liftweb.json._
import net.liftweb.json.Serialization.{read, write}
import scala.collection.immutable.Stream;


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
          case Some(filter) => ResponseString(write(ReceiptDAO.search(filter)))
          case None => ResponseString(write(ReceiptDAO.all))
        }
      }
    }


    case GET(Path(Seg("receipt" :: id :: Nil))) => {
      ResponseString(write(ReceiptDAO.get(id.toInt)))
    }


    case req@POST(Path("/receipt")) => {
      val receipt = read[Receipt](param(req))

      val receiptId = ReceiptDAO.create(receipt)

      Created ~> ResponseString( receiptId.toString )
    }


    case DELETE(Path(Seg("receipt" :: id :: Nil))) => {
      ReceiptDAO.delete(id.toInt)

      Ok
    }


    case GET(Path(Seg("receipt" :: id :: "image" :: Nil))) => {
      val filename = "images/" + ReceiptDAO.get(id.toInt).filename

      val bytes = Files.readAllBytes(java.nio.file.Paths.get(filename))

      ResponseBytes(bytes)
    }



  case req@POST(Path(Seg("receipt" :: id :: "image" :: Nil))) => {

    val data = Stream.continually(req.inputStream.read).takeWhile(-1 !=).map(_.toByte).toArray

    println("LEN: " + data.length)

    Ok
  }
}


  def param(req: HttpRequest[HttpServletRequest]) =
    Source.fromInputStream(req.inputStream).mkString

}


object Server extends App {
  unfiltered.jetty.Http
    .local(2010)
    .context("/public") { ctx: ContextBuilder =>
        ctx.resources(new java.net.URL("file:///home/mattis/temp/scala-unfiltered/justplayin/src/main/resources/www"))
    }
    .filter(ReceiptService)
    .run()
}
