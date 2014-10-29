#include <cstdlib>
#include <iostream>
#include "linkedlist.h"

using namespace std;

List::List() {
	head = NULL;	
	length = 0;
}

void List::push_back(int val) {

	Item* newItem = new Item;
	newItem->val = val;
	newItem->next = NULL;

	if (head == NULL) {
		head = newItem;
		length = 1;
	} else {
		Item* temp = head;
		while (temp->next) {
			temp = temp->next;
		}		
		temp->next = newItem;
		length += 1;
	}
}

void List::delete_item(int position) {
	
	//will point to the item before the deleted item
	Item* beforeDelete = head;
	//will point to the item being deleted
	Item* atDelete = head;
	//will point to the item after the deleted item
	Item* afterDelete = head;
	
	if (position != 1) {	
		//makes beforeDelete point to the item before (position)
		for (int i = 2; i <= position-1; i++) {
			beforeDelete = beforeDelete->next;	
		}

		//makes atDelete point to item at (position)
		for (int i = 2; i <= position; i++) {
			atDelete = atDelete->next;
		}

	}
	
	//makes afterDelete point to the item after (position)
	for (int i = 2; i <= position+1; i++) {
		afterDelete = afterDelete->next;
	}

	if (position != 1) {
		beforeDelete->next = afterDelete;
	} else {
		head = afterDelete;
	}

	//deletes item at (position)	
	delete atDelete;
	
}

void List::print() {

	Item* temp = head;

	print_helper(temp);
}

int List::print_helper(Item* head) {

	if (head == NULL) {
		return 0;
	} else {
		cout << head->val;
		print_helper(head->next); 
	}

	return 0;
}

Item* List::get_head() {
	return head;
}

int List::get_item(int position) {
	
	Item* temp = head;

	for (int i = 2; i <= position; i++) {
		temp = temp->next;		
	}	

	return temp->val;
}

int List::get_length() {
	return length;
}

List::~List() {}
