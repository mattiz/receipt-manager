package com.example


import unfiltered.request._
import unfiltered.response._


object Echo extends unfiltered.filter.Plan {
  def intent = {
    case GET(Path("/test")) => ResponseString("Hohoh")

    case GET(Path(Seg("test" :: id :: Nil))) => ResponseString("jaujau: " + id)

    case Path(Seg(p :: Nil)) => ResponseString(p)

    case _ => ResponseString(
      "I can echo exactly one path element. Woop"
    )
  }
}


object Server extends App {
  unfiltered.jetty.Http.local(2010).filter(Echo).run()
}
