#include <QApplication>
#include <QPushButton>
#include <QPlainTextEdit>
#include <QVBoxLayout>
#include <QTabWidget>
#include <QStringList>
#include <QLabel>
#include <QListWidget>
#include <QInputDialog>
#include <QLineEdit>
#include <QDir>
#include <QComboBox>
#include <QSize>

#include <ctime>
#include <algorithm>

using namespace std;

#include "twitterengine.h"
#include "occurancesComparator.h"
#include "datestampComparator.h"
#include "hsort.h"

twitterEngine::twitterEngine()
{}

twitterEngine::~twitterEngine()
{}

void twitterEngine::getLines(int argc, char** argv)
{
	//checks whether user entered correct number of arguments at command line
	if (argc != 2)
	{
		throw invalid_argument("you did not provide enough arguments when running your program.");
	}

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
}



void twitterEngine::getUsers()
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

void twitterEngine::getAllHashtags()
{
	//go through all users in engine
	for(std::vector<User*>::iterator u = users.begin(); u != users.end(); u++)
	{
		User user = **u;
		//get that user's tweets
		vector<Tweet*> userTweets = user.tweets();
		//go through all the tweets of a given user
		for(std::vector<Tweet*>::iterator t = userTweets.begin(); t != userTweets.end(); t++)
		{
			Tweet tweet = **t;
			//get that tweet's hashtags
			std::set<std::string> tweetHashtags = tweet.getHashTags();
			//go through all the hashtags of a given tweet
			for (std::set<std::string>::iterator h = tweetHashtags.begin(); h != tweetHashtags.end(); h++)
			{
				std::string hashtag = *h;
				//if the map HTOfAllTime does not contain the given hashtag already
				if (HTOfAllTime.find(hashtag) == HTOfAllTime.end())
				{
					//add that hashtag to the map
					HTOfAllTime[hashtag];
					//add the tweet containing that hashtag to the list of tweets in the vector value of that hashtag
					HTOfAllTime[hashtag].push_back(*t);
					//add that hashtag to the list of hashtags in the vector that will sort them by number of occurances
					mostPopularHTOfAllTime.push_back(hashtag);
				}
				//if the map HTOfAllTime does contain the given hashtag
				else
				{
					//check whether the vector associated with that hashtag contains the tweet in which this instance of the hashtag was found
					bool hashtagContainsTweet = false;
					for (vector<Tweet*>::iterator i = HTOfAllTime[hashtag].begin(); i != HTOfAllTime[hashtag].end(); i++)
					{
						if (*i == *t)
						{
							hashtagContainsTweet = true;
						}
					}
					//if the vector does not contain the tweet
					if (!hashtagContainsTweet)
					{
						//add the tweet to the vector
						HTOfAllTime[hashtag].push_back(*t);
					}
				}
			}
		}
	}
}

void twitterEngine::getHashtagsFromPast24Hours()
{
	time_t now;
	//subtract a days worth of seconds from time_t
	struct tm *backOneDay;
	now = time(0) - 24*60*60;
	backOneDay = localtime(&now);

	DateTime dayAgo(backOneDay->tm_hour, backOneDay->tm_min, backOneDay->tm_sec, (backOneDay->tm_year + 1900), (backOneDay->tm_mon + 1), backOneDay->tm_mday);

	//go through all users in engine
	for(std::vector<User*>::iterator u = users.begin(); u != users.end(); u++)
	{
		User user = **u;
		//get that user's tweets
		vector<Tweet*> userTweets = user.tweets();
		//go through all the tweets of a given user
		for(std::vector<Tweet*>::iterator t = userTweets.begin(); t != userTweets.end(); t++)
		{
			Tweet tweet = **t;
			//get that tweet's hashtags
			std::set<std::string> tweetHashtags = tweet.getHashTags();
			//go through all the hashtags of a given tweet
			for (std::set<std::string>::iterator h = tweetHashtags.begin(); h != tweetHashtags.end(); h++)
			{
				std::string hashtag = *h;
				//if the map HTOfAllTime does not contain the given hashtag already
				if (HTOfPast24Hours.find(hashtag) == HTOfPast24Hours.end())
				{
					//if the hashtag in question is from a tweet with a datestamp within the last 24 hours
					if (!(dayAgo > tweet.time()))
					{
						//add that hashtag to the map
						HTOfPast24Hours[hashtag];
						//add the tweet containing that hashtag to the list of tweets in the vector value of that hashtag
						HTOfPast24Hours[hashtag].push_back(*t);
						//add that hashtag to the list of hashtags in the vector that will sort them by number of occurances
						mostPopularHTOfPast24Hours.push_back(hashtag);
					}
				}
				//if the map HTOfAllTime does contain the given hashtag
				else
				{
					//check whether the vector associated with that hashtag contains the tweet in which this instance of the hashtag was found
					bool hashtagContainsTweet = false;
					for (vector<Tweet*>::iterator i = HTOfPast24Hours[hashtag].begin(); i != HTOfPast24Hours[hashtag].end(); i++)
					{
						if (*i == *t)
						{
							hashtagContainsTweet = true;
						}
					}
					//if the vector does not contain the tweet
					if (!hashtagContainsTweet)
					{
						//add the tweet to the vector
						HTOfPast24Hours[hashtag].push_back(*t);
					}
				}
			}
		}
	}
}

