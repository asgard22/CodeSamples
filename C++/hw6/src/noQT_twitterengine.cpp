#include "noQT_twitterengine.h"

twitterEngine::twitterEngine()
{}

twitterEngine::~twitterEngine()
{}

void twitterEngine::getLines(int argc, char** argv)
{
	//tries to open file
	char* fileName;
	fileName = argv[1];
	ifstream ifile;
	ifile.open(fileName);
	if(ifile.fail())
	{
		throw invalid_argument("no file could be found whose name matches the name you provided.");
	}

	//reads file line by line
	string fileLine;
	int numLines = 0;
	while(getline(ifile, fileLine))
	{
		numLines++;
	}
	ifile.close();
	ifile.open(fileName);
	fileLine = "";
	for (int line = 0; line < numLines; ++line)
	{
		getline(ifile, fileLine);
		fileLines.push_back(fileLine);
	}
	if (fileLines.size() < 3)
	{
		throw invalid_argument("your file is inproperly formatted.");
	}
	ifile.close();
}



vector<User*> twitterEngine::getUsers()
{
	
	////gets how many users are listed in file

	//creates storage necessary for getting and storing number of users
	int numUsers  = 0;
	stringstream ss;
	//puts first line of file into stringstream
	ss << fileLines.at(0);
	//puts first line of file into numUsers (as int)
	ss >> numUsers;
	//checks that file actually lists the number of users (if the first line is not a number, this will be a problem)
	if (ss.fail())
	{
		throw invalid_argument("your file is not formatted properly.");
	}
	ss.clear();

	////creates storage necessary for all operations in the function

	//creates storage space for all user names
	vector<string> usernames;
	//creates storage space for each individual user name after it is read out of the file
	string name = "";

	////gets usernames from the file

	//iterate through the lines with user names at the beginning of the file
	for (int i = 1; i <= numUsers; ++i)
	{
		string line;
		stringstream ss2;
		string lineWord;
		//gets the ith line of the file
		line = fileLines.at(i);
		//puts this file into stringstream
		ss2 << line;
		//takes out the first word (the username), and puts this in lineWord
		ss2 >> lineWord;
		//puts the username into the usernames list
		usernames.push_back(lineWord);
	}

	//initializes user objects

	//loops once for each user
	for (int i = 0; i < numUsers; ++i)
	{
		//stores each username 
		name = usernames.at(i);
		//dynamically allocates a new user object 
		User* user = new User(name);
		//puts the pointer to the user object into the users list.
		users.push_back(user);
	}

	return users;
}



void twitterEngine::getFollowing()
{

	//creates storage necessary to get and store all the users a user is following
	string line;
	string lineWord;
	stringstream ss;
	User* user;
	User* thisUser;
	string username;

	//iterates through the lines of the file on which users and those they follow are listed
	for (unsigned int i = 1; i <= users.size(); ++i)
	{
		vector<string> fileLine;
		//store each line of the file
		line = fileLines.at(i);
		ss << line;
		//separate a given line into words, and make an list of those words called fileLine
		while (ss >> lineWord)
		{
			fileLine.push_back(lineWord);
			//empties lineWord for the next use
			lineWord.clear();
		}

		//get the first word of a given line, which is the name of a user
		username = fileLine.at(0);

		//find which user object has that name

		//looks through all the user objects
		for (unsigned int j = 0; j < users.size(); ++j)
		{
			//stores a pointer to a given user object
			thisUser = users.at(j);
			//checks whether the name of that user object matches first word of the file line
			if (thisUser->name() == username)
			{
				//if there is a match, store the pointer to that user
				user = thisUser;
				//and stop looking through the users
				break;
			}
			//otherwise, continue looking through all the user objects
		}

		//for the remaining words on the line (which are the users that the first listed user follows)
		for (unsigned int j = 1; j < fileLine.size(); ++j)
		{
			//store the name of a given user
			username = fileLine.at(j);

			//find which user object has that name

			//look through all user objects
			for (unsigned int k = 0; k < users.size(); ++k)
			{
				//store a pointer to a given user
				thisUser = users.at(k);
				//check whether the name of the given user matches the name listed on the file line
				if (thisUser->name() == username)
				{
					//if there is a match, add that user to the followers of the user listed at the beginning of the file line
					user->addFollowing(thisUser);
					//stop looking
					break;
				}
				//otherwise, continue looking through all the user objects
			}
		}

		//empties stringstream for next use
		ss.clear();
    }
}



