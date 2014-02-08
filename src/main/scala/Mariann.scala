

/**
 *
 *
 * @author Mathias Bjerke
 */



class Person(balle:String) {
  def name = balle
}







object Mariann extends App {


  val mattis = new Person("mattis")


  val person:Option[Person] = Some(new Person("mariann"))
  //val person:Option[Person] = None


  /*
  person match {
    case Some(p) => println(p.name)
    case None => println("Nothing")
  }
  */



  /*
  val result = person match {
    case Some(p) => p.name
    case None => "WTF"
  }

  println( result )
  */




  //println( person.getOrElse(mattis).name )




  //person.foreach( x => println( x.name ) )





  /*
  person
    .map( x => x.name.toUpperCase )
    .foreach(println)
  */






  val map = Map( "Mariann" -> 4, "Mathias" -> 31 )

  println( map.get("Mariann") )
  println( map.get("Folco") )
  println( map("Mariann") )
  //println( persons("Folco") )     // Exception

  val newMap = map.lift
  println( newMap("Folco").getOrElse(123) )

}
