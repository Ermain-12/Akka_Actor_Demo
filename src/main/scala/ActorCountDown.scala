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
  case class CountDown(n: Int)

  // Create the Actor object
  class CountDownActor extends Actor{
    override def receive: Receive = {
      case StartCounting(n, other) => {
        println(n)
        other ! CountDown(n-1)
      }
      case CountDown(n) => {
        println(self)
        if (n > 0){
          println(n)
          sender ! CountDown(n-1)
        }else{
          context.system.terminate()
        }
      }
    }
  }

  val system = ActorSystem("CountDownSystem")
  val actor1 = system.actorOf(Props[CountDownActor], "CountDown1")
  val actor2 = system.actorOf(Props[CountDownActor], "CountDown2")


  // Tell the first actor to start counting down from ten along with the second actor.
  actor1 ! StartCounting(10, actor2)
}
