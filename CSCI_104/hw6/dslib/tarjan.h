#ifndef TARJAN_H
#define TARJAN_H

#include <vector>
#include <map>

#include "noQT_twitterengine.h"
#include "user.h"
#include "stack.h"
#include "set.h"

class Tarjan 
{
	public:
		Tarjan(int argc, char** argv);
		~Tarjan();
		void algorithm();
		void strongconnect(User*&);
		void output();
	private:
		//name of output file
		char* outputFilename;
		//user objects from twitter engine
		vector<User*> users;
		//contains lists of usernames representing vertices in
		//strongly connected subgraphs
		vector< vector<string> > SCCs;
		//depth of search of graph at any given time
		int depthIndex;
		//associates username with corresponding depth index in search
		map<string, int> indices;
		//associates username with corresponding low link in search
		map<string, int> lowlinks;
		//stores nodes of trees involved in searching for strongly connected subgraphs
		Stack<User*> treeStack;
		//stores nodes of trees involved in searching for strongly connected subgraphs to see if nodes have already been added.
		Set<User*> treeStackSet;
};

#endif