int twitterEngine::displayWindow(int argc, char** argv) 
{

	QApplication app(argc, argv);
	QWidget *centralWidget = new QWidget;

		QVBoxLayout *windowLayout = new QVBoxLayout();

			QPushButton *exportButton = new QPushButton("Export Twitter Information");

				//connect export button to export feed function
				QObject::connect(
								exportButton, SIGNAL(clicked()), 
								this, SLOT(exportFeeds())
								);

			windowLayout->addWidget(exportButton);

			QPushButton *trendingButton = new QPushButton("View Trending Topics");

				//connect trending button to display of trending topics window function
				QObject::connect(
								trendingButton, SIGNAL(clicked()), 
								this, SLOT(displayTrendingWindow())
								);

			windowLayout->addWidget(trendingButton);

			QLabel *userSelectLabel = new QLabel("Choose a user: ");

			windowLayout->addWidget(userSelectLabel);

			usernameSelection = new QComboBox;

				for (unsigned int i = 0; i < users.size(); i++)
				{
					QString name = QString(users.at(i)->name().c_str());
					usernameSelection->addItem(name);
				}

				comboUser = users.at(0);

				//connect selection of user to changing comboUser
				QObject::connect(
								usernameSelection, SIGNAL(currentIndexChanged(int)),
								this, SLOT(updateComboUser())
								);

			windowLayout->addWidget(usernameSelection);

			sectionTabs = new QTabWidget;

				QWidget *meTab = new QWidget();

					QVBoxLayout *meLayout = new QVBoxLayout;

						QHBoxLayout *tweetLayout = new QHBoxLayout;

							tweetEntryBox = new QPlainTextEdit("Write your tweet here!");

							tweetLayout->addWidget(tweetEntryBox);

							QPushButton *tweetPublishButton = new QPushButton("Tweet!");

								//connect tweet publish button to adding a tweet to the comboUser
								QObject::connect(
												tweetPublishButton, SIGNAL(clicked()),
												this, SLOT(addTweetToComboUser())
												);

							tweetLayout->addWidget(tweetPublishButton);

						meLayout->addLayout(tweetLayout);

						meLayout->addSpacing(20);

					meTab->setLayout(meLayout);

				sectionTabs->addTab(meTab, QString::fromStdString("Tweet as " + comboUser->name()));

				QWidget *feedsTab = new QWidget();

					QTabWidget *feedTabs = new QTabWidget (feedsTab);

						QWidget *mainFeedTab = new QWidget();

							QHBoxLayout *mainFeedLayout = new QHBoxLayout();

								mainFeedDisplay = new QListWidget();

								mainFeedLayout->addWidget(mainFeedDisplay);

							mainFeedTab->setLayout(mainFeedLayout);

						feedTabs->addTab(mainFeedTab, "Main Feed");

						QWidget *mentionsFeedTab = new QWidget();

							QHBoxLayout *mentionsFeedLayout = new QHBoxLayout();

								mentionsFeedDisplay = new QListWidget();

								mentionsFeedLayout->addWidget(mentionsFeedDisplay);

							mentionsFeedTab->setLayout(mentionsFeedLayout);

						feedTabs->addTab(mentionsFeedTab, "Mentions Feed");

				sectionTabs->addTab(feedsTab, QString::fromStdString(comboUser->name() + "'s Feeds"));

		windowLayout->addWidget(sectionTabs);

	this->updateMainFeedDisplay();
	this->updateMentionsFeedDisplay();

	mainFeedDisplay->setMinimumSize(450,300);
	mentionsFeedDisplay->setMinimumSize(450, 300);

	centralWidget->setMaximumSize(500, 525);
	centralWidget->setMinimumSize(500, 525);

	centralWidget->setLayout(windowLayout);
	centralWidget->show();

	trendingTopicsWindow = new QWidget;

		QVBoxLayout *trendingLayout = new QVBoxLayout();

		QPushButton *trendingRefreshButton = new QPushButton("Refresh Trending Hashtag Information");

				//connect trending button to display of trending topics window function
				QObject::connect(
								trendingRefreshButton, SIGNAL(clicked()), 
								this, SLOT(refreshTrendingWindow())
								);

		trendingLayout->addWidget(trendingRefreshButton);	

			QTabWidget* trendingTabs = new QTabWidget();

				QWidget *allTimeTab = new QWidget();

					QVBoxLayout *allTimeLayout = new QVBoxLayout();

						allTimeHTDisplay = new QListWidget();

						this->updateAllTimeHTDisplay();

						allTimeLayout->addWidget(allTimeHTDisplay);

						QWidget* allTimeTweetsContainingLayoutPlatform = new QWidget();

							QHBoxLayout* allTimeTweetsContainingLayout = new QHBoxLayout();

								QLabel *allTimeTweetDisplayLabel = new QLabel("                                        Show Tweets Below That Contain");

								allTimeTweetsContainingLayout->addWidget(allTimeTweetDisplayLabel);

								allTimeHTSelection = new QComboBox;

									for (std::vector<string>::iterator h = mostPopularHTOfAllTime.begin(); h != mostPopularHTOfAllTime.end(); h++)
									{
										std::string HT = *h;
										QString hashtag = QString(HT.c_str());
										allTimeHTSelection->addItem(hashtag);
									}

									if (mostPopularHTOfAllTime.size() > 0)
									{
										comboAllTimeHT = mostPopularHTOfAllTime.at(0);
									}

									//connect selection of user to changing comboUser
									QObject::connect(
													allTimeHTSelection, SIGNAL(currentIndexChanged(int)),
													this, SLOT(updateComboAllTimeHT())
													);

								allTimeTweetsContainingLayout->addWidget(allTimeHTSelection);

							allTimeTweetsContainingLayoutPlatform->setLayout(allTimeTweetsContainingLayout);

						allTimeLayout->addWidget(allTimeTweetsContainingLayoutPlatform);

						allTimeTweetDisplay = new QListWidget();

						this->updateAllTimeHTTweetsDisplay();

						allTimeLayout->addWidget(allTimeTweetDisplay);

					allTimeTab->setLayout(allTimeLayout);

				trendingTabs->addTab(allTimeTab, QString::fromStdString("Most Popular Hashtags of All Time"));

				QWidget *allDayTab = new QWidget();

					QVBoxLayout *allDayLayout = new QVBoxLayout();

						allDayHTDisplay = new QListWidget();

						this->updateAllDayHTDisplay();

						allDayLayout->addWidget(allDayHTDisplay);

						QWidget* allDayTweetsContainingLayoutPlatform = new QWidget();

							QHBoxLayout* allDayTweetsContainingLayout = new QHBoxLayout();

								QLabel *allDayTweetDisplayLabel = new QLabel("                                        Show Tweets Below That Contain");

								allDayTweetsContainingLayout->addWidget(allDayTweetDisplayLabel);

								allDayHTSelection = new QComboBox;

									for (std::vector<string>::iterator h = mostPopularHTOfPast24Hours.begin(); h != mostPopularHTOfPast24Hours.end(); h++)
									{
										std::string HT = *h;
										QString hashtag = QString(HT.c_str());
										allDayHTSelection->addItem(hashtag);
									}

									if (mostPopularHTOfPast24Hours.size() > 0)
									{
										comboAllDayHT = mostPopularHTOfPast24Hours.at(0);
									}

									//connect selection of user to changing comboUser
									QObject::connect(
													allDayHTSelection, SIGNAL(currentIndexChanged(int)),
													this, SLOT(updateComboAllDayHT())
													);

								allDayTweetsContainingLayout->addWidget(allDayHTSelection);

							allDayTweetsContainingLayoutPlatform->setLayout(allDayTweetsContainingLayout);

						allDayLayout->addWidget(allDayTweetsContainingLayoutPlatform);

						allDayTweetDisplay = new QListWidget();

						this->updateAllDayHTTweetsDisplay();

						allDayLayout->addWidget(allDayTweetDisplay);

					allDayTab->setLayout(allDayLayout);

				trendingTabs->addTab(allDayTab, QString::fromStdString("Most Popular Hashtags of Past 24 Hours"));

			trendingLayout->addWidget(trendingTabs);

		trendingTopicsWindow->setLayout(trendingLayout);

	trendingTopicsWindow->setMaximumSize(600, 500);
	trendingTopicsWindow->setMinimumSize(600, 500);

	return app.exec();
}

