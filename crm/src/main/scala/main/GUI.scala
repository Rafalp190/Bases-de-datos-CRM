
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
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.Alert
import org.mongodb.scala._




object GUI extends JFXApp {
  stage = new PrimaryStage {
    title = "GUI"
    scene = new Scene (800,500){  
      val tabPane = new TabPane
      tabPane += ClientTab()
     // tabPane.tabs += editClient()
    //  tabPane.tabs += newClient()    

      val borderPane = new BorderPane
      borderPane.top = new ScrollPane(tabPane)

      root = borderPane

    }
  }
//private def EditClient():Tab{

// }
//private def NewClient():Tab{

// }

  def ClientTab() : Tab = {

    val border = new BorderPane
    val searchText = new TextField
    searchText.text = "Search by ID"

    val search = new Button("Search")
    search.onAction = (e:ActionEvent) => {
      //agregar el codigo para buscar en la lista
      System.out.println("ajua")
    }


    val list = new TilePane
    for(i <- 1 to 10) {
      val editButton = new Button("...")
      editButton.onAction = (e:ActionEvent)=>{
        EditPopUp()
      }
      list.children += editButton
    }

    val newClient = new Button
    newClient.text = "New Client"
    newClient.onAction = (e:ActionEvent) =>{
      System.out.println("ajonjoli")
    }

    val searchPane = new TilePane 
    searchPane.children = List(searchText,search,newClient) 
    border.top = searchPane
    border.left = new ListView( List("1","2","3")) 
    border.right = list
    val tab = new Tab
    tab.text = "Client"
 
    tab.content = new ScrollPane(border) 
    tab
  }

  def EditPopUp() : Alert = {
   
    val editButton = new ButtonType("Edit")
    val deleteButton = new ButtonType("Delete")

    val editAlert = new Alert(AlertType.Confirmation){
      initOwner(stage)
      title  = "Alert"
      headerText = "What you want to do to the client?"
      contentText = "Choose your option"

      buttonTypes = Seq(editButton, deleteButton, ButtonType.Cancel)
    }
    
    val result = editAlert.showAndWait()

    result match{

      case Some(`editButton`) => println("edit")
      
      case Some(`deleteButton`) => DeletePopUp()

      case _ => println("nanodesu")
    }

    editAlert
  }

  def DeletePopUp() : Alert = {

    val deleteAlert = new Alert(AlertType.Confirmation){
      initOwner(stage)
      title  = "Alert"
      headerText = "Are you sure you want to delete this client?"
      contentText = "Choose your option"
    }

    val result = deleteAlert.showAndWait()

    result match{

      case Some(ButtonType.OK) => println("deleteado")
      case _ => println("nanodesu")

    }
    deleteAlert
  }

}