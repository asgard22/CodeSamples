#Compiler Variables
CC = g++
CPPFLAGS = -Wall -g -std=c++11

#Location of files
SRC_DIR = src
HEADER_DIR = include

#Update CPPFLAGS to include headers directory
CPPFLAGS += -I $(HEADER_DIR)/

all: tarjan

tarjan: $(SRC_DIR)/run_tarjan.cpp $(SRC_DIR)/tarjan.cpp $(SRC_DIR)/noQT_twitterengine.cpp $(SRC_DIR)/user.cpp $(SRC_DIR)/datetime.cpp $(SRC_DIR)/tweet.cpp
	$(CC) $(CPPFLAGS) -o $@ $?


# Memory check rule. Depends on a fresh binary.
.PHONY: memcheck
memcheck: tarjan
	valgrind --tool=memcheck --leak-check=yes ./$<
