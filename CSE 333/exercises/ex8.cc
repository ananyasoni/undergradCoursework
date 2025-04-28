// Copyright (c) 2025 Ananya Shreya Soni.
// Ananya Shreya Soni
// ananya99@cs.washington.edu
// CSE 333
// 04/21/2025
// ex8

// Prompts the user for a positive integer and prints
// out all the factors of the positive integer provided
// by the user

#include <iostream>
#include <cstdlib>

using namespace std;

int main(int argc, char** argv) {
  int num;
  cout << "Which positive integer would you like me to factorize? ";
  if (!(cin >> num)) {
    cerr << "cin failed" << endl;
    return EXIT_FAILURE;
  }
  if (num <= 0) {
    cerr << "Must enter a number strictly > 0" << endl;
    return EXIT_FAILURE;
  }
  for (int i = 1; i <= num; i++) {
    if (num % i == 0) {
      if (!(cout << i << " ")) {
        cerr << "cout failed" << endl;
        return EXIT_FAILURE;
      }
    }
  }
  if (!(cout << endl)) {
    cerr << "cout failed" << endl;
    return EXIT_FAILURE;
  }
  return EXIT_SUCCESS;
}
