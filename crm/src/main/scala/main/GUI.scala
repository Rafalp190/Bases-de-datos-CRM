
package main

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.effect.DropShadow
import scalafx.scene.layout.HBox
import scalafx.scene.layout.VBox
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
import javafx.scene.input.MouseEvent
import javafx.event.EventHandler
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
    searchText.onMouseClicked = (e: MouseEvent) =>{
      searchText.text = ""
    }

    val search = new Button("Search")
    search.onAction = (e:ActionEvent) => {
      val searchTextString = searchText.getText()
      val idInt = ToInt(searchTextString)
      println(idInt)
    }

    val newClient = new Button
    newClient.text = "New Client"
    newClient.onAction = (e:ActionEvent) =>{
      mainTabPane += NewClient()
    }
    val Tabla = new ListView(List("8","7","6")){
      onMouseClicked = (e: MouseEvent) => {
        EditPopUp()
        //val posList = Tabla.SelectionModel.getSelectedItem()
        //println(posList)
      }
    }

    val searchPane = new HBox{
      prefWidth = 600
      children = List(searchText,search,newClient)
    } 
    


    val tablaPane = new VBox{
      children = List(searchPane,Tabla)
    }

    border.center = tablaPane 

    val tab = new Tab
    tab.text = "Client"
 
    tab.content = new ScrollPane(border) 
    tab
  }

  def EstadisticasTab() : Tab = {
    
     val searchText = new TextField
    searchText.text = "Search by ID"
    searchText.onMouseClicked = (e: MouseEvent) =>{
      searchText.text = ""
    }

    val search = new Button("Search")
    search.onAction = (e:ActionEvent) => {
      val searchTextString = searchText.getText()
      val idInt = ToInt(searchTextString)
      println(idInt)
    }

    val searchPane = new HBox{
      children = List(searchText, search)
      prefWidth = 600
    }
     

    val Tabla = new ListView( List("1","2","3"))

    val tabPane = new TabPane

    val tab1 = new Tab
    tab1.text = "Estadisticas Racionales"

    tabPane += tab1
    tabPane += TwitterTab()

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

  def ToInt(s:String): Int = {
    try{
      s.toInt
    } catch{
      case e:Exception => 0
    }
  }

  def TwitterTab(): Tab = {

    val mongoClient =  MongoClient()
    val db = mongoClient("test")
    val collection = db("twitter")
    val tweetManager = new nonrel.tweetInteractions
    val hbox = new HBox{
      val searchText = new TextField
      searchText.text = "Search by Twitter Handle"
      searchText.onMouseClicked = (e: MouseEvent) =>{
      searchText.text = ""
      }

      val search = new Button("Search")
      search.onAction = (e:ActionEvent) => {
      val searchTextString = searchText.getText()
      val handle = "odersky" 
      if(searchTextString == odersky){
        val searchTwt = new VBox{
          val valSearchTwt = HBox{
            val searchTextTwt = new TextField
            searchTextTwt.text = "Search by Twitter Handle"
            searchTextTwt.onMouseClicked = (e: MouseEvent) =>{
            searchTextTwt.text = ""
            }
            val search = new Button("Search")
            search.onAction = (e:ActionEvent) => {
            val searchTextStringTwt = searchTextTwt.getText()
            val result = tweetManager.searchTweets(handle,searchTextStringTwt,collection)
            }
          }
        }
      }
      }
    }

    val vbox = new VBox{
      
    }

    val tab = new Tab
    tab.text = "Twitter"
    tab.content = new ScrollPane(vbox)
    tab
  }

  def userNotFound() : Alert = {
    val userNotFoundAlert = new Alert(AlertType.Warning){
      initOwner(stage)
      title = "Twitter not found"
      headerText = "The user you were looking for does not exist"
    }
    val result = userNotFoundAlert.showAndWait()

    userNotFoundAlert
  }

}