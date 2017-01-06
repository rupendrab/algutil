#include <cstdlib>
#include <ctime>
#include <iostream>
#include <fstream>
#include <string>
#include "MinHeapInt.h"

using namespace std;

int main(int argc, char* argv[])
{
    if (argc != 3)
    {
        cout << "Usage: " << argv[0] << " <Input File name> <Output File Name>" << endl;
        return 1;
    }
    MinHeapInt* mh = new MinHeapInt::MinHeapInt(1000);
    ifstream infile;
    string line;
    infile.open(argv[1], std::ifstream::in);
    int i = 0;
    while (getline(infile, line))
    {
        int val = stoi(line);
        mh->insert(val);
        i++;
    }
    infile.close();

    ofstream outfile;
    outfile.open(argv[2]);
    while (mh->getSize() > 0)
    {
        outfile << mh->extractMin() << endl;
    }
    outfile.close();

    return 0;
}
