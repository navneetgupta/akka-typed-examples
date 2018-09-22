package com.navneetgupta.typed

import akka.actor.typed.ExtensibleBehavior
import akka.actor.typed.ActorContext
import akka.actor.typed.Behavior
import akka.actor.typed.Signal
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.ActorSystem

class GreetingBehavior(private var greeting: String) extends ExtensibleBehavior[Command] {

  override def receive(ctx: ActorContext[Command], msg: Command): Behavior[Command] = {
    msg match {
      case Greeting(name) =>
        println(s"$greeting $name")
        Behavior.same
      case ChangeGreeting(greetingTag) =>
        greeting = greetingTag
        Behavior.same
    }
  }

  override def receiveSignal(ctx: ActorContext[Command], msg: Signal): Behavior[Command] = {
    Behavior.same
  }
}

object GreetingBehaviorApp extends App {
  val greetingBehavior = ActorSystem(new GreetingBehavior("Hello"), "Greeting-Beahvoir")

  greetingBehavior ! Greeting("World")
  greetingBehavior ! Greeting("Navneet Gupta")

  greetingBehavior ! ChangeGreeting("Hola")

  greetingBehavior ! Greeting("World")
  greetingBehavior ! Greeting("Navneet Gupta")

  val behaviorSetup: Behavior[Command] = Behaviors.setup[Command] {
    context => new GreetingBehavior("Hello")
  }
  val greetingBehavior2 = ActorSystem(behaviorSetup, "Greeting-Behavior2")

  greetingBehavior2 ! Greeting("World")
  greetingBehavior2 ! Greeting("Navneet Gupta")

  greetingBehavior2 ! ChangeGreeting("Hola")

  greetingBehavior2 ! Greeting("World")
  greetingBehavior2 ! Greeting("Navneet Gupta")
}
