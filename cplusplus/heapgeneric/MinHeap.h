#pragma once
#include <vector>

using namespace std;

template <class T>
class MinHeap
{
    public:
        MinHeap();
        MinHeap(int initialCapacity);
        ~MinHeap();
        void insert(T value);
        T extractMin();
        void printContents();
        int getSize() const;

    private:
        void init(int initialCapacity);
        void exchange(int pos1, int pos2);
        void percolateUp(int pos);
        void percolateDown(int pos);

    private:
        vector<T> data;
        int size;
};
