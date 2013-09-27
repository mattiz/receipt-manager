//import com.codahale.jerkson.Json
import unfiltered.request._
import unfiltered.response._
//import com.codahale.jerkson.Json._


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
object Receipt extends unfiltered.filter.Plan {
  def intent = {
    case GET(Path("/receipt")) => {

      //println(generate(List(1, 2, 3)))
      //Json.generate()

      ResponseString("Hohoh")
    }
  }
}


object Server extends App {
  unfiltered.jetty.Http.local(2010).filter(Receipt).run()
}
