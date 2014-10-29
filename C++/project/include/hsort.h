#include <vector>

using namespace std;

template <class T>
void swap(std::vector<T>& myArray, int pos1, int pos2)
{
	//save the values to be swapped
	T formerpos1val = myArray[pos1];
	T formerpos2val = myArray[pos2];

	//swap them
	myArray[pos1] = formerpos2val;
	myArray[pos2] = formerpos1val;
}



//adapted from code in Data Structures Fall 2013 chapter 22 (http://www-scf.usc.edu/~csci104/lectures/DataStructures.pdf)
template <class T, class Comparator>
void trickleUp(std::vector<T>& heapArray, Comparator& comp, int position)
{	
	//if you haven't reached the root of the heap
	if (position > 0)
	{
		//if the node in question (myArray[position]) has a greater value than its parent (myArray[(position-1)/2]) (lhs > rhs)
		if (comp(heapArray[position], heapArray[(position-1)/2]))
		{
			//swap the node and its parent
			swap(heapArray, position, ((position-1)/2));
			//call trickle up on the node again, which is now in the position of its former parent
			trickleUp(heapArray, comp, ((position-1)/2));
		}
	} 
}

//adapted from code in Data Structures Fall 2013 chapter 22 (http://www-scf.usc.edu/~csci104/lectures/DataStructures.pdf)
template <class T, class Comparator>
void trickleDown(std::vector<T>& heapArray, Comparator comp, int position)
{
	//if this node is not a leaf (meaning it has at least one child that is within the bounds of the array)
	if(2*position+2 < heapArray.size())
	{
		////get the highest value child and its position

		T highestChild;
		int highestChildPosition;
		//if this node has more than one child.
		if (2*position+3 < heapArray.size())
		{
			////check if the first or second child has the higher value
			T firstChild = heapArray[2*position+1];
			T secondChild = heapArray[2*position+2];

			//if the first child has the higher value
			if(comp(firstChild, secondChild))
			{
				highestChild = firstChild;
				highestChildPosition = 2*position+1;
			}
			//if the second child has the higher value
			else
			{
				highestChild = secondChild;
				highestChildPosition = 2*position+2;
			}
		}
		//if this node has only one child
		else
		{
			//record that child as the highest value child
			highestChild = heapArray[2*position+2];
			highestChildPosition = 2*position+1;
		}

		////check if the highest child has greater value than the current node

		T thisNode = heapArray[position];
		//if the highest child of this node has a greater value than this node
		if (comp(highestChild, thisNode))
		{
			//swap their positions in the MaxHeap
			swap(heapArray, highestChildPosition, position);
			//trickle down the node that has been moved to the position of its former highest child
			trickleDown(heapArray, comp, highestChildPosition);
		}

	}
}

template <class T, class Comparator>
void heapify_helper(std::vector<T>& myArray, std::vector<T>& heapArray, Comparator comp, int index)
{
	if (heapArray.size() == myArray.size())
	{
		myArray = heapArray;
		return;
	}
	else
	{
		//adds element of myArray to heap
		heapArray.push_back(myArray[index]);
		//places added element in correct location within heap (based on value)
		trickleUp(heapArray, comp, heapArray.size()-1);
		//increments index, which keeps track of which element of myArray is being added to heapArray
		heapify_helper(myArray, heapArray, comp, ++index);
	}
}


template <class T, class Comparator>
void heapify(std::vector<T>& myArray, Comparator comp)
{

	//creates array to store heap in
	std::vector<T> heapArray;
	heapify_helper(myArray, heapArray, comp, 0);
}



template <class T, class Comparator>
void heapSort (std::vector<T>& myArray, Comparator& comp)
{
	std::vector<T> sortedArray;
  	//turns the array into a MaxHeap
  	heapify(myArray, comp);
  	//returns the elements of the MaxHeap in order from greatest value to least value
  	while(myArray.size())
  	{
  		//adds greatest value element in MaxHeap to new array
  		sortedArray.push_back(myArray[0]);
  		//swaps greatest value element with last element of array representing MaxHeap, making the former last element the new root of the MaxHeap
  		swap(myArray, 0, myArray.size()-1);
  		//removes given greatest value element
  		myArray.pop_back();
  		//moves new root of MaxHeap to its correct position, ensuring that the new greatest value in the MaxHeap is the root
  		trickleDown(myArray, comp, 0);
  	}

  	myArray = sortedArray;
}