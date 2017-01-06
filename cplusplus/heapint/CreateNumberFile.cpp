#include <cstdlib>
#include <ctime>
#include <iostream>
#include <fstream>

using namespace std;

int main(int argc, char* argv[])
{
    if (argc != 3)
    {
        cout << "Usage: " << argv[0] << " <File name to create> <Number of lines>" << endl;
        return 1;
    }
    int nolines = atoi(argv[2]);
    srand((unsigned)time(0));
    ofstream outfile;
    outfile.open(argv[1]);
    for (int i=0; i<nolines; i++)
    {
        int nxt = rand() % (nolines +1);
        outfile << nxt << endl;
    }
    outfile.close();
    return 0;
}
