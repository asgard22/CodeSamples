#include <vector>
#include <iostream>
#include <cmath>

using namespace std;

template <class T, class Comparator>
void mergeSort(std::vector<T>& inputArray, Comparator& comp)
{
	std::vector<T> outputArray(inputArray.size());
	mergeSort_helper(inputArray, outputArray, 0, inputArray.size(), comp);
}

template <class T, class Comparator>
void mergeSort_helper(std::vector<T>& inputArray, std::vector<T>& outputArray, int start, int end, Comparator& comp)
{
	if (start < end)
	{
		int mid = floor((start+end)/2);
		mergeSort_helper(inputArray, outputArray, start, mid, comp);
		mergeSort_helper(inputArray, outputArray, mid+1, end, comp);
		cout << "start " << start << " mid " << mid << " end " << end << endl;
		merge(inputArray, outputArray, start, mid, end, comp);
	}
}

template <class T, class Comparator>
void merge(std::vector<T>& inputArray, std::vector<T>& outputArray, int start, int mid, int end, Comparator& comp)
{
	//sublists of size 1
	if (end-start == 1)
	{
		return;
	}
	else 
	{
		std::vector<T> tempVec1(mid-start);
		for (int i = 0; i < mid-start; i ++)
		{
			tempVec1[i] = inputArray[start+i];
		}
		std::vector<T> tempVec2(end-mid);
		for (int j = 0; j < end-mid; j ++)
		{
			tempVec2[j] = inputArray[mid+j];
		}

		int i = 0;
		if (tempVec1.size() > tempVec2.size())
		{
			//iterate over the larger array
			while (i != tempVec1.size())
			{
				//if the smaller array has no more elements to compare
				if (i >= tempVec2.size())
				{
					//do nothing but increment the index
					i++;
					continue;
				}
				//otherwise compare the ith element of both arrays
				else
				{
					if (comp(tempVec1[i], tempVec2[i]))
					{
						//add the lhs of the comparator to the output array at the correct spot
						inputArray[start+i] = tempVec1[i];
						inputArray[mid+i] = tempVec2[i];
						i++;
					}
					else
					{
						//add the rhs of the comparator to the output array at the correct spot
						inputArray[start+i] = tempVec2[i];
						inputArray[mid+i] = tempVec1[i];
						i++;
					}
				}
			}
		}
		else
		{
			while(i != tempVec2.size())
			{
				if (i >= tempVec1.size())
				{
					i++;
					continue;
				}
				else
				{
					if (comp(tempVec1[i], tempVec2[i]))
					{
						//add the lhs of the comparator to the output array at the correct spot
						inputArray[start+i] = tempVec1[i];
						inputArray[mid+i] = tempVec2[i];
						i++;
					}
					else
					{
						//add the rhs of the comparator to the output array at the correct spot
						inputArray[start+i] = tempVec2[i];
						inputArray[mid+i] = tempVec1[i];
						i++;
					}
				}
			}
		}
	}
}