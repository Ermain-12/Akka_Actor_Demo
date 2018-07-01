import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ActorCountDown extends App {

  /*
      It is best practice to use case classes to help structure the type
      of messages we want to receive. They help tell us more about the message
      that we are receiving
   */
  /*
    @params:
      n: The integer from which we begin counting
      other: The Actor with which we will be doing the counting
   */
  case class StartCounting(n: Int, other: ActorRef)
  case class CountDown()
  class CountDownActor extends Actor{
    override def receive: Receive = {
      case s: String => println("Received a string")
      case i: Int => println("Received an Integer")
    }
  }

  val system = ActorSystem("CountDownSystem")
  val actor = system.actorOf(Props[CountDownActor])
}
