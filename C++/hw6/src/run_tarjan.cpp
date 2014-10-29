#include "tarjan.h"

int main(int argc, char** argv)
{
	Tarjan tarjan(argc, argv);
	tarjan.algorithm();
	tarjan.output();
}