import akka.actor.{Actor, ActorSystem, Props}

object Main extends App{

  class SimpleActor extends Actor{
    def receive = {
      case s: String => println(s"Received string ${s}")
      case i: Int => println(s"Received integer ${i}")
    }

    def foo: Unit = println("Normal Method")
  }

  // Create an Akka system and give it name
  val system = ActorSystem("SimpleSystem")
  val actor = {
    system.actorOf(Props[SimpleActor], "SimpleActor")
  }

  println("Before Messages: ")
  // We send a message to the actor by using and exclamation point (also called a 'bang')
  actor ! "Hello, Actor"
  actor ! 22
  println("After Messages")

  system.terminate()
}
