import scala.util.{Failure, Success}
import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.pattern._

import scala.concurrent.duration._
import akka.util.Timeout

import scala.concurrent.ExecutionContext.global

object AkkaAskPattern extends App {
  // In the event the our case class does not take any arguments, it's better to use a case
  case object AskName
  case class NameResponse(name: String)
  case class AskNameOf(other: ActorRef)

  class AskActor(val name: String) extends Actor{
    override def receive: Receive = {
      case AskName =>
        Thread.sleep(1000)
        sender ! NameResponse(name)
      case AskNameOf(other) =>
        val f = other ? AskName
        f.onComplete{
          case Success(NameResponse(n)) =>
            println("Named appears to be: " + n)
          case Success(s) => println("Name unknown")
          case Failure(ex) => println("Name retrieval failed")
        }
    }
  }

  val system = ActorSystem("AskPatternSystem")
  val actor = system.actorOf(Props(new AskActor("Tom")), "AskActor")
  val actor2 = system.actorOf(Props(new AskActor("Jerry")), "AskActor")

  implicit val timeout = Timeout(1.seconds)
  val answer = actor ? AskName

  answer.foreach(n => println(s"Name is $n"))

  actor ! AskName
  system.terminate()
}
