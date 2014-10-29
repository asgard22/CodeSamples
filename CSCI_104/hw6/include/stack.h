#ifndef STACK_H
#define STACK_H

#include <vector>

template <typename T>
//inherits as private so that no derived classes of Stack or
//outside world operations can interfere with data except
//through member functions of stack (stack 'as-a' vector)
class Stack : private std::vector<T>
{
    public:
          // add appropriate constructors and destructors
          Stack();
          ~Stack();

          bool isEmpty() const;
          /* returns whether the stack contains any elements */

          void push(const T& val);
          /* adds a value to the top of the stack */

          void pop();
          /* deletes the top value from the stack */

          T const & top() const;
          /* returns the top value on the stack */
};

//constructor
template <typename T>
Stack<T>::Stack()
{
    //no use for constructor implementation
}

//destructor
template <typename T>
Stack<T>::~Stack()
{
    //no use for destructor implementation
}

//isEmpty
template <typename T>
bool Stack<T>::isEmpty() const
{
    if (this->size() == 0)
    {
        return true;
    }
    else
    {
        return false;
    }
}

//push
template <typename T>
void Stack<T>::push(const T& val)
{
    //use of array under the hood of vector means that making the end
    //of the list the top of the stack will lead to the best
    //possible runtime (no elements need to change position)
    this->push_back(val);
}

//pop
template <typename T>
void Stack<T>::pop()
{
    if (this->size() != 0)
    {
        //removes top element of stack, which is chosen to be
        //the end of the vector
        this->pop_back();
    }
    else
    {
      throw out_of_range("there is nothing in the stack.");
    }
}

//top
template <typename T>
T const& Stack<T>::top() const
{
    if (this->size() != 0)
    {
        //returns top element of stack, which is chosen to be 
        //the end of the vector
        return this->at(this->size()-1);
    }
    else
    {
      throw out_of_range("there is nothing in the stack.");
    }
}

#endif