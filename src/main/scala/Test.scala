/**
 *
 *
 * @author Mathias Bjerke
 */


import scala.xml.XML


object Test extends App {

  case class CalendarEntry(title: String, updated: String, published: String, summary: String)

  def readCalendar(url: String): Seq[CalendarEntry] = {
    val xml = XML.load(url)

    for (entry <- xml \\ "entry") yield CalendarEntry(
      (entry \ "title").text,
      (entry \ "updated").text,
      (entry \ "published").text,
      (entry \ "summary").text)
  }

  val calendar = readCalendar("https://www.google.com/calendar/feeds/mathbje%40gmail.com/private-8576ad84c0a35b497f070d0bbe74158a/basic")

  calendar.foreach(e => println(e.title))
}
