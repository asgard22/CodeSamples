#include <fstream>
#include <iostream>
#include <sstream>
#include "linkedlist.h"

using namespace std;

int readFile(ifstream&, ofstream&, int&, char**&, string*, List&, List&);

//needed to alter signature from assignment specifications since List (linked list object) 
//could not be used without being passed directly from the main() scope.
void removeDuplicates(Item*, List&);

//needed to alter signature from assignment specifications since List (linked list object) 
//could not be used without being passed directly from the main() scope.
void removeDuplicates_helper(Item*, List&, int);

//needed to alter signature from assignment specifications since List (linked list object) 
//could not be used without being passed directly from the main() scope.
Item* concatenate(Item*, Item*, List&, List&, List&);

Item* concatenate_helper(Item*, Item*, List&, List&, List&);

int main(int argc, char** argv) {

        //initializes i/o objects
        ifstream ifile;
        ofstream ofile;
	
	//place to store the two lists of integers	
	string intLists[2];

	//instantiates two linked lists, one for each of the lines in the file
	List line1;
	List line2;
	//creates List object to store the concatenation of the two lines in the file	
	List concatList;
	
	//reads two lists of integers from the file, and stores them in intLists
	readFile(ifile, ofile, argc, argv, intLists, line1, line2);
	
	//removes duplicates from the first linked list, which stores the integers from the first line of the file	
	removeDuplicates(line1.get_head(), line1);

	//concatenates two lists of integers together
	concatenate(line1.get_head(), line2.get_head(), line1, line2, concatList);

	for (int i = 1; i <= (concatList.get_length()); i++) {
		ofile << concatList.get_item(i) << ' ';
	}	
	
}

int readFile(ifstream& ifile, ofstream& ofile, int& argc, char**& argv, string* intLists, List& line1, List& line2) {

	////checks whether the user gave the correct number of arguments

        if (argc != 3) {
                cout << "you did not give the correct number of arguments" << endl;
                cout << "quitting program..." << endl;
                return 0;
        }


        ////checks whether all files can be opened

        int fileCount = 0;

        ifile.open(argv[1]);
        ofile.open(argv[2]);

        if (ifile.fail()){
                cout << "could not open your input file." << endl;
        } else { fileCount++; }


        if (ofile.fail()) {
                cout << "could not open your output file." << endl;
        } else { fileCount ++; }


        if (fileCount != 2) {
                cout << "missing files." << endl;
                cout << "quitting program..." << endl;
        } else {
                cout << "input and output files successfully opened." << endl;
        }

	////reads in the two lists of integers

	string line;	
	int i = 0;

	while (getline(ifile, line)) {	
		intLists[i] = line;
		i++;
	}
	
	stringstream list;
	int listInt;
	list << intLists[0];
	
	for (unsigned int j = 0; j < intLists[0].length(); j ++) { 
		if(list >> listInt) {
			line1.push_back(listInt);
		}
	}

	list.clear();
	list << intLists[1];
	for (unsigned int j = 0; j < intLists[1].length(); j ++) { 
		if(list >> listInt) {
			line2.push_back(listInt);
		}
	}	

	return 0;
}


void removeDuplicates(Item* head, List& line1) {
	
	int posInList = 1;
	removeDuplicates_helper(head, line1, posInList);

}

void removeDuplicates_helper(Item* head, List& line1, int posInList) {

	//recursively removes all duplicates in line1
	if (head == NULL || head->next == NULL) {
		return;
	} else {
		if (head->val == (head->next)->val) {
			line1.delete_item(posInList);	
		}
		removeDuplicates_helper(head->next, line1, posInList+1);
	}
}

Item* concatenate(Item* head1, Item* head2, List& line1, List& line2, List& concatenatedList) {

	return concatenate_helper(head1, head2, line1, line2, concatenatedList);

}

Item* concatenate_helper(Item* head1, Item* head2, List& line1, List& line2, List& concatenatedList) {

	//recursively concatenates line1 and line 2
	if (head1 == NULL) {
		if (head2 == NULL) {
			return concatenatedList.get_head();
		} else {
			concatenatedList.push_back(head2->val);
			return concatenate_helper(head1, head2->next, line1, line2, concatenatedList);
		}
	} else {
		concatenatedList.push_back(head1->val);
		return concatenate_helper(head1->next, head2, line1, line2, concatenatedList);
	}
}

