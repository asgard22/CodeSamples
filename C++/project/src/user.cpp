#include "user.h"
#include "tweet.h"

 User::User()
 {
  username = "temp name";
 }

User::User(std::string name)
{
  username = name;
}

 
User::~User()
{}

 
std::string User::name()
{
  return username;
}

 
Set<User*> User::followers()
{
  return uFollowers;
}

 
Set<User*> User::following()
{
  return uFollowing;
}

 
vector<Tweet*> User::tweets()
{
  return uTweets;
}

 
void User::addFollower(User* u)
{
  uFollowers.insert(u);
}

 
void User::addFollowing(User* u)
{
  uFollowing.insert(u);
}

 
void User::addTweet(Tweet* t)
{
  uTweets.insert(uTweets.begin(), t);
}

vector<Tweet*> User::getMainFeed()
{
  //creates storage to store all the tweets of the user and those they follow
  vector<Tweet*> allTweets;
  User user;
  Tweet* tweet;
  vector<Tweet*> userTweets;
  int numUserTweets;
  //creates storage to sort all the tweets of the user and those they follow by date stamp
  Tweet* highest;
  DateTime highestTime;
  vector<Tweet*>::iterator highestPosition;
  DateTime tweetTime;
  Tweet* highestTweet;
  Tweet* startingTweet;

  //loops once for each tweet of the user
  for (vector<Tweet*>::iterator i = uTweets.begin(); i != uTweets.end(); ++i)
  {
    tweet = *i;
    //add their tweets (pointers) to an array list
    allTweets.push_back(tweet);
  }


  for (set<User*>::iterator i = uFollowing.begin(); i != uFollowing.end(); ++i)
  {
    
    user = **i;

    //gets the tweets of the followed user
    userTweets = user.tweets();
    //gets the number of tweets of the followed user
    numUserTweets = userTweets.size();

    //for each tweet of the followed user
    for (int i = 0; i < numUserTweets; ++i)
    {
      tweet = userTweets.at(i);
      //checks whether the tweet starts with a mention
      string word;
      stringstream ss;
      ss << tweet->text();
      int numWords = 0;
      while(ss >> word)
      {
        numWords++;
      }

      word = "";
      ss.clear();
      ss << tweet->text();

      vector<string> fileLine;
      for (int i = 0; i < numWords; i++)
      {
        ss >> word;
        fileLine.push_back(word);
      }
      //if the first word of the tweet is a mention
      for (int i = 0; i < numWords; i++)
      {
        if (fileLine.at(i)[0] == '@') 
        {
          string mentionedName = fileLine.at(i).substr(1, fileLine.at(i).size());
          //only add that tweet to the user's feed if the mention mentions the user
          if (mentionedName == this->name())
          {
              User* userTweeting = tweet->getUser();
              Set<User*> userTweetingFollowing = userTweeting->following();
              //and the user who mentioned this user follows this user
              if (userTweetingFollowing.find(this) != userTweetingFollowing.end())
              {
                allTweets.push_back(tweet);
              }
          }
        }
        //if the first word of the tweet is not a mention
        else if (i == 0)
        {
          allTweets.push_back(tweet);
          break;
        }
      }
    }
  }

  //sort datestamp of array list
  //selection sort
  if (allTweets.size() > 0)
  {
    //starting index of sublist
    for (vector<Tweet*>::iterator start = allTweets.begin(); start != allTweets.end(); ++start)
    {
      //sets the first tweet of sublist to the earliest datetime, initially
      highest = *start;
      highestTime = highest->time();
      highestPosition = start;
      //go through every tweet in list after first element and check if any have earlier datetimes
      for (vector<Tweet*>::iterator j = start; j != allTweets.end(); ++j)
       {
          //gets the datetime of a given tweet 
          tweet = *j;
          tweetTime = tweet->time();

          //if that datetime is earlier than the current earliest
          if(tweetTime > highestTime)
          {
            //records that tweet as the earliest
            highest = tweet;
            //records that tweet's time as the earliest
            highestTime = highest->time();
            //records that tweet's position
            highestPosition = j;
          }
       }

       //places first tweet in sublist at the position of the tweet with the earliest datestamp
       highestTweet = allTweets.at(distance(allTweets.begin(), highestPosition));
       startingTweet = allTweets.at(distance(allTweets.begin(), start));

       this->name();
       //places the tweet in the sublist at the start in the former position of the lowest tweet
       allTweets.erase(highestPosition);
       allTweets.insert(highestPosition, startingTweet);
       //places the tweet in the sublist with the earliest datestamp at the start of the sublist
       allTweets.erase(start);
       allTweets.insert(start, highestTweet);
    }
  }

  return allTweets;
}

