#include "tweet.h"

using namespace std;

struct datestampComparator {
	
	bool operator()(Tweet*& lhs, Tweet*& rhs) 
	{ 
		//see whether the date of the lhs hashtag is greater than the tweet of the rhs hashtag
		return lhs->time() > rhs->time();; 
	}
};