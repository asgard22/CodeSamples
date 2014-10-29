#ifndef TRUTHVALUES_H
#define TRUTHVALUES_H

class truthValues {

	private:
		//stores truth values of variables
		int* truthOfVariables;
		//stores truth values of clauses
		int* truthOfClauses;
	public:
		//initializes truth values of variables and clauses, takes in number of variables and number of clauses
		truthValues(int, int);
		//returns truth values of a variable
		int get_variable(int);
		//returns truth values of a clause
		int get_clause(int);
		//sets truth value of a variable
		void set_variable(int, int);
		//sets truth value of a clause
		void set_clause(int, int);
		~truthValues();
};


#endif 
