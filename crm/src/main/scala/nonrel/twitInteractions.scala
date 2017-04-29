package nonrel
import com.danielasfregola.twitter4s.TwitterRestClient


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
}