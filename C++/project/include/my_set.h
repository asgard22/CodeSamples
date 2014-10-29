#ifndef SET_H
#define SET_H

#include "my_alist.h"
#include <stdexcept>

using namespace std;

template <class T>
class Set 
{
	public: 
		Set ();                     // constructor for an empty set

		Set (const Set<T> & other); // copy constructor, making a deep copy

		~Set ();                    // destructor

		void add (const T & item); 
		  /* Adds the item to the set. 
		     Throws an exception if the set already contains the item. */

		void remove (const T & item);
		  /* Removes the item from the set.
		     Throws an exception if the set does not contain the item. */

		bool contains (const T & item) const;
		  /* Returns true if the set contains the item, and false otherwise. */

		int size () const;
		  /* Returns the number of elements in the set. */

		bool isEmpty () const;
		  /* Returns true if the set is empty, and false otherwise. */

		Set<T> setIntersection (const Set<T> & other) const;
		  /* Returns the intersection of the current set with other.
		     That is, returns the set of all items that are both in this
		     and in other. */

		Set<T> setUnion (const Set<T> & other) const;
		  /* Returns the union of the current set with other.
		     That is, returns the set of all items that are in this set
		     or in other (or both).
		     The resulting set should not contain duplicates. */

		/* The next two functions together implement a suboptimal version
		   of what is called an "iterator".
		   Together, they should give you a way to loop through all elements
		   of the set. The function "first" starts the loop, and the function
		   "next" moves on to the next element.
		   You will want to keep the state of the loop inside a private variable.
		   We will learn the correct way to implement iterators soon, at 
		   which point you will replace this.
		   For now, we want to keep it simple. */

		T* first ();
		  /* Returns the pointer to some element of the set, 
		     which you may consider the "first" element.
		     Should return NULL if the set is empty. */

		T* next ();
		  /* Returns the pointer to an element of the set different from all 
		     the ones that "first" and "next" have returned so far. 
		     Should return NULL if there are no more element. */
	    // other private variables you think you need.

  private:

  		AList <T> internalStorage;

	    //keeps track of previous iterators
	    AList <T*> pastIterators;

	    //keeps track of pointer in index
	    int iteratorIndex;
};

template <typename T>
Set <T>::Set() 
{
	//redundant
	iteratorIndex = 0;
}

template <typename T>
Set <T>::Set(const Set<T> & other) 
{
	this->internalStorage = other.internalStorage;

	this->pastIterators = other.pastIterators;
}

template <typename T>
Set <T>::~Set() {}

template <typename T>
void Set<T>::add (const T & item) 
{
	//flag to see if item is already in set
	bool inList = false;

	//looks through set to see if item is already in set
	for (int i = 0; i < internalStorage.size(); ++i)
	{
		if (internalStorage.get(i) == item) 
		{
			//flags if item is already in set
			inList = true;
			//stops looking
			break;
		}
	}

	//if the item is not already in the set
	if (!inList)
	{
		//add it
		internalStorage.insert(internalStorage.size(), item);
	}
	//if the item is already in the set
	else
	{
		//throw an exception saying so
		throw invalid_argument("that item is already present in the set!");
	}
}

template <typename T> 
void Set<T>::remove (const T & item) 
{
	//flag to see if item is already in list
	bool inList = false;

	//looks through list to see if item is already in list
	for (int i = 0; i < internalStorage.size(); ++i)
	{
		if (internalStorage.get(i) == item) 
		{
			//flags if item is already in set
			inList = true;
			//removes item if it is already in the list
			internalStorage.remove(i);
			//stops searching through the list
			break;
		}
	}

	//if the item is not in the set
	if (!inList)
	{
		//throw an exception saying so
		throw invalid_argument("that item is not in the set!");
	}
}

template <typename T>
bool Set<T>::contains (const T & item) const
{
	//flag to see if item is already in list
	bool inList = false;

	//looks through list to see if item is already in list
	for (int i = 0; i < internalStorage.size(); ++i)
	{
		if (internalStorage.get(i) == item) 
		{
			//flags if item is already in set
			inList = true;	
			//stops looking	
			break;
		}
	}

	return inList;
}

template <typename T> 
int Set<T>::size () const
{
	return internalStorage.size();
}

template <typename T>
bool Set<T>::isEmpty () const
{
	if (!internalStorage.size())
	{
		return true;
	}
	else 
	{
		return false;
	}
}

template <typename T> 
Set<T> Set<T>::setIntersection (const Set<T> & other) const
{
	Set<T> Intersection;

	//revise to go through smaller

	//if the set of which this function is a member has more elements than the set it is intersecting with...
	if (this->size() > other.size()) 
	{
		T item;
		//go through each item in the larger set
		for (int i = 0; i < this->size(); ++i)
		{
			//check if the item is in the smaller set
			item = this->internalStorage.get(i);
			if(other.contains(item))
			{
				//if the item is in both sets, add it to their intersection
				Intersection.add(item);
			}
		}
	}
	//if the set of which this function is a member has less elements than the set it is intersecting with...
	else
	{
		T item;
		//go through each item in the larger set
		for (int i = 0; i < other.size(); ++i)
		{
			//check if the item is in the smaller set
			item = other.internalStorage.get(i);
			if (this->contains(item))
			{
				//if the item is in both sets, add it to their intersection
				Intersection.add(item);
			}
		}
	}

	return Intersection;
}

template <typename T>
Set<T> Set<T>::setUnion (const Set<T> & other) const
{
	Set<T> Union;

	T item;

	//adds all items to Union that are unique to <this>
	for (int i = 0; i < this->size(); ++i)
	{
		item = this->internalStorage.get(i);
		//only adds items to union that are not duplicates between the two sets
		if (!other.contains(item))
		{
			Union.add(item);
		}
	}

	//adds all items in other to Union
	for (int i = 0; i < other.size(); i++)
	{
		item = other.internalStorage.get(i);
		Union.add(item);
	}

	return Union;
}

template <typename T>
T* Set<T>::first ()
{
	//if the set contains items
	if (this->size())
	{
		iteratorIndex = 0;
		//add a pointer to the first item in the list to the list of past iterators
		pastIterators.insert(iteratorIndex, &internalStorage.get(iteratorIndex));
		//returns a pointer to the first item of the list
		return &internalStorage.get(iteratorIndex);
	}
	//if the set is empty
	else
	{
		return NULL;
	}
}

template <typename T>
T* Set<T>::next ()
{
	if (pastIterators.size() < this->size())
	{
		iteratorIndex++;
		//add a pointer to the first item in the list to the list of past iterators
		pastIterators.insert(pastIterators.size(), &internalStorage.get(iteratorIndex));
		//returns a pointer to the first item of the list
		return &internalStorage.get(iteratorIndex);
	}
	else
	{
		return NULL;
	}
}

#endif