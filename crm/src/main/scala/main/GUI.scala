
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
import scalafx.geometry.Orientation
import org.mongodb.scala._





object GUI extends JFXApp {

  val mainTabPane = new TabPane
  stage = new PrimaryStage {
    title = "GUI"
    scene = new Scene {  
      
      mainTabPane += ClientTab()
      mainTabPane += EstadisticasTab()

      val borderPane = new BorderPane
      borderPane.center = new ScrollPane(mainTabPane)

      root = borderPane

    }
  }

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
      mainTabPane += NewClient()
    }
    val Tabla = new ListView( List("1","2","3"))

    val searchPane = new SplitPane 
    searchPane.items ++= List(searchText,search,newClient) 

    val tablaPane = new SplitPane
    tablaPane.items ++= List(Tabla,list)

    border.right = searchPane
    border.bottom = tablaPane 

    val tab = new Tab
    tab.text = "Client"
 
    tab.content = new ScrollPane(border) 
    tab
  }

  def EstadisticasTab() : Tab = {
    
    val searchText = new TextField
    searchText.text = "Search by ID"

    val search = new Button("Search")
    search.onAction = (e:ActionEvent) => {
      //agregar el codigo para buscar en la lista
      System.out.println("ajua")
    }

    val searchPane = new SplitPane
    searchPane.items ++= List(searchText, search)

    val Tabla = new ListView( List("1","2","3"))

    val tabPane = new TabPane

    val tab1 = new Tab
    tab1.text = "Estadisticas Racionales"

    val tab2 = new Tab
    tab2.text = "Twitter"

    tabPane += tab1
    tabPane += tab2

    val topSplit = new SplitPane
    topSplit.orientation = Orientation.Vertical
    topSplit.items ++= List(Tabla, tabPane)

    val border = new BorderPane
    border.top = searchPane
    border.center = topSplit
    
    val tab = new Tab
    tab.text = "Estadisticas"
 
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

      case Some(`editButton`) => {
        mainTabPane += EditClient()
      }
      
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

  def EditClient(): Tab = {
    val textField = new TextField
    textField.text = "nigga"

    val tab = new Tab
    tab.text = "EditClient"
 
    val border = new BorderPane
    border.top = textField

    tab.content = new ScrollPane(border)
    tab
  }

  def NewClient(): Tab = {
    val textField = new TextField
    textField.text = "nigga"

    val tab = new Tab
    tab.text = "New Client"
 
    val border = new BorderPane
    border.top = textField

    tab.content = new ScrollPane(border)
    tab
  }

}