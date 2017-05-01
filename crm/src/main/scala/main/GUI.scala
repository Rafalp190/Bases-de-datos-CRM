
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
  
  //Instanciacion del cliente de mongodb, la base de datos y la coleccion a usar
  val mongoClient: MongoClient = MongoClient()
  val db: MongoDatabase = mongoClient.getDatabase("Tweets")
  val col: MongoCollection[Document] = db.getCollection("test")    
  
  //Prueba de insert para comprobar que funciona la conexion a mongo
  /*val doc: Document = Document("_id" -> 1, "name" -> "MongoDB", "type" -> "database",
                             "count" -> 1, "info" -> Document("x" -> 203, "y" -> 102))
  col.insertOne(doc)
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
}