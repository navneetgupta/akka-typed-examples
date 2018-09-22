package com.navneetgupta.untyped

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorSystem

object HelloWorldUntyped extends App {
  import GreetingActor._

  val system = ActorSystem("my-kingdom")
  val greetingActor = system.actorOf(GreetingActor.props, GreetingActor.name)

  // Sending Valid Messages that Actor is handling
  greetingActor ! Greeting("World")
  greetingActor ! Greeting("Navneet Gupta")

  // Sending Msgs that Actor is not handling
  // The Below Msg will got lost and Client donot have any idea that what happened to those messages. So a client Must Know
  // What messages are being handled by a cleint , Becomes even more complex Due to AKKA FSM Actors, When a msg is send when actor is another state
  // In whihc it cannot handle those messages.
  // recieve type Any => Unit type is the culprit.
  greetingActor ! "World"
  greetingActor ! "Navneet Gupta"

}
object GreetingActor {
  case class Greeting(name: String)
  def props = Props(new GreetingActor())
  val name = "Greeting-Actor"
}
class GreetingActor extends Actor {
  import GreetingActor._

  override def receive = {
    case Greeting(name) =>
      println(s"Hello $name")
  }
}
