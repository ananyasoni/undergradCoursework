// Copyright (c) 2025 Ananya Shreya Soni.
// Ananya Shreya Soni
// ananya99@cs.washington.edu
// CSE 333
// 04/28/2025
// ex9

#include <iostream>
#include <sstream>
#include <cstdlib>

#include "Vector.h"

using namespace vector333;

int main(int argc, char **argv) {
  // Invoke default constructor
  Vector v0;  // (0,0,0)
  // Invoke overloaded constructor
  Vector v1(1, 1, 1);  // (1,1,1)
  Vector v2(2, 2, 2);  // (2,2,2)
  // Invokes the copy constructor
  Vector v1_copy = v1;  // (1,1,1)
  Vector v2_copy(v2);  // (2,2,2)

  // Invokes the "<<" operator with args (cout, c), then (cout, endl).
  std::cout << "Test constructors and the '<<' operator" << std::endl;
  std::cout << "v0: " << v0 << std::endl;
  std::cout << "v1: " << v1 << std::endl;
  std::cout << "v2: " << v2 << std::endl;
  std::cout << "Vector v1_copy = v1: " << v1_copy << std::endl;
  std::cout << "Vector v2_copy(v2): " << v2_copy << std::endl;
  std::cout << std::endl;

  // Test "=" operator
  v2_copy = v1;
  std::cout << "Test the '=' operator" << std::endl;
  std::cout << "v2_copy after 'v2_copy = v1': " << v2_copy << " = "
            << v1 << std::endl;  // (1,1,1)
  std::cout << std::endl;
  v2_copy = v2;

  // Test "+=" operator
  std::cout << "Test the '+=' operator" << std::endl;
  v1 += v2;
  std::cout << "v1 after 'v1 += v2': " << v1 << std::endl;  // (3,3,3)
  std::cout << std::endl;

  // Test "-=" operator
  std::cout << "Test the '-=' operator" << std::endl;
  v1 -= v1;
  std::cout << "v1 after 'v1 -= v1': " << v1 << std::endl;  // (0,0,0)
  v1 = v1_copy;
  std::cout << std::endl;

  // Test "+" operator
  std::cout << "Test the '+' operator" << std::endl;
  Vector v5 = v2 + v2 + v1;
  std::cout << v5 <<  " = " << v2 << " + " << v2 << " + "
            << v1 << std::endl;  // (5,5,5)
  std::cout << std::endl;

  // Test "-" operator
  std::cout << "Test the '-' operator" << std::endl;
  Vector v6 = v5 + v2 - v1;
  std::cout << v6 <<  " = " << v5 << " + " << v2 << " - "
            << v1 << std::endl;  // (6,6,6)
  std::cout << std::endl;

  // Test "*" operator on 2 vectors
  Vector v7(1, -2, 3);
  std::cout << "Test the '*' operator on 2 vectors (ie: dot product)"
            << std::endl;
  Vector v8 = v7 + v1;  // (2,-1,4)
  double v8_dot_v7 = v8 * v7;  // 16
  std::cout << v8 << " * " << v7 << " = " << v8_dot_v7 << std::endl;
  std::cout << std::endl;

  // Test "*" operator on a vector and double
  std::cout << "Test '*' operator on a vector and double" << std::endl;
  double k = 9;
  Vector v9 = k * v1;  // (9,9,9)
  std::cout << k << " * " << v1 << " = " << v9 << std::endl;
  k = 10;
  Vector v10 = v1 * k;  // (10,10,10)
  std::cout << v1 << " * " << k << " = " << v10 << std::endl;
  std::cout << std::endl;
  std::cout << "end of ex9!" << std::endl;
  return EXIT_SUCCESS;
}
