#include <sstream>
#include <vector>

#include "tweet.h"
#include "user.h"

Tweet::Tweet()
{}

 
Tweet::Tweet(User* usr, DateTime& time, std::string& text)
{
  user = usr;
  _time = time;
  _text = text;
}

 
DateTime const & Tweet::time() const
{
  return _time;
}

 
std::string const & Tweet::text() const
{
  return _text;
}

User* Tweet::getUser()
{
	return user;
}

std::set<string> Tweet::getHashTags()
{
	////parse the tweet into individual words
	
	std::stringstream ss;
	std::string chunk;
	unsigned int numWords = 0;

	//store the text of the tweet in a stringstream
	ss << _text;
	//while portions of this text separated by spaces can be removed
	while (ss>>chunk)
	{
		//increment the number of words in the tweet
		numWords++;
		//clear the chunk for another use
		chunk.clear();
	}

	std::stringstream ss2;
	std::string chunk2;
	std::vector<string> tweetWords;
	//store the text of the tweet in a stringstream
	ss2 << _text;

	//while the vector has not been filled with all the words from the tweet
	while(tweetWords.size() < numWords)
	{
		//get a word from the text of the tweet
		ss2 >> chunk2;
		//add this word to the vector of words from the tweet
		tweetWords.push_back(chunk2);
		//clear the chunk for the next use
		chunk2.clear();
	}


	////go through all words, and store the hashtags

	//creates set to store the hashtags
	std::set<string> hashtags;
	//goes through all the words in the tweet
	for (std::vector<string>::iterator w = tweetWords.begin(); w != tweetWords.end(); w++)
	{
		string word = *w;
		//if the first character of a given word is a #
		if (word[0] == '#')
		{
			//if that word is not already in the set
			if (hashtags.find(word) == hashtags.end())
			{
				//add it to the set of hashtags
				hashtags.insert(word);
			}
		}
	}

	//returns the set of hashtags
	return hashtags;
}

std::ostream& operator<<(std::ostream& os, const Tweet& t)
{
  return (os << t.time() << t.user->name() << t.text());
}