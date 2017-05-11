package nonrel
import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities.{HashTag, Tweet}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import nonrel.Helpers._
import com.mongodb.casbah.Imports._
import scala.collection.mutable.ListBuffer
import scalax.chart.api._
import scalax.chart._
import java.io.File


import com.github.nscala_time.time.Imports._
/**
 * @author Rafa
 *Class that contains all the twitter interaction methods
 */
class twitInteractions{
 
 /**
 * Basic Test function, makes a tweet to check if connected
 * @param message
 */
  def tweet(message: String): Unit = {
    val client = TwitterRestClient()
    client.createTweet(message)
  }
/**
* Function for cleaning the static charts fuction to delete the current Graph before creating a new one
* This ensures graph file integrity and makes a clean write instead of a rewrite that could dmg the file
* @param fileName: relative path to the file 
**/
  def cleanGraphs(fileName: String) = {new File(fileName).delete()}

  /**
 * @param handle The username of the Client to look for
 * @param collection The MongoDB Collection object
 * Fetches the tweets for a user, Parses their contents and Stores them in a MongoDB Collection
 * -Fetches tweets using RestAPI
 * -Parses into Map
 * -Recursively Inserts into MongoDB
 * Validations:
 * -Must check Tweet isnt already on DB (USING TWEET ID)
 * 
 */
  def mongoInsert(handler: String, collection: MongoCollection): Unit = {
    val client = TwitterRestClient()
    val result =  client.userTimelineForUser(handler).map { ratedData =>
    val tweets = ratedData.data
    //Recursively traverses the tweet map
    for (tweet <- tweets) {
      val id = tweet.id
      val handle: String = tweet.user.map(_.screen_name).mkString
      val date =  tweet.created_at
      val country: String = tweet.place.map(_.country).mkString
      val place: String = tweet.place.map(_.full_name).mkString
      val content: String = tweet.text
      val source: String = tweet.source
      val favorites = tweet.favorite_count
      val retweets = tweet.retweet_count
      //entities
      val hashtags: String = tweet.entities.map(_.hashtags.map(_.text).mkString(", ")).mkString
      val mentions: String = tweet.entities.map(_.user_mentions.map(_.screen_name).mkString(", ")).mkString
      val urls: String = tweet.entities.map(_.urls.map(_.display_url).mkString(", ")).mkString
      // Structured document for recursive insertion
      val doc: MongoDBObject = MongoDBObject("_id"-> id, 
                                    "handle"-> handle,
                                    "date" -> date,
                                    "location" -> MongoDBObject("place"-> place,
                                                          "country"-> country), 
                                     "content" -> content,
                                     "source" -> source,
                                     "favorites" -> favorites,
                                     "retweets" -> retweets,
                                     "entities" -> MongoDBObject("Hashtags"-> hashtags,
                                                            "mentions"-> mentions,
                                                            "urls"-> urls)
                                     )


      collection.save(doc)
  }}
  
}
/**
Top Hashtags
  @param
  handle: Username for twittter
  collection: MongoCollection
  n: Amount to show: Default 10
  -Gets the top hashtag for the user
  -Returns as a Seq[(String, Int)], String is mentioned user, Int is times it appears
  **/
  def topHashtags(handle:String, collection: MongoCollection, n :Int = 10 ): Seq[(String,Int)] = {
    val query = MongoDBObject("handle" -> handle)
    var hashtags = new ListBuffer[String]
    val results = collection.find(query)
    for (res <- results){
      val hashtagString = res.expand[String]("entities.Hashtags").mkString.toLowerCase
        if (!hashtagString.equals("")){
          val hashtag = hashtagString.split(", ").foreach(hashtags +=)
        }
    }
    
  
    val hashtagList: List[String] = hashtags.toList
 
    val hashtagFrequencies: Map[String, Int] = hashtagList.groupBy(identity).mapValues(_.size)
    hashtagFrequencies.toSeq.sortBy { case (entity, frequency) => -frequency }.take(n)
  }
    /**
  * Top Hashtag Plot
  * @param hashtagSeq result of a top hashtags function, type Seq[(String, Int)]
  * Makes the graph for the top mentions, stores it in the static/charts directory
  **/
  def topHashtagPlot(hashtagSeq: Seq[(String,Int)]): Unit = {
    val ds = new org.jfree.data.category.DefaultCategoryDataset
    for(hashtag <- hashtagSeq){
      ds.addValue(hashtag._2 ,"hashtags", hashtag._1)
    }
    val chart = BarChart(ds)
    if (cleanGraphs("src/main/resources/static/charts/hashtagsChart.jpg")){
      chart.saveAsJPEG("src/main/resources/static/charts/hashtagsChart.jpg")
      chart.show()
    }
    else {
    chart.saveAsJPEG("src/main/resources/static/charts/hashtagsChart.jpg")
    }
  }


