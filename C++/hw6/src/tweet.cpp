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

 
std::ostream& operator<<(std::ostream& os, const Tweet& t)
{
  return (os << t.time() << t.user->name() << t.text());
}