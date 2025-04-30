// Copyright (c) 2025 Ananya Shreya Soni.
// Ananya Shreya Soni
// ananya99@cs.washington.edu
// CSE 333
// 04/30/2025
// ex10

#ifndef VECTOR_H_
#define VECTOR_H_


#include <iostream>
using std::ostream;

namespace vector333 {

// A Vector represents a vector in 3-space.
class Vector {
 public:
  // constructors:
  // Default: construct the vector (0,0,0)
  Vector();
  // Construct the vector (x,y,z)
  Vector(const double x, const double y, const double z);
  // Copy constructor
  Vector(const Vector &v);
  // Destructor
  ~Vector();

  // Assignment
  Vector &operator=(const Vector &rhs);
  // Updating assignment
  Vector &operator+=(const Vector &rhs);
  Vector &operator-=(const Vector &rhs);

 private:
  // The representation of a Vector is an array containing three
  // doubles giving the magnitudes in the x, y, and z directions
  double* xyz_;

  // stream output (global function, not class member)
  friend std::ostream &operator<<(std::ostream &out, const Vector &v);
  // addition and subtraction: produce a new Vector that results from
  // element-wise addition or subtraction of a and b
  friend Vector operator+(const Vector &a, const Vector &b);
  friend Vector operator-(const Vector &a, const Vector &b);
  // dot-product: if a is (a,b,c) and b is (x,y,z),
  // return ax+by+cz
  friend double operator*(const Vector &a, const Vector &b);
  // scalar multiplication: if v is (a,b,c), return (ak,bk,ck)
  friend Vector operator*(const double k, const Vector &v);
  friend Vector operator*(const Vector &v, const double k);
};
}  // namespace vector333

#endif  //  VECTOR_H_

