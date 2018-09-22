package com.navneetgupta.typed

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.ActorSystem

object HelloWorldTyped extends App {
  case class Greeting(name: String)

  val greetingBehaviour: Behavior[Greeting] =
    Behaviors.receiveMessage {
      case Greeting(name) =>
        println(s"Hello $name")
        Behaviors.same
    }

  val system = ActorSystem(greetingBehaviour, "my-Kingdom")
  system ! Greeting("World")
  system ! Greeting("Navneet Gupta")

  // Below Msg Gives Compiler Error Showing that it only accepts Type Greeting Which prevents Much error
  // and brings in much awaited type safety of actors

  //system ! "Navneet Gupta"
}
