#pragma once
#include <vector>

using namespace std;

class MinHeapInt
{
    public:
        MinHeapInt();
        MinHeapInt(int initialCapacity);
        ~MinHeapInt();
        void insert(int value);
        int extractMin();
        void printContents();
        int getSize() const;

    private:
        void init(int initialCapacity);
        void exchange(int pos1, int pos2);
        void percolateUp(int pos);
        void percolateDown(int pos);

    private:
        vector<int> data;
        int size;
};
