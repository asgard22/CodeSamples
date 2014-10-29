#ifndef MAP_H
#define MAP_H

#include "my_alist.h"
#include <stdexcept>

template <class keyType, class valueType>
struct pair
{
	keyType key;	
	valueType val;
};

template <class keyType, class valueType>
class Map
{
	public:
		Map ();  // constructor for a new empty map

		~Map (); // destructor

		int size () const; // returns the number of key-value pairs

		void add (const keyType & key, const valueType & value); 
		/* Adds a new association between the given key and the given value.
		 If the key already has an association, it should simply replace the old value with this given value
		*/

		void remove (const keyType & key);
		/* Removes the association for the given key.
		If the key has no association, it should throw an exception */

		valueType const & get (const keyType & key) const;
		/* Returns the value associated with the given key by constant reference.
		If the key has no association, it should throw an exception. */

		valueType& get (const keyType & key);
		/* Returns the value associated with the given key by mutable reference.
		If the key has no association, it should throw an exception. */

		bool contains (const keyType & key) const;
		/* Returns true if the key exists in the map, false, otherwise */

	private:
		AList< pair<keyType, valueType> > internalStorage;
		
		int mapSize;
		/* You should store all of your associations inside one list.
		It is part of your assignment to figure out what types you 
		should store in that list.
		(Hint: you may need to add an additional definition.) */

		/* If you like, you can add further data fields and private
		helper methods.
		In particular, a 'find' function could be useful, which
		will find the index in the list at which a given key sits. */
};

//constructor
template <class keyType, class valueType>
Map<keyType, valueType>::Map() 
{
	mapSize = 0;
}

//deconstructor
template <class keyType, class valueType>
Map<keyType, valueType>::~Map()
{}

//gives size of map
template <class keyType, class valueType>
int Map<keyType, valueType>::size() const 
{
	return mapSize;
}

//adds key-value pair to map
template <class keyType, class valueType>
void Map<keyType, valueType>::add (const keyType & key, const valueType & value)
{
	//flag to check whether the key already exists in the map
	bool keyExists = false;
	
	//looks through the map for the key
	for (int i = 0; i < mapSize; i++)
	{
		if ((internalStorage.get(i)).key == key)
		{
			//if the key already exists in the map, associates the value passed with the key, and stops looking through the map
			keyExists = true;
			(internalStorage.get(i)).val = value;
			break;
		}
	}

	//if the key does not already exist in the map
	if (!keyExists)
	{
		//creates a new key-value pair with the values passed
		pair<keyType, valueType> p;
		p.key = key;
		p.val = value;
		//adds the new key-value pair to the front of the list 
		internalStorage.insert(0,p);
		//increases size
		mapSize++;	
	}
}

//removes key-value pair from map
template <class keyType, class valueType>
void Map<keyType, valueType>::remove (const keyType & key)
{
	//checks whether they key exists in the map
	bool keyExists = false;
	
	//looks through the map for the key
	for (int i = 0; i < mapSize; i++)
	{
		if ((internalStorage.get(i)).key == key)
		{
			//if the key already exists in the map, removes the key-value pair from the map and stops looking through the map
			keyExists = true;
			internalStorage.remove(i);
			//decreases size
			mapSize--;
			break;
		}
	}

	//if the key does not exist in the map
	if (!keyExists)
	{
		//throw an exception
		throw std::invalid_argument("the key you gave is not in the map!");	
	}
}

//gets value associated with given key as a const reference 
template <class keyType, class valueType>
valueType const & Map<keyType, valueType>::get (const keyType & key) const 
{
	//looks through the map for the key
	for (int i = 0; i < mapSize; i++)
	{
		if ((internalStorage.get(i)).key == key)
		{
			//if the key exists in the map, returns the value associated with the key
			return (internalStorage.get(i)).val;
		}
	}

	//if the key does not exist in the map
	//throw an exception
	throw std::invalid_argument("the key you gave is not in the map!");	
}

//gets value associated with given key as a const reference 
template <class keyType, class valueType>
valueType& Map<keyType, valueType>::get (const keyType & key) 
{
	//looks through the map for the key
	for (int i = 0; i < mapSize; i++)
	{
		if ((internalStorage.get(i)).key == key)
		{
			//if the key exists in the map, returns the value associated with the key
			return (internalStorage.get(i)).val;
		}
	}

	//if the key does not exist in the map
	//throw an exception
	throw std::invalid_argument("the key you gave is not in the map!");	
}

//checks whether map contains given key
template <class keyType, class valueType>
bool Map<keyType, valueType>::contains (const keyType & key) const
{
	
	//looks through the map for the key
	for (int i = 0; i < mapSize; i++)
	{
		if ((internalStorage.get(i)).key == key)
		{
			//if the key exists in the map, returns true

			return true;
		}
	}

	//if key does not exist in map, return false
	return false;
}

#endif