vector<Tweet*> User::getMentionsFeed() {
  //creates storage to store all the tweets of the user and those they follow
  vector<Tweet*> allTweets;
  User user;
  Tweet* tweet;
  vector<Tweet*> userTweets;
  int numUserTweets;
  //creates storage to check for any mentions of a user in a tweet
  string tweetText;
  string mention;
  bool containsMention;
  vector <Tweet*> mentionTweets;
  //creates storage to sort all the tweets of the user and those they follow by date stamp
  Tweet* highest;
  DateTime highestTime;
  vector<Tweet*>::iterator highestPosition;
  DateTime tweetTime;
  Tweet* highestTweet;
  Tweet* startingTweet;

  //loops once for each tweet of the user
  for (vector<Tweet*>::iterator i = uTweets.begin(); i != uTweets.end(); ++i)
  {
    tweet = *i;
    //add their tweets (pointers) to an array list
    allTweets.push_back(tweet);
  }


  for (set<User*>::iterator i = uFollowing.begin(); i != uFollowing.end(); ++i)
  {
    
    user = **i;

    //gets the tweets of the followed user
    userTweets = user.tweets();
    //gets the number of tweets of the followed user
    numUserTweets = userTweets.size();

    //for each tweet of the followed user
    for (int i = 0; i < numUserTweets; ++i)
    {
        tweet = userTweets.at(i);
        //add their tweet to an array list
        allTweets.push_back(tweet);
    }
  }

  //removes all tweets that don't mention the user
  for (vector<Tweet*>::iterator t = allTweets.begin(); t != allTweets.end(); ++t)
  {
    mention = '@' + ((*allTweets.begin())->getUser()->name());
    //get text of tweet
    tweetText = (**t).text();
    //search for substring of mention of user
    if (tweetText.find(mention) != std::string::npos)
    {
      containsMention = true;
    }
    else
    {
      containsMention = false;
    }
    //erase tweet if it does not contain a mention of the user
    if (containsMention)
    {
      mentionTweets.insert(mentionTweets.begin(), *t);
    }
  }


  //sort datestamp of array list
  //selection sort
  if (mentionTweets.size() > 0)
  {
    //starting index of sublist
    for (vector<Tweet*>::iterator start = mentionTweets.begin(); start != mentionTweets.end(); ++start)
    {
      //sets the first tweet of sublist to the earliest datetime, initially
      highest = *start;
      highestTime = highest->time();
      highestPosition = start;
      //go through every tweet in list after first element and check if any have earlier datetimes
      for (vector<Tweet*>::iterator j = start; j != mentionTweets.end(); ++j)
       {
          //gets the datetime of a given tweet 
          tweet = *j;
          tweetTime = tweet->time();

          //if that datetime is earlier than the current earliest
          if(tweetTime > highestTime)
          {
            //records that tweet as the earliest
            highest = tweet;
            //records that tweet's time as the earliest
            highestTime = highest->time();
            //records that tweet's position
            highestPosition = j;
          }
       }

       //places first tweet in sublist at the position of the tweet with the earliest datestamp
       highestTweet = mentionTweets.at(distance(mentionTweets.begin(), highestPosition));
       startingTweet = mentionTweets.at(distance(mentionTweets.begin(), start));

       this->name();
       //places the tweet in the sublist at the start in the former position of the lowest tweet
       mentionTweets.erase(highestPosition);
       mentionTweets.insert(highestPosition, startingTweet);
       //places the tweet in the sublist with the earliest datestamp at the start of the sublist
       mentionTweets.erase(start);
       mentionTweets.insert(start, highestTweet);
    }
  }

  return mentionTweets;
}
