#include <iostream>
#include <vector>
#include <cstdlib>
#include <ctime>
#include "MinHeap.h"

using namespace std;

template <class T>
MinHeap<T>::MinHeap()
{
    init(100);
}

template <class T>
MinHeap<T>::MinHeap(int initialCapacity)
{
    init(initialCapacity);
}

template <class T>
MinHeap<T>::~MinHeap()
{
    // cout << "Destructor called!!!" << endl;
    data = vector<T>();
}

template <class T>
void MinHeap<T>::init(int initialCapacity)
{
    data = vector<T>(initialCapacity);
    size = 0;
    srand((unsigned)time(0));
}

template <class T>
void MinHeap<T>::insert(T value)
{
    size++;
    if (size >= data.capacity())
    {
        data.resize(size * 2);
    }
    data[size] = value;
    percolateUp(size);
}

template <class T>
T MinHeap<T>::extractMin()
{
    if (size >= 1)
    {
        int minValue = data[1];
        exchange(1, size);
        size--;
        if (size > 0)
        {
            percolateDown(1);
        }
        return minValue;
    }
    else
    {
        return NULL;
    }
}

template <class T>
void MinHeap<T>::exchange(int pos1, int pos2)
{
    if (pos1 <= size && pos2 <= size)
    {
        int temp = data[pos1];
        data[pos1] = data[pos2];
        data[pos2] = temp;
    }
}

template <class T>
void MinHeap<T>::percolateUp(int pos)
{
    while (pos > 1)
    {
        int parentPos = pos / 2;
        if (data[parentPos] > data[pos])
        {
            exchange(pos, parentPos);
            pos = parentPos;
        }
        else
        {
            return;
        }
    }
}

template <class T>
void MinHeap<T>::percolateDown(int pos)
{
    while (pos <= size/2)
    {
        int childPos = pos * 2;
        if (childPos < size && data[childPos+1] < data[childPos])
        {
            childPos++;
        }
        if (data[pos] > data[childPos])
        {
            exchange(pos, childPos);
            pos = childPos;
        }
        else
        {
            return;
        }
    }
}

template <class T>
void MinHeap<T>::printContents()
{
    for (int i=1; i<size; i++)
    {
        cout << data[i] << ", ";
    }
    if (size >= 1)
    {
      cout << data[size] << endl;
    }
}

template <class T>
int MinHeap<T>::getSize() const
{
    return size;
}

void test01(MinHeap<int>* mh)
{
    mh->printContents();
    mh->insert(3);
    mh->printContents();
    mh->insert(2);
    mh->printContents();
    mh->insert(1);
    mh->printContents();
    cout << "Popping out contents of Heap..." << endl;
    while (mh->getSize() > 0)
    {
        cout << mh->extractMin() << endl;
    }
    // cout << mh->extractMin() << endl;
}

void test02(MinHeap<int>* mh, int cases)
{
    for (int i=0; i<cases; i++)
    {
        mh->insert(rand() % 100);
    }
    mh->printContents();
    while (mh->getSize() > 0)
    {
        cout << mh->extractMin() << endl;
    }
}

void test03(MinHeap<int>* mh)
{
    int arr[] = {17, 39, 78, 1};
    for (int i=0; i<sizeof(arr)/sizeof(arr[0]); i++)
    {
        mh->insert(arr[i]);
    }
    mh->printContents();
}

/*
int main()
{
  MinHeap<int>* mh = new MinHeap<int>::MinHeap();
  // test01(mh);
  test02(mh, 20);
  // test03(mh);
  return 0;
}
*/

template class MinHeap<int>;
