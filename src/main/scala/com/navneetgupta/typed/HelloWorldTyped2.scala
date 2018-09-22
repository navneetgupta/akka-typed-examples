package com.navneetgupta.typed

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem

object HelloWorldTyped2 {
  final case class Greet(whom: String, replyTo: ActorRef[Greeted])
  final case class Greeted(whom: String, from: ActorRef[Greet])

  val greeter: Behavior[Greet] = Behaviors.receive { (ctx, msg) ⇒
    ctx.log.info("Hello {}!", msg.whom)
    msg.replyTo ! Greeted(msg.whom, ctx.self)
    Behaviors.same
  }
}

object HelloWorldTyped2Bot {

  def bot(greetingCounter: Int, max: Int): Behavior[HelloWorldTyped2.Greeted] =
    Behaviors.receive { (ctx, msg) ⇒
      val n = greetingCounter + 1
      ctx.log.info("Greeting {} for {}", n, msg.whom)
      if (n == max) {
        Behaviors.stopped
      } else {
        msg.from ! HelloWorldTyped2.Greet(msg.whom, ctx.self)
        bot(n, max)
      }
    }
}

object HelloWorldTyped2App extends App {

  final case class Start(name: String)

  val main: Behavior[Start] =
    Behaviors.setup { context ⇒
      val greeter = context.spawn(HelloWorldTyped2.greeter, "greeter")

      Behaviors.receiveMessage { msg ⇒
        val replyTo = context.spawn(HelloWorldTyped2Bot.bot(greetingCounter = 0, max = 3), msg.name)
        greeter ! HelloWorldTyped2.Greet(msg.name, replyTo)
        Behaviors.same
      }
    }

  val system: ActorSystem[HelloWorldTyped2App.Start] =
    ActorSystem(HelloWorldTyped2App.main, "hello")

  system ! HelloWorldTyped2App.Start("World")
  system ! HelloWorldTyped2App.Start("Navneet_Gupta")
}
