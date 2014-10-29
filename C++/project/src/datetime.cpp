#include <cmath>
#include "datetime.h"

DateTime::DateTime()
{
  hour = 00;
  minute = 00;
  second = 00;

  year = 2000;
  month = 01;
  day = 01;
}

DateTime::DateTime(int hh, int mm, int ss, int yr, int mnth, int dy)
{
  hour = hh;
  minute = mm;
  second = ss;

  year = yr;
  month = mnth;
  day = dy;
}

bool DateTime::operator<(const DateTime& other)
{
  int theseTimeComponents[6] = {this->year, this->month, this->day, this->hour, this->minute, this->second};
  int otherTimeComponents[6] = {other.year, other.month, other.day, other.hour, other.minute, other.second};

  long thisTime = 0;
  long otherTime = 0;

  for (int i = 6; i > 0; --i)
  {
    thisTime += theseTimeComponents[i-1]*pow(10,2*(6-i));
  }

  for (int i = 6; i > 0; --i)
  {
    otherTime += otherTimeComponents[i-1]*pow(10,2*(6-i));
  }

  //checks whether this Datetime is less than other Datetime;
  //returns true if other is greater
  return (otherTime > thisTime);
}

bool DateTime::operator<(const DateTime& other) const
{
  int theseTimeComponents[6] = {this->year, this->month, this->day, this->hour, this->minute, this->second};
  int otherTimeComponents[6] = {other.year, other.month, other.day, other.hour, other.minute, other.second};

  long thisTime = 0;
  long otherTime = 0;

  for (int i = 6; i > 0; --i)
  {
    thisTime += theseTimeComponents[i-1]*pow(10,2*(6-i));
  }

  for (int i = 6; i > 0; --i)
  {
    otherTime += otherTimeComponents[i-1]*pow(10,2*(6-i));
  }

  //checks whether this Datetime is less than other Datetime;
  //returns true if other is greater
  return (otherTime > thisTime);
}

bool DateTime::operator>(const DateTime& other)
{
  int theseTimeComponents[6] = {this->year, this->month, this->day, this->hour, this->minute, this->second};
  int otherTimeComponents[6] = {other.year, other.month, other.day, other.hour, other.minute, other.second};

  long thisTime = 0;
  long otherTime = 0;

  for (int i = 6; i > 0; --i)
  {
    thisTime += theseTimeComponents[i-1]*pow(10,2*(6-i));
  }

  for (int i = 6; i > 0; --i)
  {
    otherTime += otherTimeComponents[i-1]*pow(10,2*(6-i));
  }

  //checks whether this Datetime is less than other Datetime;
  //returns true if this is greater
  return (thisTime > otherTime);
}

bool DateTime::operator>(const DateTime& other) const
{
  int theseTimeComponents[6] = {this->year, this->month, this->day, this->hour, this->minute, this->second};
  int otherTimeComponents[6] = {other.year, other.month, other.day, other.hour, other.minute, other.second};

  long thisTime = 0;
  long otherTime = 0;

  for (int i = 6; i > 0; --i)
  {
    thisTime += theseTimeComponents[i-1]*pow(10,2*(6-i));
  }

  for (int i = 6; i > 0; --i)
  {
    otherTime += otherTimeComponents[i-1]*pow(10,2*(6-i));
  }

  //checks whether this Datetime is less than other Datetime;
  //returns true if this is greater
  return (thisTime > otherTime);
}

std::ostream& operator<<(std::ostream& os, const DateTime& other)
{

  int otherTimeComponents[6] = {other.year, other.month, other.day, other.hour, other.minute, other.second};

  std::stringstream ss;
  std::string timeChunk;
  std::string otherTimeSTR;

  //results in string contained within thisTimeSTR in the form: year month day hour minute second
  for (int i = 0; i < 6; ++i)
  {
    //converts time components from integers to strings
    ss << otherTimeComponents[i];
    ss >> timeChunk;
    //adds number of year, month, day, hour, minute, or second
    otherTimeSTR += timeChunk;
    //adds special characters between numbers
    if (i < 2)
    {
      otherTimeSTR += '-';
    }
    else if (i == 2)
    {
      otherTimeSTR += ' ';
    }
    else if (i > 2 && i < 5)
    {
      otherTimeSTR += ":";
    }
    //clears stringstream for next use
    ss.clear();
  }

  return (os << otherTimeSTR);
}