  /**
  Top mentions
  @param
  handle: Username for twittter
  collection: MongoCollection
  n: Amount to show: Default 10
  -Gets the top mentions for the user
  -Returns as a Seq[(String, Int)], String is mentioned user, Int is times it appears
  **/
  def topMentions(handle:String, collection: MongoCollection, n :Int = 10 ): Seq[(String,Int)] = {
    val query = MongoDBObject("handle" -> handle)
    var mentions = new ListBuffer[String]
    val results = collection.find(query)
    for (res <- results){

      val mentionString = res.expand[String]("entities.mentions").mkString.toLowerCase
        if (!mentionString.equals("")){
          val mention = mentionString.split(", ").foreach(mentions +=)
        }
    }
    
  
    val mentionList: List[String] = mentions.toList
 
    val mentionFrequencies: Map[String, Int] = mentionList.groupBy(identity).mapValues(_.size)
    mentionFrequencies.toSeq.sortBy { case (entity, frequency) => -frequency }.take(n)
  }
  /**
  * Top Mention Plot
  * @param mentionSeq result of a top mentions function, type Seq[(String, Int)]
  * Makes the graph for the top mentions, stores it in the static/charts directory
  **/
  def topMentionPlot(mentionSeq: Seq[(String,Int)]): Unit = {
    val ds = new org.jfree.data.category.DefaultCategoryDataset
    for(mention <- mentionSeq){
      ds.addValue(mention._2 ,"users", mention._1)
    }
    val chart = BarChart(ds)
    if (cleanGraphs("src/main/resources/static/charts/mentionsChart.jpg")){
    	chart.show()
      chart.saveAsJPEG("src/main/resources/static/charts/mentionsChart.jpg")
      
    }
    else {
    chart.saveAsJPEG("src/main/resources/static/charts/mentionsChart.jpg")
    
    }
  }
  /**
  Top devices
  @param
  handle: Username for twittter
  collection: MongoCollection
  n: Amount to show: Default 10
  -Gets the top devices for the user
  -Returns as a Seq[(String, Int)], String is deviceed user, Int is times it appears
  **/
  def topDevices(handle:String, collection: MongoCollection, n :Int = 5 ): Seq[(String,Int)] = {
    val query = MongoDBObject("handle" -> handle)
    var devices = new ListBuffer[String]
    val results = collection.find(query)
    for (res <- results){
      val deviceString = res.expand[String]("source").mkString.toLowerCase
        if (!deviceString.equals("")){

          val device = deviceString.split(", ").foreach(devices +=)
        }
    }
    
  
    val deviceList: List[String] = devices.toList
    
    val deviceFrequencies: Map[String, Int] = deviceList.groupBy(identity).mapValues(_.size)
    deviceFrequencies.toSeq.sortBy { case (entity, frequency) => -frequency }.take(n)
  }  

  def dailyTweets(handle: String, collection: MongoCollection, startDate: String ="2000-1-1"): Seq[(LocalDate, Int)] = {
    val query = MongoDBObject("handle" -> "odersky")
    var days = new ListBuffer[LocalDate]
    val results = collection.find(query)
    val Begining: LocalDate = new LocalDate(startDate) 
      for (res <- results) {

      
        val result = res.get("date")
        val resDateTime = new DateTime(result)
        val resDate = new LocalDate(resDateTime)
        if (resDate >= Begining){
          days += resDate
        }

      //val dateStream: DateTime = res.get("date")
    }
    val dayList: List[LocalDate] = days.toList

    
    val dayFrequencies: Map[LocalDate, Int] = dayList.groupBy(identity).mapValues(_.size)
    dayFrequencies.toSeq.sortBy { case (date, frequency) => -frequency }
  }

  def searchTweets(handle: String, term: String, collection: MongoCollection): (String, Int) = {
      val query = MongoDBObject("handle" -> handle)
  val results = collection.find(query)
  val text = term
  val textClean = text.toLowerCase
  var tweets = new ListBuffer[String]
  for (res <- results) {
    val resultString = res.getAs[String]("content").mkString.toLowerCase
    tweets += resultString

  }
  var c = 0
  val tweetString = tweets.mkString(" ")
  val wordList: Array[String] = tweetString.split(" ")
  for (word <- wordList){
    if (word.equals(text)){
      c += 1
    }
  }
  return (text, c)  
  }
  

  def impact(handle: String, collection: MongoCollection): ((Int, Int),Float) = {
      val query = MongoDBObject("handle" -> handle)
  val results = collection.find(query)
  var favoriteCount: Int = 0
  var retweetCount: Int = 0
  def toInt(s: String): Int = {
  try {
    s.toInt
  } catch {
    case e: Exception => 0
  }
}
  var counter = 0
  for (res <- results) {
    val favoriteInt = res.getAs[Int]("favorites").getOrElse(0)
    favoriteCount += favoriteInt
    val retweetString = res.getAs[Int]("retweets").map(_.toString).getOrElse("42")
    val retweetInt = toInt(retweetString)
    retweetCount += retweetInt 
    //val retweetInt= res.getAs[Int]("retweets").getOrElse(0)
    counter +=1
    //retweetCount += retweetInt
  
    
  }
  val index = (favoriteCount + retweetCount)/ counter
  return ((favoriteCount, retweetCount), index)
  }
}