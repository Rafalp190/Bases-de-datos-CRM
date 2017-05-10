
package main

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.HBox
import scalafx.scene.paint.Color._
import scalafx.scene.paint.{Stops, LinearGradient}
import scalafx.scene.text.Text
import scalafx.scene.control._
import scalafx.scene.layout._
import javafx.scene.control.ScrollPane
import org.mongodb.scala._




object GUI extends JFXApp {
  stage = new PrimaryStage {
    title = "GUI"
    scene = new Scene (600,360){  
      fill = Grey  
      val tabPane = new TabPane
      
      val tab2 = new Tab
      tab2.text = "Estadisticas"



      tabPane += clientTab()
     // tabPane.tabs += editClient()
    //  tabPane.tabs += newClient()    

      val borderPane = new BorderPane
      borderPane.top = tabPane

      root = borderPane

    }
  }
//private def editClient():Tab{

// }
//private def newClient():Tab{

// }

  def clientTab() : Tab = {

    val border = new BorderPane
    val searchText = new TextField
    searchText.text = "Search by ID"
    val list = new TilePane
    for(i <- 1 to 10) list.children +=  new Button("...") 
    
    border.left = new ListView( List("1","2","3"))
    border.top = searchText
    border.right = list
    val tab = new Tab
    tab.text = "Client"
 



    tab.content = new ScrollPane(border) 
    tab
  }

}