//export feed function
void twitterEngine::exportFeeds()
{
	bool ok;
	//choose name of file 
     QString Qname = QInputDialog::getText(0, tr("QInputDialog::getText()"), tr("Enter File Name (without file extension):"), QLineEdit::Normal, QDir::home().dirName(), &ok);
     Qname += ".info";
     string fileName = Qname.toStdString();
     ofstream ofile(fileName.c_str());

     //outputs number of users
     ofile << users.size() << endl;

     //outputs user names and those they follow 
     for (unsigned int i = 0; i < users.size(); i++)
     {
     	User* user = users.at(i);
     	string username = user->name();
     	ofile << username << " ";
     	set<User*> userFollowing = user->following();
     	for (set<User*>::iterator u = userFollowing.begin(); u != userFollowing.end(); u++)
     	{
     		ofile << (*u)->name() << " ";
     	}
   		ofile << endl;
     }

     //outputs user
     for (unsigned int i = 0; i < users.size(); ++i)
     {
     	User* user = users.at(i);
     	vector<Tweet*> tweets = user->tweets();
     	for (vector<Tweet*>::iterator t = tweets.begin(); t != tweets.end(); t++)
     	{
     		ofile << (*t)->time() << " ";
     		ofile << (*t)->getUser()->name() << " ";
     		ofile << (*t)->text() << " ";
     		ofile << endl;
     	}
     }
}

