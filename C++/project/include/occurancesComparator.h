#include "tweet.h"

struct occurancesComparator {
	
	map<string, vector<Tweet*> > hashtagTweetMap;

	occurancesComparator(const map<string, vector<Tweet*> > map)
	{
		hashtagTweetMap = map;
	}
	
	bool operator()(string& lhs, string& rhs) 
	{ 
		//see whether the number of occurances of the lhs hashtag is greater than the number of occurances of the rhs hashtag
 		return hashtagTweetMap[lhs].size() > hashtagTweetMap[rhs].size();
	}
};