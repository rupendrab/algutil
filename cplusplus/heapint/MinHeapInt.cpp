#include <iostream>
#include <vector>
#include <cstdlib>
#include <ctime>
#include "MinHeapInt.h"

using namespace std;

MinHeapInt::MinHeapInt()
{
    init(100);
}

MinHeapInt::MinHeapInt(int initialCapacity)
{
    init(initialCapacity);
}

MinHeapInt::~MinHeapInt()
{
    // cout << "Destructor called!!!" << endl;
    data = vector<int>();
}

void MinHeapInt::init(int initialCapacity)
{
    data = vector<int>(initialCapacity);
    size = 0;
    srand((unsigned)time(0));
}

void MinHeapInt::insert(int value)
{
    size++;
    if (size >= data.capacity())
    {
        data.resize(size * 2);
    }
    data[size] = value;
    percolateUp(size);
}

int MinHeapInt::extractMin()
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

void MinHeapInt::exchange(int pos1, int pos2)
{
    if (pos1 <= size && pos2 <= size)
    {
        int temp = data[pos1];
        data[pos1] = data[pos2];
        data[pos2] = temp;
    }
}

void MinHeapInt::percolateUp(int pos)
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

void MinHeapInt::percolateDown(int pos)
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

void MinHeapInt::printContents()
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

int MinHeapInt::getSize() const
{
    return size;
}

void test01(MinHeapInt* mh)
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

void test02(MinHeapInt* mh, int cases)
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

void test03(MinHeapInt* mh)
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
  MinHeapInt* mh = new MinHeapInt::MinHeapInt();
  // test01(mh);
  test02(mh, 20);
  // test03(mh);
  return 0;
}
*/
