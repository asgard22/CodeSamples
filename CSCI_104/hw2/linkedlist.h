struct Item {
	int val;
	Item* next;
};

class List {
	public:
		List();
		//adds an element to the back of the linked list, takes value of element as argument
		void push_back(int);
		//deletes an element from a linked list, takes position of element as argument
		void delete_item(int);
		//prints linked list, takes head pointer as argument
		void print();
		int print_helper(Item*);
		//returns the head pointer
		Item* get_head();
		//gets item, takes position of item as argument
		int get_item(int);
		//gets length of list
		int get_length();
		~List();
	private:
		//stores the head pointer
		Item* head;
		//stores list length
		int length;
};
