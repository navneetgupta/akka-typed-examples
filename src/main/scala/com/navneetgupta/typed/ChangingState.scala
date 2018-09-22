package com.navneetgupta.typed

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.ActorSystem

object ChangingState extends App {
  def greeter(greeting: String): Behavior[Command] =
    Behaviors.receiveMessage {
      case Greeting(name) =>
        println(s"$greeting $name")
        Behavior.same
      case ChangeGreeting(greetingTag) =>
        greeter(greetingTag)
    }

  val system = ActorSystem(greeter("Hello"), "greeter-system")

  system ! Greeting("World")
  system ! Greeting("Navneet Gupta")

  system ! ChangeGreeting("Hola")

  system ! Greeting("World")
  system ! Greeting("Navneet Gupta")

}

sealed trait Command
case class Greeting(name: String) extends Command
case class ChangeGreeting(newGreeting: String) extends Command
