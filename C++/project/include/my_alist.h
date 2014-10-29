#ifndef ALIST_H
#define ALIST_H

#include <cstdlib>
#include <stdexcept>
#include <iostream>

template <class T>
class AList
{
	public:
		AList ();   // constructor

		~AList ();  // destructor

		int size () const;  // returns the number of elements in the list

		void insert (int position, const T & val);
		  /* Inserts val into the list before the given position,
		     shifting all later items one position to the right.
		     Inserting before 0 (i.e., position = 0) makes it the 
		     new first element.
		     Inserting before size() (i.e., position = size()) makes 
		     it the new last element.
		     Your function should throw an exception if position is
		     outside the legal range, which would be 0-size() here. */

		void remove (int position);
		  /* Removes the item from the given position, shifting all
		     later items one position to the left.
		     Your function should throw an exception if position is
		     outside the legal range. */

		void set (int position, const T & val);
		  /* Overwrites the given position with the given value.
		     Does not affect any other positions.  
		     Your function should throw an exception if position is
     			outside the legal range. */

		T& get (int position) ;
		  /* Returns a non-const reference to the item at the given position.
		     Your function should throw an exception if position is
		     outside the legal range. */

		 T const& get (int position) const;
		  /* Returns a const reference to the item at the given position.
		     Your function should throw an exception if position is
		     outside the legal range. */

		bool contains(const T & val);
			// Checks if AList contains the given value. Returns true if it does, returns false otherwise.

		  AList(const AList<T>& copiedTo);
		  // A copy constructor which performs a deep copy.

		  AList& operator=(const AList<T>& assignedTo);
		  // An assignment operator.

		 private: 
	         //pointer to array containing list
		  	T * array;
			//capacity of the array
			int listCapacity;
			//size of the array
			int listSize;
};



//constructor implementation
template <typename T>
AList<T>::AList() 
{
	//the list is initially empty
	listSize = 0;
	//the capacity is initially 5
	listCapacity = 5;
	array = new T[listCapacity];
}



//destructor implementation
template <typename T>
AList<T>::~AList()
{
	// delete[] array;
}



//implementation of function that gets the size of the list
template <typename T>
int AList<T>::size() const 
{
	return listSize;
}



//implementation of function that inserts an element before a specified position
template <typename T>
void AList<T>::insert(int position, const T & val)
{
	//case 1: position is outside the legal range
	if (position < 0 || position > listSize) 
	{
		throw std::out_of_range("position is not > 0 and <= size");
	}

	//case 2: position is within legal range
	else
	{
		//case 2a: size + 1 <= capacity, meaning array does not need to change capacity
		if (listSize+1 <= listCapacity) 
		{
			//case 2ai: list is empty
			if (listSize == 0) 
			{
				//increase size of AList
				listSize++;
				//set first value equal to val
				array[0] = val;
			}
			//case 2aii: list has at least one element
			else
			{
				//increase size of AList
				listSize++;
				//moves all elements of the list after <position> forward by one
				for (int i = listSize-1; i > position; i--)
				{
					array[i] = array[i-1];
				}
				//sets element at <position> equal to value passed
				array[position] = val;
			}
		}
		//case 2b: size + 1 > capacity 	
		else
		{
			//increase size of AList
			listSize++;
			//create a temporary array with double the capacity
			T* tempArray = new T[2*listCapacity];
			//copy the elements of the AList array to the temporary array up to the index of the position to be inserted at		
			for (int i = 0; i < position; i++) 
			{
				tempArray[i] = array[i];		
			}
			//add the new element to the temporary array
			tempArray[position] = val;
			//copy the remaining elements of the AList array to the temporary array
			for (int i = position+1; i < listSize; i++) 
			{
				tempArray[i] = array[i-1];
			}
			//delete the old array
			//delete[] array;
			//set the array member equal to the temporary array
			array = tempArray;
			//increase capacity of AList
			listCapacity = listCapacity*2;
		}
	}
}



////implementation of function that removes an item at a specified position
template <typename T>
void AList<T>::remove (int position) 
{
	//case 1: position is outside legal range
	if (position < 0 || position >= listSize) 
	{
		throw std::out_of_range("position is not > zero and < than size ()");
	}
	//case 2: position is within the legal range
	else
	{
		//move all elements in the list back one after position, overwriting position
		for (int i = position; i < listSize-1; i++)
		{
			array[i] = array[i+1];	
		}
		//decrease size of AList
		listSize--;
	}
}



//implementation of a function that sets an item at a specified position
template <typename T>
void AList<T>::set(int position, const T & val)
{
	//case 1: position is outside legal range
	if (position < 0 || position >= listSize) 
	{
		throw std::out_of_range("position is not > 0 and < size");
	}
	//case 2: position is within legal range, sets val of item at position to val passed as argument to function
	else
	{
		//set the given position in the array equal to the value passed
		array[position] = val;	
	}	
}



//implementation of a function that gets an item at a specified position
template <typename T>
T& AList<T>::get(int position) 
{
	//case 1: position is outside legal range
	if (position < 0 || position >= listSize) 
	{
		throw std::out_of_range("position is not > 0 and < size ()");
	}
	//case 2: position is within legal range, returns  of item at position passed as argument to function
	else 
	{
		return array[position];
	}
}



////implementation of a function that gets an item at a specified position, returns this item as a const.
template <typename T>
T const & AList<T>::get(int position) const 
{
	//case 1: position is outside legal range
	if (position < 0 || position >= listSize) 
	{
		throw std::out_of_range("position is not > 0 and < size ()");
	}
	//case 2: position is within legal range, returns  of item at position passed as argument to function
	else 
	{
		return array[position];			
	}
}

// A copy constructor which performs a deep copy.
template <typename T>
AList<T>::AList(const AList<T>& copiedFrom)
{
	//copies size of previously existing object
	listSize = copiedFrom.listSize;
	//copies list capacity of previously existing object
	listCapacity = copiedFrom.listCapacity;
	//allocates array for new object
	array = new T[listCapacity];
	//copies all data over from previously existing object
	for (int i = 0; i < copiedFrom.size(); i++)
	{
		array[i] = copiedFrom.get(i);
	}
}

template <typename T>
AList<T>& AList<T>::operator=(const AList<T>& assignedTo)
{
	//copies size of previously existing object
	listSize = assignedTo.listSize;
	//copies list capacity of previously existing object
	listCapacity = assignedTo.listCapacity;
	//allocates array for new object
	array = new T[listCapacity];
	//copies all data over from previously existing object
	for (int i = 0; i < assignedTo.size(); i++)
	{
		array[i] = assignedTo.get(i);
	}
}
// An assignment operator.

template <typename T>
bool AList<T>::contains(const T & val)
{
	for (int i = 0; i < this->size(); ++i)
	{
		if (this->get(i) == val)
		{
			return true;
		}
	}

	return false;
}

#endif