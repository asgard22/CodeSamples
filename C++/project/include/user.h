#ifndef USER_H
#define USER_H

//C++
#include <string>
//my data structures
#include "set.h"
#include <vector>

#include "datetime.h"

/* Forward Declaration to avoid #include dependencies */
class Tweet;

class User {
 public:

  /*
  Default Constructor
  */
  User();

  /**
   * Constructor 
   */
  User(std::string name);

  /**
   * Destructor
   */
  ~User();

  /**
   * Gets the name of the user 
   * 
   * @return name of the user 
   */
  std::string name();

  /**
   * Gets all the followers of this user  
   * 
   * @return Set of Users who follow this user
   */
  Set<User*> followers();

  /**
   * Gets all the users whom this user follows  
   * 
   * @return Set of Users whom this user follows
   */
  Set<User*> following();

  /**
   * Gets all the tweets this user has posted
   * 
   * @return List of tweets this user has posted
   */
  vector<Tweet*> tweets();

  /**
   * Adds a follower to this users set of followers
   * 
   * @param u User to add as a follower
   */
  void addFollower(User* u);

  /**
   * Adds another user to the set whom this User follows
   * 
   * @param u User that the user will now follow
   */
  void addFollowing(User* u);

  /**
   * Adds the given tweet as a post from this user
   * 
   * @param t new Tweet posted by this user
   */
  void addTweet(Tweet* t);

  /**
   * Produces the list of Tweets that represent this users feed/timeline
   *  It should contain in timestamp order all the tweets from
   *  this user and all the tweets from all the users whom this user follows
   *
   * @return vector of pointers to all the tweets from this user
   *         and those they follow in timestamp order
   */

  vector<Tweet*> getMainFeed();

  //produces a list of tweets that that mention this user, sorted in timestamp order from 
  //least recent to most recent

  vector<Tweet*> getMentionsFeed();

  private:

    std::string username;
    Set<User*> uFollowing;
    Set<User*> uFollowers;
    vector<Tweet*> uTweets;

};

 
#endif