void twitterEngine::getTweets()
{
	//creates storage necessary to get and store all tweets of a given user
	vector<string> fileLine;
	string line;
	string lineWord;
	stringstream ss;
	User* user;
	User* thisUser;

	//loops once for each line in the file where tweets are listed
	for (unsigned int i = users.size()+1; i < fileLines.size(); ++i)
	{

		vector<string> fileLine;
		////splits up the line into a list of words in the line

		//puts all the words on a line into a list with each word as a separate element
		line = fileLines.at(i);
		ss << line;
		while (ss >> lineWord)
		{
			fileLine.push_back(lineWord);
			//empties lineword for next use
			lineWord.clear();
		}

		////gets a pointer to the user object whose tweet is in the line

		//get name of person whose tweet is in that line
		string username = fileLine.at(2);
		//go through users until you've found the user whose tweet is in the line
		for (unsigned int j = 0; j < users.size(); ++j)
		{
			//stores a pointer to a given user
			thisUser = users.at(j);
			//checks whether the name of the given user object matches the user whose tweet is in the line
			if (thisUser->name() == username)
			{
				//if there is a match, point to that user object
				user = thisUser;
				//stop looking
				break;
			}
			//otherwise, continue looking
		}
		
		////creates datestamp

		//splits and stores the datestamp part of the tweet into two components: YYYY-MM-DD and hh:mm:ss
		string dateComponents[2] = {fileLine.at(0), fileLine.at(1)};

		//takes the first component (YYYY-MM-DD) and puts it in a stringstream
		stringstream chunk1(dateComponents[0]);
		string chunk;
		vector<string> parts;

		//splits the first component of the datestamp into parts (YYYY, MM, DD) and puts them into a list called parts
		while(getline(chunk1, chunk, '-'))
		{
			parts.push_back(chunk);
		}

		//splits and stores the datestamp part of the tweet into parts (hh, mm, ss) and puts them into a list called parts
		stringstream chunks2(dateComponents[1]);
		while(getline(chunks2, chunk, ':'))
		{
			parts.push_back(chunk);
		}

		//stores YYYY part of date stamp
		int year;
		stringstream timeParts;
		timeParts << parts.at(0);
		timeParts >> year;
		timeParts.clear();

		//stores MM part of date stamp
		int month;
		timeParts << parts.at(1);
		timeParts >> month;
		timeParts.clear();

		//stores DD part of date stamp
		int day;
		timeParts << parts.at(2);
		timeParts >> day;
		timeParts.clear();

		//stores hh part of date stamp
		int hour;
		timeParts << parts.at(3);
		timeParts >> hour;
		timeParts.clear();

		//stores mm part of date stamp
		int minute;
		timeParts << parts.at(4);
		timeParts >> minute;
		timeParts.clear();

		//stores ss part of date stamp
		int second;
		timeParts << parts.at(5);
		timeParts >> second;
		timeParts.clear();

		//creates datestamp for tweet
		DateTime dt(hour, minute, second, year, month, day);

		////gets tweet content

		//creates storage for getting tweet content
		stringstream tweetWord;
		string word;
		string tweetText; 
		//goes through every word in line composing part of the tweet's content
		for (unsigned int k = 3; k < fileLine.size(); ++k)
		{
			//gets a word from the line
			tweetWord << fileLine.at(k);
			//stores the word
			tweetWord >> word;
			//adds the word to the tweet with a space between words
			tweetText += ' ';
			tweetText += word;
			//empties the storage for the word for next use
			tweetWord.clear();
		}

		//dynamically allocates new tweet
		Tweet* t = new Tweet(user, dt, tweetText);
		//adds tweet to user's list of tweets
		user->addTweet(t);
		//empties fileLine for next use
		for (unsigned int i = 0; i < fileLine.size(); ++i)
		{
			fileLine.erase(fileLine.begin());
		}

		//empties stringstream for next use
		ss.clear();
	}
}



void twitterEngine::outputMainFeeds()
{
	//loops once for every user
	for (unsigned int i = 0; i < users.size(); ++i)
	{
		//stores a pointer to a given user object
		User* user = users.at(i);
		//makes a name for the output file in the format Name.feed
		string fileName = user->name() + ".feed";
		//creates an output file
		ofstream ofile(fileName.c_str());
		//stores the feeds of a given user as a list of pointers to the tweets of themselves and their followers
		vector<Tweet*> feed = user->getMainFeed();
		//makes the user's name the first line of the file
		ofile << user->name() << endl;
		//for each tweet in the user's feed (sorted from earliest datestamp to latest datestamp)
		for (unsigned int i = 0; i < feed.size(); ++i)
		{
			//stores a pointer to the given tweet
			Tweet* tweet = feed.at(i);
			//starts the next line of the output file with the datetime of the tweet
			ofile << tweet->time();
			//stores a pointer to the user who tweeted
			User* user = tweet->getUser();
			//makes the next part of the line in the output file the name of the user who tweeted
			ofile << " " << user->name();
			//makes the next part of the line in the output file the text of the tweet
			ofile << tweet->text();
			//starts a new line in the file
			ofile << '\n';
		}
	}
}

void twitterEngine::outputMentionsFeeds()
{
	//loops once for every user
	for (unsigned int i = 0; i < users.size(); ++i)
	{
		//stores a pointer to a given user object
		User* user = users.at(i);
		//makes a name for the output file in the format Name.feed
		string fileName = user->name() + ".mentions";
		//creates an output file
		ofstream ofile(fileName.c_str());
		//stores the feeds of a given user as a list of pointers to the tweets of themselves and their followers
		vector<Tweet*> feed = user->getMentionsFeed();
		//makes the user's name the first line of the file
		ofile << user->name() << endl;
		//for each tweet in the user's feed (sorted from earliest datestamp to latest datestamp)
		for (unsigned int i = 0; i < feed.size(); ++i)
		{
			//stores a pointer to the given tweet
			Tweet* tweet = feed.at(i);
			//starts the next line of the output file with the datetime of the tweet
			ofile << tweet->time();
			//stores a pointer to the user who tweeted
			User* user = tweet->getUser();
			//makes the next part of the line in the output file the name of the user who tweeted
			ofile << " " << user->name();
			//makes the next part of the line in the output file the text of the tweet
			ofile << tweet->text();
			//starts a new line in the file
			ofile << '\n';
		}
	}	
}