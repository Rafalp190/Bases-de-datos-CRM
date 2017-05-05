
package main

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{Stops, LinearGradient}
import scalafx.scene.text.Text
import org.mongodb.scala._




object GUI extends JFXApp {
  
  //Instanciacion del TwitterRest API twitInteractions.scala
  val test = new nonrel.twitInteractions
  test.mongoTweets("garnluzcglp")
  //val mongoClient: MongoClient = MongoClient()
  //val database: MongoDatabase = mongoClient.getDatabase("db")
  //val collection: MongoCollection[Document] = database.getCollection("twitter");
  //val doc: Document = Document("_id" -> 1, "name" -> "MongoDB", "type" -> "database",
  //                                "count" -> 1, "info" -> Document("x" -> 203, "y" -> 102))
      
  // val observable: Observable[Completed] = collection.insertOne(doc)
  /*
  observable.subscribe(new Observer[Completed] {    
    override def onNext(result: Completed): Unit = println("Inserted")
    override def onError(e: Throwable): Unit = println("Failed")
    override def onComplete(): Unit = println("Completed")
  })
  */  
  stage = new PrimaryStage {
    title = "ScalaFX Hello World"
    scene = new Scene {
      fill = Black
      content = new HBox {
        padding = Insets(20)
        children = Seq(
          new Text {
            text = "Hello "
            style = "-fx-font-size: 48pt"
            fill = new LinearGradient(
              endX = 0,
              stops = Stops(PaleGreen, SeaGreen))
          },
          new Text {
            text = "World!!!"
            style = "-fx-font-size: 48pt"
            fill = new LinearGradient(
              endX = 0,
              stops = Stops(Cyan, DodgerBlue)
            )
            effect = new DropShadow {
              color = DodgerBlue
              radius = 25
              spread = 0.25
            }
          }
        )
      }
    }
  }
    //mongoClient.close()
    
}