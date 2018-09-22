package com.navneetgupta.typed

import akka.actor.typed.ActorContext
import akka.actor.typed.scaladsl.MutableBehavior
import akka.actor.typed.Behavior
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors

class MutableGreetingBehavior(context: ActorContext[Command], var greeting: String) extends MutableBehavior[Command] {
  override def onMessage(msg: Command): Behavior[Command] =
    msg match {
      case Greeting(name) =>
        println(s"$greeting $name")
        Behavior.same

      case ChangeGreeting(greetingTag) =>
        greeting = greetingTag
        Behavior.same
    }
}
object MutableGreetingBehaviorApp extends App {

  val behaviorSetup = Behaviors.setup[Command] {
    context => new MutableGreetingBehavior(context, "Hello")
  }
  val system = ActorSystem(behaviorSetup, "mutable-greeting-behavior")

  system ! Greeting("World")
  system ! Greeting("Navneet Gupta")

  system ! ChangeGreeting("Hola")

  system ! Greeting("World")
  system ! Greeting("Navneet Gupta")
}
