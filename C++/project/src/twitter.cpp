//C++ Libraries 
#include <stdexcept>
#include <sstream>
#include <fstream>
#include <iostream>

//Array List class
#include <vector>

//classes composing other parts of twitter
#include "twitterengine.h"
#include "user.h"
#include "tweet.h"

using namespace std;

//Generates feeds for each user, in the form of output plaintext files.
int main(int argc, char** argv)
{
	twitterEngine engine;

	//I/O, reads lines of file, places each line into fileLines
	engine.getLines(argc, argv);

	//creates user objects for all users listed in file, stores pointers to each object in users
	engine.getUsers();

	//matches a user with those they follow, according to the information stored in the input file
	engine.getFollowing();

	//matches a user with their tweets, according to the information stored in the input file
	engine.getTweets();

	//creates .feed output files with user name at top, their tweets and the tweets of those they are following below their name, sorted by datestamp
	engine.outputMainFeeds();

	engine.outputMentionsFeeds();

	engine.getAllHashtags();

	engine.getHashtagsFromPast24Hours();

	return engine.displayWindow(argc, argv);
}
