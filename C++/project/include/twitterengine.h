#ifndef TWITTERENGINE_H
#define TWITTERENGINE_H

//C++ Libraries 
#include <stdexcept>
#include <sstream>
#include <fstream>

//Qt
#include <QObject>
#include <QComboBox>
#include <QPlainTextEdit>
#include <QListWidget>

//Array List class
#include <vector>

//classes composing other parts of twitter
#include "user.h"
#include "tweet.h"

class twitterEngine : public QObject {

	Q_OBJECT

	public:

		twitterEngine();
		~twitterEngine();

	//member functions that get twitter content

		////I/O, reads lines of file, returns list of lines in file
		void getLines(int, char**);
		//creates user objects for all users listed in file passed to the function, returns list of pointers to each object in users
		void getUsers();
		//modifies user objects to match all users with those they follow, according to the information stored in the input file
		void getFollowing();
		//modifies user objects to match  a user with their tweets, according to the information stored in the input file
		void getTweets();
		//creates .feed output files with user name at top, their tweets and the tweets of those they are following below their name, sorted by datestamp
		void outputMainFeeds();
		//creates .feed output files with user name at top, and tweets where they are mentioned below their name, sorted by datestamp
		void outputMentionsFeeds();
		//stores all hashtags from all tweets in engine in HTOfAllTime map
		void getAllHashtags();
		//stores all hashtags from all tweets in past 24 hours in engine in HTOfPast24Hours map
		void getHashtagsFromPast24Hours();

	//member functions that handle display of content

	public slots:

		int displayWindow(int, char**);

		void displayTrendingWindow();

		void refreshTrendingWindow();

		void updateAllTimeHTDisplay();

		void updateAllDayHTDisplay();

		void exportFeeds();

		//changing comboUser
		void updateComboUser();

		//changing comboAllTimeHT
		void updateComboAllTimeHT();

		void updateComboAllTimeHTContents();

		//changing comboAllDayHT
		void updateComboAllDayHT();

		void updateComboAllDayHTContents();

		//adding a tweet to the comboUser
		void addTweetToComboUser();

		//update main feed display with current main feeds
		void updateMainFeedDisplay();

		//update mentions feed display with current mentions feed
		void updateMentionsFeedDisplay();

		void updateAllTimeHTTweetsDisplay();

		void updateAllDayHTTweetsDisplay();

	private: 
		//stores lines of input file
		vector<string> fileLines;
		//stores user objects in twitter
		vector<User*> users;
		//stores which user is currently selected in combo box
		User* comboUser;
		//stores which all time most popular hashtag is currently selected in combo box
		string comboAllTimeHT;
		//stores which hashtag most popular in the last 24 hours is currently selected in combo box
		string comboAllDayHT;
		//stores a map of all hashtags, and the corresponding tweets that contain that hashtag, stored in a vector
		map<string, vector<Tweet*> > HTOfAllTime;
		//stores a vector of the 10 most popular hashtags of all time, sorted based on # of occurances
		vector<string> mostPopularHTOfAllTime;
		//stores a map of all hashtags from the past 24 hours, and the corresponding tweets that contain that hashtag, stored in a vector
		map<string, vector<Tweet*> > HTOfPast24Hours;
		//stores a vector of the 10 most popular hashtags of the past 24 hours, sorted based on # of occurances
		vector<string> mostPopularHTOfPast24Hours;
		//shows trending topics based on hashtags
		QWidget* trendingTopicsWindow;
		//shows most popular hashtags of all time in trendingTopicsWindow
		QListWidget* allTimeHTDisplay;
		//shows the tweets containing a given hashtag from the most popular hashtags of all time in trendingTopicsWindow
		QListWidget* allTimeTweetDisplay;
		//shows most popular hashtags of the past 24 hours in trendingTopicsWindow
		QListWidget* allDayHTDisplay;
		//shows the tweets containing a given hashtag from the most popular hashtags of the past 24 hours in trendingTopicsWindow
		QListWidget* allDayTweetDisplay;
		//gives options for selecting different users to tweet from
		QComboBox *usernameSelection;
		//gives options for selecting one of the most popular of all time hashtags
		QComboBox *allTimeHTSelection;
		//gives options for selecting one of the most popular hashtags of the past 24 hours
		QComboBox *allDayHTSelection;
		//contains text of users tweets
		QPlainTextEdit *tweetEntryBox;
		//main feed
		QListWidget *mainFeedDisplay;
		//mentions feed
		QListWidget *mentionsFeedDisplay;
		//tabs of sections of twitter in interface
		QTabWidget *sectionTabs;
};

#endif