void twitterEngine::displayTrendingWindow()
{

	trendingTopicsWindow->show();
}

void twitterEngine::refreshTrendingWindow()
{
	this->getAllHashtags();
	this->getHashtagsFromPast24Hours();
	this->updateAllTimeHTDisplay();
	this->updateAllDayHTDisplay();
	this->updateComboAllTimeHTContents();
	this->updateComboAllDayHTContents();
	this->updateAllTimeHTTweetsDisplay();
	this->updateAllDayHTTweetsDisplay();
}

void twitterEngine::updateAllTimeHTDisplay()
{
	while(allTimeHTDisplay->count() > 0)
	{
		allTimeHTDisplay->takeItem(0);
	}

	if (mostPopularHTOfAllTime.size() == 0)
	{
		mainFeedDisplay->addItem("No hashtags have been used in any tweets.");
	}

	//sort mostPopularHTOfAllTime
	occurancesComparator comp(HTOfAllTime);
 	heapSort(mostPopularHTOfAllTime, comp);

	for (std::vector<string>::iterator h = mostPopularHTOfAllTime.begin(); h != mostPopularHTOfAllTime.end(); h++)
	{
		string hashtag = *h;
		int numTweetsINT = HTOfAllTime[hashtag].size();
		string numTweetsSTR;
		stringstream ss; 
		ss << numTweetsINT;
		ss >> numTweetsSTR;
		string hashtagPlusOccurances;
		if (numTweetsINT > 1)
		{
			hashtagPlusOccurances = hashtag + " occurred in " + numTweetsSTR + " tweets.";
		}
		else
		{
			hashtagPlusOccurances = hashtag + " occurred in " + numTweetsSTR + " tweet.";	
		}
		QString hashtagInfo = QString::fromStdString(hashtagPlusOccurances);
		allTimeHTDisplay->addItem(hashtagInfo);
	}
}

