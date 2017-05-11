
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
import com.mongodb.casbah.Imports._
import scala.collection.mutable.ListBuffer
import scalax.chart.api._
import scalax.chart._
import java.io.File
import scalafx.scene.image._



import com.github.nscala_time.time.Imports._





object GUI extends JFXApp {

  val mainTabPane = new TabPane
  
  stage = new PrimaryStage {
    title = "GUI"
    scene = new Scene(800,600) {  
      
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
      prefWidth = 800
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
      prefWidth = 800
    }
     

    

    val tabPane = new TabPane

    val tab1 = new Tab
    tab1.text = "Estadisticas Racionales"

    tabPane += tab1
    tabPane += TwitterTab()

    val topSplit = new SplitPane
    topSplit.items ++= List(tabPane)
    

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
    val tweetManager = new nonrel.twitInteractions
    
    
    val searchText = new TextField
    searchText.text = "Search by Twitter Handle"
    searchText.onMouseClicked = (e: MouseEvent) =>{
    searchText.text = ""
    }

    val searchTextTwt = new TextField
    searchTextTwt.text = "Search Content"
    searchTextTwt.onMouseClicked = (e: MouseEvent) =>{
    searchTextTwt.text = ""
    }

    val searchTwtBar = new HBox

    val searchTwt = new HBox

    val handleBox = new VBox

    val mentionBox = new VBox

    val hashBox = new VBox

    val dBox = new VBox

    val searchIf = new Button("Search")
    searchIf.onAction = (e:ActionEvent) => {
      val searchTextString = searchText.getText()
      val handle = "odersky" 
      if(searchTextString == handle){
        tweetManager.mongoInsert(handle,collection)
        val searchConctent = new Button("Search")
        searchConctent.onAction = (e:ActionEvent) => {
        val searchTextStringTwt = searchTextTwt.getText()
        val result = tweetManager.searchTweets(handle,searchTextStringTwt,collection)
        val resultName = result._1
        val resultCount = result._2.toString
        val textResult = new Text{
          text = ( "You searched for the Term: \n"++ resultName ++ " Found: \n" ++ resultCount ++ " Times")
          style = "-fx-font-size: 15pt"
          }
          handleBox.children = List(textResult)
        }

        val mentions = tweetManager.topMentions(handle,collection)
        tweetManager.topMentionPlot(mentions)
        var mentionListBuffer = new ListBuffer[String]
        for(mention <- mentions){
          val mentionResultName = mention._1
          val mentionResultCoun = mention._2.toString
          mentionListBuffer += mentionResultName ++ ": " ++ mentionResultCoun

        }
        val mentionList = mentionListBuffer.toList
        val mentionView = new ListView(mentionList)
        val labelmentions = new Text{
          text = "Users most mentioned"
          style = "-fx-font-size: 15pt"
        }

        val mentionsHash = tweetManager.topHashtags(handle,collection)
        tweetManager.topHashtagPlot(mentionsHash)
        var hashListBuffer = new ListBuffer[String]
        for(hastag <- mentionsHash){
          val hashResultName = hastag._1
          val hashResultCoun = hastag._2.toString
          hashListBuffer += hashResultName ++ ": " ++ hashResultCoun

        }
        val hashList = hashListBuffer.toList
        val hashView = new ListView(hashList)
        val labelHash = new Text{
          text = "Top ten Hashtags"
          style = "-fx-font-size: 15pt"
        }

        val Dtwt = tweetManager.dailyTweets(handle,collection)
        var dtwtBuffer = new ListBuffer[String]
        for(day <- Dtwt){
          val daytwt = day._1.toString
          val doytwt = day._2.toString
          dtwtBuffer += daytwt ++ ": " ++ doytwt
        }
        val dTwtList = dtwtBuffer.toList
        val dtwtView = new ListView(dTwtList)
        val labelDailyTwt = new Text{
          text = "Daily Tweets"
          style = "-fx-font-size: 15pt"
        }
        
        dBox.children = List(labelDailyTwt, dtwtView)
        hashBox.children = List(labelHash, hashView)
        mentionBox.children = List(labelmentions,mentionView)
        searchTwtBar.children = List(searchTextTwt, searchConctent)
        searchTwt.children = List(mentionBox, hashBox, dBox, searchTwtBar,handleBox )
     }
    }
         
    val hbox = new HBox{
      children = List(searchText, searchIf)
    }

    val vbox = new VBox{
      children = List(hbox, searchTwt)
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