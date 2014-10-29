#include <iostream>
#include <stdexcept>
#include "set.h"

using namespace std;

int main() 
{

	///BASIC TESTS


	Set<int> testSet1;

	for (int i = 0; i < 5; ++i)
	{
		testSet1.add(i);
	}

	for (int i = 0; i < 5; ++i)
	{
		cout << testSet1.contains(i) << endl;
	}

	cout << testSet1.size() << endl;

	for (int i = 0; i < 5; ++i)
	{
		testSet1.remove(i);
	}

	for (int i = 0; i < 5; ++i)
	{
		cout << testSet1.contains(i) << endl;
	}

	cout << testSet1.size() << endl;

	cout << testSet1.isEmpty() << endl;

	for (int i = 0; i < 5; ++i)
	{
		testSet1.add(i);
	}

	//ADVANCED TESTS

	Set<int> testSet2;

	for (int i = 3; i < 8; ++i)
	{
		testSet2.add(i);
	}

	//intersection test

	Set<int> intersectionSet = testSet1.setIntersection(testSet2);

	cout << "size of intersection" << endl;

	cout << intersectionSet.size() << endl;

	//union test

	Set<int> unionSet = testSet1.setUnion(testSet2);

	cout << "size of union" << endl;

	cout << unionSet.size() << endl;

	for (int i = 0; i < 8; ++i)
	{
		cout << unionSet.contains(i);
	}

	cout << endl;

	for (int i = 3; i < 5; ++i)
	{
		unionSet.remove(i);
	}

	for (int i = 0; i < 8; ++i)
	{
		cout << unionSet.contains(i);
	}

	cout << endl;

	//iterator tests

	Set<int> iteratorSet;

	cout << iteratorSet.first() << endl;

	for (int i = 1; i < 11; ++i)
	{
		iteratorSet.add(i);
	}

	cout << *iteratorSet.first() << endl;

	for (int i = 0; i < iteratorSet.size()-1; ++i)
	{
		cout << *iteratorSet.next() << endl;
	}

	Set<int> testSet3(testSet2);

	cout << testSet2.size() << endl;
	cout << testSet3.size() << endl;

	cout << testSet2.contains(1) << endl;
	cout << testSet3.contains(1) << endl;

	cout << testSet2.contains(3) << endl;
	cout << testSet3.contains(3) << endl;


}