void twitterEngine::updateAllDayHTDisplay()
{
	while(allDayHTDisplay->count() > 0)
	{
		allDayHTDisplay->takeItem(0);
	}

	if (mostPopularHTOfPast24Hours.size() == 0)
	{
		allDayHTDisplay->addItem("No hashtags have been used in the past 24 hours.");
	}

	//sort mostPopularHTOfPast24Hours
	occurancesComparator comp(HTOfPast24Hours);
	heapSort(mostPopularHTOfPast24Hours, comp);

	for (vector<string>::iterator h = mostPopularHTOfPast24Hours.begin(); h != mostPopularHTOfPast24Hours.end(); h++)
	{
		string hashtag = *h;
		int numTweetsINT = HTOfPast24Hours[hashtag].size();
		string numTweetsSTR;
		stringstream ss; 
		ss << numTweetsINT;
		ss >> numTweetsSTR;
		string hashtagPlusOccurances = "";
		if (numTweetsINT > 1)
		{
			hashtagPlusOccurances = hashtag + " occurred in " + numTweetsSTR + " tweets.";
		}
		else
		{
			hashtagPlusOccurances = hashtag + " occurred in " + numTweetsSTR + " tweet.";	
		}
		QString hashtagInfo = QString::fromStdString(hashtagPlusOccurances);
		allDayHTDisplay->addItem(hashtagInfo);
	}
}

//changing comboUser
void twitterEngine::updateComboUser()
{
	for (vector<User*>::iterator u = users.begin(); u != users.end(); u++)
	{
		string username = (*u)->name();
		if (username == (usernameSelection->currentText()).toStdString())
		{
			comboUser = *u;
			break;
		}
	}

	sectionTabs->setTabText(0, QString::fromStdString("Tweet as " + comboUser->name()));
	sectionTabs->setTabText(1, QString::fromStdString(comboUser->name() + "'s Feeds"));
	this->updateMainFeedDisplay();
	this->updateMentionsFeedDisplay();
}

void twitterEngine::updateComboAllTimeHT()
{
	for (vector<string>::iterator h = mostPopularHTOfAllTime.begin(); h != mostPopularHTOfAllTime.end(); h++)
	{
		string hashtag = *h;
		if (hashtag == (allTimeHTSelection->currentText()).toStdString())
		{
			comboAllTimeHT = hashtag;
		}
	}

	this->updateAllTimeHTTweetsDisplay();
}

void twitterEngine::updateComboAllTimeHTContents()
{
	allTimeHTSelection->clear();

	for (vector<string>::iterator h = mostPopularHTOfAllTime.begin(); h!= mostPopularHTOfAllTime.end(); h++)
	{
		std::string HT = *h;
		QString hashtag = QString(HT.c_str());
		allTimeHTSelection->addItem(hashtag);
	}
}

void twitterEngine::updateComboAllDayHT()
{
	for (vector<string>::iterator h = mostPopularHTOfPast24Hours.begin(); h != mostPopularHTOfPast24Hours.end(); h++)
	{
		string hashtag = *h;
		if (hashtag == (allDayHTSelection->currentText()).toStdString())
		{
			comboAllDayHT = hashtag;
		}
	}

	this->updateAllDayHTTweetsDisplay();
}

void twitterEngine::updateComboAllDayHTContents()
{
	allDayHTSelection->clear();

	for (vector<string>::iterator h = mostPopularHTOfPast24Hours.begin(); h!= mostPopularHTOfPast24Hours.end(); h++)
	{
		std::string HT = *h;
		QString hashtag = QString(HT.c_str());
		allDayHTSelection->addItem(hashtag);
	}
}

//adding a tweet to the comboUser
void twitterEngine::addTweetToComboUser()
{
	//gets tweet text in box
	string text = tweetEntryBox->toPlainText().toStdString();

	//gets date of tweet
	time_t now;
	struct tm *current;
	now = time(0);
	current = localtime(&now);
	DateTime dt(current->tm_hour, current->tm_min, current->tm_sec, (current->tm_year + 1900), (current->tm_mon + 1), current->tm_mday);

	//initializes tweet
	Tweet* t = new Tweet(comboUser, dt, text);

	//adds tweet to combo user
	comboUser->addTweet(t);

	this->updateMainFeedDisplay();
	this->updateMentionsFeedDisplay();
}

