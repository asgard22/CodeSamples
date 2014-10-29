#ifndef TWITTERENGINE_H
#define TWITTERENGINE_H

//C++ Libraries 
#include <stdexcept>
#include <sstream>
#include <fstream>


//Array List class
#include <vector>

//classes composing other parts of twitter
#include "user.h"
#include "tweet.h"

class twitterEngine {

	public:

		twitterEngine();
		~twitterEngine();

		//member functions that get twitter content

		////I/O, reads lines of file, returns list of lines in file
		void getLines(int, char**);
		//creates user objects for all users listed in file passed to the function, returns list of pointers to each object in users
		vector<User*> getUsers();
		//modifies user objects to match all users with those they follow, according to the information stored in the input file
		void getFollowing();
		//modifies user objects to match  a user with their tweets, according to the information stored in the input file
		void getTweets();
		//creates .feed output files with user name at top, their tweets and the tweets of those they are following below their name, sorted by datestamp
		void outputMainFeeds();
		//creates .feed output files with user name at top, and tweets where they are mentioned below their name, sorted by datestamp
		void outputMentionsFeeds();

		//member functions that handle display of content

	private: 
		//stores lines of input file
		vector<string> fileLines;
		//stores user objects in twitter
		vector<User*> users;
		//stores which user is currently selected in combo box
};

#endif