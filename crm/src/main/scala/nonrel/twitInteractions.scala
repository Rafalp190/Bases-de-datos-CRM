package nonrel
import com.danielasfregola.twitter4s.TwitterRestClient
import com.danielasfregola.twitter4s.entities.{HashTag, Tweet}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import org.mongodb.scala._



/**
 * @author Rafa
 *Class that contains all the twitter interaction methods
 */
class tweetInteractions{
 
 /**
 * Basic Test function, makes a tweet to check if connected
 * @param message
 */
  def tweet(message: String): Unit = {
    val client = TwitterRestClient()
    client.createTweet(message)
  }
  /**
 * @param handle The username of the Client to look for
 * @param collection The MongoDB Collection object
 * Fetches the tweets for a user, Parses their contents and Stores them in a MongoDB Collection
 * -Fetches tweets using RestAPI
 * -Parses into Array
 * -Recursively Inserts into MongoDB
 * Validations:
 * -Must check Tweet isnt already on DB (USING TWEET ID)
 * 
 */

      
    
  
}