//update main feed display with current main feeds
void twitterEngine::updateMainFeedDisplay()
{
	while(mainFeedDisplay->count() > 0)
	{
		mainFeedDisplay->takeItem(0);
	}

	vector<Tweet*> feed = (comboUser)->getMainFeed();
	if (feed.size() == 0)
	{
		mainFeedDisplay->addItem("No tweets available for this user");
	}
	for (vector<Tweet*>::iterator t = feed.begin(); t != feed.end(); t++)
	{
		stringstream ss;
		string tweetTime;
		string tweetTime1;
		string tweetTime2;
		ss << (*t)->time();
		ss >> tweetTime1;
		ss >> tweetTime2;
		string tweetFeedContent = (*t)->getUser()->name() + " tweeted -- " + (*t)->text() + " -- at " + tweetTime2 + " on " + tweetTime1;
		QString content = QString::fromStdString(tweetFeedContent);
		mainFeedDisplay->addItem(content);
	}

}

//update mentions feed display with current mentions feed
void twitterEngine::updateMentionsFeedDisplay()
{
	while(mentionsFeedDisplay->count() > 0)
	{
		mentionsFeedDisplay->takeItem(0);
	}

	vector<Tweet*> feed = (comboUser)->getMentionsFeed();
	if (feed.size() == 0)
	{
		mentionsFeedDisplay->addItem("No mentions available for this user");
	}
	for (vector<Tweet*>::iterator t = feed.begin(); t != feed.end(); t++)
	{
		stringstream ss;
		string tweetTime;
		string tweetTime1;
		string tweetTime2;
		ss << (*t)->time();
		ss >> tweetTime1;
		ss >> tweetTime2;
		tweetTime = tweetTime1 + ' ' + tweetTime2;
		string tweetFeedContent = (*t)->getUser()->name() + " tweeted -- " + (*t)->text() + " -- at " + tweetTime2 + " on " + tweetTime1;
		QString content = QString::fromStdString(tweetFeedContent);
		mentionsFeedDisplay->addItem(content);
	}
}

void twitterEngine::updateAllTimeHTTweetsDisplay()
{
	while(allTimeTweetDisplay->count() > 0)
	{
		allTimeTweetDisplay->takeItem(0);
	}

	//sort HTOfAllTime[comboAllTimeHT]
	datestampComparator comp;
	heapSort(HTOfAllTime[comboAllTimeHT], comp);

	while(HTOfAllTime[comboAllTimeHT].size() > 10)
	{
		HTOfAllTime[comboAllTimeHT].pop_back();
	}

	vector<Tweet*> tweets = HTOfAllTime[comboAllTimeHT];
	for (vector<Tweet*>::iterator t = tweets.begin(); t != tweets.end(); t++)
	{
		Tweet* tweet = *t;
		stringstream ss;
		string tweetTime;
		string tweetTime1;
		string tweetTime2;
		ss << (tweet)->time();
		ss >> tweetTime1;
		ss >> tweetTime2;
		tweetTime = tweetTime1 + ' ' + tweetTime2;
		string hashtagTweetContent = (tweet)->getUser()->name() + " tweeted -- " + (tweet)->text() + " -- at " + tweetTime2 + " on " + tweetTime1;
		QString content = QString::fromStdString(hashtagTweetContent);
		allTimeTweetDisplay->addItem(content);
	}
}

void twitterEngine::updateAllDayHTTweetsDisplay()
{
	while(allDayTweetDisplay->count() > 0)
	{
		allDayTweetDisplay->takeItem(0);
	}

	//sort HTOfPast24Hours[comboAllDayHT]
	datestampComparator comp;
	heapSort(HTOfPast24Hours[comboAllDayHT], comp);

	while(HTOfPast24Hours[comboAllDayHT].size() > 10)
	{
		HTOfPast24Hours[comboAllDayHT].pop_back();
	}

	vector<Tweet*> tweets = HTOfPast24Hours[comboAllDayHT];
	for (vector<Tweet*>::iterator t = tweets.begin(); t != tweets.end(); t++)
	{
		Tweet* tweet = *t;
		stringstream ss;
		string tweetTime;
		string tweetTime1;
		string tweetTime2;
		ss << (tweet)->time();
		ss >> tweetTime1;
		ss >> tweetTime2;
		tweetTime = tweetTime1 + ' ' + tweetTime2;
		string hashtagTweetContent = (tweet)->getUser()->name() + " tweeted -- " + (tweet)->text() + " -- at " + tweetTime2 + " on " + tweetTime1;
		QString content = QString::fromStdString(hashtagTweetContent);
		allDayTweetDisplay->addItem(content);
	}
}